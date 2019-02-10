package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-sum-2-x-n-grid-no-two-elements-adjacent/

public class MaxSum2x1Grid {

	public static int getMaxSum(int[][] a) {
		int n = a[0].length;
		int[] res = new int[n];
		
		res[0] = Math.max(a[0][0], a[1][0]);
		res[1] = Math.max(Math.max(a[0][1], a[1][1]), res[0]);
		for (int i = 2; i < n; i++) {
			res[i] = Math.max(res[i-2] + Math.max(a[0][i], a[1][i]), res[i-1]);
		}
		
		return res[n-1];
	}
	
	public static int getMaxSumSpaceOptimized(int[][] a) {
		int n = a[0].length;
		
		int i_2 = Math.max(a[0][0], a[1][0]);
		int i_1 = Math.max(Math.max(a[0][1], a[1][1]), i_2);
		int res = i_1;
		for (int i = 2; i < n; i++) {
			res = Math.max(i_2 + Math.max(a[0][i], a[1][i]), i_1);
			i_2 = i_1;
			i_1 = res;
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		int[][] a = {
						{ 1, 2, 3, 4, 5}, 
						{ 6, 7, 8, 9, 10}
					}; 
		System.out.println(getMaxSum(a));
		System.out.println(getMaxSumSpaceOptimized(a));
	}
}
