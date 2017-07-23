package chapter4_graphs.C4_3_MinimumSpanningTrees;

/******************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted edge.
 *
 ******************************************************************************/

import chapter1_fundamentals.C1_4_AnalysisOfAlgorithms.DoublingRatio;

/**
 * The {@code Edge} class represents a weighted edge in an
 * {@link EdgeWeightedGraph}. Each edge consists of two integers
 * (naming the two vertices) and a real-value weight. The data type
 * provides methods for accessing the two endpoints of the edge and
 * the weight. The natural order for this data type is by
 * ascending order of weight.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Edge implements Comparable<Edge> {

    private final int v;

    private final int w;

    private final double weight;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param v      one vertex
     * @param w      the other vertex
     * @param weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *                                  is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weight) {
        validateVertexes(v, w);
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN.");

        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    private void validateVertexes(int... vertexes) {
        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] < 0) {
                throw new IllegalArgumentException(
                        String.format("Vertex %d must be a nonnegative integer.", vertexes[i]));
            }
        }
    }


    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *                                  endpoints of this edge
     */
    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Illegal endpoint.");
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     * the weight of this is less than, equal to, or greater than the
     * argument edge
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    @Override
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        int edge01_v = 0;
        int edge01_w = 1;
        int edge02_v = 0;
        int edge02_w = 2;
        double edge01_weight = 1.2;
        double edge02_weight = 2.2;
        Edge edge01 = new Edge(edge01_v, edge01_w, edge01_weight);
        Edge edge02 = new Edge(edge02_v, edge02_w, edge02_weight);
        System.out.printf("Edge 01 : %s\n", edge01);
        System.out.printf("Edge 02 : %s\n", edge02);
        assert edge01.either() == edge01_v;
        assert edge01.other(edge01_v) == edge01_w;
        assert edge02.either() == edge02_v;
        assert edge02.other(edge02_v) == edge02_w;
        assert edge01.compareTo(edge02) < 0;
    }

}
