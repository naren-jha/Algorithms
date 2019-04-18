package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/ways-transforming-one-string-removing-0-characters/

public class WaysOfTransformingOneStringToAnother {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int countWays(String a, String b) {
			return countWays(a, b, a.length(), b.length());
		}
		
		public int countWays(String a, String b, int i, int j) {
			// Base cases:
			// 1. When second string is empty, first string can be converted 
			// to second string in exactly one way by removing nothing from 
			// first string, and that is true even when first string is also empty
			if (j == 0)
				return 1;
			
			// 2. When first string is empty, but second string is not empty
			// then first string cannot be converted to second string
			if (i == 0)
				return 0;
			
			// If characters are equal, then we can choose to remove or not remove that 
			// character from first string.
			if (a.charAt(i-1) == b.charAt(j-1))
				return countWays(a, b, i-1, j) + countWays(a, b, i-1, j-1);
			
			// If characters are not equal, then we have to remove it from first string
			else
				return countWays(a, b, i-1, j);
		}
	}
	
	class DPSolution {
		// TC: O(l1*l2), SC: O(l1*l2)
		public int countWays(String a, String b) {
			int l1 = a.length(), l2 = b.length();
			int[][] dp = new int[l1 + 1][l2 + 1];
			
			// 1.
			for (int i = 0; i <= l1; i++)
				dp[i][0] = 1;
			// 2.
			for (int j = 1; j <= l2; j++)
				dp[0][j] = 0;
			
			for (int i = 1; i <= l1; i++) {
				for (int j = 1; j <= l2; j++) {
					if (a.charAt(i-1) == b.charAt(j-1))
						dp[i][j] = dp[i-1][j] + dp[i-1][j-1];
					else
						dp[i][j] = dp[i-1][j];
				}
			}
			
			return dp[l1][l2];
		}
	}
	
	
	public static void main(String[] args) {
		String a = "abcccdf", b = "abccdf";
		WaysOfTransformingOneStringToAnother o = new WaysOfTransformingOneStringToAnother();
		System.out.println(o.new SimpleRecursiveSolution().countWays(a, b)); // 3
		System.out.println(o.new DPSolution().countWays(a, b)); // 3
	}
	
}
