// O. Bittel
// 22.02.2017


package dictionary;



import java.util.Comparator;
import java.util.Iterator;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import java.lang.Math;

import static java.lang.Math.max;

/**
 * Implementation of the Dictionary interface as AVL tree.
 * <p>
 * The entries are ordered using their natural ordering on the keys,
 * or by a Comparator provided at set creation time, depending on which constructor is used.
 * <p>
 * An iterator for this dictionary is implemented by using the parent node reference.
 *
 * @param <K> Key.
 * @param <V> Value.
 */


public class BinaryTreeDictionary<K extends Comparable <? super K>, V> implements Dictionary<K, V> {


    static private class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }
    }

    private Node<K, V> root = null;
    private int size = 0;
    // ...
    private V oldValue;


    private Node<K,V> leftMostDescendant(Node<K,V> p) {
        assert p != null;
        while(p.left != null)
            p = p.left;
        return p;
    }

    private Node<K,V> ParentOfLeftMostAncestor(Node<K,V> p) {
        assert p != null;
        while(p.parent != null && p.parent.right == p)
            p = p.parent;
        return p.parent;
    }

    private int getHeight(Node<K,V> p) {
        if(p == null)
            return -1;
        else
            return p.height;
    }

    private int getBalance(Node<K,V> p) {
        if(p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }

    /////////////--------->Comparator cmp schreiben
    private V searchR(K key, Node<K,V> p) {
        if (p == null) {
            return null;
        }
        else if (key.compareTo(p.key) < 0) {
            return searchR(key, p.left);
        }
        else if (key.compareTo(p.key) > 0) {
            return searchR(key, p.right);
        }
        else {
            return p.value;
        }
    }

    @Override
    public V insert(K key, V value) {
        root = insertR(key,value,root);
        if(root != null)
            root.parent = null;
        return oldValue;
    }

    private Node<K,V> insertR(K key, V value, Node<K,V> p) {
        if(p == null){
            p = new Node(key, value);
            oldValue = null;
            ++size;
        } else if(key.compareTo(p.key) < 0){ //////mit cmp.compare ersetzen
            p.left = insertR(key,value,p.left);
            if(p.left != null)
                p.left.parent = p;
        } else if(key.compareTo(p.key) > 0) {
            p.right = insertR(key,value,p.right);
            if(p.right != null)
                p.right.parent = p;
        } else { //key bereits vorhanden
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;
    }

    private Node<K,V> balance(Node<K,V> p) {
        if(p == null)
            return null;
        p.height = max(getHeight(p.left), getHeight(p.right)) +1;
        if(getBalance(p) == 2){
            if(getBalance(p.left) <= 0)
                p = rotateRight(p);         //Fall A1
            else
                p = rotateLeftRight(p);     //Fall A2
        }
        else if(getBalance(p) == +2){
            if(getBalance(p.right) >= 0)
                p = rotateLeft(p);          //Fall B1
            else
                p = rotateRightLeft(p);     //Fall B2
        }
        return p;
    }

    private Node<K,V> rotateRight(Node<K,V> p) {
        assert p.left != null;
        Node<K,V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.height = max(getHeight(p.left), getHeight(p.right)) +1;
        q.height = max(getHeight(q.left), getHeight(q.right)) +1;
        return q;
    }

    private Node<K,V> rotateLeft(Node<K,V> p) {
        assert p.right != null;
        Node<K,V> q = p.right;
        p.right = q.left;
        q.left = p;
        p.height = max(getHeight(p.left), getHeight(p.right)) +1;
        q.height = max(getHeight(q.left), getHeight(q.right)) +1;
        return q;
    }

    private Node<K,V> rotateLeftRight(Node<K,V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }

    private Node<K,V> rotateRightLeft(Node<K,V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }

	/**
	 * Pretty prints the tree
	 */

	public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);
        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }
        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }
}
