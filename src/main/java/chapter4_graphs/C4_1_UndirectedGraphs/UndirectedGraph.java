package chapter4_graphs.C4_1_UndirectedGraphs;

/******************************************************************************
 *  Compilation:  javac UndirectedGraph.java
 *  Execution:    java UndirectedGraph input.txt
 *  Dependencies: Bag.java Stack.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  A undirected graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java UndirectedGraph tinyG.txt
 *  13 vertices, 13 edges
 *  0: 6 2 1 5
 *  1: 0
 *  2: 0
 *  3: 5 4
 *  4: 5 6 3
 *  5: 3 4 0
 *  6: 0 4
 *  7: 8
 *  8: 7
 *  9: 11 10 12
 *  10: 9
 *  11: 9 12
 *  12: 11 9
 *
 *  % java UndirectedGraph mediumG.txt
 *  250 vertices, 1273 edges
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15
 *  1: 220 203 200 194 189 164 150 130 107 72
 *  2: 141 110 108 86 79 51 42 18 14
 *  ...
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The {@code UndirectedGraph} class represents an undirected graph of vertices
 * named 0 through <em>V</em> – 1.
 * It supports the following two primary operations: add an edge to the graph,
 * iterate over all of the vertices adjacent to a vertex. It also provides
 * methods for returning the number of vertices <em>V</em> and the number
 * of edges <em>E</em>. Parallel edges and self-loops are permitted.
 * By convention, a self-loop <em>v</em>-<em>v</em> appears in the
 * adjacency list of <em>v</em> twice and contributes two to the degree
 * of <em>v</em>.
 * <p>
 * This implementation uses an adjacency-lists representation, which
 * is a vertex-indexed array of {@link Bag} objects.
 * All operations take constant time (in the worst case) except
 * iterating over the vertices adjacent to a given vertex, which takes
 * time proportional to the number of such vertices.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class UndirectedGraph implements Graph {

    private static final String NEW_LINE_SEPARATOR = System.getProperty("line.separator");
    private final int vertex;
    private int edge;
    private final Bag<Integer>[] adjacent;

    /**
     * Initializes an empty undirected graph with {@code vertex} vertices and 0 edges.
     * param vertex the number of vertices
     *
     * @param vertex number of vertices
     * @throws IllegalArgumentException if {@code V <= 0}
     */
    public UndirectedGraph(int vertex) {
        checkVertex(vertex);

        this.vertex = vertex;
        this.edge = 0;
        this.adjacent = (Bag<Integer>[]) new Bag[vertex];
        for (int v = 0; v < vertex; v++)
            adjacent[v] = new Bag<Integer>();
    }

    /**
     * Initializes a undirected graph from the specified input stream.
     * The format is the number of vertices <em>vertex</em>,
     * followed by the number of edges <em>edge</em>,
     * followed by <em>edge</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param scanner the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public UndirectedGraph(Scanner scanner) {
        if (scanner == null)
            throw new IllegalArgumentException("Specified input stream must not null!");

        try {
            this.vertex = scanner.nextInt();
            checkVertex(this.vertex);
            int edge = scanner.nextInt();
            checkEdge(this.edge);
            this.adjacent = (Bag<Integer>[]) new Bag[this.vertex];
            for (int v = 0; v < this.vertex; v++)
                adjacent[v] = new Bag<Integer>();

            for (int i = 0; i < edge; i++) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid input format in Undirected Graph constructor", e);
        }
    }

    /**
     * Initializes a new undirected graph that is a deep copy of {@code graph}.
     *
     * @param graph the graph to copy
     */
    public UndirectedGraph(Graph graph) {
        this(graph.vertex());
        this.edge = graph.edge();
        for (int v = 0; v < this.vertex; v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> stack = new Stack<Integer>();
            for (int w : graph.adj(v))
                stack.push(w);
            for (int w : stack)
                adjacent[v].add(w);
        }
    }

    private void checkVertex(int vertex) {
        if (vertex <= 0)
            throw new IllegalArgumentException("Number of vertices must be positive number!");
    }

    private void checkEdge(int edge) {
        if (edge < 0)
            throw new IllegalArgumentException("Number of edges must be positive number!");
    }

    /**
     * Returns the number of vertices in this undirected graph.
     *
     * @return the number of vertices in this undirected graph
     */
    public int vertex() {
        return vertex;
    }


    /**
     * Returns the number of edges in this undirected graph.
     *
     * @return the number of edges in this undirected graph
     */
    public int edge() {
        return edge;
    }

    /**
     * Adds the undirected edge v-w to this undirected graph.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adjacent[v].add(w);
        adjacent[w].add(v);
        edge++;
    }

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adjacent[v];
    }


    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param v the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adjacent[v].size();
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= this.vertex)
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (this.vertex - 1));
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>vertex</em>, followed by the number of edges <em>edge</em>,
     * followed by the <em>vertex</em> adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertex).append(" Edges: ").append(edge).append(NEW_LINE_SEPARATOR);
        for (int v = 0; v < vertex; v++) {
            sb.append(v).append(": ");
            for (int w : adjacent[v])
                sb.append(w).append(" ");
            sb.append(NEW_LINE_SEPARATOR);
        }
        return sb.toString();
    }

    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream =
                UndirectedGraph.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Graph graph = new UndirectedGraph(scanner);
        System.out.println(graph);
    }
}
