package dynamicprogramming.intermediate;

/*
 * https://youtu.be/vi_1eHCsR9A
 * 
 * Find maximum subsquare in a matrix made up of Xs and Os such that all 
 * four sides of subsquare are Xs. It does not matter what is inside the
 * subsquare, all 4 sides should be made up entirely of Xs
 * 
 * e.g 
 * 0 0 0 0 0 X         0,0  0,0  0,0  0,0  0,0  1,1
 * 0 X 0 X X X         0,0  1,1  0,0  1,1  1,2  2,3 
 * 0 X 0 X 0 X         0,0  2,1  0,0  2,1  0,0  3,1
 * 0 X X X X X         0,0  3,1  1,2  3,3  1,4  4,5
 * 0 0 0 0 0 0         0,0  0,0  0,0  0,0  0,0  0,0   
 * 
 * Output for above example should be 3
 * 
 * Solution
 * Have another matrix which is capable of holding 2 values 'hori' and 'ver'
 * 'ver' stores how far vertically you can see Xs. 'hori' stores how far 
 * horizontally you can see Xs.
 * Once this matrix is build look for biggest subsquare by getting min 
 * of 'hori' and 'ver' at each point and checking if subsquare can be formed 
 * from value min to 1.
 * 
 */

public class MaximumSubsquareWithSidesAsX {

	class Cell {
		int ver; // vertical
		int hori; // horizontal
		
		Cell(int ver, int hori) {
			this.ver = ver;
			this.hori = hori;
		}
	}
	
	// T(n): O(m*n), S(n): O(m*n)
	public int maxSubSquraeMatrix(char[][] mat) {
		int m = mat.length, n = mat[0].length;
		Cell[][] dp = new Cell[m][n];
		
		// intialize values for first row and column
		dp[0][0] = (mat[0][0] == '0') ? new Cell(0, 0) : new Cell(1, 1);
		for (int c = 1; c < n; c++) {
			if (mat[0][c] == '0')
				dp[0][c] = new Cell(0, 0);
			else // 'X'
				dp[0][c] = new Cell(1, dp[0][c-1].hori + 1);
		}
		for (int r = 1; r < m; r++) {
			if (mat[r][0] == '0')
				dp[r][0] = new Cell(0, 0);
			else // 'X'
				dp[r][0] = new Cell(dp[r-1][0].ver + 1, 1);
		}
		
		// fill rest of the table
		for (int r = 1; r < m; r++) {
			for (int c = 1; c < n; c++) {
				if (mat[r][c] == '0')
					dp[r][c] = new Cell(0, 0);
				else // 'X'
					dp[r][c] = new Cell(dp[r-1][c].ver + 1, dp[r][c-1].hori + 1);
			}
		}
		
		/*for(int i=0; i < dp.length; i++){
            for(int j=0; j < dp[0].length; j++){
                System.out.print(dp[i][j].ver + "," + dp[i][j].hori+ "  ");
            }
            System.out.println();
        }*/
		
		int max = 0;
		for (int r = m-1; r >= 0; r--) {
			for (int c = n-1; c >= 0; c--) {
				int k = Math.min(dp[r][c].ver, dp[r][c].hori);
				while (k != 0 && k > max) {
					if (dp[r][c - k + 1].ver >= k && dp[r - k + 1][c].hori >= k)
						max = k;
					k--;
				}
			}
		}
		return max;
	}
	
	public static void main(String[] args) {
		char[][] input1 = {{'X','0','0','0','0','0'},
		                  {'0','0','0','0','0','0'},
		                  {'X','X','X','X','0','0'},
		                  {'X','X','X','X','X','0'},
		                  {'X','0','0','X','X','0'},
		                  {'X','0','X','X','X','0'}};
		
		char[][] input2 = {{'0', '0', '0', '0', '0', 'X'},
		                    {'0', 'X', '0', 'X', 'X', 'X'},
		                    {'0', 'X', '0', 'X', '0', 'X'},
		                    {'0', 'X', 'X', 'X', 'X', 'X'},
		                    {'0', '0', '0', '0', '0', '0'}};
		
		char[][] input3 = {{'0', '0', 'X', '0', 'X'},
		                    {'0', 'X', 'X', '0', 'X'},
		                    {'0', 'X', '0', 'X', 'X'},
		                    {'X', 'X', 'X', 'X', 'X'},
		                    {'0', 'X', 'X', 'X', '0'}};
		
		MaximumSubsquareWithSidesAsX obj = new MaximumSubsquareWithSidesAsX();
		System.out.println(obj.maxSubSquraeMatrix(input1)); // 2
		System.out.println(obj.maxSubSquraeMatrix(input2)); // 3
		System.out.println(obj.maxSubSquraeMatrix(input3)); // 2
	}
}
