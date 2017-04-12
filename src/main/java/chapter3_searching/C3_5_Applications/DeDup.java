package chapter3_searching.C3_5_Applications;

/******************************************************************************
 *  Compilation:  javac DeDup.java
 *  Execution:    java DeDup < input.txt
 *  Dependencies: SET StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/tinyTale.txt
 *
 *  Read in a list of words from standard input and print out
 *  each word, removing any duplicates.
 *
 *  % more tinyTale.txt
 *  it was the best of times it was the worst of times
 *  it was the age of wisdom it was the age of foolishness
 *  it was the epoch of belief it was the epoch of incredulity
 *  it was the season of light it was the season of darkness
 *  it was the spring of hope it was the winter of despair
 *
 *  % java DeDup < tinyTale.txt
 *  it
 *  was
 *  the
 *  best
 *  of
 *  times
 *  worst
 *  age
 *  wisdom
 *  ...
 *  winter
 *  despair
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code DeDup} class provides a client for reading in a sequence of
 * words from standard input and printing each word, removing any duplicates.
 * It is useful as a test client for various symbol table implementations.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DeDup {

    // Do not instantiate.
    private DeDup() {
    }

    public static void main(String[] args) {
        SET<String> set = new SET<String>();

        // read in strings and add to set
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (!set.contains(key)) {
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}
