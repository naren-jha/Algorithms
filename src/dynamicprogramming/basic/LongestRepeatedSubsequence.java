package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/longest-repeated-subsequence/

public class LongestRepeatedSubsequence {
	/*
	 * This problem is just the modification of 
	 * Longest Common Subsequence problem. 
	 * The idea is to find the LCS(str, str)
	 * where str is the input string with the restriction
	 * that when both the characters are same, they 
	 * shouldn’t be on the same index in the two strings.
	 */
	public int lrs(char[] x) {
		return lrs(x, x);
	}
	
	// Bottom-up tabulation
	// T(m,n): O(mn), S(m,n): O(mn)
	private int lrs(char[] x, char[] y) {
		int m = x.length, n = y.length;
		int[][] res = new int[m+1][n+1];
		
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0)
					res[i][j] = 0;
				else if (x[i-1] == y[j-1] && i!= j)
					res[i][j] = res[i-1][j-1] + 1;
				else
					res[i][j] = Math.max(res[i][j-1], res[i-1][j]);
			}
		}
		
		return res[m][n];
	}
	
	/*
	 * We can also print the LRS. see the solution below.
	 */
	public void printLrs(char[] x) {
		printLrs(x, x);
	}
	
	public void printLrs(char[] x, char[] y) {
		int m = x.length, n = y.length;
		int[][] res = new int[m+1][n+1];
		
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0)
					res[i][j] = 0;
				else if (x[i-1] == y[j-1] && i != j)
					res[i][j] = res[i-1][j-1] + 1;
				else
					res[i][j] = Math.max(res[i][j-1], res[i-1][j]);
			}
		}			
		
		printLrsUtil(res, m, n, x, y, new StringBuilder());
	}
	
	private void printLrsUtil(int[][] res, int i, int j, char[] x, char[] y, 
										StringBuilder lrs) {
		if (i == 0 || j == 0) {
			System.out.println("Longest repeated subsequence is: ");
			System.out.println(lrs.reverse());
			return;
		}
		
		if (x[i-1] == y[j-1] && i != j) {
			lrs.append(x[i-1]);
			printLrsUtil(res, i-1, j-1, x, y, lrs);
		}
		else if (res[i-1][j] >= res[i][j-1])
			printLrsUtil(res, i-1, j, x, y, lrs);
		else 
			printLrsUtil(res, i, j-1, x, y, lrs);
	}
	
	public static void main(String[] args) {
		String s = "abab";
		LongestRepeatedSubsequence obj = new LongestRepeatedSubsequence();
		
		System.out.println(obj.lrs(s.toCharArray())); // 2
		obj.printLrs(s.toCharArray()); // ab
		
		s = "axxxy";		
		System.out.println(obj.lrs(s.toCharArray())); // 2
		obj.printLrs(s.toCharArray()); // xx
	}

}
