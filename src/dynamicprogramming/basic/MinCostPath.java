package dynamicprogramming.basic;

import static java.lang.Math.min;

// https://www.geeksforgeeks.org/min-cost-path-dp-6/

public class MinCostPath {

    class SimpleRecursiveSolution {        
        public int minCost(int[][] cost, int m, int n) {
            if (m < 0 || n < 0)
                return Integer.MAX_VALUE;
            
            if (m == 0 && n == 0)
                return cost[0][0];
            
            return min(minCost(cost, m-1, n), 
                    min(minCost(cost, m-1, n-1), minCost(cost, m, n-1))) 
                    + cost[m][n];
        }
    }
    
    class DPSolution {
        // bottom-up
        public int minCost(int[][] cost) {
            int m = cost.length;
            int n = cost[0].length;
            
            int[][] dp = new int[m][n];
            dp[0][0] = cost[0][0];
            
            // initialize first column
            for (int i = 1; i < m; i++)
                dp[i][0] = dp[i-1][0] + cost[i][0];
            
            // initialize first row
            for (int j = 1; j < n; j++)
                dp[0][j] = dp[0][j-1] + cost[0][j];
            
            // construct rest of the res[]
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = min(dp[i-1][j], min(dp[i-1][j-1], dp[i][j-1])) + cost[i][j];
                }
            }
            return dp[m-1][n-1];
        }
    }
    
    public static void main(String[] args) {
        MinCostPath o = new MinCostPath();
        int[][] cost = {
                        {1, 2, 3}, 
                        {4, 8, 2}, 
                        {1, 5, 3}
                       };
        System.out.println(o.new SimpleRecursiveSolution().minCost(cost, 2, 2)); // 8
        System.out.println(o.new DPSolution().minCost(cost)); // 8
    }
}