package com.learning.multithreadedblockingserver;

public class Util {
    public static int transmogrify(int data) {
        return Character.isLetter(data) ? data ^ ' ' : data;
    }
}
