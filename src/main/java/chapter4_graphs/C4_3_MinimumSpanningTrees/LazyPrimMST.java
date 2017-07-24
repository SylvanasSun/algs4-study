package chapter4_graphs.C4_3_MinimumSpanningTrees;

/******************************************************************************
 *  Compilation:  javac LazyPrimMST.java
 *  Execution:    java LazyPrimMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                MinPQ.java UF.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using a lazy version of Prim's
 *  algorithm.
 *
 *  %  java LazyPrimMST tinyEWG.txt
 *  0-7 0.16000
 *  1-7 0.19000
 *  0-2 0.26000
 *  2-3 0.17000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 *  % java LazyPrimMST mediumEWG.txt
 *  0-225   0.02383
 *  49-225  0.03314
 *  44-49   0.02107
 *  44-204  0.01774
 *  49-97   0.03121
 *  202-204 0.04207
 *  176-202 0.04299
 *  176-191 0.02089
 *  68-176  0.04396
 *  58-68   0.04795
 *  10.46351
 *
 *  % java LazyPrimMST largeEWG.txt
 *  ...
 *  647.66307
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.UF;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * The {@code LazyPrimMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph.
 * The edge weights can be positive, zero, or negative and need not
 * be distinct. If the graph is not connected, it computes a <em>minimum
 * spanning forest</em>, which is the union of minimum spanning trees
 * in each connected component. The {@code weight()} method returns the
 * weight of a minimum spanning tree and the {@code edges()} method
 * returns its edges.
 * <p>
 * This implementation uses a lazy version of <em>Prim's algorithm</em>
 * with a binary heap of edges.
 * The constructor takes time proportional to <em>E</em> log <em>E</em>
 * and extra space (not including the graph) proportional to <em>E</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code weight()} method takes constant time
 * and the {@code edges()} method takes time proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * For alternate implementations, see {@link PrimMST}, {@link KruskalMST},
 * and {@link BoruvkaMST}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class LazyPrimMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12; // 1E-12 = 1 * 10^-12

    private final EdgeWeightedGraph graph;

    // total weight of MST
    private double weight;

    // edges in the MST
    private final Queue<Edge> mst;

    // marked[v] = true if v on tree
    private final boolean[] marked;

    // edges with one endpoint in tree
    private final PriorityQueue<Edge> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param graph the edge-weighted graph
     */
    public LazyPrimMST(EdgeWeightedGraph graph) {
        this.graph = graph;
        int vertex = graph.vertex();
        mst = new ArrayDeque<>();
        pq = new PriorityQueue<>();
        marked = new boolean[vertex];

        for (int v = 0; v < vertex; v++)
            if (!marked[v]) prim(v);

        assert check(graph);
    }

    private void prim(int s) {
        scanAndPushPQ(s);
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();  // smallest edge on pq
            int v = edge.either(), w = edge.other(v);  // two endpoints
            assert marked[v] || marked[w];

            if (marked[v] && marked[w])
                continue; // lazy, both v and w already scanned

            mst.add(edge); // add edge to mst
            weight += edge.weight();
            if (!marked[v]) scanAndPushPQ(v); // v becomes part of tree
            if (!marked[w]) scanAndPushPQ(w); // w becomes part of tree
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    private void scanAndPushPQ(int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : graph.adj(v))
            if (!marked[e.other(v)]) pq.add(e);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     *
     * @return the edges in a minimum spanning tree (or forest) as
     * an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     *
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.vertex());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.vertex());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }

    public static void main(String[] args) {
        InputStream inputStream
                = LazyPrimMST.class.getResourceAsStream("/graph_file/C4_3_MinimumSpanningTrees/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(scanner);
        LazyPrimMST lazyPrimMST = new LazyPrimMST(graph);
        for (Edge e : lazyPrimMST.edges()) {
            System.out.println(e);
        }
        System.out.printf("%.5f\n", lazyPrimMST.weight());
    }

}
