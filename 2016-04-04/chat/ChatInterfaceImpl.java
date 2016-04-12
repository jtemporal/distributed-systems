import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatInterfaceImpl extends UnicastRemoteObject implements ChatInterface {

    private ArrayList<Participant> list;
    private int chatId;

	public ChatInterfaceImpl() throws RemoteException {
		super();

        list = new ArrayList<Participant>();
        chatId = 0;
	}
	
    int joinGroup(String name, MessageInterface ref) throws RemoteException {

        Participant p;
        p = new Participant(name, chatId, ref);
        chatId++;

        if (list.add(p)){
            return p.getChatIdentifier();
        } else return -1;
    }

    void leaveGroup(int id) throws RemoteException {
        
        Participant p;

        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getChatIdentifier() == id) {
                list.remove(p);
                break;
            }
        }
    }

    void message(int id, String msg) throws RemoteException {

        Participant p;

        try {
            p = getParticipant(id);
            if (p != null) {
                 sender = p.getName();
                 for (int i = 0; i < list.size(); i++) {
                    ref = list.get(i).getRef();

                    //chama msg de notificacao
                    ref.messageNotification(sender, msg);
                 }
            }
        
        } catch(Exception e) {
            System.out.prinln("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Participant getParticipant (int id) {

        Participant p;

        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getChatIdentifier() == id) {
                return(p);
            }
        }
    
    }
}
