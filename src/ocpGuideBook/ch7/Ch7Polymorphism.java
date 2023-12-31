package ocpGuideBook.ch7;

public class Ch7Polymorphism {
    
    // Polymorphism:
    // Java object can be assessed as: same type, or superclass/interface that object extends/implements.
    // (Subclass is superclass. Superclass is not subclass. Cast is not required when being assigned as superclass or interface.)
    
    // When assigned as superclass, only methods and variables defined in superclass are available to be called.
    // Calling a method calls overridden method of actual type.
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
    }

}

class Parent {}
class Child extends Parent {}
