package dynamicprogramming.basic;

// CLRS 15.4
// https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
// https://www.geeksforgeeks.org/printing-longest-common-subsequence/
// https://www.geeksforgeeks.org/printing-longest-common-subsequence-set-2-printing/
// https://www.geeksforgeeks.org/space-optimized-solution-lcs/

public class LongestCommonSubsequence {

	class SimpleRecursiveSolution {
		// T(m,n) = Exp
		public int lcs(char[] x, char[] y, int m, int n) {
			if (m == 0 || n == 0)
				return 0;
			if (x[m-1] == y[n-1])
				return lcs(x, y, m-1, n-1) + 1;
			else
				return Math.max(lcs(x, y, m, n-1), lcs(x, y, m-1, n));
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(m,n): O(mn), S(m,n): O(mn)
		public int lcs(char[] x, char[] y) {
			int m = x.length, n = y.length;
			int[][] res = new int[m+1][n+1];
			
			for (int i = 0; i <= m; i++) {
				for (int j = 0; j <= n; j++) {
					if (i == 0 || j == 0)
						res[i][j] = 0;
					else if (x[i-1] == y[j-1])
						res[i][j] = res[i-1][j-1] + 1;
					else
						res[i][j] = Math.max(res[i][j-1], res[i-1][j]);
				}
			}
			
			return res[m][n];
		}
		
		/*
		 * We can also print the LCS. see the solution below.
		 */
		public void printLcs(char[] x, char[] y) {
			int m = x.length, n = y.length;
			int[][] res = new int[m+1][n+1];
			
			for (int i = 0; i <= m; i++) {
				for (int j = 0; j <= n; j++) {
					if (i == 0 || j == 0)
						res[i][j] = 0;
					else if (x[i-1] == y[j-1])
						res[i][j] = res[i-1][j-1] + 1;
					else
						res[i][j] = Math.max(res[i][j-1], res[i-1][j]);
				}
			}			
			
			printLcsUtil(res, m, n, x, y, new StringBuilder());
		}
		
		private void printLcsUtil(int[][] res, int i, int j, char[] x, char[] y, 
											StringBuilder lcs) {
			if (i == 0 || j == 0) {
				System.out.println("Longest common subsequence is: ");
				System.out.println(lcs.reverse());
				return;
			}
			
			if (x[i-1] == y[j-1]) {
				lcs.append(x[i-1]);
				printLcsUtil(res, i-1, j-1, x, y, lcs);
			}
			else if (res[i-1][j] >= res[i][j-1])
				printLcsUtil(res, i-1, j, x, y, lcs);
			else 
				printLcsUtil(res, i, j-1, x, y, lcs);
		}
		
		// Another approach for above recursive util method
		// Change 1: it does not use a string builder
		// Change 2: it does not require both input string
		private void printLcsUtil2(int[][] res, int i, int j, char[] x) {
			if (i == 0 || j == 0)
				return;
			
			if (res[i][j] == res[i-1][j-1] + 1) {
				printLcsUtil2(res, i-1, j-1, x);
				System.out.print(x[i-1]);
			}
			else if (res[i-1][j] >= res[i][j-1])
				printLcsUtil2(res, i-1, j, x);
			else 
				printLcsUtil2(res, i, j-1, x);
		}
		
		// We can also print all the possible LCS. see solution below.
		public void printAllLcs(char[] x, char[] y) {
			int m = x.length, n = y.length;
			int[][] res = new int[m+1][n+1];
			
			for (int i = 0; i <= m; i++) {
				for (int j = 0; j <= n; j++) {
					if (i == 0 || j == 0)
						res[i][j] = 0;
					else if (x[i-1] == y[j-1])
						res[i][j] = res[i-1][j-1] + 1;
					else
						res[i][j] = Math.max(res[i][j-1], res[i-1][j]);
				}
			}			
			
			System.out.println("Longest common subsequences are: ");
			printAllLcsUtil(res, m, n, x, y, new StringBuilder());
		}
		
		private void printAllLcsUtil(int[][] res, int i, int j, char[] x, char[] y, 
												StringBuilder lcs) {
			if (i == 0 || j == 0) {
				System.out.println(lcs.reverse());
				return;
			}
			
			if (x[i-1] == y[j-1]) {
				lcs.append(x[i-1]);
				printAllLcsUtil(res, i-1, j-1, x, y, lcs);
			}
			else if (res[i-1][j] == res[i][j-1]) {
				// two different LCS branches out
				StringBuilder lcs2 = new StringBuilder(lcs);
				printAllLcsUtil(res, i-1, j, x, y, lcs);
				printAllLcsUtil(res, i, j-1, x, y, lcs2);
			}
			else if (res[i-1][j] > res[i][j-1])
				printAllLcsUtil(res, i-1, j, x, y, lcs);
			else 
				printAllLcsUtil(res, i, j-1, x, y, lcs);
		}
		
		/*
		 * Since we are always using only the immediate previous
		 * row while calculating current row, we can manage with
		 * 2 rows instead of m, and use then on alternate basis.
		 * See the solution below.
		 * If we use this solution, however, 
		 * then we cannot construct LCS from res[][] matrix.
		 */
		// T(m,n): O(mn), S(m,n): O(min(m,n))
		public int lcsSpaceOptimized(char[] x, char[] y) {
			int m = x.length, n = y.length;
			int[][] res = new int[2][n+1];
			
			for (int i = 0; i <= m; i++) {
				for (int j = 0; j <= n; j++) {
					if (i == 0 || j == 0)
						res[i%2][j] = 0;
					else if (x[i-1] == y[j-1])
						res[i%2][j] = res[(i-1)%2][j-1] + 1;
					else
						res[i%2][j] = Math.max(res[i%2][j-1], res[(i-1)%2][j]);
				}
			}
			
			return res[m%2][n];
		}
	}
	
	public static void main(String[] args) {
		String s1 = "ABCBDAB";
		String s2 = "BDCABA";
		
		char[] x = s1.toCharArray();
		char[] y = s2.toCharArray();
		int m = x.length, n = y.length;
		
		LongestCommonSubsequence obj = new LongestCommonSubsequence();
		
		System.out.println(obj.new SimpleRecursiveSolution().lcs(x, y, m, n)); // 4
		
		System.out.println(obj.new DPSolution().lcs(x, y)); // 4
		
		obj.new DPSolution().printLcs(x, y); // BCBA
		
		obj.new DPSolution().printAllLcs(x, y);
		/*
		 * BCBA
		 * BCAB
		 * BDAB
		 */
		
		System.out.println(obj.new DPSolution().lcsSpaceOptimized(x, y)); // 4
	}
}
