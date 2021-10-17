package dictionary;

import java.util.Iterator;

import static dictionary.hashHelpers.PrimRechner.findAllPrimesInRange;
import static dictionary.hashHelpers.PrimRechner.findNextLargerPrime;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private final int primToStartWith = 7;
    private int tableSize;

    public HashDictionary(int x) {
        //findAllPrimesInRange(100);
        //findNextLargerPrime(20);

        tableSize = primToStartWith;
        int[] hashTable = new int[primToStartWith];
    }

    int computeHash(K key) {
        int adr = key.hashCode();
        if (adr < 0 ) {
            adr = -adr;
            adr = adr % tableSize;
        }

        return adr;
    }

    @Override
    public V insert(K key, V value) {
        //checken: Wert schon vorhanden? (search(key))

        //falls Eintrag nicht vorhanden:
        //hashcode


        return null;
    }

    @Override
    public V search(K key) {
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