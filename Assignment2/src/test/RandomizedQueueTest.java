import main.RandomizedQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RandomizedQueueTest {


    private RandomizedQueue<Integer> randomizedQueue;


    @Before
    public void setUp() throws Exception {

        randomizedQueue = new RandomizedQueue<Integer>();
    }


    @Test
    public void isEmpty2() {

        assertTrue(randomizedQueue.isEmpty());

        randomizedQueue.enqueue(53);
        assertFalse(randomizedQueue.isEmpty());
    }


    @Test
    public void size2() {

        int expectedSize1 = 0;
        assertEquals(expectedSize1, randomizedQueue.size());

        randomizedQueue.enqueue(44);
        int expectedSize2 = 1;
        assertEquals(expectedSize2, randomizedQueue.size());
    }


    @Test
    public void enqueue() {

        Integer val = 71;
        randomizedQueue.enqueue(val);
        assertEquals(val, randomizedQueue.sample());

        randomizedQueue.enqueue(88);
        int expectedSize = 2;
        assertEquals(expectedSize, randomizedQueue.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void enqueue_IllegalArgument() {

        randomizedQueue.enqueue(null);
    }


    @Test
    public void dequeue() {

        randomizedQueue.enqueue(22);
        randomizedQueue.enqueue(31);

        randomizedQueue.dequeue();
        int expectedSize1 = 1;
        assertEquals(expectedSize1, randomizedQueue.size());

        randomizedQueue.dequeue();
        int expectedSize2 = 0;
        assertEquals(expectedSize2, randomizedQueue.size());
    }


    @Test(expected = NoSuchElementException.class)
    public void deque_NoSuchElement() {

        randomizedQueue.dequeue();
    }


    @Test
    public void sample() {

        Integer val = 93;
        randomizedQueue.enqueue(val);
        assertEquals(val, randomizedQueue.sample());
    }


    @Test(expected = NoSuchElementException.class)
    public void sample_NoSuchElement() {

        randomizedQueue.sample();
    }


    @Test
    public void iterator() {

        int[] input = { 1, 3, 8, 17, 455 };
        int[] output = new int[5];

        for (int i = 0; i < input.length; i++) {

            randomizedQueue.enqueue(input[i]);
        }

        int idx = 0;
        for (Iterator<Integer> it = randomizedQueue.iterator(); it.hasNext(); ) {

            Integer i = it.next();
            output[idx] = i;
            idx++;

            //System.out.println(i);
        }

        for (int i = 0; i < input.length; i++) {

            assertFalse(Arrays.binarySearch(input, output[i]) < 0);
        }
    }


    @Test(expected = UnsupportedOperationException.class)
    public void iterator_UnsupportedOperation() {

        int[] input = { 1, 3, 8, 17, 455 };

        for (int i = 0; i < input.length; i++) {

            randomizedQueue.enqueue(input[i]);
        }

        for (Iterator<Integer> it = randomizedQueue.iterator(); it.hasNext(); ) {

            it.remove();
        }
    }
}
