package dynamicprogramming.basic;

import java.util.Arrays;

// https://leetcode.com/problems/coin-change/

public class ChangeMakingProblem {

    // Recurrence:
    int f(int[] coins, int amount, int i) {
        if (amount == 0) return 0;

        if (i == 0) {
            if (amount >= coins[i])
                return 1 + f(coins, amount-coins[i], i);
            else return -1;
        }
        
        int minCount = f(coins, amount, i-1);
        if (amount >= coins[i])
            minCount = Math.min(minCount, 1 + f(coins, amount-coins[i], i));
        return minCount;
    }
    
    // DP Bottom-Up, T(n): O(n*amount), S(n): O(n*amount)
    public int coinChange(int[] coins, int amount) {        
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        
        for (int i = 0; i < n; ++i) // amount == 0
            dp[i][0] = 0;
        
        // if amount is 5 then max valid count will be 1+1+1+1+1 = 5, so we take one more than that
        int maxVal = amount+1;
        
        for (int j = 1; j <= amount; ++j) {// i == 0
            if (j >= coins[0] && dp[0][j-coins[0]] != maxVal)
                dp[0][j] = 1 + dp[0][j-coins[0]];
            else
                dp[0][j] = maxVal;
        }
            
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j <= amount; ++j) {
                dp[i][j] = dp[i-1][j];
                if (j >= coins[i])
                    dp[i][j] = Math.min(dp[i][j], 1 + dp[i][j-coins[i]]);
            }
        }
        
        return dp[n-1][amount] != maxVal ? dp[n-1][amount] : -1;
    }
    
    // Space Optimization, T(n): O(n*amount), S(n): O(amount)
    public int coinChange2(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        
        dp[0] = 0; // amount == 0
            
        // if amount is 5 then max valid count will be 1+1+1+1+1 = 5, so we take one more than that
        int maxVal = amount+1; 
        
        for (int j = 1; j <= amount; ++j) {// i == 0
            if (j >= coins[0] && dp[j-coins[0]] != maxVal)
                dp[j] = 1 + dp[j-coins[0]];
            else
                dp[j] = maxVal;
        }
            
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j <= amount; ++j) {
                if (j >= coins[i])
                    dp[j] = Math.min(dp[j], 1 + dp[j-coins[i]]);
            }
        }
        
        return dp[amount] != maxVal ? dp[amount] : -1;
    }
    
    // Further refactoring
    public int coinChange3(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount+1];
        int maxVal = amount+1;
        
        Arrays.fill(dp, maxVal);
        dp[0] = 0; // amount == 0
            
        for (int i = 0; i < n; ++i) {
            for (int j = 1; j <= amount; ++j) {
                if (j >= coins[i])
                    dp[j] = Math.min(dp[j], 1 + dp[j-coins[i]]);
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    public static void main(String[] args) {
        ChangeMakingProblem solver = new ChangeMakingProblem();
        int[] coins = {1,2,5};
        int amount = 11;
        
        System.out.println(solver.coinChange(coins, amount)); // 3
        System.out.println(solver.coinChange2(coins, amount)); // 3
        System.out.println(solver.coinChange3(coins, amount)); // 3
    }
}
