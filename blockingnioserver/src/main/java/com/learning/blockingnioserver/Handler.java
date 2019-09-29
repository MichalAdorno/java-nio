package com.learning.blockingnioserver;

import java.io.IOException;

public interface Handler<S, X extends Throwable> {
    void handle(S s) throws X;
}
