import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

class UDPClient implements Runnable {
    private static final int IP_OFFSET = 4;
    private static final int MAC_OFFSET = 6;

    public byte[] IP_ADDR;
    public byte[] MAC_ADDR;
    public byte[] FIRST_NAME;

    public void initClient() throws IOException {
        Enumeration<NetworkInterface> net = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        ipAddr: while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress i = addresses.nextElement();
                if (i instanceof Inet4Address) {
                    if (!InetAddress.getLocalHost().toString().contains(i.toString())) {
                        ip = i;
                        break ipAddr;
                    }
                }
            }
        }

        String[] ab = ip.toString().substring(1).split("\\.");
        byte[] ipBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            ipBytes[i] = Integer.valueOf(ab[i]).byteValue();
        }

        IP_ADDR = ipBytes;
        MAC_ADDR = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
        FIRST_NAME = "Dmitry".getBytes();
    }

    public class ClientTask extends TimerTask {
        private byte[] sendData;

        public ClientTask() {
            sendData = new byte[IP_OFFSET + MAC_OFFSET + FIRST_NAME.length + 1];

            System.arraycopy(IP_ADDR, 0, sendData, 0, IP_ADDR.length);
            System.arraycopy(MAC_ADDR, 0, sendData, IP_OFFSET, MAC_ADDR.length);
            System.arraycopy(FIRST_NAME, 0, sendData, IP_OFFSET + MAC_OFFSET, FIRST_NAME.length);
        }

        @Override
        public void run() {
            try {
                DatagramSocket clientSocket = new DatagramSocket();
                clientSocket.setBroadcast(true);
                InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 7777);
                clientSocket.send(sendPacket);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            initClient();
            Timer t = new Timer();
            t.scheduleAtFixedRate(new ClientTask(), 0, 2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}