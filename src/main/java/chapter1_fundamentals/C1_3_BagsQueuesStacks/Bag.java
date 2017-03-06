package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;

/**
 * 使用链表实现的Bag,Bag的访问顺序也是后进先出的,只不过Bag没有删除元素的操作
 * <p>
 * Created by SylvanasSun on 2017/3/6.
 */
public class Bag<T> implements Iterable<T> {

    private Node first; //链表头部节点
    private int N; //size

    /**
     * 用于表示链表中的节点
     */
    private class Node {
        T item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    /**
     * 向背包中添加元素
     *
     * @param t 添加的元素
     */
    public void add(T t) {
        Node oldFirst = first;
        first = new Node();
        first.item = t;
        first.next = oldFirst;
        N++;
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

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
