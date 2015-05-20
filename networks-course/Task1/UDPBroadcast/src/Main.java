
public class Main {
    public static void main(String[] args) {
        Thread serverThread = new Thread(new UDPServer());
        serverThread.start();
        Thread clientThread = new Thread(new UDPClient());
        clientThread.start();
    }
}
