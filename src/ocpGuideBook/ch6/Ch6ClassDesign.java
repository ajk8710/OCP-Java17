package ocpGuideBook.ch6;

public class Ch6ClassDesign {  // class modifiers: public, no-modifier, abstract, final (protected and private only for nested class)
                               // sealed, non-sealed, static (for static nested class)
    public class a {}  // nested class (not top level)
    protected class b {}  // nested class
    private class c {}  // nested class
    
    public static void main(String[] args) {
        
        // A single java file can have at most one top level public class. Can have no public class at all.
        // A top level public class must have same name as file name.
        
        // final class cannot be extended.
        
        // Java is single inheritance: Can only have one direct parent class. (Can implement multiple interfaces)
        // (Multiple inheritance complicates program when it's ambiguous which parent to inherit from, in case of conflict.)
        
        // All classes inherit from java.lang.Object class. Object class do not have parent.
        // Object class has toString() printing hash code, equals() doing == (comparing references).
        // Compiler adds "extends java.lang.Object" when a class does not extend any class.
        
        // this keyword cannot be used in static method or static initializer, where it has no implicit instance of a class.
        // System.out.println(this);  // Compile error (Cannot use this in a static context)
        
        new Child();  // example using super
        
        // Compiler only inserts the default constructor when no other constructor is defined.
        
        // If parent class does not have default constructor, child class may have problems.
        // Child class's constructors will insert super() call, calling parent's default constructor, unless another super(params) call is specified.
        new ConstructorEx(2);  // constructor example
        
        
        // Class initialization (not object initialization):
        // Class is initialized/loaded before it is used. JVM determines exactly when. It happens at most once.
        // Parent class is loaded first before child class. It runs all static variable declaration. Then static initializers.
        // Class with main method needs be loaded first, before main method runs.
        
        // Creating new instance (instance initialization):
        // 1. Load classes from parent to this class.
        // 2. Initialization process of parent objects. (instance variable initialization, instance initializer {}, then constructor with appropriate super())
        // 3. Initialization process of this object. (instance variable initialization, instance initializer {}, then constructor)
        
        
        // Overriding vs Hiding:
        // Hidden instance variable and hidden static methods are referred by object type that calls it.
        // Overridden methods replaces parent methods.
        Parent2 p = new Child2();
        Child2 c = new Child2();
        System.out.println(p.val);  // parent2.val (hidden instance variable)
        System.out.println(c.val);  // child2.val
        
        System.out.println(p.staticMethod());  // Parent2.staticMethod (hidden static method) (static method technically should be accessed static way though)
        System.out.println(c.staticMethod());  // Child2.staticMethod
        
        System.out.println(p.method1());  // child2.method1 (parent2.method1 is Overridden. Polymorphism allows correct method to be run depending on actual sub-type)
        System.out.println(c.method1());  // child2.method1
        
        // Variable/method hiding should generally be avoided to reduce confusion.
    }
}

class Parent {
    String str = "Parent Str";
    String str2 = "Inherits From Parent";
    // Parent(int a) {}  // This will make Child not compile, because then there is no default Parent() constructor,
                         // and Child constructors will call super(), unless another super(params) call is specified.
}

class Child extends Parent {
    String str = "Child Str";  // Child has two str: this.str and super.str.
    
    Child() {
        // The constructors of the parent classes are called, all the way up the class hierarchy through Object,
        // before the child class's constructor is called. (It is called constructor chaining.)
        // super();  // Java automatically inserts super() if super(), super(params), this(), or this(params) is not specified.
        
        System.out.println(this);  // prints ocpGuideBook.ch6.Child@hashcode
        System.out.println(str);  // Child Str. Java looks from narrowest scope. If not exists, looks further.
        System.out.println(super.str);  // Parent Str (hidden, unless specify with super)
        
        System.out.println(str2);  // Inherits From Parent
        System.out.println(this.str2);  // Inherits From Parent
        System.out.println(super.str2);  // Inherits From Parent (Child has only one str2, inherited from parent.)
    }
}

class ConstructorEx {  // Example using this() constructor call. "this" is this instance. "this()" is constructor call within constructor.
    int a;
    int b;
    
    ConstructorEx() {
        // this();  // Compile error. this() call cannot be itself, because it's infinite loop.
        // Also avoid cyclic constructor calls: Const1 call Const2. Const2 call Const1. (It's compile error, infinite loop.)
        a = 1;
    }
    
    ConstructorEx(int a) {
        // ConstructorEx();  // Compile error. Constructor call within a constructor must be made with this().
        this();  // calls ConstructorEx() and sets this.a = 1;
        // this(a, 3);  // Compile error. Constructor call must be first statement in constructor. (There can only be one this() or super() call in a constructor.)
        System.out.println("a: " + this.a);
        this.a = a;  // sets this.a = a
        System.out.println("a: " + this.a);
    }
    
    ConstructorEx(int a, int b) {  // for other constructors to call such as this(a, defaultValue);
        this.a = a;
        this.b = b;
    }
}

class Parent2 {
    int val = 1;
    
    int method1() {
        return 1;
    }
    
    static int staticMethod() {
        return 1;
    }
}

class Child2 extends Parent2 {
    int val = 2;  // super.val is hidden.
    
    // Overriding method (child method with same signature as parent method) rules, or gets compile error:
    // 1. Return type must be same or sub-type (covariant).
    // 2. Access modifier can't be more restrictive. (ex. If parent method is public, child method can't be private.) 
    // 3. Must not declare new or broader checked exception.
    @Override  // @Override annotation prevents from making mistakes, complains if method is not overriding. (i.e. if not same signature)
    int method1() {
        return super.method1() + 1;
    }
    
    static int staticMethod() {  // Static methods can be hidden, not overridden. Still same rules follow as overriding, or gets compile error.
        // Compile error if one is static and the other is not (between child & parent method with same signature).
        // There is no super or this here because method is static.
        return 2;
    }
    
    // final instance method cannot be overridden. final static method cannot be hidden.
}

abstract class AbstractClass1 {  // abstract class can't be instantiated. (except as for part of process of child class instantiation)
    public AbstractClass1() {}  // abstract class can have constructors, only to be called by child class. (Java inserts default constructor if no constructor exists)
    abstract void abstractMethod1();  // abstract class can have abstract (instance) methods that must be implemented by first concrete child class.
    void method1() {}
    // abstract cannot be used with final, private, or static.
}

abstract class AbstractClass2 extends AbstractClass1 {  // abstract class can extend another abstract class or concrete class.
    void abstractMethod1() {}  // Abstract class can implement abstract method (abstract method from AbstractClass1).
    abstract void abstractMethod2();
}

class concreteClass extends AbstractClass2 {  // concrete (non-abstract) class must implement all abstract methods it inherits.
    // abstractMethod1 is already implemented by AbstractClass2. If it was not, concrete class must implement it.
    void abstractMethod2() {}
}
