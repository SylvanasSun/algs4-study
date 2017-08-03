package chapter5_strings.C5_2_Tries;

/******************************************************************************
 *  Compilation:  javac TrieST.java
 *  Execution:    java TrieST < words.txt
 *  Dependencies: StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/52trie/shellsST.txt
 *
 *  A string symbol table for extended ASCII strings, implemented
 *  using a 256-way trie.
 *
 *  % java TrieST < shellsST.txt
 *  by 4
 *  sea 6
 *  sells 1
 *  she 0
 *  shells 3
 *  shore 7
 *  the 5
 *
 ******************************************************************************/

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The {@code TrieST} class represents an symbol table of key-value
 * pairs, with string keys and generic values.
 * It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 * <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 * It also provides character-based methods for finding the string
 * in the symbol table that is the <em>longest prefix</em> of a given prefix,
 * finding all strings in the symbol table that <em>start with</em> a given prefix,
 * and finding all strings in the symbol table that <em>match</em> a given pattern.
 * A symbol table implements the <em>associative array</em> abstraction:
 * when associating a value with a key that is already in the symbol table,
 * the convention is to replace the old value with the new value.
 * Unlike {@link java.util.Map}, this class uses the convention that
 * values cannot be {@code null}—setting the
 * value associated with a key to {@code null} is equivalent to deleting the key
 * from the symbol table.
 * <p>
 * This implementation uses a 256-way trie.
 * The <em>put</em>, <em>contains</em>, <em>delete</em>, and
 * <em>longest prefix</em> operations take time proportional to the length
 * of the key (in the worst case). Construction takes constant time.
 * The <em>size</em>, and <em>is-empty</em> operations take constant time.
 * Construction takes constant time.
 * <p>
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/52trie">Section 5.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class TrieST<Value> {

    // extended ASCII
    private static final int R = 256;

    // root of trie
    private Node root;

    // number of keys in trie
    private int N;

    // Trie Node
    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TrieST() {
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(String key) {
        validateKey(key, "argument to get() is null.");

        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        validateKey(key, "argument to contains() is null.");
        return get(key) != null;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Value value) {
        validateKey(key, "first argument to put() is null.");
        if (value == null) delete(key);
        else root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, Value value, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.value == null) N++;
            x.value = value;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, value, d + 1);
        return x;
    }

    /**
     * Removes the key from the set if the key is present.
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(String key) {
        validateKey(key, "argument to delete() is null.");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.value != null) N--;
            x.value = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.value != null) return x;
        for (int c = 0; c < R; c++) {
            if (x.next[c] != null)
                return x;
        }
        return null;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return N;
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     *
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     * as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new ArrayDeque<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where . symbol is treated as a wildcard character.
     *
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     * as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new ArrayDeque<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     *
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    public String longestPrefixOf(String query) {
        validateKey(query, "argument to longestPrefixOf() is null.");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of given length (-1 if no such match)
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.value != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d + 1, length);
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.value != null) results.add(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.value != null)
            results.add(prefix.toString());
        if (d == pattern.length())
            return;

        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(c);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void validateKey(String key, String message) {
        if (key == null)
            throw new IllegalArgumentException(message);
    }

}
