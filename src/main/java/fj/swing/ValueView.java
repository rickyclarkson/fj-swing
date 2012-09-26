package fj.swing;

import fj.Effect;
import fj.F;

import java.util.List;

public abstract class ValueView<B> {
    public abstract B get();
    public abstract void addListener(Effect<B> listener);

    public final <C> ValueView<C> map(final F<B, C> f) {
        return new ValueView<C>() {
            @Override
            public C get() {
                return f.f(ValueView.this.get());
            }

            @Override
            public void addListener(final Effect<C> cListener) {
                ValueView.this.addListener(new Effect<B>() {
                    @Override
                    public void e(B b) {
                        cListener.e(f.f(b));
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
            public void addListener(final Effect<Boolean> booleanListener) {
                for (ValueView<Boolean> valueView: valueViews)
                    valueView.addListener(new Effect<Boolean>() {
                        @Override
                        public void e(Boolean aBoolean) {
                            booleanListener.e(get());
                        }
                    });
            }
        };
    }
}
