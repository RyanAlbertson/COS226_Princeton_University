import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Builds a {@code WordNet} which implements methods to query the association
 * between nouns.
 *
 * @author Ryan Albertson
 */
public class WordNet {


    // Synset indices are mapped to their corresponding synsets.
    private final HashMap<Integer, String> synsets;

    // Individual nouns are mapped to the indices of all synsets they're within.
    private final HashMap<String, LinkedList<Integer>> nouns;

    // Directed acyclic graph of hyponyms and hypernyms.
    private final ShortestCommonAncestor shortestCommonAncestor;


    /**
     * Constructs a {@code WordNet} by reading in given noun relationships and storing them
     * in data structures such that the nouns' relationships can be queried.
     *
     * @param synsets   CSV file name containing synsets of nouns.
     * @param hypernyms CSV file name containing hypernyms of the same nouns.
     * @throws IllegalArgumentException If {@code synsets} or {@code hypernyms} is null.
     */
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null) throw new IllegalArgumentException("'synsets' is null.");
        if (hypernyms == null) throw new IllegalArgumentException("'hypernyms' is null.");

        this.synsets = new HashMap<Integer, String>();
        nouns = new HashMap<String, LinkedList<Integer>>();

        // Read synsets and individual nouns into their corresponding hash maps.
        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {

            // Read synsets and their indices.
            StringTokenizer st1 = new StringTokenizer(synsetsIn.readLine(), ",");
            int synsetIdx = Integer.parseInt(st1.nextToken());
            String synset = st1.nextToken();
            this.synsets.put(synsetIdx, synset);

            // Read individual nouns within each synset.
            StringTokenizer st2 = new StringTokenizer(synset, " ");
            while (st2.hasMoreTokens()) {

                String noun = st2.nextToken();
                nouns.getOrDefault(noun, new LinkedList<Integer>()).add(synsetIdx);
            }
        }
        synsetsIn.close();

        // Read synset-hypernym edges into a digraph (not necessarily a DAG).
        Digraph digraph = new Digraph(this.synsets.size());
        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {

            // Read synset indices
            StringTokenizer st = new StringTokenizer(hypernymsIn.readLine(), ",");
            int synsetIdx = Integer.parseInt(st.nextToken());

            // Read corresponding hypernyms.
            while (st.hasMoreTokens()) {

                // Create edges between synsets and the hypernym(s) they're in.
                int hypernymIdx = Integer.parseInt(st.nextToken());
                digraph.addEdge(synsetIdx, hypernymIdx);
            }
        }
        hypernymsIn.close();

        shortestCommonAncestor = new ShortestCommonAncestor(digraph);
    }


    /**
     * Returns an iterable of all nouns in this {@code WordNet}.
     */
    public Iterable<String> nouns() {

        return nouns.keySet();
    }


    /**
     * Returns true if given string is a noun in this {@code WordNet}, false otherwise.
     *
     * @param word String to be checked.
     * @throws IllegalArgumentException If {@code word} is null.
     */
    public boolean isNoun(String word) {

        if (word == null) throw new IllegalArgumentException("'word' is null.");

        return nouns.containsKey(word);
    }


    /**
     * Returns the synset which is the shortest common ancestor of the 2 given nouns.
     *
     * @param noun1 Returned synset contains this noun.
     * @param noun2 Returned synset contains this noin.
     * @throws IllegalArgumentException If either {@code noun1} or {@code noun2} is
     *                                  null or if either is not a noun in this {@code WordNet}.
     */
    public String sca(String noun1, String noun2) {

        validate(noun1);
        validate(noun2);

        // Get lists of all synsets that each corresponding noun is within.
        List<Integer> noun1Synsets = nouns.get(noun1);
        List<Integer> noun2Synsets = nouns.get(noun2);

        // Calculate synset index of shortest common ancestor.
        int ancestorIdx = shortestCommonAncestor.ancestorSubset(noun1Synsets, noun2Synsets);

        return synsets.get(ancestorIdx);
    }


    /**
     * Returns distance of the shortest ancestral path between the given nouns.
     *
     * @param noun1 Shortest ancestral path considers this noun.
     * @param noun2 Shortest ancestral path considers this noun.
     * @throws IllegalArgumentException If either {@code noun1} or {@code noun2} is
     *                                  null or if either is not a noun in this {@code WordNet}.
     */
    public int distance(String noun1, String noun2) {

        validate(noun1);
        validate(noun2);

        // Get lists of all synsets that each corresponding noun is within.
        List<Integer> noun1Synsets = nouns.get(noun1);
        List<Integer> noun2Synsets = nouns.get(noun2);

        return shortestCommonAncestor.lengthSubset(noun1Synsets, noun2Synsets);
    }


    /**
     * Checks that given parameter is valid for use in {@link #sca(String, String)}
     * and {@link #distance(String, String)}.
     *
     * @param noun Noun to be checked.
     */
    private void validate(String noun) {

        if (noun == null) throw new IllegalArgumentException();
        if (!isNoun(noun)) throw new IllegalArgumentException();
    }


    public static void main(String[] args) {

        // Unit test located at ~/Tests/WordNet_Test.java
    }
}







































