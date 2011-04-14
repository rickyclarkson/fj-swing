package fj.swing;

import fj.F;
import java.util.List;

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

    public static ValueView<Boolean> all(final List<ValueView<Boolean>> valueViews) {
        return new ValueView<Boolean>() {
            @Override
            public Boolean get() {
                for (ValueView<Boolean> valueView: valueViews)
                    if (!valueView.get())
                        return false;

                return true;
            }

            @Override
            public void addListener(final Listener<Boolean> booleanListener) {
                for (ValueView<Boolean> valueView: valueViews)
                    valueView.addListener(new Listener<Boolean>() {
                        @Override
                        public void act(Boolean aBoolean) {
                            booleanListener.act(get());
                        }
                    });
            }
        };
    }
}
