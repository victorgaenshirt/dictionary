// O. Bittel
// 22.02.2017
package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
public class BinaryTreeDictionary_christian<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

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

    @Override
    public V insert(K key, V value) {

        //falls Liste leer
        if (root == null) {
            root = new Node<>(key, value);
            size++;
            return null;
        }

        //Baum von oben nach unten durchlaufen und Einfüge-Position finden
        V retValue = traverseDownwards(root, key, value);


        return retValue;
    }

    private V traverseDownwards(Node<K, V> p, K key, V value) {

        /* falls aktueller Knoten der zu Ersetzende ist */

        if (p.key.equals(key)) {

            V oldValue = p.value;
            p.value = value;
            return value;

        /* sonst weiter nach unten durchlaufen */

        /* wenn links eingefügt werden muss */

        } else if (key.compareTo(p.key) < 0 ) {

            if (p.left == null) {

                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = p;
                newNode.height = p.height + 1;
                p.left = newNode;
                return value;

            } else if (key.compareTo(p.left.key) <= 0)
                return traverseDownwards(p.left, key, value);

            /* neuen Knoten als p.left einfügen und p.left als linkes Kind vom neuen Knoten eintragen */
            else {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = p;
                newNode.height = p.left.height;
                newNode.left = p.left;
                p.left = newNode;
                size++;
                incrementHeightRecursively(p.left.left);
            }

        }
        return null;
    }

    private void incrementHeightRecursively(Node<K,V> p) {
        if (p == null)
            throw new NoSuchElementException();

        p.height++;

        if (p.left != null)
            incrementHeightRecursively(p.left);

        if (p.right != null)
            incrementHeightRecursively(p.right);

        return;
    }

    @Override
    public V search(K key) {
        return searchRecursive(key, root);
    }

    private V searchRecursive(K key, Node<K,V> p) {
        //falls Knoten leer: Suche Abbrechen
        if (p == null)
            return null;
        else  if (key.compareTo(p.key) < 0)
            searchRecursive(key, p.left);
        else if (key.compareTo(p.key) > 0)
            searchRecursive(key, p.right);
        else
            return p.value;

        return null;
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
        return new Iterator<Entry<K, V>>() {

            int beenThere = 0;
            Node<K, V> currentNode = null;

            @Override
            public boolean hasNext() {
                if (beenThere < size) {
                    return true;
                }
                return false;
            }

            @Override
            public Entry<K, V> next() {

                if (root == null)
                    throw new NoSuchElementException();

                //beim ersten Aufruf von next: kleinsten Knoten im Baum suchen und zurückgeben
                if (beenThere == 0)
                    currentNode = findMostLeftNode(root);

                // jede weitere Iteration
                else {
                    //suche den nächst-größeren Knoten
                    currentNode = getNextBiggerKey(currentNode);
                    if (currentNode == null)
                        throw new NoSuchElementException();
                }

                beenThere++;
                return new Entry<>(currentNode.key, currentNode.value);
            }
        };
    }

    private Node<K,V> getNextBiggerKey(Node<K,V> currentNode) {

        /* falls rechtes Kind vorhanden hole Wert dort*/
        if (currentNode.right != null) {

            if (currentNode.right.left != null)
                return findMostLeftNode(currentNode.right);
            else
                return currentNode.right;

        /* sonst current.parent als Rückgabewert*/
        } else if (currentNode.parent != null)
            return currentNode.parent;

        /* falls keine groeßerer Knoten verfügbar gebe null zurück*/
        else
            return null;
    }

    /* finde den kleinsten Knoten ausgehend von Knoten p */
    private Node<K, V> findMostLeftNode(Node p) {
        if (p == null)
            return null;
        else if (p.left != null)
            return findMostLeftNode(p.left);
        else
            return p;
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