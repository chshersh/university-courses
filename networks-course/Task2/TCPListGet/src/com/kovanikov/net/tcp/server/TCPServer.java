package com.kovanikov.net.tcp.server;

import com.kovanikov.net.tcp.misc.Utils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.kovanikov.net.tcp.server.ProtocolConstants.*;

public class TCPServer implements Runnable {
    private static Set<String> lockedFiles = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private class TCPConnection implements Runnable {
        private Socket clientSocket;

        private TCPConnection(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                System.err.println("ACQUIRED NEW TCP CONNECTION FROM " + clientSocket.getInetAddress());
                DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

                byte code = is.readByte();
                switch (code) {
                    case LIST_CODE : {
                        System.err.println("RECEIVE LIST CMD");
                        os.writeByte(LIST_RESP_CODE);
                        File dir = new File(SHARED_DIR);
                        File[] sharedFiles = dir.listFiles();
                        int fileCount = 0;
                        List<byte[]> md5s = new ArrayList<>();
                        List<String> names = new ArrayList<>();

                        for (File file : sharedFiles) {
                            if (file.isFile() && !lockedFiles.contains(file.getAbsolutePath())) {
                                fileCount++;
                                InputStream fis = new FileInputStream(file);
                                md5s.add(DigestUtils.md5(fis));
                                names.add(file.getName() + '\u0000');
                            }
                        }

                        os.writeInt(fileCount);
                        for (int i = 0; i < md5s.size(); i++) {
                            os.write(md5s.get(i));
                            os.writeBytes(names.get(i));
                        }
                        break;
                    }
                    case GET_CODE : {
                        System.err.println("RECEIVE GET CMD");
                        String fileName = new String(Utils.readString(is));
                        File f = new File(SHARED_DIR + "/" + fileName);
                        if (f.exists() && !lockedFiles.contains(f.getAbsolutePath())) {
                            os.write(GET_RESP_CODE);
                            os.writeLong(f.length());

                            try (InputStream fis = new FileInputStream(f)) {
                                os.write(DigestUtils.md5(fis));
                            }

                            Path filePath = Paths.get(f.getAbsolutePath());
                            os.write(Files.readAllBytes(filePath));
                        } else {
                            os.writeByte(ERROR_CODE);
                        }
                        break;
                    }
                    case PUT_CODE : {
                        System.err.println("RECEIVE PUT CMD");
                        // read data from input stream
                        String fileName = new String(Utils.readString(is));
                        long fileSize = is.readLong();
                        byte[] data = Utils.readString(is, fileSize);

                        // creating file
                        File f = new File(SHARED_DIR + "/" + fileName);
                        lockedFiles.add(f.getAbsolutePath());
                        if (!f.createNewFile()) {
                            lockedFiles.remove(f.getAbsolutePath());
                            break;
                        }
                        Files.write(Paths.get(f.getAbsolutePath()), data);
                        lockedFiles.remove(f.getAbsolutePath());
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean locked(String filePath) {
        return lockedFiles.contains(filePath);
    }

    @Override
    public void run() {
        System.err.println("STARTING TCP SERVER");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                Thread connectionThread = new Thread(new TCPConnection(clientSocket));
                connectionThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
