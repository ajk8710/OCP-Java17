package ocpGuideBook.ch3;

public class Ch3ControlFlow {
    static final int staticFinalInt = 100;
    
    public static void main(String[] args) {
        
        // Playing with instanceof and pattern variable.
        Number number = 1;  // 1 is integer
        System.out.println(number instanceof Integer);  // true
        System.out.println(number instanceof Integer myInt);  // true. Instantiates pattern variable myInt and not using it.
        number = 1.0;  // 1.0 is double
        System.out.println(number instanceof Integer);  // false
        System.out.println(number instanceof Double);  // true
        
        
        // If Statement: Exam will trick you by else statement followed by else statement.
        // It's actually inner else & outer else if you look closely.
        if (false)  // a statement here makes following else not compile, because then it's no longer outer if.
        if (true) System.out.println("Inner If");  // printed if true true
        else System.out.println("Inner Else");  // printed if true false
        else System.out.println("Outer Else");  // printed if false any
        
        
        // Switch Statement:
        // Switch variable can be primitives except boolean, long, float, double, which are:
        // byte, short, int, char & their wrapper types & String, enum, and var if actual type is one of these.
        // (Remember as boolean having too small range, and long, float, double having too large range.)
        
        // Case values must be compile-time constant values or final variables, and must be same type as switch variable.
        // It also can't be final variable passed in method, because it's not compile-time constant (passed value can be anything).
        // It also can't be return value of a method that must run at runtime, even if return value is always the same.
        
        // From Java 14, you can combine switch cases with comma.
        int onMe = 1;  // 1 1 1 Default 1 More
        final int seven = 7;
        switch (onMe) {
            case 1:  // From the first matching case, switch runs everything below until break, including default below.
                System.out.println(onMe);
            case 2, 3:  // This is newer style
                System.out.println(onMe);
            case 4: case 5:  // This is older style. (If case 4, runs everything below until break.)
                System.out.println(onMe);
            default:
                System.out.println("Default can be anywhere on switch cases");  // Optional default matches if nothing else matches (from above & below).
            case 6:
                {System.out.println(onMe);}  // brackets are unnecessary.
                System.out.println("More");
                break;  // break breaks out from switch brackets.
            case 'a':  // int and char can be evaluated to each other as ASCII counterpart.
                System.out.println(onMe);
            case seven:  // Wouldn't compile if seven was not final.
                System.out.println(onMe);
            case 8 * 9:  // Compiles as it resolves at compile time.
                System.out.println(onMe);
            // case 8 * 9:  // Compile error due to duplicate case. case values must be distinct.
            case staticFinalInt:  // static final variable allowed for case value
        }  // Unlike if-statement and loops, switch must have brackets even if there is a single case.
        
        // int and char can be evaluated to each other as ASCII counterpart.
        int a = 'a';  // 'a' assigned as int 97.
        System.out.println(a);  // 97
        char aa = 97;  // 97 assigned as char 'a'.
        System.out.println(aa);  // a
        
        System.out.println('a' < 'b');  // can compare chars. true.
        System.out.println(++aa);  // can increment/decrement char variable. a increments to b. a decrements to `.
        
        // switch onMe {}  // Does not compile without parentheses around onMe.
        // switch (onMe) case 1: System.out.println(onMe);  // Does not compile without brackets.
        // switch (onMe) {case 1: 2:}  // Should  be {case 1, 2:} or {case 1: case 2:}
        switch (onMe) {case 1:}  // Compiles, not doing anything in case. Must have ":" though.
        switch (onMe) {}  // Compiles without any case. case is optional.
        
        
        // Switch Expression:
        // Java 14 introduced switch expression which (optionally) can return a value.
        // It uses arrows which do not fall through. So do not need breaks.
        int fish = 2;
        switch (fish) {
            case 1 -> System.out.println("Small Fish");
            case 2 -> System.out.println("Medium Fish");  // Medium Fish
            case 3 -> System.out.println("Big Fish");
        }
        
        // Optional assignment.
        String myFish = switch (fish) {
            default -> "Great Fish";  // must return value if assigning
        };  // assignment must end with semicolon
        System.out.println(myFish);  // Great Fish
        
        // If assigning, each branch must return a value that can be assigned without casting, or return exception.
        // Can use "yield" within bracket to return a value. (It's named yield, not return, to not to be confused with method return.)
        String yourFish = switch (fish) {
            case 1 -> "Small Fish";
            case 2 -> {System.out.println("OK"); yield "Medium Fish";}  // yield must end with semicolon. Bracket within switch should not end with semicolon.
            case 3, 4 -> "Big Fish";
            // case 5 -> System.out.println("OK"); yield "Big Fish";  // Does not compile. Must have brackets if using yield.
            default -> "Great Fish";  // cases must cover all possible range of switch variable (if enum) or must have default (if not enum).
            case 5 -> {if (true) yield "Fish"; else yield "New Fish";}  // Without else, compile error. Each branch must be guaranteed to return a value.
        };  // assignment must end with semicolon
        System.out.println(yourFish);  // Medium Fish
        
        
        // While Loop, Do While Loop:
        // Unlike switch statement/expression, brackets {} are optional if there is only one statement on if-statement and while/for loops.
        do System.out.println("Single Statement"); while (1 == 0);  // Semicolon required at the end of do-while loop ().
        
        
        // For Loop:
        // Each block (initialization; boolean evaluation; update) is optional.
        // for (;;) System.out.println("Infinite Loop");  // Compiles but infinite loop. Two semicolons are required.
        int count = 0;
        for (; count < 2; count++) System.out.println("Compiles");  // Compiles Compiles
        for (int i; count < 0; ) System.out.println("Compiles");  // Initialization is not required if not using it.
        
        // First and third block can have multiple items separated by comma. Second block must be a boolean expression.
        // Variables can be any type but all variables in initialization block must be same type.
        // (as you can do a single line variable declaration for same type. But should not repeat type.)
        // Variables declared do not need be used. Variables declared outside can also be used.
        Long myLong = 1L;
        for (double i = 0, j = 0; i < 1 && j < 1 && myLong < 2; i++, myLong++) System.out.println("Runs Once");
        // for (int i, int j; ;) {}  // Does not compile. Should not repeat type when declaring on a single line with comma.
        // for (Object Obj = null; ;) {}  // Compiles but infinite loop
        
        int cnt = 0;
        for (; cnt++ < 2;) System.out.print(cnt + " "); // prints 1 2
        System.out.println();
        cnt = 0;
        for (; ++cnt < 2;) System.out.print(cnt + " "); // prints 1
        System.out.println();
        
        
        // For Each Loop: Iterate over collection (array or object implementing Iterable - List, Queue, Set).
        // for (dataType eachItem : collection) {Do something with eachItem.}
        // eachItem is a variable that gets a copy of each item in collection. Modifying eachItem do not modify contents in collection.
        
        // Map do not implement Iterable, but has methods that returns collection that implements Iterable.
        // map.keySet(), map.values()
        
        // Iterating over 2D array, for example, Double[][] (array of array) iterates over outer Double[], where eachItem is inner Double[].
        
        // To use for-each loop on String, use toCharArray() method.
        String str = "Hello";
        for (char c : str.toCharArray()) {  // can do "var c :", since compiler knows it's actually char.
            System.out.print(c + " ");  // H e l l o
        }
        System.out.println();
        
        
        // Labels:
        // if statement, switch statement, loops can have optional labels, such as SOME_LABEL:
        // break can only be used inside of loop or switch. It breaks out from current loop or switch.
        // break can take optional label parameter and break out from loop/switch with that label.
        OUTER_LOOP: for (int i = 0; i < 3; i++) {
            INNER_LOOP: for (int j = 0; j < 3; j++) {
                System.out.print(j + " ");
                if (j == 2) break OUTER_LOOP;  // Label allows to break out from parent loop.
            }                                  // Without label, break only breaks out from current loop.
        }
        System.out.println();  // 0 1 2
        
        // continue can only be used inside of loop. (Do not need to be directly inside. i.e. Can be in if-statement, then in loop.)
        // continue stops current iteration of loop and continue to next iteration (i.e. evaluation of boolean expression).
        // continue can take optional label parameter and continue to next iteration of outer loop.
        OUTER_LOOP: for (int i = 0; i < 3; i++) {
            INNER_LOOP: for (int j = 0; j < 3; j++) {
                System.out.print(j + ": ");
                if (j == 0) continue;  // continue skips below code if j==0. Without if-statement, compile error due to unreachable code below.
                System.out.print("I'm not printed when j == 0. ");
            }
            System.out.println();
        }
        System.out.println();  // prints 3 of "0: 1: I'm not printed when j == 0. 2: I'm not printed when j == 0."
                               // continue OUTER_LOOP; would print "0: 0: 0:"
                               // break breaks out from inner loop then do what's left below on outer loop. continue starts next iteration of outer loop.
        
        // Any code right after break, continue, return, and compile-time known infinite loop is considered unreachable and cause compile error,
        // unless they (break, continue, return, infinite loop) are within if-statement. Compiles even if (true) break; Then dead code;
    
        
        // Passing primitives into object. They converts into wrapper class.
        printType(3);  // passing int into printType(Object o) and compiles.
        printType('a');  // compiles
        printType("abc");  // compiles
        printType(true);  // compiles
    }
    
