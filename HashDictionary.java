package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import static dictionary.hashHelpers.PrimRechner.findNextLargerPrime;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private LinkedList<Dictionary.Entry<V,K>>[] map;
    private final int primToStartWith = 7;
    private int tableSize;

    public HashDictionary(int sizeOfDataset) {

        /* Berechung der Startgroesse der Hashmap */

        if (sizeOfDataset > primToStartWith / 2) {
            tableSize = findNextLargerPrime(primToStartWith * 2);
        } else {
            tableSize = primToStartWith;
        }

        System.out.println("tablesize: " + tableSize);

        /* Anlegen einer neuen leeren Hashmap */

        tableSize = primToStartWith;
        int[] hashTable = new int[primToStartWith];
        map= new LinkedList[primToStartWith];
        //map[1] = new LinkedList<Entry<V, K>>();
        //map[1].add(new Entry<V, K>("aaa", "bbb"));
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

        //checken: Wert schon vorhanden? (search(key))
        V searchedValue = (search(key));

        int hashValue = computeHash(key);

        /*falls Eintrag nicht vorhanden: */

        if (searchedValue == null) {

            /* falls keine Eintraege in map[hashValue] neue LinkedList erzeugen */

            if (map[hashValue] == null) {
                map[hashValue] = new LinkedList<Dictionary.Entry<V, K>>();
            }

            /* Wert zu Liste hinzufügen */
            map[hashValue].add(new Entry(key, value));
        }

        return null;
    }

    @Override
    public V search(K key) {
        System.out.println("in search-Methode....");

        /* Hashcode für key generieren */
        int hashValue = computeHash(key);
        System.out.println(" hashValue: " + hashValue);

        String notFound = "Key nicht in HashMap gespeichert";
        if (map[hashValue] == null) {
            System.out.println(notFound);
            return null;
        }

        int mapSize = map[hashValue].size();

        if (mapSize != 0) {
            for (int i=0; i < mapSize; i++) {
                K keyAtIndex = map[hashValue].get(i).getValue();
                System.out.println("keyAtIndex: " + keyAtIndex);

            }
        }

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
        return null;
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