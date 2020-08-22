import main.WordNet;
import org.junit.Before;
import org.junit.Test;

public class WordNet_Test {

    private WordNet wordnet;

    @Before
    public void setUp() throws Exception {

        String synsets = "synsets.txt";
        String hypernyms = "hypernyms.txt";
        wordnet = new WordNet(synsets, hypernyms);
    }


    @Test
    public void nouns_test() {


    }


    @Test
    public void isNoun_test() {


    }


    @Test
    public void sca_test() {


    }


    @Test
    public void distance_test() {


    }
}
