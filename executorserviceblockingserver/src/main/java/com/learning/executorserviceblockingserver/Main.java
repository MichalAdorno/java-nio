package com.learning.executorserviceblockingserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String... args) throws IOException {
        var ss = new ServerSocket(8080);
        var handler = new ExecutorServiceHandler<>(
                new PrintHandler<>(new TransmogrifyHandler()),
                Executors.newCachedThreadPool(),
                (t, e) -> System.out.println("Uncaught: " + t + " error " + e)
        );

        while (true) {
            var s = ss.accept();
            handler.handle(s); //start a new thread, and handle s in that thread
        }
    }

}
