import edu.princeton.cs.algs4.Digraph;

/**
 *
 */
public class ShortestCommonAncestor {


    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {

        if (G == null) throw new IllegalArgumentException("'G' is null.");

        if (!isDAG(G)) throw new IllegalArgumentException("'G' is not a DAG.");


    }


    /**
     * Returns true if given digraph is acyclic, false otherwise.
     *
     * @param G Digraph object to be tested for acyclicity.
     */
    private static boolean isDAG(Digraph G) {

        int numVertices = G.V();

        // Track whether each vertex has been found.
        boolean[] found = new boolean[numVertices];  // RENAME TO visited  ??

        //

    }


    /**
     * @param G
     * @param vertex
     * @param found
     * @param departures
     * @param time
     * @return
     */
    private static int DFS(Digraph G, int vertex, boolean[] found,
                           int[] departures, int time) {


    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {


    }


    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {


    }


    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {


    }


    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {


    }


    public static void main(String[] args) {

    }
}
