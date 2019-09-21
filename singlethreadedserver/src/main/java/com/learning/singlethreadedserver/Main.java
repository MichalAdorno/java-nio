package com.learning.singlethreadedserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    /**
     * When multiple users eg. U1, U2, U3 connect in order (U1, U2, U3),
     * then a Single-Threaded Server communicates with them in the same order.
     * This means that all communication with U1 is resolved before that of U2 and so on.
     * So the subsequent users U2 and U3 are not able to get responses from the server
     * until all requests of U1 are resolved.
     */
    public static void main(String... args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);

        while (true) {
            Socket s = ss.accept();
            try (s;
                 InputStream in = s.getInputStream();
                 OutputStream out = s.getOutputStream();) {
                int data;
                while ((data = in.read()) != -1) {
                    out.write(transmogrify(data));
                }
            } finally {
                System.out.println("Disconnected from " + s);
            }
        }
    }

    private static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}
