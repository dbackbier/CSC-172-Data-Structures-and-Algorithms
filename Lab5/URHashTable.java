// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Lab 5 - Hash Tables

import java.util.Iterator;
import java.lang.Iterable;

public class URHashTable<Key,Value> implements Iterable<Key> {
    private static final int INIT_CAPACITY = 7; // initial size of hash table, # of buckets (should be prime)
    private int dataSize; // number of key-value pairs in the table
    private int tableSize; // size of the hash table/ # of buckets
    private Key[] keys;
    Value[] vals;

    // Constructors
    public URHashTable() {
        this(INIT_CAPACITY);
    }
    public URHashTable(int cap) {
        tableSize = cap;
        dataSize = 0;
        keys = (Key[]) new Object[tableSize];
        vals = (Value[]) new Object[tableSize];
    }

    public void put(Key key, Value val) { // insert key-value pair
        if (dataSize >= tableSize / 2) {
            resize(2 * tableSize); // double table size if 50% full or more
        }
        int i;
        for (i = hash(key); keys[i] != null; i = i++ % tableSize) {
            if (keys[i].equals(key)) {
                vals[i] = val; // update value if key already exists
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        dataSize++;
    }

    public Value get(Key key) { // get value associated with key
        for (int i = hash(key); keys[i] != null; i = i++ % tableSize) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null; // key not found
    }

    public void delete(Key key) { // delete key (and associated value)
        if (!contains(key)) {
            return; // key not found
        }
        
        int i;
        for (i = hash(key); keys[i] != null; i = i++ % tableSize) {
            if (keys[i].equals(key)) {
                keys[i] = null;
                vals[i] = null;
                break;
            }
        }
        // Rehash
        i = (i + 1) % tableSize;
        while (keys[i] != null) {
            Key rehashKey = keys[i];
            Value rehashVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            tableSize--;
            put(rehashKey, rehashVal);
            i = (i + 1) % tableSize;
        }
        dataSize--;
        
    }

    public int size() {
        return dataSize;
    }

    public boolean isEmpty() {
        return dataSize == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Iterable<Key> keys() {
        URLinkedList<Key> list = new URLinkedList<Key>();
        for (int i = 0; i < tableSize; i++) {
            if (keys[i] != null) {
                list.addLast(keys[i]);
            }
        }
        return list;
    }

    public Iterator<Key> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<Key> {
        private int current = 0;
        private URLinkedList<Key> list = new URLinkedList<Key>();

        public HashIterator() {
            for (int i = 0; i < tableSize; i++) {
                if (keys[i] != null) {
                    list.addLast(keys[i]);
                }
            }
        }

        public boolean hasNext() {
            return !list.isEmpty();
        }

        public Key next() {
            return list.pollFirst();
        }
    }
    

    // Useful helpers
    private int hash(Key key) {
        return Math.abs(key.hashCode() % tableSize);
    }

    private void resize(int capacity) {
        URHashTable<Key, Value> temp = new URHashTable<Key, Value>(capacity);
        for (int i = 0; i < tableSize; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        tableSize = temp.tableSize;
        dataSize = temp.dataSize;
    }

    public void main(String[] args) {
        // TODO: UNIT TESTING
        URHashTable<Integer, Integer> hashTable = new URHashTable<>();

        // Test put(Key key, Value val) and get() methods
        hashTable.put(1, 1);
        hashTable.put(2, 2);
        hashTable.put(7, 0);
        assert hashTable.get(1) == 1;
        assert hashTable.get(2) == 2;
        assert hashTable.get(7) == 0;

        // Test updating value of existing key
        hashTable.put(2, 3);
        assert hashTable.get(2) == 3;
        System.out.println("put and get methods passed.\n");

        // Test delete(Key key) method
        hashTable.delete(2);
        assert hashTable.get(2) == null;
        System.out.println("delete method passed.\n");

        // Test size() and isEmpty() methods
        assert hashTable.size() == 2;
        assert hashTable.isEmpty() == false;
        System.out.println("size and isEmpty methods passed.\n");

        // Test contains(Key key) method
        assert hashTable.contains(7) == true;
        assert hashTable.contains(2) == false;
        System.out.println("contains method passed.\n");

        // Test resize() by adding more elements
        for (int i = 3; i <= 10; i++) {
            hashTable.put(i, i);
        }
        assert hashTable.get(6) == 6;
        assert hashTable.get(7) == 7;
        System.out.println("resize method passed.\n");

        // Test iterator() method
        int c = 0;
        for (Integer key : hashTable) {
            c++;
        }
        assert c == hashTable.size();
        System.out.println("iterator method passed.\n");
        System.out.println("All tests passed!");
    }
}