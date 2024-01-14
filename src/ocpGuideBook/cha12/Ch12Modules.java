package ocpGuideBook.cha12;

public class Ch12Modules {
    
    public static void main(String[] args) {
        
        // If a class has no package declaration, -cp (-classpath, --class-path) is not required to run.
        // javac MyClass.java
        // java MyClass
        // (From Java 11, you can compile and run single file with: java Myclass.java)
        
        // If a class has package declaration, -cp is required to find the class. Also need to say full class name that includes packages.
        // java -cp C:\Users\UserName\desktop\"My Example"\feeding zoo.animal.feeding.MyClass
        // (MyClass is in C:\Users\UserName\desktop\"My Example"\feeding\zoo\animal\feeding directory)
        
        
        // Module is a group of packages + module-info.java
        // module-info.java should be in root directory of module. (feeding folder)
        
        // Syntax of module-info.java file: exports allows packages in this module to be used by outsiders.
        // module zoo.animal.feeding {
        //     exports zoo.animal.feeding;
        // }
        
        // Compile module: --module-path (-p) for directory of dependent modules. -d for directory to put class files. (from My Example)
        // javac --module-path mods -d feeding feeding/zoo/animal/feeding/MyClass.java feeding/module-info.java
        
        // Run module to test before packaging: --module-path (-p) for directory of modules. -module (-m) for module name. (from My Example)
        // java --module-path dirMods --module module.name/package.name.MyClass
        // java --module-path feeding --module zoo.animal.feeding/zoo.animal.feeding.MyClass
        
        // Package module: package everything under feeding directory, and store as zoo.animal.feeding.jar under mods folder.
        // jar -cvf mods/zoo.animal.feeding.jar -C feeding/ .
        
        // Run the package.
        // java --module-path mods --module zoo.animal.feeding/zoo.animal.feeding.MyClass
        
        
        // module-info.java under care folder. (care module)
        // module zoo.animal.care {
        //     exports zoo.animal.care.medical;
        //     // omits zoo.animal.care.details package, not exporting, only used in this module.
        //     requires zoo.animal.feeding;  // this module is dependent on feeding module.
        // }
        
        // Can export package to only specific modules.
        // exports zoo.animal.care.medical to zoo.staff;
        
        // requires transitive means any module (staff) requiring me (care), also requires my dependency (feeding).
        // Therefore staff does not need to declare both require statements (care & feeding), but only one (care).
        // module zoo.animal.care {
        //     ...
        //     requires transitive zoo.animal.feeding;
        // }
        
        // You cannot have duplicate require statements, such as requires feeding & requires transitive feeding.
        
        
        // "opens" allows technique called reflection to be used on the package.
        // opens zoo.animal.care.medical;  // opens to anyone
        // opens zoo.animal.care.medical to zoo.staff;  // opens to staff only
        
        // open module zoo.animal.care {  // "open" on module opens all its exporting packages.
               // opens zoo.animal.care.medical;  // Compile error: open cannot be used with opens, it's duplicate.
        // }
        
    }
    
}

