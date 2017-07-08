package chapter4_graphs.C4_1_UndirectedGraphs;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac CC.java
 *  Execution:    java CC filename.txt
 *  Dependencies: Graph.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *  % java CC tinyG.txt
 *  3 components
 *  0 1 2 3 4 5 6
 *  7 8
 *  9 10 11 12
 *
 *  % java CC mediumG.txt
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  % java -Xss50m CC largeG.txt
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  Note: This implementation uses a recursive DFS. To avoid needing
 *        a potentially very large stack size, replace with a non-recurisve
 *        DFS ala NonrecursiveDFS.java.
 *
 ******************************************************************************/

/**
 * The {@code CC} class represents a data type for
 * determining the connected components in an undirected graph.
 * The <em>id</em> operation determines in which connected component
 * a given vertex lies; the <em>connected</em> operation
 * determines whether two vertices are in the same connected component;
 * the <em>count</em> operation determines the number of connected
 * components; and the <em>size</em> operation determines the number
 * of vertices in the connect component containing a given vertex.
 *
 * The <em>component identifier</em> of a connected component is one of the
 * vertices in the connected component: two vertices have the same component
 * identifier if and only if they are in the same connected component.
 *
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>id</em>, <em>count</em>, <em>connected</em>,
 * and <em>size</em> operations take constant time.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class ConnectedComponent {

    private final Graph graph;

    // marked[v] = has vertex v been marked?
    private final boolean[] marked;

    // id[v] = id of connected component containing v
    private final int[] id;

    // size[id] = number of vertices in given component
    private final int[] size;

    // number of connected components
    private int count;

    /**
     * Computes the connected components of the undirected graph {@code graph}.
     *
     * @param graph the undirected graph
     */
    public ConnectedComponent(Graph graph) {
        this.graph = graph;
        int vertex = graph.vertex();
        this.marked = new boolean[vertex];
        this.id = new int[vertex];
        this.size = new int[vertex];

        for (int v = 0; v < vertex; v++) {
            if (!marked[v]) {
                dfs(v);
                count++;
            }
        }
    }

    /**
     * Returns the component id of the connected component containing vertex {@code vertex}.
     *
     * @param vertex the vertex
     * @return the component id of the connected component containing vertex {@code vertex}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int id(int vertex) {
        validateVertex(vertex);
        return id[vertex];
    }

    /**
     * Returns the number of vertices in the connected component containing vertex {@code vertex}.
     *
     * @param vertex the vertex
     * @return the number of vertices in the connected component containing vertex {@code vertex}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int size(int vertex) {
        validateVertex(vertex);
        return size[id[vertex]];
    }

    /**
     * Returns the number of connected components in the graph {@code graph}.
     *
     * @return the number of connected components in the graph {@code graph}
     */
    public int count() {
        return count;
    }

    /**
     * Returns true if vertices {@code v} and {@code w} are in the same
     * connected component.
     *
     * @param v one vertex
     * @param w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     * connected component; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        id[vertex] = count;
        size[count]++;

        for (int adj : graph.adj(vertex)) {
            if (!marked[adj])
                dfs(adj);
        }
    }

    private void validateVertex(int vertex) {
        int length = marked.length;
        if (vertex < 0 || vertex >= length)
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (length - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = ConnectedComponent.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Graph graph = new UndirectedGraph(scanner);
        ConnectedComponent cc = new ConnectedComponent(graph);

        int c = cc.count();
        Queue<Integer>[] queues = (ArrayDeque<Integer>[]) new ArrayDeque[c];
        for (int i = 0; i < c; i++)
            queues[i] = new ArrayDeque<>();
        System.out.printf("Connected Components: %d\n", c);

        for (int v = 0; v < graph.vertex(); v++)
            queues[cc.id(v)].add(v);

        for (int i = 0; i < c; i++) {
            System.out.printf("Connected Components %d ", i);
            System.out.printf("Have size %d\n", cc.size(queues[i].peek()));
            System.out.printf("Vertex: ");
            for (int v : queues[i]) {
                System.out.printf("%d ", v);
            }
            System.out.printf("\n");
        }
    }

}
