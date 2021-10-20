package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static dictionary.hashHelpers.PrimRechner.findNextLargerPrime;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private LinkedList<Dictionary.Entry<K, V>>[] map;
    private final int loadFactor = 2;
    private int tableSize = 0;
    private int elementsInTable = 0;
    private int hashValue = 0;

    public HashDictionary(int sizeOfDataset) {
        tableSize = findNextLargerPrime(sizeOfDataset);
        map = new LinkedList[tableSize];
    }

    int computeHash(K key, int size) {
        int adr = key.hashCode();
        if (adr < 0 ) {
            adr = -adr;
        }
        adr = adr % size;
        return adr;
    }

    @Override
    public V insert(K key, V value) {

        hashValue = computeHash(key, tableSize);

        /* falls key schon in Liste */
        if (search(key) != null) {
            return overwriteValueForSameKey(key, value, hashValue);
        }

        /* falls key noch nicht in Liste: */

        /*falls groessere Hashmap angelegt werden muss*/
        if (elementsInTable >= loadFactor * tableSize) {
                copyMapToBiggerMap();
        }

        /* falls keine Einträge in map[hashValue] neue LinkedList erzeugen */
        if (map[hashValue] == null) {
            map[hashValue] = new LinkedList<Dictionary.Entry<K, V>>();
        }

        /* eigentlichen insert durchführen */
        map[hashValue].add(new Entry(key, value));

        elementsInTable++;
        return null;
    }

    private V overwriteValueForSameKey(K key, V value, int hashValue) {
        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        while (it.hasNext()) {
            Entry<K, V> entry = it.next();

            if (entry.getKey().equals(key)) {
                V retValue = entry.getValue();
                entry.setValue(value);
                return retValue;
            }
        }
        return null;
    }

    private void copyMapToBiggerMap() {
        tableSize = findNextLargerPrime(tableSize*2);
        LinkedList<Entry<K,V>>[] newMap = new LinkedList[tableSize];
        Iterator<Entry<K,V >> it = this.iterator();

        while (it.hasNext()) {

            Entry<K, V> entry = it.next();
            int newHashAdr = computeHash(entry.getKey(), tableSize);
            if (newMap[newHashAdr] == null) {
                newMap[newHashAdr] = new LinkedList<Entry<K, V>>();
            }
            newMap[newHashAdr].add(entry);
        }

        map = newMap;

    }


    @Override
    public V search(K key) {

        int hashValue = computeHash(key, tableSize);

        if (map[hashValue] == null) {
            return null;
        }

        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        while (it.hasNext()) {

            Entry<K, V> entry = it.next();

            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    @Override
    public V remove(K key) {

        /*falls Wert nicht in map existiert breche ab*/
        if (search(key) == null) {
            return null;
        }

        int hashValue = computeHash(key, tableSize);
        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        int index = 0;

        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
//            if (entry.getKey() == key) {
            if (entry.getKey().equals(key)) {
                V retValue = entry.getValue();
                map[hashValue].remove(index);
                elementsInTable--;
                return retValue;
            }
            index++;
        }

        return null;
    }


    @Override
    public int size() {
        return tableSize;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Entry<K, V>> {

/*        private int counter = 0;
        private int sumOfElementsInPreviousLists = 0;*/

        private int nextCounter = 0;
        private int nrOfMapArray = 0;
        private int indexProListe = 0;

        @Override
        public boolean hasNext() {
            if (nextCounter < elementsInTable) {
                return true;
            }
            return false;
        }


        @Override
        public Entry<K, V> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            while(true){
                if(map[nrOfMapArray] != null && indexProListe < map[nrOfMapArray].size()){
                    Entry<K, V> ret = map[nrOfMapArray].get(indexProListe);
                    ++indexProListe;
                    ++nextCounter;
                    return ret;

                }else{
                    indexProListe = 0;
                    ++nrOfMapArray;
                }
            }
        }
    }


}

