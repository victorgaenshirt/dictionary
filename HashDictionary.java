/*
static int hash(String key) {
    int adr = 0;
    for(int i = 0; i < key.length(); i++)
        adr = 31*adr + key.charAt(i);

    if(adr < 0)
        adr = -adr;
    return adr % m;
}
*/

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