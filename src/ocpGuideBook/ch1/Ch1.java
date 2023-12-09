package ocpGuideBook.ch1;
// Package names are recommended to start with lower case, otherwise it creates confusion whether it's package or class.
// All lowercase is more recommended because (from stackoverflow):
//   Packages use the underlying file system, which might be case-sensitive or case-unsensitive. 
//   In the case of multi-developers, multi-platform project, it might lead to problems for folder names.

// java.lang package is automatically imported.

// import brings in class. Wildcard brings in all the classes in package.
import java.util.Date;
import java.sql.*;  // wildcard can only be at the end.
// wildcard does not bring in classes in child packages.

public class Ch1 {

    public static void main(String[] args) {
        Date date;  // It's util.Date. Explicit import takes priority.
        // To use sql.Date, use fully qualified name.
       
        int million = 1_000_000;  // can have underscores between numbers to make it easier to read
        System.out.println(million);  // except at the beginning, end, before & after decimal point (Can only be between numbers).
        
        // Wrapper classes
        Integer myInt = null;
        myInt = Integer.valueOf(1);  // can initialize wrapper class with valueOf.
        // valueOf takes int or String, & return Integer.
        // parseInt takes String, & return int.
        
        System.out.println(myInt.doubleValue());  // all primitives extends Number class with helpful methods
        double myDouble = myInt;  // auto-casting between Wrapper & primitive either way. (but not between Wrapper)
        System.out.println(myDouble);
        
        // Text Blocks (multi-line Strings): Starting triple quotes must be in it's own line. Spaces at the end are ignored.
        String myString = """
                    Where do i start from  
                  Align respect with ending triple quotes  
                """;  // Ending triple quotes defines start position if it's leftmost, or defines positions of other lines respect to this line.
                      // Since ending """ is on its own line, there is a line break before it.
        String yourString = """
                Where do i start from  
                  Align respect with me """;  // The line with the last triple quotes defines positions of other lines respect to this line.
        
        System.out.println(myString);
        System.out.println(yourString);
        
        // Can declare variables in one line as long as same type.
        int a, b, c;
        int d, e = 3, f;  // only e is initialized. Comma separates them.
        // int g, int h;  // can't repeat type even though it's same.
        
        // var: could be used for shortening a line of code: MyLongNamedClass myInstance = new MyLongNamedClass(); -> var myInstance...
        var myVar = "one";  // var must be initialized. You can only use var for local variable (variable inside method).
        // var nullVar = null;  // var cannot be initialized null (Java is typed after all. Must know type at declaration).
        // myVar = 1;  // Compile error. Cannot change type of var.
        myVar = null;  // Can change to null once it knows what type.
        // var i = 1, j = 2, k = 3;  // var is not allowed for one line declaration.
    }
    
    // void printMe(int length = 1) {}  // Java do not have default parameters.
}

// field and method must be within class.
// field and method can be in any order.
// fields are declared first (and initialized with default values if not given) then constructor is run.
class Meerkat {
    Meerkat() {
        weight = 3;
    }
    
    public double getWeight() {
        return weight;
    }
    
    double weight = 1;  // field = instance variable = variable outside of method
    
    {weight = 2;}  // brackets outside the method is called instance initializer (Something you would normally do in constructor)
}
