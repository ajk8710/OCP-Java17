package ocpGuideBook.ch9;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Ch9Collection {
    
    public static void main(String[] args) {
        
        // Java Collections Framework: List, Queue, Set, Map. These are interfaces.
        // (List, Set, Queue is sub-interface of Collection. Collection is sub-interface of Iterable.)
        // (Map does not extends Collection interface. But it is considered part of Collections Framework.)
        // (Deque is sub-interface of Queue)
        
        // Common methods of Collection interface:
        // add(E), remove(Object), contains(Object), isEmpty(), size(), clear()
        // (remove and contains use equals() method to compare. (remove removes first occurrence.))
        
        // equals(anotherCollection) implementation differs in List and Set. Set doesn't care about order of elements.
        
        // removeIf(Predicate), forEach(Consumer)
        Collection<String> list = new ArrayList<>();  // Normally this would be List<String> list =
        list.add("St1");
        list.add("St2");
        list.add("");
        list.removeIf(s -> s.contains("1"));  // Apply predicate to each element, if predicate returns true, remove.
        list.removeIf(String::isEmpty);
        System.out.println(list);  // [St2]
        
        // removeIf modifies original list. forEach does not.
        list.forEach(s -> {s = s + "A"; System.out.println(s);});  // St2A
        System.out.println(list);  // [St2]
        
        
        // Iterating collection
        list = new ArrayList<>();
        list.add("St1");
        list.add("St2");
        
        // Using forEach with lambda or method reference
        list.forEach(s -> System.out.println(s));
        list.forEach(System.out::println);
        
        // Using for-each loop
        for (String s : list) {
            System.out.println(s);
        }
        
        // Using iterator
        Iterator<String> itr = list.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        
        // Vector, Hashtable, Stack are thread-safe List, Map, Queue structures that are rarely used anymore,
        // as there are better concurrent alternatives now.
    }
}
