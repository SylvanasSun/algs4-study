package chapter4_graphs.C4_1_UndirectedGraphs;


public interface Graph {

    int vertex();

    int edge();

    void addEdge(int v, int w);

    Iterable<Integer> adj(int v);

    int degree(int v);

    String toString();

}
