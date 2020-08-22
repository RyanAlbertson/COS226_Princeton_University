package main;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class Outcast {

    // Store copy of given WordNet.
    private final WordNet wordnet;

    /**
     * Constructs a copy of the given {@link WordNet}.
     *
     * @param wordnet {@link WordNet}.
     */
    public Outcast(WordNet wordnet) {

        this.wordnet = wordnet;
    }


    /**
     * Returns the longest common ancestor shared between all nouns in the given
     * array of nouns.
     *
     * @param nouns A set of nouns in {@code wordnet}.
     */
    public String outcast(String[] nouns) {

        int longestDist = 0;
        String outcast = null;

        // Loop through nouns.
        for (int i = 0; i < nouns.length; i++) {

            int currentNounDist = 0;

            // Loop through nouns again to get distances to other nouns.
            for (int j = 0; j < nouns.length; j++) {

                currentNounDist += wordnet.distance(nouns[i], nouns[j]);
            }

            // Update outcast noun if needed.
            if (currentNounDist > longestDist) {
                longestDist = currentNounDist;
                outcast = nouns[i];
            }
        }
        return outcast;
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

            // ShortestCommonAncestor.validate() is throwing exception bc theres a
            // null value somewheres.
        }
    }
}
