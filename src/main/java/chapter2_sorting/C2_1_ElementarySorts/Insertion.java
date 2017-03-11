package chapter2_sorting.C2_1_ElementarySorts;

/******************************************************************************
 *  Compilation:  javac Insertion.java
 *  Execution:    java Insertion < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/21sort/tiny.txt
 *                http://algs4.cs.princeton.edu/21sort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using insertion sort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Insertion < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Insertion < words3.txt
 *  all bad bed bug dad ... yes yet zoo   [ one string per line ]
 *
 ******************************************************************************/

import java.util.Comparator;
import java.util.Scanner;

/**
 * 插入排序
 * 插入排序与选择排序一样,当前索引左边的所有元素都是有序的,但它们的最终位置是不确定的,可能会被移动.
 * 插入排序所需的时间取决于输入中元素的初始顺序,如果输入是一个有序的数组,插入排序能够立即发现每个元素都已在合适位置上.
 * <p>
 * Created by SylvanasSun on 2017/3/11.
 */
public class Insertion {

    private Insertion() {
    }

    /**
     * 按升序进行重排序,使用自然顺序(需要目标数组实现Comparable接口)
     *
     * @param a 要排序的数组
     */
    public static void sort(Comparable[] a) {
        int length = a.length;
        for (int i = 0; i < length; i++) {
            //将a[i]插入到a[i-1] a[i-2] a[i-3]...之中
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    /**
     * 按升序进行重排序a[lo..hi],使用自然顺序(需要目标数组实现Comparable接口)
     *
     * @param a  要排序的数组
     * @param lo 索引下界
     * @param hi 索引上界
     */
    public static void sort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi);
    }

    /**
     * 按升序进行重排序,使用比较器
     *
     * @param a          要排序的数组
     * @param comparator 比较器
     */
    public static void sort(Object[] a, Comparator comparator) {
        int length = a.length;
        for (int i = 0; i < length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i, comparator);
        }
        assert isSorted(a, comparator);
    }

    /**
     * 按升序进行重排序a[lo..hi],使用比较器
     *
     * @param a          要排序的数组
     * @param lo         索引下界
     * @param hi         索引上界
     * @param comparator 比较器
     */
    public static void sort(Object[] a, int lo, int hi, Comparator comparator) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi, comparator);
    }

    // return a permutation that gives the elements in a[] in ascending order
    // do not change the original array a[]
    /**
     * Returns a permutation that gives the elements in the array in ascending order.
     *
     * @param a the array
     * @return a permutation {@code p[]} such that {@code a[p[0]]}, {@code a[p[1]]},
     * ..., {@code a[p[n-1]]} are in ascending order
     */
    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        for (int i = 0; i < n; i++)
            for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--)
                exch(index, j, j - 1);

        return index;
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w ?
    private static boolean less(Object v, Object w, Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // exchange a[i] and a[j]  (for indirect sort)
    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, 0, a.length - 1, comparator);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, int lo, int hi, Comparator comparator) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1], comparator)) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    /**
     * Reads in a sequence of strings from standard input; insertion sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = new Scanner(System.in).nextLine().split("\\s+");
        Insertion.sort(a);
        show(a);
    }
}
