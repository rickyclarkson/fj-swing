package fj.swing;

import fj.F;
import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueViewTest {
    @Test
    public void map() {
        Value<String> value = new Value<String>("hello");
        ValueView<Integer> length = value.map(new F<String, Integer>() {
            @Override
            public Integer f(String s) {
                return s.length();
            }
        });
        ValueView<Integer> lengthSquared = length.map(new F<Integer, Integer>() {
            @Override
            public Integer f(Integer integer) {
                return integer * integer;
            }
        });

        assertEquals((int)lengthSquared.get(), 25);
        value.set("hello again");
        assertEquals((int)lengthSquared.get(), 121);
    }

    @Test
    public void all() {
        Value<Integer> value = new Value<Integer>(0);
        ValueView<Boolean> fizz = value.map(new F<Integer, Boolean>() {
            @Override
            public Boolean f(Integer integer) {
                return integer % 3 == 0;
            }
        });

        ValueView<Boolean> buzz = value.map(new F<Integer, Boolean>() {
            @Override
            public Boolean f(Integer integer) {
                return integer % 5 == 0;
            }
        });

        ValueView<Boolean> all = ValueView.all(Arrays.asList(fizz, buzz));

        int fizzBuzzes = 0;
        for (int a = 1; a < 100; a++) {
            value.set(a);
            if (all.get())
                fizzBuzzes++;
        }

        assertEquals(6, fizzBuzzes);
    }
}
