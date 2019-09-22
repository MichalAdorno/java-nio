package com.learning.blockingnioserver;

public class DecoratedHandler<S, X extends Throwable> implements Handler<S, X> {

    private final Handler<S, X> other;

    public DecoratedHandler(Handler<S, X> other) {
        this.other = other;
    }

    @Override
    public void handle(S s) throws X {
        other.handle(s);
    }
}
