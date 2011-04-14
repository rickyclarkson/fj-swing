package fj.swing;

import fj.F;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public final class Example1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Example1().start();
            }
        });
    }

    private final JFrame frame = new JFrame();

    Example1() {
        frame.getContentPane().setLayout(new GridLayout());
        final Value<String> text = new Value<String>("Enter a number");
        frame.getContentPane().add(TextFieldW.textField(new JTextField(20)).bind(text).unwrap());
        final ValueView<Boolean> isANumber = text.map(new F<String, Boolean>() {
            @Override
            public Boolean f(String s) {
                try {
                    Integer.parseInt(s);
                    return true;
                } catch (NumberFormatException ignored) {
                    return false;
                }
            }
        });
        frame.getContentPane().add(ButtonW.button(new JButton("Well done")).bindVisibility(isANumber).unwrap());
    }

    void start() {
        frame.pack();
        frame.setVisible(true);
    }
}