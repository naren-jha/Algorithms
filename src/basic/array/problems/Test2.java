package basic.array.problems;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test2 {
	public static void main(String[] args) {
		LRUCache<Integer, Integer> cache = new LRUCache<Integer, Integer>(4);
		cache.put(1, 10);cache.put(2, 20);cache.put(3, 30);cache.put(4, 40);
		printCache(cache);
		
		cache.get(2);
		cache.put(5, 50);cache.put(6, 60);
		printCache(cache);
	}
	
	public static void printCache(LRUCache<Integer, Integer> cache) {
		for (Map.Entry<Integer, Integer> entry : cache.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());			
		}
		System.out.println("------------");
	}
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	
	private int capacity;
	public LRUCache(int capacity) {
		super(16, 0.75f, true);
		this.capacity = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > capacity;
	}
}