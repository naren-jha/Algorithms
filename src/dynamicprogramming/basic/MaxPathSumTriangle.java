package dynamicprogramming.basic;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/maximum-path-sum-triangle/
/*
 * Input : 
       3
      7 4
     2 4 6
    8 5 9 3
    
    Output : 23
    Explanation : 3 + 7 + 4 + 9 = 23 
 */
public class MaxPathSumTriangle {
    
    // August 2020
    int maxSumRec(int[][] t) {
        int r = t.length - 1;
        
        int maxSum = 0;
        for (int j = 0; j < t[r].length; ++j)
            maxSum = max(maxSum, max(maxSumUtil(t, r-1, j-1), maxSumUtil(t, r-1, j)) + t[r][j]);
            
        return maxSum;
    }

    int maxSumUtil(int[][] t, int r, int c) {
        if (r < 0 || c < 0) return 0;
        if (c >= t[r].length) return 0;
        
        return max(maxSumUtil(t, r-1, c-1), maxSumUtil(t, r-1, c)) + t[r][c];
    }
    
    // dp bottom-up
    int maxSum(int[][] t) {
        int r = t.length;
        int[][] dp = new int[r][];
        dp[0] = new int[1];
        dp[0][0] = t[0][0];
        
        for (int i = 1; i < r; ++i) {
            dp[i] = new int[t[i].length];
            for (int j = 0; j < t[i].length; ++j) {
                if (j == 0) dp[i][j] = dp[i-1][j] + t[i][j];
                else if (j == t[i].length - 1) dp[i][j] = dp[i-1][j-1] + t[i][j];
                else dp[i][j] = max(dp[i-1][j-1], dp[i-1][j]) + t[i][j];
            }
        }
        
        int maxSum = 0;
        for (int j = 0; j < dp[r-1].length; ++j)
            maxSum = max(maxSum, dp[r-1][j]);
            
        return maxSum;
    }
    // August 2020 - end
    
    // old
    
    public int maxPathSum(int[][] t) {
        int n = t.length;
        int[][] res = new int[n][];
        
        int col = t[n-1].length;
        res[n-1] = new int[col];
        // initialize last row
        for (int j = 0; j < t[n-1].length; j++)
            res[n-1][j] = t[n-1][j];
        
        for (int i = n-2; i >= 0; i--) {
            col = t[i].length;
            res[i] = new int[col];
            for (int j = 0; j < col; j++) {
                res[i][j] = Math.max(res[i+1][j], res[i+1][j+1]) + t[i][j];
            }
        }
        return res[0][0];
    }
    
    public int maxPathSumSpaceOptimized(int[][] t) {
        int n = t.length;
        int[] res = new int[n];
        
        // first, calculate for bottom most row
        for (int j = 0; j < t[n-1].length; j++)
            res[j] = t[n-1][j];
        
        // calculate for rest of the rows bottom-up
        for (int i = n-2; i >= 0; i--) {
            for (int j = 0; j < t[i].length; j++) {
                res[j] = Math.max(res[j], res[j+1]) + t[i][j];
            }
        }
        
        return res[0];
    }
    
    public static void main(String[] args) {
        int[][] t = {
                {3},
                {7, 4},
                {2, 4, 6},
                {8, 5, 9, 3}
        };
        
        MaxPathSumTriangle o = new MaxPathSumTriangle();
        System.out.println(o.maxPathSum(t)); // 23
        System.out.println(o.maxPathSumSpaceOptimized(t)); // 23
        
        System.out.println(o.maxSumRec(t)); // 23
        System.out.println(o.maxSum(t)); // 23
    }
}
