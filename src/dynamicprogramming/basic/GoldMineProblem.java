package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;
import java.util.StringJoiner;

// https://www.geeksforgeeks.org/gold-mine-problem/

public class GoldMineProblem {
    
    private class BruteForce {
        // T(n) = Exponential
        public int maxGold(int[][] m) {
            int r = m.length; // row length
            if (r == 0) throw new IllegalArgumentException("empty matrix");
            
            int maxGold = 0;
            for (int i = 0; i < r; i++) {
                maxGold = max(maxGoldUtil(m, i, 0), maxGold);
            }
            
            return maxGold;
        }
        
        private int maxGoldUtil(int[][] m, int r, int c) {
            // base conditions
            if (r < 0 || r >= m.length)
                return 0;
            if (c < 0 || c >= m[0].length)
                return 0;
            
            return m[r][c] + max(maxGoldUtil(m, r-1, c+1), max(maxGoldUtil(m, r, c+1), maxGoldUtil(m, r+1, c+1)));
        }
    }
    
    
    private class DPSolution {
        // T(n) = O(mn)
        public int maxGoldMemoized(int[][] m) {
            int r = m.length; // row length
            if (r == 0) throw new IllegalArgumentException("empty matrix");
            int c = m[0].length; // col length
            
            // create a matrix to store intermediate results
            int[][] dp = new int[r][c];
            for (int i = 0; i < r; i++)
                Arrays.fill(dp[i],  -1);
            
            int maxGold = 0;
            for (int i = 0; i < r; i++) {
                maxGold = max(maxGoldUtilMemoized(m, i, 0, dp), maxGold);
            }
            
            return maxGold;
        }
        
        // top to bottom memoized
        private int maxGoldUtilMemoized(int[][] m, int r, int c, int[][] dp) {
            // base conditions
            if (r < 0 || r >= m.length)
                return 0;
            if (c < 0 || c >= m[0].length)
                return 0;
            
            if (dp[r][c] != -1)
                return dp[r][c];
            
            dp[r][c] = m[r][c] + max(maxGoldUtilMemoized(m, r-1, c+1, dp), 
                                    max(maxGoldUtilMemoized(m, r, c+1, dp), 
                                        maxGoldUtilMemoized(m, r+1, c+1, dp)));
            return dp[r][c];
        }
        
        // botton-up tabulation
        // T(n) = O(mn)
        public int getMaxGoldBottomUp(int[][] m) {
            int r = m.length; // row length
            if (r == 0) throw new IllegalArgumentException("empty matrix");
            int c = m[0].length; // col length
            
            // matrix to store intermediate results
            int[][] dp = new int[r][c];
            
            for (int i = 0; i < r; ++i) dp[i][c-1] = m[i][c-1];
            
            for (int j = c-2; j >= 0; --j) {
                for (int i = 0; i < r; ++i) {
                    int rightUp = (i == 0) ? 0 : dp[i-1][j+1];
                    int right = dp[i][j+1];
                    int rightDown = (i == r-1) ? 0 : dp[i+1][j+1];
                    
                    dp[i][j] = m[i][j] + max(rightUp, max(right, rightDown));
                }
            }
            
            int maxGold = 0;
            for (int i = 0; i < r; ++i)
                maxGold = max(maxGold, dp[i][0]);
            
            return maxGold;
        }
        
        public String getMaxGoldBottomUpWithPath(int[][] m) {
            int r = m.length; // row length
            if (r == 0) throw new IllegalArgumentException("empty matrix");
            int c = m[0].length; // col length
            
            // matrix to store intermediate results
            int[][] dp = new int[r][c];
            int[][] next = new int[r][c];
            
            for (int i = 0; i < r; ++i) {
                dp[i][c-1] = m[i][c-1];
                next[i][c-1] = -1; // to indicate end of path
            }
            
            for (int j = c-2; j >= 0; --j) {
                for (int i = 0; i < r; ++i) {
                    int rightUp = (i == 0) ? 0 : dp[i-1][j+1];
                    int right = dp[i][j+1];
                    int rightDown = (i == r-1) ? 0 : dp[i+1][j+1];
                    
                    int nextMax = max(rightUp, max(right, rightDown));
                    dp[i][j] = m[i][j] + nextMax;
                    
                    if (nextMax == rightUp && i != 0) next[i][j] = i-1;
                    else if (nextMax == right) next[i][j] = i;
                    else if (nextMax == rightDown && i != r-1) next[i][j] = i+1;
                }
            }
            
            int maxGold = 0, startRow = 0;
            for (int i = 0; i < r; ++i) {
                if (dp[i][0] > maxGold) {
                    maxGold = dp[i][0];
                    startRow = i;
                }
            }
            
            String path = reconstructPath(next, startRow);
            return String.format("maxGold: %d, Path: %s", maxGold, path);
        }
        
        private String reconstructPath(int[][] next, int startRow) {
            StringJoiner joiner = new StringJoiner(" -> ");
            int row = startRow;
            int col = 0;
            
            while (row != -1) {
                joiner.add(String.format("(%d, %d)", row, col));
                row = next[row][col];
                col++;
            }
            
            return joiner.toString();
        }
    }
    
    public static void main(String[] args) {
        GoldMineProblem solver = new GoldMineProblem();
        int[][] goldMine = {
                             {1, 3, 1, 5}, 
                             {2, 2, 4, 1}, 
                             {5, 0, 2, 3}, 
                             {0, 6, 1, 2}
                           };
        
        System.out.println(solver.new BruteForce().maxGold(goldMine)); // 16
        System.out.println(solver.new DPSolution().maxGoldMemoized(goldMine)); // 16
        System.out.println(solver.new DPSolution().getMaxGoldBottomUp(goldMine)); // 16
        
        System.out.println(solver.new DPSolution().getMaxGoldBottomUpWithPath(goldMine)); 
        // maxGold: 16, Path: (2, 0) -> (1, 1) -> (1, 2) -> (0, 3)
    }

}
