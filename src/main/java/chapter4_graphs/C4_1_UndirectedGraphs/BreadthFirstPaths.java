package chapter4_graphs.C4_1_UndirectedGraphs;

/******************************************************************************
 *  Compilation:  javac BreadthFirstPaths.java
 *  Execution:    java BreadthFirstPaths G s
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run breadth first search on an undirected graph.
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
 *  %  java BreadthFirstPaths tinyCG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (1):  0-1
 *  0 to 2 (1):  0-2
 *  0 to 3 (2):  0-2-3
 *  0 to 4 (2):  0-2-4
 *  0 to 5 (1):  0-5
 *
 *  %  java BreadthFirstPaths largeG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (418):  0-932942-474885-82707-879889-971961-...
 *  0 to 2 (323):  0-460790-53370-594358-780059-287921-...
 *  0 to 3 (168):  0-713461-75230-953125-568284-350405-...
 *  0 to 4 (144):  0-460790-53370-310931-440226-380102-...
 *  0 to 5 (566):  0-932942-474885-82707-879889-971961-...
 *  0 to 6 (349):  0-932942-474885-82707-879889-971961-...
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * The {@code BreadthFirstPaths} class represents a data type for finding
 * shortest paths (number of edges) from a source vertex <em>s</em>
 * (or a set of source vertices)
 * to every other vertex in an undirected graph.
 * <p>
 * This implementation uses breadth-first search.
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
public class BreadthFirstPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private final Graph graph;
    private final boolean[] marked;  // marked[v] = is there an s-v path
    private final int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private final int[] distTo;      // distTo[v] = number of edges shortest s-v path


    /**
     * Computes the shortest path between the source vertex {@code originPoint}
     * and every other vertex in the graph {@code graph}.
     *
     * @param graph       the graph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BreadthFirstPaths(Graph graph, int originPoint) {
        this.graph = graph;
        int vertex = graph.vertex();
        marked = new boolean[vertex];
        edgeTo = new int[vertex];
        distTo = new int[vertex];
        for (int i = 0; i < vertex; i++)
            distTo[i] = INFINITY;
        validateVertex(originPoint);
        bfs(originPoint);
    }

    /**
     * Computes the shortest path between any one of the source vertices in {@code sources}
     * and every other vertex in graph {@code graph}.
     *
     * @param graph   the graph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless {@code 0 <= s < V} for each vertex
     *                                  {@code s} in {@code sources}
     */
    public BreadthFirstPaths(Graph graph, Iterable<Integer> sources) {
        this.graph = graph;
        int vertex = graph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        this.distTo = new int[vertex];
        for (int i = 0; i < vertex; i++)
            distTo[i] = INFINITY;
        validateVertices(sources);
        bfs(sources);
    }

    /**
     * Is there a path between the source vertex {@code originPoint} (or sources) and vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code originPoint}
     * (or sources) and vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int vertex) {
        validateVertex(vertex);
        return distTo[vertex];
    }

    /**
     * Returns a shortest path between the source vertex {@code originPoint} (or sources)
     * and {@code vertex}, or {@code null} if no such path.
     *
     * @param vertex the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int vertex) {
        validateVertex(vertex);

        Stack<Integer> path = new Stack<>();
        int x;
        for (x = vertex; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    // check optimality conditions for single source
    private boolean check(Graph G, int s) {

        // check that the distance of s = 0
        if (distTo[s] != 0) {
            System.out.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        // check that for each edge v-w dist[w] <= dist[v] + 1
        // provided v is reachable from s
        for (int v = 0; v < G.vertex(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
        // provided v is reachable from s
        for (int w = 0; w < G.vertex(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                System.out.println("shortest path edge " + v + "-" + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    private void bfs(int vertex) {
        Queue<Integer> queue = new ArrayDeque<>();
        marked[vertex] = true;
        distTo[vertex] = 0;
        queue.add(vertex);

        searchAndMarkAdjacent(queue);
    }

    private void bfs(Iterable<Integer> sources) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int v : sources) {
            marked[v] = true;
            distTo[v] = 0;
            queue.add(v);
        }

        searchAndMarkAdjacent(queue);
    }

    private void searchAndMarkAdjacent(Queue<Integer> queue) {
        while (!queue.isEmpty()) {
            Integer v = queue.remove();
            for (int adj : graph.adj(v)) {
                if (!marked[adj]) {
                    marked[adj] = true;
                    edgeTo[adj] = v;
                    distTo[adj] = distTo[v] + 1;
                    queue.add(adj);
                }
            }
        }
    }

    private void validateVertex(int vertex) {
        int length = marked.length;
        if (vertex < 0 || vertex >= length)
            throw new IllegalArgumentException("Vertex " + vertex + " is not between 0 and " + (length - 1));
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null)
            throw new IllegalArgumentException("Vertices is null.");

        int length = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= length)
                throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (length - 1));
        }
    }

    public static void main(String[] args) {
        InputStream inputStream
                = BreadthFirstPaths.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Queue<Integer> queue = null;
        int originPoint = 0;
        if (args.length > 2) {
            queue = new ArrayDeque<>();
            for (int i = 1; i < args.length; i++)
                queue.add(Integer.valueOf(args[i]));
        } else {
            originPoint = Integer.valueOf(args[1]);
        }
        Scanner scanner = new Scanner(inputStream);
        Graph graph = new UndirectedGraph(scanner);
        BreadthFirstPaths breadthFirstPaths;
        if (queue != null)
            breadthFirstPaths = new BreadthFirstPaths(graph, queue);
        else
            breadthFirstPaths = new BreadthFirstPaths(graph, originPoint);

        for (int v = 0; v < graph.vertex(); v++) {
            if (breadthFirstPaths.hasPathTo(v)) {
                int s = originPoint;
                if (breadthFirstPaths.distTo(v) == 0)
                    s = v;
                System.out.printf("%d to %d path: ", s, v);
                Iterable<Integer> path = breadthFirstPaths.pathTo(v);
                for (int w : path) {
                    if (w == s)
                        System.out.printf("%d", w);
                    else
                        System.out.printf("-%d", w);
                }
                System.out.printf("\n");
            } else {
                System.out.printf("%d not connected!\n", v);
            }
        }
    }

}
