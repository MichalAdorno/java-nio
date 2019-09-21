package com.learning.executorserviceblockingserver;

import java.io.IOException;

public class PrintHandler<S> extends DecoratedHandler<S> {

    public PrintHandler(Handler<S> other) {
        super(other);
    }

    @Override
    public void handle(S s) throws IOException {
        System.out.println("Connected to " + s);
        try{
            super.handle(s);
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }
}
