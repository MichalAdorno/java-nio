package com.learning.multithreadeddecoratedblockingserver;

public class ThreadedHandler<S> extends UncheckedIOExceptionConvertHandler<S> {
    public ThreadedHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) {
        new Thread(() -> super.handle(s)).start();
    }
}
