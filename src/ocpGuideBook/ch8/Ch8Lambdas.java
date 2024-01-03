package ocpGuideBook.ch8;

import java.util.ArrayList;
import java.util.List;

public class Ch8Lambdas {
    
    public static void main(String[] args) {
        
        // Lambda: params -> return value or statements
        // x -> true
        // x -> return true;
        // () -> true  // for method with no parameter
        
        // Can specify type of param. When specifying type or having multiple params, must provide ().
        // (String s) -> System.out.println(s)
        // (x, y) -> System.out.println("Yes")
        // (ClassX x, ClassY y) -> x.method
        
        // Can provide body for result {}. Must provide body when having multiple statements.
        // x -> {System.out.println("Yes"); return true;}  // When using body, must specify return if returning.  
        
        
        // Assigning lambda to a variable or Passing lambda as a parameter to a method:
        // Lambda looks at context. Context means where and how lambda is used. (For this reason, you can't assign lambda to var.)
        
        // Functional interface is an interface with exactly one abstract method.
        // Lambda has a special relationship with functional interface.
        
        Animal Bird = new Animal("Bird", true, false);
        Animal Fish = new Animal("Fish", false, true);
        List<Animal> animals = new ArrayList<>();
        animals.add(Bird);
        animals.add(Fish);
        
        // Create an object of (anonymous) class that implements Tester (functional interface).
        // Implement the only one abstract method of Tester by lambda provided.
        // Java says the abstract method's parameter is Animal, so "a" must be Animal. The method returns boolean, so a.isCanFly() must be boolean.
        Tester testerObj = a -> a.isCanFly();  // an object of a class that implements Tester

        for (Animal a : animals) {  // testerObj implemented with lambda
            System.out.println(testerObj.test(a));  // true for bird, false for fish
        }
        
        testerObj = a -> a.isCanSwim();
        for (Animal a : animals) {  // testerObj implemented with lambda
            System.out.println(testerObj.test(a));  // false for bird, true for fish
        }
        
        
        // Lambda vs Method Reference:
        // Method reference is even shorter form of Lambda.
        // Method reference omits the parameter, and let Java to figure out depending on context.
        
        // Method reference calling static method with given parameter.
        interface Converter {long round(double num);}  // Abstract method takes double and returns long.
        Converter converter = x -> Math.round(x);  // Java says x must be double and what returns must be long.
        Converter converter2 = Math::round;  // Java says there is version of Math.round that takes double & returns long. So this must be it. 
        
        System.out.println(converter.round(1.1));  // 1
        System.out.println(converter2.round(1.1));  // 1
        
        // Method reference calling instance method of an particular object (The object here is not the parameter).
        // TakesStringReturnsBoolean tsrb = stringParam -> someStringObj.startsWith(stringParam);
        // TakesStringReturnsBoolean tsrb = someStringObj::startsWith;  // It says run someStringObj.startsWith with param
        // tsrb.method(s);
        
        // Method reference calling instance method on parameter that is an object. (Note that it specifies class name of the object.)
        // TakesStringReturnsBoolean tsrb = stringObjParam -> stringObjParam.isEmpty();
        // TakesStringReturnsBoolean tsrb = String::isEmpty;  // It says what is being passed is String object.
        // tsrb.method(s);
        
        // Method reference calling constructor.
        // TakesNoneReturnsString tnrs = () -> new String();
        // TakesNoneReturnsString tnrs = String::new;
        // String newString = tnrs.method();
    }
}

@FunctionalInterface  // @FunctionalInterface annotation checks if interface contains exactly one abstract method.
interface Tester {
    boolean test(Animal a);
}
// Note that having Object methods (toString, equals(), hashCode()) in the interface does not count toward exactly one abstract method.
// Also you cannot create an interface that has an incompatible method with object methods. (i.e. having non covariant return type.)

class Animal {
    private String name;
    private boolean canFly;
    private boolean canSwim;
    public Animal(String name, boolean canFly, boolean canSwim) {
        this.name = name;
        this.canFly = canFly;
        this.canSwim = canSwim;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isCanFly() {
        return canFly;
    }
    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }
    public boolean isCanSwim() {
        return canSwim;
    }
    public void setCanSwim(boolean canSwim) {
        this.canSwim = canSwim;
    }
}
