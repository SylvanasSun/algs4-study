package chapter3_searching.C3_3_BalancedSearchTrees;

import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The {@code AvlTree} class represents an ordered symbol table of generic
 * key-value pairs.
 * This implements uses a AVL balanced tree.
 * Allow any node in the tree of the height of the two subtree biggest
 * difference for one.
 * It requires that the key type implements the {@code Comparable} interface
 * and calls the {@code compareTo()} and method to compare two keys.
 * It does not call either {@code equals()} or {@code hashCode()}.
 * {@code K} is key,{@code V} is value.
 * <p>
 * Created by SylvanasSun on 2017/4/3.
 *
 * @author SylvanasSun
 */
public class AvlTree<K extends Comparable<K>, V> {

    private Node root;

    private class Node {
        private K key;
        private V value;
        // left subtree,right subtree,parent node
        private Node left, right, parent;
        private int size; // the number of children node size
        private int balance; // the number of balance
        private int depth; // the number of tree depth

        Node(K key, V value, int size, Node parent) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.parent = parent;
            this.depth = 1;
            this.balance = 0;
        }

        Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.depth = 1;
            this.balance = 0;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public AvlTree() {

    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the sybol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    /**
     * This symbol table is empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of tree height.
     *
     * @return the number of tree height
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return 0;
        return x.depth;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if  the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("call get() with key is null.");
        return get(root, key);
    }

    private V get(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.value;
            }
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key}
     * and {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(K key) {
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

        put(root, key, value);
    }

    private void put(Node x, K key, V value) {
        Node parent = x;
        int cmp = 0;
        while (x != null) {
            parent = x;
            cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                x.value = value;
                return;
            }
        }
        // if not find key,create new node
        x = new Node(key, value, 1, parent);
        if (parent != null) {
            if (cmp < 0)
                parent.left = x;
            else
                parent.right = x;
        } else {
            root = x;
        }
        // fixup balance
        balance(x);
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is is in this symbol table) and return old value.
     *
     * @param key the key
     * @return the old value (if return {@code null} symbol table no contain the key)
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if the symbol table is empty
     */
    public V remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("called remove() with key is null.");
        if (isEmpty())
            throw new NoSuchElementException("called remove() with empty symbol table.");
        V oldValue = get(key);
        if (oldValue == null)
            return null;
        remove(root, key);
        return oldValue;
    }

