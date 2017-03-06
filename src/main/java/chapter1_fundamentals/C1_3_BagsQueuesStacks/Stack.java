package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;

/**
 * 使用链表实现的可迭代的下压栈(后进先出)
 * <p>
 * Created by SylvanasSun on 2017/3/6.
 */
public class Stack<T> implements Iterable<T> {

    private Node first; //栈顶(链表头部)
    private int N; //元素个数

    /**
     * 用于表示链表中的节点
     */
    private class Node {
        T item;
        Node next;
    }

    /**
     * 判断Stack是否为空
     *
     * @return true代表Stack为空, false为未空
     */
    public boolean isEmpty() {
        return first == null; //也可以用N==0来判断
    }

    /**
     * 返回Stack中的元素数量
     *
     * @return 元素数量
     */
    public int size() {
        return N;
    }

    /**
     * 将元素t添加到栈顶
     *
     * @param t 添加的元素
     */
    public void push(T t) {
        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
        N++;
    }

    /**
     * 将栈顶的元素弹出
     *
     * @return 栈顶节点的item
     */
    public T pop() {
        T item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**
     * 迭代器,维护了一个实例变量current来记录链表的当前结点.
     * 这段代码可以在Stack和Queue之间复用,因为它们内部数据的数据结构是相同的,
     * 只是访问顺序分别为后进先出和先进先出而已.
     */
    private class ListIterator implements Iterator<T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {

        }
    }
}
