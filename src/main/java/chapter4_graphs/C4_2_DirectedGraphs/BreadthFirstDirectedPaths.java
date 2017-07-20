package chapter4_graphs.C4_2_DirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac BreadthFirstDirectedPaths.java
 *  Execution:    java BreadthFirstDirectedPaths digraph.txt s
 *  Dependencies: Digraph.java Queue.java Stack.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Run breadth-first search on a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 ******************************************************************************/

/**
 * The {@code BreadthDirectedFirstPaths} class represents a data type for finding
 * shortest paths (number of edges) from a source vertex <em>s</em>
 * (or set of source vertices) to every other vertex in the digraph.
 * <p>
 * This implementation uses breadth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * It uses extra space (not including the digraph) proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BreadthFirstDirectedPaths {

    private final Digraph digraph;

    // marked[v] = is there an s->v path?
    private final boolean[] marked;

    // edgeTo[v] = last edge on shortest s->v path
    private final int[] edgeTo;

    // distTo[v] = length of shortest s->v path
    private final int[] distTo;

    /**
     * Computes the shortest path from {@code s} and every other vertex in graph {@code digraph}.
     *
     * @param digraph the digraph
     * @param s       the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public BreadthFirstDirectedPaths(Digraph digraph, int s) {
        this.digraph = digraph;
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        this.distTo = new int[vertex];
        for (int v = 0; v < vertex; v++)
            distTo[v] = Integer.MAX_VALUE;
        validateVertex(s);
        bfs(s);
    }

    /**
     * Computes the shortest path from any one of the source vertices in {@code sources}
     * to every other vertex in graph {@code digraph}.
     *
     * @param digraph the digraph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless each vertex {@code v} in
     *                                  {@code sources} satisfies {@code 0 <= v < V}
     */
    public BreadthFirstDirectedPaths(Digraph digraph, Iterable<Integer> sources) {
        this.digraph = digraph;
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        this.distTo = new int[vertex];
        for (int v = 0; v < vertex; v++)
            distTo[v] = Integer.MAX_VALUE;
        validateVertices(sources);
        bfs(sources);
    }

    /**
     * Is there a directed path from the source {@code s} (or sources) to vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a directed path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    /**
     * Returns the number of edges in a shortest path from the source {@code s}
     * (or sources) to vertex {@code vertex}?
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
     * Returns a shortest path from {@code s} (or sources) to {@code vertex}, or
     * {@code null} if no such path.
     *
     * @param vertex the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int vertex) {
        validateVertex(vertex);

        Stack<Integer> stack = new Stack<>();
        int x;
        for (x = vertex; distTo[x] != 0; x = edgeTo[x])
            stack.push(x);
        stack.push(x);
        return stack;
    }

    private void bfs(int vertex) {
        marked[vertex] = true;
        distTo[vertex] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(vertex);

        while (!queue.isEmpty()) {
            Integer v = queue.remove();
            for (int w : digraph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    queue.add(w);
                }
            }
        }
    }

    private void bfs(Iterable<Integer> vertexes) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int s : vertexes) {
            marked[s] = true;
            distTo[s] = 0;
            queue.add(s);
        }

        while (!queue.isEmpty()) {
            Integer v = queue.remove();
            for (int w : digraph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    queue.add(w);
                }
            }
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }

    public static void main(String[] args) {
        InputStream inputStream
                = BreadthFirstDirectedPaths.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < args.length; i++)
            queue.add(Integer.valueOf(args[i]));

        BreadthFirstDirectedPaths directedPaths = new BreadthFirstDirectedPaths(digraph, queue);
        int originPoint = queue.peek();
        for (int v = 0; v < digraph.vertex(); v++) {
            if (directedPaths.distTo(v) == 0)
                originPoint = v;
            System.out.printf("%d to %d: ", originPoint, v);
            if (directedPaths.hasPathTo(v)) {
                Iterable<Integer> pathTo = directedPaths.pathTo(v);
                for (int w : pathTo) {
                    if (w == originPoint)
                        System.out.printf("%d", w);
                    else
                        System.out.printf("->%d", w);
                }
            } else {
                System.out.printf("not connected.");
            }
            System.out.printf("\n");
        }
    }

}
