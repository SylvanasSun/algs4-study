package chapter1_fundamentals.C1_2_DataAbsType;

/******************************************************************************
 *  Compilation:  javac Counter.java
 *  Execution:    java Counter n trials
 *  Dependencies: StdRandom.java StdOut.java
 *
 *  A mutable data type for an integer counter.
 *
 *  The test clients create n counters and performs trials increment
 *  operations on random counters.
 *
 * java Counter 6 600000
 *  100140 counter0
 *  100273 counter1
 *  99848 counter2
 *  100129 counter3
 *  99973 counter4
 *  99637 counter5
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * counter
 * <p>
 * Created by SylvanasSun on 2017/3/3.
 */
public class Counter implements Comparable<Counter> {

    private final String name; //Counter name
    private int count; //value

    public Counter(String name) {
        this.name = name;
    }

    public void increment() {
        count++;
    }

    public int tally() {
        return count;
    }

    @Override
    public String toString() {
        return count + " " + name;
    }

    public int compareTo(Counter o) {
        if (this.count < o.count) return -1;
        else if (this.count > o.count) return 1;
        else return 0;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        Counter[] hits = new Counter[N];
        for (int i = 0; i < N; i++) {
            hits[i] = new Counter("counter" + i);
        }

        for (int t = 0; t < T; t++) {
            hits[StdRandom.uniform(N)].increment();
        }

        for (int i = 0; i < N; i++) {
            StdOut.println(hits[i]);
        }
    }
}
