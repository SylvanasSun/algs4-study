package chapter4_graphs.C4_2_DirectedGraphs;

/******************************************************************************
 *  Compilation:  javac DirectedDFS.java
 *  Execution:    java DirectedDFS digraph.txt s
 *  Dependencies: Digraph.java Bag.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                http://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Determine single-source or multiple-source reachability in a digraph
 *  using depth first search.
 *  Runs in O(E + V) time.
 *
 *  % java DirectedDFS tinyDG.txt 1
 *  1
 *
 *  % java DirectedDFS tinyDG.txt 2
 *  0 1 2 3 4 5
 *
 *  % java DirectedDFS tinyDG.txt 1 2 6
 *  0 1 2 3 4 5 6 8 9 10 11 12
 *
 ******************************************************************************/

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * The {@code DirectedDFS} class represents a data type for
 * determining the vertices reachable from a given source vertex <em>s</em>
 * (or set of source vertices) in a digraph. For versions that find the paths,
 * see {@link DepthFirstDirectedPaths} and {@link BreadthFirstDirectedPaths}.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DirectedDFS {

    private final Digraph digraph;

    // marked[v] = true if v is reachable from source (or sources)
    private final boolean[] marked;

    // number of vertices reachable from s
    private int count;


    /**
     * Computes the vertices in digraph {@code digraph} that are
     * reachable from the source vertex {@code originPoint}.
     *
     * @param digraph     the digraph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DirectedDFS(Digraph digraph, int originPoint) {
        this.marked = new boolean[digraph.vertex()];
        this.digraph = digraph;
        validateVertex(originPoint);
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
    public DirectedDFS(Digraph digraph, Iterable<Integer> sources) {
        this.marked = new boolean[digraph.vertex()];
        this.digraph = digraph;
        validateVertices(sources);
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
     * Is there a directed path from the source vertex (or any
     * of the source vertices) and vertex {@code vertex}?
     *
     * @param vertex the vertex
     * @return {@code true} if there is a directed path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        count++;

        for (int w : digraph.adj(vertex)) {
            if (!marked[w])
                dfs(w);
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
                = DirectedDFS.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Digraph digraph = new Digraph(scanner);

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i < args.length; i++)
            queue.add(Integer.parseInt(args[i]));

        DirectedDFS directedDFS = new DirectedDFS(digraph, queue);
        for (int v = 0; v < digraph.vertex(); v++) {
            if (directedDFS.marked(v))
                System.out.print(v + " ");
        }
        System.out.println();
    }

}
