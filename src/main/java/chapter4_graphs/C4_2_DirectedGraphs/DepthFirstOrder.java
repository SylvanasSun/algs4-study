package chapter4_graphs.C4_2_DirectedGraphs;

import chapter4_graphs.C4_1_UndirectedGraphs.Graph;
import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac DepthFirstOrder.java
 *  Execution:    java DepthFirstOrder digraph.txt
 *  Dependencies: Digraph.java Queue.java Stack.java StdOut.java
 *                EdgeWeightedDigraph.java DirectedEdge.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *                http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *
 *  Compute preorder and postorder for a digraph or edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstOrder tinyDAG.txt
 *     v  pre post
 *  --------------
 *     0    0    8
 *     1    3    2
 *     2    9   10
 *     3   10    9
 *     4    2    0
 *     5    1    1
 *     6    4    7
 *     7   11   11
 *     8   12   12
 *     9    5    6
 *    10    8    5
 *    11    6    4
 *    12    7    3
 *  Preorder:  0 5 4 1 6 9 11 12 10 2 3 7 8
 *  Postorder: 4 5 1 12 11 10 9 6 0 3 2 7 8
 *  Reverse postorder: 8 7 2 3 0 6 9 10 11 12 1 5 4
 *
 ******************************************************************************/

/**
 * The {@code DepthFirstOrder} class represents a data type for
 * determining depth-first search ordering of the vertices in a digraph
 * or edge-weighted digraph, including preorder, postorder, and reverse postorder.
 * <p>
 * This implementation uses depth-first search.
 * The constructor takes time proportional to <em>V</em> + <em>E</em>
 * (in the worst case),
 * where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>preorder</em>, <em>postorder</em>, and <em>reverse postorder</em>
 * operation takes take time proportional to <em>V</em>.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstOrder {

    private final Graph graph;

    // marked[v] = has v been marked in dfs?
    private final boolean[] marked;

    // pre[v]    = preorder  number of v
    private final int[] pre;

    // post[v]   = postorder number of v
    private final int[] post;

    // vertices in preorder
    private final Queue<Integer> preorder;

    // vertices in postorder
    private final Queue<Integer> postorder;

    // counter or preorder numbering
    private int preCounter;

    // counter for postorder numbering
    private int postCounter;

    /**
     * Determines a depth-first order for the digraph {@code graph}.
     *
     * @param graph the digraph
     */
    public DepthFirstOrder(Graph graph) {
        this.graph = graph;
        int vertex = graph.vertex();
        this.pre = new int[vertex];
        this.post = new int[vertex];
        this.preorder = new ArrayDeque<>();
        this.postorder = new ArrayDeque<>();
        this.marked = new boolean[vertex];

        for (int v = 0; v < vertex; v++)
            if (!marked[v]) dfs(v);

        assert check();
    }

    /**
     * Returns the preorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the preorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    /**
     * Returns the postorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the postorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    /**
     * Returns the vertices in postorder.
     *
     * @return the vertices in postorder, as an iterable of vertices
     */
    public Iterable<Integer> post() {
        return postorder;
    }

    /**
     * Returns the vertices in preorder.
     *
     * @return the vertices in preorder, as an iterable of vertices
     */
    public Iterable<Integer> pre() {
        return preorder;
    }


    /**
     * Returns the vertices in reverse postorder.
     *
     * @return the vertices in reverse postorder, as an iterable of vertices
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }

    private void dfs(int vertex) {
        marked[vertex] = true;
        pre[vertex] = preCounter++;
        preorder.add(vertex);
        for (int w : graph.adj(vertex)) {
            if (!marked[w])
                dfs(w);
        }
        post[vertex] = postCounter++;
        postorder.add(vertex);
    }

    // check that pre() and post() are consistent with pre(v) and post(v)
    private boolean check() {

        // check that post(v) is consistent with post()
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                System.out.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }

        // check that pre(v) is consistent with pre()
        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                System.out.println("pre(v) and pre() inconsistent");
                return false;
            }
            r++;
        }

        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        InputStream inputStream
                = DepthFirstOrder.class.getResourceAsStream("/graph_file/C4_2_DirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream);
        Graph graph = new Digraph(scanner);
        DepthFirstOrder order = new DepthFirstOrder(graph);

        System.out.println("   v pre post");
        System.out.println("-------------");
        for (int v = 0; v < graph.vertex(); v++)
            System.out.printf("%4d %4d %4d\n", v, order.pre(v), order.post(v));

        System.out.print("Preorder: ");
        for (int v : order.pre())
            System.out.print(v + " ");
        System.out.println();

        System.out.print("Postorder: ");
        for (int v : order.post())
            System.out.print(v + " ");
        System.out.println();

        System.out.print("Reverse postorder: ");
        for (int v : order.reversePost())
            System.out.print(v + " ");
        System.out.println();
    }

}
