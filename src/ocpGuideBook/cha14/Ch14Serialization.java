package ocpGuideBook.cha14;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ch14Serialization {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        // Serialization solves how to convert between objects to I/O streams.
        // Serialization: (In-memory) Object -> I/O (byte) Stream. Writing object to stored or transmittable format.
        // Deserialization: I/O Stream -> Object
        
        // Any class/record to be serialized should implement Serializable interface (marker interface without any method).
        // (or extend class that implement Serializable.)
        // Java primitives and Many built-in Java classes are Serializable.
        // transient field do not get saved to I/O stream when class is serialized, gets default Java value when deserialized.
        // Static variables are not serialized (except serialVersionUID). Method are not serialized.
        // To serialize, every instance field must be serializable, or have null value at time of serialization.
        
        // High level InputStream/OutputStream classes for deserialaztion/serialization:
        // ObjectInputStream deserializes an object. (reads "in" data)
        // ObjectOutputStream serializes an object. (write "out" data)
        
        // public Object readObject() throws IOException, ClassNotFoundException
        // public void writeObject(Object obj) throws IOException
        
        List<Dog>  dogs = new ArrayList<Dog>();
        dogs.add(new Dog("Puppy", 1, "Bow"));
        dogs.add(new Dog("Pup", 2, "Wow"));
        File dogsFile = new File("C:\\ch14\\dogsFile.data");
        
        saveToFile(dogs, dogsFile);  // creates dogsFile.data and writes hard-to-human-readable data.
        List<Dog> dogsFromFile = readFromFile(dogsFile);
        System.out.println(dogsFromFile);  // [Puppy of age 1, Pup of age 2]
        
        // When deserialize, Java do not call constructor of serialized class or its initializer,
        // instead it calls no-arg constructor of first non-serializable parent class (Object for Dog)
    }
    
    // Serialization
    static void saveToFile(List<Dog> dogs, File dataFile) throws IOException {  // wrapping in bufferedStream to improve performance.
        try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            for (Dog dog : dogs) out.writeObject(dog);  // pass to writeObject() to write to out stream.
        }
    }
    
    // Deserialization
    static List<Dog> readFromFile(File dataFile) throws IOException, ClassNotFoundException {
        var dogs = new ArrayList<Dog>();
        try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            while (true) {  // infinite loop until EOFException is thrown
                var object = in.readObject();  // -1 or null would not have special meaning for reading object.
                if (object instanceof Dog dog) dogs.add(dog);
            }
        } catch (EOFException e) {
            // end of file reached
        }
        return dogs;
    }
    
}

class Dog implements Serializable {
    private static final long serialVersionUID = 1L;  // good practice to have serialVersionUID, or get compiler warning
    String name;
    int age;
    transient String temp;
    
    Dog() {}
    
    Dog(String name, int age, String temp) {
        this.name = name;
        this.age = age;
        this.temp = temp;
    }
    
    public String toString() {
        return name + " of age " + age;
    }
}
