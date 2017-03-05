package chapter1_fundamentals.C1_3_BagsQueuesStacks;

/**
 * 一个只能处理String类型的定容栈(不支持迭代).
 * 使用一个数组用于保存栈中的元素.
 * <p>
 * Created by SylvanasSun on 2017/3/5.
 */
public class FixedCapacityStackOfStrings {

    private String[] a;
    private int N; //当前元素数量

    public FixedCapacityStackOfStrings(int cap) {
        a = new String[cap];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(String s) {
        a[N++] = s;
    }

    public String pop() {
        return a[--N];
    }

}
