package advance_ds.lru_cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of Least Recently Used (LRU) Cache data structure
 * 
 * Third approach:
 * We use Java's built in LinkedHashMap to implement LRUCache
 * 
 * @author Narendra Jha
 *
 */
public class LRUCache3<K, V> extends LinkedHashMap<K, V> {

	private int capacity; // capacity of LRUCache
	
	public LRUCache3(int capacity) {
		super(16, 0.75f, true); // LinkedHashMap with accessOrder
		this.capacity = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldestEntry) {
		return size() > capacity;
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
