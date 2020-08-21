import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


/**
 * Implements the autocompletion of a string using data containing the string.
 *
 * @author Ryan Albertson
 */
public class Autocomplete {


    // Store lexicographically sorted array of Terms.
    private Term[] terms;


    /**
     * Constructs a sorted array of Terms.
     *
     * @param terms Array of Term objects.
     */
    public Autocomplete(Term[] terms) {

        // Check if Terms argument itself is null.
        if (null == terms) {

            throw new IllegalArgumentException("'terms' cannot be null.");
        }

        this.terms = new Term[terms.length];

        // Add given Terms to a temp array.
        for (int i = 0; i < terms.length; i++) {

            // Check for a null Term.
            if (null == terms[i]) {

                throw new IllegalArgumentException("'terms[Term]' cannot be null.");
            }

            this.terms[i] = terms[i];
        }

        // Lexicographically sort the terms by their query string.
        Arrays.sort(this.terms);
    }


    /**
     * Returns an array of all Terms which begin with the given prefix. Array is
     * ordered by descending weight.
     *
     * @param prefix A substring to search for within each Term.
     */
    public Term[] allMatches(String prefix) {

        // Check if prefix argument is null.
        if (prefix == null) {

            throw new IllegalArgumentException("'prefix' cannot be null.");
        }

        Term prefixToTerm = new Term(prefix, 0);

        // Find first occurance of the prefix in the sorted terms.
        int firstIdx = BinarySearchDeluxe.firstIndexOf(terms, prefixToTerm,
                                                       Term.byPrefixOrder(prefix.length()));
        // Prefix wasn't found.
        if (firstIdx == -1) {

            return new Term[0];
        }

        // Find last occurance of the prefix in the sorted terms.
        int lastIdx = BinarySearchDeluxe.lastIndexOf(terms, prefixToTerm,
                                                     Term.byPrefixOrder(prefix.length()));

        int numFoundTerms = (lastIdx + 1) - firstIdx;
        Term[] foundTerms = new Term[numFoundTerms];

        // Copy found terms from original array to the new array.
        int idx = 0;
        for (int i = firstIdx; i <= lastIdx; i++) {

            foundTerms[idx] = terms[i];
            idx++;
        }

        // Sort new array in order by descending # of occurances.
        Arrays.sort(foundTerms, Term.byReverseWeightOrder());

        return foundTerms;
    }


    /**
     * Returns the number of Terms that begin with the given prefix.
     *
     * @param prefix A substring to search for within each Term.
     */
    public int numberOfMatches(String prefix) {

        // Check if prefix argument is null.
        if (prefix == null) {

            throw new IllegalArgumentException("'prefix' cannot be null.");
        }

        Term prefixToTerm = new Term(prefix, 0);

        // Find first occurance of the prefix in the sorted terms.
        int firstIdx = BinarySearchDeluxe.firstIndexOf(terms, prefixToTerm,
                                                       Term.byPrefixOrder(prefix.length()));

        // Prefix wasn't found.
        if (firstIdx == -1) {

            return 0;
        }

        // Find last occurance of the prefix in the sorted terms.
        int lastIdx = BinarySearchDeluxe.lastIndexOf(terms, prefixToTerm,
                                                     Term.byPrefixOrder(prefix.length()));

        return (lastIdx + 1) - firstIdx;
    }


    public static void main(String[] args) {

        // Unit test located at ~/Tests/AutocompleteTest.java


        // Below is a test client taken from the project description.


        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query, weight);
        }

        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
