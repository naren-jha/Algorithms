package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/find-maximum-dot-product-two-arrays-insertion-0s/

public class MaximumDotProductTwoArrays {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int maxDotProd(int[] a, int[] b) {
			return maxDotProd(a, b, a.length, b.length);
		}

		private int maxDotProd(int[] a, int[] b, int m, int n) {
			// Base case:
			if (m < n) // optional, but makes algorithm more efficient
				return 0;
			if (m == 0 || n == 0)
				return 0;
			
			return Math.max(maxDotProd(a, b, m-1, n-1) + a[m-1]*b[n-1], 
							maxDotProd(a, b, m-1, n));
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(m*n), S(n): O(m*n)
		public int maxDotProd(int[] a, int[] b) {
			int m = a.length;
			int n = b.length;
			
			int[][] res = new int[m+1][n+1];
			
			for (int i = 0; i <= m; i++) {
				// j <= i covers the case m < n, which is optional
				for (int j = 0; j <= n && j <= i; j++) {
					if (i == 0 || j == 0)
						res[i][j] = 0;
					else
						res[i][j] = Math.max(res[i-1][j-1] + a[i-1]*b[j-1], res[i-1][j]);
				}
			}
			
			return res[m][n];
		}
	}
	
	public static void main(String[] args) {
		int[] a = {2, 3 , 1, 7, 8};
		int[] b = {3, 6, 7};
		MaximumDotProductTwoArrays obj = new MaximumDotProductTwoArrays();
		System.out.println(obj.new SimpleRecursiveSolution().maxDotProd(a, b)); // 107
		System.out.println(obj.new DPSolution().maxDotProd(a, b)); // 107
	}
}
