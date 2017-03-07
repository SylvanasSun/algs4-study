package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;

/**
 * 使用链表实现的Queue,它与Stack的区别在于链表的访问顺序.
 * Queue的访问顺序是先进先出的.
 * <p>
 * Created by SylvanasSun on 2017/3/6.
 */
public class Queue<T> implements Iterable<T> {

    private Node first; //链表头部,即队头
    private Node last; //链表尾部,即队尾
    private int N; //size

    /**
     * 用于表示链表中的节点
     */
    private class Node {
        T item;
        Node next;
    }

    /**
     * 判断Queue是否为空
     *
     * @return true代表Stack为空, false为未空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 返回Queue中的元素数量
     *
     * @return 元素数量
     */
    public int size() {
        return N;
    }

    /**
     * 入队,向队尾添加新的元素
     *
     * @param item 添加的元素
     */
    public void enqueue(T item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        /**
         * 如果队列为空,队头指向队尾(队列中只有一个元素),
         * 否则将旧的队尾的next指向新的队尾
         */
        if (isEmpty()) first = last;
        else oldLast.next = last;
        N++;
    }

    /**
     * 出队,将队头节点弹出队列
     *
     * @return 队头节点的item
     */
    public T dequeue() {
        T item = first.item;
        first = first.next;
        N--;
        //如果队列为空,队尾则为null
        if (isEmpty()) last = null;
        return item;
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**
     * 迭代器,与Stack中的实现一致
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
