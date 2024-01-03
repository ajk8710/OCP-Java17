package ocpGuideBook.ch8;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Ch8BuiltInFunctionalInterfaces {
    
    public static void main(String[] args) {
        
        // Benefit of lambda: Lambda can be passed to a method expecting an instance of a functional interface.
        // Having a single method that takes multiple variant implementations of (functional) an interface:
        // Param can be passed to the method as a lambda (or method reference) that is like an object of anonymous class that implements interface.
        // This removes the needs of creating multiple named classes that implements the interface.
        
        // java.util.function package has multiple built-in functional interfaces, with one abstract method & default methods.
        
        // Supplier returns something, upon get() method.
        // public interface Supplier<T> { T get(); }  get() takes no param and returns T.
        
        // An object of a class that implement Supplier. get() implemented to return LocalDate.
        Supplier<LocalDate> localDateSupplier1 = LocalDate::now;  // public static LocalDate now()
        Supplier<LocalDate> localDateSupplier2 = () -> LocalDate.now();
        
        LocalDate d1 = localDateSupplier1.get();
        LocalDate d2 = localDateSupplier2.get();
        
        // get() implemented to return StringBuilder.
        Supplier<StringBuilder> s1 = StringBuilder::new;
        Supplier<StringBuilder> s2 = () -> new StringBuilder();
        
        StringBuilder sb1 = s1.get();  // returns empty StringBuilder
        StringBuilder sb2 = s2.get();
        
        // In practice, intermediate variables such as s1 and s2 are normally not needed.
        // Instead, lambda expression or method reference is passed as parameter to a method that expects functional interface.
        
        
        // Consumer takes something, upon accept(T) method.
        // public interface Consumer<T> { void accept(T t); }  accept(T) returns void.
        
        Consumer<String> c1 = System.out::println;
        Consumer<String> c2 = s -> System.out.println(s);
        
        c1.accept("Hello");  // accept(String) takes "Hello" and runs System.out.println("Hello").
        c2.accept("Hello");
        
        // BiConsumer takes two parameters.
        // public interface BiConsumer<T, U> { void accept(T t, U u); }
        HashMap<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> b1 = map::put;  // accept method implemented to take String & Integer, then do map.put() with them.
        BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
        // Note that { map.put(k,v) }: Lambda body are allowed to refer to variables (map) from surrounding codes,
        // such as instance variables & static variables (always),
        // local variables, and method parameters (if final or effectively final)
        // (Effectively final: The variable is never reassigned after its initial assignment.)
        
        // Unlike lambda body, lambda variables (on left side) are considered independent from outside of lambda expression and can be reused.
        // x( (var x) -> {}, (var x, var y) -> true )  // This is ok.
        // x -> {int x;}  // This is not ok. Re-declaration not allowed in body.
        
        b1.accept("Chicken", 1);
        b2.accept("Pizza", 2);
        System.out.println(map);  // {Pizza=2, Chicken=1}
        
        
        // Predicate & BiPredicate are used for filtering or matching. test(T) takes params & returns boolean (not Boolean).
        // public interface Predicate<T> { boolean test(T t); }
        // public interface BiPredicate<T, U> { boolean test(T t, U u); }
        
        Predicate<String> p1 = String::isEmpty;
        Predicate<String> p2 = s -> s.isEmpty();
        
        System.out.println(p1.test(""));  // true
        System.out.println(p2.test(""));  // true
        
        BiPredicate<String, String> bp1 = String::startsWith;  // takes two strings & returns return value of function with params.
        BiPredicate<String, String> bp2 = (string, prefix) -> string.startsWith(prefix);
        
        System.out.println(bp1.test("chicken", "chick"));  // true
        System.out.println(bp2.test("chicken", "chick"));  // true
        
        
        // Function & BiFunction takes one or two parameters then turns into a potentially different value & returns it.
        // public interface Function<T, R> { R apply(T t); }
        // public interface BiFunction<T, U, R> { R apply(T t, U u); }
        
        Function<String, Integer> f1 = String::length;  // takes string object and returns return value of length function.
        Function<String, Integer> f2 = s -> s.length();
        
        System.out.println(f1.apply("STR"));  // 3
        System.out.println(f2.apply("STR"));  // 3
        
        BiFunction<String, String, String> bf1 = String::concat;
        BiFunction<String, String, String> bf2 = (string1, string2) -> string1.concat(string2);
        
        System.out.println(bf1.apply("Nice ", "Day"));  // Nice Day
        System.out.println(bf2.apply("Nice ", "Day"));  // Nice Day
        
        
        // UnaryOperator & BinaryOperator are special function that takes same type. (They extends Function & BiFunction.)
        // public interface UnaryOperator<T> extends Function<T, T> { }  // Note it only accepts one generic.
        // public interface BinaryOperator<T> extends BiFunction<T, T, T> {  // Same character generic forces types to be the same.
        
        UnaryOperator<String> uo1 = String::toUpperCase;  // takes string object and returns a string object.
        UnaryOperator<String> uo2 = s -> s.toUpperCase();
        
        System.out.println(uo1.apply("nice day"));  // NICE DAY
        System.out.println(uo1.apply("nice day"));  // NICE DAY
        
        BinaryOperator<String> bo1 = String::concat;  // takes two string object and returns a string object.
        BinaryOperator<String> bo2 = (string1, string2) -> string1.concat(string2);
        
        System.out.println(bo1.apply("Nice ", "Day"));
        System.out.println(bo2.apply("Nice ", "Day"));
        
        
        // Default convenience methods takes an Interface parameter and returns an Interface (result is combined interface).
        // Predicate: and(), or(), negate()
        // Consumer: andThen()
        // Function: andThen(), compose()
        
        Predicate<String> egg = s -> s.contains("egg");
        Predicate<String> brown = s -> s.contains("brown");
        
        Predicate<String> brownEggs = egg.and(brown);  // return true if test on egg is true & test on brown is true
        Predicate<String> otherEggs = egg.and(brown.negate());  // return true if test on egg is true & test on brown is false
        Predicate<String> anyEggs = egg.or(brown);
        
        System.out.println(brownEggs.test("brown eggs"));  // true
        System.out.println(otherEggs.test("white eggs"));  // true
        System.out.println(anyEggs.test("eggs"));  // true
        
        
        // Consumer.andThen() runs in sequence (takes given param independently, as consumer has no return value.)
        Consumer<String> cons1 = s -> System.out.println("1: " + s);
        Consumer<String> cons2 = s -> System.out.println("2: " + s);
        Consumer<String> cons3 = cons1.andThen(cons2);  // takes String param, runs cons1 then cons2
        cons3.accept("Chicken");  // 1: Chicken then 2: Chicken
        
        
        // Function.compose() uses returned value
        Function<Integer, Integer> before = x -> x + 1;
        Function<Integer, Integer> after = x -> x * 2;
        Function<Integer, Integer> result = after.compose(before);  // before runs then returns Integer, which is used as param in after.
        System.out.println(result.apply(3));  // 3 + 1 then * 2 = 8
        
        // Function.andThen() runs in sequence, using returned value.
        Function<Integer, Integer> result2 = after.andThen(before);  // after runs and then before runs with returned value.
        System.out.println(result2.apply(3));  // 3 * 2 then + 1 = 7
        
        
        // There are functional interfaces for primitives: boolean, double, int, long, that takes and returns primitives.
        // (such as IntSupplier, IntConsumer, IntPredicate, IntFuntion (returns generic), IntUnaryOperator, IntBinaryOperator)
        
        
        // Lambda infers the types from context, which means var is ok for followings.
        Predicate<String> pred1 = x -> true;
        Predicate<String> pred2 = (var x) -> true;  // Compiler knows it's String
        Predicate<String> pred3 = (String x) -> true;
        
        consume((var x) -> System.out.print(x), 123);  // var is Integer: consume(Consumer<Integer>, int)
        consume((final var x) -> System.out.print(x), 123);  // modifier can come before parameters, although uncommon.
        consume((@Deprecated var x) -> System.out.print(x), 123);
        
        // If specifying type, all variables need to be specified. Cannot mix var with other types.
        // (Integer x, y) -> true;  // Compile error.
        // (var x, y) -> true;  // Compile error.
        // (String x, var y, Integer z) -> true;  // Compile error.
        
        // (a, b) -> { int a = 0; return 5; }  // Compile error. Redeclaration of "a" not allowed
        
    }
    
    public static void consume(Consumer<Integer> c, int num) {
        c.accept(num);
    }
}
