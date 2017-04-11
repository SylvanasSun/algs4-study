package chapter3_searching.C3_4_HashTables;

import java.util.*;

/**
 * The {@code LinearProbingHashST} class represents a symbol table of generic
 * key-value paris.
 * This implementation uses a linear probing hash table.
 * It requires that the key type overrides the {@code equals()} and {@code hashCode()} methods.
 * <p>
 * Created by SylvanasSun on 2017/4/11.
 */
public class LinearProbingHashST<K, V> {

    private static final int INIT_CAPACITY = 4;

    private int n; // the number of key-value pairs in the symbol table
    private int m; // the number of size of linear probing table
    private K[] keys; // the keys
    private V[] vals; // the values

    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (K[]) new Object[m];
        vals = (V[]) new Object[m];
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
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with {@code key},{@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("called get() with key is null.");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key))
                return vals[i];
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
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m / 2) resize(2 * m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = value;
                return;
            }
        }
        keys[i] = key;
        vals[i] = value;
        n++;
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
    public V delete(K key) {
        if (key == null)
            throw new IllegalArgumentException("called delete() with key is null.");
        if (isEmpty())
            throw new NoSuchElementException("called delete() with empty symbol table.");

        if (!contains(key))
            return null;
        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        V oldValue = vals[i];
        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            K keyToRehash = keys[i];
            V valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m / 8) resize(m / 2);
        assert check();
        return oldValue;
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<K> keys() {
        List<K> list = new ArrayList<K>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null)
                list.add(keys[i]);
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
        LinearProbingHashST<K, V> temp = new LinearProbingHashST<K, V>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2 * n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

    /**
     * Unit test the {@code LinearProbingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        System.out.println("Please input order.");
        System.out.println("example: get xx / put xx / delete xx / select");
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if ("end".equals(s)) {
                break;
            } else if ("get".equals(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("get result: " + st.get(key));
            } else if ("put".equals(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("execute put " + key + "-" + count);
                st.put(key, count++);
            } else if ("delete".equals(s.substring(0, 6))) {
                String key = s.substring(7);
                System.out.println("execute delete " + key);
                st.delete(key);
            } else if ("select".equals(s)) {
                System.out.println("linear probing hash symbol table key-value pairs size: " + st.size());
                for (String key : st.keys()) {
                    System.out.println(key + "-" + st.get(key));
                }
            } else {
                System.out.println("invalid order....");
            }
        }
    }

}
