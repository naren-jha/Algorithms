package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/coin-change-dp-7/
// https://www.youtube.com/watch?v=jaNZ83Q3QGc

public class CoinChangeProblem {

    private class SimpleRecursiveSolution {
        // T(n) = Exp
        public int coinChange(int[] coins, int n, int sum) {
            if (sum == 0)
                return 1;
            if (n == 0)
                return 0;
            
            int count = coinChange(coins, n-1, sum); // without considering last coin
            
            if (sum >= coins[n-1])
                count += coinChange(coins, n, sum-coins[n-1]); // by considering last coin
            
            return count;
        }
    }
    
    private class DPSolution {
        // top-to-bottom memoization
        // T(n) = O(n*sum), S(n) = O(n*sum)
        public int coinChangeMemoized(int[] coins, int n, int sum, int[][] res) {
            if (sum == 0)
                return 1;
            if (n == 0)
                return 0;
            
            if (res[n][sum] != -1)
                return res[n][sum];
            
            res[n][sum] = coinChangeMemoized(coins, n-1, sum, res);
            
            if (sum >= coins[n-1])
                res[n][sum] += coinChangeMemoized(coins, n, sum-coins[n-1], res);
            
            return res[n][sum];
        }
        
        // bottom-up tabulation
        // T(n) = O(n*sum), S(n) = O(n*sum)
        public int coinChangeBottomUp(int[] coins, int sum) {
            int n = coins.length;
            int[][] dp = new int[n+1][sum+1];
            
            // when n == 0
            Arrays.fill(dp[0], 0); // redundant in java
            
            // when sum == 0
            for (int i = 0; i <= n; i++)
                dp[i][0] = 1; 
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= sum; j++) {
                    dp[i][j] = dp[i-1][j];
                    if (j >= coins[i-1])
                        dp[i][j] += dp[i][j-coins[i-1]];
                }
            }
            return dp[n][sum];
        }
        
        /*
         * For bottom up tabulation method, in every iteration of inner for loop,
         * since we only need the elements on/before the current element, we can
         * solve this in O(sum) space. See solution below.
         */
        // bottom-up tabulation, space optimized
        // T(n) = O(n*sum), S(n) = O(sum)
        // Advantage: better space complexity, cleaner solution.
        public int coinChange(int[] coins, int sum) {
            int[] res = new int[sum+1];
            Arrays.fill(res, 0); // redundant in java
            res[0] = 1; // when sum == 0
            
            for (int i = 1; i <= coins.length; i++) {
                for (int j = coins[i-1]; j <= sum; j++) {
                    res[j] += res[j-coins[i-1]];
                }
            }
            return res[sum];
        }
    }
    
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int n = coins.length;
        int sum = 12;
        
        CoinChangeProblem obj = new CoinChangeProblem();
        System.out.println(obj.new SimpleRecursiveSolution().coinChange(coins, n, sum)); // 13
        
        int[][] res = new int[n+1][sum+1]; // (n+1) x (sum+1)
        for (int[] r : res)
            Arrays.fill(r, -1);
        System.out.println(obj.new DPSolution().coinChangeMemoized(coins, n, sum, res)); // 13
        
        System.out.println(obj.new DPSolution().coinChangeBottomUp(coins, sum)); // 13
        
        System.out.println(obj.new DPSolution().coinChange(coins, sum)); // 13
    }

}
