package chapter4_graphs.C4_2_DirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac NonrecursiveDirectedDFS.java
 *  Execution:    java NonrecursiveDirectedDFS digraph.txt s
 *  Dependencies: Digraph.java Queue.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Run nonrecurisve depth-first search on an directed graph.
 *  Runs in O(E + V) time.
 *
 *  Explores the vertices in exactly the same order as DirectedDFS.java.
 *
 *
 *  % java NonrecursiveDirectedDFS tinyDG.txt 1
 *  1
 *
 *  % java NonrecursiveDirectedDFS tinyDG.txt 2
 *  0 1 2 3 4 5
 *
 ******************************************************************************/


/**
 * The {@code NonrecursiveDirectedDFS} class represents a data type for finding
 * the vertices reachable from a source vertex <em>s</em> in the digraph.
 * <p>
 * This implementation uses a nonrecursive version of depth-first search
 * with an explicit stack.
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
public class NonrecursiveDirectedDFS {

    private final Digraph digraph;

    // marked[v] = true if v is reachable from source (or sources)
    private final boolean[] marked;

    private final Iterator<Integer>[] adj;

    // number of vertices reachable from s
    private int count;

    /**
     * Computes the vertices reachable from the source vertex {@code originPoint} in the digraph {@code digraph}.
     *
     * @param digraph     the digraph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public NonrecursiveDirectedDFS(Digraph digraph, int originPoint) {
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        validateVertex(originPoint);
        this.digraph = digraph;
        this.adj = new Iterator[vertex];
        for (int v = 0; v < vertex; v++)
            adj[v] = digraph.adj(v).iterator();
        dfs(originPoint);
    }

    /**
     * Computes the vertices in digraph {@code digraph} that are
     * connected to any of the source vertices {@code sources}.
     *
     * @param digraph the graph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     *                                  for each vertex {@code s} in {@code sources}
     */
    public NonrecursiveDirectedDFS(Digraph digraph, Iterable<Integer> sources) {
        int vertex = digraph.vertex();
        this.marked = new boolean[vertex];
        validateVertices(sources);
        this.digraph = digraph;
        this.adj = new Iterator[vertex];
        for (int v = 0; v < vertex; v++)
            adj[v] = digraph.adj(v).iterator();

        for (int s : sources) {
            if (!marked[s])
                dfs(s);
        }
    }

    /**
     * Returns the number of vertices reachable from the source vertex
     * (or source vertices).
     *
     * @return the number of vertices reachable from the source vertex
     * (or source vertices)
     */
    public int count() {
        return count;
    }


    /**
     * Is vertex {@code vertex} reachable from the source vertex {@code s}?
     *
     * @param vertex the vertex
     * @return {@code true} if vertex {@code vertex} is reachable from the source vertex {@code s},
     * and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        count++;
        Stack<Integer> stack = new Stack<>();
        stack.push(vertex);

        while (!stack.isEmpty()) {
            Integer v = stack.peek();
            Iterator<Integer> iterator = adj[v];
            if (iterator.hasNext()) {
                Integer w = iterator.next();
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    stack.push(w);
                }
            } else {
                stack.pop();
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
                = NonrecursiveDirectedDFS.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < args.length; i++)
            queue.add(Integer.valueOf(args[i]));

        NonrecursiveDirectedDFS dfs = new NonrecursiveDirectedDFS(digraph, queue);
        for (int v = 0; v < digraph.vertex(); v++) {
            if (dfs.marked(v))
                System.out.printf("%d ", v);
        }
        System.out.println();
    }

}
