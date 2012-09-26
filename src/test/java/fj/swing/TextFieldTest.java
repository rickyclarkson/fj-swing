package fj.swing;

import fj.F;
import fj.F2;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JTextField;

import static org.junit.Assert.assertEquals;

public final class TextFieldTest {
    private Value<String> value;
    private JTextField field;

    @Before
    public void setUp() {
        value = new Value<String>("foo");
        field = new JTextField();
        TextFieldW.bind(field, value);
    }

    @Test
    public void changeText() {
        field.setText("bar");
        assertEquals("The value should be bar, reflecting the change to the textfield", "bar", value.get());
    }

    @Test
    public void changeValue() {
        value.set("baz");
        assertEquals("The textfield should be showing baz, reflecting the change to the value", "baz", field.getText());
    }

    @Test
    public void initialValue() {
        assertEquals("The textfield should be showing foo, as that is the value bound to it", "foo", field.getText());
    }

    @Test
    public void binding() {
        Value<String> surname = new Value<String>("spam");
        ValueView<String> allName = value.bind(surname, new F2<String, String, String>() {
            @Override
            public String f(String s, String s1) {
                return s + " " + s1;
            }
        });
        assertEquals("The name should be foo spam", "foo spam", allName.get());
        value.set("bar");
        assertEquals("The name should be bar spam", "bar spam", allName.get());
        surname.set("eggs");
        assertEquals("The name should be bar eggs", "bar eggs", allName.get());
    }

    @Test
    public void bindingToAValueView() {
        ValueView<String> reverse = value.map(new F<String, String>() {
            @Override
            public String f(String s) {
                return new StringBuilder(s).reverse().toString();
            }
        });
        JTextField textField = new JTextField();
        TextFieldW.bind(textField, reverse);
        value.set("spam");
        assertEquals("The textfield should contain the text 'maps'.", "maps", textField.getText());
    }

    @Test
    public void mapping() {
        value.set("5");

        ValueView<Integer> intView = TextFieldW.map(field, new F<String, Integer>() {
            @Override
            public Integer f(String s) {
                return Integer.parseInt(s);
            }
        });
        
        assertEquals("intView should contain 5", 5, (long)intView.get());
        value.set("66");
        assertEquals("intView should contain 66", 66, (long)intView.get());
    }
}
