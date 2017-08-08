package chapter5_strings.C5_5_DataCompression;

/******************************************************************************
 *  Compilation:  javac PictureDump.java
 *  Execution:    java PictureDump width height < file
 *  Dependencies: BinaryStdIn.java Picture.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *
 *  Reads in a binary file and writes out the bits as w-by-h picture,
 *  with the 1 bits in black and the 0 bits in white.
 *
 *  % more abra.txt
 *  ABRACADABRA!
 *
 *  % java PictureDump 16 6 < abra.txt
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;


/**
 * The {@code PictureDump} class provides a client for displaying the contents
 * of a binary file as a black-and-white picture.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * <p>
 * See also {@link BinaryDump} and {@link HexDump}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class PictureDump {

    // Do not instantiate.
    private PictureDump() {
    }

    /**
     * Reads in a sequence of bytes from standard input and draws
     * them to standard drawing output as a width-by-height picture,
     * using black for 1 and white for 0 (and red for any leftover
     * pixels).
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!BinaryStdIn.isEmpty()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) picture.set(col, row, Color.BLACK);
                    else picture.set(col, row, Color.WHITE);
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }
}
