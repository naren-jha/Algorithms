package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/total-number-of-non-decreasing-numbers-with-n-digits/

public class NumberOfNonDecreasingNumbers {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int count(int n, int max) {
			if (n == 0 || max == 0)
				return 1;
			
			int count = 0;
			for (int x = max; x >= 0; x--)
				count += count(n-1, x);
			return count;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n), S(n): O(n)
		public int count(int n) {
			int[][] dp = new int[n+1][10];
			for (int i = 0; i <= n; i++)
				dp[i][0] = 1;
			for (int j = 1; j <= 9; j++)
				dp[0][j] = 1;
			
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= 9; j++)
					for (int x = j; x >= 0; x--)
						dp[i][j] += dp[i-1][x];
			return dp[n][9];
		}
	}
	
	public static void main(String[] args) {
		NumberOfNonDecreasingNumbers o = new NumberOfNonDecreasingNumbers();
		int n = 3;
		System.out.println(o.new SimpleRecursiveSolution().count(n, 9)); // 220
		System.out.println(o.new DPSolution().count(n)); // 220
	}
}
