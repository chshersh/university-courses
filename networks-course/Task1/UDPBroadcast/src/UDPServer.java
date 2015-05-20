import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class UDPServer implements Runnable {
    private static final int IP_OFFSET = 4;
    private static final int MAC_OFFSET = 6;

    private BlockingQueue<Message> incomingMessages;

    public UDPServer() {
        incomingMessages = new ArrayBlockingQueue<>(20);
    }

    @Override
    public void run() {
        try {
            Thread msgWorker = new Thread(new MessageWorker(incomingMessages));
            msgWorker.start();

            DatagramSocket serverSocket = new DatagramSocket(7777);
            byte[] name = new byte[1024];
            while (true) {
                int nameLen = 0;
                byte[] receiveData = new byte[30];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // read ip
                byte[] ipBytes = Arrays.copyOfRange(receiveData, 0, IP_OFFSET);
                String ip = "";
                for (int i = 0; i < 4; i++) {
                    int b = (ipBytes[i] < 0) ? 256 + (int) ipBytes[i] : (int) ipBytes[i];
                    ip += String.valueOf(b);
                    if (i < 3) ip += ".";
                }

                // read mac
                byte[] macBytes = Arrays.copyOfRange(receiveData, IP_OFFSET, IP_OFFSET + MAC_OFFSET);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < macBytes.length; i++) {
                    sb.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
                }
                String mac = sb.toString();

                // read name
                for (int i = IP_OFFSET + MAC_OFFSET; receiveData[i] != 0; i++) {
                    name[nameLen++] = receiveData[i];
                }
                String n = new String(Arrays.copyOfRange(name, 0, nameLen));

                Message msg = new Message(ip, mac, n);
                incomingMessages.offer(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}