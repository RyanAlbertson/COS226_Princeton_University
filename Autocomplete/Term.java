import java.util.Comparator;

/**
 * Implements storage and comparison of query strings, for use in autocomplete.
 *
 * @author Ryan Albertson
 */
public class Term implements Comparable<Term> {


    // Store the given query string.
    private String query;

    // Store the weight of the given query string.
    private long weight;


    /**
     * Constructs a Term from a given query string and its corresponding weight.
     *
     * @param query  A query string.
     * @param weight Measures the frequency of the query string.
     * @throws IllegalArgumentException If query is null or weight < 0.
     */
    public Term(String query, long weight) {

        // Check that arguments satisfy restrictions.
        if (query == null || weight < 0) {

            throw new IllegalArgumentException("There's an iilegal argument.");
        }

        this.query = query;
        this.weight = weight;
    }


    /**
     * Implements a comparator that orders the weights of 2 Terms. A Term is greater
     * than another if its weight is lower.
     */
    private static class ReverseWeightOrder implements Comparator<Term> {


        public int compare(Term term1, Term term2) {

            return Long.compare(term2.weight, term1.weight);
        }
    }


    /**
     * Returns a comparator that orders Term objects using weight and sorts
     * them in descending order.
     */
    public static Comparator<Term> byReverseWeightOrder() {

        return new ReverseWeightOrder();
    }


    /**
     * Implements a comparator that lexicographically compares a prefixed number of
     * characters from the beginning of 2 Terms.
     */
    private static class PrefixOrder implements Comparator<Term> {


        // Store prefix length.
        private int r;


        // Constructs an instance of a prefix order comparison.
        private PrefixOrder(int r) {

            this.r = r;
        }


        public int compare(Term term1, Term term2) {

            // The prefix strings to be compared.
            String term1Prefix;
            String term2Prefix;

            // If term1 is shorter than the prefix.
            if (term1.query.length() < r) {

                term1Prefix = term1.query;
            }
            else {

                term1Prefix = term1.query.substring(0, r);
            }

            // If term2 is shorter than the prefix.
            if (term2.query.length() < r) {

                term2Prefix = term2.query;
            }
            else {

                term2Prefix = term2.query.substring(0, r);
            }

            return term1Prefix.compareTo(term2Prefix);
        }
    }


    /**
     * Returns a comparator that orders Term objects using a prefixed number of
     * characters starting from the beginning of each Term. Terms are ordered in
     * lexicographical order.
     *
     * @param r Length of prefix.
     */
    public static Comparator<Term> byPrefixOrder(int r) {

        // Check if prefix length is negative.
        if (r < 0) {

            throw new IllegalArgumentException("Prefix length must be > 0.");
        }

        return new PrefixOrder(r);
    }


    /**
     * Returns a lexicographic comparison between 2 Term objects.
     *
     * @param that The other Term object to compare with this Term.
     */
    @Override
    public int compareTo(Term that) {

        return this.query.compareTo(that.query);
    }


    /**
     * Returns a string representation of this Term object.
     */
    @Override
    public String toString() {

        return weight + "\t" + query;
    }


    /**
     * Returns the query instance variable.
     */
    public String getQuery() {

        return query;
    }


    /**
     * Returns the weight instance variable.
     */
    public long getWeight() {

        return weight;
    }


    public static void main(String[] args) {

        // Unit test located at ~/Tests/Tests.TermTest.java
    }
}
