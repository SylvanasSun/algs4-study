package chapter4_graphs.C4_1_UndirectedGraphs;

import edu.princeton.cs.algs4.Stack;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by SylvanasSun on 2017/7/8.
 */
public class Cyclic {

    private final Graph graph;
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cyclic;

    public Cyclic(Graph graph) {
        this.graph = graph;
        if (hasSelfLoop()) return;
        if (hasParallelEdges()) return;

        int vertex = graph.vertex();
        this.marked = new boolean[vertex];
        this.edgeTo = new int[vertex];

        for (int v = 0; v < vertex; v++) {
            if (!marked[v])
                dfs(v, -1);
        }
    }

    /**
     * Returns true if the graph {@code graph} has a cycle.
     *
     * @return {@code true} if the graph has a cycle; {@code false} otherwise
     */
    public boolean hasCyclic() {
        return cyclic != null;
    }

    /**
     * Returns a cycle in the graph {@code graph}.
     *
     * @return a cycle if the graph {@code graph} has a cycle,
     * and {@code null} otherwise
     */
    public Iterable<Integer> cyclic() {
        return cyclic;
    }

    private boolean hasSelfLoop() {
        for (int v = 0; v < graph.vertex(); v++) {
            for (int w : graph.adj(v)) {
                if (v == w) {
                    cyclic = new Stack<>();
                    cyclic.push(v);
                    cyclic.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasParallelEdges() {
        int vertex = graph.vertex();
        boolean[] marked = new boolean[vertex];
        for (int v = 0; v < vertex; v++) {
            // check for parallel edges incident to v
            for (int w : graph.adj(v)) {
                if (marked[w]) {
                    cyclic = new Stack<>();
                    cyclic.push(v);
                    cyclic.push(w);
                    cyclic.push(v);
                    return true;
                }
                marked[w] = true;
            }

            // reset so marked[v] = false for all v
            for (int w : graph.adj(v))
                marked[w] = false;
        }
        return false;
    }

    private void dfs(int v, int u) {
        marked[v] = true;

        for (int w : graph.adj(v)) {
            if (cyclic != null) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w, v);
            } else if (w != u) {
                // check for cycle (but disregard reverse of edge leading to v)
                cyclic = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cyclic.push(x);
                cyclic.push(w);
                cyclic.push(v);
            }
        }
    }

    public static void main(String[] args) {
        InputStream inputStream
                = Cyclic.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Graph graph = new UndirectedGraph(scanner);
        Cyclic cyclic = new Cyclic(graph);

        if (cyclic.hasCyclic()) {
            System.out.printf("Have cyclic,path: ");
            for (int v : cyclic.cyclic()) {
                System.out.printf("%d ", v);
            }
            System.out.printf("\n");
        } else {
            System.out.printf("Not have cyclic!\n");
        }
    }

}
