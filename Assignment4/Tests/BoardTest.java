import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    
    
    Board board1;
    
    Board board2;
    
    Board board3;
    
    Board board4;
    
    
    @Before
    public void setUp() throws Exception {
        
        int[][] tiles1 = { { 0, 1, 3 },
                           { 4, 2, 5 },
                           { 7, 8, 6 } };
        
        int[][] tiles2 = { { 1, 2, 3, 4 },
                           { 5, 6, 7, 8 },
                           { 9, 10, 11, 12 },
                           { 13, 15, 14, 0 } };
        
        int[][] goalTiles = { { 1, 2, 3 },
                              { 4, 5, 6 },
                              { 7, 8, 0 } };
        
        board1 = new Board(tiles1);
        board2 = new Board(tiles2);
        board3 = new Board(goalTiles);
        board4 = new Board(tiles2);
        
    }
    
    
    @Test
    public void test_toString() {
        
        String expected = "3\n 0  1  3 \n 4  2  5 \n 7  8  6 \n";
        
        assertEquals(board1.toString(), expected);
        // System.out.println(board1);
    }
    
    
    @Test
    public void test_tileAt() {
        
        int expected1 = 4;
        assertEquals(board1.tileAt(1, 0), expected1);
        
        int expected2 = 0;
        assertEquals(board2.tileAt(3, 3), expected2);
        
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void test_tileAt_IllegalArgument() {
        
        board1.tileAt(10, 0);
    }
    
    
    @Test
    public void test_size() {
        
        int expected1 = 3;
        assertEquals(board1.size(), expected1);
        
        int expected2 = 4;
        assertEquals(board2.size(), expected2);
    }
    
    
    @Test
    public void test_hamming() {
        
        Iterable<Board> neighbors = board3.neighbors();
        
        for (Board neighbor : neighbors) {
            
            int expected = 1;
            assertEquals(neighbor.hamming(), expected);
        }
    }
    
    
    @Test
    public void test_manhattan() {
        
        int expected1 = 4;
        assertEquals(board1.manhattan(), expected1);
        
        int expected2 = 0;
        assertEquals(board3.manhattan(), expected2);
    }
    
    
    @Test
    public void test_isGoal() {
        
        assertFalse(board1.isGoal());
        assertTrue(board3.isGoal());
    }
    
    
    @Test
    public void test_isEquals() {
        
        assertNotEquals(board1, board2);
        assertEquals(board1, board1);
        assertEquals(board2, board4);
    }
    
    
    @Test
    public void test_neighbors() {
        
        int[][] neighbor1 = { { 1, 0, 3 },
                              { 4, 2, 5 },
                              { 7, 8, 6 } };
        
        int[][] neighbor2 = { { 4, 1, 3 },
                              { 0, 2, 5 },
                              { 7, 8, 6 } };
        
        Board expectedNeighbor1 = new Board(neighbor1);
        Board expectedNeighbor2 = new Board(neighbor2);
        
        Iterable<Board> neighbors = board1.neighbors();
        
        for (Board neighbor : neighbors) {
            
            assertTrue(neighbor.equals(expectedNeighbor1) ||
                               neighbor.equals(expectedNeighbor2));
            
            // System.out.println(neighbor);
        }
    }
    
    
    @Test
    public void test_isSolvable() {
        
        assertTrue(board1.isSolvable());
        assertTrue(board3.isSolvable());
        assertFalse(board2.isSolvable());
    }
}
