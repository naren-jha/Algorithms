package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/path-maximum-average-value/

public class PathWithMaximumAverageValue {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public double maxAvgPathVal(int[][] a) {
            int n = a.length;
            return (double) maxAvgPathValUtil(a, n-1, n-1) / (2*n - 1);
        }
        
        private int maxAvgPathValUtil(int[][] a, int row, int col) {
            if (row < 0 || col < 0)
                return 0;
            if (row == 0 && col == 0)
                return a[0][0];
            return Math.max(maxAvgPathValUtil(a, row-1, col),
                    maxAvgPathValUtil(a, row, col-1)) + a[row][col];
        }
    }
    
    class DPSolution {
        // Top-down memoization
        // T(n): O(n^2), S(n): O(n^2)
        public double maxAvgPathValMemoized(int[][] a) {
            int n = a.length;
            int[][] res = new int[n][n];
            for (int i = 0; i < n; i++)
                Arrays.fill(res[i], -1);
            return (double) maxAvgPathValMemUtil(a, n-1, n-1, res) / (2*n - 1);
        }
        
        private int maxAvgPathValMemUtil(int[][] a, int row, int col, int[][] res) {
            if (row < 0 || col < 0)
                return 0;
            if (row == 0 && col == 0)
                return a[0][0];
            
            if (res[row][col] != -1)
                return res[row][col];
            res[row][col] = Math.max(maxAvgPathValMemUtil(a, row-1, col, res),
                    maxAvgPathValMemUtil(a, row, col-1, res)) + a[row][col];
            return res[row][col];
        }
        
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n^2)
        public double maxAvgPathVal(int[][] a) {
            int n = a.length;
            int[][] dp = new int[n][n];
            
            dp[0][0] = a[0][0];
            for (int i = 1; i < n; i++)
                dp[i][0] = dp[i-1][0] + a[i][0];
            for (int j = 1; j < n; j++)
                dp[0][j] = dp[0][j-1] + a[0][j];
            
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = max(dp[i-1][j], dp[i][j-1]) + a[i][j];
                }
            }
            return (double) dp[n-1][n-1] / (2*n - 1);
        }
    }
    
    public static void main(String[] args) {
        int[][] a = {
                        {1, 2, 3}, 
                        {6, 5, 4}, 
                        {7, 3, 9}
                    };
        PathWithMaximumAverageValue o = new PathWithMaximumAverageValue();
        System.out.println(o.new SimpleRecursiveSolution().maxAvgPathVal(a)); // 5.2
        System.out.println(o.new DPSolution().maxAvgPathValMemoized(a)); // 5.2
        System.out.println(o.new DPSolution().maxAvgPathVal(a)); // 5.2
    }
}
