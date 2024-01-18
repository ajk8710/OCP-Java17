package ocpGuideBook.cha13;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class Ch13ConcurrentCollectionsStreams {
    
    public static void main(String[] args) {
        
        // Concurrency API includes interfaces and classes that help coordinate access to collections shared by multiple tasks.
        // It makes sure threads have same consistent view of a collection while adding and removing items.
        // ConcurrentHashMap, ConcurrentLinkedQueue, ConcurrentSkipListMap, ConcurrentSkipListSet (Skip means Sorted here.)
        // CopyOnWriteArrayList, CopyOnWriteArraySet, LinkedBlockingQueue (BlockingQueue has offer() and poll() that take a timeout.)
        
        Map<String,Integer> map = new ConcurrentHashMap<>();  // ConcurrentCollections inherits from corresponding Collections
        
        // CopyOnWrite is commonly used to ensure the iterator does not see modifications to the collection.
        // They use a lot of memory, because new collection is created as copy, thus mostly used in multi-threaaded environment where reads are far more than writes.
        List<Integer> nums = new CopyOnWriteArrayList<>(List.of(1, 2, 3));
        for (var n : nums) {
            System.out.print(n + " ");  // 1 2 3
            nums.add(n * 2);
        }
        System.out.println();
        System.out.println(nums);  // [1, 2, 3, 2, 4, 6]
        System.out.println("Size: " + nums.size());  // Size: 6
        
        
        List<Integer> numsNonConccurent = new ArrayList<>(List.of(1, 2, 3));
        /*
        for (var n : numsNonConccurent) {
            System.out.print(n + " ");  // 1
            numsNonConccurent.add(n * 2);  // second iteration throws ConcurrentModificationException
        }
        */
        
        System.out.println("-- numsConccurent-- ");
        Collection<Integer> numsConccurent = new ConcurrentLinkedQueue<>();
        numsConccurent.add(1);
        numsConccurent.add(2);
        numsConccurent.add(3);
        for (var n : numsConccurent) {
            System.out.print(n + " ");
            // numsConccurent.add(n * 2);  // does not throw exception, but infinite loop
        }
        
        System.out.println();
        System.out.println("-- setConccurent-- ");
        Set<Integer> setConccurent = new ConcurrentSkipListSet<>();
        setConccurent.add(1);
        setConccurent.add(2);
        setConccurent.add(3);
        for (var n : setConccurent) {
            System.out.print(n + " ");  // 
            setConccurent.add(5);  // does not throw exception, set do not add duplicates. (n * 2 would be infinite loop, adding new values)
        }
        System.out.println();
        System.out.println("setConccurent: " + setConccurent);
        
        
        // Collections has static method to return synchronized versions of existing non-concurrent collection, each taking corresponding collection as parameter.
        // synchronizedCollection(), synchronizedList(), synchronizedMap(), synchronizedNavigableMap(), synchronizedNavigableSet()
        // synchronizedSet(), synchronizedSortedMap(), synchronizedSortedSet()
        nums = Collections.synchronizedList(numsNonConccurent);
        
        
        /*
        Identifying Threading Problems:
        
        Liveness is the ability of an application to be able to execute in a timely manner.
        Liveness problem is application being unresponsive or is in some kind of "stuck" state: Deadlock, Starvation, Livelock.
        
        Deadlock occurs when two or more threads are blocked forever, each waiting on the other.
        Ex. Threads are permanently blocked, waiting on resources that each other is holding.
        
        Starvation occurs when a single thread is permanently blocked because of other threads constantly taking the resource that it's trying to access.
        
        Livelock is a form of starvation that is often a result of two threads trying to resolve a deadlock.
        One thread lets go of resource, than takes resource the other was holding. Same for the other tread. They are again in deadlock. Repeats.
        
        Race condition occurs when two tasks that should be completed sequentially are completed at the same time.
        Ex. Two thread read and increment a variable at same time.
        */
        
        
        // Parallel Streams
        Collection<Integer> collection = List.of(1, 2, 3, 4, 5);
        Stream<Integer> p1 = collection.stream().parallel();  // creates parallel stream from existing stream
        Stream<Integer> p2 = collection.parallelStream();  // creates parallel stream
        
        collection.parallelStream().map(i -> doSomeWork(i)).forEach(i -> System.out.print(i + " "));  // 1 5 3 4 2 done after ~2 seconds, not 2 * 5 seconds.
        System.out.println();
        
        // forEachOrdered() outputs in the order they are defined in the stream
        collection.parallelStream().map(i -> doSomeWork(i)).forEachOrdered(i -> System.out.print(i + " "));  // 1 2 3 4 5
        System.out.println();
        
        // Reduction in parallelStream may be different from stream.
        System.out.println(collection.parallelStream().findAny().get());  // can be any number depending on which thread finishes first.
        System.out.println(collection.stream().findAny().get());  // usually the first value but not guaranteed.
        
        // Operations that consider order, such as findFirst(), limit(), and skip() preserves order in parallel stream, but performance is reduced.
        
        // <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)
        // Serial stream adds one character at a time. Parallel stream create intermediate values and combine them.
        // Stream API preserves order, as long as accumulator and combiner produce the same result regardless of the order they are called in. (Ex. subtraction do not work)
        System.out.println(List.of('o', 'r', 'a', 'n', 'g', 'e').parallelStream().reduce("", (s1, c) -> s1 + c, (s2, s3) -> s2 + s3));  // orange
        System.out.println(List.of('o', 'r', 'a', 'n', 'g', 'e').stream().reduce("", (s1, c) -> s1 + c, (s2, s3) -> s2 + s3));  // orange
        
        // <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
        // Like reduce(), the accumulator and combiner operations must be able to process results in any order.
        Stream<String> stream = Stream.of("o", "r", "a", "n", "g", "e").parallel();
        SortedSet<String> set = stream.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);
        System.out.println(set);  // [a, e, g, n, o, r]
        
    }
    
    private static int doSomeWork(int input) {  // this method pretends doing some work, such as DB lookup, or reading file.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        return input;
    }
    
}
