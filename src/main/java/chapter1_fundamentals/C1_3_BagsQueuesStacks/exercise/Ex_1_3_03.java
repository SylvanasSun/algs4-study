package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 假设某个用例程序会进行一系列入栈和出栈的混合栈操作.
 * 入栈操作会将整数0到9按顺序压入栈;出栈操作会打印出返回值.
 * 下面哪种序列是不可能产生的?
 * % java Ex_1_3_03
 * 9 8 7 6 5 4 3 2 1 0
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * 4 3 2 1 0 9 8 7 6 5
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * 4 6 8 7 5 3 2 9 0 1
 * false (Unprinted: 2; Stack: [ 1 0 ])
 * <p>
 * 2 5 6 7 4 8 9 3 1 0
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * 4 3 2 1 0 5 6 7 8 9
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * 1 2 3 4 5 6 9 8 7 0
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * 0 4 6 5 3 8 1 7 2 9
 * false (Unprinted: 4; Stack: [ 9 7 2 1 ])
 * <p>
 * 1 4 7 9 8 6 5 3 0 2
 * false (Unprinted: 2; Stack: [ 2 0 ])
 * <p>
 * 2 1 4 3 6 5 8 7 9 0
 * true (Unprinted: 0; Stack: [ ])
 * <p>
 * Created by SylvanasSun on 2017/3/10.
 */
public class Ex_1_3_03 {

    private static void checkSequeue(int[] a) {

        Stack<Integer> s = new Stack<Integer>();
        int n = a.length;

        int i = 0, j = 0;
        while (i < n && j <= n) {
            if (!s.isEmpty() && s.peek() == a[i]) {
                StdOut.print(s.pop() + " ");
                i++;
            } else {
                if (j < n)
                    s.push(j);
                j++;
            }
        }
        StdOut.println();

        StdOut.printf("%s (Unprinted: %d; Stack: [ %s])\n",
                i == n && s.isEmpty(), n - i, s);
    }

    public static void main(String[] args) {
        String[] strings = StdIn.readAll().split("\\s+");

        int[] a = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            a[i] = Integer.parseInt(strings[i]);
        }

        checkSequeue(a);
    }

}
