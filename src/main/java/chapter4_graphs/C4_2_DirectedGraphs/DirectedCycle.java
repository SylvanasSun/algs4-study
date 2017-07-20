package chapter4_graphs.C4_2_DirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac DirectedCycle.java
 *  Execution:    java DirectedCycle input.txt
 *  Dependencies: Digraph.java Stack.java StdOut.java In.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *
 *  Finds a directed cycle in a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java DirectedCycle tinyDG.txt
 *  Directed cycle: 3 5 4 3
 *
 *  %  java DirectedCycle tinyDAG.txt
 *  No directed cycle
 *
 ******************************************************************************/

/**
 * The {@code DirectedCycle} class represents a data type for
 * determining whether a digraph has a directed cycle.
 * The <em>hasCycle</em> operation determines whether the digraph has
 * a directed cycle and, and of so, the <em>cycle</em> operation
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
 * See {@link Topological} to compute a topological order if the
 * digraph is acyclic.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DirectedCycle {

    private final Digraph digraph;

    // marked[v] = has vertex v been marked?
    private final boolean[] marked;

    // edgeTo[v] = previous vertex on path to v
    private final int[] edgeTo;

    // onStack[v] = is vertex on the stack?
    private final boolean[] onStack;

    // directed cycle (or null if no such cycle)
    private Stack<Integer> cycle;

    /**
     * Determines whether the digraph {@code digraph} has a directed cycle and, if so,
     * finds such a cycle.
     *
     * @param digraph the digraph
     */
    public DirectedCycle(Digraph digraph) {
        this.digraph = digraph;
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        this.onStack = new boolean[vertex];

        for (int v = 0; v < vertex; v++) {
            if (!marked[v] && cycle == null)
                dfs(v);
        }
    }

    /**
     * Does the digraph have a directed cycle?
     *
     * @return {@code true} if the digraph has a directed cycle, {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the digraph has a directed cycle, and {@code null} otherwise.
     *
     * @return a directed cycle (as an iterable) if the digraph has a directed cycle,
     * and {@code null} otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        onStack[vertex] = true;

        for (int w : digraph.adj(vertex)) {
            if (cycle != null)
                return;
            else if (!marked[w]) {
                edgeTo[w] = vertex;
                dfs(w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = vertex; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(vertex);
                assert check();
            }
        }
        onStack[vertex] = false;
    }

    // certify that digraph has a directed cycle if it reports one
    private boolean check() {
        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) first = v;
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        InputStream inputStream
                = DirectedCycle.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);
        DirectedCycle directedCycle = new DirectedCycle(digraph);

        if (directedCycle.hasCycle()) {
            System.out.printf("Directed Cycle:");
            for (int v : directedCycle.cycle())
                System.out.printf(" %d", v);
            System.out.printf("\n");
        } else {
            System.out.println("Not find cycle.");
        }
    }

}
