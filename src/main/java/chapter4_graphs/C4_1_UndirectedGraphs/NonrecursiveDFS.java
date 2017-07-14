package chapter4_graphs.C4_1_UndirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac NonrecursiveDFS.java
 *  Execution:    java NonrecursiveDFS graph.txt s
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run nonrecurisve depth-first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  Explores the vertices in exactly the same order as DepthFirstSearch.java.
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
 *  %  java NonrecursiveDFS tinyCG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (1):  0-1
 *  0 to 2 (1):  0-2
 *  0 to 3 (2):  0-2-3
 *  0 to 4 (2):  0-2-4
 *  0 to 5 (1):  0-5
 *
 ******************************************************************************/

/**
 * The {@code NonrecursiveDFS} class represents a data type for finding
 * the vertices connected to a source vertex <em>s</em> in the undirected
 * graph.
 * <p>
 * This implementation uses a nonrecursive version of depth-first search
 * with an explicit stack.
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
public class NonrecursiveDFS {

    private final boolean[] marked; // marked[v] = is there an s-v path?
    private final Iterator<Integer>[] adj;

    /**
     * Computes the vertices connected to the source vertex {@code originPoint} in the graph {@code graph}.
     *
     * @param graph       the graph
     * @param originPoint the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public NonrecursiveDFS(Graph graph, int originPoint) {
        int vertex = graph.vertex();
        this.marked = new boolean[vertex];

        validateVertex(originPoint);

        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        adj = (Iterator<Integer>[]) new Iterator[vertex];
        for (int v = 0; v < vertex; v++)
            adj[v] = graph.adj(v).iterator();

        dfs(originPoint);
    }

    // depth-first search using an explicit stack
    private void dfs(int originPoint) {
        Stack<Integer> stack = new Stack<>();
        marked[originPoint] = true;
        stack.push(originPoint);
        while (!stack.isEmpty()) {
            Integer v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            } else {
                stack.pop();
            }
        }
    }

    /**
     * Is vertex {@code v} connected to the source vertex {@code s}?
     *
     * @param v the vertex
     * @return {@code true} if vertex {@code v} is connected to the source vertex {@code s},
     * and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = NonrecursiveDFS.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Graph graph = new UndirectedGraph(scanner);
        Integer originPoint = Integer.valueOf(args[1]);
        NonrecursiveDFS dfs = new NonrecursiveDFS(graph, originPoint);

        System.out.printf("%d: ", originPoint);
        for (int v = 0; v < graph.vertex(); v++) {
            if (dfs.marked(v) && v != originPoint) {
                System.out.printf("%d ", v);
            }
        }
        System.out.printf("\n");
    }

}
