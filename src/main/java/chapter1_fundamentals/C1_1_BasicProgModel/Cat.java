package chapter1_fundamentals.C1_1_BasicProgModel;

/*************************************************************************
 *  Compilation:  javac Cat.java
 *  Execution:    java Cat input0.txt input1.txt ... output.txt
 *  Dependencies: In.java Out.java
 *
 *  Reads in text files specified as the first command-line
 *  parameters, concatenates them, and writes the result to
 *  filename specified as the last command line parameter.
 *
 *  % more in1.txt
 *  This is
 *
 *  % more in2.txt
 *  a tiny
 *  test.
 *
 *  % java Cat in1.txt in2.txt out.txt
 *
 *  % more out.txt
 *  This is
 *  a tiny
 *  test.
 *
 *************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

/**
 * concatenate files
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Cat {
    public static void main(String[] args) {
        //将所有输入文件复制到输出流(最后一个参数)中
        Out out = new Out(args[args.length - 1]);
        for (int i = 0; i < args.length - 1; i++) {
            //读取i个文件
            In in = new In(args[i]);
            String s = in.readAll();
            out.println(s);
            in.close();
        }
        out.close();
    }
}
