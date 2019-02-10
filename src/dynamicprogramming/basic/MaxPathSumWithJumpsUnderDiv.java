package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-path-sum-position-jumps-divisibility-condition/

public class MaxPathSumWithJumpsUnderDiv {

	// T(n): O(n*sqrt(n)), S(n): O(n)
	public static void printMaxPathSum(int[] a) {
		int n = a.length;
		
		// create an array to store max path sum, such that
		// res[x] stores max path sum ending at location 'x'
		int[] res = new int[n+1];

		res[1] = a[0];
		for (int i = 2; i <= n; i++) {
			res[i] = a[i-1];
			
			int max_i = 0;
			for (int j = 1; j <= Math.sqrt(i); j++) {
				if (i % j == 0) {
					max_i = Math.max(max_i, res[j]);
					if (j != 1)
						max_i = Math.max(max_i, res[i/j]);
				}
			}
			res[i] += max_i;
		}
		
		for (int i = 1; i <= n; i++)
			System.out.print(res[i] + " ");
	}
	
	public static void main(String[] args) {
		int[] a = { 2, 3, 1, 4, 6, 5 };
		printMaxPathSum(a); // 2 5 3 9 8 10 
	}
}
