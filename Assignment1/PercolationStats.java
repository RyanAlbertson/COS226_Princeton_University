import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Performs a Monte Carlo simulation on percolation systems. Also implements
 * statistical methods relating to the experiments.
 *
 * @author Ryan Albertson
 */
public class PercolationStats {


    // Critical value for a 95% confidence interval.
    private static final double CRITICAL_VALUE = 1.96;

    // Number of indendent experiments in the Monte Carlo simulation.
    private int trials;

    // Stores percolation threshold for each trial.
    private double[] thresholds;

    // Mean percolation threshold of all trials.
    private double mean;


    /**
     * Tests a percolating system of a given size and statistically analyzes the tests.
     *
     * @param n      Size of percolating system.
     * @param trials Number of times to test the percolating system.
     */
    public PercolationStats(int n, int trials) {

        // Check if arguments are <= 0.
        if (n <= 0 || trials <= 0) {

            throw new IllegalArgumentException("Constructor arguments must be > 0.");
        }

        // Initialize instance variables.
        this.trials = trials;
        thresholds = new double[trials];

        // Run Monte Carlo simulation.
        for (int i = 0; i < trials; i++) {

            // Current Percolation system.
            Percolation currentPerc = new Percolation(n);

            // Iterate until opening a site results in a percolation.
            while (!currentPerc.percolates()) {

                // Choose a random site to open.
                int randomRow = StdRandom.uniform(n);
                int randomCol = StdRandom.uniform(n);

                // Open the current site.
                currentPerc.open(randomRow, randomCol);
            }

            // Store current percolation threshold.
            thresholds[i] = (double) currentPerc.numberOfOpenSites() / (n * n);
        }

        // Calculate mean of all trials.
        mean = StdStats.mean(thresholds);
    }


    /**
     * Returns the mean percolation threshold for all trials of Monte Carlo simulation.
     */
    public double mean() {

        return mean;
    }


    /**
     * Calculates and returns the standard deviation of the Monte Carlo simulation.
     */
    public double stddev() {

        return StdStats.stddev(thresholds);
    }


    /**
     * Returns lower bound of a 95% confidence interval for the percolation threshold.
     */
    public double confidenceLow() {

        return mean() - (CRITICAL_VALUE * stddev() / Math.sqrt(trials));
    }


    /**
     * Returns higher bound of a 95% confidence interval for the percolation threshold.
     */
    public double confidenceHigh() {

        return mean() + (CRITICAL_VALUE * stddev() / Math.sqrt(trials));
    }


    /**
     * PercolationStats test client.
     */
    public static void main(String[] args) {

        // Get size of Percolation system.
        int n = Integer.parseInt(args[0]);

        // Get number of trials of Monte Carlo simululation.
        int trials = Integer.parseInt(args[1]);

        // Start timer.
        Stopwatch timer = new Stopwatch();

        PercolationStats stats = new PercolationStats(n, trials);

        // Print statistics.
        StdOut.printf("%-17s %f\n", "mean():", stats.mean());
        StdOut.printf("%-17s %f\n", "stddev():", stats.stddev());
        StdOut.printf("%-17s %f\n", "confidenceLow():", stats.confidenceLow());
        StdOut.printf("%-17s %f\n", "confidenceHigh():", stats.confidenceHigh());

        // Print elapsed time.
        StdOut.printf("%-17s %f\n", "Elapsed time:", timer.elapsedTime());


        /* My implementation takes significantly longer to run than the example provided
         * in the project description. The upper bound of the running time must be way worse
         * than expected because it skyrockets when n gets close to 1000. I thought that using
         * a hash table would be faster than storing open sites in a 2D array but maybe because
         * of the accompanying method calls, the running time ends up being worse for large values of n.
         * My first guess was where I randomly chose sites on lines 53-60 in this module. I tested
         * it a bit and I don't think that it factors significantly into the runtime. Also I couldn't
         * figure out how to better implement the random sites without making extra data structures.
         */
    }
}
