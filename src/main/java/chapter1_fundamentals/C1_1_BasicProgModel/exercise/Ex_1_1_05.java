package chapter1_fundamentals.C1_1_BasicProgModel.exercise;

/**
 * 1.1.5: 编写一段程序,如果double类型的变量x和y都严格位于0和1之间则打印true,否则打印false.
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Ex_1_1_05 {
    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        if ((x >= 0 && x <= 1) && (y >= 0 && y <= 1)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
