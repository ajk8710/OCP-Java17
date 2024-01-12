package ocpGuideBook.cha11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ch11Exceptions {
    
    public static void main(String[] args) {
        
        // A method can handle the exception by itself or make it the caller’s responsibility.
        // Throwable -extended by- Exception & Error.  Exception -extended by- RuntimeException.
        // Unchecked exceptions are ones that extends RuntimeException or Error.
        // Checked exceptions are Exceptions but are not RuntimeException.
        
        // Checked exceptions have "Handle or Declare" rule:
        // All checked exceptions that could be thrown in a method
        // should be handled by try & catch block (handle myself), or be declared in method signature (let caller handle).
        // (Checked exceptions are ones that tend to be more anticipated. Programmer must be thinking about it when writing codes.)
        
        // Unchecked exceptions could happen anywhere, such as NullPointerException.
        // Error means something went horribly wrong and you should not try to recover, such as disk drive disconnected.
        
        // Unchecked Exceptions (extends RuntimeException):
        // ArithmeticException, ArrayIndexOutOfBoundsException, ClassCastException, NullPointerException
        // IllegalArgumentException, NumberFormatException (extends IllegalArgumentException)
        
        // Checked Exceptions:
        // IOException, FileNotFoundException (extends IOException), NotSerializableException (extends IOException), ParseException, SQLException
        
        // An Overriding method can not declare any new or broader checked exceptions than parent's method.
        // method(Parent parent) { parent.someMethod don't throw exception and is not handled }
        // A child can go into parent, then child.someMethod (overridden) throws exception - This is problem thus not allowed.
        
        
        try {  // braces are "required" for try catch blocks.
            throw new RuntimeException();  // Exception thrown, method returns.
            // throw new ArrayIndexOutOfBoundsException();  // Compile error, unreachable code.
        } catch (Exception e) {}
        
        /* 
        // Compile error if checked exception is caught from where it cannot be thrown.
        try {
            // No method is throwing checked exception.
        } catch (IOException e) {  // checked exception. Compile error, unreachable catch block.
            System.out.println("Unreachable");
        }
        */
        
        try {
            // This is fine. Compiler has no way of knowing if unchecked exception will be thrown or not.
        } catch (RuntimeException e) {  // unchecked exception
            System.out.println("Compiler thinks it's reachable");
        }
        
        // If it's impossible for one of the catch blocks to be executed, a compiler error about unreachable code occurs.
        // For example, this happens when a superclass catch block appears before a subclass catch block.
        try {
            throw new RuntimeException();
        } catch (Exception e) {}  // parent of RuntimeException
        // catch (RuntimeException e) {}  // Compile error, unreachable code.
        
        // Multi-catch block, separated by pipe |. Note there is only one variable e at the end.
        try {
            throw new NumberFormatException();
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println("Multi-Catch Block");
        }
        
        /* Compile error, redundant multi-catch.
        try {
            throw new NumberFormatException();
        } catch (NumberFormatException | IllegalArgumentException e) {  // NumberFormatException (child) can be caught by IllegalArgumentException (parent) as well.
            System.out.println("Multi-Catch Block");
        }
        */
        
        
        // finally block
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Runtime exception occured");
        } finally {  // finally block should be at the end
            System.out.println("Always runs after try-catch and before returning, whether or not exception occurs.");
        }
        
        try {
            
        } finally {  // catch block is not required only if there is finally block. (try alone is not allowed)
            System.out.println("Catch block is not required only if there is finally block.");
        }
        
        int returnValue = finallyExample();  // 1 3
        System.out.println(returnValue);  // 3
        
        /*
        // One exception to finally always runs: System.exit(int exitCode);
        try {
            System.exit(0);
        } finally {
            System.out.print("Never gets here");  // not printed
        }
        */
        
        
        // try-with-resources: automatic resource management
        // Instead of manually having finally block to close resources,
        // implicit finally block is created by Java to close all resources specified by try-with-resources.
        // try (Must be resources. i.e. Only classes that implement AutoCloseable interface are allowed here) {...}
        // (AutoCloseable has close() method that throws Exception.) (Close extends AutoCloseable and its close() throws IOException.)
        
        // Instead of declaring resources above here (so that finally block has access to them), I declared below with try.
        try (FileInputStream input = new FileInputStream("myfile.txt")) {  // resources are in scope of try block only
            // Instead of opening resources here, I opened above with try.
            // Read file data.
        } catch (IOException e) {
            // e.printStackTrace();
        }  // No need for finally block to close resources. Resources are closed when they go out of try block with try-with-resources.
        
        // Having multiple resources
        try (FileInputStream input = new FileInputStream("myfile1.txt");  // required semicolon between resources
            FileInputStream output = new FileInputStream("myfile2.txt");) {  // optional semicolon after the last resource
            // Read file data.
            // Resources are closed in reverse order before leaving try block.
        } catch (IOException e) {
            // resources are closed if they left try block.
            // catch is optional with try-with-resources, if method signature declares required checked exception.
        } finally {
            // optional finally runs after resources are closed.
        }
        
        // Resources can be declared & initialized outside of try-with-resources, as long as they are final or effectively final.
        // try (input; output;) {...}
        
    }
    
    // finally block example
    // finally block always runs after try-catch and before returning
    static int finallyExample() {
        try {
            // Optionally throw an exception here
            System.out.println("1");  // prints if exception not thrown
            return 1;  // never gets to this return (if finally is returning)
        } catch (Exception e) {
            System.out.println("2");  // prints if exception is thrown
            return 2;  // never gets to this return (if finally is returning)
        } finally {  // Warning: finally block does not complete normally (because finally block normally does not have return statement)
            System.out.println("3");  // prints always after try-catch
            return 3;  // return happens here (You do not need to have return statement in finally. Control will return to try or catch to return.)
        }
    }
    
    static void tryWithResourcesExample() throws IOException {
        try (FileInputStream input = new FileInputStream("myfile1.txt")) {
            // Example of: catch is optional with try-with-resources, if method signature declares required checked exception.
        }
    }
    
}

class MyResource implements AutoCloseable {

    @Override
    public void close() { // throws Exception {  // Overridden method can shrink exception. (But cannot widen it.)
    
    }
}
