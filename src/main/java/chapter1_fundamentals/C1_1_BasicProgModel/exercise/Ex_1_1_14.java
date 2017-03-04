package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

/**
 * 编写一个静态方法lg(),接受一个整型参数N,返回不大于log2N的最大整数.不要使用Math库.
 * <p>
 * Created by SylvanasSun on 2017/3/4.
 */
public class Ex_1_1_14 {

    public static void main(String[] args) {
        int result = lg(Integer.parseInt(args[0]));
        System.out.println(result);
    }

    public static int lg(int n) {
        int m = 1;
        int i;
        for (i = 0; m <= n; i++) {
            m *= 2;
        }
        return i - 1;
    }
}
