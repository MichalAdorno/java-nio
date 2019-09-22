package com.learning.blockingnioserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class Main {

    public static void main(String... args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));

        ExecutorService pool = new ThreadPoolExecutor(
                10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000));
        Handler<SocketChannel, IOException> handler =
                new ExecutorServiceHandler<>(
                        pool,
                        new PrintHandler<>(
                                new BlockingChannelHandler(
                                        new TransmogrifyChannelHandler())));
        while (true) {
            SocketChannel sc = ssc.accept(); // never null - blocks
            handler.handle(sc);
        }
    }

}
