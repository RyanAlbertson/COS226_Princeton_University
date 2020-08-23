package main;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implements a board, as used in an k-puzzle. Methods are designed to assist
 * in the optimal solution die each of the boards.
 *
 * @author Ryan Albertson
 */
public class Board {
    
    
    // Store the board as a 2D array. Takes the form [rows][cols].
    private final int[][] board;
    
    // Store size of board. Note that board is a square.
    private final int size;
    
    // Store Manhattan distance of this board.
    private final int manhattanDist;
    
    // Store Hamming distance of this board.
    private final int hammingDist;
    
    // Row coordinate of current empty tile.
    private int emptyRow;
    
    // Column coordinate of current empty tile.
    private int emptyCol;
    
    
    /**
     * Constructs a square board given a 2D array of rows and columns.
     *
     * @param tiles 2D array such that tiles[rows][cols].
     */
    public Board(int[][] tiles) {
        
        board = deepCopy(tiles);
        size = board.length;
        
        manhattanDist = calcManhattan();
        hammingDist = calcHamming();
    }
    
    
    /**
     * Returns a deep copy of the given 2D int array.
     */
    private static int[][] deepCopy(int[][] arr) {
        
        int[][] copyBoard = new int[arr.length][arr.length];
        
        for (int row = 0; row < arr.length; row++) {
            
            copyBoard[row] = arr[row].clone();
        }
        
        return copyBoard;
    }
    
    
    /**
     * Returns true if given tile is in correct final position, false otherwise.
     *
     * @param row Row of a tile.
     * @param col Column of a tile.
     */
    private boolean inPosition(int row, int col) {
        
        int tileVal      = tileAt(row, col);
        int emptyTileVal = size() * size();
        int expectedVal  = row * size() + (col + 1);
        
        // If given tile is the empty tile.
        if (tileVal == 0) { return (emptyTileVal) == expectedVal; }
        
        else { return tileVal == expectedVal; }
    }
    
    
    /**
     * Returns number of tiles in the incorrect final position.
     */
    private int calcHamming() {
        
        // Count # of tiles in wrong position.
        int numWrongTiles = 0;
        
        // Check if every tile is either in correct position or not.
        for (int row = 0; row < size(); row++) {
            
            for (int col = 0; col < size(); col++) {
                
                // Skip if current tile is empty.
                if (tileAt(row, col) == 0) { continue; }
                
                // Otherwise check if current tile is in position.
                if (!inPosition(row, col)) { numWrongTiles++; }
            }
        }
        
        return numWrongTiles;
    }
    
    
    /**
     * Returns sum of all distances that every tile must move in order to achieve
     * its respective correct final position.
     */
    private int calcManhattan() {
        
        // Sum of distances that each tile must yet move.
        int dist = 0;
        
        // Calculate remaining distance for every tile.
        for (int row = 0; row < size(); row++) {
            
            for (int col = 0; col < size(); col++) {
                
                int tileVal = tileAt(row, col);
                
                // Check if current tile is empty.
                if (tileVal == 0) {
                    
                    // Initial empty tile was found, update coordinates.
                    emptyRow = row;
                    emptyCol = col;
                    
                    // Add vertical and horizontal distances to the sum.
                    int correctRow = size() - 1;
                    int correctCol = size() - 1;
                }
                else {
                    
                    // Add vertical and horizontal distances to the sum.
                    int correctRow = (tileVal - 1) / size();
                    int correctCol = (tileVal - 1) % size();
                    dist += Math.abs(row - correctRow);
                    dist += Math.abs(col - correctCol);
                }
            }
        }
        
        return dist;
    }
    
    
    /**
     * Returns string representation of the board. Takes the form of board size
     * on the top line. Then below, the numbers at each tile are added in
     * row-major order.
     */
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append(size() + "\n");
        
