package fj.swing;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class TextFieldW {
    private final JTextField wrapped;
    
    private TextFieldW(JTextField wrapped) {
        this.wrapped = wrapped;
    }

    public static TextFieldW textField(JTextField wrapped) {
        return new TextFieldW(wrapped);
    }

    public TextFieldW bind(final Value<String> value) {
        wrapped.setText(value.get());
        final boolean[] recursionGuard = {false};

        wrapped.getDocument().addDocumentListener(new DocumentListener() {
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
                        value.set(wrapped.getText());
                    } finally {
                        recursionGuard[0] = false;
                    }
            }
        });

        value.addListener(new Listener<String>() {
            @Override
            public void act(String s) {
                if (!recursionGuard[0])
                    try {
                        recursionGuard[0] = true;
                        wrapped.setText(s);
                    } finally {
                        recursionGuard[0] = false;
                    }
            }
        });

        return this;
    }

    public JTextField unwrap() {
        return wrapped;
    }
}
