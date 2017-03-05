package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

import edu.princeton.cs.algs4.StdOut;

import java.util.Scanner;

/**
 * 编写一段程序,从标准输入按行读取数据,其中每行都包含一个名字和两个整数.
 * 然后用printf()打印一张表格,每行的若干列数据包括名字、两个整数和第一个整数除以第二个整数的结果.
 * 精确到小数点后三位.可以用这种程序将棒球球手的击球命中率或者学生的考试分数制成表格.
 * <p>
 * Created by SylvanasSun on 2017/3/5.
 */
public class Ex_1_1_21 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] strings = line.split("\\s+");
            String name = strings[0];
            Integer a = Integer.parseInt(strings[1]);
            Integer b = Integer.parseInt(strings[2]);

            System.out.printf("name: %s a: %d b: %d a/b: %.3f", name, a, b, (double) (a / b));
        }
    }

}
