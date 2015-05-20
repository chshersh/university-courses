package com.kovanikov.net.tcp.misc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static byte[] readString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            int c = is.read();
            if (c == 0 || c == -1) break;
            baos.write(c);
        }
        return baos.toByteArray();
    }

    public static byte[] readString(InputStream is, long size) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        long i = 0;
        while (true) {
            if (i >= size) break;
            i++;
            int c = is.read();
            if (c == -1) break;
            baos.write(c);
        }
        return baos.toByteArray();
    }
}
