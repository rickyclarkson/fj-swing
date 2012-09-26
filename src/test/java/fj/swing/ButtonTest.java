package fj.swing;

import fj.F;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JButton;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ButtonTest {
    private Value<String> value;
    private JButton button;

    @Before
    public void setUp() {
        value = new Value<String>("foo");
        button = new JButton();
        ButtonW.bind(button, ButtonW.visibility, value.map(new F<String, Boolean>() {
            @Override
            public Boolean f(String s) {
                return s.equals("bar");
            }
        }));
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
}
