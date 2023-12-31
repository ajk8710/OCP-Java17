package ocpGuideBook.ch7;

public class Ch7NestedClasses {
    
    private String str = "Outer String";
    
    public static void main(String[] args) {
        
        // Compile error. (Non-static) InnerClass instance belongs to Ch7NestedClasses instance & main is static method. It would work in instance method.
        // InnerClass innerClass = new InnerClass();
        
        Ch7NestedClasses outerClass = new Ch7NestedClasses();
        InnerClass innerClass = outerClass.new InnerClass();  // funky syntax to initialize inner class
        
        innerClass = new Ch7NestedClasses().getInnerClass();  // Or use instance method of outer class to get inner class.
        
        
        // Outer class has access to all members of inner or static nested class, even private.
        // (That's what makes difference compared to non inner/nested classes.)
        innerClass.printStr();  // prints Inner String, Inner String, then Outer String
        System.out.println(InnerClass.staticStr);  // Static Inner String
        
        StaticNestedClass staticClass = new StaticNestedClass();
        System.out.println(staticClass.instanceVarOfStaticNestedClass);  // instanceVarOfStaticNestedClass
        System.out.println(StaticNestedClass.staticVarOfStaticNestedClass);  // staticVarOfStaticNestedClass
        
        
        outerClass.methodWithLocalClass();  // example using local class
        
        
        // Anonymous class is a specialized form of a local class that does not have a name.
        // It must extend/implement existing class/interface.
        // Useful for short implementation of class/interface that will not be used anywhere else.
        // It can access final & effectively final local variables.
        
        // Create an instance of anonymous class without a name, that extends AbsClass.
        AbsClass tempClass = new AbsClass() {
            void absMethod() {  // implements absMethod (Note method should explicitly declare public, if implementing interface.)
                System.out.println("Created an instance of temporary class that extends AbsClass");
            }
        };  // assignment must end with semicolon
        
        // Use that instance.
        tempClass.absMethod();  // Created an instance of temporary class that implements abstract method
        
        // Anonymous class can't both extend a class and implement an interface. (only one or the other)
    }
    
    public InnerClass getInnerClass() {  // Outer class's instance method to return new instance of inner class.
        return new InnerClass();  // this inner class is associated with this outer class.
    }
    
    // Inner member (non-static) class:
    // Unlike top-level type, which can only be public or default package access,
    // it can be any access (public, protected, default package, private).
    // It can extend/implement classes/interfaces. Can be abstract or final.
    // It can access outer class members, even private.
    public class InnerClass {  // InnerClass can be referred by either Ch7NestedClasses.InnerClass or just InnerClass (in scope of outer class).
        private String str = "Inner String";
        private static String staticStr = "Static Inner String";
        
        // Can have same field name as outer class, although bad practice.
        public void printStr() {
            System.out.println(str);  // this inner instance
            System.out.println(this.str);  // this inner instance
            System.out.println(Ch7NestedClasses.this.str);  // "this" required to get this instance of its outer Ch7NestedClasses.
        }
    }
    
    // Static nested class:
    // It can be any access (public, protected, default package, private). It can extend/implement classes/interfaces. Can be abstract or final.
    // It cannot access instance fields of outer class, since the class is static.
    public static class StaticNestedClass {
        private String instanceVarOfStaticNestedClass = "instanceVarOfStaticNestedClass";
        private static String staticVarOfStaticNestedClass = "staticVarOfStaticNestedClass";
    }
    
    // Local class is nested class defined within a method, constructor, or initializer.
    // It is local scope, just like local variables. (Thus it does not have any access modifiers.)
    // It can extend/implement classes/interfaces. Can be abstract or final.
    // It can access outer class members. (Instance fields as well if local class is defined in instance method.)
    // It can access final & effectively final local variables.
    public void methodWithLocalClass() {
        int a = 1;
        int b = 2;
        class LocalClass {
            int multiply() {System.out.println(str); return a * b;}  // prints Outer String, then 2
        }
        System.out.println(new LocalClass().multiply());
    }
}

abstract class AbsClass {
    abstract void absMethod();
}
