package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-path-sum-starting-cell-0-th-row-ending-cell-n-1-th-row/

// This problem is similar to gold mine problem

public class MaxPathSum {

    // T(n) = O(RC)
    public int getMaxPathSumBottomUp(int[][] m) {
        int R = m.length;
        if (R == 0) throw new IllegalArgumentException("empty matrix");
        int C = m[0].length;
        
        int[][] dp = new int[R][C];
        for (int j = 0; j < C; ++j) dp[R-1][j] = m[R-1][j];
        
        for (int i = R-2; i >= 0; --i) {
            for (int j = 0; j < C; ++j) {
                int ld = (j > 0) ? dp[i+1][j-1] : 0; // left down
                int d = dp[i+1][j]; // down
                int rd = (j < C-1) ? dp[i+1][j+1] : 0; // right down
                
                dp[i][j] = m[i][j] + Math.max(ld, Math.max(d, rd));
            }
        }
        
        int maxSum = 0;
        for (int j = 0; j < C; ++j)
            maxSum = Math.max(maxSum, dp[0][j]);
        
        return maxSum;
    }
    
    public static void main(String[] args) {
        int[][] mat = {
                        { 4, 2, 3, 4 }, 
                        { 2, 9, 1, 10 }, 
                        { 15, 1, 3, 0 }, 
                        { 16, 92, 41, 44 }
                      };
        
        System.out.println(new MaxPathSum().getMaxPathSumBottomUp(mat)); // 120
    }
}
