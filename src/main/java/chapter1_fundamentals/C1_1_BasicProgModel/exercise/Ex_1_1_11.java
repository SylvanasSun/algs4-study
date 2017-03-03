package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

/**
 * 打印出一个二维布尔数组的内容,其中,使用*表示true、空格表示false.打印出行号和列号.
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Ex_1_1_11 {
    public static void main(String[] args) {
        boolean[][] booleans = new boolean[10][10];

        //初始化二维数组
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                double r = Math.random() * 10;
                if (r > 5) {
                    booleans[x][y] = true;
                } else {
                    booleans[x][y] = false;
                }
            }
        }

        //遍历二维数组
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                boolean b = booleans[x][y];
                if (b) {
                    System.out.println("row: " + x + " column: " + y + " --> *");
                } else {
                    System.out.println("row: " + x + " column: " + y + " --> ");
                }
            }
        }
    }
}
