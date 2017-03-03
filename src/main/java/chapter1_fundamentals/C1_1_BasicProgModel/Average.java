package chapter1_fundamentals.C1_1_BasicProgModel;


/*************************************************************************
 *  Compilation:  javac Average.java
 *  Execution:    java Average < data.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Reads in a sequence of real numbers, and computes their average.
 *
 *  % java Average
 *  10.0 5.0 6.0
 *  3.0 7.0 32.0
 *  <Ctrl-d>
 *  Average is 10.5

 *  Note <Ctrl-d> signifies the end of file on Unix.
 *  On windows use <Ctrl-z>.
 *
 *************************************************************************/

import edu.princeton.cs.algs4.StdIn;

/**
 * 计算平均值
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Average {

    private Average() {
    }

    public static void main(String[] args) {
        int count = 0;
        double sum = 0.0;

        //读取数据并计算总数与统计个数
        while (!StdIn.isEmpty()) {
            double value = StdIn.readDouble();
            sum += value;
            count++;
        }

        double average = sum / count;

        System.out.println("Average is " + average);
    }

}
