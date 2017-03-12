package chapter2_sorting.C2_1_ElementarySorts;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 用于比较排序算法性能的实验
 * <p>
 * Created by SylvanasSun on 2017/3/12.
 */
public class SortCompare {

    public static double time(String alg, Double[] a) {
        long start = System.currentTimeMillis();
        if ("Insertion".equals(alg))
            Insertion.sort(a);
        if ("Selection".equals(alg))
            Selection.sort(a);

        long end = System.currentTimeMillis();
        return (end - start) / 1000.0;
    }

    /**
     * 生成随机的Double值,将它们排序,并返回指定次测试的总时间
     *
     * @param N 数组大小
     * @param T 测试次数
     */
    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) {
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }
            total += time(alg, a);
        }
        return total;
    }

    /**
     * args:Insertion Selection 1000 100
     */
    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);
        System.out.printf("For %d random Doubles\n   %s is", N, alg1);
        System.out.printf(" %.1f times faster than %s\n", t2 / t1, alg2);
    }

}
