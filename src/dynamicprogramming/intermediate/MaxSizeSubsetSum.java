package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-size-subset-given-sum/

public class MaxSizeSubsetSum {
    
    // Dec 2020
    int f(int[] a, int n, int sum) {
        if (sum == 0) return 0;
        if (n == 0) return Integer.MIN_VALUE;
        
        int count = f(a, n-1, sum);
        if (sum >= a[n-1])
            count = Math.max(count, 1 + f(a, n-1, sum-a[n-1]));
        return count;
    }
    
    // T(n) = O(n*sum), S(n) = O(n*sum)
    int bottomUp(int[] a, int sum) {
        int n = a.length;
        int[][] dp = new int[n+1][sum+1];
        
        for (int i = 0; i <= n; ++i)
            dp[i][0] = 0;
        for (int j = 1; j <= sum; ++j)
            dp[0][j] = Integer.MIN_VALUE;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i-1][j];
                if (j >= a[i-1])
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i-1][j-a[i-1]]);
            }
        }
        
        return dp[n][sum];
    }
    
    // Old approach
    // This problem is mainly an extension of SubsetSumProblem
    
    // T(n) = O(n*sum), S(n) = O(n*sum)
    public int maxSizeSubsetSum(int[] a, int sum) {
        int n = a.length;
        boolean[][] dp = new boolean[n+1][sum+1];
        
        // another array to store max size subset sum
        int[][] count = new int[n+1][sum+1];
        
        // when sum == 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
            count[i][0] = 0;
        }
        
        // when (sum != 0 && n == 0)
        for (int j = 1; j <= sum; j++) {
            dp[0][j] = false; // redundant in Java
            count[0][j] = -1; // assign some -ve number
        }
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (a[i-1] > j) { // when last number cannot be included
                    dp[i][j] = dp[i-1][j];
                    count[i][j] = count[i-1][j];
                }
                else {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-a[i-1]];
                    if (dp[i][j])
                        count[i][j] = Math.max(count[i-1][j], 1 + count[i-1][j-a[i-1]]);
                }
            }
        }
        
        return count[n][sum];
    }
    
    public static void main(String[] args) {
        int[] a = {3, 6, 4, 12, 7, 2};
        int sum = 18;
        MaxSizeSubsetSum solver = new MaxSizeSubsetSum();
        System.out.println(solver.f(a, a.length, sum)); // 4
        System.out.println(solver.bottomUp(a, sum)); // 4
        
        System.out.println(solver.maxSizeSubsetSum(a, sum)); // 4
    }
}
