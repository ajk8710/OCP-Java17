package ocpGuideBook.cha14;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

public class Ch14UserInteractions {
    
    public static void main(String[] args) throws IOException {
        
        // PrintStream instances write output to user
        try (var in = new FileInputStream("file.txt")) {
            System.out.println("Found file!");
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");  // report errors to the user in a separate I/O stream. Prints in red in IDE console.
        }  // if being on server, may write to different log file.
        
        // System.in returns an InputStream, used to retrieve text input from the user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Plese type something then enter: ");
        String userInput = reader.readLine();
        System.out.println("You entered: " + userInput);
        
        // Closing the System streams is not recommended. It closes and make it unavailable for remainder of program.
        
        // java.io.Console provides methods to handle user input.
        // Console class is a singleton: accessible only from a factory method & only one instance is created.
        // Console c = new Console();  // compile error
        Console console = System.console();  // If program is run from an IDE, console will not connect automatically, so returns null.
        if (console != null) {
            System.out.print("Plese type something then enter: ");
            String userInputs = console.readLine();
            console.writer().println("You entered: " + userInputs);
        } else {
            System.err.println("Console not available");
        }
        // Console includes access to following I/O streams. Calling these are analogous to calling System.in & System.out. (although it's char stream compared to byte stream)
        // public Reader reader()
        // public PrintWriter writer()
        
        // console.printf(string, varargs) behaves same as console.format(string, varargs), printing formatted string to console.
        // Another ways are console.writer().printf(string, varargs) & console.writer().format(string, varargs).
        
        // Console includes overloaded readLine(String prompt, varargs) that writes prompt before asking for user input.
        // readPassword() does not echo back user input to screen, like when asking to type password. Returns char[] instead of String. (prevents passwords from entering String pool)
        
        // For IDE, I would say use usual scanner.
        // Reminder: Scanner.next() or nextInt() does not read new line character during user input. nextLine() after these will take that new line.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Plese type something then enter: ");
            String usersInput = scanner.nextLine();
            System.out.println(usersInput);
        }
        
        
        /*
        Advanced topics:
        
        InputStream and Reader have following methods:
        public boolean markSupported() - Must call to check before calling mark() or reset().
        public void mark(int readLimit) - Mark position to return upon reset. Throws exception if reset called after readLimit bytes.
        public void reset() throws IOException - Return to marked position. (In reality, it uses temporary buffer of readLimit size, instead of going back.)
        public long skip(long n) throws IOException - Reads then discards contents. i.e. skips upto n bytes. Returns actual number of bytes skipped.
        
        Checking for symbolic links (shortcuts):
        File class has isDirectory() & isFile(). They cannot check for symbolic link.
        Files class has isDirectory() & isRegularFile(). They return true for symbolic link, if it resolves to directory or file.
        Files also has isSymbolicLink(). It returns true if symbolic link, whether or not it resolves to anything.
        
        Checking file's accessibility - boolean attribute: Only isHidden() method throw checked exception if file does not exist. Others return false.
        Files has isHidden(), isReadable(), isWriteable(), and isExecutable()
        */
        
        try {
            // readAttributes() gets all attributes in one method call, then you can call above methods on it.
            var path = Paths.get("file.txt");
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            attributes.isDirectory();
            
            // Create view: a group of related attributes for a particular file system type.
            // getFileAttributeView() creates view, and allows you modify attributes.
            BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
            BasicFileAttributes attributez = view.readAttributes();
            
            FileTime lastModifiedTime = FileTime.fromMillis(attributez.lastModifiedTime().toMillis() + 10_000);
            view.setTimes(lastModifiedTime, null, null);  // pass null for any date/time value that you do not want to modify
        }
        catch (Exception e) {
            System.out.println("Exception");
        }
        
    }
    
}
