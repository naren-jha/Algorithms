package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/different-ways-sum-n-using-numbers-greater-equal-m/

public class DiffWaysToSum {

    // This problem is a variation of coin change problem
    
    /*
     * The idea is that, since the sum has to be formed using numbers
     * >= m, the numbers that can be used to make sum are [m, m+1, m+2 ... n]
     * This is because if m is > n then a sum cannot be made
     */
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countWays(int n, int m) {
            if (n == 0) return 1;
            if (m > n) return 0;
            
            return countWays(n-m, m) + countWays(n, m+1);
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*(n-m)), S(n): O(mn)
        public int countWays(int n, int m) {
            int[][] dp = new int[n+1][n+1];
            //Arrays.fill(dp[0], 1);
            
            for (int i = 1; i <= n; i++) {
                dp[i][i] = 1;
                for (int j = i-1; j >= m; j--)
                    dp[i][j] = dp[i-j][j] + dp[i][j+1];
            }
            
            return dp[n][m];
        }
        
        // We should be able to space optimize above solution
    }
    
    public static void main(String[] args) {
        int m = 1;
        int n = 3;
        DiffWaysToSum o = new DiffWaysToSum();
        System.out.println(o.new SimpleRecursiveSolution().countWays(n, m));
        System.out.println(o.new DPSolution().countWays(n, m));
    }
}
