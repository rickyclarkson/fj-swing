package fj.swing;

import fj.Effect;
import fj.F;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public final class TextFieldW {
    public static void bind(final JTextComponent component, final ValueView<String> value) {
        component.setText(value.get());
        value.addListener(new Effect<String>() {
            @Override
            public void e(String s) {
                component.setText(s);
            }
        });
    }

    public static void bind(final JTextComponent component, final Value<String> value) {
        component.setText(value.get());
        final boolean[] recursionGuard = {false};

        component.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!recursionGuard[0])
                    try {
                        recursionGuard[0] = true;
                        value.set(component.getText());
                    } finally {
                        recursionGuard[0] = false;
                    }
            }
        });

        value.addListener(new Effect<String>() {
            @Override
            public void e(String s) {
                if (!recursionGuard[0])
                    try {
                        recursionGuard[0] = true;
                        component.setText(s);
                    } finally {
                        recursionGuard[0] = false;
                    }
            }
        });
    }

    public static <A> ValueView<A> map(final JTextComponent component, final F<String, A> f) {
        return new ValueView<A>() {
            @Override
            public A get() {
                return f.f(component.getText());
            }

            @Override
            public void addListener(final Effect<A> listener) {
                component.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        listener.e(f.f(component.getText()));
                    }
                });
            }
        };
    }
}
