package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/find-minimum-number-of-coins-that-make-a-change/

public class MinCoinsToMakeChange {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int minCoin(int[] coins, int n, int sum) {
			if (sum == 0)
				return 0;
			if (sum < 0)
				// -1 so that 1 can be added in previous recursive call
				return Integer.MAX_VALUE - 1;
			if (n == 0)
				// -1 so that 1 can be added in previous recursive call
				return Integer.MAX_VALUE - 1;
			
			return Math.min(1 + minCoin(coins, n, sum-coins[n-1]), minCoin(coins, n-1, sum));
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n*sum), S(n): O(n*sum)
		public int minCoin(int[] coins, int sum) {
			int n = coins.length;
			int[][] res = new int[n+1][sum+1];
			
			for (int i = 0; i <= n; i++)
				res[i][0] = 0; // when sum == 0 // redundant in Java
			for (int j = 1; j <= sum; j++)
				res[0][j] = Integer.MAX_VALUE - 1; // when n == 0, but sum is non-zero
			
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= sum; j++) {
					res[i][j] = res[i-1][j];
					if (j >= coins[i-1])
						res[i][j] = Math.min(1 + res[i][j-coins[i-1]], res[i][j]);
				}
			}
			return res[n][sum];
		}
		
		/*
		 * We can space optimize above solution. See Solution Below.
		 * Beware that unlike original coin change problem, we cannot use a 1D array 
		 * here, because we need to keep track of the case:
		 * when n == 0, but sum is non-zero
		 */
		// T(n): O(n*sum), S(n): O(sum)
		public int minCoinSO(int[] coins, int sum) {
			int n = coins.length;
			int[][] res = new int [2][sum+1];
			
			res[0][0] = 0; res[1][0] = 0; // when sum == 0 // redundant in Java
			for (int j = 1; j <= sum; j++)
				res[0][j] = Integer.MAX_VALUE - 1; // when n == 0, but sum is non-zero
			
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= sum; j++) {
					res[i%2][j] = res[(i-1)%2][j];
					if (j >= coins[i-1])
						res[i%2][j] = Math.min(1 + res[i%2][j-coins[i-1]], res[i%2][j]);
				}
			}
			return res[n%2][sum];
		}
	}
	
	public static void main(String[] args) {
		int[] coins =  {9, 6, 5, 1};
		int n = coins.length;
        int sum = 11;
        
        MinCoinsToMakeChange o = new MinCoinsToMakeChange();
        System.out.println(o.new SimpleRecursiveSolution().minCoin(coins, n, sum)); // 2
        System.out.println(o.new DPSolution().minCoin(coins, sum)); // 2
        System.out.println(o.new DPSolution().minCoinSO(coins, sum)); // 2
	}
}
