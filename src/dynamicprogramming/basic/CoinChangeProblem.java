package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/coin-change-dp-7/
// https://www.youtube.com/watch?v=jaNZ83Q3QGc

public class CoinChangeProblem {

	private class SimpleRecursiveSolution {
		public int coinChange(int[] coins, int m, int n) {
			if (n == 0)
				return 1;
			if (n < 0)
				return 0;
			if (m == 0)
				return 0;
			
			return coinChange(coins, m, n-coins[m-1]) + coinChange(coins, m-1, n);
		}
	}
	
	private class DPSolution {
		
		/* 
		 * This will not work, because solution for a particular value 
		 * of 'n' will change depending on different values of 'm'. so we 
		 * cannot be dependent on just the value of n. we can solve it by 
		 * storing result in an nxm matrix, for all different combinations 
		 * of 'm' and 'n'. see the next solution with nxm matrix.
		 * 
		 * public int coinChangeMemoized(int[] coins, int m, int n, int[] combinations) {
			if (n == 0)
				return 1;
			if (n < 0)
				return 0;
			if (m == 0)
				return 0;
			
			if (combinations[n] != -1)
				return combinations[n];
			
			combinations[n] = coinChangeMemoized(coins, m, n-coins[m-1], combinations) 
								+ coinChangeMemoized(coins, m-1, n, combinations);
			return combinations[n];
		}*/
		
		// top-to-bottom memoization
		// T(n) = O(mn), as we are solving total m*n different problems.
		// S(n) = O(mn)
		public int coinChangeMemoized(int[] coins, int m, int n, int[][] combinations) {
			if (n == 0)
				return 1;
			if (n < 0)
				return 0;
			if (m == 0)
				return 0;
			
			if (combinations[n][m] != -1)
				return combinations[n][m];
			
			combinations[n][m] = coinChangeMemoized(coins, m, n-coins[m-1], combinations) 
									+ coinChangeMemoized(coins, m-1, n, combinations);
			return combinations[n][m];
		}
		
		// bottom-up tabulation
		// T(n) = O(mn)
		// S(n) = O(mn)
		public int coinChangeBottomUp(int[] coins, int n) {
			int m = coins.length;
			int[][] combinations = new int[m][n+1];
			
			for (int i = 0; i < m; i++) {
				Arrays.fill(combinations[i], 0); // redundant in java
				
				combinations[i][0] = 1; // whenever we reach amount  
				// zero while calculating results, that means we have
				// found a combination, so we should return 1 for that.
			}
			
			for (int i = 0; i < m; i++) {
				for (int j = 1; j <= n; j++) {
					if (i > 0)
						combinations[i][j] = combinations[i-1][j];
					if (j >= coins[i])
						combinations[i][j] += combinations[i][j-coins[i]];
				}
			}
			return combinations[m-1][n];
		}
		
		/*
		 * For bottom up tabulation method, in every iteration of inner for loop,
		 * since we only need the elements on/before the current element, we can
		 * solve this in O(n) space.
		 */
		// bottom-up tabulation, space optimized
		// T(n) = O(mn)
		// S(n) = O(n)
		// Advantage: better space complexity, cleaner solution.
		public int coinChange(int[] coins, int n) {
			int[] combinations = new int[n+1];
			Arrays.fill(combinations, 0); // redundant in java
			
			combinations[0] = 1; // whenever we reach zero while calculating results 
			// in innermost 'for' loop below, that simply means that we have 
			// found a combination that sums to current amount (j) 
			// so we should return 1 to count that combination.
			for (int i = 0; i < coins.length; i++) {
				for (int j = coins[i]; j <= n; j++) {
					combinations[j] += combinations[j-coins[i]];
				}
			}
			return combinations[n];
		}
	}
	
	public static void main(String[] args) {
		int[] coins = {1, 2, 5}; 
        int m = coins.length;
        int n = 12;
        
        CoinChangeProblem obj = new CoinChangeProblem();
        System.out.println(obj.new SimpleRecursiveSolution().coinChange(coins, m, n)); // 13
        
        int[][] combinations = new int[n+1][m+1]; // (n+1) x (m+1)
        for (int[] combination : combinations)
        	Arrays.fill(combination, -1);
        System.out.println(obj.new DPSolution().coinChangeMemoized(coins, m, n, combinations)); // 13
        
        System.out.println(obj.new DPSolution().coinChangeBottomUp(coins, n)); // 13
        
        System.out.println(obj.new DPSolution().coinChange(coins, n)); // 13
	}

}
