package main;

import edu.princeton.cs.algs4.StdOut;
import utils.WeightedQuickUnionPathCompressionUF;

import java.util.HashMap;

/**
 * Stores an (n x n) grid system and initializes methods that allow manipulation
 * and testing of the system.
 *
 * @author Ryan Albertson
 */
public class Percolation {


    // Store height and width of an n x n grid.
    private final int GRID_SIZE;

    /* Store all sites as sets in a WeightedQuickUnion object. This UF correctly
       calculates percolation, but it displays backwash when visualizing. */
    private WeightedQuickUnionPathCompressionUF sitesUF1;

    /* This UF doesn't connect sites to the botNode, this is to prevent
       backwash. Therefore it cannot be used to determine percolation. */
    private WeightedQuickUnionPathCompressionUF sitesUF2;

    // This set will connect to open sites in the top row of the grid.
    private int topNode;

    // This set will connect to open sites in the bottom row of the grid.
    private int botNode;

    // Store all sites that are open.
    private HashMap<Integer, Integer> openSites;


    /**
     * Instead of storing sites in an (n x n) grid, flattens the [row][col]
     * coordinates to 1D such that the sites can be stored in a list of sets.
     * The elements of the sets are the flattened indices.
     *
     * @throws IllegalArgumentException if system size is at most 0.
     */
    public Percolation(int n) {

        // Check that n is greater than 0.
        if (n <= 0) {

            throw new IllegalArgumentException("n must be greater than 0.");
        }

        // Set grid size.
        GRID_SIZE = n;

        // Initialize all sites in WeightedQuickUnion. Plus top and bot nodes.
        sitesUF1 = new WeightedQuickUnionPathCompressionUF(GRID_SIZE * GRID_SIZE + 2);

        // Initialize all sites in WeightedQuickUnion. Plus top node.
        sitesUF2 = new WeightedQuickUnionPathCompressionUF(GRID_SIZE * GRID_SIZE + 1);

        // Initialize hashmap to store open sites.
        openSites = new HashMap<Integer, Integer>();

        // Define top and bottom nodes as the last two indices in sites.
        topNode = GRID_SIZE * GRID_SIZE;
        botNode = GRID_SIZE * GRID_SIZE + 1;

        // Open the top and bottom nodes so that sites can be connected to them.
        openSites.put(topNode, 0);
        openSites.put(botNode, 0);
    }


    /**
     * Checks that a given site is within the bounds of the (n x n) grid.
     *
     * @param row Row of given site.
     * @param col Column of given site.
     * @throws IllegalArgumentException if given site is out of bounds.
     */
    private void outOfBoundsCheck(int row, int col) {

        if ((row < 0) || (row > GRID_SIZE - 1) || (col < 0) || (col > GRID_SIZE - 1)) {

            throw new IllegalArgumentException("Site is out of bounds.");
        }
    }


    /**
     * Flattens 2D (row, col) pair to a 1D index, then returns it.
     *
     * @param row Row of given site.
     * @param col Column of given site.
     */
    private int flatten(int row, int col) {

        return row * GRID_SIZE + col;
    }


    /**
     * Connects two sites. Both sites must be open and within bounds.
     *
     * @param site1 Flattened index of first site. (must be open).
     * @param site2 Flatened index of second site.
     */
    private void connectSites(int site1, int site2) {

        // Check second site is open. site1 is assumed to be open to save time.
        if (openSites.containsKey(site2)) {

            // Connect the first and second sites.
            sitesUF1.union(site1, site2);
            sitesUF2.union(site1, site2);
        }

        // Second site not open so don't connect.
    }


    /**
     * Opens a given site in the grid, if not already open. Then connects to
     * adjacent open sites.
     *
     * @param row Row of given site.
     * @param col Column of given site.
     * @throws IllegalArgumentException if given site is out of bounds.
     */
    public void open(int row, int col) {

        // Check if site is already open.
        if (isOpen(row, col)) {

            return;
        }

        // Check if given site is within the bounds of the (n x n) grid.
        outOfBoundsCheck(row, col);

        // Add given site to a table of open sites.
        openSites.put(flatten(row, col), 0);

        // If given site is in top row, connect it to topNode.
        if (row == 0) {

            connectSites(topNode, flatten(row, col));
        }

        // If given site is in bottom row, connect it to botNode.
        if (row == GRID_SIZE - 1) {

            // Only within the first UF object.
            sitesUF1.union(botNode, flatten(row, col));
        }


        // Try to connect to adjacent sites if they're open.

        // Site below.
        try {
            outOfBoundsCheck(row + 1, col);
            connectSites(flatten(row, col), flatten(row + 1, col));
        }
        catch (IllegalArgumentException outOfBounds) { // Don't connect.
        }

        // Site above.
        try {
            outOfBoundsCheck(row - 1, col);
            connectSites(flatten(row, col), flatten(row - 1, col));
        }
        catch (IllegalArgumentException outOfBounds) { // Don't connect.
        }

        // Site to right.
        try {
            outOfBoundsCheck(row, col + 1);
            connectSites(flatten(row, col), flatten(row, col + 1));
        }
        catch (IllegalArgumentException outOfBounds) { // Don't connect.
        }

        // Site to left.
        try {
            outOfBoundsCheck(row, col - 1);
            connectSites(flatten(row, col), flatten(row, col - 1));
        }
        catch (IllegalArgumentException outOfBounds) { // Don't connect.
        }
    }


