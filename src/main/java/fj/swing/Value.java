package fj.swing;

import fj.Effect;
import fj.F;
import fj.F2;
import fj.data.Option;

import java.util.ArrayList;
import java.util.List;

public final class Value<A> {
    private A a;
    private final List<Effect<A>> listeners = new ArrayList<Effect<A>>();

    public Value(A a) {
        this.a = a;
    }

    public A get() {
        return a;
    }

    public void set(A a) {
        this.a = a;
        for (Effect<A> listener: listeners)
            listener.e(a);
    }

    public void addListener(Effect<A> listener) {
        listeners.add(listener);
    }

    public <B> ValueView<B> map(final F<A, B> f) {
        return new ValueView<B>() {
            @Override
            public B get() {
                return f.f(Value.this.get());
            }

            @Override
            public void addListener(final Effect<B> listener) {
                listeners.add(new Effect<A>() {
                    @Override
                    public void e(A a) {
                        listener.e(f.f(a));
                    }
                });
            }
        };
    }

    public <B> Value<B> map(final F<A, B> aToB, final F<B, Option<A>> bToA, B defaultValue) {
        final Value<B> result = new Value<B>(defaultValue);
        final boolean[] recursionGuard = {false};

        addListener(new Effect<A>() {
            @Override
            public void e(A a) {
                if (!recursionGuard[0]) {
                    recursionGuard[0] = true;
                    try {
                        result.set(aToB.f(a));
                    } finally {
                        recursionGuard[0] = false;
                    }
                }
            }
        });
        result.addListener(new Effect<B>() {
            @Override
            public void e(B b) {
                if (!recursionGuard[0]) {
                    recursionGuard[0] = true;
                    try {
                        for (A a: bToA.f(b))
                            set(a);
                    } finally {
                        recursionGuard[0] = false;
                    }
                }
            }
        });
        return result;
    }

    public <B, C> ValueView<C> bind(final Value<B> other, final F2<A, B, C> f2) {
        return new ValueView<C>() {
            @Override
            public C get() {
                return f2.f(Value.this.get(), other.get());
            }

            @Override
            public void addListener(final Effect<C> cListener) {
                Value.this.addListener(new Effect<A>() {
                    @Override
                    public void e(A a) {
                        cListener.e(f2.f(a, other.get()));
                    }
                });

                other.addListener(new Effect<B>() {
                    @Override
                    public void e(B b) {
                        cListener.e(f2.f(Value.this.get(), b));
                    }
                });
            }
        };
    }
}
