package fj.swing;

import fj.P;
import fj.P1;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CellTest {
    @Test
    public void retainValues() {
        Cell<Integer> a = new Cell<Integer>(P.p(5));
        assertTrue(a.get() == 5);
    }

    @Test
    public void transferValues() {
        final Cell<Integer> a = new Cell<Integer>(P.p(5));
        Cell<Integer> doubleA = new Cell<Integer>(new P1<Integer>() {
            @Override
            public Integer _1() {
                return a.get() * 2;
            }
        });

        assertTrue(doubleA.get() == 10);
    }
}
