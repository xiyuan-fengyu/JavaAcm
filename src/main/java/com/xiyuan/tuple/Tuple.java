package com.xiyuan.tuple;

public class Tuple <T1, T2> {

    public T1 t1;
    public T2 t2;

    public Tuple(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() +
            " {\n" +
            "t1=" + t1 + "\n" +
            "t2=" + t2 + "\n" +
            "}";
    }

}