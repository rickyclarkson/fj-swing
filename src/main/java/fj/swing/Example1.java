package fj.swing;

import fj.F;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;

import static fj.swing.ButtonW.visibility;

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
        JTextField enterANumber = new JTextField("Enter a number");
        TextFieldW.bind(enterANumber, text);
        frame.getContentPane().add(enterANumber);
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
        JButton button = new JButton("Well done");
        ButtonW.bind(button, visibility, isANumber);
        frame.getContentPane().add(button);
    }

    void start() {
        frame.pack();
        frame.setVisible(true);
    }
}
