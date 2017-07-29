package chapter5_strings.C5_1_StringSorts;

/******************************************************************************
 *  Compilation:  javac Count.java
 *  Execution:    java Count alpha < input.txt
 *  Dependencies: Alphabet.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/50strings/abra.txt
 *                http://algs4.cs.princeton.edu/50strings/pi.txt
 *
 *  Create an alphabet specified on the command line, read in a
 *  sequence of characters over that alphabet (ignoring characters
 *  not in the alphabet), computes the frequency of occurrence of
 *  each character, and print out the results.
 *
 *  %  java Count ABCDR < abra.txt
 *  A 5
 *  B 2
 *  C 1
 *  D 1
 *  R 2
 *
 *  % java Count 0123456789 < pi.txt
 *  0 99959
 *  1 99757
 *  2 100026
 *  3 100230
 *  4 100230
 *  5 100359
 *  6 99548
 *  7 99800
 *  8 99985
 *  9 100106
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.Alphabet;

import java.io.InputStream;
import java.util.Scanner;

/**
 * The {@code Count} class provides an {@link Alphabet} client for reading
 * in a piece of text and computing the frequency of occurrence of each
 * character over a given alphabet.
 * <p>
 * For additional documentation,
 * see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class Count {

    private Count() {
    }

    /**
     * Reads in text from standard input; calculates the frequency of
     * occurrence of each character over the alphabet specified as a
     * commmand-line argument; and prints the frequencies to standard
     * output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String arg = args[0];
        Alphabet alphabet = new Alphabet(arg);
        final int R = alphabet.radix();
        int[] count = new int[R];
        System.out.printf("String %s char size = %d", arg, R);

        InputStream inputStream = Count.class.getResourceAsStream("/string/" + args[1]);
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (alphabet.contains(c))
                    count[alphabet.toIndex(c)]++;
            }
        }
        for (int c = 0; c < R; c++)
            System.out.println(alphabet.toChar(c) + " " + count[c]);
    }

}
