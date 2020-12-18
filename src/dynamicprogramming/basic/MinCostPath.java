package dynamicprogramming.basic;

import static java.lang.Math.min;

// https://www.geeksforgeeks.org/min-cost-path-dp-6/

public class MinCostPath {

    class SimpleRecursiveSolution {  
        // TC: O(3^(m+n)), SC: O(m+n)
        public int minCost(int[][] mat, int m, int n) {
            if (m == 0 || n == 0)
                return Integer.MAX_VALUE;
            
            int cost = min(minCost(mat, m-1, n-1), 
                    min(minCost(mat, m-1, n), minCost(mat, m, n-1)));
            
            if (cost != Integer.MAX_VALUE) cost += mat[m-1][n-1];
            else cost = mat[m-1][n-1];
            
            return cost;
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // TC: O(mn), SC: O(mn)
        public int minCost(int[][] mat) {
            int m = mat.length;
            if (m == 0) throw new IllegalArgumentException("empty matrix!");
            int n = mat[0].length;
            
            int[][] dp = new int[m+1][n+1];
            dp[0][0] = mat[0][0];
            
            for (int i = 0; i <= m; i++)
                dp[i][0] = Integer.MAX_VALUE;
            for (int j = 0; j <= n; j++)
                dp[0][j] = Integer.MAX_VALUE;
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    int cost = min(dp[i-1][j-1], min(dp[i-1][j], dp[i][j-1]));
                    
                    if (cost != Integer.MAX_VALUE) cost += mat[i-1][j-1];
                    else cost = mat[i-1][j-1];
                    
                    dp[i][j] = cost;
                }
            }
            
            return dp[m][n];
        }
    }
    
    public static void main(String[] args) {
        MinCostPath o = new MinCostPath();
        int[][] cost = {
                        {1, 2, 3}, 
                        {4, 8, 2}, 
                        {1, 5, 3}
                       };
        System.out.println(o.new SimpleRecursiveSolution().minCost(cost, 3, 3)); // 8
        System.out.println(o.new DPSolution().minCost(cost)); // 8
    }
}