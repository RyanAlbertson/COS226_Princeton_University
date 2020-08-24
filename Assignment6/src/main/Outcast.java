package main;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Finds a 'outcast' of a set of nouns. An outcast of some noun can be thought of
 * as an antonym that's limited to the range of the given nouns.
 *
 * @author Ryan Albertson
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
     * Test client.
     */
    public static void main(String[] args) {

        // Below is a provided test client.

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));

            // TODO somehow nouns aren't being added to wordnet properly.


            // java-algs4 main/Outcast resources/synsets.txt resources/hypernyms.txt resources/outcast5.txt resources/outcast8.txt resources/outcast11.txt
        }
    }
}
