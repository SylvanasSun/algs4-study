package chapter4_graphs.C4_1_UndirectedGraphs;

/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5
 *  1: 0 2
 *  2: 0 1 3 4
 *  3: 5 4 2
 *  4: 3 2
 *  5: 3 0
 *
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code DepthFirstPaths} class represents a data type for finding
 * paths from a source vertex <em>s</em> to every other vertex
 * in an undirected graph.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * It uses extra space (not including the graph) proportional to <em>V</em>.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 * of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstPaths {

    private final Graph graph;
    private final boolean[] marked; // marked[v] = is there an s-v path?
    private final int[] edgeTo; // edgeTo[v] = last edge on s-v path
    private final int originPoint;

    /**
     * Computes a path between {@code graph} and every other vertex in graph {@code originPoint}.
     *
     * @param graph       the graph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstPaths(Graph graph, int originPoint) {
        int vertex = graph.vertex();
        this.graph = graph;
        this.originPoint = originPoint;
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        validateVertex(originPoint);
        dfs(originPoint);
    }

    /**
     * Is there a path between the source vertex {@code originPoint} and vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    /**
     * Returns a path between the source vertex {@code originPoint} and vertex {@code vertex}, or
     * {@code null} if no such path.
     *
     * @param vertex the vertex
     * @return the sequence of vertices on a path between the source vertex
     * {@code originPoint} and vertex {@code vertex}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int vertex) {
        validateVertex(vertex);

        Stack<Integer> stack = new Stack<>();
        for (int x = vertex; x != originPoint; x = edgeTo[x])
            stack.push(x);
        stack.push(originPoint);
        return stack;
    }

    private void dfs(int vertex) {
        marked[vertex] = true;

        for (int adj : graph.adj(vertex)) {
            if (!marked[adj]) {
                marked[adj] = true;
                edgeTo[adj] = vertex;
                dfs(adj);
            }
        }
    }

    private void validateVertex(int vertex) {
        int length = marked.length;
        if (vertex < 0 || vertex >= length)
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (length - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = DepthFirstPaths.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Integer originPoint = Integer.valueOf(args[1]);
        Scanner scanner = new Scanner(inputStream);
        Graph graph = new UndirectedGraph(scanner);
        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, originPoint);

        for (int v = 0; v < graph.vertex(); v++) {
            if (depthFirstPaths.hasPathTo(v)) {
                System.out.printf("%d to %d path: ", originPoint, v);
                Iterable<Integer> paths = depthFirstPaths.pathTo(v);
                for (int x : paths) {
                    if (x == originPoint)
                        System.out.printf("%d", x);
                    else
                        System.out.printf("-%d", x);
                }
                System.out.printf("\n");
            } else {
                System.out.printf("%d to %d is not connected!\n", originPoint, v);
            }
        }
    }

}
