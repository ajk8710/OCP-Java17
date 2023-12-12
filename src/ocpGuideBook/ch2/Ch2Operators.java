package ocpGuideBook.ch2;

public class Ch2Operators {

    // This program describes niche properties and behaviors of operators
    public static void main(String[] args) {

        // ~ is bitwise complement unary operator.
        // To convert to complement: Multiply by -1 then subtract 1. (2 -> -3)
        int value = 2;
        int complement = ~value;
        System.out.println(complement);  // -3
        
        double decrementDouble = 1.1;
        System.out.println(--decrementDouble);  // 0.10000000000000009
        
        // 0, 1, or any numeric and null are not boolean value in Java. Only true & false. Specify (object == null) to see if object is null.
        
        // Prefix increments/decrements by 1 and returns updated value.
        // Postfix increments/decrements by 1 and returns original value.
        int a = 1;
        System.out.println(a++ + a++);  // 1 + 2 = 3
        System.out.println(a);  // 3
        
        // You can apply modulus to negative and floating point numbers, although it's not required to be able to calculate for exam.
        System.out.println(-5 % 3);  // -2
        System.out.println(-5 % 5);  // 0
        System.out.println(-5 % 2.0);  // -1.0
        System.out.println(-5.12 % -2.0);  // -1.12
        System.out.println(5.12 % 2.0);  // 1.12
        
        // When operator is applied to two values with different types, Java automatically promotes smaller one to larger data type.
        // byte, short, and char are promoted to int then computed when they are used with binary operator.
        // Then int will be promoted to float/double when computed with float/double.
        System.out.println('a' + 1);  // 98. 'a' is ASCII 97.
        System.out.println('A' + 'A');  // 130. 'A' is ASCII 65.
        
        byte byteMax = 127;
        short shortMax = 32767;
        System.out.println(byteMax + shortMax);  // int 32894. It's auto promoted int + auto promoted int.
        
        // short s1 = 1; short s2 = 2; short s3 = s1 + s2;  // Does not compile. Cannot convert int to short without casting (short) (s1 + s2).
        // short s4 = 1 * byteMax;  // Does not compile. byteMax promoted to int.
        
        short s5 = 1 + 1;  // Can assign int number without casting (but not int variable), as long as fits within size.
        // short s6 = 1L;  // Does not compile. Above behavior is only for int number.
        short s7 = (int) 1L;  // Does compile. Can assign int number (but not int variable), as long as fits within size.
        
        long myLong1 = 2147483647;  // int 2147483647 is auto promoted to larger type long.
        // long myLong2 = 2147483648;  // Compile error. Without l or L, int number is int. (int max is 2147483647).
        // long myLong3 = (long) 2147483648;  // Compile error. Still int number is int. (int max is 2147483647).
        long myLong4 = 2147483648L;  // Does compile.
        
        // Assigning int number to char is like assigning ASCII number.
        char c2 = 65;
        System.out.println(c2);  // A
        
        // Compound assignment operator auto casts result.
        int i = 1;
        long l = 2;
        // i = i * l;  // Regular assignment. auto promoted long * long. Does not compile without casting the result: int (i * l).
        i *= l;  // Does compile. Compound assignment operator does auto casting the result. (to int, in this case).
        
        // Assignment operator not only assigns value but also returns assigned value.
        // Followings are valid although looking odd.
        long l1 = 1;
        long l2 = (l1 = 3);  // "l1 = 3" assigns 3 to l1, then returns 3. Then 3 is assigned to l2.
        System.out.println(l1);  // 3
        System.out.println(l2);  // 3
        
        boolean isHealthy = false;
        if (isHealthy = true) {  // this assigns true to isHealthy, then returns true, so evaluates to "if (true)".
            System.out.println("Your are Healthy.");
        }
        
        // Postfix has higher precedence than assignment. See below example.
        // Postfix increments it, returns original value. Then original value is assigned back.
        int incrementedOrNot = 1;
        incrementedOrNot = incrementedOrNot++;
        System.out.println(incrementedOrNot);  // 1
        
        // null == null is true in Java
        System.out.println(null == null);  // true
        String str1 = null;
        String str2 = null;
        System.out.println(str1 == str2);  // null == null. true.
        
        // instanceof is a relational operator which returns true if left side is instance of right side (class, interface, enum, record, annotation).
        // Useful to find out if an arbitrary object is an instance of particular class/interface at runtime, when using polymorphism.
        // For example, a method that takes parameter that is superclass. Different subclasses can be passed as parameter.
        // Then the method does different things depending on which subclass it is.
        // instanceof is usually used with casting. If (myInt instanceof Integer) {Integer i = (Integer) myInt; i.someMethodSpecificToInteger...}
        System.out.println(str1 instanceof String);  // false. null instanceof validType evaluates to false.
        str1 = "";
        System.out.println(str1 instanceof String);  // true
        
        // If compiler knows it cannot possibly be instance of, it gives compiler error.
        // Number n = 1;
        // if (n instanceof String) {}  // Compile error. Number has no relation to String.
        
        // bitwise operators: &, |, ^
        // ^ is exclusive or (true only if operands are different).
        // When they are used with boolean values instead of numeric data types, they are called logical operators.
        // They are not short circuit unlike conditional operators (&&, ||).
        
        // if (dog != null & dog.getAge() < 3) This can throw null pointer exception. (& is not short circuit)
        // if (dog != null && dog.getAge() < 3) This will not throw null pointer exception. (&& is short circuit)
        
        if (true ^ true ^ true) {System.out.println("Playing with ^");}  // true ^ true is false. Then false ^ true is true.
        
        // Ternary Operator:
        // Boolean expression ? Value returning expression if true : Value returning expression if false
        // Second & third expression does not need to be same type.
        System.out.println(1 < 2 ? "Yes" : 3);  // Yes
        // int intFromTernary = 1 < 2 ? "Yes" : 3;  // Compile error. Java knows String cannot be assigned to int.
    }

}
