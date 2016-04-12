import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MessageInterfaceImpl extends UnicastRemoteObject implements MessageInterface {

	public MessageInterfaceImpl() throws RemoteException {
		super();
	}
	
    public void messageNotification(String sender, String msg) throws RemoteException {
        System.out.println(); // pula linha
        System.out.println(sender + " >> " + msg); 
    }
}
