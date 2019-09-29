package com.learning.singlethreadedselectornonblockingserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class WriteHandler implements Handler<SelectionKey> {

    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public WriteHandler(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        var sc = (SocketChannel) selectionKey.channel();
        pendingData.getOrDefault(sc, new ArrayDeque<>());
        Queue<ByteBuffer> queue = pendingData.get(sc);
        while(!queue.isEmpty()){
            ByteBuffer buf = queue.peek();
            int written = sc.write(buf);
            if(written == -1){
                sc.close();
                pendingData.remove(sc);
                System.out.println("Disconnected at Write " + sc);
                return;
            }
            if(buf.hasRemaining()){
                return;
            }
            queue.remove();
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }


}
