package fj.swing;

import fj.Effect;
import fj.P;
import fj.P1;
import fj.P2;

import java.util.*;

public class Cell<T> {
    private P1<T> p;
    private final List<Effect<T>> whenChanged = new ArrayList<Effect<T>>();
    private static final List<P2<Cell<?>, Cell<?>>> dependsOn = new ArrayList<P2<Cell<?>, Cell<?>>>();

    private static final ThreadLocal<Stack<Cell<?>>> recording = new ThreadLocal<Stack<Cell<?>>>();

    public Cell(P1<T> p) {
        this.p = p;
        recordDependencies();
    }

    private void recordDependencies() {
        if (recording.get() == null) {
            recording.set(new Stack<Cell<?>>());
        }
        recording.get().push(this);
        get();
        if (!recording.get().pop().equals(this))
            throw null;
    }

    public T get() {
        Stack<Cell<?>> r = recording.get();
        if (r != null && !r.empty() && !r.peek().equals(this)) {
            dependsOn.add(P.<Cell<?>, Cell<?>>p(r.peek(), this));
        }
        return p._1();
    }

    public void whenChanged(Effect<T> effect) {
        whenChanged.add(effect);
    }

    public void set(T t) {
        p = P.p(t);
        for (Effect<T> effect: whenChanged)
            effect.e(t);
        for (P2<Cell<?>, Cell<?>> d: dependsOn)
            if (d._2().equals(this))
                d._1().evaluate();
    }

    private void evaluate() {
        T t = get();
        for (Effect<T> effect: whenChanged)
            effect.e(t);
    }
}
