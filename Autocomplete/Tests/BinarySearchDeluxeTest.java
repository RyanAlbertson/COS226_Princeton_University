import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinarySearchDeluxeTest {


    private String[] keys;


    @Before
    public void setUp() throws Exception {

        keys = new String[7];
        keys[0] = "ab";
        keys[1] = "ab";
        keys[2] = "ab";
        keys[3] = "bb";
        keys[4] = "bb";
        keys[5] = "bb";
        keys[6] = "cc";
    }


    @Test
    public void testFirstIndexOf() {

        int resultIdx1 = BinarySearchDeluxe.firstIndexOf(keys, "bb", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx1 = 3;
        assertEquals(expectedIdx1, resultIdx1);

        int resultIdx2 = BinarySearchDeluxe.firstIndexOf(keys, "ab", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx2 = 0;
        assertEquals(expectedIdx2, resultIdx2);

        int resultIdx3 = BinarySearchDeluxe.firstIndexOf(keys, "A", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx3 = -1;
        assertEquals(expectedIdx3, resultIdx3);

        int resultIdx4 = BinarySearchDeluxe.firstIndexOf(keys, "zz", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx4 = -1;
        assertEquals(expectedIdx4, resultIdx4);

        int resultIdx5 = BinarySearchDeluxe.firstIndexOf(keys, "ba", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx5 = -1;
        assertEquals(expectedIdx5, resultIdx5);

    }


    @Test
    public void testLastIndexOf() {

        int resultIdx1 = BinarySearchDeluxe.lastIndexOf(keys, "bb", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx1 = 5;
        assertEquals(expectedIdx1, resultIdx1);

        int resultIdx2 = BinarySearchDeluxe.lastIndexOf(keys, "ab", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx2 = 2;
        assertEquals(expectedIdx2, resultIdx2);

        int resultIdx3 = BinarySearchDeluxe.lastIndexOf(keys, "A", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx3 = -1;
        assertEquals(expectedIdx3, resultIdx3);

        int resultIdx4 = BinarySearchDeluxe.lastIndexOf(keys, "zz", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx4 = -1;
        assertEquals(expectedIdx4, resultIdx4);

        int resultIdx5 = BinarySearchDeluxe.lastIndexOf(keys, "ba", String.CASE_INSENSITIVE_ORDER);
        int expectedIdx5 = -1;
        assertEquals(expectedIdx5, resultIdx5);
    }
}
