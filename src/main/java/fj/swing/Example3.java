package fj.swing;

import fj.F;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import static fj.swing.ValueView.all;

public class Example3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Example3().start();
            }
        });
    }

    private final JFrame frame = new JFrame();

    public Example3() {
        frame.getContentPane().setLayout(new GridLayout(1, 2));

        List<ValueView<Boolean>> correct = new ArrayList<ValueView<Boolean>>();

        JPanel questionPanel = new JPanel(new GridLayout(3, 2));

        for (int a = 0; a < 3; a++) {
            final Question question = new Question();
            questionPanel.add(new JLabel(question.toString()));
            JTextField field = new JTextField();
            ValueView<Boolean> c = TextFieldW.map(field, new F<String, Boolean>() {
                @Override
                public Boolean f(String s) {
                    return question.getAnswer().equals(s);
                }
            });
            correct.add(c);
            questionPanel.add(field);
        }

        frame.getContentPane().add(questionPanel);
        JTextArea email = new JTextArea("Now you have proved you're not drunk,\nyou can send emails.");
        ButtonW.bind(email, ButtonW.visibility, all(correct));
        frame.getContentPane().add(email);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

    private class Question {
        private final int x = (int) (Math.random() * 10);
        private final int y = (int) (Math.random() * 10);

        public String getAnswer() {
            return String.valueOf(x + y);
        }

        public String toString() {
            return x + " + " + y + " = ";
        }
    }
}
