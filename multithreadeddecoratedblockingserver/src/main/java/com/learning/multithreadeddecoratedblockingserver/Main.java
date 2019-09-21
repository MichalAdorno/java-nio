package com.learning.multithreadeddecoratedblockingserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String... args) throws IOException {
        var ss = new ServerSocket(8080);
        var handler = new ThreadedHandler<>(
                new PrintHandler<>(
                        new TransmogrifyHandler()
                )
        );

        while (true) {
            var s = ss.accept();
            handler.handle(s); //start a new thread, and handle s in that thread
        }
    }

}
