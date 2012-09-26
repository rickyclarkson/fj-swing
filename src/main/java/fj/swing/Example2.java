package fj.swing;

import fj.F;
import fj.F2;
import fj.data.Option;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;

public class Example2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Example2().start();
            }
        });
    }

    private final JFrame frame = new JFrame();

    Example2() {
        frame.getContentPane().setLayout(new GridLayout(1, 3));
        Value<Integer> firstNumber = new Value<Integer>(3);
        Value<Integer> secondNumber = new Value<Integer>(4);
        ValueView<Integer> sum = firstNumber.bind(secondNumber, new F2<Integer, Integer, Integer>() {
            @Override
            public Integer f(Integer first, Integer second) {
                return first + second;
            }
        });

        final F<Integer, String> intToString = new F<Integer, String>() {
            @Override
            public String f(Integer integer) {
                return integer.toString();
            }
        };
        final F<String, Option<Integer>> stringToInt = new F<String, Option<Integer>>() {
            @Override
            public Option<Integer> f(String s) {
                try {
                    return Option.some(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    return Option.none();
                }
            }
        };

        JTextField firstNumberField = new JTextField();
        TextFieldW.bind(firstNumberField, firstNumber.map(intToString, stringToInt, "3"));
        frame.getContentPane().add(firstNumberField);

        JTextField secondNumberField = new JTextField();
        TextFieldW.bind(secondNumberField, secondNumber.map(intToString, stringToInt, "4"));
        frame.getContentPane().add(secondNumberField);

        JTextField sumField = new JTextField();
        TextFieldW.bind(sumField, sum.map(intToString));
        frame.getContentPane().add(sumField);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }
}
