package com.learning.multithreadedblockingserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String... args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);

        while(true){
            Socket s = ss.accept();
            handleRequestInNewThread(s);
        }
    }

    private static void handleRequestInNewThread(Socket s) throws IOException {
        System.out.println("Connected to " + s);
        new Thread(() -> {
            try(s;
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream();) {
                int data;
                while ((data = in.read()) != -1) {
                    out.write(transmogrify(data));
                }
            } catch(IOException ioex){
                System.out.println("Throwing an IO exception");
                throw new UncheckedIOException(ioex);
            } finally {
                System.out.println("Disconnected from " + s);
            }
        }).start();

    }

    private static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}
