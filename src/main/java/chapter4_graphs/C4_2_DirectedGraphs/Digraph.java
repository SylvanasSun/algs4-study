package chapter4_graphs.C4_2_DirectedGraphs;

import chapter4_graphs.C4_1_UndirectedGraphs.Graph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac Digraph.java
 *  Execution:    java Digraph filename.txt
 *  Dependencies: Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  A graph, implemented using an array of lists.
 *  Parallel edges and self-loops are permitted.
 *
 *  % java Digraph tinyDG.txt
 *  13 vertices, 22 edges
 *  0: 5 1
 *  1:
 *  2: 0 3
 *  3: 5 2
 *  4: 3 2
 *  5: 4
 *  6: 9 4 8 0
 *  7: 6 9
 *  8: 6
 *  9: 11 10
 *  10: 12
 *  11: 4 12
 *  12: 9
 *
 ******************************************************************************/

/**
 * The {@code Digraph} class represents a directed graph of vertices
 * named 0 through <em>V</em> - 1.
 * It supports the following two primary operations: add an edge to the digraph,
 * iterate over all of the vertices adjacent from a given vertex.
 * Parallel edges and self-loops are permitted.
 * <p>
 * This implementation uses an adjacency-lists representation, which
 * is a vertex-indexed array of {@link Bag} objects.
 * All operations take constant time (in the worst case) except
 * iterating over the vertices adjacent from a given vertex, which takes
 * time proportional to the number of such vertices.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

public class Digraph implements Graph {

    private static final String NEWLINE = System.getProperty("line.separator");

    // number of vertices in this digraph
    private final int vertex;

    // number of edges in this digraph
    private int edge;

    // adj[v] = adjacency list for vertex v
    private Bag<Integer>[] adj;

    // indegree[v] = indegree of vertex v
    private int[] indegree;

    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param vertex the number of vertices
     * @throws IllegalArgumentException if {@code vertex < 0}
     */
    public Digraph(int vertex) {
        validateVertex(vertex);

        this.vertex = vertex;
        this.edge = 0;
        this.indegree = new int[vertex];
        this.adj = (Bag<Integer>[]) new Bag[vertex];
        for (int i = 0; i < vertex; i++)
            adj[i] = new Bag<Integer>();
    }

    /**
     * Initializes a digraph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param scanner the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public Digraph(Scanner scanner) {
        if (scanner == null)
            throw new IllegalArgumentException("Scanner must be not null.");

        try {
            int vertex = scanner.nextInt();
            validateVertex(vertex);
            this.vertex = vertex;
            this.indegree = new int[vertex];
            this.adj = (Bag<Integer>[]) new Bag[vertex];
            for (int i = 0; i < vertex; i++)
                adj[i] = new Bag<Integer>();

            int edge = scanner.nextInt();
            validateEdge(edge);
            for (int i = 0; i < edge; i++) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid input format in Digraph constructor", e);
        }
    }

    /**
     * Initializes a new digraph that is a deep copy of the specified digraph.
     *
     * @param digraph the digraph to copy
     */
    public Digraph(Digraph digraph) {
        this(digraph.vertex);
        this.edge = digraph.edge;

        for (int v = 0; v < vertex; v++)
            this.indegree[v] = digraph.indegree(v);

        for (int v = 0; v < vertex; v++) {
            Stack<Integer> reverse = new Stack<>();
            for (int w : digraph.adj(v))
                reverse.push(w);
            for (int w : reverse)
                this.adj[v].add(w);
        }
    }

    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    @Override
    public int vertex() {
        return vertex;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    @Override
    public int edge() {
        return edge;
    }

    /**
     * Adds the directed edge v→w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < vertex} and {@code 0 <= w < vertex}
     */
    @Override
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        indegree[w]++;
        edge++;
    }

    /**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
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

    @Override
    @Deprecated
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the reverse of the digraph.
     *
     * @return the reverse of the digraph
     */
    public Digraph reverse() {
        Digraph reverse = new Digraph(vertex);
        for (int v = 0; v < vertex; v++) {
            for (int w : adj[v]) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     * followed by the <em>V</em> adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Vertexes: %s, Edges: %s", vertex, edge));
        sb.append(NEWLINE);
        for (int v = 0; v < vertex; v++) {
            sb.append(String.format("vertex %d, ", v));
            sb.append(String.format("indegree: %d, outdegree: %d", indegree(v), outdegree(v)));
            sb.append(NEWLINE);
            sb.append("adjacent point: ");
            for (int w : adj[v])
                sb.append(w).append(" ");

            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    private void validateEdge(int edge) {
        if (edge < 0)
            throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative.");
    }

    private void validateVertex(int vertex) {
        if (vertex < 0)
            throw new IllegalArgumentException("Number of vertex in a Digraph must be nonnegative.");
    }

    public static void main(String[] args) {
        InputStream inputStream
                = Digraph.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);
        System.out.println(digraph);
    }
}
