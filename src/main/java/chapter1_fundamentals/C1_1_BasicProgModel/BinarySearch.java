package chapter1_fundamentals.C1_1_BasicProgModel;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Execution: java BinarySearch tinyW.txt < tinyT.text
 * DataFile:
 * http://algs4.cs.princeton.edu/11model/tinyW.txt
 * http://algs4.cs.princeton.edu/11model/tinyT.txt
 * http://algs4.cs.princeton.edu/11model/largeW.txt
 * http://algs4.cs.princeton.edu/11model/largeT.txt
 * <p>
 * % java BinarySearch tinyW.txt < tinyT.txt
 * 50
 * 99
 * 13
 * <p>
 * % java BinarySearch largeW.txt < largeT.txt | more
 * 499569
 * 984875
 * 295754
 * 207807
 * 140925
 * 161828
 * [3,675,966 total values]
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class BinarySearch {

    /**
     * 二分查找(需要数组必须是有序的)
     */
    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] whiteList = In.readInts(args[0]);

        Arrays.sort(whiteList);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            //将不存在于白名单中的数输出打印
            if (rank(key, whiteList) == -1) {
                StdOut.println(key);
            }
        }
    }
}
