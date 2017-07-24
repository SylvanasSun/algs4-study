package chapter4_graphs.C4_3_MinimumSpanningTrees;

/******************************************************************************
 *  Compilation:  javac KruskalMST.java
 *  Execution:    java  KruskalMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                UF.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Kruskal's algorithm.
 *
 *  %  java KruskalMST tinyEWG.txt
 *  0-7 0.16000
 *  2-3 0.17000
 *  1-7 0.19000
 *  0-2 0.26000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 *  % java KruskalMST mediumEWG.txt
 *  168-231 0.00268
 *  151-208 0.00391
 *  7-157   0.00516
 *  122-205 0.00647
 *  8-152   0.00702
 *  156-219 0.00745
 *  28-198  0.00775
 *  38-126  0.00845
 *  10-123  0.00886
 *  ...
 *  10.46351
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.UF;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * The {@code KruskalMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph.
 * The edge weights can be positive, zero, or negative and need not
 * be distinct. If the graph is not connected, it computes a <em>minimum
 * spanning forest</em>, which is the union of minimum spanning trees
 * in each connected component. The {@code weight()} method returns the
 * weight of a minimum spanning tree and the {@code edges()} method
 * returns its edges.
 * <p>
 * This implementation uses <em>Krusal's algorithm</em> and the
 * union-find data type.
 * The constructor takes time proportional to <em>E</em> log <em>E</em>
 * and extra space (not including the graph) proportional to <em>V</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code weight()} method takes constant time
 * and the {@code edges()} method takes time proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * For alternate implementations, see {@link LazyPrimMST}, {@link PrimMST},
 * and {@link BoruvkaMST}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class KruskalMST {

    // edges in MST
    private final Queue<Edge> mst;

    // weight of MST
    private double weight;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param graph the edge-weighted graph
     */
    public KruskalMST(EdgeWeightedGraph graph) {
        this.mst = new ArrayDeque<>();
        // more efficient to build heap by passing array of edges
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for (Edge e : graph.edges()) {
            pq.add(e);
        }

        // run greedy algorithm
        int vertex = graph.vertex();
        UF uf = new UF(vertex);
        while (!pq.isEmpty() && mst.size() < vertex - 1) {
            Edge e = pq.poll();
            int v = e.either();
            int w = e.other(v);
            // v-w does not create a cycle
            if (!uf.connected(v, w)) {
                uf.union(v, w); // merge v and w components
                mst.add(e);
                weight += e.weight();
            }
        }
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

    public static void main(String[] args) {
        InputStream inputStream
                = KruskalMST.class.getResourceAsStream("/graph_file/C4_3_MinimumSpanningTrees/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(scanner);
        KruskalMST kruskalMST = new KruskalMST(graph);

        for (Edge e : kruskalMST.edges()) {
            System.out.println(e);
        }
        System.out.printf("%.5f\n", kruskalMST.weight());
    }

}
