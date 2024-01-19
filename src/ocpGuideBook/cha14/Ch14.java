package ocpGuideBook.cha14;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ch14 {
    
    public static void main(String[] args) throws IOException {
        
        // I/O (input/output). NIO.2 (non-blocking I/O, or commonly referred new I/O). NIO version 1 that covered channels is not on exam.
        // I/O is all about java.io.File. NIO.2 is java.nio.file.Path + extensive use of factory pattern (abstract class/interface + factory/helper class)
        // Java often treats path as a file. (They both reference location on disk)
        
        // File class with constructors.
        File file1 = new File("/a/b/c/file.txt");
        File file2 = new File("/a/b", "c/file.txt");  // Java inserts system dependent path separator between arguments.
        File parentPath = new File("/a/b");
        File file3 = new File(parentPath, "c/file.text");  // if parentPath is null, it ignores it.
        
        System.out.println(file1.exists());  // false
        
        // NIO.2: Paths factory class creates instance of Path interface, with static method get().
        //        Path interface also has static factory method of(). 
        Path path1 = Path.of("a/b/c/file.txt");
        Path path2 = Path.of("/a", "b", "c", "file.txt");  // Path.of(first, varargs)
        Path path3 = Paths.get("a/b/c/file.txt");
        Path path4 = Paths.get("/a", "b", "c", "file.txt");  // Paths.get(first, varargs)
        
        // Many older libraries use File to switch between path and file. Newer applications should use Path.
        Path nowPath = file3.toPath();
        File nowFile = path3.toFile();
        
        // NIO.2: FileSystems factory class creates instance of abstract class FileSystem, with getDefault().
        FileSystem fileSystem = FileSystems.getDefault();
        Path path5 = FileSystems.getDefault().getPath("a/b/c/file.txt");  // long way of getting path
        Path path6 = FileSystems.getDefault().getPath("/a", "b", "c", "file.txt");
        
        
        // I/O methods
        File newFile1 = new File("C:\\ch14\\newFile.txt");  // \\ writes \
        if (newFile1.exists()) {
            System.out.println(newFile1.getAbsolutePath());  // C:\ch14\newFile.txt
            System.out.println(newFile1.getParent());  // C:\ch14
            System.out.println(newFile1.isFile());  // true
            System.out.println(newFile1.isDirectory());  // false
            
            if (newFile1.isFile()) {
                System.out.println(newFile1.length());  // size in bytes
                System.out.println(newFile1.lastModified());  // +/- long in milliseconds since the epoch, or 0L if file does not exist
            }
            else {  // if C:\\ch14
                for (File subFile : newFile1.listFiles()) {  // file.listFiles() returns an array of sub files and directories. file.list() returns String names of them.
                    System.out.println(subFile.getName());  // newFile.txt subDirOnCh14
                }
            }
        }
        
        // NIO.2 methods. Many uses helper class Files. Files is helper class of NIO.2 (different API from File)
        // Path instances are immutable, like String. Many methods return new Path object.
        Path newPath1 = Path.of("C:\\ch14\\newFile.txt");
        if (Files.exists(newPath1)) {  // not newPath.exists()
            System.out.println(newPath1.toAbsolutePath());  // C:\ch14\newFile.txt  (toAbsolutePath, not getAbsolutePath)
            System.out.println(newPath1.getParent());  // C:\ch14  (same as File method)
            System.out.println(Files.isRegularFile(newPath1));  // true  not newPath.isFile()
            System.out.println(Files.isDirectory(newPath1));  // false  not newPath.isDirectory()
            
            if (Files.isRegularFile(newPath1)) {
                System.out.println(Files.size(newPath1));  // size in bytes  not newPath.length()  throws IOException
                System.out.println(Files.getLastModifiedTime(newPath1));  // returns FileTime.  not newPath.lastModified()  throws IOException
            }
            else {  // if C:\\ch14
                try (Stream<Path> stream = Files.list(newPath1)) {  // not newPath1.listFiles()  throws IOException
                    stream.forEach(path -> System.out.println(path.getFileName()));  // (getFileName, not getName)  Returns Path object.
                }  // Using try-with-resouce to close resource properly. Otherwise resource leak may happen and may not be able to modify the path later.
            }
        }
        
        System.out.println("toString: " + newPath1);  // toString: C:\ch14\newFile.txt  toString is only method that returns String.
        System.out.println("toString: " + newPath1.toString());  // toString: C:\ch14\newFile.txt
        for(int i = 0; i < newPath1.getNameCount(); i++) {  // getNameCount() returns number of element to the path, excluding root directory C:\
            System.out.println("Element " + i + " is: " + newPath1.getName(i));
        }  // Element 0 is: ch14  Element 1 is: newFile.txt
        
        var path = Paths.get("/");
        System.out.println(path.getNameCount());  // 0
        path = Paths.get("C:\\");
        System.out.println(path.getNameCount());  // 0
        
        path = Paths.get("/non/existing/file.image");  // path.subpath(include, exclude)
        System.out.println("subpath(0,3): " + path.subpath(0, 3));  // non\existing\file.image
        System.out.println("subpath(1,2): " + path.subpath(1, 2));  // existing
        System.out.println("subpath(1,3): " + path.subpath(1, 3));  // existing\file.image
        
        System.out.println("a".substring(0, 0));  // empty string
        // System.out.println(path.subpath(0, 0));  // IllegalArgumentException
    }
    
}
