package fj.swing;

import fj.P1;

public class Cell<T> {
    private final P1<T> p;

    public Cell(P1<T> p) {
        this.p = p;
    }

    public T get() {
        return p._1();
    }
}
