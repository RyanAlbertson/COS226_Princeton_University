import main.Term;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TermTest {


    private String str1;
    private String str2;
    private String str3;

    private Term term1;
    private Term term2;
    private Term term3;


    @Before
    public void setUp() throws Exception {

        str1 = "THIS is a target.test string";
        str2 = "this is another target.test string";
        str3 = "zzzzzzzzzzzz";

        term1 = new Term(str1, 3);
        term2 = new Term(str2, 1);
        term3 = new Term(str3, 2);
    }


    @Test
    public void testEqual() {

        Term term3 = new Term(str1, 3);
        int result = term1.compareTo(term3);

        assertEquals("Expected to be equal.", result, 0);
    }


    @Test
    public void testLessThan() {

        int result = term1.compareTo(term2);

        assertTrue("Expected to be less than.", result < 0);
    }


    @Test
    public void testGreaterThan() {

        int result = term2.compareTo(term1);

        assertTrue("Expected to be greater than.", result > 0);
    }


    @Test
    public void testByReverseWeightOrder() {

        Term[] terms = new Term[4];
        terms[0] = term1;
        terms[1] = term2;
        terms[2] = term3;
        terms[3] = term2;

        Term[] expectedOrder = new Term[4];
        expectedOrder[0] = term1;
        expectedOrder[1] = term3;
        expectedOrder[2] = term2;
        expectedOrder[3] = term2;

        Arrays.sort(terms, Term.byReverseWeightOrder());

        for (int i = 0; i < 4; i++) {

            assertEquals(expectedOrder[i], terms[i]);
        }
    }


    @Test
    public void testByPrefixOrder() {

        Term[] terms = new Term[4];
        terms[0] = term1;
        terms[1] = term2;
        terms[2] = term3;
        terms[3] = term1;

        Term[] expectedOrder = new Term[4];
        expectedOrder[0] = term1;
        expectedOrder[1] = term1;
        expectedOrder[2] = term2;
        expectedOrder[3] = term3;

        Arrays.sort(terms, Term.byPrefixOrder(4));

        for (int i = 0; i < 4; i++) {
            assertEquals(expectedOrder[i], terms[i]);
        }
    }


    @Test
    public void testToString() {

        String expectedStr = String.format("%d\t%s", term1.getWeight(),
                                           term1.getQuery());

        assertEquals(expectedStr, term1.toString());
    }
}
