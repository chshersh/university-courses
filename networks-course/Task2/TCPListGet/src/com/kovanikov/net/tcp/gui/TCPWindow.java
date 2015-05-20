package com.kovanikov.net.tcp.gui;

import com.kovanikov.net.tcp.server.AnnounceReceiver;
import com.kovanikov.net.tcp.server.CommandParser;
import com.kovanikov.net.tcp.server.TCPServer;
import com.kovanikov.net.tcp.server.UDPAnnouncer;

import javax.swing.*;

public class TCPWindow extends JFrame {
    private JPanel root;
    private JTextField cmdTextField;
    private JLabel respLabel;
    private JLabel cmdTextLabel;
    private JList ipList;
    private JTextArea respTextArea;
    private JButton announceButton;

    private UDPAnnouncer announcer;

    public TCPWindow(String netInterface) {
        super("TCP/IP, ctd lab");
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cmdTextField.addActionListener(e -> {
            String cmdText = cmdTextField.getText();
            System.err.println("EXECUTING CMD: '" + cmdText + "'");
            String response = CommandParser.parseCommand(cmdText);
            respTextArea.setText("");
            respTextArea.append(response + "\n");
        });

        this.announcer = new UDPAnnouncer(netInterface);
        announceButton.addActionListener(e -> {
            announcer.announce();
            cmdTextField.requestFocus();
        });

        ipList.setModel(new DefaultListModel<>());
        ipList.setLayoutOrientation(JList.VERTICAL);

        Thread udpThread = new Thread(new AnnounceReceiver(ipList));
        udpThread.start();

        Thread tcpServer = new Thread(new TCPServer());
        tcpServer.start();
    }
}
