package chapter4_graphs.C4_4_ShortestPaths;

/******************************************************************************
 *  Compilation:  javac EdgeWeightedDirectedCycle.java
 *  Execution:    java EdgeWeightedDirectedCycle V E F
 *  Dependencies: EdgeWeightedDigraph.java DirectedEdge.java Stack.java
 *
 *  Finds a directed cycle in an edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;

/**
 * The {@code EdgeWeightedDirectedCycle} class represents a data type for
 * determining whether an edge-weighted digraph has a directed cycle.
 * The <em>hasCycle</em> operation determines whether the edge-weighted
 * digraph has a directed cycle and, if so, the <em>cycle</em> operation
 * returns one.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>hasCycle</em> operation takes constant time;
 * the <em>cycle</em> operation takes time proportional
 * to the length of the cycle.
 * <p>
 * See {@link Topological} to compute a topological order if the edge-weighted
 * digraph is acyclic.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class EdgeWeightedDirectedCycle {
    private boolean[] marked;             // marked[v] = has vertex v been marked?
    private DirectedEdge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<DirectedEdge> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the edge-weighted digraph {@code G} has a directed cycle and,
     * if so, finds such a cycle.
     *
     * @param G the edge-weighted digraph
     */
    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.vertex()];
        onStack = new boolean[G.vertex()];
        edgeTo = new DirectedEdge[G.vertex()];
        for (int v = 0; v < G.vertex(); v++)
            if (!marked[v]) dfs(G, v);

        // check that digraph has a cycle
        assert check();
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();

            // short circuit if directed cycle found
            if (cycle != null) return;

                // found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<DirectedEdge>();

                DirectedEdge f = e;
                while (f.from() != w) {
                    cycle.push(f);
                    f = edgeTo[f.from()];
                }
                cycle.push(f);

                return;
            }
        }

        onStack[v] = false;
    }

    /**
     * Does the edge-weighted digraph have a directed cycle?
     *
     * @return {@code true} if the edge-weighted digraph has a directed cycle,
     * {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the edge-weighted digraph has a directed cycle,
     * and {@code null} otherwise.
     *
     * @return a directed cycle (as an iterable) if the edge-weighted digraph
     * has a directed cycle, and {@code null} otherwise
     */
    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }


    // certify that digraph is either acyclic or has a directed cycle
    private boolean check() {

        // edge-weighted digraph is cyclic
        if (hasCycle()) {
            // verify cycle
            DirectedEdge first = null, last = null;
            for (DirectedEdge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.to() != e.from()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }

}