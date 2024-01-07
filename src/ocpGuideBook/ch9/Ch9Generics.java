package ocpGuideBook.ch9;

import java.util.ArrayList;
import java.util.LinkedList;
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
        
        
        var objectList = new LinkedList<>();  // with var and not declaring type inside diamond, this is LinkedList<Object>
        
        // Example using generic in method.
        Box<String> box1 = method2("Hi");
        // Box<String> box2 = method2(1);  // Compile error. Cannot convert from Box<Integer> to Box<String>.
        // Ch9Generics.<String>method2("Hi");  // funky syntax specifying type.
        
        // Generic can be used with interface and record.
        
        
        // Bounding generic types using wildcard "?"
        // Wildcards cannot be at right side of assignment. Can only be at left side or method parameter.
        // Wildcards support both upper and lower bounds. Generic type parameters only support upper bounds (when passing as parameter, adding item to list, etc).
        List<?> list1 = new ArrayList<String>();  // unbound
        List<? extends Exception> list2 = new ArrayList<RuntimeException>();  // upper bound (Exception is upper bound). Whatever extends Exception (or Exception itself).
        List<? super Exception> list3 = new ArrayList<Object>();  // lower bound (Exception is lower bound). Whatever super class of Exception (or Exception itself).
        // (Use extends for both class and interface)
        
        // List<T> list4 = new ArrayList<String>();  // Compile error. T cannot be here. Wildcard can be.
        
        // Compile error when attempting to add item to list with an unbounded or upper-bounded wildcard.
        // list1.add("One");  // Cannot add anything except null because type is wildcard: List<?>.
        List<String> list5 = new ArrayList<>();
        list5.add("One");
        list5.add("Two");
        usingT(list5);  // One Two
        usingWild(list5);  // One Two
        
        // ArrayList<Number> list6 = new ArrayList<Integer>();  // Compile error. Generic type must match here. (Generic forces exact type.)
        List<? extends Number> list6 = new ArrayList<Integer>();  // Can't add anything to list b/c it could be anything extending Number. Wildcard is mostly used as method parameter.
        
        // Can add on wildcard with super. (lower bound)
        // But it bahaves funky.
        List<? super String> list7 = new ArrayList<Object>();
        list7.add("One");
        // list7.add(Integer.valueOf(1));  // Can only add String or its subclass (because Java says subclass can be String)
        // list7.add(new StringBuilder("One"));
        // list7.add(new Object());
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
    
    // T vs ?
    public static <T> void usingT(List<T> list) {
        for (T t: list) {System.out.println(t);}
    }
    
    public static void usingWild(List<?> list) {
        for (Object o: list) {System.out.println(o);}
    }
    
    public static <T extends Number> void copyWithT(List<T> dest, List<T> src) {}  // Safe in that both are list of type T
    public static void copyWithWild(List<? extends Number> dest, List<? extends Number> src) {}// Two lists can hold different types.

}

// Box can contain any specific object T.
// Ex: Box of Animal can contain Dog or Cat. Box of Dog can contain Dog.
class Box<T> {  // Generic type parameter can be named anything (as long as valid identifier), but convention is a single upper-case letter.
    private T contents;
    public T getContents() {
        return contents;
    }
    public Box() {}
    public Box(T contents) {
        this.contents = contents;
    }
    
    public Box<T> method1(T t) {  // T from class
        return new Box<>();
    }
    
    // Warning: type parameter T is hiding the type T. (Use different character.)
    public <T> Box<T> method2(T t) {  // Re-defines T for this method, which can be different from T of class.
        return new Box<>();
    }
}
