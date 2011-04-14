package fj.swing;

import javax.swing.JTextField;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class TextFieldTest {
    private Value<String> value;
    private JTextField field;

    @Before
    public void setUp() {
        value = new Value<String>("foo");
        field = TextFieldW.textField(new JTextField()).bind(value).unwrap();
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
}
