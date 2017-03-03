package chapter1_fundamentals.C1_1_BasicProgModel;

/******************************************************************************
 *  Compilation:  javac Knuth.java
 *  Execution:    java Knuth < list.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/11model/cards.txt
 *                http://algs4.cs.princeton.edu/11model/cardsUnicode.txt
 *
 *  Reads in a list of strings and prints them in random order.
 *  The Knuth (or Fisher-Yates) shuffling algorithm guarantees
 *  to rearrange the elements in uniformly random order, under
 *  the assumption that Math.random() generates independent and
 *  uniformly distributed numbers between 0 and 1.
 *
 *  % more cards.txt
 *  2C 3C 4C 5C 6C 7C 8C 9C 10C JC QC KC AC
 *  2D 3D 4D 5D 6D 7D 8D 9D 10D JD QD KD AD
 *  2H 3H 4H 5H 6H 7H 8H 9H 10H JH QH KH AH
 *  2S 3S 4S 5S 6S 7S 8S 9S 10S JS QS KS AS
 *
 *  % java Knuth < cards.txt
 *  6H
 *  9C
 *  8H
 *  7C
 *  JS
 *  ...
 *  KH
 *
 *  % more cardsUnicode.txt
 *  2♣ 3♣ 4♣ 5♣ 6♣ 7♣ 8♣ 9♣ 10♣ J♣ Q♣ K♣ A♣
 *  2♦ 3♦ 4♦ 5♦ 6♦ 7♦ 8♦ 9♦ 10♦ J♦ Q♦ K♦ A♦
 *  2♥ 3♥ 4♥ 5♥ 6♥ 7♥ 8♥ 9♥ 10♥ J♥ Q♥ K♥ A♥
 *  2♠ 3♠ 4♠ 5♠ 6♠ 7♠ 8♠ 9♠ 10♠ J♠ Q♠ K♠ A♠
 *
 *  % java Knuth < cardsUnicode.txt
 *  2♠
 *  K♥
 *  6♥
 *  5♣
 *  J♣
 *  ...
 *  A♦
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Knuth shuffle
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Knuth {

    public static void main(String[] args) {
        //read data,according space split string.
        String[] a = StdIn.readAll().split("\\s+");
        int N = a.length;

        //shuffle
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - 1));
            String swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }

        for (String anA : a) {
            StdOut.println(anA);
        }
    }

}
