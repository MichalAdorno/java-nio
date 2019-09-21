package com.learning.executorserviceblockingserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.Socket;

public class TransmogrifyHandler implements Handler<Socket> {

    @Override
    public void handle(Socket s) throws IOException {
        try (s;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream();) {
            int data;
            while ((data = in.read()) != -1) {
                out.write(transmogrify(data));
            }
        } catch (IOException ioex) {
            System.out.println("Throwing an IO exception");
            throw new UncheckedIOException(ioex);
        }
    }

    public int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }

}
