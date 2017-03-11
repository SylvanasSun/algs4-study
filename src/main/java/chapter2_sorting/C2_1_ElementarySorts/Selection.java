package chapter2_sorting.C2_1_ElementarySorts;

/******************************************************************************
 *  Compilation:  javac Selection.java
 *  Execution:    java  Selection < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/21sort/tiny.txt
 *                http://algs4.cs.princeton.edu/21sort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using selection sort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Selection < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Selection < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

import java.util.Comparator;
import java.util.Scanner;

/**
 * 选择排序
 * 基本思想:
 * 首先,将找到数组中最小的那个元素,其次,将它和数组的第一个元素交换位置(如果第一个元素就是最小元素那么它就和自己交换).
 * 再次,在剩下的元素中找到最小的元素,将它与数组的第二个元素交换位置.如此反复,直到将整个数组排序.
 * <p>
 * 选择排序有两个特点:
 * 1. 运行时间和输入无关.为了找出最小的元素而扫描一遍数组并不能为下一遍扫描提供什么信息.
 * 如果一个已经有序的数组或是主键全部相等的数组和一个元素随机排序的数组所用的排序时间是一样长的.
 * 2. 数据移动是最少的.每次交换都会改变两个数组元素的值,因此选择排序用了N次交换----交换次数和数组的大小是线性关系.
 * <p>
 * Created by SylvanasSun on 2017/3/11.
 */
public class Selection {

    private Selection() {
    }


    /**
     * 使用升序的顺序排序(自然排序)
     *
     * @param a 需要排序的数组
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i; //最小元素的索引
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min]))
                    min = j;
                exch(a, i, min);
                assert isSorted(a, 0, i);
            }
        }
        assert isSorted(a);
    }

    /**
     * 使用升序的顺序排序(使用指定的比较规则)
     *
     * @param a          需要排序的数组
     * @param comparator 比较器指定的比较规则
     */
    public static void sort(Object[] a, Comparator comparator) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i; //最小元素的索引
            for (int j = i + 1; j < N; j++) {
                if (less(comparator, a[j], a[min]))
                    min = j;
                exch(a, i, min);
                assert isSorted(a, comparator, 0, i);
            }
        }
        assert isSorted(a, comparator);
    }


    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    // is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    // is the array a[] sorted?
    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(comparator, a[i], a[i - 1])) return false;
        return true;
    }


    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
//        String[] s = StdIn.readAllStrings();
        String[] s = new Scanner(System.in).nextLine().split("\\s+");
        Selection.sort(s);
        Selection.show(s);
    }
}
