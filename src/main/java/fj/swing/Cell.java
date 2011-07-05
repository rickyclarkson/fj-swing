package fj.swing;

import fj.Effect;
import fj.P;
import fj.P1;

import java.util.ArrayList;
import java.util.List;

public class Cell<T> {
    private P1<T> p;
    private final List<Effect<T>> whenChanged = new ArrayList<Effect<T>>();

    public Cell(P1<T> p) {
        this.p = p;
    }

    public T get() {
        return p._1();
    }

    public void whenChanged(Effect<T> effect) {
        whenChanged.add(effect);
    }

    public void set(T t) {
        p = P.p(t);
        for (Effect<T> effect: whenChanged)
            effect.e(t);
    }
}
