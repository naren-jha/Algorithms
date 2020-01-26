package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/min-cost-path-dp-6/

public class MinCostPath {

    class SimpleRecursiveSolution {        
        public int minCost(int[][] cost, int m, int n) {
            if (m < 0 || n < 0)
                return Integer.MAX_VALUE;
            
            if (m == 0 && n == 0)
                return cost[0][0];
            
            return Math.min(minCost(cost, m-1, n), 
                    Math.min(minCost(cost, m-1, n-1), minCost(cost, m, n-1)))
                    + cost[m][n];
        }
    }
    
    class DPSolution {
        // bottom-up
        public int minCost(int[][] cost, int m, int n) {
            int[][] res = new int[m+1][n+1];
            
            res[0][0] = cost[0][0];
            
            // initialize first column
            for (int i = 1; i <= m; i++)
                res[i][0] = res[i-1][0] + cost[i][0];
            
            // initialize first row
            for (int j = 1; j <= n; j++)
                res[0][j] = res[0][j-1] + cost[0][j];
            
            // construct rest of the res[]
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    res[i][j] = Math.min(res[i-1][j], Math.min(res[i-1][j-1], res[i][j-1]))
                                + cost[i][j];
                }
            }
            return res[m][n];
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
        System.out.println(o.new DPSolution().minCost(cost, 2, 2)); // 8
    }
}