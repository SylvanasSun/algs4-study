package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

/**
 * 将一个正整数N用二进制表示并转换为一个String类型的值s.
 * Integer.toBinaryString(N)就是用于专门完成这个需求的.
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Ex_1_1_09 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        String s = "";
        //十进制转二进制,系数除基数,对商取余
        for (int n = N; n > 0; n /= 2) {
            s = (n % 2) + s;
        }
        System.out.println(s);
    }
}
