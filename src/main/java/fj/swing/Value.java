package fj.swing;

import java.util.ArrayList;
import java.util.List;

public final class Value<A> {
    private A a;
    private final List<Listener<A>> listeners = new ArrayList<Listener<A>>();

    public Value(A a) {
        this.a = a;
    }

    public A get() {
        return a;
    }

    public void set(A a) {
        this.a = a;
        for (Listener<A> listener: listeners)
            listener.act(a);
    }

    public void addListener(Listener<A> listener) {
        listeners.add(listener);
    }
}
