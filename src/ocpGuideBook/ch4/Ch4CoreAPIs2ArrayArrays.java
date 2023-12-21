package ocpGuideBook.ch4;

import java.util.Arrays;

public class Ch4CoreAPIs2ArrayArrays {

    public static void main(String[] args) {
        
        // When initialized with given size, all elements are given default values.
        int[] intArr = new int[5];  // {0, 0, 0, 0, 0}
        for (int i : intArr) {System.out.print(i + " ");} System.out.println();  // 0 0 0 0 0
        
        // Initializing with elements (Initializing with array initializer).
        int[] intArr2 = new int[] {1, 2, 3};  // {} is called array initializer
        int[] intArr3 = {1, 2, 3};  // compiler already knows it's int[] from left hand side, so new int[] can be omitted
        // System.out.println({1, 2, 3});  // compile error. compiler does not know type. new int[] required.
        
        // [] can go anywhere before or after variable name, spaces are ignored.
        int [] a1; int []a2; int a3[]; int a4  []; int[]nospace;
        
        int[] a5, a6;  // a5 and a6 are both int array.
        int a7[], a8;  // a7 is int array. a8 is int.
        int a9[] = {0}, a10 = 0;
            
        String[] strArr = new String[3];
        Object[] objArr = strArr;  // casting rule is the same. cast needed when assigning bigger into smaller.
        
        // Java's inconsistent naming for the size:
        // arr.length, str.length(), arrayList.size()
        
        // equals() compares references as java does for all reference types. (except string)
        // toString() prints hash values.
        
        
        // Utility Class: java.util.Arrays class has static methods to work with arrays.
        System.out.println(Arrays.toString(intArr3));  // java.util.Arrays.toString prints nicely [1, 2, 3]
        
        // Arrays.sort(arr) sorts given array itself.
        // Sorting string array sorts on numbers first (in order such as 1, 100, 9), then upper cases, then lower cases.
        
        // Arrays.binarySearch(arr, element) can search on sorted array. It returns index if found.
        // If not found, it returns one smaller then negative of index where it needs be inserted to preserve order. (multiply -1 then subtract 1)
        // (If element needs to be inserted at 0, it returns -1) (To get index, add 1 then multiply -1)
        // (If element needs to be inserted at 1, it return -2) (To get index, add 1 then multiply -1)
        
        // Arrays.compare(arr1, arr2) compares array. Return value (+/-/0) can be mentally thought as arr1 - arr2. (0 if same)
        // 0 if it's same length and all elements are same.
        // If all elements are the same, but the other has extra element, the array without extra (shorter in length) is smaller array.
        // If the first element that differer is smaller, then it's smaller array.
        // null is considered smaller than any other.
        System.out.println(Arrays.compare(new int[] {1}, null));  // 1. (Arguments can't be both null and null)
        System.out.println(Arrays.compare(new String[] {"The other array contains a null"}, new String[1]));  // 1
        // Arrays.compare(new int[] {1}, new String[] {"1"});  // compile error. compare/mismatch/equals must be arrays of same type.
        
        // Arrays.mismatch(arr1, arr2) is opposite concept of compare(). If same returns -1.
        // If different, returns index of first mismatch.
        // System.out.println(Arrays.mismatch(new int[] {1}, null));  // runtime error. null point exception.
        System.out.println(Arrays.mismatch(new int[] {1}, new int[] {1}));  // -1
        
        // Arrays.equals(arr1, arr2) returns if all elements are the same.
        System.out.println(Arrays.equals(new int[] {1}, null));  // false. (Arguments can't be both null and null)
        System.out.println(Arrays.equals(new int[] {1}, new int[] {1}));  // true
        
        
        // Multi-dimentional Array
        int[][][] array1 = new int[2][][];  // only top-most size must be declared at declaration.
        int[][] array2 = new int[3][];
        array2[0] = new int[3];  // building asymmetric array
        array2[1] = new int[2];
        array2[2] = new int[] {1};
        
        for (int[] arr : array2) {  // for each array of array2
            for (int i : arr) {  // for each int of array
                System.out.print(i + " ");  // 0 0 0
            }                               // 0 0
            System.out.println();           // 1
        }
        
    }

}
