package ocpGuideBook.ch9;

import java.util.Arrays;
import java.util.List;

public class Ch9ListSet {
    
    public static void main(String[] args) {
        
        // List is ordered collection that can contain duplicate entries.
        // ArrayList does instant lookup by indexing, good for frequent read. Write may take time.
        // LinkedList implements both List and Deque. Constant read/write on front & end, Good for Deque operations.
        // LinkedList can do indexing, but it just takes time.
            
        
        // Arrays.asList(varargs)  List.of(varargs)  List.copyOf(collection)
        String[] array = new String[] {"a", "b", "c"};
        
        List<String> asList = Arrays.asList(array);  // Arrays.asList(varargs) returns list backed by array. Cannot add/remove but can replace elements.
        List<String> of = List.of(array);  // List.of(varargs) returns immutable list.
        List<String> copyOf = List.copyOf(asList);  // List.copyOf(varargs) takes list and returns immutable list.
        
        // List returned by Arrays.asList is a view of the passed array. Changes on one affects the other.
        array[0] = "z";
        System.out.println(asList);  // [z, b, c]  Updating array updated list.
        System.out.println(of);  // [a, b, c]
        System.out.println(copyOf);  // [a, b, c]
        
        asList.set(0, "y");
        System.out.println(asList);  // [y, b, c]  Updating list updated array.
        System.out.println(of);  // [a, b, c]
        System.out.println(copyOf);  // [a, b, c]
        
        
        // listObj.toArray() returns copy as an array. It returns array of object unless specifying actual class.
        Object[] objectArray = copyOf.toArray();  // creates array of objects, which isn't really useful.
        String[] stringArray = copyOf.toArray(new String[0]);  // Java will create proper size if passing 0.
        
        
        // Common methods of List interface:
        // add(E), add(idx, E), get(idx), set(idx, E), remove(idx), replaceAll(UnaryOperator), sort(Comparator)
        var numbers = Arrays.asList(1, 2, 3);
        numbers.replaceAll(x -> x * 2);
        System.out.println(numbers);  // [2, 4, 6]
        
        
        // Set is a group of unique items. Set is unordered (except sorted implementations like TreeSet).
        
        // HashSet uses hash table (using hashCode() method) to efficiently store and retrieve objects. (keys are hash, values are object)
        // Thus adding and checking if contains are constant time.
        
        // TreeSet stores in sorted tree structure.
        // It's always sorted (in respect to Comparable interface). But adding and checking if contains are slow.
        
        // Set also has of & copyOf methods + common collection methods such as: add(E), remove(Object)
        // Adding duplicate returns false, and does not add it.
    }
}
