package chapter4_graphs.C4_2_DirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac DepthFirstDirectedPaths.java
 *  Execution:    java DepthFirstDirectedPaths digraph.txt s
 *  Dependencies: Digraph.java Stack.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Determine reachability in a digraph from a given vertex using
 *  depth-first search.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0:  3-5-4-2-0
 *  3 to 1:  3-5-4-2-0-1
 *  3 to 2:  3-5-4-2
 *  3 to 3:  3
 *  3 to 4:  3-5-4
 *  3 to 5:  3-5
 *  3 to 6:  not connected
 *  3 to 7:  not connected
 *  3 to 8:  not connected
 *  3 to 9:  not connected
 *  3 to 10:  not connected
 *  3 to 11:  not connected
 *  3 to 12:  not connected
 *
 ******************************************************************************/

/**
 * The {@code DepthFirstDirectedPaths} class represents a data type for finding
 * directed paths from a source vertex <em>s</em> to every
 * other vertex in the digraph.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>,
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * It uses extra space (not including the graph) proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstDirectedPaths {

    private final Digraph digraph;

    // marked[v] = true if v is reachable from s
    private final boolean[] marked;

    // edgeTo[v] = last edge on path from s to v
    private final int[] edgeTo;

    // source vertex
    private final int originPoint;

    /**
     * Computes a directed path from {@code originPoint} to every other vertex in digraph {@code digraph}.
     *
     * @param digraph     the digraph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstDirectedPaths(Digraph digraph, int originPoint) {
        this.digraph = digraph;
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];
        this.originPoint = originPoint;
        validateVertex(originPoint);

        dfs(originPoint);
    }

    /**
     * Is there a directed path from the source vertex {@code originPoint} to vertex {@code v}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a directed path from the source
     * vertex {@code originPoint} to vertex {@code v}, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    /**
     * Returns a directed path from the source vertex {@code originPoint} to vertex {@code vertex}, or
     * {@code null} if no such path.
     *
     * @param vertex the vertex
     * @return the sequence of vertices on a directed path from the source vertex
     * {@code s} to vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int vertex) {
        validateVertex(vertex);
        if (!hasPathTo(vertex))
            return null;

        Stack<Integer> stack = new Stack<>();
        for (int x = vertex; x != originPoint; x = edgeTo[x])
            stack.push(x);
        stack.push(originPoint);
        return stack;
    }

    private void dfs(int vertex) {
        marked[vertex] = true;

        for (int w : digraph.adj(vertex)) {
            if (!marked[w]) {
                edgeTo[w] = vertex;
                dfs(w);
            }
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = DepthFirstDirectedPaths.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);
        Integer originPoint = Integer.valueOf(args[1]);
        DepthFirstDirectedPaths directedPaths = new DepthFirstDirectedPaths(digraph, originPoint);

        for (int v = 0; v < digraph.vertex(); v++) {
            System.out.printf("%d to %d: ", originPoint, v);
            if (directedPaths.hasPathTo(v)) {
                Iterable<Integer> pathTo = directedPaths.pathTo(v);
                for (int w : pathTo) {
                    if (w == originPoint)
                        System.out.printf("%d", w);
                    else
                        System.out.printf("-%d", w);
                }
            } else {
                System.out.printf("not connected.");
            }
            System.out.printf("\n");
        }
    }

}
