package com.learning.executorserviceblockingserver;

import java.io.IOException;

public interface Handler<S> {
    void handle(S s) throws IOException;
}
