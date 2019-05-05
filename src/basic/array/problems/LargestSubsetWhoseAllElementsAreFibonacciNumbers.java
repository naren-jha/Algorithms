package basic.array.problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://www.geeksforgeeks.org/largest-subset-whose-all-elements-are-fibonacci-numbers/
// https://practice.geeksforgeeks.org/problems/largest-fibonacci-subsequence/0

public class LargestSubsetWhoseAllElementsAreFibonacciNumbers {

	public static void largestFibonacciSubsequence(int[] a) {
		int n = a.length;
		
		int max = 0;
		for (int i = 0; i < n; i++)
			max = Math.max(max, a[i]);
		
		// generate all fibonacci numbers till 'max' and store them in a hash
		Set<Integer> hash = new HashSet<Integer>();
		int x = 0, y = 1;
		hash.add(x); hash.add(y);
		while (y < max) {
			int z = x + y;
			x = y;
			y = z;
			hash.add(y);
		}
		
		List<Integer> resultList = new ArrayList<Integer>();
		for (int i = 0; i < n; i++)
			if (hash.contains(a[i]))
				resultList.add(a[i]);
		
		System.out.println(resultList);
	}
	
	public static void main(String[] args) {
		int[] a = {0, 2, 8, 5, 2, 1, 4, 13, 23};
		largestFibonacciSubsequence(a);
	}
}
