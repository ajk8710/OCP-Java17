package ocpGuideBook.ch4;

public class Ch4CoreAPIs3Math {

    public static void main(String[] args) {
        
        // Math class is in java.lang package, so it does not need to be imported.
        
        // Utility Class: java.lang.Math class has static method to work with numbers. (I mean num for int/long/float/double)
        // Math.min(num, num), Math.max(num, num) return smaller/larger of two. 
        System.out.println(Math.max(2, 1.0));  // int parameter 2 auto converts to double. Method returns 2.0
        
        // Math.round(float or double) rounds up/down number to int or long.
        System.out.println(Math.round(1));  // 1. int parameter 1 auto converts to float 1.0f then returns int 1.
        System.out.println(Math.round(1.5f));  // 2. float parameter returns int.
        System.out.println(Math.round(1.200));  // 1. double parameter returns long.
        // int i = Math.round(1.0);  // compile error. cannot convert returned long to int without cast.
        
        // Math.ceil(double), Math.floor(double) returns double.
        // ceil always rounds up to next whole number if there is decimal. floor always drops decimals.
        System.out.println(Math.ceil(1));  // int parameter 1 auto converts to double 1.0. Method returns 1.0.
        System.out.println(Math.ceil(1.01));  // 2.0
        System.out.println(Math.floor(1.99));  // 1.0
        
        // Math.pow(double num, double expo) does exponents and return double.
        System.out.println(Math.pow(1, 1));  // 1.0
        System.out.println(Math.pow(3, 3));  // 27.0
        System.out.println(Math.pow(9, 0.5));  // 3.0. doing square-root
        System.out.println(Math.pow(9, 1/2));  // 1.0. 1/2 does integer division and gives 0.
        
        // Math.random() returns a double value that is, 0 <= double < 1
        
    }

}