    /**
     * Checks if a given site is open. Returns true if site is open, false otherwise.
     *
     * @param row Row of given site.
     * @param col Column of given site.
     * @throws IllegalArgumentException if given site is out of bounds.
     */
    public boolean isOpen(int row, int col) {

        // Check if given site is within the bounds of the (n x n) grid.
        outOfBoundsCheck(row, col);

        // Check if given site is in the table of open sites.
        return openSites.containsKey(flatten(row, col));
    }


    /**
     * Checks if given site is full. Returns true if site if full, false othwerwise.
     *
     * @param row Row of given site.
     * @param col Column of given site.
     * @throws IllegalArgumentException if given site is out of bounds.
     */
    public boolean isFull(int row, int col) {

        // Check if given site is within the bounds of the (n x n) grid.
        outOfBoundsCheck(row, col);

        // Check if given site is connected to the top row.
        return sitesUF2.find(flatten(row, col)) == sitesUF2.find(topNode);
    }


    /**
     * Returns number of open sites.
     */
    public int numberOfOpenSites() {

        return openSites.size() - 2;  // Subtract topNode and botNode, hence 2.
    }


    /**
     * Returns true if the system percolates, false otherwise.
     */
    public boolean percolates() {

        // Check if the top row is connected to the bottom row.
        return sitesUF1.find(botNode) == sitesUF1.find(topNode);
    }


    /**
     * Prints a grid representation of the current system. For testing.
     */
    private void displayGrid() {

        StdOut.println();

        // Line of hyphens that separate each row.
        StringBuilder ROW_SEPARATOR = new StringBuilder();

        for (int col = 0; col < GRID_SIZE; col++) {

            ROW_SEPARATOR.append("--------");
        }

        // Iterate through rows, top to bottom.
        for (int row = GRID_SIZE - 1; row >= 0; row--) {

            // Row number.
            StdOut.printf("%2d  |", row);

            // Iterate through columns, left to right.
            for (int col = 0; col < GRID_SIZE; col++) {

                // Fill cell if corresponding site is closed.
                if (!isOpen(row, col)) {

                    StdOut.print(" ===== |");
                }
                // Otherwise the cell is open.
                else {

                    StdOut.print("       |");
                }
            }

            // Separate next row with dashes.
            StdOut.printf("\n    -%s\n", ROW_SEPARATOR);
        }

        // Column numbers.
        StdOut.print("   ");
        for (int col = 0; col < GRID_SIZE; col++) {

            StdOut.printf(" %5d  ", col);
        }

        StdOut.println();
    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

        // Test constructor.
        StdOut.println("Testing constructor...");
        StdOut.println("> main.Percolation percolation = new main.Percolation(10)");
        Percolation percolation = new Percolation(8);
        StdOut.println("> percolation.displayGrid");
        percolation.displayGrid();


        // Test open() method.
        StdOut.println("\n\nTesting open()...");
        StdOut.println("> percolation.open(0, 0)");
        percolation.open(0, 0);
        StdOut.println("> percolation.open(3, 4)");
        percolation.open(3, 4);
        StdOut.println("> percolation.open(7, 7)");
        percolation.open(7, 7);
        StdOut.println("> percolation.displayGrid");
        percolation.displayGrid();


        // Test isOpen() method.
        StdOut.println("\n\nTesting isOpen()...");
        StdOut.println("> percolation.isOpen(0, 0)");
        StdOut.println(percolation.isOpen(0, 0));
        StdOut.println("> percolation.isOpen(5, 5)");
        StdOut.println(percolation.isOpen(5, 5));


        // Test numberOfOpenSites() method.
        StdOut.println("\n\nTesting numberOfOpenSites()...");
        StdOut.println("> percolation.numberOfOpenSites()");
        StdOut.println(percolation.numberOfOpenSites());


        // Test isFull() method.
        StdOut.println("\n\nTesting isFull()...");
        StdOut.println("> percolation.open(6, 7)");
        percolation.open(6, 7);
        StdOut.println("> percolation.isFull(6, 7)");
        StdOut.println(percolation.isFull(6, 7));


        // Test percolates method().
        StdOut.println("\n\nTesting percolates()...");
        StdOut.println("> percolation.displayGrid");
        percolation.displayGrid();
        StdOut.println("> percolation.percolates()");
        StdOut.println(percolation.percolates());

        StdOut.println("\n\nTesting percolates() again...");
        StdOut.println("> percolation.open(5, 7)");

        // Open a percolating path.
        percolation.open(5, 7);
        StdOut.println("> percolation.open(4, 7)");
        percolation.open(4, 7);
        StdOut.println("> percolation.open(3, 7)");
        percolation.open(3, 7);
        StdOut.println("> percolation.open(2, 7)");
        percolation.open(2, 7);
        StdOut.println("> percolation.open(1, 7)");
        percolation.open(1, 7);
        StdOut.println("> percolation.open(0, 7)");
        percolation.open(0, 7);

        StdOut.println("> percolation.displayGrid");
        percolation.displayGrid();
        StdOut.println("> percolation.percolates()");
        StdOut.println(percolation.percolates());
    }
}
