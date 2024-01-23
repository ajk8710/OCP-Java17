package ocpGuideBook.cha15;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class Ch15JDBC {
    
    public static void main(String[] args) {
        
        /*
        CRUD: CREATE, READ, UPDATE, DELETE
        SQL:  INSERT, SELECT, UPDATE, DELETE
              INTEGER, DECIMAL, VARCHAR = int, double, String
              BIGINT, BOOLEAN = long, boolean
        
        Interfaces are declared in JDK.
        Concrete classes come from JDBC Driver in vendor-specific jar file.
        You do not need to know concrete class names, use interface names in your code.
        
        Interfaces in java.sql:
        Driver: Establishes a connection to the database
        Connection: Sends commands to a database
        PreparedStatement: Executes a SQL query
        CallableStatement: Executes commands stored in the database
        ResultSet: Reads the results of a query
        
        Exam uses DriverManager class instead of the DataSource interface. (Use DataSource in practice.) 
        */
        
        String url = "jdbc:hsqldb:file:example";  // DriverManager class manages set of drivers available.
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement ps = conn.prepareStatement("SELECT name FROM exhibits"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) System.out.println(rs.getString(1));  // first column is 1 (Remember SQL index is 1-based)
        }
        catch (SQLException e) {
            System.out.println("Exception");
        }
        
        /*
        JDBC URL:
        protocol(always jdbc) : subprotocol(vendor name) : subname(varies, location and/or name))
            jdbc:hsqldb://localhost:5432/zoo
        
        DriverManager uses factory pattern, has static methods getConnection(url) & getConnection(url, username, pw) to get instance of Connection (vendor-specific connection class).
        Factory pattern takes care of details of creating instance. For example, you do not need to know actual class name.
        (DriverManager looks for driver that can handle given JDBC URL.) (Look out for new keyword on exam. It's probably compile error.)
        
        PreparedStatement & CallableStatement is sub-interface of Statement. Statement is not SQL-injection safe.
        PreparedStatment requires SQL query as parameter.
        var ps = conn.prepareStatement("SELECT name FROM exhibits");
        var ps = conn.prepareStatement();  // Compile error without SQL query
        */
        
        try (Connection conn = DriverManager.getConnection(url)) {
            
            // INSERT, UPDATE, DELETE: They typically use executeUpdate().
            String insertSql = "INSERT INTO exhibits VALUES(10, 'Deer', 3)";
            try (var ps = conn.prepareStatement(insertSql)) {
                int result = ps.executeUpdate();  // executeUpdate() returns number of rows affected.
                System.out.println(result);
            }
            
            // SELECT: uses executeQuery()
            String sql = "SELECT * FROM exhibits";
            try (var ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery() ) {  // executeQuery() returns ResultSet.
                // work with rs
            }
            
            // execute() executes query then returns boolean whether there is ResultSet. Then we can call either getResultSet() or getUpdateCount()
            // If SELECT, it returns true, false otherwise.
            try (var ps = conn.prepareStatement(sql)) {
                boolean isResultSet = ps.execute();
                if (isResultSet) {
                    try (ResultSet rs = ps.getResultSet()) {
                        System.out.println("Ran a query");
                    }
                } else {
                    int result = ps.getUpdateCount();
                    System.out.println("Ran an update: " + result);
                }
            }
            
            // If wrong method is called for SQL statement, SQLException is thrown. Driver can't translate query into expected return type.
            
            
            // Working with parameters: Question marks (?) are bind variable (place holders).
            sql = "INSERT INTO USERS VALUES(?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int userId = 1;
                String name = "Potato";
                ps.setInt(1, userId);  // SQL index starts with 1.
                ps.setString(2, name);
                ps.executeUpdate();
            }
            // setNull takes int parameter (sets to designated SQL NULL), setObject takes any Java type (if primitive is passed, wraps to Wrapper).
            // setNull(3, Types.INTEGER);  Sets column 3, Integer Null.
            
            // When executing ps multiple times, PreparedStatement is smart enough to remember parameters that were already set.
            
            
            // Reading ResultSet:
            sql = "SELECT id, name FROM exhibits";
            HashMap idToNameMap = new HashMap<Integer, String>();
            
            try (var ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {  // rs cursor initially points to table header. Call rs.next() to advance to a row of data.
                while (rs.next()) {  // advance cursor, return true if new current row is valid, false if there are no more rows.
                    int id = rs.getInt("id");  // rs.getInt(1);  You can use column position as parameter, but it's confusing and causes issue if column is reordered.
                    String name = rs.getString("name");  // rs.getString(2);  Make sure rs.next() is true (row exists) before getting columns, or SQLException.
                    idToNameMap.put(id, name);
                }
                System.out.println(idToNameMap);
            }
            
            
            /*
            In some situations, it is useful to store SQL queries in the database instead of inside Java codes.
            Stored procedure is code that is compiled in advance and stored in the database. They are database-specific.
            For exam, you don't need to know how to write/read stored procedures, but how to call them.
            */
            
            // Call stored procedure with no parameter:
            sql = "{call read_e_names()}";  // {call procedure_name}
            try (CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString(3));  // 3rd column. Getting by column position is not good practice though.
                }
            }
            
            // Call stored procedure with IN parameter:
            sql = "{call read_names_by_letter(?)}";  // pass ? to show that you have parameter.
            try (var cs = conn.prepareCall(sql)) {
                cs.setString("prefix", "Z");  // setString(paramName, param)  Unlike PreparedStatement, you can use param name, instead of param index.
                try (var rs = cs.executeQuery()) {  // nested try-with-resources, after setting bind variable (?)
                    while (rs.next()) {
                        System.out.println(rs.getString(3));  // Getting by column position is not good practice though.
                    }
                }
            }
            
            // Call stored procedure with OUT parameter as output:
            sql = "{?= call magic_number(?)}";  // ?= to specify that stored procedure has output that is not ResultSet. It's optional but helps readability.
            try (var cs = conn.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);  // allows JDBC to retrieve output that is not ResultSet.
                cs.execute();  // execute() executes query then returns boolean whether there is ResultSet. We know we are not getting ResultSet (This statement is not SELECT).
                System.out.println(cs.getInt("num"));  // grab the output and print. Note CallableStatment has getXXX for many types since result can vary depending on stored procedure.
            }
            
            // Call stored procedure with INOUT parameter:
            sql = "{call double_number(?)}";  // optional ?= omitted.
            try (var cs = conn.prepareCall(sql)) {
                cs.setInt(1, 8);  // set IN parameter
                cs.registerOutParameter(1, Types.INTEGER);  // be ready to get result into OUT parameter
                cs.execute();  // execute and get OUT parameter
                System.out.println(cs.getInt("num"));  // grab the output and print
            }
            
            // Just be able to recognize that you can pass options on these methods. There is no method with just one option.
            // prepareStatement(String sql, int resultSetTypeOption, int resultSetConcurrencyOption)
            // prepareCall(String sql, int resultSetTypeOption, int resultSetConcurrencyOption)
            
        }
        catch (SQLException e) {
            System.out.println("Exception");
        }
        
        
        // On exam, changes are committed automatically unless otherwise specified.
        // Know following behaviors.
        
        // conn.setAutoComit(false)
        // conn.rollback() - Rolls back all executed commands that you did since last commit/transaction.
        // conn.comit()
        
        // conn.setAutoComit(true) automatically triggers commit if not already in auto-comit mode.
        // Closing connection without roll back or commit - Results of executed commands are undefined.
        
        // Savepoint sp1 = conn.setSavepoint();
        // Savepoint sp2 = conn.setSavepoint("second savepoint");  // Optional toString string.
        // conn.rollback(sp1);  // rolls back to sp1, removes Savepoint sp2.
        // conn.rollback();  // rolls back to last commit, removes all savepoints.
        // conn.rollback(sp2);  // throws SQLExeption. sp2 was removed.
        
        // Calling rollback(), when there's nothing to roll back, does not do anything (when on non auto-comit mode).
        
        
        // Closing Database Resources:
        // Closing Connection also closes PreparedStatement (or CallableStatement) and ResultSet.
        // Closing PreparedStatement (or CallableStatement) also closes ResultSet.
        // If you run another SQL statement on Pre/CallStatement, its previous ResultSet automatically closes.
        
        // To properly close one by one, close ResultSet, Pre/CallStatement, then Connection.
        
    }
    
}
