package dynamicprogramming.hard;

/**
 * @author Narendra Jha
 *
 * CLRS 15.2
 * https://www.geeksforgeeks.org/matrix-chain-multiplication-dp-8/
 * https://www.geeksforgeeks.org/printing-brackets-matrix-chain-multiplication-problem/
 */
public class MatrixChainMultiplication {
	
	class SimpleRecursiveSolution {
		// T(n): Exponential
		public int matrixChainOrder(int[] p, int i, int j) {
			// base case
			if (i == j)
				return 0;
			
			int minCost = Integer.MAX_VALUE;
			for (int k = i; k < j; k++) {
				int cost = matrixChainOrder(p, i, k) + matrixChainOrder(p, k+1, j)
						   + p[i-1]*p[k]*p[j];
				if (cost < minCost)
					minCost = cost;
			}
			return minCost;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^3), S(n): O(n^2)
		public int matrixChainOrder(int[] p) {
			int n = p.length - 1; // Number of matrices being multiplied
			
			// dp[i][j] stores minimum cost required to multiply matrix chain A[i..j]
			// therefore dp[1][n] will hold the final result to multiply A[1..n]
			int[][] dp = new int[n+1][n+1];
			
			// initialize cost for length 1
			for (int i = 1; i <= n; i++)
				dp[i][i] = 0;
			
			// fill entries for lengths 2 to n
			for (int len = 2; len <= n; len++) {
				for (int i = 1; i <= n - len + 1; i++) {
					int j = i + len - 1;
					dp[i][j] = Integer.MAX_VALUE;
					for (int k = i; k < j; k++) {
						int cost = dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j];
						if (cost < dp[i][j])
							dp[i][j] = cost;
					}
				}
			}
			
			return dp[1][n];
		}
		
		/*
		 * We can extend above solution to print optimal matrix product parenthesization
		 */
		public void printOptimalParenthesis(int[] p) {
			int n = p.length - 1; // Number of matrices being multiplied
			
			// dp[i][j] stores minimum cost required to multiply matrix chain A[i..j]
			// therefore dp[1][n] will hold the final result to multiply A[1..n]
			int[][] dp = new int[n+1][n+1];
			
			// split[i][j] stores matrix number where matrix product of A[i..j]
			// should be split to get optimal solution
			int[][] split = new int[n+1][n+1];
			
			// initialize cost for length 1
			for (int i = 1; i <= n; i++)
				dp[i][i] = 0;
			
			// fill entries for lengths 2 to n
			for (int len = 2; len <= n; len++) {
				for (int i = 1; i <= n - len + 1; i++) {
					int j = i + len - 1;
					dp[i][j] = Integer.MAX_VALUE;
					for (int k = i; k < j; k++) {
						int cost = dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j];
						if (cost < dp[i][j]) {
							dp[i][j] = cost;
							split[i][j] = k;
						}
					}
				}
			}
			
			printParenthesis(split, 1, n);
		}

		private void printParenthesis(int[][] split, int i, int j) {
			// base case:
			if (i == j) {
				System.out.print("A"+i);
				return;
			}
			
			System.out.print("(");
			printParenthesis(split, i, split[i][j]);
			printParenthesis(split, split[i][j]+1, j);
			System.out.print(")");
		}
		
		
		/*
		 * Below is an algorithm to actually multiply a given matrix chain in optimal way
		 * CLRS: Exercise 15.2-2
		 * 
		 * BELOW CODE IS NOT TESTED
		 */
		public int[][] MatrixChainMultiply(int[][][] A, int[][] split, int i, int j) {
			// base case:
			if (i == j) {
				return A[i];
			}
			
			int[][] X = MatrixChainMultiply(A, split, i, split[i][j]);
			int[][] Y = MatrixChainMultiply(A, split, split[i][j]+1, j);
			return MatrixMultiply(X, Y);
		}
		
		public int[][] MatrixMultiply(int[][] A, int[][] B) {
			int aRows = A.length, aCols = A[0].length;
			int bRows = B.length, bCols = B[0].length;
			
			if (aCols != bRows)
				throw new IllegalStateException("Matrices cannot be multiplied");
			
			int[][] res = new int[aRows][bCols];
			for (int i = 0; i < aRows; i++) {
				for (int j = 0; j < bCols; j++) {
					res[i][j] = 0;
					for (int k = 0; k < aCols; k++)
						res[i][j] += A[i][k] * B[k][j];
				}
			}
			return res;
		}
	}
	
	public static void main(String[] args) {
		int[] p = {30, 35, 15, 5, 10, 20, 25}; // matrices dimensions
		int n = p.length - 1; // Number of matrices being multiplied
		MatrixChainMultiplication o = new MatrixChainMultiplication();
		
		System.out.println(o.new SimpleRecursiveSolution().matrixChainOrder(p, 1, n));// 15125
		System.out.println(o.new DPSolution().matrixChainOrder(p)); // 15125
		
		o.new DPSolution().printOptimalParenthesis(p); // ((A1(A2A3))((A4A5)A6))
	}

}
