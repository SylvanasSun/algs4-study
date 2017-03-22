package chapter3_searching.C3_1_SymbolTables;

/******************************************************************************
 *  Compilation:  javac SequentialSearchST.java
 *  Execution:    java SequentialSearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java SequentialSearchST < tiny.txt
 *  L 11
 *  P 10
 *  M 9
 *  X 7
 *  H 5
 *  C 4
 *  R 3
 *  A 8
 *  E 12
 *  S 0
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;

/**
 * The {@code SequentialSearchST} class represents an (unordered) symbol table of generic key-value pairs.
 * using linked list implements.
 * {@code K} is key,{@code V} is value.
 * <p>
 * Created by SylvanasSun on 2017/3/22.
 *
 * @author SylvanasSun
 */
public class SequentialSearchST<K, V> {

    private int size; // the number of key-value pairs size
    private Node first; // the linked list head node

    // linked list data type
    private class Node {
        private K key;
        private V val;
        private Node next;

        public Node(K key, V val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table
     */
    public SequentialSearchST() {
    }

    /**
     * Returns the number of key-value pairs in this symbol table
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this symbol table is empty
     *
     * @return {@code true} if this symbol table is empty;{@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the value associated with the given key in this symbol table
     *
     * @param key the key
     * @return the value associated with the given key in this symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("argument key to get() is null.");
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;  // hit the target
        }
        return null; // no hit the target
    }

    /**
     * Insert the specified key-value pair into the symbol table,overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key(and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("argument key to put() is null.");
        if (value == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                // hit target,overwriting value
                x.val = value;
                return;
            }
        }
        // no hit target,create new node into linked list head
        first = new Node(key, value, first);
        size++;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(K key) {
        if (key == null)
            throw new IllegalArgumentException("argument key to delete() is null.");
        first = delete(first, key);
    }

    // delete key in linked list beginning at Node x
    private Node delete(Node x, K key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            size--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param key the key
     * @return {@code true} if this symbol table contains the specified key,{@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(K key) {
        if (key == null)
            throw new IllegalArgumentException("argument key to contains() is null.");
        return get(key) != null;
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable},
     * To iterable over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for {Key key : st.keys() }}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<K> keys() {
        Queue<K> queue = new Queue<K>();
        for (Node x = first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }
        return queue;
    }

    /**
     * Unit tests the {@code SequentialSearchST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
