import main.Deque;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DequeTest {


    private Deque<Integer> deque;


    @Before
    public void setUp() throws Exception {

        deque = new Deque<Integer>();
    }


    @Test
    public void isEmpty1() {

        assertTrue(deque.isEmpty());

        Integer val1 = 31;
        deque.addFirst(val1);
        assertFalse(deque.isEmpty());
    }


    @Test
    public void size1() {

        int expectedSize1 = 0;
        assertEquals(expectedSize1, deque.size());

        Integer val = 23;
        deque.addFirst(val);
        int expectedSize2 = 1;
        assertEquals(expectedSize2, deque.size());
    }


    @Test
    public void addFirst() {

        Integer val1 = 41;
        deque.addFirst(val1);
        int expectedSize1 = 1;
        assertEquals(expectedSize1, deque.size());

        Integer val2 = 42;
        deque.addFirst(val2);
        int expectedSize2 = 2;
        assertEquals(expectedSize2, deque.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void addFirst_IllegalArgument() {

        deque.addFirst(null);
    }


    @Test
    public void addLast() {

        Integer val1 = 94;
        deque.addLast(val1);
        int expectedSize1 = 1;
        assertEquals(expectedSize1, deque.size());

        Integer val2 = 95;
        deque.addLast(val2);
        int expectedSize2 = 2;
        assertEquals(expectedSize2, deque.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void addLast_IllegalArgument() {

        deque.addLast(null);
    }


    @Test
    public void removeFirst() {

        Integer val1 = 57;
        deque.addLast(val1);
        deque.removeFirst();
        assertTrue(deque.isEmpty());

        Integer val2 = 58;
        deque.addFirst(val2);
        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }


    @Test(expected = NoSuchElementException.class)
    public void removeFirst_NoSuchElement() {

        deque.removeFirst();
    }


    @Test
    public void removeLast() {

        Integer val1 = 11;
        deque.addLast(val1);
        deque.removeLast();
        assertTrue(deque.isEmpty());

        Integer val2 = 12;
        deque.addFirst(val2);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }


    @Test(expected = NoSuchElementException.class)
    public void removeLast_NoSuchElement() {

        deque.removeLast();
    }


    @Test
    public void iterator() {

        int[] input = { 1, 3, 8, 17, 455 };
        int[] output = new int[5];

        for (int i = 0; i < input.length; i++) {

            deque.addFirst(input[i]);
        }

        int idx = 1;
        for (Iterator<Integer> it = deque.iterator(); it.hasNext(); ) {

            Integer i = it.next();

            output[output.length - idx] = i;
            idx++;
        }

        for (int i = 0; i < input.length; i++) {

            assertEquals(input[i], output[i]);
        }
    }
}
