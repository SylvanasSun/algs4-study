package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

/**
 * 编写一个程序,从命令行得到三个整数参数.如果它们都相等则打印equal,否则打印not equal.
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Ex_1_1_03 {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);
        if (a == b && b == c) {
            System.out.println("equal");
        } else {
            System.out.println("not equal");
        }
    }
}
