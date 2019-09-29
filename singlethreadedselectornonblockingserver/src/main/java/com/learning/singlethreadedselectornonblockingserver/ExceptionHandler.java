package com.learning.singlethreadedselectornonblockingserver;

import java.util.function.BiConsumer;

public class ExceptionHandler<S> extends DecoratedHandler<S> {
    private final BiConsumer<S, Throwable> exceptionConsumer;

    public ExceptionHandler(Handler<S> other, BiConsumer<S, Throwable> exceptionConsumer) {
        super(other);
        this.exceptionConsumer = exceptionConsumer;
    }

    public ExceptionHandler(Handler<S> other) {
        this(other, (s, x) -> System.err.println("Issue with " + s + " error " + x));
    }

    public void handle(S s) {
        try {
            super.handle(s);
        } catch (Throwable x) {
            exceptionConsumer.accept(s, x);
        }
    }
}