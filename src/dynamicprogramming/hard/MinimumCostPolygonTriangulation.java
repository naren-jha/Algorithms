package dynamicprogramming.hard;

import java.math.RoundingMode;
import java.text.DecimalFormat;

// https://www.geeksforgeeks.org/minimum-cost-polygon-triangulation/
// http://www.cs.utexas.edu/users/djimenez/utsa/cs3343/lecture12.html
// https://youtu.be/s8z5fTelrlg

public class MinimumCostPolygonTriangulation {
	
	/*
	 * A triangulation of a polygon can be thought of as a set of chords that 
	 * divide the polygon into triangles such that no two chords intersect 
	 * (except possibly at a vertex).
	 * 
	 * This problem has recursive substructure. The idea is to divide the polygon 
	 * into three parts: a single triangle, the sub-polygon to the left, and the 
	 * sub-polygon to the right. We try all possible divisions like this and find 
	 * the one that minimizes the cost of the triangle plus the cost of the 
	 * triangulation of the two sub-polygons.
	 */

	// The solution is similar to Matrix Chain Multiplication problem.
	
	/*
	 *  Let Minimum Cost of triangulation of vertices from i to j be minCost(i, j)
		If j < i + 2 Then
		  minCost(i, j) = 0
		Else
		  minCost(i, j) = Min { minCost(i, k) + minCost(k, j) + cost(i, k, j) }
		                  Where k varies from 'i+1' to 'j-1'
		
		Cost of a triangle formed by edges (i, j), (j, k) and (k, i) is 
		  cost(i, j, k)  = dist(i, j) + dist(j, k) + dist(k, i)
	 */
	class SimpleRecursiveSolution {
		// T(n): Exponential
		public double minCost(Point[] points, int i, int j) {
			// Base case
			if (j < i+2)
				return 0;
			
			double minCost = Double.MAX_VALUE;
			for (int k = i+1; k < j; k++) {
				double cost = minCost(points, i, k) + minCost(points, k, j) 
						   + cost(points, i, j, k);
				minCost = Math.min(minCost, cost);
			}
			return minCost;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^3), S(n): O(n^2)
		public String minCost(Point[] points) {
			int n = points.length; // Number of vertices in polygon
			
			// dp[i][j] stores minimum cost required to triangulate points[i..j]
			// dp[0][n-1] will hold the final result to triangulate points[0..n-1]
			double[][] dp = new double[n][n];
			
			for (int len = 1; len <= n; len++) {
				for (int i = 0; i <= n - len; i++) {
					int j = i + len - 1;
					if (j < i+2)
						dp[i][j] = 0;
					else {
						dp[i][j] = Double.MAX_VALUE;
						for (int k = i+1; k < j; k++) {
							double cost = dp[i][k] + dp[k][j] + cost(points, i, j, k);
							dp[i][j] = Math.min(dp[i][j], cost);
						}
					}
				}
			}
			
			// return dp[0][n-1];
			
			// we can round off result up to 4 decimal places
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			return df.format(dp[0][n-1]);
		}
		
		// We can extend above solution to print optimal triangulation
		// We can print the list of triangles as triples of vertex indices
		public void printOptimalTriangulation(Point[] points) {
			int n = points.length;
			
			double[][] dp = new double[n][n];
			int[][] split = new int[n][n];
			
			for (int len = 1; len <= n; len++) {
				for (int i = 0; i <= n - len; i++) {
					int j = i + len - 1;
					if (j < i+2)
						dp[i][j] = 0;
					else {
						dp[i][j] = Double.MAX_VALUE;
						for (int k = i+1; k < j; k++) {
							double cost = dp[i][k] + dp[k][j] + cost(points, i, j, k);
							if (cost < dp[i][j]) {
								dp[i][j] = cost;
								split[i][j] = k;
							}
						}
					}
				}
			}
			
			printTriangulation(points, split, 0, n-1);
		}
		
		private void printTriangulation(Point[] points, int[][] split, int i, int j) {
			if (j < i+2)
				return;
			printTriangulation(points, split, i, split[i][j]);
			System.out.println("(" + i + ", " + split[i][j] + ", " + j + ")");
			printTriangulation(points, split, split[i][j], j);
		}
	}
	
	private class Point {
		int x, y;
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	// returns distance b/w two points
	private double dist(Point p1, Point p2) {
		int x_dist = p1.x - p2.x;
		int y_dist = p1.y - p2.y;
		return Math.sqrt(x_dist * x_dist + y_dist * y_dist);
	}
	
	// returns perimeter of the triangle
	private double cost(Point[] points, int i, int j, int k) {
		Point p_i = points[i], p_j = points[j], p_k = points[k];
		return dist(p_i, p_j) + dist(p_j, p_k) + dist(p_k, p_i);
	}
	
	public static void main(String[] args) {
		MinimumCostPolygonTriangulation o = new MinimumCostPolygonTriangulation();
		Point[] points = {
							o.new Point(0, 0),
							o.new Point(1, 0), 
							o.new Point(2, 1), 
							o.new Point(1, 2), 
							o.new Point(0, 2)
						 };
		int n = points.length;
		
		System.out.println(o.new SimpleRecursiveSolution().minCost(points, 0, n-1)); 
		// 15.30056307974577
		
		System.out.println(o.new DPSolution().minCost(points)); // 15.3006
		
		o.new DPSolution().printOptimalTriangulation(points);
		/*
		 * (0, 1, 4)
		 * (1, 2, 3)
		 * (1, 3, 4)
		 */
		
		// We can also print actual points instead of vertices numbers
		
		/*
		 * split[][] matrix generated for given input (NOT THE PART OF O/P)
		 * [[0, 0, 1, 1, 1], 
		 * 	[0, 0, 0, 2, 3], 
		 * 	[0, 0, 0, 0, 3], 
		 * 	[0, 0, 0, 0, 0], 
		 * 	[0, 0, 0, 0, 0]]
		 */
	}
}
