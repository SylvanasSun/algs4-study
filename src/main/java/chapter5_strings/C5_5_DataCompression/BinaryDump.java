package chapter5_strings.C5_5_DataCompression;

/******************************************************************************
 *  Compilation:  javac BinaryDump.java
 *  Execution:    java BinaryDump N < file
 *  Dependencies: BinaryStdIn.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *
 *  Reads in a binary file and writes out the bits, N per line.
 *
 *  % more abra.txt
 *  ABRACADABRA!
 *
 *  % java BinaryDump 16 < abra.txt
 *  0100000101000010
 *  0101001001000001
 *  0100001101000001
 *  0100010001000001
 *  0100001001010010
 *  0100000100100001
 *  96 bits
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code BinaryDump} class provides a client for displaying the contents
 * of a binary file in binary.
 * <p>
 * For more full-featured versions, see the Unix utilities
 * {@code od} (octal dump) and {@code hexdump} (hexadecimal dump).
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * <p>
 * See also {@link HexDump} and {@link PictureDump}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BinaryDump {

    // Do not instantiate.
    private BinaryDump() {
    }

    /**
     * Reads in a sequence of bytes from standard input and writes
     * them to standard output in binary, k bits per line,
     * where k is given as a command-line integer (defaults
     * to 16 if no integer is specified); also writes the number
     * of bits.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int bitsPerLine = 16;
        if (args.length == 1) {
            bitsPerLine = Integer.parseInt(args[0]);
        }

        int count;
        for (count = 0; !BinaryStdIn.isEmpty(); count++) {
            if (bitsPerLine == 0) {
                BinaryStdIn.readBoolean();
                continue;
            } else if (count != 0 && count % bitsPerLine == 0) StdOut.println();
            if (BinaryStdIn.readBoolean()) StdOut.print(1);
            else StdOut.print(0);
        }
        if (bitsPerLine != 0) StdOut.println();
        StdOut.println(count + " bits");
    }
}