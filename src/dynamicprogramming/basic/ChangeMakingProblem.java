package dynamicprogramming.basic;

// https://leetcode.com/problems/coin-change/

public class ChangeMakingProblem {

    // Recurrence:
    int f(int[] coins, int amount, int n) {
        if (amount == 0) return 0;
        if (n == 0) return Integer.MAX_VALUE;
        
        int minCount = f(coins, amount, n-1); // without considering last coin
        
        if (amount >= coins[n-1]) {
            int consideringLastCoin = f(coins, amount-coins[n-1], n); // we can consider same coin multiple times
            if (consideringLastCoin != Integer.MAX_VALUE)
                consideringLastCoin += 1; // since we considered this coin, add 1
            minCount = Math.min(minCount, consideringLastCoin);
        }
        
        return minCount;
    }
    
    // DP Bottom-Up, T(n): O(n*amount), S(n): O(n*amount)
    public int coinChange(int[] coins, int amount) {        
        int n = coins.length;
        int[][] dp = new int[n+1][amount+1];
        
        for (int i = 0; i <= n; ++i) // amount == 0
            dp[i][0] = 0;
        
        for (int j = 1; j <= amount; ++j) // n == 0
            dp[0][j] = Integer.MAX_VALUE;
            
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= amount; ++j) {
                dp[i][j] = dp[i-1][j]; // without considering
                
                if (j >= coins[i-1]) {
                    int consideringLastCoin = dp[i][j-coins[i-1]];
                    if (consideringLastCoin != Integer.MAX_VALUE)
                        consideringLastCoin += 1;
                    dp[i][j] = Math.min(dp[i][j], consideringLastCoin);
                }
            }
        }
        
        return dp[n][amount] != Integer.MAX_VALUE ? dp[n][amount] : -1;
    }
    
    // Space Optimization, T(n): O(n*amount), S(n): O(amount)
    public int coinChangeSO(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        
        dp[0] = 0; // amount == 0
        for (int j = 1; j <= amount; ++j) // n == 0
            dp[j] = Integer.MAX_VALUE;
            
        for (int i = 1; i <= n; ++i) {
            for (int j = coins[i-1]; j <= amount; ++j) {
                int consideringLastCoin = dp[j-coins[i-1]];
                if (consideringLastCoin != Integer.MAX_VALUE)
                    consideringLastCoin += 1;
                dp[j] = Math.min(dp[j], consideringLastCoin);
            }
        }
        
        return dp[amount] != Integer.MAX_VALUE ? dp[amount] : -1;
    }
    
    public static void main(String[] args) {
        ChangeMakingProblem solver = new ChangeMakingProblem();
        int[] coins = {1,2,5};
        int amount = 11;
        
        System.out.println(solver.coinChange(coins, amount)); // 3
        System.out.println(solver.coinChangeSO(coins, amount)); // 3
        
        coins = new int[]{4,5};
        System.out.println(solver.coinChange(coins, amount)); // -1
        System.out.println(solver.coinChangeSO(coins, amount)); // -1
    }
}
