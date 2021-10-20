package dictionary;

import java.util.Iterator;
import java.util.LinkedList;

import static dictionary.hashHelpers.PrimRechner.findNextLargerPrime;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private LinkedList<Dictionary.Entry<K, V>>[] map;
    private final int loadFactor = 2;
    private int tableSize = 0;
    private int elementsInTable = 0;

    public HashDictionary(int sizeOfDataset) {
        tableSize = findNextLargerPrime(sizeOfDataset);
        System.out.println("tablesize: " + tableSize);
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

        int hashValue = computeHash(key, tableSize);

        /* falls key schon in Liste */
        if (search(key) != null) {
            return overwriteValueForSameKey(key, value, hashValue);
        }

        /* falls key noch nicht in Liste: */

        /*falls groessere Hashmap angelegt werden muss*/
        if (elementsInTable > loadFactor * tableSize) {
                copyMapToBiggerMap();
        }

        /* falls keine Einträge in map[hashValue] neue LinkedList erzeugen */
        if (map[hashValue] == null) {
            map[hashValue] = new LinkedList<Dictionary.Entry<K, V>>();
        }

        /* eigentlichen insert durchführen */
        map[hashValue].add(new Entry(key, value));

        System.out.println("key: " + key);
        System.out.println("value: " + value);

        elementsInTable++;
        return null;
    }

    private V overwriteValueForSameKey(K key, V value, int hashValue) {
        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
//                if (entry.getKey() == key) {
            if (entry.getKey().equals(key)) {
                V retValue = entry.getValue();
                entry.setValue(value);
                return retValue;
            }
        }
        return null;
    }

    private void copyMapToBiggerMap() {
        int newTableSize = findNextLargerPrime(tableSize*2);
        LinkedList<Entry<K,V>>[] newMap = new LinkedList[newTableSize];
        Iterator<Entry<K,V >> it = this.iterator();

        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            int newHashAdr = computeHash(entry.getKey(), newTableSize);
            System.out.println("newHashAdr: " + newHashAdr);
            if (newMap[newHashAdr] == null) {
                newMap[newHashAdr] = new LinkedList<Entry<K, V>>();
            }
            newMap[newHashAdr].add(entry);
        }

        map = newMap;
        tableSize = newTableSize;
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
//            if (key == entry.getKey()) {
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

        private int counter = 0;

        @Override
        public boolean hasNext() {
            if (counter < elementsInTable) {
                return true;
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            counter++;
            System.out.println("counter: " + counter);
            int sumOfElementsInPreviousLists = 0;

            /*  prüft für jede LinkedList, ob counter größer ist
                als die bisher gezählten Elemente. Falls ja,
                springe eine LinkedList weiter.
            */
            for (LinkedList<Entry<K, V>> list : map) {

                /* überspringe leere Liste */
                if (list == null || list.size() == 0) {
                    continue;
                }

                if (counter <= (sumOfElementsInPreviousLists + list.size())) {
                    int idxForCurrentList = counter - sumOfElementsInPreviousLists;

                    if (idxForCurrentList == list.size()) {
                        sumOfElementsInPreviousLists += list.size();
                    }
                    return (list.get(idxForCurrentList-1));
                }
                else {
                    continue;
                }
            }
            return null;
        }
    }

}


/*
static int hash(String key) {
    int adr = 0;
    for(int i = 0; i < key.length(); i++)
        adr = 31*adr + key.charAt(i);

    if(adr < 0)
        adr = -adr;
    return adr % m;
}


V search(K key) {
    if(adr = searchAdr(key)!=1)
        return tab[adr].value;
    else
        return null;
}

int searchAdr(K key){
    j = 0;
    do{
        adr = (h(key)+s.(j,key)) % m;
        j++
    }while(tab[adr] != null && tab[adr].key != key);
    if(tab[adr] != null)
        return adr;
    else
        return -1;

}

 */