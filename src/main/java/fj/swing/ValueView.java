package fj.swing;

import fj.F;

public abstract class ValueView<B> {
    public abstract B get();
    public abstract void addListener(Listener<B> listener);

    public final <C> ValueView<C> map(final F<B, C> f) {
        return new ValueView<C>() {
            @Override
            public C get() {
                return f.f(ValueView.this.get());
            }

            @Override
            public void addListener(final Listener<C> cListener) {
                ValueView.this.addListener(new Listener<B>() {
                    @Override
                    public void act(B b) {
                        cListener.act(f.f(b));
                    }
                });
            }
        };
    }
}
