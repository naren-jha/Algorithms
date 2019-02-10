package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/minimum-sum-path-triangle/
/*
 * Input : 
	   2
	  3 7
	 8 5 6
	6 1 9 3
	
	Output : 11
	Explanation : 2 + 3 + 5 + 1 = 11
 */
public class MinPathSumTriangle {

	public int minPathSum(int[][] t) {
		int n = t.length;
		int[] res = new int[n];
		
		// first, calculate for bottom most row
		for (int j = 0; j < t[n-1].length; j++)
			res[j] = t[n-1][j];
		
		// calculate for rest of the rows bottom-up
		for (int i = n-2; i >= 0; i--) {
			for (int j = 0; j < t[i].length; j++) {
				res[j] = Math.min(res[j], res[j+1]) + t[i][j];
			}
		}
		
		return res[0];
	}
	
	public static void main(String[] args) {
		int[][] t = {
				{2},
				{3, 7},
				{8, 5, 6},
				{6, 1, 9, 3}
		};
		
		MinPathSumTriangle o = new MinPathSumTriangle();
		System.out.println(o.minPathSum(t)); // 11
	}
	
}
