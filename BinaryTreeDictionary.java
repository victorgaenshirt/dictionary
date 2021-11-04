// O. Bittel
// 22.02.2017

package dictionary;

import java.util.Iterator;
import java.lang.Comparable;

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
 class BinaryTreeDictionary<K extends Comparable <? super K>, V> implements Dictionary<K, V> {


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

    private Node<K,V> parentOfLeftMostAncestor(Node<K,V> p) {
        assert p != null;
        while(p.parent != null && p.parent.right == p)
            p = p.parent;
        return p.parent;
    }

    private int getHeight(Node<K,V> p) {
        if (p == null)
            return -1; //ist Definitionssache: Höhe eines leeren Baumes = -1
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
        root = removeR(key, root);
        if (root != null)
            root.parent = null;
        return oldValue;
    }

    private Node<K,V> removeR(K key, Node<K,V> p){
        if(p == null)
            oldValue = null;
        else if(key.compareTo(p.key) < 0)
            p.left = removeR(key,p.left);
        else if(key.compareTo(p.key) > 0)
            p.right = removeR(key, p.right);
        else if(p.left == null || p.right == null) {
            //p muss gelöscht werden, hat ein oder kein kind;
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
            --size;
        } else {
            //p muss gelöscht werden und hat zwei kinder:
            MinEntry<K,V> min = new MinEntry<K,V>();
            p.right = getRemMinR(p.right, min);
            --size;
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        return p;
    }

    private Node<K,V> getRemMinR(Node<K,V> p, MinEntry<K,V> min) {
        assert p!= null;
        if(p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else
            p.left = getRemMinR(p.left, min);
            return p;
    }

    private static class MinEntry<K,V> {
        private K key;
        private V value;
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BinaryDictionaryIterator();
    }

    private class BinaryDictionaryIterator implements Iterator<Entry<K, V>> {

        int counter = 0;
        Node<K, V> currentNode = root;
        K highestKeyUntilNow = leftMostDescendant(root).key;

        @Override
        public boolean hasNext() {
            if (counter < size)
                return true;
            return false;
        }

        /**
         * Durchläuft sämtliche Knoten in korrekter (also sortierter) Reihenfolge.
         * <p>
         * Anzahl der bisherigen Aufrufe wird je Iterator in counter gespeichert.
         * Größter bisher besuchter Key wird in highestKeyUntilNow gespeichert.
         * <p>
         * Vorgehensweise:
         * <p>
         * Prüfe die Kindknoten, ob deren key kleiner ist als highestKeyUntilNow.
         * Zuerst versuche den linken Knoten. Falls kleiner, gehe zu dessen leftMostDescendant.
         * Falls nicht kleiner (also schon besucht), prüfe auf einen rechten Kindknoten mit kleinerem key.
         * Falls kleiner, gehe zu diesem. Nun prüfe erneut auf kleinere Kindknoten.
         * ...
         *
         */
        @Override
        public Entry<K, V> next() {

            Node <K,V> iterationStartNode = currentNode;
            currentNode = getSmallestUnvisitedLeftNode(currentNode);

            while  (checkForSmallerRightChild(currentNode)) {
                currentNode = currentNode.right;
                currentNode = getSmallestUnvisitedLeftNode(currentNode);
            }

            /* wenn kein kleinerer Kindknoten besucht wurde, steige auf zum Elternknoten*/
            if (iterationStartNode == currentNode) {
                currentNode = currentNode.parent;
            }
            highestKeyUntilNow = currentNode.key;
            counter++;
            return new Entry<>(currentNode.key, currentNode.value);
        }


        private boolean checkForSmallerRightChild(Node<K, V> p) {
            return p.right != null && highestKeyUntilNow.compareTo(p.right.key) < 0 ;
        }


        private Node<K, V> getSmallestUnvisitedLeftNode(Node<K, V> p) {

            if (p.left != null) {
                while ( p.left != null && highestKeyUntilNow.compareTo(p.left.key) < 0 ) {
                    p = p.left;
                }
            }

            return p;
        }

    };


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

    /* TODO wo wird height inkrementiert?? */

    private Node<K,V> insertR(K key, V value, Node<K,V> p) {
        if(p == null){
            p = new Node(key, value);
            oldValue = null;
            ++size;
        } else if(key.compareTo(p.key) < 0){
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
