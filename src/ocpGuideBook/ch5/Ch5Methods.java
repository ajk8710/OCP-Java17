package ocpGuideBook.ch5;

import ocpGuideBook.ch5.protectedMethods.Ch5ClassWithProtectedMethods;

// Static import imports static members of class. (Regular import imports a class or classes within a package with *)
import static java.lang.Math.*;  // Static import let us to use static variables/methods without specifying class name.
import static java.util.Collections.sort;  // Static import imports all overloaded methods. Do not include parameters in import.

public class Ch5Methods {  // class modifiers: public, no-modifier, abstract, final (protected and private only for nested class)
    
    final int imFinal;  // Compile error. "Final" instance variables do not get default values. Must initialize here or in constructor.
    {imFinal = 0;}      // or in initializer block
    
    final static int imStaticFinal;
    static {
        imStaticFinal = 0;  // Static variables must use static initializer instead or regular initializer.
        // System.out.println(imFinal);  // Cannot make static reference to non-static field.
    }  
    // Static initializers run when class is loaded.
    // Regular initializers run when instance is created: After initialization of instance variables and before running constructor.
    // Regular initializers do not run if instance is not created.
    
    public static final double MY_PI = 3.14;  // common and conventional way to define constants
    
    public static void main(String args[]) {
        
        // Methods:
        // The order must be: modifier, optional, (then) return type, method name
        // The optional can come before modifier, or be mixed (optional modifier optional) although un-conventional.
        // No modifier means default package-private. Return type and method name must exist, in that order.
        
        // Optional specifiers: static, final, abstract (only for abstract class), default (only for interface), synchronized
        // final method cannot be over-riden in sub-class.
        // abstract: method without body (to be implemented by implementing class).
        // default: interface to provide default implementation of a method.
        // final & abstract is incompatible obviously, because then method has no use.
        
        // Method signature is method name and types of parameters & their order. Method signature defines method overloading.
        // Method signature is how caller refers the method. i.e. identifies which method the caller is trying to call.
        // A class can't have methods that have same signatures, obviously. (Note that same types but different orders of parameters are ok.)
        
        
        // Can assign final variable later if it is un-itialized/un-assigned.
        final boolean myBool;
        myBool = true;
        
        // final of reference type variable: the reference can't be changed (can't point to different object). Object's content can change.
        final int[] arr = {1};
        arr[0] = 2;
        
        final double LOCAL_PI = 3.14;  // final is only available modifier for local variable. (No public, static, etc. It is "local" variable.)
        // Instance variable modifiers: final, volatile, transient, as well as public, static, etc.
        
        
        // varargs: varargsEx(int intParam, int... intParams)
        varargsEx(0, new int[] {0, 1, 2});  // calling method with varargs by passing an array
        varargsEx(0);  // can omit varargs. Java creates array of length 0.
        varargsEx(0, 0, 1, 2);  // calling method with varargs by listing parameters
        
        
        // protected modifier
        ClassToUseProtectedMethods.testProtectedMethod();  // Extended class have access to protected members.
        ClassToUseProtectedMethods cupm = new ClassToUseProtectedMethods();  // Instance of extended class does not have access to protected members if in another class.
        
        
        // Purpose of static method: 1. Utility/helper method that does not require instance to be created. 2. A state to be shared throughout instances.
        ClassWithStaticVariable csv = null;  // When accessing static member, compiler checks the type of variable, and use that instead of object.
        System.out.println(csv.staticVar);  // Works fine (no null pointer exception) although gets warning: static field should be accessed static way (ClassWithStaticVariable.staticVar).
        
        
        // Java is pass by value: method gets a new copy of variable, or a new copy of reference variable that points to same object.
        // paramPassed = new Obj() does not modify original.  paramPassed.appened(something) modifies original.
        
        
        // Autoboxing (primitives to Wrappers) and Unboxing (Wrappers to primitives)
        // Doing explicitly:
        int myInt = 1;
        Integer myInteger = Integer.valueOf(myInt);
        myInt = myInteger.intValue();
        
        // Above is verbose and Java does autoboxing/unboxing (auto conversion) between primitives and Wrappers
        myInteger = 1;
        myInt = myInteger;
        
        // More common use of valueOf (one that takes string parameter)
        myInteger = Integer.valueOf("1");
        myInt = Integer.parseInt("1");
        
        // Unboxing then auto-casting to larger type can happen together. But not auto-casting and Autoboxing.
        long myLong = myInteger;  // Integer to int to long
        // Long myLongWrap = 1;  // Compile error (cannot convert int to long)
        
        
        // Overloaded methods are looked up by the order of: exact match, larger primitive, wrapper, varargs
        // Calling method(int) will look for method(int), method(long), method(Integer), method(int varargs), method(long varargs)
        method(1);  // long: 1
    }
    
    static void method(long i) {System.out.println("long: " + i);}
    static void method(Integer i) {System.out.println("Integer: " + i);}
    static void method(int... i) {System.out.println("int varargs: " + i);}
    static void method(long... i) {System.out.println("long varargs: " + i);}
    
    void overloadEx(int[] ints) {}
    // void overloadEx(int... ints) {}  Java treats varargs as arrays. Compile error. If an array is passed, it is not distinguishable.
    
    static void sqrt(double d) {};  // Method defined in the class hides imported class/method with same name.
        
    public static String ex1() {
        if (1 < 2) return "1 < 2 always gets here";  // won't compile without below, because compiler says the method may not return String.
        return "won't reach here";  // dead code but compiles
    }
    
    // Method with varargs: At most one varargs parameter. Must be the last parameter.
    // Pass varargs either by passing an array, or listing parameters (method creates array for you).
    public static void varargsEx(int intParam, int... intParams) {
        for (int i : intParams) {
            System.out.print(i + " ");
        }
        System.out.println();
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
    
    static void staticMethod() {
        System.out.println(staticVar);
        // System.out.println(instantVar);  // Compile error. Static member cannot access instance member. Cannot make a static reference to the non-static field.
        ClassWithStaticVariable csv = new ClassWithStaticVariable();  // unless instance is created and refers that instance
        System.out.println(csv.instantVar);
    }
    
    void instantMethod() {
        System.out.println(staticVar);  // Instance member can access static member.
    }
}
