import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implements the A* search algorithm to find a potential shortest solution to
 * a k-puzzle board.
 *
 * @author Ryan Albertson
 */
public class Solver {
    
    
    // Keep reference to the last node in the solution. Backtracking used to trace solution.
    private final SearchNode finalNode;
    
    
    /**
     * Implements a search node for use in the A* search algorithm. Allows for
     * backtracking between nodes.
     */
    private class SearchNode implements Comparable<SearchNode> {
        
        
        // Board stored at this node.
        private Board board;
        
        // # of slides to get to the current node from the initial node.
        private int dist;
        
        // # of moves to reach current plus Manhattan distance of the entire board.
        private int cost;
        
        // Store the previously visited Board.
        private SearchNode prev;
        
        
        /**
         * Constructs a search node within the A* search algorithm.
         *
         * @param board The Board stored in this node.
         * @param dist  # of slides to get to previous node from initial node.
         * @param prev  Previously visited node within the A* algorithm.
         */
        private SearchNode(Board board, int dist, SearchNode prev) {
            
            this.board = board;
            this.dist = dist + 1;
            this.cost = this.board.manhattan() + this.dist;
            this.prev = prev;
        }
        
        
        /**
         * Returns lexicographic comparison between the # of moves incurred by
         * the use of each search node plus their Manhattan distance.
         *
         * @param that Another SearchNode to compare with this SearchNode.
         */
        @Override
        public int compareTo(SearchNode that) {
            
            return Integer.compare(this.cost, that.cost);
        }
    }
    
    
    /**
     * Implements an A* search algorithm to find the optimal solution for the k-puzzle
     * game, using the Manhattan hueristic.
     *
     * @param initial Starting Board for the game.
     * @throws IllegalArgumentException If 'initial' is null.
     * @throws IllegalArgumentException If 'initial' isn't solvable.
     */
    public Solver(Board initial) {
        
        // Check for valid argument.
        if (initial == null) {
            
            throw new IllegalArgumentException("'initial' cannot be null.");
        }
        
        // Check that initial board is solvable.
        if (!initial.isSolvable()) {
            
            throw new IllegalArgumentException("Given board is not solvable.");
        }
        
        // Store search nodes in a minimum priority queue.
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        
        // Set initial search node as the given Board and add to PQ.
        SearchNode current = new SearchNode(initial, 0, null);
        minPQ.insert(current);
        
        // A* search algorithm.
        while (!current.board.isGoal()) {
            
            // Get neighbor Boards of the current node.
            Iterable<Board> neighbors = current.board.neighbors();
            
            for (Board neighborBoard : neighbors) {
                
                // Disregard if neighbor is equal to the previous node.
                if (current.prev != null) {
                    
                    if (neighborBoard.equals(current.prev.board)) { continue; }
                }
                
                // Add neighbor to the priority queue.
                minPQ.insert(new SearchNode(neighborBoard, current.dist, current));
            }
            
            // Traverse to the least cost node.
            current = minPQ.delMin();
        }
        
        // Update final node of the solution.
        finalNode = current;
    }
    
    
    /**
     * Returns the minimum number of slides to get from initial node to the final
     * node in the solution.
     */
    public int moves() {
        
        return finalNode.dist - 1;
    }
    
    
    /**
     * Returns an iterable deque that contains a start-to-finish ordering of the
     * Boards along the solution path.
     */
    public Iterable<Board> solution() {
        
        // Create a deque to store the solution path of Boards.
        Deque<Board> solutionPath = new ArrayDeque<Board>();
        
        // Trace solution path starting at finalNode.
        SearchNode current = finalNode;
        while (current.prev != null) {
            
            // Push current search node's Board onto deque.
            solutionPath.push(current.board);
            
            // Trace back to previous node in the solution path.
            current = current.prev;
        }
        
        // Add initial node to the solution.
        solutionPath.push(current.board);
        
        return solutionPath;
    }
    
    
    /* Test client. Given a text file that contains an k-puzzle, it builds a
       solution to the puzzle and outputs each step. */
    public static void main(String[] args) {
        
        // Read and create an initial k-puzzle from the file.
        In file = new In(args[0]);
        
        int size = file.readInt();
        
        int[][] tiles = new int[size][size];
        
        for (int row = 0; row < size; row++) {
            
            for (int col = 0; col < size; col++) {
                
                tiles[row][col] = file.readInt();
            }
        }
        
        Board board = new Board(tiles);
        
        if (!board.isSolvable()) {
            
            StdOut.println(board);
            StdOut.println("\n Unsolvable puzzle.");
        }
        else {
            
            Solver solver = new Solver(board);
            
            StdOut.printf("Minimum number of moves = %d\n", solver.moves());
            
            for (Board solutionBoard : solver.solution()) {
                
                StdOut.println(solutionBoard);
            }
        }
    }
}

