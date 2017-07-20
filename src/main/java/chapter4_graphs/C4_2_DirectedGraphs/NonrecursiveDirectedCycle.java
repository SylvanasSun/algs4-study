package chapter4_graphs.C4_2_DirectedGraphs;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayDeque;
import java.util.Queue;

/******************************************************************************
 *  Compilation:  javac DirectedCycleX.java
 *  Execution:    java DirectedCycleX V E F
 *  Dependencies: Queue.java Digraph.java Stack.java
 *
 *  Find a directed cycle in a digraph, using a nonrecursive, queue-based
 *  algorithm. Runs in O(E + V) time.
 *
 ******************************************************************************/

/**
 * The {@code DirectedCycleX} class represents a data type for
 * determining whether a digraph has a directed cycle.
 * The <em>hasCycle</em> operation determines whether the digraph has
 * a directed cycle and, and of so, the <em>cycle</em> operation
 * returns one.
 * <p>
 * This implementation uses a nonrecursive, queue-based algorithm.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>hasCycle</em> operation takes constant time;
 * the <em>cycle</em> operation takes time proportional
 * to the length of the cycle.
 * <p>
 * See {@link DirectedCycle} for a recursive version that uses depth-first search.
 * See {@link Topological} or {@link TopologicalX} to compute a topological order
 * when the digraph is acyclic.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class NonrecursiveDirectedCycle {

    private Stack<Integer> cycle;

    public NonrecursiveDirectedCycle(Digraph digraph) {
        int vertex = digraph.vertex();
        // indegrees of remaining vertices
        int[] indegress = new int[vertex];
        for (int v = 0; v < vertex; v++)
            indegress[v] = digraph.indegree(v);

        // initialize queue to contain all vertices with indegree = 0
        Queue<Integer> queue = new ArrayDeque<>();
        for (int v = 0; v < vertex; v++)
            if (indegress[v] == 0) queue.add(v);

        while (!queue.isEmpty()) {
            Integer v = queue.remove();
            for (int w : digraph.adj(v)) {
                indegress[w]--;
                if (indegress[w] == 0) queue.add(w);
            }
        }

        // there is a directed cycle in subgraph of vertices with indegree >= 1.
        int[] edgeTo = new int[vertex];
        int root = -1; // any vertex with indegree >= -1
        for (int v = 0; v < vertex; v++) {
            if (indegress[v] == 0) continue;
            else root = v;
            for (int w : digraph.adj(v)) {
                if (indegress[w] > 0)
                    edgeTo[w] = v;
            }
        }

        if (root != -1) {
            // find any vertex on cycle
            boolean[] visited = new boolean[vertex];
            while (!visited[root]) {
                visited[root] = true;
                root = edgeTo[root];
            }
            // extract cycle
            cycle = new Stack<Integer>();
            int v = root;
            do {
                cycle.push(v);
                v = edgeTo[v];
            } while (v != root);
            cycle.push(root);
        }
        assert check();
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

    /**
     * Does the digraph have a directed cycle?
     *
     * @return {@code true} if the digraph has a directed cycle, {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
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
        // create random DAG with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);
        Digraph G = DigraphGenerator.dag(V, E);

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            G.addEdge(v, w);
        }

        System.out.println(G);


        NonrecursiveDirectedCycle finder = new NonrecursiveDirectedCycle(G);
        if (finder.hasCycle()) {
            System.out.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        } else {
            System.out.println("No directed cycle");
        }
        System.out.println();
    }

}
