package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/path-maximum-average-value/

public class PathWithMaximumAverageValue {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public double maxAvgPathVal(int[][] a) {
			int n = a.length;
			return (double) maxAvgPathValUtil(a, n-1, n-1) / (2*n - 1);
		}
		
		private int maxAvgPathValUtil(int[][] a, int row, int col) {
			if (row < 0 || col < 0)
				return 0;
			if (row == 0 && col == 0)
				return a[0][0];
			return Math.max(maxAvgPathValUtil(a, row-1, col),
					maxAvgPathValUtil(a, row, col-1)) + a[row][col];
		}
	}
	
	class DPSolution {
		// Top-down memoization
		// T(n): O(n^2), S(n): O(n^2)
		public double maxAvgPathValMemoized(int[][] a) {
			int n = a.length;
			int[][] res = new int[n][n];
			for (int i = 0; i < n; i++)
				Arrays.fill(res[i], -1);
			return (double) maxAvgPathValMemUtil(a, n-1, n-1, res) / (2*n - 1);
		}
		
		private int maxAvgPathValMemUtil(int[][] a, int row, int col, int[][] res) {
			if (row < 0 || col < 0)
				return 0;
			if (row == 0 && col == 0)
				return a[0][0];
			
			if (res[row][col] != -1)
				return res[row][col];
			res[row][col] = Math.max(maxAvgPathValMemUtil(a, row-1, col, res),
					maxAvgPathValMemUtil(a, row, col-1, res)) + a[row][col];
			return res[row][col];
		}
		
		// Bottom-up tabulation
		// T(n): O(n^2), S(n): O(n^2)
		public double maxAvgPathVal(int[][]	a) {
			int n = a.length;
			
			int[][] res = new int[n][n];
			
			int left, up;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i == 0 && j == 0) {
						res[i][j] = a[i][j];
						continue;
					}
					up = 0;
					if (i > 0)
						up = res[i-1][j];
					left = 0;
					if (j > 0)
						left = res[i][j-1];
					res[i][j] = Math.max(up, left) + a[i][j];
				}
			}
			return (double) res[n-1][n-1] / (2*n - 1);
		}
	}
	
	public static void main(String[] args) {
		int[][] a = {
						{1, 2, 3}, 
		                {6, 5, 4}, 
		                {7, 3, 9}
	                };
		PathWithMaximumAverageValue o = new PathWithMaximumAverageValue();
		System.out.println(o.new SimpleRecursiveSolution().maxAvgPathVal(a)); // 5.2
		System.out.println(o.new DPSolution().maxAvgPathValMemoized(a)); // 5.2
		System.out.println(o.new DPSolution().maxAvgPathVal(a)); // 5.2
	}
}
