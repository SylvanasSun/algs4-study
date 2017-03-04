package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

import java.util.Scanner;

/**
 * 编写一个静态方法histogram(),接受一个整型数组a[]和一个整数M为参数并返回一个大小为M的数组,
 * 其中第i个元素的值为整数i在参数数组中出现的次数.如果a[]中的值均在0到M-1之间,
 * 返回数组中所有元素之和应该和a.length相等.
 * <p>
 * Created by SylvanasSun on 2017/3/4.
 */
public class Ex_1_1_15 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a the size of the array and m.");
        int aLength = scanner.nextInt();
        int m = scanner.nextInt();

        int[] a = new int[aLength];
        //初始化数组a
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * m);
        }

        int[] b = histogram(a, m);
        int sum = 0;
        for (int i = 0; i < b.length; i++) {
            sum += b[i];
        }

        if (sum == a.length) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static int[] histogram(int[] a, int m) {
        int[] b = new int[m];

        for (int i = 0; i < a.length; i++) {
            b[a[i]]++;
        }

        return b;
    }

}
