import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

public class HostPerson {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private String ip;
    private String mac;
    private String name;
    private long lastReceived;
    private Queue<Long> msgHistory;

    public HostPerson(String ip, String mac, String name, long lastReceived) {
        this.ip = ip;
        this.mac = mac;
        this.name = name;
        this.lastReceived = lastReceived;
        this.msgHistory = new ArrayDeque<>(20);
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }

    public long getLastReceived() {
        return lastReceived;
    }

    public int getPacketsLost() {
        return msgHistory.size();
    }

    public void updateHistory(long curTime, boolean received) {
        if (curTime - lastReceived > 2000) {
            msgHistory.add(curTime);
        }
        if (received) lastReceived = curTime;

        while (!msgHistory.isEmpty() && curTime - msgHistory.peek() > 20000)
            msgHistory.poll();
    }

    private static String fitToWidth(String s, int width) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.substring(0, Math.min(width, s.length())));
        width -= s.length();
        for (; width > 0; width--) {
            sb.append(' ');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "| IP: " + fitToWidth(getIp(), 15) +
              " | MAC: " + fitToWidth(getMac(), 17) +
              " | Name: " + fitToWidth(getName(), 13) +
              " | Lost: " + fitToWidth(String.valueOf(getPacketsLost()), 2) +
              " | Last received: " + dateFormat.format(new Date(getLastReceived()));

    }
}
