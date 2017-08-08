package chapter5_strings.C5_5_DataCompression;

/******************************************************************************
 *  Compilation:  javac RunLength.java
 *  Execution:    java RunLength - < input.txt   (compress)
 *  Execution:    java RunLength + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   http://algs4.cs.princeton.edu/55compression/4runs.bin
 *                http://algs4.cs.princeton.edu/55compression/q32x48.bin
 *                http://algs4.cs.princeton.edu/55compression/q64x96.bin
 *
 *  Compress or expand binary input from standard input using
 *  run-length encoding.
 *
 *  % java BinaryDump 40 < 4runs.bin
 *  0000000000000001111111000000011111111111
 *  40 bits
 *
 *  This has runs of 15 0s, 7 1s, 7 0s, and 11 1s.
 *
 *  % java RunLength - < 4runs.bin | java HexDump
 *  0f 07 07 0b
 *  4 bytes
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * The {@code RunLength} class provides static methods for compressing
 * and expanding a binary input using run-length coding with 8-bit
 * run lengths.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class RunLength {
    private static final int R = 256;
    private static final int LG_R = 8;

    // Do not instantiate.
    private RunLength() {
    }

    /**
     * Reads a sequence of bits from standard input (that are encoded
     * using run-length encoding with 8-bit run lengths); decodes them;
     * and writes the results to standard output.
     */
    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(LG_R);
            for (int i = 0; i < run; i++)
                BinaryStdOut.write(b);
            b = !b;
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input; compresses
     * them using run-length coding with 8-bit run lengths; and writes the
     * results to standard output.
     */
    public static void compress() {
        char run = 0;
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(run, LG_R);
                run = 1;
                old = !old;
            } else {
                if (run == R - 1) {
                    BinaryStdOut.write(run, LG_R);
                    run = 0;
                    BinaryStdOut.write(run, LG_R);
                }
                run++;
            }
        }
        BinaryStdOut.write(run, LG_R);
        BinaryStdOut.close();
    }


    /**
     * Sample client that calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
