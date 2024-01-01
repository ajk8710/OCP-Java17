package ocpGuideBook.ch7;

public class Ch7Records {
    
    public static void main(String[] args) {
        
        // Chicken koko = new Chicken();  // Does not have no-arg constructor by default.
        Chicken koko = new Chicken("Koko", 3);
        Chicken coco = new Chicken("Koko", 3);
        System.out.println(koko +"\n" + coco);  // Chicken[name=KOKO, numEggs=3]
        System.out.println(koko.hashCode() +"\n" + coco.hashCode());  // 72677147
        System.out.println(koko == coco);  // false (== always compares reference on Objects)
        System.out.println(koko.equals(coco));  // true
        
        // Record is immutable and every field is final.
        // Must create new copy to update.
        koko = new Chicken(koko.name(), koko.numEggs() + 2);  // Chicken[name=KOKO, numEggs=5]
        System.out.println(koko);
        
        System.out.println(new Chicken("ChiChi"));  // calling custom overloaded constructor. Chicken[name=CHICHI, numEggs=0]
        System.out.println(Chicken.farm);  // My Farm
    }
}

// Record is a special type of data-oriented class (encapsulated & immutable). (It's shorthand version of encapsulated POJO. JavaBean.)
// public (final) record RecordName(fields...) {}  Records are implicitly final (and cannot be extended). May not have any field, although not so useful.
// All fields are private final, and has getter methods. (Getters do not have get prefix.)
// It has an implicit constructor with all fields in same order.
// It has equals(), hashCode(), toString() methods: they are implemented to run on all fields.
record Chicken(String name, int numEggs) {
    // May declare optional constructors, methods, and constants.
    // If you put constructor with all fields in same order, compiler do not insert it. In this case, make sure to set all fields.
    // Or you can use compact constructor: special constructor for records, to validate and modify parameters.
    
    // Record cannot have any other instance fields other than declared in record.
    public static String farm = "Farm";  // static field. Having public static field may not be good encapsulation but allowed.
    // Records do not have instance initializers. All initialization for the fields happens in constructor.
    static {farm = "My Farm";}  // static initializer
    
    // Compact constructor: Constructor with no parameters & (). Can include parameter validations or update parameters.
    // Implicit constructor is called after and sets all fields. 
    public Chicken {
        if (numEggs < 0) {
            throw new IllegalArgumentException();  // parameter validation
        }
        name = name.toUpperCase();  // Updating parameter. name refers input parameters, not instance fields.
        // Compact constructor cannot set or modify instance fields.
    }  // Implicit constructor is called after compact constructor.
    
    public Chicken(String name) {  // custom overloaded constructor
        this(name, 0);  // must call implicit or another constructor. Constructor call must be at the first line.
        // Cannot modify instance fields at this point. They are final and already set.
    }
}

// My thought is that not having no-arg constructor may be ok because record cannot be extended anyways. Has no child to call super().

// Record can implement interface.
