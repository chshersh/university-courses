package com.kovanikov.net.tcp.server;

import com.kovanikov.net.tcp.misc.Utils;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.kovanikov.net.tcp.server.ProtocolConstants.*;

public class CommandParser {
    public static String parseCommand(String cmdText) {
        if (cmdText.isEmpty()) {
            return "empty command";
        }

        String[] line = cmdText.split("\\s");
        if (line.length < 2) {
            return "USAGE: <cmd> <ip> [arguments]";
        }

        String cmd = line[0];
        cmd = cmd.toUpperCase();
        StringBuilder respMessage = new StringBuilder();
        switch (cmd) {
            case "LIST" : {
                System.err.println("LIST CMD");
                String serverIP = line[1];
                try (Socket clientSocket = new Socket(serverIP, PORT)) {
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                    os.write(LIST_CODE); // send LIST code to server

                    byte respCode = is.readByte(); // getting server response
                    if (respCode == LIST_RESP_CODE) {
                        int fileNumber = is.readInt();
                        respMessage.append("Files count: ").append(fileNumber).append('\n');
                        for (int i = 0; i < fileNumber; i++) {
                            byte[] md5 = new byte[16];
                            is.read(md5);

                            respMessage
                                .append("File ").append(i + 1).append(":\n")
                                .append("    ").append("file name: ").append(new String(Utils.readString(is))).append('\n')
                                .append("    ").append("md5: ").append(Hex.encodeHex(md5)).append('\n');
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return respMessage.toString();
            }
            case "GET" : {
                System.err.println("GET CMD");
                String serverIP = line[1];
                String fileName = line[2];
                try (Socket clientSocket = new Socket(serverIP, PORT)) {
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                    os.writeByte(GET_CODE);
                    os.writeBytes(fileName + '\u0000');

                    byte respCode = is.readByte();
                    if (respCode == GET_RESP_CODE) {
                        long fileSize = is.readLong();
                        byte[] md5 = new byte[16];
                        is.read(md5);
                        byte[] body = Utils.readString(is, fileSize);

                        respMessage
                            .append("File: ").append(fileName).append('\n')
                            .append("File size: ").append(fileSize).append('\n')
                            .append("md5: ").append(Hex.encodeHex(md5)).append('\n')
                            .append("Contains:\n").append(new String(Arrays.copyOfRange(body, 0, 1024)));

                        File getFile = new File(GET_FOLDER + "/" + fileName);
                        getFile.createNewFile();
                        Files.write(Paths.get(getFile.getAbsolutePath()), body);

                        return respMessage.toString();
                    }

                    return "No such file: '" + fileName + "'";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case "PUT" : {
                System.err.println("PUT CMD");
                String serverIP = line[1];
                String fileName = line[2];
                String data = "";
                if (line.length > 3) {
                    data = String.join(" ", Arrays.copyOfRange(line, 3, line.length));
                } else {
                    File f = new File(SHARED_DIR + "/" + fileName);
                    Path filePath = Paths.get(f.getAbsolutePath());
                    try {
                        data = new String(Files.readAllBytes(filePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try (Socket clientSocket = new Socket(serverIP, PORT)) {
                    DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
                    os.writeByte(PUT_CODE);
                    os.writeBytes(fileName + '\u0000');
                    byte[] dataBytes = data.getBytes();
                    long fileSize = dataBytes.length;
                    os.writeLong(fileSize);
                    os.write(dataBytes);

                    return "file should be put";
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            default: return "unknown command: '" + cmdText + "'";
        }

        return "wtf!?";
    }
}