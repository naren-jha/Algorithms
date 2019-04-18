package dynamicprogramming.hard;

/*
 * https://www.geeksforgeeks.org/probability-knight-remain-chessboard/
 * https://youtu.be/OrS7PaJ-5ck
 * https://qr.ae/TWTkn3
 * https://leetcode.com/problems/knight-probability-in-chessboard/
 * https://community.topcoder.com/stat?c=problem_statement&pm=3509&rd=6528
 */ 
public class ProbabilityOfKnightToRemainInChessboard {

	private final int n = 8; // size of chessboard
	
	// direction array
	private int[] dx = {-1, 1, 2, 2, 1, -1, -2, -2};
	private int[] dy = {2, 2, 1, -1, -2, -2, -1, 1};
	
	// returns true if knight position is inside chessboard
	private boolean isInside(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}
	
	class SimpleRecursiveSolution {
		// T(n): Exp
		public double probability(int start_x, int start_y, int k) {
			// Base case
			if (k == 0)
				return 1;
			
			double probability = 0;
			for (int move = 0; move < 8; move++) {
				int next_x = start_x + dx[move];
				int next_y = start_y + dy[move];
				if (isInside(next_x, next_y))
					probability += probability(next_x, next_y, k-1) / 8;
			}
			return probability;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^2 * k), S(n): O(n^2 * k)
		public double probability(int start_x, int start_y, int k) {
			double[][][] dp = new double[n][n][k+1];
			
			// initialize base case for k = 0
			for (int x = 0; x < n; x++)
				for (int y = 0; y < n; y++)
					dp[x][y][0] = 1;
			
			// fill rest of the table for 1 to k
			for (int step = 1; step <= k; step++) {
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						double probability = 0;
						for (int move = 0; move < 8; move++) {
							int next_x = x + dx[move];
							int next_y = y + dy[move];
							if (isInside(next_x, next_y))
								probability += dp[next_x][next_y][step-1] / 8;
						}
						dp[x][y][step] = probability;
					}
				}
			}
			
			return dp[start_x][start_y][k];
		}
		
		/*
		 * We can optimize space complexity for above solution to O(n^2). We only need  
		 * immediate previous 2-d array to calculate values for current 2-d array. 
		 * See solution below.
		 */
		// T(n): O(n^2 * k), S(n): O(n^2)
		public double probabilitySO(int start_x, int start_y, int k) {
			double[][][] dp = new double[n][n][2];
			
			// initialize base case for k = 0
			for (int x = 0; x < n; x++)
				for (int y = 0; y < n; y++)
					dp[x][y][0] = 1;
			
			// fill rest of the table for 1 to k
			for (int step = 1; step <= k; step++) {
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						double probability = 0;
						for (int move = 0; move < 8; move++) {
							int next_x = x + dx[move];
							int next_y = y + dy[move];
							if (isInside(next_x, next_y))
								probability += dp[next_x][next_y][(step-1) % 2] / 8;
						}
						dp[x][y][step % 2] = probability;
					}
				}
			}
			
			return dp[start_x][start_y][k % 2];
		}
	}
	
	public static void main(String[] args) {
		ProbabilityOfKnightToRemainInChessboard o = 
				new ProbabilityOfKnightToRemainInChessboard();
		int k = 3;
		System.out.println(o.new SimpleRecursiveSolution().probability(0, 0, k)); // 0.125
		System.out.println(o.new DPSolution().probability(0, 0, k)); // 0.125
		System.out.println(o.new DPSolution().probabilitySO(0, 0, k)); // 0.125
	}
}
