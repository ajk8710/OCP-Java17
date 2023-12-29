package ocpGuideBook.ch7;

// Java file can have at most one top-level public type and it should match file name. (type: class, interface, enum, record, annotation)
// Top-level type can only be public or default package access.
public interface Ch7Interfaces {
    
    // Interface Visibility:
    // Interface can only be public or default package access.
    // Interface cannot be final (as final & abstract combination is not allowed, even for class).
    // Interface cannot be instantiated. Class that implement interface can be instantiated.
    // protected interface NestedInterface {}  // Compile error. protected not allowed.
    abstract interface NestedInterface1 {}  // all interfaces are abstract, implicitly.
    
    // protected and package access is not allowed for interface members (members mean variables and methods: only public and private allowed).
    
    // Implicit Modifiers: compiler inserts if not there (and compile error if it has modifiers that are conflict with them).
    // All interfaces are: abstract
    // All methods without a body in interface are: abstract
    // All method that are not private are: public (Thus implemented method in implementing class should also be explicitly declared public.) 
    // All variables in interface are: public static final
    public abstract int method1(int i);
    int method2(int i);
    private int method3(int i) {return 1;}  // All private methods must be implemented. (private & abstract are incompatible.)
    
    public static final int CONSTANT1 = 1;
    int CONSTANT2 = 2;
    
    
    // interface can extends another (multiple) interfaces.
    // For exam, keep in mind: class implements interface, interface extends interface. (Likewise, class extends class.)
    interface NestedInterface2 extends Ch7Interfaces, NestedInterface1 {}
    
    // abstract class can implement interfaces (and does not actually have to implement it, leaving extending concrete class to implement).
    
    
    // Resolving multi-implementation conflict:
    // Unlike extending a class (that is single-inheritance), conflict from multi-implementation is allowed and resolved for interface,
    // as all public methods are abstract in interface (except default and static methods).
    // (Conflicting default method must be overridden. Static methods in interface are not inherited.)
    // There is no conflict on which implementation to use, even if two interfaces have the method with same signature (that are compatible).
    // The implementing class's implemented version is the only one to use it.
    
    // However, conflicting methods still must be compatible, for example, return type must be covariant.
    // A class cannot implement two interfaces with methods with same method signatures with non-covariant return types.
    // (covariant means same or sub-type: Number & Integer are ok. double & int are not ok.)
    
    
    // Rules for default method:
    // Interface, and only the interface can have default methods, that can be overridden by implementing class.
    // (Note that default method is different from default label in switch statement, or default package private access.)
    // Default method is implicitly public (and can only be public).
    // Default method cannot be abstract, final, private, or static (as default method are meant to be associated with implementing class).
    // If class implements multiple default methods with same signature, class must override the method to avoid conflict.
    
    
    // Rules for static method (of interface):
    // Static method are implicitly public, unless marked private.
    // Static method cannot be abstract or final.
    // Static method are not inherited, and cannot be accessed by implementing class without a reference to interface name.
}

interface Interface1 {
    void conflictingMethod1();  // implicitly public abstract
    default void conflictingMethod2() {}  // default method implementation
    static void staticMethod() {};  // static method
}

interface Interface2 {
    void conflictingMethod1();
    default void conflictingMethod2() {Interface1.staticMethod();}  // You do not need to extend interface, to access static method.
    // static {}  // Compile error.  Interfaces do have any initializer (static or not), since it cannot be instantiated.
}

class Class1 implements Interface1, Interface2 {
    // void conflictingMethod1() {}  // Compile error. Cannot reduce visibility (from public to package private).
    public void conflictingMethod1() {}  // Conflict resolved. Class1.conflictingMethod1 is the only implemented version.
    public void conflictingMethod2() {}  // Conflict resolved, by "must override conflicting default method" rule.
}

class Class2 implements Interface1, Interface2 {
    public void conflictingMethod1() {}
    public void conflictingMethod2() {Interface1.super.conflictingMethod2();}  // Special syntax, if class wants to use default implementation of one of interface.
    {Interface1.staticMethod();}  // Static methods are not inherited and can be accessed with class name.
}

class Class3 {
    {Interface1.staticMethod();}  // You do not need to implement interface, to access static method.
}

// private methods are used for code re-usability and encapsulation of logic.
// private static method can be called by any method within interface (static or non-static).
// private non-static method cannot be called by static method (static cannot make reference to non-static).

// default & private non-static method can call abstract method. (Reason for existence of private non-static method in interface. It is associated with instance of implementing class.)
// - private static method cannot make static reference to non-static abstract method. (Note that abstract cannot be used with static.)
interface PrivateMethodEx {
    void abstractMethod();
    default void defaultMethod() {
        anotherPrivateMethod();
        anotherPrivateStaticMethod();
        abstractMethod();
    }
    private void privateMethod() {};
    private static void privateStaticMethod() {};
    private void anotherPrivateMethod() {
        privateMethod();
        privateStaticMethod();
        abstractMethod();
    }
    private static void anotherPrivateStaticMethod() {
        // privateMethod();  // Compile error. Cannot make static reference to non-static method.
        privateStaticMethod();
        // abstractMethod();  // Compile error. Cannot make static reference to non-static method.
    }
}
