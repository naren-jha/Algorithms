package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/optimal-strategy-for-a-game-dp-31/

public class OptimalStrategyForGame {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int optimalStrategy(int[] a, int i, int j) {
			// Base case:
			if (i == j)
				return 0; // last element is selected by opponent, so user gets nothing
			
			// If we have even number of elements, then its users turn
			// otherwise its opponents turn
			int turn = (j - i + 1) % 2;
			if (turn == 0) { // users turn
				return Math.max(a[i] + optimalStrategy(a, i+1, j), 
								a[j] + optimalStrategy(a, i, j-1));
			}
			else { // opponents turn
				// When opponent selects maximum for himself, user gets minimum
				return Math.min(optimalStrategy(a, i+1, j), 
								optimalStrategy(a, i, j-1));
			}
		}
	}
	
	class DPSolution {
		// T(n): O(n^2), S(n): O(n^2)
		public int optimalStrategy(int[] a) {
			int n = a.length;
			int[][] dp = new int[n][n];
			
			for (int i = n-1; i >= 0; i--) {
				for (int j = i; j < n; j++) {
					if (i == j)
						dp[i][j] = 0;
					else if ((j - i + 1) % 2 == 0) // users turn
						dp[i][j] = Math.max(a[i] + dp[i+1][j], a[j] + dp[i][j-1]);
					else // opponents turn
						dp[i][j] = Math.min(dp[i+1][j], dp[i][j-1]);
				}
			}
			return dp[0][n-1];
		}
	}
	
	public static void main(String[] args) {
		OptimalStrategyForGame o = new OptimalStrategyForGame();
		int[] a = {8, 15, 3, 7};
		System.out.println(o.new SimpleRecursiveSolution().optimalStrategy(a, 0, a.length-1)); // 22
		System.out.println(o.new DPSolution().optimalStrategy(a)); // 22
	}
}
