package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/maximize-binary-matrix-filpping-submatrix/

public class MaximizeBinaryMatrixByFilppingSubmatrixOnce {
	
	// Idea of solution is similar to MaximumSumRectangleInMatrix
	
	// TC: O(cols^2 * rows), SC: O(rows)
	public static int maxOnes(int[][] m) {
		int rows = m.length;
		int cols = rows != 0 ? m[0].length : 0;
		
		int ones = 0;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				ones += m[i][j];
		
		// extra max no. of ones we can acheive by flipping a submatrix
		int extraMaxOnes = 0;
		
		int[] tmp = new int[rows];
		for (int left = 0; left < cols; left++) {
			
			Arrays.fill(tmp, 0); // reset temp array
			
			for (int right = left; right < cols; right++) {
				
				for (int i = 0; i < rows; i++) {
					if (m[i][right] == 0)
						tmp[i] += 1;
					else // 1
						tmp[i] += -1;
				}
				
				int extraOnes = kadane(tmp);
				if (extraOnes > 0 && extraOnes > extraMaxOnes)
					extraMaxOnes = extraOnes;
			}
			
		}
		
		return ones + extraMaxOnes;
	}
	
	// handles all negative number case as well
	public static int kadane(int[] a) {
		int maxSoFar = Integer.MIN_VALUE;
		int maxEndingHere = 0;
		
		for (int i = 0; i < a.length; i++) {
			maxEndingHere = Math.max(a[i], maxEndingHere + a[i]);
			maxSoFar = Math.max(maxSoFar, maxEndingHere);
		}
		
		return maxSoFar;
	}
	
	public static void main(String[] args) {
		int[][] m = {{0, 0, 1},  
			         {0, 0, 1},  
			         {1, 0, 1}};
		System.out.println(maxOnes(m)); // 8
	}

}
