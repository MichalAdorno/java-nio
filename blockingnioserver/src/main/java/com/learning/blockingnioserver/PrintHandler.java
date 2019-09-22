package com.learning.blockingnioserver;

public class PrintHandler<S, X extends Throwable> extends DecoratedHandler<S, X> {
    public PrintHandler(Handler<S, X> other) {
        super(other);
    }

    public void handle(S s) throws X {
        System.out.println("Connected to " + s);
        try {
            super.handle(s);
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }
}
