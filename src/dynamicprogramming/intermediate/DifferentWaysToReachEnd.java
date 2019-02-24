package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-number-ways-jump-reach-end/

public class DifferentWaysToReachEnd {
	
	// This problem is a variation MinJumpsToReachEnd problem
	
	// bottom-up tabulation
	// T(n): O(n^2), S(n): O(n)
	public void countWays(int[] a) {		
		int n = a.length;
		
		// create an array to store results, such that res[i] indicates
		// the number of ways to reach a[n-1] (i.e., end) from a[i]
		int[] res = new int[n]; // res[0] will hold the final result
		
		res[n-1] = 0;
		for (int i = n-2; i >= 0; i--) {
			// If a[i] is 0 then a[n-1] can't be reached from here
			if (a[i] == 0)
				res[i] = 0;			
			else {
				// If we can directly reach to the end point from i
				if (i + a[i] >= n-1)
					res[i] = 1;
				
				// Add the counts of all the elements that can reach to end 
				// and a[i] can reach to them
				for (int j = i+1; j <= i + a[i] && j < n-1; j++) {
					res[i] += res[j];
				}
			}
		}
		
		System.out.println(Arrays.toString(res));
		// [52, 52, 28, 16, 8, 0, 0, 4, 2, 1, 0]
	}
	
	/*
	 * We can time optimized above solution to O(n), by using some extra 
	 * space, though overall space complexity remains same
	 */
	// T(n): O(n), S(n): O(n)
	public void countWaysOptimized(int[] a) {		
		int n = a.length;
		
		// create an array to store results, such that res[i] indicates
		// the number of ways to reach a[n-1] (i.e., end) from a[i]
		int[] res = new int[n]; // res[0] will hold the final result
		
		// create a temporary array to store summed results
		int[] tmp = new int[n];
		
		res[n-1] = 0; tmp[n-1] = 0;
		for (int i = n-2; i >= 0; i--) {
			// If a[i] is 0 then a[n-1] can't be reached from here
			if (a[i] == 0)
				res[i] = 0;			
			else {
				// If we can directly reach to the end point from i
				if (i + a[i] >= n-1)
					res[i] = 1;
				
				// Add the counts of all the elements that can reach to end 
				// and a[i] can reach to them
				res[i] += tmp[i + 1];
				if (i + a[i] < n-1)
					res[i] -= tmp[i + a[i] + 1];
			}
			tmp[i] = tmp[i+1] + res[i];
		}
		
		System.out.println(Arrays.toString(res));
		// [52, 52, 28, 16, 8, 0, 0, 4, 2, 1, 0]
	}
	
	public static void main(String[] args) {
		DifferentWaysToReachEnd obj = new DifferentWaysToReachEnd();
		int[] a = {1, 3, 5, 8, 9, 1, 0, 7, 6, 8, 9};
		obj.countWays(a);
		obj.countWaysOptimized(a);
	}
}
