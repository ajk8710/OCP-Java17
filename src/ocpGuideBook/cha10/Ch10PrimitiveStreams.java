package ocpGuideBook.cha10;

import java.util.IntSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ch10PrimitiveStreams {
    
    public static void main(String[] args) {
        
        // Instead of using Stream with wrapper for type parameter like Stream<Integer>,
        // Primitive streams (IntStream, LongStream, DoubleStream) has special methods to work with numeric data, like sum() and average().
        // (IntStreams can be created with int, short, byte, char values. DoubleStream with double & float values.)
        
        DoubleStream ds = DoubleStream.empty(); // empty stream
        ds = DoubleStream.of(1.0, 1.1, 1.2f);  // DoubleStream of(double...)
        var random = DoubleStream.generate(Math::random);  // Infinite stream. Stream doesn't run unless you call function on it (lazy evaluated).
        // random.limit(3);  // Below gives runtime error if I use stream here. Stream has already been operated upon or closed.
        random.limit(3).forEach(System.out::println);  // prints 3 random doubles
        
        IntStream testing = IntStream.range(1, 6);  // finite stream
        testing.limit(1);
        // testing.limit(2);  // Runtime error. Stream has already been operated upon or closed.
        
        
        // IntStream, LongStream.range(inclusive, exclusive)
        IntStream is = IntStream.range(1, 6);
        is.forEach(System.out::print);  // 12345
        System.out.println();
        
        // IntStream, LongStream.rangeClosed(inclusive, inclusive)
        is = IntStream.rangeClosed(1, 6);
        is.forEach(System.out::print);  // 123456
        System.out.println();
        
        // min() and max() returns OptionalInt OptionalLong, or OptionalDouble
        System.out.println(IntStream.range(1, 6).min().getAsInt());  // 1
        System.out.println(IntStream.range(1, 6).max().getAsInt());  // 5
        
        // average() returns OptionalDouble.
        System.out.println(IntStream.range(1, 6).average());  // OptionalDouble[3.0]
        
        // sum() returns int, long, or double. (Does not return Optional because it can return 0, meaning empty optional)
        System.out.println(IntStream.range(1, 6).sum());  // 15
        
        // summaryStatistics() returns numerical statistics
        IntSummaryStatistics iStats = IntStream.range(1, 6).summaryStatistics();
        System.out.println(iStats);  // IntSummaryStatistics{count=5, sum=15, min=1, average=3.000000, max=5}
        
        // boxed() returns the stream with wrapper, Stream<T>. It is similar to mapToObj(x -> x).
        Stream<Integer> s = IntStream.range(1, 6).boxed();
        
        
        // There are number of methods to map one stream from to another. Object stream to primitive stream, or any stream to any (as long as compatible).
        Stream<String> objStream = Stream.of("Salmon", "Tuna");
        IntStream intStream = objStream.mapToInt(str -> str.length());
        intStream.forEach(System.out::println);  // 6 4
        
    }
}
