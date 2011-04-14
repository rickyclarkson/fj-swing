package fj.swing;

import fj.Effect;
import fj.F;
import javax.swing.JButton;

public class ButtonW {
    private final JButton wrapped;
    public static final F<ButtonW, Effect<Boolean>> visibility = new F<ButtonW, Effect<Boolean>>() {
        @Override
        public Effect<Boolean> f(final ButtonW buttonW) {
            return new Effect<Boolean>() {
                @Override
                public void e(Boolean b) {
                    buttonW.wrapped.setVisible(b);
                }
            };
        }
    };
    public static ButtonW button(JButton wrapped) {
        return new ButtonW(wrapped);
    }

    private ButtonW(JButton wrapped) {
        this.wrapped = wrapped;
    }

    public <A> ButtonW bind(final F<ButtonW, Effect<A>> effect, ValueView<A> view) {
        effect.f(this).e(view.get());

        view.addListener(new Listener<A>() {
            @Override
            public void act(A a) {
                effect.f(ButtonW.this).e(a);
            }
        });

        return this;
    }

    public JButton unwrap() {
        return wrapped;
    }

    public static ButtonW button(String text) {
        return button(new JButton(text));
    }
}
