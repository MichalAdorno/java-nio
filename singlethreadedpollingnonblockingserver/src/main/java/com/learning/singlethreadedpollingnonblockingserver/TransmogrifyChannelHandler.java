package com.learning.singlethreadedpollingnonblockingserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TransmogrifyChannelHandler implements Handler<SocketChannel> {

    @Override
    public void handle(SocketChannel sc) throws IOException {
        ByteBuffer buf = ByteBuffer.allocateDirect(80);
        int read = sc.read(buf);
        if (read == -1) {
            sc.close();
            return;
        }
        if (read > 0) {
            transmogrify(buf);
            while (buf.hasRemaining()) {
                sc.write(buf);
            }
            buf.compact(); //limit==capacity, pos==0
        }
    }

    public void transmogrify(ByteBuffer buf) {
        //pos ==0, limit==80, capacity==80
        //hello\n
        //pos==6, limit==80, capacity==80
        buf.flip();
        //or:
        //buf.limit(buf.position());
        //buf.position(0);
        //pos==0, limit==6, capacity==80
        for (int i = 0; i < buf.limit(); i++) {
            buf.put(i, (byte) transmogrify(buf.get(i)));
        }

    }

    private int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;

    }

}
