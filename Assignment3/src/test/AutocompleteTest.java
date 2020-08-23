import main.Autocomplete;
import main.Term;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AutocompleteTest {


    private String[] queries;

    private Term[] terms;

    private Autocomplete autocomplete;


    @Before
    public void setUp() throws Exception {

        queries = new String[7];
        queries[0] = "aa";
        queries[1] = "zz";
        queries[2] = "ff";
        queries[3] = "bb";
        queries[4] = "ee";
        queries[5] = "aacc";
        queries[6] = "aabb";

        terms = new Term[7];
        for (int i = 0; i < 7; i++) {

            terms[i] = new Term(queries[i], i + 1);
        }

        autocomplete = new Autocomplete(terms);
    }


    @Test
    public void allMatches() {

        Term[] results = autocomplete.allMatches("a");

        String[] expectedResults = new String[3];
        expectedResults[0] = "aabb";
        expectedResults[1] = "aacc";
        expectedResults[2] = "aa";

        for (int i = 0; i < expectedResults.length; i++) {

            assertEquals(results[i].getQuery(), expectedResults[i]);
        }
    }


    @Test
    public void numberOfMatches() {

        assertEquals(autocomplete.numberOfMatches("aa"), 3);
    }
}
