import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorInterface extends Remote {
    public int add(int a, int b) throws java.rmi.RemoteException;
    public int sub(int a, int b) throws java.rmi.RemoteException;
    public int times(int a, int b) throws java.rmi.RemoteException;
    public int div(int a, int b) throws java.rmi.RemoteException;
}