    /**
     * Remove node x and then rebalance the tree.
     */
    private void remove(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else {
                // if x have two child node
                // use successor replace x
                if (x.left != null && x.right != null) {
                    Node s = successor(x);
                    x.key = s.key;
                    x.value = s.value;
                    x = s;
                }

                // fixup at replacement node, if it exists.
                Node replacement = (x.left != null ? x.left : x.right);

                removeSingle(x, replacement);
                x = null;
            }
        }
    }

    private void removeSingle(Node x, Node replacement) {
        if (replacement != null) {
            // link replacement to parent
            replacement.parent = x.parent;
            if (x.parent == null)
                root = replacement;
            else if (x == x.parent.left)
                x.parent.left = replacement;
            else
                x.parent.right = replacement;

            x.left = x.right = x.parent = null;
            // fixup balance
            balance(replacement);
        } else if (x.parent == null) {
            // if x is only node
            root = null;
        } else {
            // if no children
            if (x == x.parent.left)
                x.parent.left = null;
            else if (x == x.parent.right)
                x.parent.right = null;
            // fixup balance
            balance(x);
            x.parent = null;
        }
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     * and return old value.
     *
     * @return @return the old value (if return {@code null} symbol table no contain the key)
     * @throws NoSuchElementException if the symbol table is empty
     */
    public V removeMin() {
        if (isEmpty())
            throw new NoSuchElementException("called removeMin() with empty symbol table.");
        V oldValue = get(min());
        if (oldValue == null)
            return null;
        removeMin(root);
        return oldValue;
    }

    private void removeMin(Node x) {
        while (x != null) {
            if (x.left != null)
                x = x.left;
            else {
                Node replacement = x.right;

                removeSingle(x, replacement);
                x = null;
            }
        }
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     * and return old value.
     *
     * @return @return the old value (if return {@code null} symbol table no contain the key)
     * @throws NoSuchElementException if the symbol table is empty
     */
    public V removeMax() {
        if (isEmpty())
            throw new NoSuchElementException("called removeMax() with empty symbol table.");
        V oldValue = get(max());
        if (oldValue == null)
            return null;
        removeMax(root);
        return oldValue;
    }

    private void removeMax(Node x) {
        while (x != null) {
            if (x.right != null)
                x = x.right;
            else {
                Node replacement = x.left;

                removeSingle(x, replacement);
                x = null;
            }
        }
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K min() {
        if (isEmpty())
            throw new NoSuchElementException("called min() with empty symbol table.");
        return min(root).key;
    }

    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K max() {
        if (isEmpty())
            throw new NoSuchElementException("called max() with empty symbol table.");
        return max(root).key;
    }

    private Node max(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Returns the largest key in the symbol table less than or equals to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equals to {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if there is no such key
     */
    public K floor(K key) {
        if (key == null)
            throw new IllegalArgumentException("called floor() with key is null.");
        if (isEmpty())
            throw new NoSuchElementException("called floor() with empty symbol table.");

        Node x = floor(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node floor(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                if (x.right != null)
                    x = x.right;
                else
                    return x;
            } else if (cmp < 0) {
                if (x.left != null) {
                    x = x.left;
                } else {
                    Node parent = x.parent;
                    Node t = x;
                    while (parent != null && t == parent.left) {
                        t = parent;
                        parent = parent.parent;
                    }
                    return parent;
                }
            } else {
                return x;
            }
        }
        return null;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equals to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equals to {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if there is no such key
     */
    public K ceiling(K key) {
        if (key == null)
            throw new IllegalArgumentException("called ceiling() with key is null.");
        if (isEmpty())
            throw new NoSuchElementException("called ceiling() with empty symbol table.");

        Node x = ceiling(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node ceiling(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left != null)
                    x = x.left;
                else
                    return x;
            } else if (cmp > 0) {
                if (x.right != null) {
                    x = x.right;
                } else {
                    Node parent = x.parent;
                    Node t = x;
                    while (parent != null && t == parent.right) {
                        t = parent;
                        parent = parent.parent;
                    }
                    return parent;
                }
            } else {
                return x;
            }
        }
        return null;
    }

    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param k the order statistic
     * @return the {@code k}th smallest key in the symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and <em>n</em> - 1
     */
    public K select(int k) {
        if (k < 0 || k >= size())
            throw new IllegalArgumentException("called select() with invalid argument. ");

        Node x = select(root, k);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node select(Node x, int k) {
        while (x != null) {
            int t = size(x.left);
            if (t > k)
                x = x.left;
            else if (t < k) {
                x = x.right;
                k = k - t - 1;
            } else
                return x;
        }
        return null;
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
            throw new IllegalArgumentException("called rank() with key is null.");

        return rank(root, key);
    }

    private int rank(Node x, K key) {
        if (x == null)
            return 0;
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
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<K> keys() {
        if (isEmpty()) return new Queue<K>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the sybol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive) as an {@code Iterable}
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<K> queue = new Queue<K>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Queue<K> queue, K lo, K hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the successor of the node x or null if no such.
     * successor is the right subtree the leftmost node.
     */
    private Node successor(Node x) {
        if (x == null)
            return null;
        else if (x.right != null) {
            Node r = x.right;
            while (r.left != null)
                r = r.left;
            return r;
        } else {
            Node p = x.parent;
            Node t = x;
            // if x the right node is null
            while (p != null && t == p.right) {
                t = p;
                p = p.parent;
            }
            return p;
        }
    }


    /*************************************************************
     * Helper functions
     *************************************************************/
    // re balance tree
    private void balance(Node x) {
        while (x != null) {
            x.depth = calcDepth(x);
            x.balance = calcBalance(x);
            // if x left subtree high,rotateRight
            if (x.balance >= 2) {
                // if x.left.right high,rotateLeft
                if (x.left != null && x.left.balance == -1) {
                    x.left = rotateLeft(x.left);
                }
                x = rotateRight(x);
            }
            // if x right subtree high,rotateLeft
            if (x.balance <= -2) {
                // if x.right.left high,rotateRight
                if (x.right != null && x.right.balance == 1) {
                    x.right = rotateRight(x.right);
                }
                x = rotateLeft(x);
            }
            x.size = 1 + size(x.left) + size(x.right);
            x = x.parent;
        }
    }

    // calculate node x depth
    private int calcDepth(Node x) {
        int depth = 0;
        if (x.left != null)
            depth = x.left.depth;
        if (x.right != null && x.right.depth > depth)
            depth = x.right.depth;
        // parent + left or right depth
        depth++;
        return depth;
    }

    // calculate node x balance(left.depth - right.depth)
    private int calcBalance(Node x) {
        int leftDepth = 0;
        int rightDepth = 0;
        if (x.left != null)
            leftDepth = x.left.depth;
        if (x.right != null)
            rightDepth = x.right.depth;
        return leftDepth - rightDepth;
    }

    private Node rotateLeft(Node x) {
        Node t = x.right;
        x.right = t.left;
        t.left = x;
        if (x.parent != null) {
            t.parent = x.parent;
            if (x.parent.left == x)
                x.parent.left = t;
            else
                x.parent.right = t;
        } else {
            t.parent = null;
            root = t;
        }
        x.parent = t;
        // calculate depth and balance
        x.depth = calcDepth(x);
        x.balance = calcBalance(x);
        t.depth = calcDepth(t);
        t.balance = calcBalance(t);
        // calculate size
        t.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return t;
    }

    private Node rotateRight(Node x) {
        Node t = x.left;
        x.left = t.right;
        t.right = x;
        if (x.parent != null) {
            t.parent = x.parent;
            if (x.parent.left == x)
                x.parent.left = t;
            else
                x.parent.right = t;
        } else {
            t.parent = null;
            root = t;
        }
        x.parent = t;
        // calculate depth and balance
        x.depth = calcDepth(x);
        x.balance = calcBalance(x);
        t.depth = calcDepth(t);
        t.balance = calcBalance(t);
        // calculate size
        t.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        return t;
    }

    /**
     * Unit Test
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        AvlTree<String, Integer> tree = new AvlTree<String, Integer>();
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if ("end".equalsIgnoreCase(s))
                break;
            if ("put".equalsIgnoreCase(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("put " + key + "-" + count);
                tree.put(key, count++);
                System.out.println("tree depth: " + tree.height());
            } else if ("get".equalsIgnoreCase(s.substring(0, 3))) {
                String key = s.substring(4);
                System.out.println("get " + key + "-" + tree.get(key));
            } else if ("removeMin".equalsIgnoreCase(s)) {
                String k = tree.min();
                Integer v = tree.removeMin();
                System.out.println("remove min " + k + "-" + v);
                System.out.println("tree depth: " + tree.height());
            } else if ("removeMax".equalsIgnoreCase(s)) {
                String k = tree.max();
                Integer v = tree.removeMax();
                System.out.println("remove max " + k + "-" + v);
                System.out.println("tree depth: " + tree.height());
            } else if ("select".equalsIgnoreCase(s.substring(0, 6))) {
                String k = s.substring(7);
                System.out.println("select " + k + "result: " + tree.select(Integer.parseInt(k)));
            } else if ("rank".equalsIgnoreCase(s.substring(0, 4))) {
                String k = s.substring(5);
                System.out.println("rank " + k + "result: " + tree.rank(k));
            } else if ("remove".equalsIgnoreCase(s.substring(0, 6))) {
                String k = s.substring(7);
                Integer v = tree.remove(k);
                System.out.println("remove " + k + "-" + v);
                System.out.println("tree depth: " + tree.height());
            } else if ("floor".equalsIgnoreCase(s.substring(0, 5))) {
                String k = s.substring(6);
                System.out.println("floor " + k + " result: " + tree.floor(k));
            } else if ("ceiling".equalsIgnoreCase(s.substring(0, 7))) {
                String k = s.substring(8);
                System.out.println("ceiling " + k + "result: " + tree.ceiling(k));
            } else if ("findAll".equalsIgnoreCase(s)) {
                System.out.println("tree size: " + tree.size());
                System.out.println("tree depth: " + tree.height());
                for (String key : tree.keys()) {
                    System.out.println(key + "-" + tree.get(key));
                }
            } else {
                System.out.println("invalid order....");
            }
        }
    }

}
