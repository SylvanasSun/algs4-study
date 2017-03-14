package chapter2_sorting.C2_3_Quicksort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 *  Compilation:  javac Quick.java
 *  Execution:    java Quick < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                http://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using quicksort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Quick < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Quick < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 *  Remark: For a type-safe version that uses static generics, see
 *
 *    http://algs4.cs.princeton.edu/23quicksort/QuickPedantic.java
 *
 ******************************************************************************/


/**
 * 快速排序
 * 它将一个数组分成两个子数组,并将两部分独立地排序.
 * 当两个子数组有序时整个数组也就自然有序了,快速排序中的递归调用发生在处理整个数组之后.
 * <p>
 * Created by SylvanasSun on 2017/3/14.
 */
public class Quick {

    private Quick() {
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
        assert isSorted(a, lo, hi);
    }

    /**
     * 切分元素
     * 一般策略是先随意地取a[lo]作为切分元素,即那个将会被排定的元素,
     * 然后从数组的左端开始向右扫描直到找到一个大于等于它的元素,
     * 再从数组的右端开始向左扫描直到找到一个小于等于它的元素.
     * 交换它们的位置,当两个指针相遇时,只需要将切分元素a[lo]和左子数组最右侧的元素(a[j])交换然后返回即可.
     *
     * @param a  子数组
     * @param lo 上界
     * @param hi 下界
     * @return 切分元素
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分为a[lo..i-1],a[i],a[i+1..hi]
        int i = lo, j = hi + 1; //左右扫描的指针
        Comparable v = a[lo]; //切分元素
        while (true) {
            //扫描左右,检查扫描是否结束并交换元素
            // find item on lo to swap
            while (less(a[++i], v)) {
                if (i == hi)
                    break;
            }
            // find item on hi to swap
            while (less(v, a[--j])) {
                if (j == lo)
                    break;
            }
            // check if pointers cross
            if (i >= j)
                break;
            exch(a, i, j);
        }
        //将v=a[j]放入正确的位置
        exch(a, lo, j);
        return j; //a[lo..j-1] <= a[j] <= a[j+1..hi]
    }

    /**
     * Rearranges the array so that {@code a[k]} contains the kth smallest key;
     * {@code a[0]} through {@code a[k-1]} are less than (or equal to) {@code a[k]}; and
     * {@code a[k+1]} through {@code a[n-1]} are greater than (or equal to) {@code a[k]}.
     *
     * @param a the array
     * @param k the rank of the key
     * @return the key of rank {@code k}
     */
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }


    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; quicksorts them;
     * and prints them to standard output in ascending order.
     * Shuffles the array and then prints the strings again to
     * standard output, but this time, using the select method.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick.sort(a);
        show(a);
        assert isSorted(a);

        // shuffle
        StdRandom.shuffle(a);

        // display results again using select
        StdOut.println();
        for (int i = 0; i < a.length; i++) {
            String ith = (String) Quick.select(a, i);
            StdOut.println(ith);
        }
    }

}
