package basic.hashing.openaddressing;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Hashing using open addressing - linear probing
 */

class HashTable<K, V> {
    
    // template for key, value pair entry
    private class Entry<K, V> {
        K key;
        V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
        
    }
    
    // array of buckets to store hash table
    private Entry<K, V>[] buckets;
    
    // current capacity of hash table
    private int capacity;
    
    // current size or number of elements in hash table
    private int size;
    
    // default initial capacity of hash table
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    // constructors
    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    public HashTable(int capacity) {
        this.capacity = capacity;
        buckets = new Entry[capacity];
        size = 0;
    }
    
    // adds a key, value pair to the hash table
    public void insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        int i;
        for (i = getBucketNumber(key); buckets[i] != null; 
                i = (i + 1) % capacity) {
            
            if (buckets[i].key.equals(key)) {
                // existing entry found, so update value and exit
                buckets[i].value = value;
                return;
            }
        }
        
        // if we came till this point, that means there was no existing 
        // entry for given key, and so we will add new entry in the slot
        // found. we will always find slot for new entries as we are 
        // ensuring extra capacity, whenever load factor crosses 0.75
        buckets[i] = new Entry<K, V>(key, value);
        size++;

        
        // PERFORMANCE OPTIMIZATION:
        
        // lets increase capacity of hash table when
        // load factor goes beyond a certain threshold.
        // lets take threshold value here as 0.75
        if ((1.0 * size) / capacity >= 0.75) {
            Entry<K, V>[] tmp = buckets;
            
            capacity = capacity * 2; // double the capacity
            buckets = new Entry[capacity];
            size = 0;
            
            for (Entry<K, V> entry : tmp) {
                if (entry != null) {
                    insert(entry.key, entry.value);
                }
            }
        }
    }
    
    // returns value for a given key
    // returns null if key is not present in the hash table
    public V search(K key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        int i;
        for (i = getBucketNumber(key); buckets[i] != null; 
                i = (i + 1) % capacity) {
            if (buckets[i].key.equals(key))
                return buckets[i].value;
        }
        
        return null; // key not found
    }
    
    // removes a key, value pair from the hash table
    public void delete(K key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        int i;
        for (i = getBucketNumber(key); buckets[i] != null; 
                i = (i + 1) % capacity) {
            if (buckets[i].key.equals(key)) {
                buckets[i] = null;
                
                // rehash all keys in same cluster
                i = (i + 1) % capacity;
                while (buckets[i] != null) {
                    K keyToRehash = buckets[i].key;
                    V valueToRehash = buckets[i].value;
                    
                    buckets[i] = null;
                    size--;
                    insert(keyToRehash, valueToRehash);
                    
                    i = (i + 1) % capacity;
                }
                size--;
                break;
            }
        }
    }
    
    // returns size or number of elements in the hash table
    public int size() {
        return size;
    }
    
    // checks if hash table is empty or not
    public boolean isEmpty() {
        return size == 0;
    }
    
    // checks if hash table contains a key or not
    public boolean contains(K key) {
        return search(key) != null;
    }
    
    // utility method to get the bucket/slot number
    private int getBucketNumber(K key) {
        int hashcode = key.hashCode();
        
        // & 0x7fffffff (bit wise and) is used to handle negative number
        // 7fffffff = most significant bit 0, rest all bits 1
        int index = (hashcode & 0x7fffffff) % capacity;
        return index;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (Entry<K, V> entry : buckets) {
            if (entry != null) {
                result.append(entry + ", ");
            }
        }
        if (result.indexOf(",") != -1)
            result.delete(result.lastIndexOf(","), result.length());
        
        result.append("]");
        return result.toString();
    }
    
}

public class HashTableTest {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        // Put some key values.
        for(int key = 1; key <= 30; key++) {
            hashTable.insert(key, key);
        }
        hashTable.insert("x", 1);
        hashTable.insert("y", 2);
        hashTable.insert("z", 3);
        hashTable.insert(-1, -1);
        System.out.println(hashTable);
        
        hashTable.delete(29);
        hashTable.delete("y");
        System.out.println(hashTable);
        
        System.out.println(hashTable.search("z"));
        
        // hash table with string keys and integer values
        HashTable<String, Integer> hashTable2 = new HashTable<String, Integer>();
        hashTable2.insert("Rahul", 21);
        hashTable2.insert("Sunil", 27);
        hashTable2.insert("Rita", 31);
        hashTable2.insert("Rahul", 22);
        System.out.println(hashTable2);
    }

}
