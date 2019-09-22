package com.learning.blockingnioserver;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class BlockingChannelHandler extends DecoratedHandler<SocketChannel, IOException> {

    public BlockingChannelHandler(Handler<SocketChannel, IOException> other) {
        super(other);
    }

    @Override
    public void handle(SocketChannel sc) throws IOException {
        while (sc.isConnected()) {
            super.handle(sc);
        }
    }
}
