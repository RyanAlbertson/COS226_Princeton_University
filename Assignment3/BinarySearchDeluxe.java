import java.util.Comparator;


/**
 * Implements a binary search that returns both the first and last occurance of a
 * Key object from an array of Keys.
 *
 * @author Ryan Albertson
 */
public class BinarySearchDeluxe {


    /**
     * Returns the lowest index of a Key within an array of Keys.
     *
     * @param a    Array of Keys.
     * @param key  Key to be searched for.
     * @param low  First index to consider for search.
     * @param high Last index to consider for search.
     */
    private static <Key> int findFirstIndex(Key[] a, Key key, Comparator<Key> comparator,
                                            int firstIdx, int low, int high) {

        // End of search.
        if (low > high) {

            return firstIdx;
        }

        int mid = low + (high - low) / 2;

        int compare = comparator.compare(a[mid], key);

        // If key is found.
        if (compare == 0) {

            firstIdx = mid;
        }

        // Continue searching for multiple occurances of key to the left of mid.
        if (compare >= 0) {

            return findFirstIndex(a, key, comparator, firstIdx, low, mid - 1);
        }

        // Search right side of mid as normal.
        else {

            return findFirstIndex(a, key, comparator, firstIdx, mid + 1, high);
        }
    }


    /**
     * Returns the highest index of a Key in an array of Keys.
     *
     * @param a    Array of Keys.
     * @param key  Key to be searched for.
     * @param low  First index to consider for search.
     * @param high Last index to consider for search.
     */
    private static <Key> int findLastIndex(Key[] a, Key key, Comparator<Key> comparator,
                                           int lastIdx, int low, int high) {

        // End of search.
        if (low > high) {

            return lastIdx;
        }

        int mid = low + (high - low) / 2;

        int compare = comparator.compare(a[mid], key);

        // If key is found.
        if (compare == 0) {

            lastIdx = mid;
        }

        // Search left side of mid as normal.
        if (compare > 0) {

            return findLastIndex(a, key, comparator, lastIdx, low, mid - 1);
        }

        // Continue searching for multiple occurances of key to the right of mid.
        else {

            return findLastIndex(a, key, comparator, lastIdx, mid + 1, high);
        }
    }


    /**
     * Check if any arguments are null.
     *
     * @param a          Array of Keys.
     * @param key        Key to be searched for.
     * @param comparator Defined total order of Keys.
     * @throws IllegalArgumentException If any arguments are null.
     */
    private static <Key> void checkNull(Key[] a, Key key, Comparator<Key> comparator) {

        if (a == null) {
            throw new IllegalArgumentException("'a' cannot be null.");
        }

        if (key == null) {
            throw new IllegalArgumentException("'key' cannot be null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("'comparator' cannot be null.");
        }
    }


    /**
     * Finds and returns index of the first occurance of the given Key within an
     * array of sorted Keys.
     *
     * @param a          Array of Keys.
     * @param key        Key to be searched for.
     * @param comparator Defined total order of Keys.
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {

        // Check for any null arguments.
        checkNull(a, key, comparator);

        return findFirstIndex(a, key, comparator, -1, 0, a.length - 1);
    }


    /**
     * Finds and returns index of the last occurance of the given Key within an
     * array of sorted Keys.
     *
     * @param a          Array of Keys.
     * @param key        Key to be searched for.
     * @param comparator Defined total order of Keys.
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {

        // Check for any null arguments.
        checkNull(a, key, comparator);

        return findLastIndex(a, key, comparator, -1, 0, a.length - 1);
    }


    public static void main(String[] args) {

        // Unit testing located at ~/Tests/BinarySearchDeluxeTest
    }
}
