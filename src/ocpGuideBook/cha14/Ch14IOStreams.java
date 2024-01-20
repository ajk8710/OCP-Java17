package ocpGuideBook.cha14;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
    
}
