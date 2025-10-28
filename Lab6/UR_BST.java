// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Lab 6 - Binary Search Trees

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Iterable;
import java.security.Key;

public class UR_BST<Key extends Comparable<Key>, Value> implements Iterable<Key>{
    private UR_Node root; // root of BST
    
    private class UR_Node {
        private Key key; // sorted by key
        private Value val; // associated data
        private UR_Node left, right; // left and right subtrees
        private int size; // number of nodes in subtree

        UR_Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public UR_BST() {
        root = null;
    }

    public UR_BST(Key key, Value val) {
        root = new UR_Node(key, val, 1);
    }

    public boolean isEmpty() {
        return (size(root) == 0);
    }

    public int size() {
        return size(root);
    }
    
    private int size(UR_Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    /**
    * @return {@code true} if this symbol table contains {@code key} and
    * {@code false} otherwise
    * @throws IllegalArgumentException if {@code key} is {@code null}
    */
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot equal null.");
        }
        return (get(key) != null);
    }

    /** @throws IllegalArgumentException if {@code key} is {@code null} */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot equal null.");
        }
        UR_Node curr = root;
        while (curr != null) {
            if (key.compareTo(curr.key) == 0) {
                return curr.val;
            } else if (key.compareTo(curr.key) > 0) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return null; // key not found
    }

    /** @throws IllegalArgumentException if {@code key} is {@code null} */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot equal null.");
        }
        root = put(root, key, val);
    }

    private UR_Node put(UR_Node node, Key key, Value val) {
        if (node == null) {
            return new UR_Node(key, val, 1);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = put(node.left, key, val);
        } else if (key.compareTo(node.key) > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node; // return the (unchanged) node pointer
    }

    /** @throws NoSuchElementException if the symbol table is empty */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty so there is no minimum.");
        }
        root = deleteMin(root);
    }
    private UR_Node deleteMin(UR_Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node; // return the (possibly updated) node pointer
    }

    /** @throws NoSuchElementException if the symbol table is empty */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty so there is no maximum.");
        }
        root = deleteMax(root);
    }

    private UR_Node deleteMax(UR_Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node; // return the (possibly updated) node pointer
    }

    /** @throws IllegalArgumentException if {@code key} is {@code null} */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot equal null.");
        }
        UR_Node parent = null;
        UR_Node curr = root;
        while (curr != null) {
            if (curr.key == key) {
                if (curr.left == null && curr.right == null) { // if leaf node
                    if (parent == null) { // is root
                        root = null;
                    } else if (parent.left == curr) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                    return;
                } else if (curr.right == null) { // only left child
                    if (parent == null) { // is root
                        root = curr.left;
                    } else if (parent.left == curr) {
                        parent.left = curr.left;
                    } else {
                        parent.right = curr.left;
                    }
                    return;
                } else if (curr.left == null) { // only right child
                    if (parent == null) { // is root
                        root = curr.right;
                    } else if (parent.left == curr) {
                        parent.left = curr.right;
                    } else {
                        parent.right = curr.right;
                    }
                    return;
                } else { // has two children
                    // find successor
                    UR_Node succ = curr.right;
                    while (succ.left != null) {
                        succ = succ.left;
                    }
                    // copy successor key to curr
                    curr.key = succ.key;
                    parent = curr;
                    // reassign curr and key so that loop continues with new key
                    curr = curr.right;
                    key = succ.key;
                }
            } else if (curr.key.compareTo(key) < 0) {
                parent = curr;
                curr = curr.right;
            } else {
                parent = curr;
                curr = curr.left;
            }
        }
    }

    public Iterable<Key> keys() {
        return levelOrder();
    }

    public int height() {
        return height(root);
    }

    private int height(UR_Node node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(height(node.left), height(node.right));
        }
    }

    public Iterator<Key> iterator() {
    // You can implement this properly later.
    // For now, just return an empty iterator to satisfy the compiler:
    return new Iterator<Key>() {
        Stack<UR_Node> stack = new Stack<>();
        {
            UR_Node curr = root;
            while (curr != null) {
                stack.push(curr);
                curr = curr.left
            }
        }
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            UR_Node node = stack.pop();
            Key res = node.key;
            UR_Node curr = node.right;
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            return res;
        }};
    }

    /**
    * Returns the keys in the BST in level order (for debugging).
    * @return the keys in the BST in level order traversal
    * usually requires a supplemental Queue
    * include this in your test case
    */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new LinkedList<>();
        Queue<UR_Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            UR_Node node = queue.poll();
            if (node == null) {
                continue;
            }
            keys.add(node.key);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return keys;
    }

    public void main(String[] args) {
        UR_BST<Integer, Integer> BST = new UR_BST<Integer,Integer>();

        // Test put and get
        BST.put(5, 5);
        BST.put(3, 3);
        BST.put(7, 7);
        assert BST.get(5) == 5;
        assert BST.get(3) == 3;
        assert BST.get(7) == 7;
        System.out.println("put and get methods passed.\n");

        // Test error handling
        try {
            BST.put(null, null);
        } catch (IllegalArgumentException e) {
            System.out.println("put method error handling passed.\n");
        }

        // Test isEmpty. size, and contains
        assert BST.size(BST.root) == 3;
        assert BST.isEmpty() == false;
        assert BST.contains(6) == false;
        assert BST.contains(7) == true;
        System.out.println("isEmpty, size, and contains methods passed.\n");

        // Create larger BST
        BST.put(1, 1);
        BST.put(2, 2);
        BST.put(4, 4);
        BST.put(6, 6);
        BST.put(8, 8);
        /*
         *        5
         *      /   \
         *    3     7
         *   / \   / \
         *  1  4  6  8
         */

        // Test deleteMin and deleteMax
        BST.deleteMin();
        assert BST.contains(1) == false;
        BST.deleteMax();
        assert BST.contains(8) == false;
        System.out.println("deleteMin and deleteMax methods passed.\n");

        // Test delete
        BST.delete(3);
        BST.delete(7);
        assert BST.contains(3) == false;
        assert BST.contains(7) == false;
        System.out.println("delete method passed.\n");

        // Test height
        assert BST.height(BST.root) == 2;
        System.out.println("height method passed.\n");

        // Build tree again
        BST.put(1, 1);
        BST.put(3, 3);
        BST.put(7, 7);
        BST.put(8, 8);

        // Test levelOrder
        LinkedList<Integer> list = new LinkedList<>();
        for (Integer key : BST.levelOrder()) {
            list.add(key);
        }
        assert list.size() == BST.size(BST.root);
        System.out.println("levelOrder method passed.\n");

        System.out.println("All tests passed!");
    }
}