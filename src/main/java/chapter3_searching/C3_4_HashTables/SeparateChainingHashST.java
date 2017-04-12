package chapter3_searching.C3_4_HashTables;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The {@code SeparateChainingHashST} class represents a symbol table of generic
 * key-value pairs.
 * This implementation uses  a separate chaining hash table.
 * It requires that the key type overrides the {@code equals()} and {@code hashCode()} methods.
 * <p>
 * Created by SylvanasSun on 2017/4/12.
 */
public class SeparateChainingHashST<K, V> {

    private static final int INIT_CAPACITY = 4;

    private int n; // the number of key-value pairs in the symbol table
    private int m; // the number of size of separate chaining table
    private Node<K, V>[] table; // array of linked-list symbol tables

    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K,V> next;

        public Node() {

        }

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with {@code capacity} chains.
     *
     * @param capacity the initial number of chains
     */
    public SeparateChainingHashST(int capacity) {
        this.m = capacity;
        this.n = 0;
        table = (Node<K, V>[]) new Node[capacity];
        for (int i = 0; i < m; i++) {
            table[i] = (Node<K, V>) new Node();
        }
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty,{@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param key the key
     * @return the value associated with {@code key} in the symbol table;{@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("called get() with key is null.");
        int i = hash(key);
        Node x = table[i];
        while (x != null) {
            if (key.equals(x.key))
                return (V) x.value;
            x = x.next;
        }
        return null;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key},{@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(K key) {
        if (key == null)
            throw new IllegalArgumentException("called contains() with key is null.");
        return get(key) != null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("called put() with key is null.");

        if (value == null) {
            remove(key);
            return;
        }

        // double table size if average length of list >= 10
        if (n >= 10 * m)
            resize(2 * m);
        int i = hash(key);
        Node x = table[i];
        Node p = null;
        while (x != null) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
            p = x;
            x = x.next;
        }
        if (p == null) {
            table[i] = new Node(key, value, null);
            n++;
        } else {
            p.next = new Node(key, value, null);
            n++;
        }
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table) and return old value.
     *
     * @param key the key
     * @return the associated value with given specified key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if this symbol table is empty
     */
    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("called remove() with key is null.");
        if (isEmpty())
            throw new NoSuchElementException("called remove() with empty symbol table.");

        if (!contains(key))
            return null;
        int i = hash(key);
        Node x = table[i];
        Node p = null;
        V oldValue = null;
        while (x != null) {
            if (key.equals(x.key)) {
                oldValue = (V) x.value;
                if (p == null) {
                    table[i] = x.next;
                } else {
                    p.next = x.next;
                }
                n--;
                break;
            }
            p = x;
            x = x.next;
        }

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2 * m)
            resize(m / 2);
        return oldValue;
    }

    /**
     * Returns keys in symbol table as an Iterable.
     *
     * @return keys in symbol table as an Iterable
     */
    public Iterable<K> keys() {
        List<K> list = new ArrayList<K>();
        for (int i = 0; i < m; i++) {
            Node<K, V> x = table[i];
            while (x != null) {
                if (x.key != null)
                    list.add(x.key);
                x = x.next;
            }
        }
        return list;
    }

    /**
     * Hash function for key
     *
     * @param key the key
     * @return value between 0 and N-1
     */
    private int hash(K key) {
        return ((key.hashCode()) & 0x7fffffff) % m;
    }

    /**
     * Resize the hash table to the given capacity by re-hashing all of the keys.
     *
     * @param capacity the new capacity
     */
    private void resize(int capacity) {
        SeparateChainingHashST<K, V> temp = new SeparateChainingHashST<K, V>(capacity);
        for (int i = 0; i < m; i++) {
            Node<K, V> x = table[i];
            while (x != null) {
                K key = x.key;
                if (key != null)
                    temp.put(key, this.get(key));
                x = x.next;
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.table = temp.table;
    }

    /**
     * Unit Test {@code SeparateChainingHashST} data type.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        int count = 1;
        System.out.println("Please input order.");
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if ("end".equalsIgnoreCase(s)) {
                break;
            } else if ("get".equalsIgnoreCase(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("execute get  result: " + st.get(key));
            } else if ("put".equalsIgnoreCase(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("execute put " + key + "-" + count);
                st.put(key, count++);
            } else if ("remove".equalsIgnoreCase(s.substring(0, 6))) {
                String key = s.substring(7);
                Integer value = st.remove(key);
                System.out.println("execute remove " + key + "-" + value);
            } else if ("select".equalsIgnoreCase(s)) {
                System.out.println("separate chaining hash table key-value pairs size: " + st.size());
                for (String key : st.keys()) {
                    System.out.println(key + "-" + st.get(key));
                }
            } else {
                System.out.println("invalid order...");
            }
        }
    }

}
