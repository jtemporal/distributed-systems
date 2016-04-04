import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends java.rmi.Remote {
    void messageNotification(String sender, String msg) throws RemoteException;
}
