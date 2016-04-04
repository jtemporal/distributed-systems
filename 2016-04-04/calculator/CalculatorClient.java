import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    
    public static void main(String[] args) {
        int a=0, b=0, result=0;	
        CalculatorInterface obj = null; 

        try { 

	    // Registry registry = LocateRegistry.getRegistry("172.16.41.103"); // for accessing other machine
	    Registry registry = LocateRegistry.getRegistry("localhost");

	    // Lookup object reference associated to the name "CalculatorServer"
        obj = (CalculatorInterface)registry.lookup("CalculatorServer"); 
        
        // extracao de valores a e b  <value> <op> <value>
        a = Integer.parseInt(args[0]); 
        b = Integer.parseInt(args[2]);
        
        //verifica e executa operacao
        switch (args[1]) {
            case "add":
                result = obj.add(a,b);
                System.out.println(args[0] + " add " + args[2] + " = " + result);
                break;
            case "sub":
                result = obj.sub(a,b);
                System.out.println(args[0] + " sub " + args[2] + " = " + result);
                break;
            case "times":
                result = obj.times(a,b);
                System.out.println(args[0] + " times " + args[2] + " = " + result);
                break;
            case "div":
                result = obj.div(a,b);
                System.out.println(args[0] + " div " + args[2] + " = " + result);
                break;
            default:
                System.out.println("Invalid operation: " + args[1]);
        }

        //String message = obj.sayCalculator();
        //System.out.println(message); 
        } catch (Exception e) { 
            System.out.println("CalculatorClient exception: " + e.getMessage()); 
            e.printStackTrace(); 
        }        
    }
}
