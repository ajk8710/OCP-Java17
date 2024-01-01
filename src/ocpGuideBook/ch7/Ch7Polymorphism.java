package ocpGuideBook.ch7;

public class Ch7Polymorphism {
    
    // Polymorphism:
    // Java object can be assigned as: same type, or superclass/interface that object extends/implements.
    // (Subclass is superclass. Superclass is not subclass. Cast is not required when being assigned as superclass or interface.)
    
    // When assigned as superclass, only methods and variables defined in superclass are available to be called.
    // Calling a method calls overridden method of actual type.  (The reference behaving differently depending on actual object type.)
    // If the method is not overridden, it calls method of superclass.
    
    // All objects can be assigned as java.lang.Object, as it is superclass of all objects.
    // When assigned as superclass/interface, the object itself does not change. What changes are the developer's ability to access methods with the object's reference.
    // You can explicitly cast back to get back the ability.
    
    public static void main(String[] args) {
        
        Parent parent = new Child();  // Actual Child type assigned as Parent.
        Child child = (Child) parent;  // Explicitly casting back to actual type.
        
        // ClassCastException at runtime when casting incompatible types: Parent (bigger) into Child (more specific).
        // Parent parent = new Parent();
        // Child child = (Child) parent;
        
        // Compile error if casting to unrelated type (that makes no sense to compiler).
        // String s = (String) parent;
        
        
        // There is limitation for compiler to enforce compile error on interfaces.
        // While a given class may not implement an interface, it's possible that some subclass of it may implement the interface.
        // (as a class can extend a class "and additionally" implement multiple interfaces.)
        ClassAImplesInterfaceA implesInterfaceA = new ClassAImplesInterfaceA();  // ClassA implements InterfaceA
        // InterfaceB badCast = (InterfaceB) implesInterfaceA;  // ClassCastException. ClassA Does not implement InterfaceB.
        
        ClassAImplesInterfaceA extendsAImplesB = new ClassBExtendsClassAImplesInterfaceB();  // ClassB (implements InterfaceB) implicitly casted to parent ClassA.
        InterfaceB goodCast = (InterfaceB) extendsAImplesB;  // No issue on casting.  Reference is ClassA, but actual object is ClassB.
        
        
        // Use instanceof operator to avoid ClassCastException at runtime.
        Parent p = new Child();
        if (p instanceof Child) {
            System.out.println("Reference p is instance of Child object");  // Reference p is instance of Child object
        }
        
        Parent pa = new Parent();
        if (!(pa instanceof Child)) {
            System.out.println("Reference pa is not instance of Child object");  // Reference pa is not instance of Child object
        }
        
        // Compile error if instanceof is used with two unrelated types.
        String s = "Hi";
        // if (s instanceof Parent) {}  // Compile error. Incompatible conditional operand types String and Parent.
        
        
        Child0 child0 = new Child0();
        child0.print();  // Child Method
        child0.printParent();  // Parent Method
        child0.printInterface();  // Interface Default Method
        
        
        // Unlike instance method,
        // Instance variable, static variables and static methods are hidden, not overridden.
        // Hidden members act on reference, not actual object type.
        SuperClass superClass = new SubClass();
        System.out.println(superClass.instInt);  // prints 0, hidden instance variable of superclass.
        System.out.println(superClass.staticInt);  // prints 0, hidden static variable of superclass. (warning: static should be accessed static way w/ class name)
    }
}

// Examples used for casting to class
class Parent {}
class Child extends Parent {}

// Examples used for casting to interface.
interface InterfaceA {}
interface InterfaceB {}
class ClassAImplesInterfaceA implements InterfaceA {}  // ClassA implements InterfaceA.
class ClassBExtendsClassAImplesInterfaceB extends ClassAImplesInterfaceA implements InterfaceB {}  // ClassB extends ClassA & implements InterfaceB.

// Using super keyword.
class Parent0 {void print() {System.out.println("Parent Method");}}
interface Interface0 {default void print() {System.out.println("Interface Default Method");}}
class Child0 extends Parent0 implements Interface0 {  // can access parent & interface methods using super within class, while still overriding.
    public void print() {System.out.println("Child Method");}  // overriden
    public void printParent() {super.print();}  // parent's method
    public void printInterface() {Interface0.super.print();}  // interface's method
}


// Static members are hidden, not overridden. (instance variable as well)
class SuperClass {int instInt = 0; static int staticInt = 0;}
class SubClass extends SuperClass {int instInt = 1; static int staticInt = 1;}
