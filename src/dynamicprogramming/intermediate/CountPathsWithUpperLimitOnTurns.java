package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-number-of-paths-with-k-turns/

public class CountPathsWithUpperLimitOnTurns {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int countPaths(int i, int j, int k) {
			// dir = 0 => rows, dir = 1 => rows
			return countPaths(i, j, k, 0) + countPaths(i, j, k, 1);
		}
		
		public int countPaths(int i, int j, int k, int dir) {
			// Base cases
			if (i < 0 || j < 0)
				return 0;
			if (i == 0 && j == 0)
				return 1;
			if (k == 0) {
				if (dir == 0 && i == 0)
					return 1;
				if (dir == 1 && j == 0)
					return 1;
				else
					return 0;
			}
			
			if (dir == 0) // When current direction is row
				return countPaths(i, j-1, k, 0) + countPaths(i-1, j, k-1, 1);
			else // When current direction is column
				return countPaths(i, j-1, k, 1) + countPaths(i-1, j, k-1, 0);
		}
	}
	
	class DPSolution {
		// top-down memoization
		// T(n): O(m*n*k), S(n): O(m*n*k)
		public int countPaths(int i, int j, int k) {
			int[][][][] res = new int[i+1][j+1][k+1][2];
			// initialize entire array with -1
			for (int x = 0; x <= i; x++) {
				for (int y = 0; y <= j; y++) {
					for (int z = 0; z <= k; z++) {
						res[x][y][z][0] = -1;
						res[x][y][z][1] = -1;
					}
				}
			}
			return countPaths(i, j, k, 0, res) + countPaths(i, j, k, 1, res);
		}
		
		public int countPaths(int i, int j, int k, int dir, int[][][][] res) {
			// Base cases
			if (i < 0 || j < 0)
				return 0;
			if (i == 0 && j == 0)
				return 1;
			if (k == 0) {
				if (dir == 0 && i == 0)
					return 1;
				if (dir == 1 && j == 0)
					return 1;
				else
					return 0;
			}
			
			if (res[i][j][k][dir] != -1)
				return res[i][j][k][dir];
			
			if (dir == 0) // When current direction is row
				res[i][j][k][dir] = countPaths(i, j-1, k, 0, res) 
											+ countPaths(i-1, j, k-1, 1, res);
			else // When current direction is column
				res[i][j][k][dir] = countPaths(i, j-1, k, 1, res) 
											+ countPaths(i-1, j, k-1, 0, res);
			return res[i][j][k][dir];
		}
		
		// similarly we can solve it using bottom-up tabulation technique as well
	}
	
	public static void main(String[] args) {
		int m = 3, n = 3, k = 2;
		CountPathsWithUpperLimitOnTurns o = new CountPathsWithUpperLimitOnTurns();
		System.out.println(o.new SimpleRecursiveSolution().countPaths(m, n, k)); // 4
		System.out.println(o.new DPSolution().countPaths(m, n, k)); // 4
	}
	
}
