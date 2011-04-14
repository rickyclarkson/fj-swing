package fj.swing;

import javax.swing.JTextArea;

public class TextAreaW {
    private final JTextArea wrapped;

    public TextAreaW(JTextArea wrapped) {
        this.wrapped = wrapped;
    }

    public static TextAreaW textArea(JTextArea wrapped) {
        return new TextAreaW(wrapped);
    }

    public TextAreaW bindVisibility(ValueView<Boolean> view) {
        wrapped.setVisible(view.get());
        
        view.addListener(new Listener<Boolean>() {
            @Override
            public void act(Boolean aBoolean) {
                wrapped.setVisible(aBoolean);
            }
        });

        return this;
    }

    public JTextArea unwrap() {
        return wrapped;
    }
}
