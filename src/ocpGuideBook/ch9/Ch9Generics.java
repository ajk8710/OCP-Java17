package ocpGuideBook.ch9;

import java.util.ArrayList;
import java.util.List;

public class Ch9Generics {
    
    public static void main(String[] args) {
        
        // Without generic, I can add anything. But can get runtime error. Generic prevents this with compile error, not allowing to insert non-variant types.
        List list = new ArrayList();  // List of objects
        list.add("a");
        list.add(new StringBuilder("a"));
        System.out.println(((String) list.get(0)).length());  // ok
        // System.out.println(((String) list.get(1)).length());  // ClassCastException StringBuilder to String
        // Also note that generic would remove burden of caller having to cast Object to specific class.
        
        // Enforcing the type is job of compiler, then compiler replaces all generic parameter T (or whatever) and to Object and adds casts when compiling.
        // This process of removing generic syntax is called as type erasure.
        // Due to type erasure, you can't overload by just changing generic, such as method(List<Object>) & method(List<String>)
        // Also you cannot override parent's method with a different generic, such as method(List<Object>) & method(List<String>)
        // Remember overriding method's return type must be covariant, while its generic must be the same.
        
        
        // Example using generic in method.
        Box<String> box1 = method2("Hi");
        // Box<String> box2 = method2(1);  // Compile error. Cannot convert from Box<Integer> to Box<String>.
        // Ch9Generics.<String>method2("Hi");  // funky syntax specifying type.
        
        // Generic can be used with interface and record.
        
        
        // Bounding generic types using wildcard "?"
        List<?> list1 = new ArrayList<String>();  // unbound
        List<? extends Exception> list2 = new ArrayList<RuntimeException>();  // upper bound (Exception is upper bound). Whatever extends Exception.
        List<? super Exception> list3 = new ArrayList<Object>();  // lower bound (Exception is lower bound). Whatever super class of Exception.
    }
    
    // Both instance method and static method can contain generic type.
    // Unless a method is obtaining the generic type from the class/interface,
    // it is specified immediately before the return type of the method.
    public static <T> void method1(T t) {
        System.out.println(t);
    }
    
    public static <T> Box<T> method2(T t) {
        return new Box<>();
    }
}

// Box can contain any specific object T.
// ex: Box of Animal can contain Dog or Cat. Box of Dog can contain Dog.
class Box<T> {  // Generic type parameter can be named anything (as long as valid identifier), but convention is a single upper-case letter.
    private T contents;
    public T getContents() {
        return contents;
    }
    public void Box(T contents) {
        this.contents = contents;
    }
    
    public Box<T> method1(T t) {  // T from class
        return new Box<>();
    }
    
    // Warning: type parameter T is hiding the type T
    public <T> Box<T> method2(T t) {  // Redefines T for this method. Can be different from T of class.
        return new Box<>();
    }
}