        // Add every tile to the string.
        for (int row = 0; row < size(); row++) {
            
            for (int col = 0; col < size(); col++) {
                
                sb.append(String.format("%2d ", tileAt(row, col)));
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    
    /**
     * Returns integer stored at the given tile coordinates.
     *
     * @param row Row a the tile.
     * @param col Column of the tile.
     * @throws IllegalArgumentException If tile is out of bounds.
     */
    public int tileAt(int row, int col) {
        
        // Check for valid tile index.
        if (row < 0 || col < 0 || row > size() - 1 || col > size() - 1) {
            
            throw new IllegalArgumentException("tile is out of bounds.");
        }
        
        return board[row][col];
    }
    
    
    /**
     * Returns length of the square board.
     */
    public int size() {
        
        return size;
    }
    
    
    /**
     * Returns number of tiles in the incorrect final position.
     */
    public int hamming() {
        
        return hammingDist;
    }
    
    
    /**
     * Returns sum of all distances that every tile must move in order to achieve its
     * respective correct final position.
     */
    public int manhattan() {
        
        return manhattanDist;
    }
    
    
    /**
     * Returns true if every tile in board is in correct final position, false otherwise.
     */
    public boolean isGoal() {
        
        return hamming() == 0;
    }
    
    
    /**
     * Returns true if the given Object is equal to this board, false otherwise.
     *
     * @param y A generic object to be compared for equality.
     */
    public boolean equals(Object y) {
        
        // Check null reference.
        if (y == null) { return false; }
        
        // Check that given object is a src.main.Board.
        if (this.getClass() != y.getClass()) { return false; }
        
        // Check if objects are referencing the same thing.
        if (this == y) { return true; }
        
        Board that = (Board) y;
        
        // Check for equal size.
        if (this.size() != that.size()) { return false; }
        
        // Check that every tile is equal across both Boards.
        for (int row = 0; row < size(); row++) {
            
            for (int col = 0; col < size(); col++) {
                
                if (this.tileAt(row, col) != that.tileAt(row, col)) { return false; }
            }
        }
        
        return true;
    }
    
    
    /**
     * Returns a deque of Boards which represents the consequent mutations of this
     * board for each possible slide.
     */
    public Iterable<Board> neighbors() {
        
        // Create a deque to store all neighbor boards for this board.
        Deque<Board> neighbors = new ArrayDeque<Board>();
        
        String[] slideDirections = { "right", "left", "up", "down" };
        
        // Try to slide in each direction. If possible, then create the subsequent neighbor.
        directionLoop:
        for (String direction : slideDirections) {
            
            // Make deep copy of this board's tiles.
            int[][] newBoard = deepCopy(board);
            
            switch (direction) {
                
                case "right":
                    
                    // Check that the necessary tile exists.
                    if (emptyCol <= 0) { continue directionLoop; }
                    
                    // Swap the tiles.
                    newBoard[emptyRow][emptyCol] = newBoard[emptyRow][emptyCol - 1];
                    newBoard[emptyRow][emptyCol - 1] = 0;
                    break;
                
                case "left":
                    
                    if (emptyCol >= size() - 1) { continue directionLoop; }
                    
                    newBoard[emptyRow][emptyCol] = newBoard[emptyRow][emptyCol + 1];
                    newBoard[emptyRow][emptyCol + 1] = 0;
                    break;
                
                case "up":
                    
                    if (emptyRow >= size() - 1) { continue directionLoop; }
                    
                    newBoard[emptyRow][emptyCol] = newBoard[emptyRow + 1][emptyCol];
                    newBoard[emptyRow + 1][emptyCol] = 0;
                    break;
                
                case "down":
                    
                    if (emptyRow <= 0) { continue directionLoop; }
                    
                    newBoard[emptyRow][emptyCol] = newBoard[emptyRow - 1][emptyCol];
                    newBoard[emptyRow - 1][emptyCol] = 0;
                    break;
            }
            
            // Create the neighbor using new tiles and push onto neighbor deque.
            Board newNeighbor = new Board(newBoard);
            neighbors.push(newNeighbor);
        }
        
        return neighbors;
    }
    
    
    /**
     * Returns true if this board can potentially be solved, false otherwise.
     */
    public boolean isSolvable() {
        
        // Pairs of tiles such that (tile1 < tile2) and tile1 appears after tile2.
        int numInversions = 0;
        
        // Count all inversions in the board.
        for (int row = 0; row < size(); row++) {
            
            for (int col = 0; col < size(); col++) {
                
                int tileVal1 = tileAt(row, col);
                
                // Disregard the empty tile.
                if (tileVal1 == 0) { continue; }
                
                int subColStart = col + 1;
                
                // Compare first tile to all tiles after it.
                for (int subRow = row; subRow < size(); subRow++) {
                    
                    for (int subCol = subColStart; subCol < size(); subCol++) {
                        
                        int tileVal2 = tileAt(subRow, subCol);
                        
                        // Disregard the empty tile.
                        if (tileVal2 == 0) { continue; }
                        
                        // Inversion was found.
                        if (tileVal1 > tileVal2) { numInversions++; }
                    }
                    
                    // Reset col index to the far left.
                    subColStart = 0;
                }
            }
        }
        
        // If the board size is odd.
        if (size() % 2 != 0) {
            
            return (numInversions % 2) == 0;
        }
        
        // src.main.Board size is even.
        return (numInversions + emptyRow) % 2 != 0;
    }
    
    
    public static void main(String[] args) {
    
    }
}
