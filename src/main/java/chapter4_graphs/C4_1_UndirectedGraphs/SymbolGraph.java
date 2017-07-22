package chapter4_graphs.C4_1_UndirectedGraphs;

/******************************************************************************
 *  Compilation:  javac SymbolGraph.java
 *  Execution:    java SymbolGraph filename.txt delimiter
 *  Dependencies: ST.java Graph.java In.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/routes.txt
 *                http://algs4.cs.princeton.edu/41graph/movies.txt
 *                http://algs4.cs.princeton.edu/41graph/moviestiny.txt
 *                http://algs4.cs.princeton.edu/41graph/moviesG.txt
 *                http://algs4.cs.princeton.edu/41graph/moviestopGrossing.txt
 *
 *  %  java SymbolGraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  LAX
 *     PHX
 *     LAS
 *
 *  % java SymbolGraph movies.txt "/"
 *  Tin Men (1987)
 *     Hershey, Barbara
 *     Geppi, Cindy
 *     Jones, Kathy (II)
 *     Herr, Marcia
 *     ...
 *     Blumenfeld, Alan
 *     DeBoy, David
 *  Bacon, Kevin
 *     Woodsman, The (2004)
 *     Wild Things (1998)
 *     Where the Truth Lies (2005)
 *     Tremors (1990)
 *     ...
 *     Apollo 13 (1995)
 *     Animal House (1978)
 *
 *
 *  Assumes that input file is encoded using UTF-8.
 *  % iconv -f ISO-8859-1 -t UTF-8 movies-iso8859.txt > movies.txt
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.ST;

import java.io.InputStream;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The {@code SymbolGraph} class represents an undirected graph, where the
 * vertex names are arbitrary strings.
 * By providing mappings between string vertex names and integers,
 * it serves as a wrapper around the
 * {@link Graph} data type, which assumes the vertex names are integers
 * between 0 and <em>V</em> - 1.
 * It also supports initializing a symbol graph from a file.
 * <p>
 * This implementation uses an {@link ST} to map from strings to integers,
 * an array to map from integers to strings, and a {@link Graph} to store
 * the underlying graph.
 * The <em>indexOf</em> and <em>contains</em> operations take time
 * proportional to log <em>V</em>, where <em>V</em> is the number of vertices.
 * The <em>nameOf</em> operation takes constant time.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class SymbolGraph {

    private TreeMap<String, Integer> symbolTable; // string -> index
    private String[] keys; // index -> string
    private Graph graph;

    /**
     * Initializes a graph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     *
     * @param filename  the name of the file
     * @param delimiter the delimiter between fields
     */
    public SymbolGraph(String filename, String delimiter) {
        validateString(new String[]{filename, delimiter});

        symbolTable = new TreeMap<>();

        // read file
        InputStream inputStream
                = SymbolGraph.class.getResourceAsStream(filename);
        Scanner scanner = new Scanner(inputStream, "UTF-8");

        // builds the index by reading strings to associate
        while (scanner.hasNextLine()) {
            String[] s = scanner.nextLine().split(delimiter);
            for (String key : s) {
                if (!symbolTable.containsKey(key))
                    symbolTable.put(key, symbolTable.size());
            }
        }
        System.out.printf("Done reading %s!\n", filename);

        // inverted index to get string keys in an array
        keys = new String[symbolTable.size()];
        for (String name : symbolTable.keySet())
            keys[symbolTable.get(name)] = name;

        // builds the graph by connecting first vertex on each line to all others
        graph = new UndirectedGraph(symbolTable.size());
        Scanner create_graph_scanner = new Scanner(SymbolGraph.class.getResourceAsStream(filename));
        while (create_graph_scanner.hasNextLine()) {
            String[] s = create_graph_scanner.nextLine().split(delimiter);
            int v = symbolTable.get(s[0]);
            for (int i = 1; i < s.length; i++) {
                int w = symbolTable.get(s[i]);
                graph.addEdge(v, w);
            }
        }
    }

    /**
     * Does the graph contain the vertex named {@code s}?
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
     */
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    /**
     * Returns the graph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the graph.
     *
     * @return the graph associated with the symbol graph
     */
    public Graph graph() {
        return graph;
    }

    private void validateVertex(int v) {
        int V = graph.vertex();
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

    public static void main(String[] args) {
        String filename = "/graph_file/C4_1_UndirectedGraphs/" + args[0];
        String delimiter = args[1];
        SymbolGraph symbolGraph = new SymbolGraph(filename, delimiter);
        Graph graph = symbolGraph.graph();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (symbolGraph.contains(s)) {
                int v = symbolGraph.indexOf(s);
                for (int w : graph.adj(v))
                    System.out.printf(" %s\n", symbolGraph.nameOf(w));
            } else {
                System.out.printf("Input not contain '%s'\n", s);
            }
            if ("end".equalsIgnoreCase(s))
                break;
        }
    }

}
