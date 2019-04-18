package dynamicprogramming.intermediate;

import basic.stack.LargestRectHistogram;

// http://www.careercup.com/question?id=6299074475065344
// https://youtu.be/g8bSdXCG-lA

public class MaximumSizeRectangleOfAll1s {
	/*
	 * The idea is this:
	 * Go through the matrix row by row. For each row, record the maximum height 
	 * each column can reach. Then it becomes LargestAreaInHistogram problem, 
	 * which can be solved in O(n). 
	 */
	// T(n): O(n^2), S(n): O(n)
	public static int maxRectOf1(int[][] m) {
		int r = m.length, c = m[0].length;
		int[] tmp = new int[c];
		
		int maxArea = 0;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (m[i][j] == 1)
					tmp[j] += 1; // 1 increases existing height of column by one unit
				else 
					tmp[j] = 0; // 0 break continuity of rectangle, so we reset height
			}
			int area = LargestRectHistogram.getLargestRectangleInHistoram(tmp); // O(n)
			maxArea = Math.max(area, maxArea);
		}
		return maxArea;
	}
	
	public static void main(String[] args) {
		int[][] m = {
						{1,1,1,0},
		                {1,1,1,1},
		                {0,1,1,0},
		                {0,1,1,1},
		                {1,0,0,1},
		                {1,1,1,1}
	                };
		System.out.println(maxRectOf1(m)); // 8
	}
}
