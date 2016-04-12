public class Participant {
    private String name;
    private int chatIdentifier;
    private MesssageInterface ref;

    public Participant(String name, int chatIdentifier, MesssageInterface ref) {
        this.name = name;
        this.chatIdentifier = chatIdentifier;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public int getChatIdentifier() {
        return chatIdentifier;
    }

    public int getRef() {
        return ref;
    }
}
