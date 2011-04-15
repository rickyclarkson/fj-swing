package fj.swing;

import fj.F;
import fj.data.Option;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueTest {
    @Test
    public void map() {
        Value<String> value = new Value<String>("foo");
        Value<Integer> length = value.map(new F<String, Integer>() {
            @Override
            public Integer f(String s) {
                return s.length();
            }
        }, new F<Integer, Option<String>>() {
            @Override
            public Option<String> f(Integer integer) {
                return integer % 2 == 0 ? Option.some(integer.toString()) : Option.<String>none();
            }
        }, 10);

        assertEquals(10, (int)length.get());
        value.set("hell");
        assertEquals(4, (int)length.get());
        length.set(4);
        assertEquals("4", value.get());
        length.set(11);
        assertEquals("4", value.get());
    }
}
