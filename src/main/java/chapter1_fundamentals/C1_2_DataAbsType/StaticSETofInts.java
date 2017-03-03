package chapter1_fundamentals.C1_2_DataAbsType;

/******************************************************************************
 *  Compilation:  javac StaticSetOfInts.java
 *  Execution:    none
 *  Dependencies: StdOut.java
 *
 *  Data type to store a set of integers.
 *
 ******************************************************************************/

import java.util.Arrays;

/**
 * set of integers
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class StaticSETofInts {

    private int[] a;

    /**
     * Initializes a set of integers specified by the integer array.
     *
     * @param keys the array of integers
     * @throws IllegalArgumentException if the array contains duplicate integers
     */
    public StaticSETofInts(int[] keys) {

        // defensive copy
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i];

        // sort the integers
        Arrays.sort(a);

        // check for duplicates
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i - 1])
                throw new IllegalArgumentException("Argument arrays contains duplicate keys.");
    }

    /**
     * Is the key in this set of integers?
     *
     * @param key the search key
     * @return true if the set of integers contains the key; false otherwise
     */
    public boolean contains(int key) {
        return rank(key) != -1;
    }

    /**
     * Returns either the index of the search key in the sorted array
     * (if the key is in the set) or -1 (if the key is not in the set).
     *
     * @param key the search key
     * @return the number of keys in this set less than the key (if the key is in the set)
     * or -1 (if the key is not in the set).
     */
    public int rank(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

}
