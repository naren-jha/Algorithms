package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/path-maximum-average-value/

public class PathWithMaximumAverageValue {

    class SimpleRecursiveSolution {
        // TC: O(2^(r+c)), SC: O(r+c)
        public double maxAvg(int[][] a) {
            int n = a.length;
            return (double) maxSum(a, n, n) / (2*n - 1);
        }
        
        private int maxSum(int[][] a, int r, int c) {
            if (r == 0 || c == 0) return 0;
            
            return Math.max(maxSum(a, r-1, c), maxSum(a, r, c-1)) + a[r-1][c-1];
        }
    }
    
    class DPSolution {
        // Top-down memoization
        // TC: O(n^2), SC: O(n^2)
        public double maxAvgMemoized(int[][] a) {
            int n = a.length;
            int[][] mem = new int[n+1][n+1];
            for (int i = 0; i <= n; i++)
                Arrays.fill(mem[i], -1);
            
            return (double) maxSumMem(a, n, n, mem) / (2*n - 1);
        }
        
        private int maxSumMem(int[][] a, int r, int c, int[][] mem) {
            if (mem[r][c] != -1) return mem[r][c];
            if (r == 0 || c == 0) return 0;
            
            return Math.max(maxSumMem(a, r-1, c, mem), maxSumMem(a, r, c-1, mem)) + a[r-1][c-1];
        }
        
        // Bottom-up tabulation
        // TC: O(n^2), SC: O(n^2)
        public double maxAvgPathVal(int[][] a) {
            int n = a.length;
            int[][] dp = new int[n+1][n+1];
            
            for (int i = 0; i <= n; i++)
                dp[i][0] = 0;
            for (int j = 0; j <= n; j++)
                dp[0][j] = 0;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + a[i-1][j-1];
                }
            }
            
            return (double) dp[n][n] / (2*n - 1);
        }
    }
    
    public static void main(String[] args) {
        int[][] a = {
                        {1, 2, 3}, 
                        {6, 5, 4}, 
                        {7, 3, 9}
                    };
        PathWithMaximumAverageValue o = new PathWithMaximumAverageValue();
        System.out.println(o.new SimpleRecursiveSolution().maxAvg(a)); // 5.2
        System.out.println(o.new DPSolution().maxAvgMemoized(a)); // 5.2
        System.out.println(o.new DPSolution().maxAvgPathVal(a)); // 5.2
    }
}
