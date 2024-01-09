package ocpGuideBook.cha10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ch10Stream {
    
    public static void main(String[] args) {
        
        // Creating finite streams
        Stream<String> empty = Stream.empty();  // emptry stream
        Stream<Integer> fromArray = Stream.of(1, 2, 3);  // Stream.of(varargs)
        
        var list = List.of("a", "b", "c");  // list.stream() creates stream from list.
        Stream<String> streamFromList = list.stream();
        Stream<String> parallelStream = list.parallelStream();  // parallelStream() creates stream that uses threads.
        
        
        // Creating infinite streams. (Stream doesn't run unless you call terminal function on it.)
        Stream<Double> randoms = Stream.generate(Math::random);  // Stream.generate(supplier)
        Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);  // Stream.iterate(startingSeed, unaryOperator)
        Stream<Integer> oddNumsUpto100 = Stream.iterate(1, n -> n < 100, n -> n + 2);  // Stream.iterate(startingSeed, predicate, unaryOperator)
        
        // Call terminal functions.
        // randoms.forEach(e -> System.out.println(e));  // prints random numbers infinitely until terminate
        // oddNumbers.forEach(e -> System.out.println(e));  // prints odd numbers infinitely until terminate
        // oddNumsUpto100.forEach(e -> System.out.println(e));  // prints odd numbers upto 100 (Until predicate returns false)
        
        
        // Common terminal operations (Terminal operation can be performed without intermediate operations)
        
        // count() returns long
        System.out.println(streamFromList.count());  // count() counts elements of a stream.
        // System.out.println(oddNumbers.count());  // oddNumbers are forever generated, never gets to count() call.
        
        // public Optional<T> min(Comparator<? super T> comparator) & max(comparator) returns optional
        Stream<String> s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());  // When a terminal operation is called on a stream, the instance gets consumed and closed.
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        Optional<String> max = s.max((s1, s2) -> s1.length() - s2.length());
        
        min.ifPresent(System.out::println);  // Pizza
        max.ifPresent(System.out::println);  // Cheese Burger
        
        // findFirst(), findAny() returns empty optional or optional with first element. findAny() is not guaranteed to be first element (especially in multi-threading).
        // It terminates on infinite stream (like as soon it sees first element, it's done its job).
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        s.findAny().ifPresent(a -> System.out.println(a));  // Pizza
        
        // anyMatch(predicate), allMatch(predicate), noneMatch(predicate) returns boolean
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        System.out.println(s.anyMatch(each -> each.charAt(0) == 'C'));  // true
        s = Stream.of("Cheese Pizza", "Chicken", "Cheese Burger");
        System.out.println(s.allMatch(each -> each.contains("C")));  // true (false as soon as it see's something un-matching, even in infinite stream)
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        System.out.println(s.noneMatch(each -> each.charAt(0) == 'A'));  // true (false as soon as it see's something matching, even in infinite stream)
        
        // forEach(consumer)
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        s.forEach(System.out::print);  // PizzaChickenCheese Burger
        System.out.println();
        
        // reduce process each element and returns single value.
        // reduce(initial, binaryOperator) returns same type as initial.
        s = Stream.of("a", "b", "c", "d", "e");
        System.out.println(s.reduce("", (cur, nxt) -> cur + nxt));  // abcde
        
        // reduce(binaryOperator) returns Optional. Considers first value as initial. If no second value, just return first value.
        s = Stream.of("a", "b", "c", "d", "e");
        System.out.println(s.reduce((cur, nxt) -> cur + nxt).orElse(null));  // abcde
        
        // collect(collector) collects results as collection.
        Stream.of("c", "b", "a", "e", "d").sorted().collect(Collectors.toList()).forEach(System.out::print);  // abcde. collect is terminal operation, returns a list. Then Collection.forEach of list.
        System.out.println();
        
        
        // Common intermediate operations (returns a stream as result)
        
        // filter(predicate)
        s = Stream.of("Pizza", "Chicken", "Cheese");
        s.filter(e -> e.charAt(0) == 'C').forEach(e -> System.out.print(e + " "));  // Chicken Cheese
        System.out.println();
        
        // distinct() removes duplicate by equals()
        s = Stream.of("Pizza", "Cheese", "Cheese");
        s.distinct().forEach(e -> System.out.print(e + " "));  // Pizza Cheese 
        System.out.println();
        
        // limit(maxSize), skip(long)
        Stream<Integer> intS = Stream.iterate(1, n -> n + 1);  // infinite stream generates every integer from 1
        intS.skip(5).limit(3).forEach(System.out::print);  // 678. limit stops after taking 3 integers. Each time it got a integer it passed to for each.
        System.out.println();
        
        // map(function) maps each element to another (converts each from a type to maybe different type)
        s = Stream.of("Pizza", "Chicken", "Cheese");
        s.map(e -> e.length()).forEach(e -> System.out.print(e + " "));  // 5 7 6 
        System.out.println();
        
        // flatMap(Stream<List<String>>) takes multiple streams and combines into one. (removes unnecessary layer)
        // (flattens nested streams into a single level and also removes empty streams.)
        // Stream.concat(s1, s2) combines streams. It only takes two parameters.
        
        // sorted() & sorted(comparator)
        s = Stream.of("Pizza", "Chicken", "Cheese");
        s.sorted().forEach(e -> System.out.print(e + " "));  // Cheese Chicken Pizza 
        System.out.println();
        
        s = Stream.of("Pizza", "Chicken", "Cheese");
        s.sorted(Comparator.reverseOrder()).forEach(e -> System.out.print(e + " "));  // Pizza Chicken Cheese 
        System.out.println();
        
        // peek(consumer) mostly used for debugging because it does not change the original stream.
        // It is an intermediate version of forEach() that returns the original stream.
        s = Stream.of("Pizza", "Chicken", "Cheese Burger");
        s.filter(e -> true).peek(System.out::print).count();  // PizzaChickenCheese Burger
        System.out.println();
        
        
        // Streams focus on what you want to accomplish, rather than how to do so. (Without streams, ex. regular for-loops, focuses on how rather than what.)
        // (Streams and functional programming, how to do is defined in function. You call them.)
        var streamEx = List.of("wwww", "xxxx", "yy", "zzzz");
        
        streamEx.stream()
        .filter(n -> n.length() == 4)  // intermediate.  Every time it sees length-4 item, pass to sorted.
        .sorted()  // intermediate.  Wait until it receives all items to sort. Then every time 1 item is sorted, pass to limit.
        .limit(2)  // intermediate.  Every time it gets an item, pass it to forEach. Stops receiving after receiving 2 item.
        .forEach(System.out::println);  // terminal.  Prints as it get an item.
        
        
        // Streams are lazy evaluated.
        var foods = new ArrayList<String>();
        foods.add("Pizza");
        foods.add("Chicken");
        var stream = foods.stream();  // Stream isn't created until you call a function on it (lazy evaluation).
        foods.add("Cheese Burger");
        System.out.println(stream.count());  // 3. Stream pipeline runs here, look for the data, arrayList has 3 elements.
        
        
    }
}
