package ocpGuideBook.ch7;

public class Ch7Enums {
    public static void main(String[] args) {
        
        System.out.println(Season.SPRING);  // prints SPRING
        
        for (Season s : Season.values()) {  // Enum.values() returns an array of values.
            // enumVal.name() returns the value name. enumVal.ordinal() returns the index position.
            System.out.print(s  + " " +  s.name() + " " + s.ordinal() + " ");  // SPRING SPRING 0 SUMMER SUMMER 1 FALL FALL 2 WINTER WINTER 3
        }
        System.out.println();
        
        // Enum.valueOf(String) returns enum value that exactly matches the String, or IllegalArgumentException if not found.
        // (mostly used for parsing user input)
        System.out.println(Season.valueOf("SPRING"));  // SPRING
        
        
        // Using enum in switch:
        Season s = Season.SPRING;
        switch (s) {  // switch (variableName)
            // Compiler knows s is Season type, and only Season type can be in case statement.
            case SPRING:  // Note it's SPRING, not Season.SPRING on case statement.
                System.out.println("It's spring");
                break;
            default: System.out.println("Else");
        }
        
        
        // Complex Enum:
        // Complex enum with constructor, field, and method:
        SeasonGuest.SPRING.printExpectedGuests();  // prints High
        SeasonGuest.printStaticMessage();  // prints Static Message
        SeasonGuest.SPRING.printStaticMessage();  // prints Static Message. Warning: static method should be accessed statc way.
        
        // Complex enum with enum values having enum body {}:
        System.out.println(SeasonGuest2.SPRING.getGuests());  // prints High
        System.out.println(SeasonGuest3.SPRING.getGuests());  // prints Medium
        
        // Complex enum with constructor, field, method, and enum body.
        SeasonGuest4.SPRING.printExpectedGuests();  // prints Spring Season then High
        SeasonGuest4.SPRING.printExpectedGuests();  // prints High. Enum values constructed only once and continue to be used for the program.
        
        // Enum can implement interface.
        System.out.println(SeasonGuest5.SPRING.getTemperature());  // prints 23
    }
}

// Enum is finite set of values.
// Better than set of constants because it provides type-checking with compile error if invalid value is passed.
// Enum can only be public or package access. Enum cannot be extended (by another Enum or any other).
enum Season {
    SPRING, SUMMER, FALL, WINTER;  // Semicolon is optional for simple enum. (Simple enum only contains a list of values.)
}

// Complex enum can have constructors, fields, and methods.
enum SeasonGuest {
    // List of values (with constructor parameter). List of values must always be at the top.
    // These are the constructor calls without new keyword.
    // All constructors are called when the program first asks to use this enum (and called only once in the program).
    SPRING("High"), SUMMER("Higher"), FALL("Medium"), WINTER("Low");  // Semicolon after the list is necessary for complex enum.
    
    private final String expectedGuests;  // enum field can be other than private final, but poor practice. (enum should be immutable)
    private static final String STATIC_MESSAGE = "Static Message";
    
    private SeasonGuest(String expectedGuests) {  // enum constructor is implicitly private and can only be private.
        this.expectedGuests = expectedGuests;
    }
    
    public void printExpectedGuests() {  // enum instance method (treating each enum value like instance)
        System.out.println(expectedGuests);  // prints corresponding field value depending on which enum value.
    }
    
    public static void printStaticMessage() {  // Enum static method
        System.out.println(STATIC_MESSAGE);
    }
}

// Complex enum with enum values having enum body {}. Enum has abstract method that each enum value must implement in their body {}.
enum SeasonGuest2 {
    SPRING {public String getGuests() {return "High";}},  // enum body {} contains method implementation. It's like treating each enum value like a class definition.
    SUMMER {public String getGuests() {return "Higher";}}, 
    FALL {public String getGuests() {return "Medium";}},
    WINTER {public String getGuests() {return "Low";}};
    public abstract String getGuests();
}

// Complex enum with enum values having enum body {}. Some enum values use default method implementation. Some override the method.
enum SeasonGuest3 {
    SPRING,
    SUMMER {public String getGuests() {return "Higher";}}, 
    FALL,
    WINTER {public String getGuests() {return "Low";}};
    public String getGuests() {return "Medium";}
}

// Complex enum with constructor, field, method, and enum body.
enum SeasonGuest4 {
    SPRING("High") {String season = "Spring Season"; void print() {System.out.println(season);} {print();}},  // It's like treating each enum value like a class definition.
    SUMMER("Higher") {},
    FALL("Medium") {},
    WINTER("Low") {};
    
    private final String expectedGuests;
    
    private SeasonGuest4(String expectedGuests) {
        this.expectedGuests = expectedGuests;
    }
    
    public void printExpectedGuests() {
        System.out.println(expectedGuests);
    }
}

// Enum can implement interface.
interface Temperature {int getTemperature();}

enum SeasonGuest5 implements Temperature {
    SPRING, SUMMER, FALL, WINTER;
    public int getTemperature() {return 23;}
    // enum field and method can be marked protected but since enum can't be extended, it's practically same as package private. (thus stylistically not a good choice)
}
