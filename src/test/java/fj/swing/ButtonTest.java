package fj.swing;

import fj.F;
import javax.swing.JButton;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ButtonTest {
    private Value<String> value;
    private JButton button;

    @Before
    public void setUp() {
        value = new Value<String>("foo");
        button = ButtonW.button(new JButton()).bind(ButtonW.visibility, value.map(new F<String, Boolean>() {
            @Override
            public Boolean f(String s) {
                return s.equals("bar");
            }
        })).unwrap();
    }

    @Test
    public void changeValue() {
        value.set("bar");
        assertTrue("The button should be visible", button.isVisible());
        value.set("baz");
        assertFalse("The button should not be visible", button.isVisible());
    }

    @Test
    public void initialState() {
        assertFalse("The button should not be visible", button.isVisible());
    }

    @Test
    public void convenienceMethod() {
        assertEquals("The generated button should have foo as its text", "foo", ButtonW.button("foo").unwrap().getText());
    }
}
