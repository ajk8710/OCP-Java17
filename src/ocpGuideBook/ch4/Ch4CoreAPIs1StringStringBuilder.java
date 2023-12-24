package ocpGuideBook.ch4;

import java.util.Arrays;

public class Ch4CoreAPIs1StringStringBuilder {

    public static void main(String[] args) {
        
        // String: Three way to create it.
        String str = "My String";  // without new keyword
        str = new String("My String");  // with new key word
        str = """
              My String""";  // with text block
        System.out.println(str);
        
        System.out.println("Can concatenate " + null + true);  // Can concatenate null, true, false.
        str += 's';  // can concatenate char
        str = "Append it: ".concat(str);  // concat method does concatenation
        
        // Java's inconsistent naming for the size:
        // arr.length, str.length(), arrayList.size()
        
        // String, StringBuffer, StringBuilder implements CharSequence interface.
        
        
        // Common String methods:
        // All string methods returns new string as string is immutable.
        // Can call on string literals as well.
        System.out.println("str".charAt(0));  // s
        System.out.println("str".indexOf('s'));  // 0
        
        // charAt(validIndex)
        System.out.println(str.charAt(0));  // M
        // System.out.println(str.charAt(str.length()));  // exception if out of bound
        
        // indexOf returns first matching index or -1. indexOf(charOrString), indexOf(charOrString, fromIndex)
        System.out.println(str.indexOf('M'));  // 0
        System.out.println(str.indexOf(77));  // 0  ASCII 77 is 'M'
        System.out.println(str.indexOf("M"));  // 0
        System.out.println(str.indexOf("My S"));  // 0
        System.out.println(str.indexOf('M', 1));  // -1, from index 1.
        
        // substring(from), substring(from, endExclusive)
        System.out.println(str.substring(1));  // y String
        System.out.println(str.substring(1, 2));  // y
        System.out.println(str.substring(1, 1));  // empty string ""
        // System.out.println(str.substring(2, 1));  // out of bound exception
        // System.out.println(str.substring(1, str.length() + 1));  // out of bound exception
        
        // toLowerCase(), toUpperCase()
        System.out.println(str.toLowerCase());  // my string
        System.out.println(str.toUpperCase());  // MY STRING
        
        // equals(obj), equalsIgnoreCase(string). equals(obj other than string) returns false.
        System.out.println("str".equals("Str"));  // false
        System.out.println("str".equalsIgnoreCase("Str"));  // true
        System.out.println("str".equals(true));  // false. boolean true converted to wrapper Boolean.
        
        // startsWith(string), startsWith(string, fromIndex), endsWith(string), contains(charSeq)
        System.out.println("str".startsWith("s"));  // true
        System.out.println("str".startsWith("tr", 1));  // true. startsWith(string, fromIndex)
        System.out.println("str".endsWith("tr"));  // true. There is no method endsWith(string, fromIndex)
        System.out.println("str".contains("S"));  // false
        
        // replace replaces all target char or string. replace(old char, new char), replace(old charSeq, new charSeq)
        System.out.println("strstr".replace('s', 'S'));  // StrStr
        System.out.println("strstr".replace("st", "ST"));  // STrSTr
        
        // trim() and strip() removes leading and trailing white spaces. strip() is unicode-aware version of trim().
        // stripLeading() and stripTrailing() removes leading or trailing white spaces.
        
        // split(delimiter) splits string by delimiter and creates an array.
        String[] strArr = "strstr".split("");
        System.out.println(Arrays.toString(strArr));  // [s, t, r, s, t, r]
        System.out.println(Arrays.toString("strstr".split("tr")));  // [s, s]
        System.out.println(Arrays.toString(" str  str ".split(" ")));  // ["", "str", "", "str"]
        System.out.println(Arrays.toString(" str str ".strip().split(" ")));  // [str, str]
        
        // isEmpty() checks if "". isBlank() check if only contains white spaces.
        System.out.println("".isEmpty());  // true
        System.out.println("".isBlank());  // true
        System.out.println(" ".isEmpty());  // false
        System.out.println(" ".isBlank());  // true
        System.out.println("\n\s\t".isBlank());  // true (new line, space, tab)
        
        // compareTo(string) & compareToIgnoreCase(string) compares lexicographically. Return value as +/-/0
        // Lexicographical such as numbers first (in order such as 1, 100, 9), then upper cases, then lower cases.
        System.out.println("10".compareTo("9"));  // -8. 1 comes before 9 lexicographically.
        System.out.println("10".compareTo("a"));  // -48. Number before alphabet.
        System.out.println("ZZZ".compareTo("a"));  // -7. upper case before lower case.
        System.out.println("A".compareToIgnoreCase("a"));  // 0
        
        // indent(#ofSpaces) adds specified number of spaces to each line & add a line break at the end it not exists
        String indentMe = "line\nline".indent(2);
        System.out.println(indentMe);  //   line\n  line\n
        System.out.println(indentMe.indent(-10));  // negative parameter retracts specified spaces or all & add a line break it not exists
        
        // Add another backslash to escape the escape. (ex: \\n is the string \n. i.e: '\' 'n')
        System.out.println("\\");  // \
        System.out.println("\\\"");  // \"
        
        // translateEscapes() removes a \ in front of escape characters.
        System.out.println("\\nline");  // \nline
        System.out.println("\\nline".translateEscapes());  // a blank space then line
        
        
        // Formatting string.
        // Static method. String.format(string, args...)
        // Instance method. formatted(args...)
        // %s: any type but commonly string.  %d: integer/long.  %f: float/double.  %n: line break
        String name = "Pikachu";
        int orderNum = 3;
        System.out.println(String.format("Hello %s, order %d is ready.", name, orderNum));  // Hello Pikachu, order 3 is ready.
        System.out.println("Hello %s, order %d is ready.".formatted(name, orderNum));  // Hello Pikachu, order 3 is ready.
        
        // % [totalLength.numDecimal] f
        // %f by default shows 6 past decimal. A digit after decimal to round. Example: %.1f round to 1st.
        System.out.println("Price is $%f.".formatted(21.155));  // Price is $21.155000.
        System.out.println("Price is $%.2f.".formatted(21.155));  // Price is $21.16.
        
        // A digit before decimal is total length of output. Having leading 0 pad with zeros.
        System.out.println("Price: [%8.2f]".formatted(21.155));  // Price: [   21.16]
        System.out.println("Price: [%08.2f]".formatted(21.155));  // Price: [00021.16]
        
        
        // StringBuilder is mutable unlike String. (String concatenation creates new String instance)
        // String methods return new String, but most StringBuilder methods modify itself and return its reference.
        // StringBuffer is slower but supports threading and is thread safe.
        
        // Three constructors: StringBuilder(), StringBuilder(CharSeqence initialString), StringBuilder(int capacity)
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");  // appends string to sb and returns its reference.
        System.out.println(sb);  // Hello
        
        // StringBuilder has indexOf(), charAt(), length(), substring() methods. (substring returns a string & doesn't modify stringBuilder)
        
        // append & insert method can take a parameter as string, char, int, charSeqence, etc.
        // insert(idx, string) method inserts at given index, and returns its reference.
        sb.insert(sb.length(), "w");
        System.out.println(sb);  // Hellow
        
        // delete(from, endExclusive), deleteCharAt(validIndex)
        System.out.println(sb.deleteCharAt(5));  // Hello
        System.out.println(sb.delete(3, 4));  // Helo
        System.out.println(sb.delete(1, 100));  // H. delete is flexible on ending index. If more than length, delete all afterward.
        
        // replace(from, endExclusive, newString)
        // replace works differently compared to replace method of string (which replaced all target char/string with new char/String).
        // Instead it replaces what's in given index range, and returns its reference.
        sb.append("ello");
        System.out.println(sb.replace(2, 3, "n"));  // Henlo. Also flexible on ending index. If more than length, replace all afterward.
        
        // reverse() method reverses itself and returns its reference. (String do not have reverse method.)
        // toString() method returns string representation of itself. (Use StringBuilder to reverse a string then return toString.)
        
        // StringBuilder equals() is not implemented to check contents, unlike string.
        // Use toString() to compare contents.
        System.out.println(new StringBuilder("hi").equals(new StringBuilder("hi")));  // false
        System.out.println(new StringBuilder("hi").toString().equals(new StringBuilder("hi").toString()));  // true
        
        
        // String Pool. JVM creates string pool to store string literals and constants to reuse them and save memory.
        // This is only for string literals (i.e. ones created with "" at compile-time) and not ones created with new keyword or method return values or by compound assignment (+=).
        String s = "Hello";   // creates "Hello" in string pool then points to it.
        String st = "Hello";  // reuses same object stored in String Pool. (pointing to same string object in string pool)
        System.out.println(s == st);  // true. only one object is created.
        
        String ss = "";
        ss += "Hello";  // not compile time operation
        System.out.println(s == ss);  // false
        
        String sss = "Hel" + "lo";  // compile time assignment
        System.out.println(s == sss);  // true
        
        String ssss = "";
        ssss = "Hel" + "lo";  // Previous ssss can't be modified. Reassigning ssss variable to point to "Hello" of string pool. (compile time assignment)
        System.out.println(s == ssss);  // true
        
        String s1 = "Hel";
        String s2 = "lo";
        String s3 = s1 + s2;  // not compile time operation
        System.out.println(s == s3);  // false
        
        String s4 = "";
        s4 = "Hel" + s2;  // not compile time operation when concatenating variable
        System.out.println(s == s4);  // false
        
        // intern() method forces to use string pool
        String string = new String("Hello").intern();
        System.out.println(s == string);  // true
        "Hello".intern();  // String literal also has intern method, although it likely has no effect. (string literals use pool anyways)
        
    }

}
