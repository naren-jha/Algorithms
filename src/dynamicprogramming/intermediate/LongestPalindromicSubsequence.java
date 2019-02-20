package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-palindromic-subsequence-dp-12/

public class LongestPalindromicSubsequence {

	/*
	 * Let X[0..n-1] be the input sequence of length n and L(0, n-1) be 
	 * the length of the longest palindromic subsequence of X[0..n-1]
	 * 
	 * If first and last characters of X are same, then L(0, n-1) = L(1, n-2) + 2
	 * else L(0, n-1) = max(L(1, n-1), L(0, n-2))
	 */
	
	class SimpleRecursiveSolution {
		// T(n): Exp
		public int lps(String s) {
			return lps(s.toCharArray(), 0, s.length()-1);
		}
		
		private int lps(char[] s, int from, int to) {
			if (from == to)
				return 1;
			if (from > to)
				return 0;
			
			if (s[from] == s[to])
				return lps(s, from+1, to-1) + 2;
			return Math.max(lps(s, from, to-1), lps(s, from+1, to));
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^2), S(n): O(n^2)
		public int lps(String s) {
			int n = s.length();
			char[] c = s.toCharArray();
			
			// create a 2D array to store intermediate results
			// where res[i][j] indicates LPS for s[i, j]
			int[][] res = new int[n][n];
			
			for (int i = n-1; i >= 0; i--) {
				// starting from j = i will also work fine, as lower diagonal 
				// of the res[][] matrix is not utilized in the solution
				for (int j = 0; j < n; j++) {
					if (i == j)
						res[i][j] = 1;
					else if (i > j)
						res[i][j] = 0;
					else if (c[i] == c[j])
						res[i][j] = res[i+1][j-1] + 2;
					else
						res[i][j] = Math.max(res[i][j-1], res[i+1][j]);
				}
			}
			return res[0][n-1];
		}
		
		/*
		 * This problem is close to the Longest Common Subsequence (LCS) problem. 
		 * In fact, we can use LCS as a subroutine to solve this problem. 
		 * Following is the two step solution that uses LCS.
		 * 
		 * 1) Reverse the given sequence and store the reversed string
		 *    in another array say rev[0..n-1]
		 * 2) LCS of the given sequence and rev[] will be the longest palindromic subsequence
		 * This solution is also a O(n^2) solution.
		 * 
		 * We can also print the longest palindromic subsequence by printing LCS of 
		 * given sequence and rev[]
		 */
		
		/*
		 * We can space optimize above solution to O(n). See below solution
		 */
		// T(n): O(n^2), S(n): O(n)
		public int lpsSO(String s) {
			int n = s.length();
			char[] c = s.toCharArray();
			
			int[] res = new int[n];
			for (int i = n-1; i >= 0; i--) {
				int backUp = 0;
				for (int j = 0; j < n; j++) {
					if (i == j)
						res[j] = 1;
					else if (i > j)
						res[j] = 0;
					else if (c[i] == c[j]) {
						int tmp = res[j];
						res[j] = backUp + 2;
						backUp = tmp;
					}
					else {
						backUp = res[j];
						res[j] = Math.max(res[j-1], res[j]);
					}
				}
			}
			return res[n-1];
		}
	}
	
	public static void main(String[] args) {
		LongestPalindromicSubsequence o = new LongestPalindromicSubsequence();
		String s = "GEEKSFORGEEKS";
		System.out.println(o.new SimpleRecursiveSolution().lps(s)); // 5
		System.out.println(o.new DPSolution().lps(s)); // 5
		System.out.println(o.new DPSolution().lpsSO(s)); // 5
	}
}
