package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/largest-divisible-pairs-subset/

public class LargestDivisiblePairsSubset {
	
	// The solution is very similar to Longest Increasing Subsequence problem

	// T(n): O(n^2), S(n): O(n^2)
	public static int lds(int[] a) {
		Arrays.sort(a);
		int n = a.length;
		int[] dp = new int[n];
		dp[0] = 1;
		
		for (int i = 1; i < n; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (a[i] % a[j] == 0) {
					if (dp[j] + 1 > dp[i])
						dp[i] = dp[j] + 1;
				}
			}
		}
		return dp[n-1];
	}
	
	public static void main(String[] args) {
		int[] a = {18, 1, 3, 6, 13, 17};
		System.out.println(lds(a)); // 4
	}
}
