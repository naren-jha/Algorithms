package dynamicprogramming.hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/largest-area-rectangular-sub-matrix-equal-number-1s-0s/

public class LargestRectangularSubMatrixWithEqual1sAnd0s {

	// This problem is similar to previous problem 'Largest Rectangular SubMatrix With Zero Sum'
	// The only difference is that we consider every 0 in matrix as -1 (see line 28)
	
	// TC: O(cols^2 * rows), SC: O(rows)
	public Result largestRect(int[][] m) {
		int rows = m.length;
		int cols = m[0].length;
		
		int[] tmp = new int[rows];
		Result result = new Result();
		for (int left = 0; left < cols; left++) {
			
			Arrays.fill(tmp, 0);
			
			for (int right = left; right < cols; right++) {
				
				for (int i = 0; i < rows; i++)
					tmp[i] += m[i][right] == 1 ? 1 : -1; // notice the difference
				
				ZeroSumResult zeroSumResult = zeroSum(tmp);
				int size = (right - left + 1) * (zeroSumResult.end - zeroSumResult.start + 1);
				
				if (zeroSumResult.isZeroSum && size > result.maxSize) {
					result.maxSize = size;
					result.left = left;
					result.right = right;
					result.top = zeroSumResult.start;
					result.bottom = zeroSumResult.end;
				}
			}
		}
		return result;
	}
	
	private class Result {
		int maxSize;
		int left, right, top, bottom;
		
		@Override
		public String toString() {
			return "Largest rectangle size with equal number of 1s and 0s = " + maxSize + "\n"
					+ "left bound = " + left + "\n"
					+ "right bound = " + right + "\n"
					+ "top bound = " + top + "\n"
					+ "bottom bound = " + bottom;
		}
	}
	
	// https://www.geeksforgeeks.org/find-the-largest-subarray-with-0-sum/
	private ZeroSumResult zeroSum(int[] a) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		int sum = 0, maxLen = 0, currLen = 0;
		int start = 0, end = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			
			if (a[i] ==	0 && maxLen == 0) {
				maxLen = 1;
				start = i;
				end = i;
			}
			
			if (sum == 0) {
				maxLen = i+1;
				start = 0;
				end = i;
			}
			
			if (map.containsKey(sum)) {
				currLen = i - map.get(sum);
				if (currLen > maxLen) {
					maxLen = currLen;
					start = map.get(sum) + 1;
					end = i;
				}
			}
			else {
				map.put(sum, i);
			}
		}
		return new ZeroSumResult(maxLen != 0, start, end);
	}

	private class ZeroSumResult {
		boolean isZeroSum; int start, end;
		
		ZeroSumResult(boolean isZeroSum, int start, int end) {
			this.isZeroSum = isZeroSum;
			this.start = start;
			this.end = end;
		}
	}
	
	public static void main(String[] args) {
		int[][] m = { 
						{0, 0, 1, 1}, 
		                {0, 1, 1, 0}, 
		                {1, 1, 1, 0}, 
		                {1, 0, 0, 1}
					};
		
		Result result = new LargestRectangularSubMatrixWithEqual1sAnd0s().largestRect(m);
		System.out.println(result);
		/*
		 * Largest rectangle size with equal number of 1s and 0s = 8
		 * left bound = 0
		 * right bound = 1
		 * top bound = 0
		 * bottom bound = 3
		 */
		
		// Printing actual largest rectangular sub-matrix with equal number of 1s and 0s
		System.out.println("\nLargest rectangular sub-matrix is:");
		for (int r = result.top; r <= result.bottom; r++) {
			for (int c = result.left; c <= result.right; c++) {
				System.out.print(m[r][c] + " ");
			}
			System.out.println();
		}
		/*
		 * Largest rectangular sub-matrix is:
		 * 0 0 
		 * 0 1 
		 * 1 1 
		 * 1 0 
		 */
	}
}
