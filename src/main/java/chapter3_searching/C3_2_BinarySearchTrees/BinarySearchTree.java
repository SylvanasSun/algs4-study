package chapter3_searching.C3_2_BinarySearchTrees;

import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The {@code BinarySearchTree} class represents an ordered symbol table of generic
 * key-value pairs.
 * This implementation uses an (unbalanced) binary search tree.
 * It requires that the key type implements the {@code Comparable} interface and
 * calls the {@code compareTo()} and method to compare two keys.
 * It does not call either {@code equals()} or {@code hashCode()}.
 * <p>
 * Created by SylvanasSun on 2017/3/24.
 *
 * @author SylvanasSun
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node root; // root node

    private class Node {
        private K key;
        private V value;
        private Node left, right; // left and right subtree
        private int size; // number of nodes in subtree

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchTree() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} is this symbol table is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table.
     */
    public int size() {
        return size(root);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}(inclusive)
     * and {@code hi}(inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi} is {@code null}
     */
    public int size(K lo, K hi) {
        if (lo == null)
            throw new IllegalArgumentException("called size(lo,hi) first argument is null.");
        if (hi == null)
            throw new IllegalArgumentException("called size(lo,hi) second argument is null.");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    // return number of key-value pairs in binary search tree rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table.
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("called get() with a null key.");
        return get(root, key);
    }


    private V get(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            // if key < x.key , search left subtree
            return get(x.left, key);
        } else if (cmp > 0) {
            // if key > x.key , search right subtree
            return get(x.right, key);
        } else {
            // hit target
            return x.value;
        }
    }

    /**
     * this symbol table contains the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key}
     * and {@code false} otherwise.
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    public boolean contains(K key) {
        if (key == null)
            throw new IllegalArgumentException("called contains() with a null key.");
        return get(key) != null;
    }

    /**
     * Inserts the specified key-value pair into  the symbol table.
     * overwriting the old value with the new value if the symbol table already contains
     * the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("called put() with a null key.");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
        assert check();
    }

    private Node put(Node x, K key, V value) {
        // if tree is empty, return a new node.
        if (x == null) return new Node(key, value, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            // hit target,overwriting old value with the new value
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right); // compute subtree node size
        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty.
     */
    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow.");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        // if the left link is empty,will link to the node of the right subtree.
        if (x.left == null) return x.right;

        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow.");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        // if the right link is empty,will link to the node of the left subtree.
        if (x.right == null) return x.left;

        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
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
            throw new IllegalArgumentException("called delete() with a null key.");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            // if x has two subtree
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K min() {
        if (isEmpty())
            throw new NoSuchElementException("called min() with empty symbol table.");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K max() {
        if (isEmpty())
            throw new NoSuchElementException("called max() with empty symbol table.");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equals to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equals to {@code key}.
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if there is no such key
     */
    public K floor(K key) {
        if (key == null)
            throw new IllegalArgumentException("called floor() with a null key.");
        if (isEmpty())
            throw new NoSuchElementException("called floor() with empty symbol table.");
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node floor(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public K ceiling(K key) {
        if (key == null)
            throw new IllegalArgumentException("called ceiling() with a null key.");
        if (isEmpty())
            throw new NoSuchElementException("called ceiling() with empty symbol table.");
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node ceiling(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param k the order statistic
     * @return the {@code k}th smallest key in the symbol table.
     * @throws IllegalArgumentException unless {@code k} is between 0 and <em>n</em> - 1
     */
    public K select(int k) {
        if (k < 0 || k >= size())
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        // if left subtree node size greater than k,in left subtree search
        if (t > k) return select(x.left, k);
            // otherwise,in right subtree search
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(K key) {
        if (key == null)
            throw new IllegalArgumentException("called rank() with a null key.");
        return rank(root, key);
    }


    private int rank(Node x, K key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(x.left, key);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(x.right, key);
        else
            return size(x.left);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (K key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null)
            throw new IllegalArgumentException("called keys(lo,hi) first argument is null.");
        if (hi == null)
            throw new IllegalArgumentException("called keys(lo,hi) second argument is null.");

        Queue<K> queue = new Queue<K>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<K> queue, K lo, K hi) {
        if (x == null) return;
        int cmp_lo = lo.compareTo(x.key);
        int cmp_hi = hi.compareTo(x.key);
        if (cmp_lo < 0)
            keys(x.left, queue, lo, hi);
        if (cmp_lo <= 0 && cmp_hi >= 0)
            queue.enqueue(x.key);
        if (cmp_hi > 0)
            keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the height of the binary search tree
     *
     * @return the height of the binary search tree
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<K> levelOrder() {
        Queue<K> keys = new Queue<K>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, K min, K max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (K key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    /**
     * Unit test.
     * example: put data1,put data2.... get data1,get data2....
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Please input data.");
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>();
        int count = 0;
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if ("end".equals(s))
                break;
            if ("PUT".equalsIgnoreCase(s.substring(0, 3))) {
                tree.put(s.substring(4), count++);
            } else if ("GET".equalsIgnoreCase(s.substring(0, 3))) {
                String key = s.substring(4);
                Integer value = tree.get(key);
                System.out.println("GET: " + key + "-" + value);
            } else if ("DELETE".equalsIgnoreCase(s.substring(0, 6))) {
                String key = s.substring(7);
                Integer value = tree.get(key);
                System.out.println("DELETE: " + key + "-" + value);
                tree.delete(key);
                count--;
            }
        }
        System.out.println("if foreach tree? y/n");
        String s = scanner.nextLine();
        if ("y".equalsIgnoreCase(s)) {
            for (String key : tree.keys()) {
                System.out.println(key + "-" + tree.get(key));
            }
        }
    }

}
