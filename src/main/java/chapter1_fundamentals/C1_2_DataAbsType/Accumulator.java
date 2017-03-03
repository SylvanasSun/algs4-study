package chapter1_fundamentals.C1_2_DataAbsType;

/******************************************************************************
 *  Compilation:  javac Accumulator.java
 *  Execution:    java Accumulator < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *
 *  Mutable data type that calculates the mean, sample standard
 *  deviation, and sample variance of a stream of real numbers
 *  use a stable, one-pass algorithm.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * running average and stddev
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Accumulator {

    private int n = 0;          // number of data values
    private double sum = 0.0;   // sample variance * (n-1)
    private double mu = 0.0;    // sample mean

    /**
     * Initializes an accumulator.
     */
    public Accumulator() {
    }

    /**
     * Adds the specified data value to the accumulator.
     *
     * @param x the data value
     */
    public void addDataValue(double x) {
        n++;
        double delta = x - mu;
        mu += delta / n;
        sum += (double) (n - 1) / n * delta * delta;
    }

    /**
     * Returns the mean of the data values.
     *
     * @return the mean of the data values
     */
    public double mean() {
        return mu;
    }

    /**
     * Returns the sample variance of the data values.
     *
     * @return the sample variance of the data values
     */
    public double var() {
        if (n <= 1) return Double.NaN;
        return sum / (n - 1);
    }

    /**
     * Returns the sample standard deviation of the data values.
     *
     * @return the sample standard deviation of the data values
     */
    public double stddev() {
        return Math.sqrt(this.var());
    }

    /**
     * Returns the number of data values.
     *
     * @return the number of data values
     */
    public int count() {
        return n;
    }

    /**
     * Unit tests the {@code Accumulator} data type.
     * Reads in a stream of real number from standard input;
     * adds them to the accumulator; and prints the mean,
     * sample standard deviation, and sample variance to standard
     * output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Accumulator stats = new Accumulator();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            stats.addDataValue(x);
        }

        StdOut.printf("n      = %d\n", stats.count());
        StdOut.printf("mean   = %.5f\n", stats.mean());
        StdOut.printf("stddev = %.5f\n", stats.stddev());
        StdOut.printf("var    = %.5f\n", stats.var());
    }

}
