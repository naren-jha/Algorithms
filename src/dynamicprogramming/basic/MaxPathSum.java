package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-path-sum-starting-cell-0-th-row-ending-cell-n-1-th-row/

// This problem is similar to gold mine problem

public class MaxPathSum {

	// T(n) = O(mn)
	public int getMaxPathSumBottomUp(int[][] mat) {
		int m = mat.length; // row length
		int n = mat[0].length; // col length
		
		// matrix to store intermediate results
		int[][] maxMat = new int[m][n];
		
		int max = 0, bottom, bottomLeft, bottomRight;
		for (int row = m-1; row >= 0; row--) {
			for (int col = 0; col < n; col++) {
				bottomLeft = (row == m-1 || col == 0) ? 0 : maxMat[row + 1][col - 1];
				bottom = (row == m-1) ? 0 : maxMat[row + 1][col];
				bottomRight = (row == m-1 || col == n-1) ? 0 : maxMat[row + 1][col + 1];
				maxMat[row][col] = mat[row][col] + Math.max(bottomLeft, Math.max(bottom, bottomRight));
				
				// Max path sum will be the max value in first row
				if (row == 0)
					max = Math.max(maxMat[row][col], max);
			}
		}
		
		return max;
	}
	
	public static void main(String[] args) {
		int[][] mat = {
						{ 4, 2, 3, 4 }, 
		                { 2, 9, 1, 10 }, 
		                { 15, 1, 3, 0 }, 
		                { 16, 92, 41, 44 }
		              };
		
		System.out.println(new MaxPathSum().getMaxPathSumBottomUp(mat));
	}
}
