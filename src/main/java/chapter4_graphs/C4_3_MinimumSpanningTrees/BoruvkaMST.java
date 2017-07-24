package chapter4_graphs.C4_3_MinimumSpanningTrees;

/******************************************************************************
 *  Compilation:  javac BoruvkaMST.java
 *  Execution:    java BoruvkaMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Bag.java
 *                UF.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Boruvka's algorithm.
 *
 *  % java BoruvkaMST tinyEWG.txt
 *  0-2 0.26000
 *  6-2 0.40000
 *  5-7 0.28000
 *  4-5 0.35000
 *  2-3 0.17000
 *  1-7 0.19000
 *  0-7 0.16000
 *  1.81000
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.UF;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code BoruvkaMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph.
 * The edge weights can be positive, zero, or negative and need not
 * be distinct. If the graph is not connected, it computes a <em>minimum
 * spanning forest</em>, which is the union of minimum spanning trees
 * in each connected component. The {@code weight()} method returns the
 * weight of a minimum spanning tree and the {@code edges()} method
 * returns its edges.
 * <p>
 * This implementation uses <em>Boruvka's algorithm</em> and the union-find
 * data type.
 * The constructor takes time proportional to <em>E</em> log <em>V</em>
 * and extra space (not including the graph) proportional to <em>V</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code weight()} method takes constant time
 * and the {@code edges()} method takes time proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * For alternate implementations, see {@link LazyPrimMST}, {@link PrimMST},
 * and {@link KruskalMST}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BoruvkaMST {

    // edges in MST
    private Bag<Edge> mst;

    // weight of MST
    private double weight;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param graph the edge-weighted graph
     */
    public BoruvkaMST(EdgeWeightedGraph graph) {
        this.mst = new Bag<>();
        UF uf = new UF(graph.vertex());

        // repeat at most log V times or until we have V-1 edges
        for (int t = 1; t < graph.vertex() && mst.size() < graph.vertex() - 1; t = t + t) {

            // foreach tree in forest, find closest edge
            // if edge weights are equal, ties are broken in favor of first edge in G.edges()
            Edge[] closest = new Edge[graph.vertex()];
            for (Edge e : graph.edges()) {
                int v = e.either(), w = e.other(v);
                int i = uf.find(v), j = uf.find(w);
                if (i == j) continue;   // same tree
                if (closest[i] == null || less(e, closest[i])) closest[i] = e;
                if (closest[j] == null || less(e, closest[j])) closest[j] = e;
            }

            // add newly discovered edges to MST
            for (int i = 0; i < graph.vertex(); i++) {
                Edge e = closest[i];
                if (e != null) {
                    int v = e.either(), w = e.other(v);
                    // don't add the same edge twice
                    if (!uf.connected(v, w)) {
                        mst.add(e);
                        weight += e.weight();
                        uf.union(v, w);
                    }
                }
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

    // is the weight of edge e strictly less than that of edge f?
    private static boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }

    public static void main(String[] args) {
        InputStream inputStream
                = BoruvkaMST.class.getResourceAsStream("/graph_file/C4_3_MinimumSpanningTrees/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(scanner);
        BoruvkaMST boruvkaMST = new BoruvkaMST(graph);

        for (Edge e : boruvkaMST.edges()) {
            System.out.println(e);
        }
        System.out.printf("%.5f\n", boruvkaMST.weight());
    }

}
