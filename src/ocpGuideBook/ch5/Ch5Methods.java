package ocpGuideBook.ch5;

import ocpGuideBook.ch5.protectedMethods.Ch5ClassWithProtectedMethods;

public class Ch5Methods {
    
    // final int imFinal;  // Compile error. Final instance variables do not get default values. Must initialize here or in constructor.
    
    public static void main(String args[]) {
        
        // Methods:
        // The order must be: modifier, optional, (then) return type, method name
        // The optional can come before modifier, or be mixed (optional modifier optional) although un-conventional.
        // No modifier means default package-private. Return type and method name must exist, in that order.
        // Optional specifiers: static, final, abstract (used when no method body), default (only for interface), synchronized
        
        // final method cannot be over-riden in sub-class.
        // default: interface to provide default implementation of a method.
        // final & abstract is incompatible obviously, because then method has no use.
        
        // Method signature is method name and types of parameters & their order.
        // Method signature is how caller refers the method. i.e. identifies which method it's trying to call.
        // A class can't have methods that have same signatures, obviously. (Note that same types but different orders of parameters are ok.)
        
        
        // Can assign final variable later if it is un-itialized/un-assigned.
        final boolean myBool;
        myBool = true;
        
        // final of reference type variable: the reference can't be changed (can't point to different object). Object's content can change.
        final int[] arr = {1};
        arr[0] = 2;
        
        
        // varargs
        varargsEx(0, new int[] {0, 1, 2});  // calling method with varargs by passing an array
        varargsEx(0);  // can omit varargs. Java creates array of length 0.
        varargsEx(0, 0, 1, 2);  // calling method with varargs by listing parameters
        
        
        // protected modifier
        ClassToUseProtectedMethods.testProtectedMethod();  // Extended class have access to protected members.
        ClassToUseProtectedMethods cupm = new ClassToUseProtectedMethods();  // Instance of extended class does not have access to protected members if in another class.
        
        
        // Purpose of static method: 1. Utility/helper method that does not requires instance to be created. 2. A state to be shared throughout instances.
        ClassWithStaticVariable csv = null;  // Compiler checks the type of variable, and use that instead of object.
        System.out.println(csv.staticVar);  // Works fine (no null pointer exception) although gets warning: static field should be accessed static way (ClassWithStaticVariable.staticVar).
        
        
    }
    
    
    public static String ex1() {
        if (1 < 2) return "1 < 2 always gets here";  // won't compile without below
        return "won't reach here";  // dead code but compiles
    }
    
    // Method with varargs: At most one varargs parameter. Must be the last parameter.
    // Pass varargs either by passing an array, or listing parameters (method creates array for you).
    public static String varargsEx(int intParam, int... intParams) {
        for (int i : intParams) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        return null;
    }
    
}


class ClassToUseProtectedMethods extends Ch5ClassWithProtectedMethods {
    
    static void testProtectedMethod() {
        protectedStaticMethod();  // Extended class has access to protected members.
        System.out.println(protectedStaticString);
        
        Ch5ClassWithProtectedMethods cpm = new Ch5ClassWithProtectedMethods();  // Instance of itself does not have access to protected members if in another class.
        
        ClassToUseProtectedMethods cupm = new ClassToUseProtectedMethods();  // Instance of extended class has access to protected members if in extended class.
        System.out.println(cupm.protectedString);
        cupm.protectedMethod();
    }
}


class ClassWithStaticVariable {
    static int staticVar = 0;
    int instantVar = 0;
    
    static void printVars() {
        System.out.println(staticVar);
        // System.out.println(instantVar);  // Compile error. Static member cannot access instance member. Cannot make a static reference to the non-static field.
    }
}
