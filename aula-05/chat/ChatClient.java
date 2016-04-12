import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
    
    public static void main(String[] args) {
   	
        ChatInterface obj = null; 
        MessageInterfaceImpl ref;
        Scanner sc;
        String line;
        int id;

        try { 

	    Registry registry = LocateRegistry.getRegistry("localhost");
        
	    // Lookup object reference associated to the name "ChatServer"
        obj = (ChatInterface)registry.lookup("localhost"); 

        // Instancia obj e implementa a interface de callback
        ref = new MessageInterfaceImpl();

        //join group
        if (id  != -1) {
            // ler e enviar mensagens
            sc = new Scanner(System.in);
            System.out.print(" >> ");
            line = sc.nextLine();
            while (!line.equals("exit")){
                obj.message(id, line);
                System.out.print(" >> ");
                line = sc.nextLine();
            }

            // leave group
            obj.leaveGroup(id);

        } else {
            System.out.println("Erro de execucao join");
            System.exit(0);
        }

        } catch (Exception e) { 
            System.out.println("ChatClient exception: " + e.getMessage()); 
            e.printStackTrace(); 
        }        
    }
}
