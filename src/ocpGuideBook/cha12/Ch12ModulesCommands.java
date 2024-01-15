package ocpGuideBook.cha12;

public class Ch12ModulesCommands {
    
    public static void main(String[] args) {
        
        /*
        java.base module is automatically added as a dependency for all modules.
        
        Commands for modules:
        
        --describe-module (-d) gives information about module.
        java -p mods -d zoo.animal.feeding
        java -p mods --describe-module zoo.animal.feeding
        
        --list-modules lists available modules
        java --list-modules  // lists modules from Java & JDK
        java -p mods --list-modules  // lists modules from Java & JDK + modules in mods folder
        
        --show-module-resolution prints a lot of output, before running program. It's like debug mode.
        java --show-module-resolution -p feeding -m zoo.animal.feeding/zoo.animal.feeding.Task
        
        jar command can also describe module.
        jar -f mods/zoo.animal.feeding.jar -d
        jar --file mods/zoo.animal.feeding.jar --describe-module
        jar options: -c -create, -v -verbose, -C (directory that contains files to be zipped to JAR)
        Example: jar -cvf mods/zoo.animal.feeding.jar -C feeding/ .
        
        jdeps tells what dependencies are actually used rather than simply declared.
        jdeps zoo.dino.jar  // tells what modules are used for what classes in this jar
        jdeps -summary zoo.dino.jar  // -summary or -s tells what modules are used
        jdeps --jdk-internals zoo.dino.jar  // --jdk-internals or -jdkinternals tells what internals or out-dated unsupported classes are included
        
        jmod is only for working with JMOD files, which are for native libraries or something that can't go inside JAR file.
        Options include: create, extract (like unzip), describe, list (list all files in JMOD), hash (print hashes)
        
        Creating Java runtime image with jlink.
        One of benefits of modules is being able to supply just the parts of Java you need. (only modules it needs, such as java.base module).
        Following creates zooApp with smaller file size that does not include whole Java.
        jlink --module-path mods --add-modules zoo.animal.staff --output zooApp
        
        Be familiar with and be able to recognize common module names provided by java & jdk. (pg 688)
        
        
        Named modules, automatic modules, unnamed modules:
        Named module is one containing a module-info.java file and is in module path.
        
        Automatic module appears in module path but does not contain a module-info.java file.
        It is regular JAR file placed on the module path. (regular JAR such as JAR created before introduction of modules)
        Java automatically determines module name, unless MANIFEST.MF in JAR specifies Automatic-Module-Name.
        Java treats as if there is module-info.java, automatically exports all packages.
        Auto naming rule: Remove .jar extension, remove version info (-0.0-snap), replace special characters with dot, remove sequence, leading, trailing dots.
        
        Unnamed modules appear on the class path. It does not contain a module-info.java file, and is ignored if contains one.
        They are regular JAR and treated like old codes. They do not export any packages.
        They can read from any JARs on the classpath or module path (like days before introduction of modules)
        (As result, codes in class path can access module path. Codes in module path cannot read class path.)
        
        
        You can have cyclic dependency between packages. You cannot have cyclic dependency between modules.
        
        Migrating non-modular application to modular.
        Bottom-Up starts from lowest-level (a project (packages) having no dependencies). Add module-info.java then move it to module path. Repeat.
        Top-Down moves all project to module path (all becomes automatic modules) then starts from highest-level (one with most dependencies).
        
        */
    }
    
}
