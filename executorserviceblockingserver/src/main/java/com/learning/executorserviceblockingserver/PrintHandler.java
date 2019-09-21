package com.learning.executorserviceblockingserver;

import java.io.IOException;

public class PrintHandler<S> implements Handler<S> {
    private final Handler<S> other;

    public PrintHandler(Handler<S> other) {
        this.other = other;
    }

    @Override
    public void handle(S s) throws IOException {
        System.out.println("Connected to " + s);
        try{
            other.handle(s);
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }
}
