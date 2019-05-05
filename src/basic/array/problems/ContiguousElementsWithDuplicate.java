package basic.array.problems;

import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/check-array-contains-contiguous-integers-duplicates-allowed/

public class ContiguousElementsWithDuplicate {
	
	public static boolean isContiguous(int[] a) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		Set<Integer> set = new HashSet<Integer>();
		
		for (int e : a) {
			min = Math.min(min, e);
			max = Math.max(max, e);
			set.add(e);
		}
		
		return max-min+1 == set.size();
	}
	
	// Second Approach 
	public static boolean isContiguous_A2(int[] a) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for (int e : a) {
			min = Math.min(min, e);
			max = Math.max(max, e);
		}
		
		boolean[] visited = new boolean[max-min+1];
		for (int e : a)
			visited[e-min] = true;
		
		for (boolean b : visited)
			if (!b) return false;
		
		return true;
	}
	
	public static void main(String[] args) {
		int[] a = {5, 2, 3, 6, 4, 4, 6, 6};
		System.out.println(isContiguous(a)); // true
		System.out.println(isContiguous_A2(a)); // true
		
		a = new int[]{10, 14, 10, 12, 12, 13, 15};
		System.out.println(isContiguous(a)); // false
		System.out.println(isContiguous_A2(a)); // false
	}

}
