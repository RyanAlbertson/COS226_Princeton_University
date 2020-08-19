import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 */
public class WordNet {


    // Synset indices are mapped to their corresponding synsets.
    private final HashMap<Integer, String> synsets;

    // Individual nouns are mapped to the indices of all synsets they're within.
    private final HashMap<String, LinkedList<Integer>> nouns;

    // Directed acyclic graph of hyponyms and hypernyms.
    private final ShortestCommonAncestor shortestCommonAncestor;


    /**
     * Constructs a WordNet by reading in given noun relationships and storing them
     * in data structures such that the nouns' relationships can be queried.
     *
     * @param synsets   CSV file containing synsets of nouns.
     * @param hypernyms CSV file containing hypernyms of the same nouns.
     */
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null) throw new IllegalArgumentException("'synsets' is null.");
        if (hypernyms == null) throw new IllegalArgumentException("'hypernyms' is null.");

        
    }


    // all WordNet nouns
    public Iterable<String> nouns() {


    }


    // is the word a WordNet noun?
    public boolean isNoun(String word) {


    }


    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {


    }


    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {


    }


    public static void main(String[] args) {

        // Unit test located at ~/Tests/WordNet_Test.java
    }
}
