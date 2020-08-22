package main;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;


/**
 * Implements methods to calculate shortest common ancestral paths between synsets
 * within a {@link WordNet}.
 *
 * @author Ryan Albertson
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


    /**
     * Returns length of shortest ancestral path between given synsets.
     *
     * @param v Synset index.
     * @param w Synset index.
     */
    public int length(int v, int w) {

        Iterable<Integer> vToIterable = Collections.singletonList(v);
        Iterable<Integer> wToIterable = Collections.singletonList(w);

        validate(vToIterable);
        validate(wToIterable);

        calcSCA(vToIterable, wToIterable);

        int getLength = length;

        // Reset attributes for use in future calculations.
        ancestor = Integer.MAX_VALUE;
        length = Integer.MAX_VALUE;

        return getLength;
    }


    /**
     * Returns index of shortest common ancestor synset of given synsets.
     *
     * @param v Synset index.
     * @param w Synset index.
     */
    public int ancestor(int v, int w) {

        Iterable<Integer> vToIterable = Collections.singletonList(v);
        Iterable<Integer> wToIterable = Collections.singletonList(w);

        validate(vToIterable);
        validate(wToIterable);

        calcSCA(vToIterable, wToIterable);

        int getAncestor = ancestor;

        // Reset attributes for use in future calculations.
        ancestor = Integer.MAX_VALUE;
        length = Integer.MAX_VALUE;

        return getAncestor;
    }


    /**
     * Returns shortest ancestral path between given sets of synsets.
     *
     * @param subsetA Set of synset indices.
     * @param subsetB Set of synset indices.
     */
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        validate(subsetA);
        validate(subsetB);

        calcSCA(subsetA, subsetB);

        int getLength = length;

        // Reset attributes for use in future calculations.
        ancestor = Integer.MAX_VALUE;
        length = Integer.MAX_VALUE;

        return getLength;
    }


    /**
     * Returns index of shortest common ancestor synset of given sets of synsets.
     *
     * @param v Set of synset indices.
     * @param w Set of synset indices.
     */
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        validate(subsetA);
        validate(subsetB);

        calcSCA(subsetA, subsetB);

        int getAncestor = ancestor;

        // Reset attributes for use in future calculations.
        ancestor = Integer.MAX_VALUE;
        length = Integer.MAX_VALUE;

        return getAncestor;
    }


    /**
     * Calculates both the shortest common ancestor and the shortest ancestral path
     * between the given sets of synset indices. {@code ancestor} and {@code length}
     * are updated according to the calculations.
     *
     * @param v Set of synset indices.
     * @param w Set of synset indices.
     */
    private void calcSCA(Iterable<Integer> v, Iterable<Integer> w) {

        // Used to find shortest paths using BFS.
        BreadthFirstDirectedPaths vPath = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wPath = new BreadthFirstDirectedPaths(G, w);

        /* Check every vertex in graph, if 'v' and 'w' have a path to the vertex,
         * then it's an ancestor. Then check if the sum of the paths is less than
         * the current shortest ancestral path. */
        for (int i = 0; i < G.V(); i++) {

            int currentLength = vPath.distTo(i) + wPath.distTo(i);

            if (vPath.hasPathTo(i) && wPath.hasPathTo(i) && currentLength < length) {

                length = currentLength;
                ancestor = i;
            }
        }
    }


    /**
     * Check that given parameter is valid for use in {@link #lengthSubset(Iterable,
     * Iterable)} and {@link #ancestorSubset(Iterable, Iterable)}.
     *
     * @param synsetIds Set of synset indices to check.
     */
    private void validate(Iterable<Integer> synsetIds) {

        if (synsetIds == null) throw new IllegalArgumentException();
        if (synsetIds.spliterator().getExactSizeIfKnown() == 0) {
            throw new IllegalArgumentException();
        }
        for (Integer id : synsetIds) {
            if (id == null) throw new IllegalArgumentException();
            if (id >= G.V()) throw new IllegalArgumentException();
        }
    }


    /**
     * Unit test.
     */
    public static void main(String[] args) {

        // Below is a provided test client.
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
