package ocpGuideBook.cha11;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Ch11PropertiesWithResourceBundles {
    
    public static void main(String[] args) {
        
        // Localization requires externalizing text strings out of class that use them.
        // Resource bundle stores key-value pairs, and is normally stored in properties file.
        // Each line is key value pairs separated by = or :
        // (Create new source folder in project, ex. resources. Add properties files there, ex. Zoo_en.properties)
        
        ResourceBundle rb1 = ResourceBundle.getBundle("Zoo");  // Selects default locale when not specified. Mostly used in practice.
        ResourceBundle rb2 = ResourceBundle.getBundle("Zoo", new Locale("fr", "FR"));  // Selects specified locale. Used on exam.
        rb1.getString("hello");  // gets string value of the key
        
        // When locale is not specified (i.e. ResourceBundle.getBundle("Zoo");), Java starts looking from default locale to set as base (Zoo_en_US -> Zoo_en -> Zoo).
        // When locale is specified, Java tries to find the best match to set as base.
        // If, fr_FR is requested, as rb2 above:
        // Look for Zoo_fr_FR, then Zoo_fr, then default locale which is Zoo_en_US, then Zoo_en then just Zoo without locale.
        // Once it selects the base resource bundle, it can look up keys from the base's parent's if it's not in base.
        // (Parent here means removing the component of name: Zoo_fr_FR (key not here) -> Zoo_fr (key not here) -> Zoo (is it here, if not exception is thrown))
        
        Locale us = new Locale("en", "US");
        Locale france = new Locale("fr", "FR");
        printWelcomeMessage(us);  // Hello, The zoo is open
        printWelcomeMessage(france);  // Bonjour, Le zoo est ouvert
        
        // In practice, text string in properties file normally contains parameters to be substituted.
        // HelloWithName=Hello, {0} and {1}!
        // Get the string with ResourceBundle, then substitute parameters with MessageFormat.
        String str = rb2.getString("HelloWithName");  // HelloWithName not in Zoo_fr.properties -> find from Zoo.properties.
        String message = MessageFormat.format(str, "Pikachu", "Raichu");  // MessageFormat.format(stringToFormat, varargs)
        System.out.println(str);  // Hello, {0} and {1}!
        System.out.println(message);  // Hello, Pikachu and Raichu!
        
        
        // Properties class are normally used to handle values that may not exist.
        // It was available from Java 1.0 before hashmap was available.
        Properties props = new Properties();
        props.setProperty("name", "Pikachu");
        System.out.println(props.getProperty("name"));  // Pikachu
        System.out.println(props.getProperty("nonKey"));  // null
        System.out.println(props.getProperty("nonKey", "Default Value"));  // Default Value
        // get(key) and getOrDefault(key, default) are also available.
    }
    
    public static void printWelcomeMessage(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
        System.out.println(rb.getString("hello") + ", " + rb.getString("open"));
    }
    
}
