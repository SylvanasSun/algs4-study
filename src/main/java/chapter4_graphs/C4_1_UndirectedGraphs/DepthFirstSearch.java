package chapter4_graphs.C4_1_UndirectedGraphs;

/******************************************************************************
 *  Compilation:  javac DepthFirstSearch.java
 *  Execution:    java DepthFirstSearch filename.txt s
 *  Dependencies: Graph.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6
 *  NOT connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12
 *  NOT connected
 *
 ******************************************************************************/

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code DepthFirstSearch} class represents a data type for
 * determining the vertices connected to a given source vertex <em>s</em>
 * in an undirected graph. For versions that find the paths, see
 * {@link DepthFirstPaths} and {@link BreadthFirstPaths}.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * It uses extra space (not including the graph) proportional to <em>V</em>.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstSearch {

    private final boolean[] marked; // marked[v] = is there an s-v path?
    private int count;  // number of vertices connected to s
    private final Graph graph;

    /**
     * Computes the vertices in graph {@code graph} that are
     * connected to the source vertex {@code originPoint}.
     *
     * @param graph       the graph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearch(Graph graph, int originPoint) {
        this.graph = graph;
        this.count = 0;
        this.marked = new boolean[graph.vertex()];
        validateVertex(originPoint);
        depthSearch(originPoint);
    }

    /**
     * Returns the number of vertices connected to the source vertex {@code originPoint}.
     *
     * @return the number of vertices connected to the source vertex {@code originPoint}
     */
    public int count() {
        return count;
    }

    /**
     * Is there a path between the source vertex {@code originPoint} and vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    private void depthSearch(int vertex) {
        marked[vertex] = true;
        count++;
        for (int adj : graph.adj(vertex)) {
            if (!marked[adj])
                depthSearch(adj);
        }
    }

    private void validateVertex(int vertex) {
        int length = marked.length;
        if (vertex < 0 || vertex >= length)
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (length - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream =
                DepthFirstSearch.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Graph graph = new UndirectedGraph(scanner);
        Integer originPoint = Integer.parseInt(args[1]);
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(graph, originPoint);

        for (int v = 0; v < graph.vertex(); v++) {
            if (depthFirstSearch.marked(v))
                System.out.printf("%d ", v);
        }
        System.out.printf("\n");
        if (depthFirstSearch.count() != graph.vertex())
            System.out.printf("Not Connected!\n");
        else
            System.out.printf("Connected!\n");
    }

}
