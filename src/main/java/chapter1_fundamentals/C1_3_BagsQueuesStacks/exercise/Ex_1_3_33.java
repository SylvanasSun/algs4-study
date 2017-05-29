package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

import java.util.Iterator;

/**
 * 编写一个双向队列,它同时支持在两端添加或删除元素.
 * 需要实现如下API的双向链表Deque与一个使用动态数组调整实现这份API的ResizingArrayDeque.
 * public class Deque<Item> implements Iterable<Item>
 * construction Deque() 创建空双向队列
 * boolean  isEmpty() 双向队列是否为空
 * int      size() 双向队列中的元素数量
 * void     pushLeft(Item item) 向左端添加一个新元素
 * void     pushRight(Item item) 向右端添加一个新元素
 * Item     popLeft() 从左端删除一个元素
 * Item     popRight() 从右端删除一个元素
 *
 * Created by SylvanasSun on 2017/5/29.
 */
public class Ex_1_3_33 {

    public class Deque<Item> implements Iterable<Item> {

        private Node head, tail;
        private int size = 0;

        private class Node {
            final Item item;
            Node prev, next;

            public Node(Item item, Node prev, Node next) {
                this.item = item;
                this.prev = prev;
                this.next = next;
            }
        }

        public Deque() {
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void pushLeft(Item item) {
            checkItemIsNull(item);
            if (head == null)
                pushIfHeadIsNull(item);
            else
                pushIfHeadNotNull(item);
        }

        private void pushIfHeadIsNull(Item item) {
            head = new Node(item, null, null);
            if (tail == null)
                tail = head;
            size++;
        }

        private void pushIfHeadNotNull(Item item) {
            Node newNode = new Node(item, null, head);
            head.prev = newNode;
            head = newNode;
            size++;
        }

        public void pushRight(Item item) {
            checkItemIsNull(item);
            if (tail == null)
                pushIfTailIsNull(item);
            else
                pushIfTailNotNull(item);
        }

        private void pushIfTailIsNull(Item item) {
            tail = new Node(item, null, null);
            if (head == null)
                head = tail;
            size++;
        }

        private void pushIfTailNotNull(Item item) {
            Node newNode = new Node(item, tail, null);
            tail.next = newNode;
            tail = newNode;
            size++;
        }

        private void checkItemIsNull(Item item) {
            if (item == null)
                throw new IllegalArgumentException();
        }

        public Item popLeft() {
            checkDequeIsEmpty();
            return popHeadAndReturnItem();
        }

        private Item popHeadAndReturnItem() {
            Item item = head.item;
            Node newHead = head.next;
            if (newHead == null && head == tail) {
                head = null;
                tail = null;
                size--;
            } else if (newHead != null) {
                newHead.prev = null;
                head = newHead;
                size--;
            }
            return item;
        }

        public Item popRight() {
            checkDequeIsEmpty();
            return popTailAndReturnItem();
        }

        private Item popTailAndReturnItem() {
            Item item = tail.item;
            Node newTail = tail.prev;
            if (newTail == null && tail == head) {
                tail = null;
                head = null;
                size--;
            } else if (newTail != null) {
                newTail.next = null;
                tail = newTail;
                size--;
            }
            return item;
        }

        private void checkDequeIsEmpty() {
            if (isEmpty())
                throw new NullPointerException();
        }

        public Iterator<Item> iterator() {
            return new DequeIterator();
        }

        private class DequeIterator implements Iterator<Item> {
            private Deque<Item> copy;

            public DequeIterator() {
                copy = new Deque<Item>();
                Node temp = head;
                while (temp != null) {
                    copy.pushRight(temp.item);
                    temp = temp.next;
                }
            }

            public boolean hasNext() {
                return !copy.isEmpty();
            }

            public Item next() {
                if (!hasNext())
                    throw new NullPointerException();
                return copy.popLeft();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        }

    }

    public class ResizingArrayDeque<Item> implements Iterable<Item> {

        private Object[] items;
        private int head, tail;
        private int size;
        private static final int DEFAULT_CAPACITY = 16;
        private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

        public ResizingArrayDeque() {
            this.items = new Object[DEFAULT_CAPACITY];
            this.head = 0;
            this.tail = 0;
            this.size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void pushLeft(Item item) {
            checkItemIsNull(item);
            checkSizeAndExpand();
            pushToHead(item);
        }

        private void pushToHead(Item item) {
            items[head++] = item;
            if (head == size)
                head = 0;
            size++;
        }

        public void pushRight(Item item) {
            checkItemIsNull(item);
            checkSizeAndExpand();
            pushToTail(item);
        }

        private void pushToTail(Item item) {
            tail = size;
            items[tail++] = item;
            size++;
        }

        public Item popLeft() {
            checkIsEmpty();
            Item item = popFromHead();
            checkSizeAndReduce();
            return item;
        }

        private Item popFromHead() {
            Object item = items[head];
            items[head++] = null;
            if (head == size)
                head = 0;
            size--;
            return (Item) item;
        }

        public Item popRight() {
            checkIsEmpty();
            Item item = popFromTail();
            checkSizeAndReduce();
            return item;
        }

        private Item popFromTail() {
            tail = --size;
            Object item = items[tail];
            items[tail] = null;
            return (Item) item;
        }

        private void checkSizeAndReduce() {
            if (size <= items.length / 4)
                resize(items.length >>> 1);
        }

        private void checkIsEmpty() {
            if (isEmpty())
                throw new NullPointerException();
        }

        private void checkSizeAndExpand() {
            if (size >= items.length)
                resize(items.length << 1);
        }

        private void checkItemIsNull(Item item) {
            if (item == null)
                throw new IllegalArgumentException();
        }

        private void resize(int capacity) {
            assert capacity > 0 && capacity > size;
            if (capacity >= MAX_ARRAY_SIZE)
                capacity = hugeCapacity(capacity);
            Object[] temp = new Object[capacity];
            for (int i = 0; i < size; i++) {
                temp[i] = items[(head + i) % items.length];
            }
            this.items = temp;
            head = 0;
            tail = size;
        }

        private int hugeCapacity(int capacity) {
            if (capacity < 0)
                throw new OutOfMemoryError();
            return capacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
        }

        public Iterator<Item> iterator() {
            return new ResizingArrayDequeIterator();
        }

        private class ResizingArrayDequeIterator implements Iterator<Item> {
            private int i;

            public ResizingArrayDequeIterator() {
                i = 0;
            }

            public boolean hasNext() {
                return i < size;
            }

            public Item next() {
                if (!hasNext())
                    throw new NullPointerException();
                Object item = items[(i + head) % items.length];
                i++;
                return (Item) item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

    }

}
