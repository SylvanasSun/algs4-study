package chapter1_fundamentals.C1_2_DataAbsType;

import edu.princeton.cs.algs4.StdDraw;

/**
 * 可视化的Accumulator.
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class VisualAccumulator {
    private double total;
    private int N;

    public VisualAccumulator(int trials, double max) {
        StdDraw.setXscale(0, trials);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(.005);
    }

    /**
     * 用灰色画出所有数据的点
     * 用红色画出实时的平均值
     */
    public void addDataValue(double value) {
        N++;
        total += value;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(N, value);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(N, mean());
    }

    private double mean() {
        return total / N;
    }

    @Override
    public String toString() {
        return "Mean (" + N + "values: " + String.format("%8.5f", mean());
    }
}
