package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

import java.util.Scanner;

/**
 * 编写一段代码,打印出一个M行N列的二维数组的转置(交换行和列)
 * <p>
 * Created by SylvanasSun on 2017/3/4.
 */
public class Ex_1_1_13 {

    /**
     * 定义了2个二维数组a、b,并初始化数组a,b则为数组a的转置数组
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //监听用户输入的参数来规定数组大小.
        System.out.println("Please enter two-dimensional array of rows and columns.");
        int row = scanner.nextInt();
        int column = scanner.nextInt();

        int[][] a = new int[row][column];
        int[][] b = new int[column][row];
        int limit = 10;
        initArray(a, limit); //初始化数组a
        replacementArray(a, b); //对数组b进行赋值(数组a的转置数组)
        System.out.println("Array b");
        printArray(b);
    }

    /**
     * 初始化二维数组
     *
     * @param a     需要初始化的二维数组
     * @param limit 生成随机数的数值上限
     */
    private static void initArray(int[][] a, int limit) {
        for (int x = 0; x < a.length; x++) {
            for (int y = 0; y < a[0].length; y++) {
                a[x][y] = (int) (Math.random() * limit);
            }
        }
        System.out.println("Array a");
        printArray(a);
    }

    /**
     * 对数组进行转置操作
     */
    private static void replacementArray(int[][] a, int[][] b) {
        for (int x = 0; x < a.length; x++) {
            for (int y = 0; y < a[0].length; y++) {
                b[y][x] = a[x][y];
            }
        }
    }

    /**
     * 将数组输出到控制台
     */
    private static void printArray(int[][] arr) {
        for (int x = 0; x < arr.length; x++) {
            System.out.print("row: " + x);
            System.out.println();
            System.out.print("column: ");
            for (int y = 0; y < arr[0].length; y++) {
                System.out.print(arr[x][y] + " ");
            }
            System.out.println();
        }
    }

}
