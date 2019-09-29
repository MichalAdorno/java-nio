package com.learning.workpoolselectorniononblockingserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class PooledReadHandler implements Handler<SelectionKey> {
    private final ExecutorService pool;
    private final Queue<Runnable> selectorActions;
    private final Map<SocketChannel, Queue<ByteBuffer>> pendingData;

    public PooledReadHandler(ExecutorService pool,
                             Map<SocketChannel, Queue<ByteBuffer>> pendingData,
                             Queue<Runnable> selectorActions) {
        this.pool = pool;
        this.selectorActions = selectorActions;
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocateDirect(80);
        int read = sc.read(buf);
        if (read == -1) {
            pendingData.remove(sc);
            sc.close();
            System.out.println("Disconnected at PoolRead " + sc + " thread = " + Thread.currentThread() );
            return;
        }
        if (read > 0) {
            pool.submit(() -> {
                transmogrify(buf);
                pendingData.get(sc).add(buf);
                selectorActions.add(() -> selectionKey.interestOps(SelectionKey.OP_WRITE));
                selectionKey.selector().wakeup();
            });


        }
    }

    public void transmogrify(ByteBuffer buf) {
        System.out.println("Transmogrification done by " + Thread.currentThread());
        buf.flip();
        for (int i = 0; i < buf.limit(); i++) {
            buf.put(i, (byte) transmogrify(buf.get(i)));
        }

    }

    private int transmogrify(int data) {

        return Character.isLetter(data) ? data ^ ' ' : data;

    }
}
