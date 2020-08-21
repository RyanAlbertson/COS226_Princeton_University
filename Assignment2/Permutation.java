import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Reads string sequence from file. Then outputs a given number of strings randomly.
 *
 * @author Ryan Albertson
 */
public class Permutation {


    /**
     * Reads string sequence from file. Then outputs a given number of strings randomly.
     **/
    public static void main(String[] args) {


        // Number of strings to output.
        int numStrings = Integer.parseInt(args[0]);

        // Store strings such that they can be retrieved randomly.
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        // Read each string and store them in a RandomizedQueue.
        for (int i = 0; i < numStrings; i++) {

            randomizedQueue.enqueue(StdIn.readString());
        }

        // Randomly dequeue and print strings from the RandomizedQueue.
        for (int i = 0; i < numStrings; i++) {

            StdOut.println(randomizedQueue.dequeue());
        }
        

        /* The project description stated that an exact amount of strings must be
         * printed but also that each printed string must be distinct from others.
         * In some cases they're mutually exclusive so I chose to implement the
         * first restriction.
         */
    }
}
