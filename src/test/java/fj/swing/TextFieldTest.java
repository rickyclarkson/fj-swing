package fj.swing;

import javax.swing.JTextField;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class TextFieldTest {
    @Test
    public void changeText() {
        final Value<String> value = new Value<String>("foo");
        final JTextField field = TextFieldW.textField(new JTextField()).bind(value).unwrap();
        field.setText("bar");
        assertEquals("The value should be bar, reflecting the change to the textfield", "bar", value.get());
    }

    @Test
    public void changeValue() {
        final Value<String> value = new Value<String>("foo");
        final JTextField field = TextFieldW.textField(new JTextField()).bind(value).unwrap();
        value.set("baz");
        assertEquals("The textfield should be showing baz, reflecting the change to the value", "baz", field.getText());
    }

    @Test
    public void initialValue() {
        final Value<String> value = new Value<String>("foo");
        final JTextField field = TextFieldW.textField(new JTextField()).bind(value).unwrap();
        assertEquals("The textfield should be showing foo, as that is the value bound to it", "foo", field.getText());
    }
}
