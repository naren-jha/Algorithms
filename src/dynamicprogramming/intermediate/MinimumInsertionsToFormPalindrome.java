package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/minimum-insertions-to-form-a-palindrome-dp-28/

public class MinimumInsertionsToFormPalindrome {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int minInsertions(String s) {
			return minInsertions(s.toCharArray(), 0, s.length()-1);
		}
		
		private int minInsertions(char[] s, int from, int to) {
			// Base cases:
			if (from == to)
				return 0;
			if (from == to-1)
				return s[from] == s[to] ? 0 : 1;
			
			if (s[from] == s[to])
				return minInsertions(s, from+1, to-1);
			else
				return 1 + Math.min(minInsertions(s, from, to-1), 
									minInsertions(s, from+1, to));
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^2), S(n): O(n^2)
		public int minInsertions(String s) {
			char[] sa = s.toCharArray();
			int n = s.length();
			
			int[][] res = new int[n][n];
			for (int i = n-1; i >= 0; i--) {
				for (int j = i; j < n; j++) {
					if (i == j)
						res[i][j] = 0;
					else if (i == j-1)
						res[i][j] = (sa[i] == sa[j]) ? 0 : 1;
					else if (sa[i] == sa[j])
						res[i][j] = res[i+1][j-1];
					else
						res[i][j] = 1 + Math.min(res[i][j-1], res[i+1][j]);
				}
			}
			return res[0][n-1];
		}
	}
	
	/*
	 * We can also solve this problem using LCS. The solution will be
	 * s.length() - LCS(s, s.reverse())
	 * i.e., we find LCS of input string and its reverse, and length
	 * of input string minus LCS gives the minimum number of insertions 
	 * required for string to be palindrome.
	 */
	
	public static void main(String[] args) {
		String s = "abcda";
		MinimumInsertionsToFormPalindrome o = new MinimumInsertionsToFormPalindrome();
		System.out.println(o.new SimpleRecursiveSolution().minInsertions(s)); // 2
		System.out.println(o.new DPSolution().minInsertions(s)); // 2
	}
}
