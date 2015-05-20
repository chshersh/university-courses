package com.kovanikov.net.tcp.server;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.kovanikov.net.tcp.server.ProtocolConstants.*;

public class AnnounceReceiver implements Runnable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    private JList jList;
    private Map<String, String> room;

    public AnnounceReceiver(JList jList) {
        this.jList = jList;
        room = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(7777);
            byte[] name = new byte[1024];
            while (true) {
                int nameLen = 0;
                byte[] receiveData = new byte[64];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                System.err.println("RECIEVE PACKET");

                // read ip
                byte[] ipBytes = Arrays.copyOfRange(receiveData, 0, IP_SIZE);
                String ip = "";
                for (int i = 0; i < 4; i++) {
                    int b = (ipBytes[i] < 0) ? 256 + (int) ipBytes[i] : (int) ipBytes[i];
                    ip += String.valueOf(b);
                    if (i < 3) ip += ".";
                }

                // read fileCount
                byte[] fcBytes = Arrays.copyOfRange(receiveData, IP_SIZE, IP_SIZE + FILE_COUNT);
                ByteBuffer fcWrapped = ByteBuffer.wrap(fcBytes);
                int fileCount = fcWrapped.getInt();

                // read timestamp
                byte[] tsBytes = Arrays.copyOfRange(receiveData, IP_SIZE + FILE_COUNT, IP_SIZE + FILE_COUNT + TIMESTAMP);
                ByteBuffer tsWrapped = ByteBuffer.wrap(tsBytes);
                long timestamp = tsWrapped.getLong();

                // read name
                for (int i = IP_SIZE + FILE_COUNT + TIMESTAMP; i < receiveData.length && receiveData[i] != 0; i++) {
                    name[nameLen++] = receiveData[i];
                }
                String n = new String(Arrays.copyOfRange(name, 0, nameLen));

                room.put(ip, announceString(ip, fileCount, timestamp, n));

                DefaultListModel<String> model = (DefaultListModel<String>) jList.getModel();
                model.removeAllElements();
                room.values().forEach(model::addElement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String announceString(String ip, int fc, long ts, String name) {
        return "IP: "          + fitToWidth(ip, 15) +
              " | Files: "     + fitToWidth(String.valueOf(fc), 4) +
              " | Timestamp: " + DATE_FORMAT.format(new Date(ts)) +
              " | Name: "      + fitToWidth(name, 20);
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
}