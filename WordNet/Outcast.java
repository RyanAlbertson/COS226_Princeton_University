import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class Outcast {


    /**
     * @param wordnet
     */
    public Outcast(WordNet wordnet) {


    }


    /**
     * @param nouns
     * @return
     */
    public String outcast(String[] nouns) {


    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

        // Below is a provided test client.

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
