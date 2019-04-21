package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/minimum-cost-sort-strings-using-reversal-operations-different-costs/
// http://codeforces.com/problemset/problem/706/C

public class MinimumCostToSortStringsUsingReversal {

	// T(n): O(n), S(n): O(n)
	public static int minCost(String[] arr, int[] cost) {
		int n = arr.length;
		if (n == 0)
			return 0;
		
		// dp[i][j] stores the minimum cost to make first i strings sorted
	    // j = 0 means i'th string is not reversed, and 
		// j = 1 means i'th string is reversed
		int[][] dp = new int[n][2];
		
		// initialize dp[][] for first string
		dp[0][0] = 0; // cost without reversing first string
		dp[0][1] = cost[0]; // cost by reversing first string
		
		String[] arrRev = new String[n];
		for (int i = 0; i < n; i++)
			arrRev[i] = new StringBuilder(arr[i]).reverse().toString();
		
		// fill entries for rest of the array elements
		for (int i = 1; i < n; i++) {
			String prevStr = arr[i-1];
			String preStrRev = arrRev[i-1];
			
			// calculate dp[i][0]: cost without reversing ith string
			dp[i][0] = Integer.MAX_VALUE;
			String currStr = arr[i];
			if (currStr.compareTo(prevStr) >= 0)
				dp[i][0] = Math.min(dp[i][0], dp[i-1][0] + 0);
			if (currStr.compareTo(preStrRev) >= 0)
				dp[i][0] = Math.min(dp[i][0], dp[i-1][1] + 0);
			
			// calculate dp[i][1]: cost by reversing ith string
			dp[i][1] = Integer.MAX_VALUE;
			String currStrRev = arrRev[i];
			if (currStrRev.compareTo(prevStr) >= 0)
				dp[i][1] = Math.min(dp[i][1], dp[i-1][0] + cost[i]);
			if (currStrRev.compareTo(preStrRev) >= 0)
				dp[i][1] = Math.min(dp[i][1], dp[i-1][1] + cost[i]);
		}
		
		int res = Math.min(dp[n-1][0], dp[n-1][1]);
		
		return (res != Integer.MAX_VALUE) ? res : -1;
	}
	
	public static void main(String[] args) {
		String[] arr = {"aa", "ba", "ac"};
		int[] cost = {1, 3, 1};
		System.out.println(minCost(arr, cost)); // 1
		
		 arr = new String[]{"bb", "aa"};
		 cost = new int[]{1, 3};
		 System.out.println(minCost(arr, cost)); // -1
	}
}
