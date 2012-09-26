package fj.swing;

import fj.Effect;
import fj.F;

import java.awt.Component;

public class ButtonW {
    public static final F<Component, Effect<Boolean>> visibility = new F<Component, Effect<Boolean>>() {
        @Override
        public fj.Effect<Boolean> f(final Component component) {
            return new Effect<Boolean>() {
                @Override
                public void e(Boolean b) {
                    component.setVisible(b);
                }
            };
        }
    };

    public static <A, C extends Component> void bind(final C component, final F<? super C, Effect<A>> effect, ValueView<A> view) {
        effect.f(component).e(view.get());

        view.addListener(new Effect<A>() {
            @Override
            public void e(A a) {
                effect.f(component).e(a);
            }
        });
    }
}
