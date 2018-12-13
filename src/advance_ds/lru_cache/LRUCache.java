package advance_ds.lru_cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Least Recently Used (LRU) Cache data structure
 * 
 * We use a Hashtable (built in Java HashMap) and a Doubly Linked 
 * List (our own customized linked list) to implement LRUCache. 
 * The LRUCache is a Hashtable of keys and doubly linked list nodes. 
 * The Hashtable makes the time of get() and put() operation O(1), 
 * and doubly linked list helps us in maintaining order of elements 
 * and evicting entry when capacity exceeds.
 * 
 * @author Narendra Jha
 *
 */

public class LRUCache<K, V> {
	
	private class Node {
		K key;
		V value;
		Node next;
		Node prev;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private int capacity; // capacity of LRUCache
	
	private Node head; // head of linked list
	private Node end; // end of linked list
	
	Map<K, Node> map = new HashMap<K, Node>();
	
	public LRUCache(int capacity) {
		this.capacity = capacity;
	}

	// Returns value for a given key in LRUCache
	public V get(K key) {
		if (map.containsKey(key)) {
			Node node = map.get(key);
			
			// Mark the entry as most recently used entry
			remove(node); // Remove node from linked list
			addFirst(node); // Then add it to the Head of the linked list
			return node.value;
		}
		return null;
	}
	
	// Adds a new key-value pair into LRUCache
	public void put(K key, V value) {
		if (map.containsKey(key)) {
			Node node = map.get(key);
			node.value = value; // update value
			
			// Mark the entry as most recently used entry
			remove(node); // Remove node from linked list
			addFirst(node); // Then add it to the Head of the linked list
		}
		else {
			Node node = new Node(key, value);
			if (map.size() >= capacity) {
				// remove least recently used entry when capacity exceeds
				
				// remove from map first
				// as after removing from linked list 'end' will change
				map.remove(end.key);
				remove(end);
			}
			
			addFirst(node);
			map.put(key, node);
		}
	}
	
	// Adds given node at the beginning of the linked list
	private void addFirst(Node node) {
		node.next = head;
		node.prev = null; // not necessary
		
		if (head != null)
			head.prev = node;
		
		head = node;
		if (end == null)
			end = head;
	}

	// Removes given node from the linked list
	private void remove(Node node) {
		if (node.prev != null)
			node.prev.next = node.next;
		else
			head = node.next;
		
		if (node.next != null)
			node.next.prev = node.prev;
		else
			end = node.prev;
		
		node.next = null; // not necessary
		node.prev = null; // not necessary
	}
	
	public static void main(String[] args) {
		LRUCache<String, Integer> cache = new LRUCache<>(5);
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
