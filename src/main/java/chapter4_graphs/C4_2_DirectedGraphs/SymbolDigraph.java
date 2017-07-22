package chapter4_graphs.C4_2_DirectedGraphs;

/******************************************************************************
 *  Compilation:  javac SymbolDigraph.java
 *  Execution:    java SymbolDigraph
 *  Dependencies: ST.java Digraph.java In.java
 *  Data files:   http://algs4.cs.princeton.edu/42digraph/routes.txt
 *
 *  %  java SymbolDigraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  ATL
 *     HOU
 *     MCO
 *  LAX
 *
 ******************************************************************************/

import java.io.InputStream;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The {@code SymbolDigraph} class represents a digraph, where the
 * vertex names are arbitrary strings.
 * By providing mappings between string vertex names and integers,
 * it serves as a wrapper around the
 * {@link Digraph} data type, which assumes the vertex names are integers
 * between 0 and <em>V</em> - 1.
 * It also supports initializing a symbol digraph from a file.
 * <p>
 * This implementation uses an {@link java.util.TreeMap} to map from strings to integers,
 * an array to map from integers to strings, and a {@link Digraph} to store
 * the underlying graph.
 * The <em>indexOf</em> and <em>contains</em> operations take time
 * proportional to log <em>V</em>, where <em>V</em> is the number of vertices.
 * The <em>nameOf</em> operation takes constant time.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class SymbolDigraph {

    // string -> index
    private TreeMap<String, Integer> symbolTable;

    // index  -> string
    private String[] keys;

    // the underlying digraph
    private Digraph digraph;

    /**
     * Initializes a digraph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     *
     * @param filename  the name of the file
     * @param delimiter the delimiter between fields
     */
    public SymbolDigraph(String filename, String delimiter) {
        validateString(new String[]{filename, delimiter});

        InputStream inputStream;
        inputStream = SymbolDigraph.class.getResourceAsStream(filename);
        Scanner scanner = new Scanner(inputStream);

        symbolTable = new TreeMap<>();
        while (scanner.hasNextLine()) {
            String[] s = scanner.nextLine().split(delimiter);
            for (int i = 0; i < s.length; i++) {
                String key = s[i];
                if (!symbolTable.containsKey(key))
                    symbolTable.put(key, symbolTable.size());
            }
        }

        keys = new String[symbolTable.size()];
        for (String name : symbolTable.keySet())
            keys[symbolTable.get(name)] = name;

        digraph = new Digraph(symbolTable.size());
        inputStream = SymbolDigraph.class.getResourceAsStream(filename);
        Scanner scanner2 = new Scanner(inputStream);
        while (scanner2.hasNextLine()) {
            String[] s = scanner2.nextLine().split(delimiter);
            int v = symbolTable.get(s[0]);
            for (int i = 1; i < s.length; i++) {
                Integer w = symbolTable.get(s[i]);
                digraph.addEdge(v, w);
            }
        }
    }

    /**
     * Does the digraph contain the vertex named {@code s}?
     *
     * @param s the name of a vertex
     * @return {@code true} if {@code s} is the name of a vertex, and {@code false} otherwise
     */
    public boolean contains(String s) {
        return symbolTable.containsKey(s);
    }

    /**
     * Returns the integer associated with the vertex named {@code s}.
     *
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
     * @deprecated Replaced by {@link #indexOf(String)}.
     */
    @Deprecated
    public int index(String s) {
        return symbolTable.get(s);
    }

    /**
     * Returns the integer associated with the vertex named {@code s}.
     *
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
     */
    public int indexOf(String s) {
        return symbolTable.get(s);
    }

    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     *
     * @param v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @return the name of the vertex associated with the integer {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @deprecated Replaced by {@link #nameOf(int)}.
     */
    @Deprecated
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     *
     * @param v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @return the name of the vertex associated with the integer {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    /**
     * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the digraph.
     *
     * @return the digraph associated with the symbol digraph
     * @deprecated Replaced by {@link #digraph()}.
     */
    @Deprecated
    public Digraph G() {
        return digraph;
    }

    /**
     * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the digraph.
     *
     * @return the digraph associated with the symbol digraph
     */
    public Digraph digraph() {
        return digraph;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = digraph.vertex();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    private void validateString(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ("".equals(arg) || arg == null)
                throw new IllegalArgumentException(String.format("String arg %s is not empty or null!", arg));
        }
    }

    /**
     * Unit tests the {@code SymbolDigraph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String filename = "/graph_file/C4_1_UndirectedGraphs/" + args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Digraph graph = sg.digraph();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String t = scanner.nextLine();
            for (int v : graph.adj(sg.indexOf(t))) {
                System.out.println("   " + sg.nameOf(v));
            }
        }
    }

}
