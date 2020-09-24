package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/minimum-cells-required-reach-destination-jumps-equal-cell-values/

public class MinCellsRequiredToReachDestination {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int minCells(int[][] a) {
            int minCell = minCells(a, 0, 0);
            return minCell == Integer.MAX_VALUE ? -1 : minCell;
        }
        
        private int minCells(int[][] a, int r, int c) {
            // Base cases:
            if (r > a.length-1 || c > a[0].length-1)
                return Integer.MAX_VALUE;
            if (r == a.length-1 && c == a[0].length-1)
                return 1;
            
            int res = Math.min(minCells(a, r + a[r][c], c), 
                                minCells(a, r, c + a[r][c]));
            
            // consider this cell
            if (res != Integer.MAX_VALUE)
                res = res + 1;
            
            return res;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(r*c), S(n): O(r*c)
        public int minCells(int[][] a) {
            int r = a.length, c = a[0].length;
            
            int[][] dp = new int[r][c];
            
            int down, right, next_i, next_j;
            for (int i = r-1; i >= 0; i--) {
                for (int j = c-1; j >= 0; j--) {
                    if (i == r-1 && j == c-1) {
                        dp[i][j] = 1;
                        continue;
                    }
                    
                    next_i = i + a[i][j];
                    down =  (next_i < r) ? dp[next_i][j] : Integer.MAX_VALUE;
                    
                    next_j = j + a[i][j];
                    right = (next_j < c) ? dp[i][next_j] : Integer.MAX_VALUE;
                    
                    dp[i][j] = Math.min(down, right);
                    
                    // consider this cell
                    if (dp[i][j] != Integer.MAX_VALUE)
                        dp[i][j] += 1;
                }
            }
            
            return dp[0][0] == Integer.MAX_VALUE ? -1 : dp[0][0];
        }
    }
    
    public static void main(String[] args) {
        MinCellsRequiredToReachDestination solver = new MinCellsRequiredToReachDestination();
        int[][] a = { { 2, 3, 2, 1, 4 }, 
                      { 3, 2, 5, 8, 2 }, 
                      { 1, 1, 2, 2, 1 } };
        
        System.out.println(solver.new SimpleRecursiveSolution().minCells(a)); // 4
        System.out.println(solver.new DPSolution().minCells(a)); // 4
    }
}
