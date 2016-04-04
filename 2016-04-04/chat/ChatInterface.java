import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends java.rmi.Remote {
    int joinGroup(String name, MessageInterface ref) throws RemoteException;
    void leaveGroup(int id) throws java.rmi.RemoteException;
    void message(int id, String msg) throws java.rmi.RemoteException;
}
