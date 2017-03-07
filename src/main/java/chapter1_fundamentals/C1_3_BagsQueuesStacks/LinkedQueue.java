package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 基于链表实现的Queue,相比Queue类包含的操作函数更丰富.
 * <p>
 * Created by SylvanasSun on 2017/3/7.
 */
public class LinkedQueue<T> implements Iterable<T> {

    private int size; //number of elements on queue
    private Node first; //队列头部
    private Node last; //队列尾部

    /**
     * 链表中的节点
     */
    private class Node {
        private T item;
        private Node next;
    }

    public LinkedQueue() {
        first = null;
        last = null;
        size = 0;
        assert check();
    }

    /**
     * 判断队列是否为空,这个方法的实现也可以使用@<code>size==0</code>。
     *
     * @return 如果为true代表队列为空, 反之如果为false队列则不为空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 返回队列中的元素数量
     *
     * @return 队列元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 将元素加入到队列中
     *
     * @param t 添加到队列中的元素
     */
    public void enqueue(T t) {
        Node oldLast = last;
        last = new Node();
        last.item = t;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        size++;
        assert check();
    }

    /**
     * 出队操作,将元素从队列中移除
     *
     * @return 被移除的元素
     * @throws java.util.NoSuchElementException 如果队列为空抛出异常
     */
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("LinkedQueue underflow");
        T item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        assert check();
        return item;
    }


    /**
     * 将队列头部中的元素返回给调用者,但不会对这个节点进行移除操作
     *
     * @return 队列头部中的元素
     * @throws java.util.NoSuchElementException 如果队列为空抛出异常
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("LinkedQueue underflow");
        return first.item;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T t : this) {
            stringBuilder.append(t).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 检查内部变量
     */
    private boolean check() {
        if (size < 0)
            return false;

        if (size == 0) {
            if (first != null) return false;
            if (last != null) return false;
        }

        if (size == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null) return false;
        } else {
            if (first == null) return false;
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.next != null) return false;
        }


        // check internal consistency of instance variable size
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= size; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != size) return false;

        // check internal consistency of instance variable last
        Node lastNode = first;
        while (lastNode.next != null) {
            lastNode = lastNode.next;
        }
        if (last != lastNode) return false;

        return true;
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
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedQueue<String> queue = new LinkedQueue<String>();
        while (scanner.hasNextLine()) {
            String order = scanner.nextLine();
            if ("end".equals(order))
                break;
            if (!"-".equals(order)) {
                queue.enqueue(order);
            } else {
                System.out.println("dequeue: " + queue.dequeue());
            }
        }
        System.out.println("queue size: " + queue.size());
        for (String s : queue) {
            System.out.print(s + " ");
        }
    }
}
