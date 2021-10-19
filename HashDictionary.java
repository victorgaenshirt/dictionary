package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static dictionary.hashHelpers.PrimRechner.findNextLargerPrime;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    //TODO Comparable noch implementieren!
    //TODO equals implementieren?

    private LinkedList<Dictionary.Entry<K, V>>[] map;
    private final int defaultPrim = 7;
    private final int maxListSize = 3;
    private int tableSize = 0;
    private int elementsInTable = 0;

    public HashDictionary(int sizeOfDataset) {

        /* Berechung der Startgroesse der Hashmap */

        if (sizeOfDataset > defaultPrim / 2) {
            tableSize = findNextLargerPrime(defaultPrim * 2);
        } else {
            tableSize = defaultPrim;
        }

        System.out.println("tablesize: " + tableSize);

        /* Anlegen einer neuen leeren Hashmap */

        int[] hashTable = new int[tableSize];
        map = new LinkedList[tableSize];
        //map[1] = new LinkedList<Entry<K, V>>();
        //map[1].add(new Entry<K, V>("aaa", "bbb"));
        //System.out.println("map[1].size()" + map[1].size());

    }

    int computeHash(K key) {
        int adr = key.hashCode();
        if (adr < 0 ) {
            adr = -adr;
        }
        adr = adr % tableSize;
        return adr;
    }

    @Override
    public V insert(K key, V value) {

        int hashValue = computeHash(key);

        /*falls key schon in Liste, überschreibe zugehörigen Value */
        if (search(key) != null) {

            Iterator<Entry<K, V>> it = map[hashValue].iterator();
            while (it.hasNext()) {

                Entry<K, V> entry = it.next();

                if (entry.getKey() == key) {
                    V retValue = entry.getValue();
                    entry.setValue(value);
                    return retValue;
                }
            }
        } else {  /*falls key noch nicht in Liste: */

            /* falls keine Einträge in map[hashValue] neue LinkedList erzeugen */
            if (map[hashValue] == null) {
                map[hashValue] = new LinkedList<Dictionary.Entry<K, V>>();
                elementsInTable++;
            }

            //TODO prüfen, ob map[hashValue] size überschreitet
            //  falls ja, komplette map mit mindestens doppelter Größe neu anlegen


            /* Wert zu Liste hinzufügen */
            map[hashValue].add(new Entry(key, value));
        }

        return null;
    }


    @Override
    public V search(K key) {

        int hashValue = computeHash(key);

        if (map[hashValue] == null) {
            return null;
        }

        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        while (it.hasNext()) {

            Entry<K, V> entry = it.next();

            if (key == entry.getKey()) {
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

        int hashValue = computeHash(key);
        Iterator<Entry<K, V>> it = map[hashValue].iterator();

        int index = 0;

        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            if (entry.getKey() == key) {
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
            int tempSumOfElementsInLists = 0;

            /*  prüft für jede LinkedList, ob counter größer ist
                als die bisher gezählten Elemente. Falls ja,
                springe eine LinkedList weiter.
            */
            for (LinkedList<Entry<K, V>> list : map) {
                tempSumOfElementsInLists += list.size();
                if (counter > tempSumOfElementsInLists) {
                    continue;
                }
                else {
                    int diff = tempSumOfElementsInLists - counter;
                    int indexFromFront = list.size() - diff;

                    return list.get(indexFromFront);
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