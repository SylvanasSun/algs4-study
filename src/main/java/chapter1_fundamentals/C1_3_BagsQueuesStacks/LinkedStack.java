package chapter1_fundamentals.C1_3_BagsQueuesStacks;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 基于链表实现的Stack,相比Stack类包含的操作函数更丰富.
 * <p>
 * Created by SylvanasSun on 2017/3/7.
 */
public class LinkedStack<T> implements Iterable<T> {

    private int size; //element size of the stack
    private Node first; //top of stack

    /**
     * 链表中的节点
     */
    private class Node {
        private T item;
        private Node next;
    }

    public LinkedStack() {
        first = null;
        size = 0;
        assert check();
    }

    /**
     * 判断栈是否为空,这个方法的实现也可以使用@<code>size==0</code>。
     *
     * @return 如果为true代表栈为空, 反之如果为false栈则不为空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 返回栈中的元素个数
     *
     * @return 栈中的元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 将元素压入栈顶
     *
     * @param item 添加的元素
     */
    public void push(T item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
        assert check();
    }

    /**
     * 将栈顶的元素弹出栈并返回给调用者
     *
     * @return 弹出的栈顶元素
     * @throws java.util.NoSuchElementException 如果栈元素溢出抛出异常
     */
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("LinkedStack underflow");
        T item = first.item;
        first = first.next; //delete first node
        size--;
        assert check();
        return item;
    }

    /**
     * 返回栈顶元素,但不会将元素弹出栈(即不会删除元素)
     *
     * @return 栈顶元素
     * @throws java.util.NoSuchElementException 如果栈元素溢出抛出异常
     */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("LinkedStack underflow");
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
        } else if (size == 1) {
            if (first == null) return false;
            if (first.next != null) return false;
        } else {
            if (first == null) return false;
            if (first.next == null) return false;
        }

        //check internal consistency of instance variable size
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= size; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != size)
            return false;

        return true;
    }

    /**
     * 返回一个按照后进先出顺序进行遍历的迭代器
     *
     * @return 按照后进先出顺序进行遍历的迭代器
     */
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        //实例变量current来记录链表的当前结点
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
        LinkedStack<String> stack = new LinkedStack<String>();
        while (scanner.hasNextLine()) {
            String order = scanner.nextLine();
            if ("end".equals(order)) {
                break;
            }
            if (!order.equals("-"))
                stack.push(order);
            else if (!stack.isEmpty())
                System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}
