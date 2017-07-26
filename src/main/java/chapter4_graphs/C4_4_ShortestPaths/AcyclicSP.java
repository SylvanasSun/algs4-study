package chapter4_graphs.C4_4_ShortestPaths;

/******************************************************************************
 *  Compilation:  javac AcyclicSP.java
 *  Execution:    java AcyclicSP V E
 *  Dependencies: EdgeWeightedDigraph.java DirectedEdge.java Topological.java
 *  Data files:   http://algs4.cs.princeton.edu/44sp/tinyEWDAG.txt
 *
 *  Computes shortest paths in an edge-weighted acyclic digraph.
 *
 *  % java AcyclicSP tinyEWDAG.txt 5
 *  5 to 0 (0.73)  5->4  0.35   4->0  0.38
 *  5 to 1 (0.32)  5->1  0.32
 *  5 to 2 (0.62)  5->7  0.28   7->2  0.34
 *  5 to 3 (0.61)  5->1  0.32   1->3  0.29
 *  5 to 4 (0.35)  5->4  0.35
 *  5 to 5 (0.00)
 *  5 to 6 (1.13)  5->1  0.32   1->3  0.29   3->6  0.52
 *  5 to 7 (0.28)  5->7  0.28
 *
 ******************************************************************************/

import chapter4_graphs.C4_2_DirectedGraphs.Topological;
import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code AcyclicSP} class represents a data type for solving the
 * single-source shortest paths problem in edge-weighted directed acyclic
 * graphs (DAGs). The edge weights can be positive, negative, or zero.
 * <p>
 * This implementation uses a topological-sort based algorithm.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code distTo()} and {@code hasPathTo()} methods take
 * constant time and the {@code pathTo()} method takes time proportional to the
 * number of edges in the shortest path returned.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class AcyclicSP {

    // distTo[v] = distance  of shortest s->v path
    private double[] distTo;

    // edgeTo[v] = last edge on shortest s->v path
    private DirectedEdge[] edgeTo;

    /**
     * Computes a shortest paths tree from {@code s} to every other vertex in
     * the directed acyclic graph {@code digraph}.
     *
     * @param digraph the acyclic digraph
     * @param s       the source vertex
     * @throws IllegalArgumentException if the digraph is not acyclic
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public AcyclicSP(EdgeWeightedDigraph digraph, int s) {
        int vertex = digraph.vertex();
        distTo = new double[vertex];
        edgeTo = new DirectedEdge[vertex];

        validateVertex(s);

        for (int v = 0; v < vertex; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // visit vertices in topological order
        Topological topological = new Topological(digraph);
        if (!topological.hasOrder())
            throw new IllegalArgumentException("Digraph is not acyclic.");
        for (int v : topological.order()) {
            for (DirectedEdge e : digraph.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        double weight = distTo[v] + e.weight();
        if (distTo[w] > weight) {
            distTo[w] = weight;
            edgeTo[w] = e;
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
     * {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Is there a path from the source vertex {@code s} to vertex {@code v}?
     *
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     * {@code s} to vertex {@code v}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     * as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = AcyclicSP.class.getResourceAsStream("/graph_file/C4_4_ShortestPaths/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        EdgeWeightedDigraph digraph = new EdgeWeightedDigraph(scanner);
        Integer s = Integer.valueOf(args[1]);
        AcyclicSP acyclicSP = new AcyclicSP(digraph, s);

        for (int v = 0; v < digraph.vertex(); v++) {
            if (acyclicSP.hasPathTo(v)) {
                System.out.printf("%d to %d (%5.2f): ", s, v, acyclicSP.distTo(v));
                for (DirectedEdge e : acyclicSP.pathTo(v)) {
                    System.out.print(e + " ");
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d not have shortest path.", s, v);
            }
        }
    }

}
