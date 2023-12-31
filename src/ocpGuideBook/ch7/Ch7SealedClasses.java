package ocpGuideBook.ch7;

// Sealed class is new feature in Java 17.
// sealed: Indicates that a class or interface may only be extended/implemented by named classes or interfaces.
// permits: Used with the sealed keyword to list the classes and interfaces allowed.
// non-sealed: Applied to a class or interface that extends a sealed class, indicating that it can be extended by unspecified classes.

// sealed is modifier that comes anywhere before class.
// sealed class can have permits keyword.
// Permitted classes must be defined in same package or same module.
public sealed class Ch7SealedClasses permits Child1, Child2 {
    
    public static void main(String[] args) {
        
    }
    
    // Below does not compile. Once permitted classes are declared, only they can be direct child.
    // Or should permit Ch7SealedClasses.NestedClass also.
    // final class NestedClass extends Ch7SealedClasses {}
}

// class that is permitted in sealed class must extend sealed class. (It's like must-have direct parent-child relationship)
// class that extends sealed class must be marked non-sealed, final, or sealed.
// sealed class and child must be in same package or same module (same compilation unit).

// non-sealed is used to open sealed parent class to potentially unknown classes.
// (The person writing sealed class gets to decide whether to have non-sealed child or not.)
non-sealed class Child1 extends Ch7SealedClasses {}

class RandomChild extends Child1 {}

abstract sealed class Child2 extends Ch7SealedClasses permits GrandChild {}  // sealed classes are commonly declared as abstract, although not required.

final class GrandChild extends Child2 {}

// Textbook says nested class and class in same file do not needs be in permit list.
// But I can only get it to work on nested class. Maybe it's still preview feature.
sealed class MyClass {  // sealed class can omit permit keyword but still needs be extended by some class.
    final class NestedClass extends MyClass {}
}

// sealed interface can have permits on implementing classes and extending interfaces.
// Interface that extends sealed interface cannot be final, as final & abstract are not compatible. (can only be sealed or non-sealed)
