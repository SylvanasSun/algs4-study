package chapter1_fundamentals.C1_3_BagsQueuesStacks;

/**
 * 带有泛型的定容栈.
 * <p>
 * Created by SylvanasSun on 2017/3/5.
 */
public class FixedCapacityStack<T> {

    private T[] a;
    private int N;

    public FixedCapacityStack(int cap) {
        a = (T[]) new Object[cap];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(T t) {
        a[N++] = t;
    }

    public T pop() {
        return a[--N];
    }

}
