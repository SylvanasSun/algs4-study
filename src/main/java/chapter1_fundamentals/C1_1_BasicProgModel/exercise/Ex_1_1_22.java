package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

/**
 * 使用1.1.6.4节中的rank()递归方法重新实现BinarySearch并跟踪该方法的调用.
 * 每当该方法被调用时,打印出它的参数lo和hi并按照递归的深度缩进.
 * 提示:为递归方法添加一个参数来保存递归的深度.
 * <p>
 * Created by SylvanasSun on 2017/3/5.
 */
public class Ex_1_1_22 {

    public static int rank(int key, int[] a, int lo, int hi, int deep) {
        System.out.printf("%s%d%d\n", retract(4 * deep, ' '), lo, hi);

        if (lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;

        if (a[mid] < key) return rank(key, a, mid + 1, hi, ++deep);
        if (a[mid] > key) return rank(key, a, lo, mid - 1, ++deep);
        return mid;
    }

    public static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1, 1);
    }

    /**
     * 根据深度来进行缩进
     *
     * @param n 缩进的空格数
     * @param c 空格字符
     */
    private static String retract(int n, char c) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += c;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] whitelist = In.readInts(args[0]);

        Arrays.sort(whitelist);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, whitelist) == -1) {
                System.out.println(key);
            }
        }
    }

}
