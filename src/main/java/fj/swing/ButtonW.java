package fj.swing;

import javax.swing.JButton;

public class ButtonW {
    private final JButton wrapped;

    public static ButtonW button(JButton wrapped) {
        return new ButtonW(wrapped);
    }

    private ButtonW(JButton wrapped) {
        this.wrapped = wrapped;
    }

    public ButtonW bindVisibility(ValueView<Boolean> valueView) {
        wrapped.setVisible(valueView.get());
        valueView.addListener(new Listener<Boolean>() {
            @Override
            public void act(Boolean b) {
                wrapped.setVisible(b);
            }
        });
        return this;
    }

    public JButton unwrap() {
        return wrapped;
    }
}
