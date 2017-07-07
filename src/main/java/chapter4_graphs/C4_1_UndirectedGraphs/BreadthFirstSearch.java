package chapter4_graphs.C4_1_UndirectedGraphs;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * The {@code BreadthFirstSearch} class represents a data type for
 * determining the vertices connected to a given source vertex <em>s</em>
 * in an undirected graph.
 *
 * Created by SylvanasSun on 2017/7/7.
 */
public class BreadthFirstSearch {

    private final boolean[] marked;
    private final Graph graph;
    private int count;

    public BreadthFirstSearch(Graph graph, int originPoint) {
        this.graph = graph;
        this.count = 0;
        this.marked = new boolean[graph.vertex()];
        validateVertex(originPoint);
        breadthSearch(originPoint);
    }

    public boolean marked(int vertex) {
        validateVertex(vertex);
        return marked[vertex];
    }

    public int count() {
        return count;
    }

    private void breadthSearch(int vertex) {
        marked[vertex] = true;
        count++;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(vertex);

        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int adj : graph.adj(v)) {
                if (!marked[adj]) {
                    marked[adj] = true;
                    count++;
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

    public static void main(String[] args) {
        InputStream inputStream
                = BreadthFirstSearch.class.getResourceAsStream("/graph_file/C4_1_UndirectedGraphs/" + args[0]);
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Graph graph = new UndirectedGraph(scanner);
        Integer originPoint = Integer.valueOf(args[1]);
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(graph, originPoint);

        for (int v = 0; v < graph.vertex(); v++) {
            if (breadthFirstSearch.marked(v))
                System.out.printf("%d ", v);
        }
        System.out.printf("\n");
        if (breadthFirstSearch.count == graph.vertex())
            System.out.printf("Connected!\n");
        else
            System.out.printf("Not Connected!\n");
    }

}
