package dynamicprogramming.basic;

import java.util.Stack;

// https://www.geeksforgeeks.org/find-maximum-length-snake-sequence/

public class MaximumLengthSnakeSequence {
	
	public void findMaxLengthSnakeSequence(int[][] mat) {
		int m = mat.length;
		if (m == 0)
			return;
		int n = mat[0].length;
		
		// create lookup matrix of same size as input matrix
		// which will store the maximum length of snake sequence 
		// possible for each location in input matrix, with tail
		// of snake at that location of input matrix
		int[][] lookup = new int[m][n];
		
		// (0, 0) location will have snake of length 0
		lookup[0][0] = 0; // redundant in java
		
		// fill the lookup matrix by calculating max length
		// snake sequence possible for each location
		int maxLength = 0;
		int maxRow = 0, maxCol = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 && j== 0)
					continue;
				
				// look above
				if (i > 0 && Math.abs(mat[i][j] - mat[i-1][j]) == 1)
					lookup[i][j] = lookup[i-1][j] + 1;
				
				// look left
				if (j > 0 && Math.abs(mat[i][j] - mat[i][j-1]) == 1)
					lookup[i][j] = Math.max(lookup[i][j], lookup[i][j-1] + 1);
				
				if (lookup[i][j] > maxLength) {
					maxLength = lookup[i][j];
					maxRow = i; maxCol = j;
				}
			}
		}
		
		System.out.println("Max length of snake sequnence is " + maxLength);
		if (maxLength > 0) {
			Stack<Point> path = new Stack<Point>();
			findPath(mat, lookup, maxRow, maxCol, path);
			
			System.out.println("Snake sequence is: ");
			while (!path.isEmpty()) {
				Point point = path.pop();
				System.out.println(mat[point.x][point.y] + 
						"\t(" + point.x + ", " + point.y + ")");
			}
		}
	}
	
	private class Point {
		int x; int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private void findPath(int[][] mat, int[][] lookup, int i, int j, Stack<Point> path) {
		
		path.push(new Point(i, j));
		
		// base condition
		if (lookup[i][j] == 0)
			return;
		
		if (i > 0 && lookup[i][j] - 1 == lookup[i-1][j])
			findPath(mat, lookup, i-1, j, path);
		else if (j > 0 && lookup[i][j] - 1 == lookup[i][j-1])
			findPath(mat, lookup, i, j-1, path);
	}
	
	public static void main(String[] args) {
		int[][] mat = { 
				         {9, 6, 5, 2}, 
				         {8, 7, 6, 5}, 
				         {7, 3, 1, 6}, 
				         {1, 1, 1, 7}, 
			    	  }; 
		MaximumLengthSnakeSequence obj = new MaximumLengthSnakeSequence();
		obj.findMaxLengthSnakeSequence(mat);
	}

}
