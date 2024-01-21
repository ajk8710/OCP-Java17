package ocpGuideBook.cha14;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Ch14IOStreams {
    
    public static void main(String[] args) {
        
        // Byte I/O streams read/write data as bytes (binary). Ex: InputStream, OutputStream. (image or executable)
        // Character I/O streams read/write data as text. Ex: Reader, Writer. (text file)
        // Difference is how they interpret stream of bits, unit of bits interpreted at a time, encode/decode characters, etc.
        
        // Charset utf8Charset = Charset.forName("UTF-8");  // Specifying character encoding with Charset.
        
        // PrintWriter has no corresponding PrintReader class (To help remember: Think it's obvious, Printer is for writing)
        // PrintStream is also output stream without corresponding input stream.
        
        // InputStream, OutputStream, Reader, and Writer are four abstract classes that are parents of all I/O stream in java.io.
        
        // For the exam, only low-level stream classes are the ones that operate on files.
        // The rest of non-abstract classes are high levels. Low level connects directly with source of data.
        try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("file.txt")))) {
            System.out.print(ois.readObject());  // High level class takes low level class as parameter to constructor.
        }  // try-with-resources    
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
        }
        // Note you cannot pass InputStream/OutputStream into Reader/Writer when passing as parameter, vice versa.
        //     Also for passing Input class into Output class, vice versa.
        // Note you cannot instantiate abstract class with new keyword then pass as parameter.
        
    }
    
    // Reading & writing a byte at a time. Not very efficient.
    void copyStream(InputStream in, OutputStream out) throws IOException {  // java.io.InputStream is not java.util.stream.Stream;
        int b;
        while ((b = in.read()) != -1) {  // read() reads & returns next byte of data as int or -1 to indicate end of stream
            out.write(b);  // void write(int) writes specified byte to this output stream
        }
    }
    
    void copyStream(Reader in, Writer out) throws IOException {
        int b;
        while ((b = in.read()) != -1) {  // read a byte, save it into int b.
            out.write(b);  // write b
        }
    }
    
    // Reading & writing multiple bytes at a time.
    void copyStreamWithBuffer(InputStream in, OutputStream out) throws IOException {
        int batchSize = 1024;
        var buffer = new byte[batchSize];  // array of 1024 bytes
        int lengthRead;                                             // read(byte[] buffer, int offset, int uptoLen)
        while ((lengthRead = in.read(buffer, 0, batchSize)) > 0) {  // reads upto length of byte from this stream, and put into buffer from offset. Returns number of bytes read.
            out.write(buffer, 0, lengthRead);  // writes exact length of byte from buffer (from offset), to this output stream
            out.flush();  // Flush to reduce amount of data lost if application terminates unexpectedly before write completes.
            // Flush requests that all accumulated data be written immediately to disk, with performance cost.
        }
    }
    
    // Reading & writing with high level classes
    void copyTextFile(File src, File dest) throws IOException {  // FileReader & FileWriter constructors take File or String.
        try (var reader = new BufferedReader(new FileReader(src)); var writer = new BufferedWriter(new FileWriter(dest))) {  // FileWriter overwrites without append boolean flag.
            String line = null;
            while ((line = reader.readLine()) != null) {  // BufferedInputStream has readAllBytes() method instead, as well as read(buffer) InputStream. write(buffer) for OutputStream.
                writer.write(line);
                writer.newLine();  // Writes new line. readLine() have striped line breaks.
            }
        }  // try-with-resources closes resources.
    }
    
    // Compared to BufferedWriter, PrintWriter provides additional methods: print(), println(), format(), printf().
    void copyTextFile2(File src, File dest) throws IOException {
        try (var reader = new BufferedReader(new FileReader(src)); var writer = new PrintWriter(new FileWriter(dest))) {  // Using PrintWriter instead of BufferedWriter.
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.println(line);  // PrintWriter & PrintStream methods do not throw exception unlike other I/O Streams. (just like println() does not)
            }
        }
    }  // System.out is PrintStream object, which also has println() method.
    
    // Using NIO.2 Files. Following three methods read all at once, may throw OutOfMemoryError on large file. Closes file when all read.
    void copyPathAsString(Path input, Path output) throws IOException {
        String string = Files.readString(input);  // Reads all content from a file into a String, decoding bytes to characters.
        Files.writeString(output, string);  // Write a CharSequence to a file.
    }
    
    void copyPathAsBytes(Path input, Path output) throws IOException {
        byte[] bytes = Files.readAllBytes(input);  // Reads all the bytes from a file into byte[].
        Files.write(output, bytes);  // From byte array, writes bytes to a file
    }
    
    void copyPathAsLines(Path input, Path output) throws IOException {
        List<String> lines = Files.readAllLines(input);  // Read all lines from a file into List<String>, decoding bytes to characters.
        Files.write(output, lines);  //  From string array, write lines of text to a file
    }
    
    // Instead of reading all at once, lines(Path) reads lines as it outputs to Stream<String>.
    void readLazily(Path path) throws IOException {  // Read all lines from a file as a Stream, decoding bytes to characters.
        try (Stream<String> s = Files.lines(path)) {  // The returned stream contains a reference to an open file. The file is closed by closing the stream. 
            s.forEach(System.out::println);
        }
        
        try (var s = Files.lines(path)) {  // filter line that starts with WARNING:, write out warning message.
            s.filter(f -> f.startsWith("WARNING:"))
            .map(f -> f.substring(8))
            .forEach(System.out::println);
        }
    }
    
    // From NIO.2 Path to I/O reader & writer (newBufferedReader & newBufferedWriter).
    void copyPath(Path input, Path output) throws IOException {
        try (var reader = Files.newBufferedReader(input); var writer = Files.newBufferedWriter(output)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();  // writes new line
            }
        }  // This is more concise than var reader = new BufferedReader(new FileReader(File))
    }
    
    
}
