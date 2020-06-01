package com.patterncat.rpc.common.util;

public class Index {
    private int index;

    public Index() {

    }

    public Index(int index) {
        this.index = index;
    }

    public int getAndAdd() {
        return index++;
    }

    public int add() {
        return index++;
    }

    public int add(int value) {
        return index += value;
    }

    public int get() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
