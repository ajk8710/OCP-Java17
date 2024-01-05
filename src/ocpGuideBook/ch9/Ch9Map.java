package ocpGuideBook.ch9;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Ch9Map {
    
    public static void main(String[] args) {
        
        // Map is a group of uniqueKey-value pairs.
        // HashMap is unordered. TreeMap is always sorted on key.
        // For TreeMap, key must implement Comparable interface or gets runtime exception when inserting.
        // Or use constructor with comparator: TreeMap(Comparator i.e. lambda expression).
        
        // There is Map.of method but bad practice, leading confusion.
        var pairs = Map.of("key1", "value1", "key2", "value2");  // var here is Map<String, String>, exact class returned by Map.of.
        // Better way
        pairs = Map.ofEntries(Map.entry("key1", "value1"), Map.entry("key2", "value2"));
        
        // Common methods of Map interface:
        // put(K, V), putIfAbsent(K, V): puts if key is absent or value is null
        // remove(K), replace(K, V): replace if key exists & returns original or null (Does not add new value unlike put)
        // replaceAll(BiFunction(K, V, V)): applies bi-function to all
        // keySet(), values(): Note it's keySet, not keys.
        // get(K), getOrDefault(K, DefaultV): returns value from key or default if key is absent
        // containsKey(K), containsValue(V)
        // isEmpty(), size(), clear()
        // entrySet(), forEach(BiConsumer(K, V))
        
        Map<String, String> map = new HashMap<>();
        map.put("K1", "V1");
        map.put("K2", "V2");
        map.put("K3", "V3");
        
        
        // Iterating Map:
        
        // forEach(BiConsumer(K, V))
        map.forEach((k, v) -> System.out.print(k + " " + v + " "));  // K1 V1 K2 V2 K3 V3
        System.out.println();
        
        map.values().forEach(System.out::println);  // V1, V2, then V3 (collection.forEach(consumer))
        
        // Java has a static interface inside Map called Entry. It provides entrySet() method to get a set of key value pairs.
        // map.entrySet(); returns a set of mapEntry. Then run forEach method on set, which is collection.forEach(consumer)
        map.entrySet().forEach(e -> System.out.print(e.getKey() + " " + e.getValue() + " "));  // K1 V1 K2 V2 K3 V3
        System.out.println();
        
        // Using traditional for each
        for (Map.Entry<String, String> e : map.entrySet()) {
            System.out.print(e.getKey() + " " + e.getValue() + " ");  // K1 V1 K2 V2 K3 V3
        }
        System.out.println();
        
        // Using collection.iteratior()
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            // System.out.print(itr.next().getKey() + " " + itr.next().getValue() + " ");  // Won't work, next() gets next entry then moves the pointer
            Map.Entry<String, String> e = itr.next();
            System.out.print(e.getKey() + " " + e.getValue() + " ");  // K1 V1 K2 V2 K3 V3
        }
        System.out.println();
    }
}
