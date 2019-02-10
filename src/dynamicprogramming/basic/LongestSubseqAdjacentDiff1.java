package dynamicprogramming.basic;

import java.util.HashMap;
import java.util.Map;

//https://www.geeksforgeeks.org/longest-subsequence-such-that-difference-between-adjacents-is-one/

public class LongestSubseqAdjacentDiff1 {
	
	/*
	 * GeeksForGeeks has a O(n^2) dynamic programming solution 
	 * based on the same idea which we used in Longest Increasing
	 * Subsequence problem. This problem however can be solved in 
	 * O(n) time using a Hashtable, see the solution below.
	 */

	public int countSeq(int[] a) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int prev, next;
		int longestSubsequence = 0;
		for (int key : a) {
			prev = map.containsKey(key-1) ? map.get(key-1) : 0;
			next = map.containsKey(key+1) ? map.get(key+1) : 0;
			int value = Math.max(prev, next) + 1;
			map.put(key, value);
			if (longestSubsequence < value)
				longestSubsequence = value;
		}
		return longestSubsequence;
	}
	
	public static void main(String[] args) {
		int[] a = {1, 2, 3, 4, 5, 3, 2};
		LongestSubseqAdjacentDiff1 obj = new LongestSubseqAdjacentDiff1();
		System.out.println(obj.countSeq(a)); // 6
	}
}
