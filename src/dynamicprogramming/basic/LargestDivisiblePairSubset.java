package dynamicprogramming.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.geeksforgeeks.org/largest-divisible-pairs-subset/

public class LargestDivisiblePairSubset {

	public int getLargestDivisiblePairSubset(int[] a) {
		int n = a.length;
		int[] dp = new int[n];
		Arrays.sort(a);
		
		for (int i = n-1; i >= 0; i--) {
			int max = 0;
			for (int j = i+1; j < n; j++) {
				if (a[j] % a[i] == 0) {
					max = Math.max(max, dp[j]);
				}
			}
			dp[i] = max+1;
		}
		
		return Arrays.stream(dp).max().getAsInt();
	}
	
	// we can also print all the subsets. see solution below.
	public void printLargestDivisiblePairSubsets(int[] a) {
		int n = a.length;
		int[] dp = new int[n];
		Arrays.sort(a);
		
		List<List<Integer>> paths = new ArrayList<List<Integer>>(n);
		for (int i = 0; i < n; i++)
			paths.add(new ArrayList<Integer>());
		
		for (int i = n-1; i >= 0; i--) {
			int max = 0;
			for (int j = i+1; j < n; j++) {
				if (a[j] % a[i] == 0) {
					if (dp[j] >= max) {
						max = dp[j];
						paths.get(i).add(j);
					}
				}
			}
			dp[i] = max+1;
		}
		
		int largestSubsetSize = Arrays.stream(dp).max().getAsInt();
		for (int i = 0; i < n; i++) {
			if (dp[i]==largestSubsetSize) {
				printSubsets(i, a, paths, new ArrayList<Integer>());
			}
		}
	}
	
	private void printSubsets(int i, int[] a, List<List<Integer>> paths, List<Integer> subset) {
		subset.add(a[i]);
		
		// base condition
		if (paths.get(i).isEmpty())
			System.out.println(subset);
		
		for (Integer next : paths.get(i))
			printSubsets(next, a, paths, subset);
		
		subset.remove(subset.size()-1);
	}
	
	public static void main(String[] args) {
		int[] a = {3, 5, 2, 12, 4, 6, 8, 7};
		
		LargestDivisiblePairSubset obj = new LargestDivisiblePairSubset();
		System.out.println(obj.getLargestDivisiblePairSubset(a)); // 3
		
		obj.printLargestDivisiblePairSubsets(a);
		/*
		 * [2, 4, 8]
		 * [2, 4, 12]
		 * [2, 6, 12]
		 * [3, 6, 12]
		 * 
		 */
	}
}
