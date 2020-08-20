import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 */
public class ShortestCommonAncestor {

    private static final int HASH_INITIAL = 17;
    private static final int HASH_MULTIPLIER = 31;

    private final Digraph G;                 //
    private final int numVertices;           //

    private int length;                      // length of the shortest path between V and W
    private int ancestor;                    // the nearest ancestor of V and W
    private int vLast;                       // most recent visited input v
    private int wLast;                       // most recent visited input w
    private int[] distTo1;                   // distTo1[v] = length of shortest V->v path
    private int[] distTo2;                   // distTo2[v] = length of shortest W->v path
    private boolean[] marked1;               // marked1[v] = is there an V->v path?
    private boolean[] marked2;               // marked2[v] = is there an W->v path?

    private final Deque<Integer> q1;
    private final Deque<Integer> q2;
    private final Deque<Integer> visited1;   // visited entries when accessing v
    private final Deque<Integer> visited2;   // visited entries when accessing w


    /**
     * Constructs a copy of the given {@code Digraph} along with attributes that
     * are used across the class methods.
     *
     * @param G A directed graph, not necessarily a DAG.
     */
    public ShortestCommonAncestor(Digraph G) {

        if (G == null) throw new IllegalArgumentException("'G' is null.");

        if (!isDAG(G)) throw new IllegalArgumentException("'G' is not a DAG.");

        this.G = new Digraph(G);
        numVertices = G.V();
        distTo1 = new int[G.V()];
        distTo2 = new int[G.V()];
        marked1 = new boolean[G.V()];
        marked2 = new boolean[G.V()];
        q1 = new ArrayDeque<>();
        q2 = new ArrayDeque<>();
        visited1 = new ArrayDeque<>();
        visited2 = new ArrayDeque<>();
    }


    /**
     * Returns true if given {@code Digraph} is acyclic, false otherwise.
     *
     * @param G {@code Digraph} to be tested for acyclicity.
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
     * Returns true if a cycle if found in the given graph, false otherwise.
     *
     * @param G       {@code Digraph} to be tested for acyclicity.
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

        // Unit test located at ~/Tests/ShortestCommonAncestor_Test.java
    }
}
