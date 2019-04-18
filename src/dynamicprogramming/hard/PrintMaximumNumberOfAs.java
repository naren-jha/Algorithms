package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/how-to-print-maximum-number-of-a-using-given-four-keys/

public class PrintMaximumNumberOfAs {

	// Explanation: https://youtu.be/nyR8K63F2KY
	class SimpleRecursiveSolution {
		// T(n): Exp
		public int maxA(int n) {
			if (n <= 6)
				return n;
			
			int max = 0, multiplier = 2;
			for (int bp = n-3; bp >= 1; bp--) { // find optimal break point
				max = Math.max(max, multiplier * maxA(bp)); // multiplier = n - bp - 1
				multiplier++;
			}
			return max;
		}
	}
	
	class DPSolution {
		// T(n): O(n^2), S(n): O(n)
		public int maxA(int n) {
			int[] dp = new int[n+1];
			for (int i = 1; i <= 6; i++)
				dp[i] = i;
			
			for (int i = 7; i <= n; i++) {
				int multiplier = 2;
				for (int bp = i-3; bp >= 1; bp--) { // find optimal break point
					dp[i] = Math.max(dp[i], multiplier * dp[bp]); // multiplier = i - bp - 1
					multiplier++;
				}
			}
			return dp[n];
		}
	}
	
	public static void main(String[] args) {
		int n = 11;
		PrintMaximumNumberOfAs o = new PrintMaximumNumberOfAs();
		
		System.out.println(o.new SimpleRecursiveSolution().maxA(n)); // 27
		System.out.println(o.new DPSolution().maxA(n)); // 27
	}
}
