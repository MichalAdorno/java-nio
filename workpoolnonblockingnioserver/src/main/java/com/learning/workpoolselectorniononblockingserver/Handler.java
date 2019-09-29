package com.learning.workpoolselectorniononblockingserver;

import java.io.IOException;

public interface Handler<S> {
    void handle(S s) throws IOException;
}
