package ocpGuideBook.cha14;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Ch14FilePathPathsFiles {
    
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
        System.out.println("--- I/O methods ---");
        File newFile1 = new File("C:\\ch14\\newFile.txt");  // \\ writes \
        if (newFile1.exists()) {
            System.out.println(newFile1.getAbsolutePath());  // C:\ch14\newFile.txt  If relative path, assume it's in current working directory.
            System.out.println(newFile1.getParent());  // C:\ch14
            System.out.println(newFile1.isFile());  // true
            System.out.println(newFile1.isDirectory());  // false
            
            if (newFile1.isFile()) {
                System.out.println(newFile1.length());  // size in bytes
                System.out.println(newFile1.lastModified());  // +/- long in milliseconds since the epoch, or 0L if file does not exist
            }
            else {  // if C:\\ch14
                for (File subFile : newFile1.listFiles()) {  // file.listFiles() returns an array of sub files and directories. file.list() returns String names of them.
                    System.out.println(subFile.getName());  // newFile.txt subDirOnCh14  Return the last segment of pathname
                }
            }
        }
        
        // NIO.2 methods. Many uses helper class Files. Files is helper class of NIO.2 (different API from File)
        // Path instances are immutable, like String. Many methods return new Path object.
        System.out.println("\n--- NIO.2 methods ---");
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
                    stream.forEach(path -> System.out.println(path.getFileName()));  // (getFileName, not getName)  Returns Path object (last segment of path)
                }  // Using try-with-resouce to close resource properly. Otherwise resource leak may happen and may not be able to modify the path later.
            }
        }
        
        // Instance methods
        System.out.println("\n--- Instance methods ---");
        System.out.println("toString: " + newPath1);  // toString: C:\ch14\newFile.txt  toString is only method that returns String.
        System.out.println("toString: " + newPath1.toString());  // toString: C:\ch14\newFile.txt
        
        // Getting index and element of path
        for(int i = 0; i < newPath1.getNameCount(); i++) {  // getNameCount() returns number of element to the path, excluding root directory C:\
            System.out.println("Element " + i + " is: " + newPath1.getName(i));
        }  // Element 0 is: ch14  Element 1 is: newFile.txt
        
        var path = Paths.get("/");
        System.out.println(path.getNameCount());  // 0
        path = Paths.get("C:\\");
        System.out.println(path.getNameCount());  // 0
        
        // Creating subpath (like substring)
        path = Paths.get("/non/existing/file.image");  // path.subpath(include, exclude)
        System.out.println("subpath(0,3): " + path.subpath(0, 3));  // non\existing\file.image
        System.out.println("subpath(1,2): " + path.subpath(1, 2));  // existing
        System.out.println("subpath(1,3): " + path.subpath(1, 3));  // existing\file.image
        
        System.out.println("a".substring(0, 0));  // empty string
        // System.out.println(path.subpath(0, 0));  // IllegalArgumentException
        
        // Resolving path (concatenate): resolve(Path), resolve(String)
        path1 = Path.of("/a/../b");
        path2 = Path.of("c");
        System.out.println(path1.resolve(path2));  // \a\..\b\c
        
        path3 = Path.of("/a/b");  // cannot concatenate two absolute path (linux absolute path starts with slash)
        System.out.println(path3.resolve("/c/d"));  // \c\d  result is parameter passed in
        
        // Relativize: create path from one to another.
        // On two relative paths, it assumes .. to get same current working directory. On two absolute paths, it computes relative path.
        // IllegalArgumentException on mixed path or different root directory (C:\ & E:\)
        path1 = Path.of("fish.txt");
        path2 = Path.of("friendly/birds.txt");
        System.out.println(path1.relativize(path2));  // ..\friendly\birds.txt
        System.out.println(path2.relativize(path1));  // ..\..\fish.txt
        
        path3 = Paths.get("E:\\habitat");
        path4 = Paths.get("E:\\sanctuary\\raven\\poe.txt");
        System.out.println(path3.relativize(path4));  // ..\sanctuary\raven\poe.txt
        System.out.println(path4.relativize(path3));  // ..\..\..\habitat
        
        // Normalize: remove redundant path symbols (/ . ..)
        path1 = Path.of("./armadillo/../shells.txt");
        System.out.println(path1.normalize());  // shells.txt  .. and one previous cancels each other. ./ is redundant.
    
        path1 = Path.of("../../fish.txt");
        System.out.println(path1.normalize()); // ../../fish.txt
        
        path2 = Path.of("shells.txt");  // normalize allows better compare
        System.out.println(path1.equals(path2));  // false
        System.out.println(path1.normalize().equals(path2)); // true
        
        // toRealPath() is like normalize() + toAbsolutePath() but throws IOException if path does not exists. Follows symbolic link (shortcut) if exists.
        System.out.println(Paths.get(".").toRealPath());  // prints current working directory
        System.out.println(Paths.get(".").toAbsolutePath());  // prints current working directory then \.
        
        
        // Files static method
        
        // Creating directory (not file):
        // Path createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException
        //     creates new directory. If already exist or wrong path, throw IOExcpetion.
        // Path createDirectories(Path dir, FileAttribute<?>... attrs) throws IOException
        //     creates directory, while also creating necessary path. Don't do anything if already exists.
        
        // Copying:
        // Path copy(Path source, Path target, CopyOption... options) throws IOException
        //     copies but shallow, meaning sub-dirs and sub-files are not copied. Deep copy requires recursion.
        // Files.copy(Paths.get("book.txt"), Paths.get("movie.txt"), StandardCopyOption.REPLACE_EXISTING);
        //     REPLACE_EXISTING, overwrites instead of throwing exception if already exists.
        
        // Moving (renaming, it's like cut & paste):
        // Path move(Path source, Path target, CopyOption... options) throws IOException
        //     Requires REPLACE_EXISTING to overwrite, or IOException.
        //     When filename -> /foldername, copy() & move() both create a file named /foldername without extension.
        //     StandardCopyOption.ATOMIC_MOVE is available to move() & copy(), likely to throw IOException when given to copy().
        
        // Deleting:
        // void delete(Path path) throws IOException  Throws an exception if the path does not exist.
        // boolean deleteIfExists(Path path) throws IOException  Returns true if the delete was successful or false otherwise.
        //     For Both method, to delete a directory, it must be empty, or IOException is thrown.
        
        // Comparing path objects:  Files.isSameFile(path1, path2)
        // isSameFile to compare two files or two directories (two path objects).
        // Throws exception if does not exist, except when they are equal in terms of equals(), then it just returns true.
        // Comparing symbolic link (shortcut) and actual path is true.
        
        // Comparing contents:  Files.mismatch(path1, path2)
        // Returns -1 if contents are same, meaning there is no mismatch.
        // Otherwise first index where it mismatches.
        
        Path newPath2 = Path.of("C:\\ch14\\newFileCopy.txt");
        // Files.createFile(newPath2);  // createFile creates file or exception if already exists.
        System.out.println(Files.isSameFile(newPath1, newPath2));  // false
        System.out.println(Files.mismatch(newPath1, newPath2));  // -1, no mismatch, same content
        
    }
    
}
