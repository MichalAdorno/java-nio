package com.learning.executorserviceblockingserver;

import java.io.IOException;
import java.io.UncheckedIOException;

public class UncheckedIOExceptionConvertHandler<S> implements Handler<S> {

    private final Handler<S> other;

    public UncheckedIOExceptionConvertHandler(Handler<S> other) {
        this.other = other;
    }

    @Override
    public void handle(S s) {
        try{
            other.handle(s);
        } catch(IOException ioex){
            throw new UncheckedIOException(ioex);
        }
    }
}
