package chapter4_graphs.C4_4_ShortestPaths;

/******************************************************************************
 *  Compilation:  javac EdgeWeightedDigraph.java
 *  Execution:    java EdgeWeightedDigraph digraph.txt
 *  Dependencies: Bag.java DirectedEdge.java
 *  Data files:   http://algs4.cs.princeton.edu/44st/tinyEWD.txt
 *                http://algs4.cs.princeton.edu/44st/mediumEWD.txt
 *                http://algs4.cs.princeton.edu/44st/largeEWD.txt
 *
 *  An edge-weighted digraph, implemented using adjacency lists.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * The {@code EdgeWeightedDigraph} class represents a edge-weighted
 * digraph of vertices named 0 through <em>V</em> - 1, where each
 * directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 * It supports the following two primary operations: add a directed edge
 * to the digraph and iterate over all of edges incident from a given vertex.
 * It also provides
 * methods for returning the number of vertices <em>V</em> and the number
 * of edges <em>E</em>. Parallel edges and self-loops are permitted.
 * <p>
 * This implementation uses an adjacency-lists representation, which
 * is a vertex-indexed array of {@link Bag} objects.
 * All operations take constant time (in the worst case) except
 * iterating over the edges incident from a given vertex, which takes
 * time proportional to the number of such edges.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class EdgeWeightedDigraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    // number of vertices in this digraph
    private final int vertex;

    // number of edges in this digraph
    private int edge;

    // adj[v] = adjacency list for vertex v
    private Bag<DirectedEdge>[] adj;

    // indegree[v] = indegree of vertex v
    private int[] indegree;

    /**
     * Initializes an empty edge-weighted digraph with {@code vertex} vertices and 0 edges.
     *
     * @param vertex the number of vertices
     * @throws IllegalArgumentException if {@code vertex < 0}
     */
    public EdgeWeightedDigraph(int vertex) {
        String message = String.format("Vertex %d must be positive number!", vertex);
        validatePositiveNumber(message, vertex);

        this.vertex = vertex;
        this.edge = 0;
        this.indegree = new int[vertex];
        this.adj = (Bag<DirectedEdge>[]) new Bag[vertex];
        for (int v = 0; v < vertex; v++)
            adj[v] = new Bag<>();
    }

    /**
     * Initializes a random edge-weighted digraph with {@code vertex} vertices and <em>E</em> edges.
     *
     * @param vertex the number of vertices
     * @param edge   the number of edges
     * @throws IllegalArgumentException if {@code vertex < 0}
     * @throws IllegalArgumentException if {@code edge < 0}
     */
    public EdgeWeightedDigraph(int vertex, int edge) {
        this(vertex);
        String message = String.format("Edge %d must be positive number!", edge);
        validatePositiveNumber(message, edge);

        Random random = new Random();
        for (int i = 0; i < edge; i++) {
            int v = random.nextInt(vertex);
            int w = random.nextInt(vertex);
            double weight = 0.01 * random.nextInt(100);
            DirectedEdge directedEdge = new DirectedEdge(v, w, weight);
            addEdge(directedEdge);
        }
    }


    /**
     * Initializes an edge-weighted digraph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param scanner the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public EdgeWeightedDigraph(Scanner scanner) {
        this(scanner.nextInt());
        int edge = scanner.nextInt();
        String message = String.format("Edge %d must be positive number!", edge);
        validatePositiveNumber(message, edge);

        for (int i = 0; i < edge; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            validateVertex(v);
            validateVertex(w);
            double weight = scanner.nextDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of {@code digraph}.
     *
     * @param digraph the edge-weighted digraph to copy
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph digraph) {
        this(digraph.vertex());
        this.edge = digraph.edge();

        for (int v = 0; v < vertex; v++)
            this.indegree[v] = digraph.indegree(v);
        for (int v = 0; v < vertex; v++) {
            Stack<DirectedEdge> reverse = new Stack<>();
            for (DirectedEdge e : digraph.adj(v))
                reverse.push(e);
            for (DirectedEdge e : reverse)
                adj[v].add(e);
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int vertex() {
        return vertex;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int edge() {
        return edge;
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
     *                                  and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        edge++;
    }


    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < vertex; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertex + " " + edge + NEWLINE);
        for (int v = 0; v < vertex; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


    private void validatePositiveNumber(String message, int... numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < 0)
                throw new IllegalArgumentException(message);
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= vertex)
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (vertex - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = EdgeWeightedDigraph.class.getResourceAsStream("/graph_file/C4_3_MinimumSpanningTrees/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(scanner);
        System.out.println(edgeWeightedDigraph);
    }

}
