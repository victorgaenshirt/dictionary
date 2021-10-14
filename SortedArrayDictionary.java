package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {


    private int size;
    private Entry<K, V>[] data;


    public SortedArrayDictionary(){
        size = 0;
        data = new Entry[16];
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator(){
        return new CustomIterator();
    };

    private class CustomIterator implements Iterator<Entry<K,V>>{
        int count = 0;

        @Override
        public boolean hasNext() {return count+1 < size;}

        @Override
        public Entry<K,V> next(){
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            ++count;
            return data[count];
        }

    }

    public V search(K key){
        int li = 0;
        int re = size -1;

        while(re>=li) {
            int m = (li+re)/2;
            if(key.compareTo(data[m].getKey()) < 0)
                re = m-1;
            else if(key.compareTo(data[m].getKey()) > 0)
                li = m+1;
            else
                /*
                hier sollte stehen:
                return m
                also der index sollte zurückgegeben werden (siehe Folie 1-21).
                Andererseits ist der Rückgabetyp ja V laut interface.
                Vielleicht brauchen wir eine private Hilfsmethode?
                z.B. int searchIndex(K key){}
                 */
                return data[m].getValue(); //key gefunden
        }
        return null; //key nicht gefunden
    }

    public int searchIdx(K key) {
        int li = 0;
        int re = size -1;

        while(re>=li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].getKey()) < 0)
                re = m - 1;
            else if (key.compareTo(data[m].getKey()) > 0)
                li = m + 1;
            else
                return m;
        }
        return -1;
    }


    public V insert(K key, V value) {
        V i = search(key);
        /*kommentarVonChristian:
        hier erstmal den Spezialfall behandeln:
        == null -> abbruch
        */

        Iterator<Entry<K,V>> it = this.iterator();
        while (it.hasNext()) {

            Entry<K, V> tmpEntry = it.next();

            // wenn Key vorhanden ist, überschreiben
            if (tmpEntry.getKey() == key) {
                V oldValue = tmpEntry.getValue();
                tmpEntry.setValue(value);
                return oldValue;
            }

            //falls key nicht vorhanden
            if (data.length == size) {
                data = Arrays.copyOf(data, 2 * size);
            }
            // while schleife verschiebt die Einträge nach hinten
            int j = size - 1;
            while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
                data[j + 1] = data[j];
                j--;
            }
            //Neueintrag an der richtigen Stelle
            data[j + 1] = new Entry<K, V>(key, value);
            size++;
        }

           /*
                //alten Einträge nach rechts verschieben im Array
                int sizeOfTmpArray = size - counter;
                Entry<V, K> [] tmpArray = new Entry[sizeOfTmpArray];
                position = counter; //merken, wo der neue Wert eingefügt werden soll

                //tmpArray mit den zu verschiebenden Entries füllen
                int arrayCopyCounter = 0;
                while (it.hasNext()) {
                    it.next();
                    tmpArray[arrayCopyCounter] = data[counter];
                }

//                tmpArray = Arrays.copyOf(data[], sizeOfTmpArray);

                //unseren neuen Entry eintragen (counter hochzählen nicht vergessen)

                //ab counter+1 tmpArray in data kopieren




                size++;

            */
        return null;
    }


    public V remove(K key) {
        int i = searchIdx(key);
        if (i == -1)
            return null;

    // Datensatz loeschen und Lücke schließen
        V r = data[i].getValue();
        for (int j = i; j < size-1; j++)
            data[j] = data[j+1];
        data[--size] = null;
        return r;
    }

}