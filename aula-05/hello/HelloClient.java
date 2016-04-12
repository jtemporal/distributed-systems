import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
    
    public static void main(String[] args) {
   	
        HelloInterface obj = null; 

        try { 

	    Registry registry = LocateRegistry.getRegistry("localhost");

	    // Lookup object reference associated to the name "HelloServer"
            obj = (HelloInterface)registry.lookup("HelloServer"); 

            String message = obj.sayHello();
            System.out.println(message); 
        } catch (Exception e) { 
            System.out.println("HelloClient exception: " + e.getMessage()); 
            e.printStackTrace(); 
        }        
    }
}
