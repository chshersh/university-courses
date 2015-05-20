package com.kovanikov.net.tcp.server;

public class ProtocolConstants {
    public static final int PORT = 7777;

    public static final byte ERROR_CODE = 0x0;
    public static final byte LIST_CODE  = 0x1;
    public static final byte GET_CODE   = 0x2;
    public static final byte PUT_CODE   = 0x3;

    public static final byte LIST_RESP_CODE = 0x4;
    public static final byte GET_RESP_CODE  = 0x5;

    public static final int IP_SIZE    = 4;
    public static final int FILE_COUNT = 4;
    public static final int TIMESTAMP  = 8;

    public static final String NAME = "Dmitry Kovanikov";
    public static final String SHARED_DIR = "shared_folder";
    public static final String GET_FOLDER = "get_folder";
}
