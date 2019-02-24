package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/coin-change-dp-7/
// https://www.youtube.com/watch?v=jaNZ83Q3QGc

public class CoinChangeProblem {

	private class SimpleRecursiveSolution {
		// T(n) = Exp
		public int coinChange(int[] coins, int n, int sum) {
			if (sum == 0)
				return 1;
			if (sum < 0)
				return 0;
			if (n == 0)
				return 0;
			
			return coinChange(coins, n, sum-coins[n-1]) + coinChange(coins, n-1, sum);
		}
	}
	
	private class DPSolution {
		// top-to-bottom memoization
		// T(n) = O(n*sum), S(n) = O(n*sum)
		public int coinChangeMemoized(int[] coins, int n, int sum, int[][] res) {
			if (sum == 0)
				return 1;
			if (sum < 0)
				return 0;
			if (n == 0)
				return 0;
			
			if (res[n][sum] != -1)
				return res[n][sum];
			
			res[n][sum] = coinChangeMemoized(coins, n, sum-coins[n-1], res) 
									+ coinChangeMemoized(coins, n-1, sum, res);
			return res[n][sum];
		}
		
		// bottom-up tabulation
		// T(n) = O(n*sum), S(n) = O(n*sum)
		public int coinChangeBottomUp(int[] coins, int sum) {
			int n = coins.length;
			int[][] res = new int[n][sum+1];
			
			for (int i = 0; i < n; i++) {
				Arrays.fill(res[i], 0); // redundant in java
				
				res[i][0] = 1; // whenever we reach amount 
				// zero while calculating results, that means we have
				// found a combination, so we should return 1 for that.
			}
			
			for (int i = 0; i < n; i++) {
				for (int j = 1; j <= sum; j++) {
					if (i > 0)
						res[i][j] = res[i-1][j];
					if (j >= coins[i])
						res[i][j] += res[i][j-coins[i]];
				}
			}
			return res[n-1][sum];
		}
		
		/*
		 * For bottom up tabulation method, in every iteration of inner for loop,
		 * since we only need the elements on/before the current element, we can
		 * solve this in O(sum) space. See solution below.
		 */
		// bottom-up tabulation, space optimized
		// T(n) = O(n*sum), S(n) = O(sum)
		// Advantage: better space complexity, cleaner solution.
		public int coinChange(int[] coins, int sum) {
			int[] res = new int[sum+1];
			Arrays.fill(res, 0); // redundant in java
			
			res[0] = 1; // whenever we reach zero while calculating results 
			// in innermost 'for' loop below, that simply means that we have 
			// found a combination that sums to current amount (j) 
			// so we should return 1 to count that combination.
			
			for (int i = 0; i < coins.length; i++) {
				for (int j = coins[i]; j <= sum; j++) {
					res[j] += res[j-coins[i]];
				}
			}
			return res[sum];
		}
	}
	
	public static void main(String[] args) {
		int[] coins = {1, 2, 5};
        int n = coins.length;
        int sum = 12;
        
        CoinChangeProblem obj = new CoinChangeProblem();
        System.out.println(obj.new SimpleRecursiveSolution().coinChange(coins, n, sum)); // 13
        
        int[][] res = new int[n+1][sum+1]; // (n+1) x (sum+1)
        for (int[] r : res)
        	Arrays.fill(r, -1);
        System.out.println(obj.new DPSolution().coinChangeMemoized(coins, n, sum, res)); // 13
        
        System.out.println(obj.new DPSolution().coinChangeBottomUp(coins, sum)); // 13
        
        System.out.println(obj.new DPSolution().coinChange(coins, sum)); // 13
	}

}
