import edu.princeton.cs.algs4.Digraph;

import java.util.Deque;

/**
 *
 */
public class ShortestCommonAncestor {

    // Store a copy of the given Digraph.
    private final Digraph G;

    // Track shortest common ancestor of current calculation.
    private int ancestor;

    // Track length of shortest common ancestral path of current calculation.
    private int length;


    /**
     * Constructs a copy of the given {@link edu.princeton.cs.algs4.Digraph#Digraph(Digraph)}
     * along with attributes that are used across the class methods.
     *
     * @param G A directed graph, not necessarily a DAG.
     */
    public ShortestCommonAncestor(Digraph G) {

        if (G == null) throw new IllegalArgumentException("'G' is null.");

        if (!isDAG(G)) throw new IllegalArgumentException("'G' is not a DAG.");

        this.G = new Digraph(G);
        ancestor = Integer.MAX_VALUE;
        length = Integer.MAX_VALUE;
    }


    /**
     * Returns true if given {@link edu.princeton.cs.algs4.Digraph#Digraph(Digraph)}
     * is acyclic, false otherwise.
     *
     * @param G Digraph to be tested for acyclicity.
     */
    private static boolean isDAG(Digraph G) {

        // Track nodes that have already been visited.
        boolean[] visited = new boolean[G.V()];

        // Track nodes that
        boolean[] inPath = new boolean[G.V()];

        // Start a DFS at all nodes.
        for (int i = 0; i < G.V(); i++) {

            if (hasCycle(G, i, visited, inPath)) return false;
        }
        return true;
    }


    /**
     * Returns true if a cycle if found in the given
     * {@link edu.princeton.cs.algs4.Digraph#Digraph(Digraph)}, false otherwise.
     *
     * @param G       Digraph to be tested for acyclicity.
     * @param node    Current node in the search.
     * @param visited Tracks the visited nodes throughout entire search.
     * @param inPath  Tracks visited nodes within current recursive search.
     */
    private static boolean hasCycle(Digraph G, int node, boolean[] visited,
                                    boolean[] inPath) {

        // Back-edge found, thus a cycle.
        if (inPath[node]) return true;

        // Ignore visited nodes.
        if (visited[node]) return false;

        // Update tracking for current node.
        visited[node] = true;
        inPath[node] = true;

        // Continue DFS through all unvisited adjacencies.
        for (int adj : G.adj(node)) {

            if (hasCycle(G, adj, visited, inPath)) return true;
        }

        // Done checking current path, so remove the current node from it.
        inPath[node] = false;

        // No back-edge was found given the starting node.
        return false;
    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {

        //check null and if synsets are out of range

    }


    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {


    }


    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        //check null and if contains null, and if iterable contains zero vertices
    }


    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {


    }


    /**
     * Calculates both the shortest common ancestor and the shortest ancestral path
     * between the given synset indices. {@code }
     *
     * @param v Index of a synset.
     * @param w Index of a synset.
     */
    private void calcSCA(int v, int w) {

        //


    }


    /**
     * Overloaded {@link #calcSCA(int, int)} that alculates both the shortest
     * common ancestor and the shortest ancestral path between the given sets
     * of synset indices. The corresponding class attributes are then updated
     * to reflect the calculations.
     *
     * @param v Set of synset indices.
     * @param w Set of synset indices.
     */
    private void calcSCA(Iterable<Integer> v, Iterable<Integer> w) {


    }


    /**
     * @param deque1
     * @param dist1
     * @param dist2
     * @param visited
     * @param inPath
     */
    private void bfs(Deque<Integer> deque1, int[] dist1, int[] dist2, boolean[] visited,
                     boolean inPath) {


    }


    public static void main(String[] args) {

        // Unit test located at ~/Tests/ShortestCommonAncestor_Test.java
    }
}
