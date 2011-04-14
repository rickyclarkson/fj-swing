package fj.swing;

import fj.Effect;
import fj.F;
import javax.swing.JTextArea;

public class TextAreaW {
    private final JTextArea wrapped;
    public static final F<TextAreaW, Effect<Boolean>> visibility = new F<TextAreaW, Effect<Boolean>>() {
        @Override
        public Effect<Boolean> f(final TextAreaW textAreaW) {
            return new Effect<Boolean>() {
                @Override
                public void e(Boolean b) {
                    textAreaW.wrapped.setVisible(b);
                }
            };
        }
    };

    public static final F<TextAreaW, Effect<Boolean>> editability = new F<TextAreaW, Effect<Boolean>>() {
        @Override
        public Effect<Boolean> f(final TextAreaW textAreaW) {
            return new Effect<Boolean>() {
                @Override
                public void e(Boolean b) {
                    textAreaW.wrapped.setEditable(b);
                }
            };
        }
    };

    public TextAreaW(JTextArea wrapped) {
        this.wrapped = wrapped;
    }

    public static TextAreaW textArea(JTextArea wrapped) {
        return new TextAreaW(wrapped);
    }

    public <A> TextAreaW bind(final F<TextAreaW, Effect<A>> effect, ValueView<A> view) {
        effect.f(this).e(view.get());

        view.addListener(new Listener<A>() {
            @Override
            public void act(A a) {
                effect.f(TextAreaW.this).e(a);
            }
        });

        return this;
    }

    public JTextArea unwrap() {
        return wrapped;
    }
}
