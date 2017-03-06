package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;

/**
 * 一个支持迭代器并且可以动态扩容的Stack,内部数据使用了数组实现.
 * <p>
 * Created by SylvanasSun on 2017/3/6.
 */
public class ResizingArrayStack<T> implements Iterable<T> {

    private T[] a = (T[]) new Object[1];
    private int N = 0; //size

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(T t) {
        //判断栈元素是否已满
        if (N == a.length) resize(2 * a.length);
        a[N++] = t;
    }

    public T pop() {
        T t = a[--N];
        a[N] = null; //避免对象游离
        //当栈元素只占四分之一时,进行减容
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return t;
    }

    /**
     * 通过拷贝一个大小为max的数组来达成扩容
     */
    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    /**
     * 由于Stack是后进先出的,所以需要逆向遍历
     */
    private class ReverseArrayIterator implements Iterator<T> {

        private int i = N;

        public boolean hasNext() {
            return i > 0;
        }

        public T next() {
            return a[--i];
        }

        public void remove() {

        }
    }
}