    // Java 16 introduced Pattern Matching & pattern variable, for shorter syntax of below common codes.
    void compareNum(Number number) {  // get parameter as super-class for polymorphism
        if (number instanceof Integer) {  // if it is specific sub-class
            Integer myInt = (Integer) number;  // cast to sub-class to run methods specific to subclass
            System.out.println(myInt.compareTo(3));
        }
    }
    
    // Using pattern variable.
    void compareNumJava16(Number number) {
        if (number instanceof Integer myInt) {  // check if number is instance of Integer, if true, instantiate myInt as Integer.
            System.out.println(myInt.compareTo(3));
        }
        
        // Can have pattern matching expression after pattern variable myInt.
        if (number instanceof Integer myInt && myInt > 3) {  // || myInt will not compile because myInt may not be declared/initialized.
            System.out.println(myInt.compareTo(3) + ": Greater than 3");
        }
        
        // Textbook says pattern variable must be sub-type of left-hand side of instanceof, and it also cannot be same type.
        Integer value = 123;
        if (value instanceof Integer data) {};  // Compiles, although textbook said it does not compile.
        
        // Flow scoping for pattern variable. (In-scope only when compiler can definitely know its type)
        // Pattern variable can be in scope even outside of if-statement only when compiler definitely knows it has declared/initialized.
        if (!(number instanceof Integer data)) {
            // This only executes if number is not Integer, so data is undeclared.
            // System.out.println(data);  // Does not compile. Compiler knows data is undeclared.
            return;  // return method if number is not Integer
        }
        System.out.println(data.intValue());  // This only executes if number is Integer and data is declared and initialized.
    }
    
    static void printType(Object o) {  // can use instanceof to find actual type.
        System.out.println(o);
    }
    
}
