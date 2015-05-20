package com.kovanikov.net.tcp.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

import static com.kovanikov.net.tcp.server.ProtocolConstants.*;

public class UDPAnnouncer {
    private byte[] ipAddr;
    private int fileCount;
    private long timestamp;

    private byte[] getMyIP(String netInterface) throws IOException {
        boolean passInterface = false;

        Enumeration<NetworkInterface> net = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        ipAddrLbl: while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress i = addresses.nextElement();
                String interfaceName = i.toString();
                passInterface |= interfaceName.contains(netInterface);
                System.out.println(interfaceName);
                if (passInterface && i instanceof Inet4Address) {
                    //System.out.println("IS Inet4");
                    if (!InetAddress.getByName("localhost").getHostAddress().contains(i.toString())) {
                        ip = i;
                        break ipAddrLbl;
                    }
                }
            }
        }

        String[] ab = ip.toString().substring(1).split("\\.");
        byte[] ipBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            ipBytes[i] = Integer.valueOf(ab[i]).byteValue();
        }

        return ipBytes;
    }

    public UDPAnnouncer(String netInterface) {
        try {
            this.ipAddr = getMyIP(netInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void announce() {
        System.err.println("UPDATING TIMESTAMP");
        updateTimestamp();
        System.err.println("DONE UPDATE");
        System.err.println("SENDING UDP PACKET");

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            clientSocket.setBroadcast(true);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dataStream = new DataOutputStream(bos);
            dataStream.write(ipAddr);
            dataStream.writeInt(fileCount);
            dataStream.writeLong(timestamp);
            dataStream.write((NAME + '\u0000').getBytes());
            dataStream.flush();
            byte[] bytes = bos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(
                bytes, bytes.length,
                InetAddress.getByName("255.255.255.255"), PORT
            );

            clientSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTimestamp() {
        File dir = new File(SHARED_DIR);
        File[] sharedFiles = dir.listFiles();
        int cnt = 0;
        long lastModified = 0;
        for (File file : sharedFiles) {
            if (file.isFile() && !TCPServer.locked(file.getAbsolutePath())) {
                lastModified = Math.max(file.lastModified(), lastModified);
                cnt++;
            }
        }

        this.fileCount = cnt;
        this.timestamp = lastModified;
    }
}
