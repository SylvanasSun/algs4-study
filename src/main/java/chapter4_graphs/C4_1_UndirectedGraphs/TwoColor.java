package chapter4_graphs.C4_1_UndirectedGraphs;

/**
 * Created by SylvanasSun on 2017/7/18.
 */
public class TwoColor {

    private final Graph graph;

    private final boolean[] marked;

    private final boolean[] color;

    private boolean isTwoColorable = true;

    public TwoColor(Graph graph) {
        this.graph = graph;
        int vertex = graph.vertex();

        this.marked = new boolean[vertex];
        this.color = new boolean[vertex];

        for (int v = 0; v < vertex; v++) {
            if (!marked[v])
                dfs(v);
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }

    private void dfs(int v) {
        marked[v] = true;

        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(w);
            } else if (color[w] == color[v]) {
                isTwoColorable = false;
            }
        }
    }

}
