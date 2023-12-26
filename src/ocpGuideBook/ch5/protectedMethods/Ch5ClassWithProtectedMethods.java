package ocpGuideBook.ch5.protectedMethods;

public class Ch5ClassWithProtectedMethods {
    
    protected static String protectedStaticString = "Protected Static String";
    
    protected String protectedString = "Protected String";
    
    protected static void protectedStaticMethod() {
        System.out.println("Protected Static Method");
    }
    
    protected void protectedMethod() {
        System.out.println("Protected Method");
    }
    
}
