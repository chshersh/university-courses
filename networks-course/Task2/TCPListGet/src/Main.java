import com.kovanikov.net.tcp.gui.TCPWindow;

public class Main {
    public static void main(String[] args) {
        TCPWindow form = new TCPWindow(args.length == 0 ? "wlan" : args[0]);
    }
}
