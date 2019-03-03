package advance_ds.lru_cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of Least Recently Used (LRU) Cache data structure
 * 
 * WARNING: DO NOT USE THIS METHOD
 * ===============================
 * THIS APPROACH IS NOT EFFICIENT AS GET AND PUT OPERATIONS USING
 * THIS APPORACH WILL TAKE O(n) TIME DUE TO REMOVE() METHOD USED
 * ON LINKED LIST
 * 
 * 
 * Second approach:
 * We use a Hashtable (built in Java HashMap) and a Doubly Linked 
 * List (built in Java Linked List) to implement LRUCache. 
 * The LRUCache is a Hashtable of keys and doubly linked list nodes. 
 * The Hashtable makes the time of get() and put() operation O(1), 
 * and doubly linked list helps us in maintaining order of elements 
 * and evicting entry when capacity exceeds.
 * 
 * @author Narendra Jha
 *
 */

public class LRUCache2<K, V> {
	
	private int capacity; // capacity of LRUCache
	
	Map<K, V> map = new HashMap<K, V>();
	LinkedList<K> linkedList = new LinkedList<K>();
	
	public LRUCache2(int capacity) {
		this.capacity = capacity;
	}

	// Returns value for a given key in LRUCache
	public V get(K key) {
		if (map.containsKey(key)) {
			V val = map.get(key);
			
			// Mark the entry as most recently used entry
			linkedList.remove(key); // Remove node from linked list. // WILL TAKE O(n)
			linkedList.offerFirst(key); // Then add it to the Head of the linked list
			return val;
		}
		return null;
	}
	
	// Adds a new key-value pair into LRUCache
	public void put(K key, V value) {
		if (map.containsKey(key)) {
			map.put(key, value); // update value
			
			// Mark the entry as most recently used entry
			linkedList.remove(key); // Remove node from linked list. // WILL TAKE O(n)
			linkedList.offerFirst(key); // Then add it to the Head of the linked list
		}
		else {
			if (map.size() >= capacity) {
				// remove least recently used entry when capacity exceeds
				
				// remove from both map and linked list
				map.remove(linkedList.removeLast());
			}
			
			linkedList.offerFirst(key);
			map.put(key, value);
		}
	}
	
	public static void main(String[] args) {
		LRUCache2<String, Integer> cache = new LRUCache2<>(5);
		cache.put("abc", 1);
		cache.put("def", 2);
		cache.put("ghi", 3);
		cache.put("jkl", 4);
		cache.put("mno", 5);
		
		// abc is currently least recently used item
		System.out.println(cache.get("abc")); // 1
		// abc now became most recently used item, and
		// def became least recently used item
		
		cache.put("pqr", 6); // removes entry corresponding to def
		// and adds entry for pqr
		
		System.out.println(cache.get("def")); // null
		System.out.println(cache.get("pqr")); // 6
		
		cache.put("stu", 7); // removes entry corresponding to ghi
		// and adds entry for stu
		
		cache.put("vwx", 8); // removes entry corresponding to jkl
		// and adds entry for vwx
		
		System.out.println(cache.get("ghi")); // null
		System.out.println(cache.get("stu")); // 7
		
		System.out.println(cache.get("jkl")); // null
		System.out.println(cache.get("vwx")); // 8
	}
}
