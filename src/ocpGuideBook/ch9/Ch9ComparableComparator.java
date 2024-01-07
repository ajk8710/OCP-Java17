package ocpGuideBook.ch9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ch9ComparableComparator {
    
    public static void main(String[] args) {
        
        // public interface Comparable<T> { int compareTo(T o); }
        
        // Any class can be Comparable by implementing Comparable interface. (implementing compareTo method)
        
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog(1));
        dogs.add(new Dog(0));
        
        // java.util.Collections is utility class.
        // public static <T extends Comparable<? super T>> void sort(List<T> list)  // T is what extends Comparable<? super T>
        Collections.sort(dogs);  // sorts original
        System.out.println(dogs);  // [0, 1]
        Collections.sort(dogs, Collections.reverseOrder());  // reverseOrder() returns a comparator that imposes the reverse of the natural ordering in Comparable.
        System.out.println(dogs);  // [1, 0]
        
        // Also can use sort method of list, without using Collection. (There is no sort method in set and map.)
        dogs.sort((dog1, dog2) -> dog1.id - dog2.id);  // list.sort(Comparator)
        dogs.sort((dog1, dog2) -> dog1.compareTo(dog2));  // same as above
        System.out.println(dogs);  // [0, 1]
        
        
        dogs = new ArrayList<>();
        dogs.add(new Dog(0, 2));
        dogs.add(new Dog(1, 0));
        dogs.add(new Dog(2, 1));
        
        // Use Comparator to sort object that does not implement Comparable, or to sort differently from how it's already implemented.
        // Comparator interface has two abstract method compare(T, T), equals(Obj).
        // Note that equals do not need to be implemented because Object class has it, and it does not count toward only one abstract method for functional interface.
        Comparator<Dog> byWeight = new Comparator<>() {  // An object of anonymous class that implements Comparator.
            @Override
            public int compare(Dog dog1, Dog dog2) {  // Note it's compare(obj, obj). Different from compareTo(obj).
                return dog1.weight - dog2.weight;
            }
        };  // assignment needs semicolon
        
        // Collections.sort(List, Comparator)
        Collections.sort(dogs, byWeight);
        System.out.println(dogs);  // [1, 2, 0]
        
        // Implementing Comparator by lambda (implements abstract method)
        // Comparator is functional interface with only one abstract method.
        // Comparator<T> & int compare(T, T). So Java knows I'm talking about Dog, and it should return int.
        Comparator<Dog> byWeight2 = (dog1, dog2) -> dog1.weight - dog2.weight;  // dog2 - dog1 if want to sort reverse
        
        dogs = new ArrayList<>();
        dogs.add(new Dog(0, 2));
        dogs.add(new Dog(1, 0));
        dogs.add(new Dog(2, 1));
        Collections.sort(dogs, byWeight);
        System.out.println(dogs);  // [1, 2, 0]
        
        Collections.reverse(dogs);  // reverse reverses the list
        System.out.println(dogs);  // [0, 2, 1]
        Collections.reverse(dogs);  // reverse reverses the list
        System.out.println(dogs);  // [1, 2, 0]
        Collections.sort(dogs, byWeight.reversed());  // reversed() returns a comparator that imposes the reverse ordering of this comparator.
        System.out.println(dogs);  // [0, 2, 1]
        
        dogs.sort(byWeight2);  // using list.sort(comparator)
        System.out.println(dogs);  // [1, 2, 0]
    }
}

class Dog implements Comparable<Dog> {  // can compare to random class like Comparable<String>, but then the class can't be used in Collections.sort.
    int id;
    int weight;
    Dog(int id) {this.id = id;}
    Dog(int id, int weight) {this.id = id; this.weight = weight;}
    
    // To properly implement compareTo, return 0 if this dog and the other dog are considered equal,
    // negative number if this is smaller, positive number if this is larger.
    // Collections.sort uses compareTo method to sort.
    public int compareTo(Dog other) {
        if (other == null) {throw new IllegalArgumentException("Null Dog");}  // In reality, do null checks such as this.
        return this.id - other.id;
    }
    
    @Override
    public String toString() {return Integer.toString(id);}  // overriding Object.toString
    
    // Natural ordering means compareTo & equals are consistent (If compareTo returns 0, equals returns true. Otherwise false.)
    // Ordering and equality do not need be same (You may want to sort on name, but equality on id). Or you could use comparator to compare on something else.
    @Override
    public boolean equals(Object obj) {  // not (Dog other), because then it's not overriding.
        if (!(obj instanceof Dog)) return false;
        Dog other = (Dog) obj;
        return this.id == other.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
}
