package basic.hashing.chaining;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Hashing using chaining
 */
class HashTable<K, V> {
    
    // template for node in chain
    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
        
    }
    
    // array of buckets to store hash table
    private Node<K, V>[] buckets;
    
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
        buckets = new Node[capacity];
        size = 0;
    }
    
    // adds a key, value pair to the hash table
    public void insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        // find bucket/slot number for given key
        int bucketNumber = getBucketNumber(key);
        
        // check if key already exists
        Node<K, V> head = buckets[bucketNumber]; // head of chain
        while (head != null) {
            if (head.key.equals(key)) {
                // key already exists
                // so update value and exit
                head.value = value;
                return;
            }
            head = head.next;
        }
        
        // key does not exist in the hash table, so we will have
        // to add a new Node in linked list of slot 'bucketNumber'
        
        // create a new node with given key and value
        // and add it to the head of the linked list
        Node<K, V> node = new Node<K, V>(key, value);
        node.next = buckets[bucketNumber];
        buckets[bucketNumber] = node;
        size++;
        
        
        // PERFORMANCE OPTIMIZATION:
        
        // lets increase capacity of hash table when
        // load factor goes beyond a certain threshold.
        // lets take threshold value here as 0.75
        if ((1.0 * size) / capacity >= 0.75) {
            Node<K, V>[] tmp = buckets;
            
            capacity = capacity * 2; // double the capacity
            buckets = new Node[capacity];
            size = 0;
            
            for (Node<K, V> headNode : tmp) {
                while (headNode != null) {
                    insert(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }
    
    // returns value for a given key
    // returns null if key is not present in the hash table
    public V search(K key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        // find bucket/slot number for given key
        int bucketNumber = getBucketNumber(key);
        
        // search for the given key in chain
        Node<K, V> head = buckets[bucketNumber]; // head of chain
        while (head != null) {
            if (head.key.equals(key)) {
                // key found
                return head.value;
            }
            head = head.next;
        }
        
        return null;
    }
    
    // removes a key, value pair from the hash table
    public void delete(K key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        
        // find bucket/slot number for given key
        int bucketNumber = getBucketNumber(key);
        
        // search for the key and delete if found
        Node<K, V> head = buckets[bucketNumber]; // head of chain
        Node<K, V> prev = null;
        while (head != null) {
            
            if (head.key.equals(key)) {
                // key found, delete it and exit
                
                if (prev == null) {
                    // target node is the first node of chain
                    buckets[bucketNumber] = head.next;
                }
                else {
                    // target node is not the first node of chain
                    prev.next = head.next;
                }
                size--;
                return;
            }
            
            prev = head;
            head = head.next;
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
        for (Node<K, V> headNode : buckets) {
            while (headNode != null) {
                result.append(headNode + ", ");
                headNode = headNode.next;
            }
        }
        if (result.indexOf(",") != -1)
            result.delete(result.lastIndexOf(","), result.length());
        
        result.append("]");
        return result.toString();
    }
    
    // utility method to print hash table graphically
    public void printGraphically() {
        int bucket = 0;
        for (Node<K, V> headNode : buckets) {
            if (headNode == null)
                continue;
            
            System.out.print("bucket[" + bucket + "]");
            while (headNode != null) {
                System.out.print(" --> " + headNode);
                headNode = headNode.next;
            }
            System.out.println();
            bucket++;
        }
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
        /* Output:
         * [1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9, 10=10,
         * 11=11, 12=12, 13=13, 14=14, 15=15, 16=16, 17=17, 18=18,
         * 19=19, 20=20, 21=21, 22=22, 23=23, 24=24, 25=25, 26=26,
         * 27=27, 28=28, 29=29, 30=30, x=1, y=2, z=3, -1=-1]
        */
        
        hashTable.delete(29);
        hashTable.delete("y");
        System.out.println(hashTable);
        /* Output:
         * [1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9, 10=10,
         *  11=11, 12=12, 13=13, 14=14, 15=15, 16=16, 17=17, 18=18,
         *  19=19, 20=20, 21=21, 22=22, 23=23, 24=24, 25=25, 26=26,
         *  27=27, 28=28, 30=30, x=1, z=3, -1=-1]
         */
        
        System.out.println(hashTable.search("z")); // 3
        
        // hash table with string keys and integer values
        HashTable<String, Integer> hashTable2 = new HashTable<String, Integer>();
        hashTable2.insert("Rahul", 21);
        hashTable2.insert("Sunil", 27);
        hashTable2.insert("Rita", 31);
        hashTable2.insert("Rahul", 22);
        System.out.println(hashTable2); // [Rahul=22, Rita=31, Sunil=27]
        hashTable2.printGraphically();
        /* Output:
         * bucket[0] --> Rahul=22
         * bucket[1] --> Rita=31
         * bucket[2] --> Sunil=27
         */
    }

}
