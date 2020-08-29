package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/

public class LongestIncreasingSubsequence {
    
    // Idea:
    public int lis(int[] a) {
        return lisUtil(a, a.length-1);
        // here we'll have to get maximum starting with every index
    }
    
    public int lisUtil(int[] a, int i) {
        if (i == 0) 
            return 1;
        
        int longest = 1; // every single element is length 1 LIS to begin with
        for (int j = i-1; j >= 0; --j) {
            if (a[i] > a[j]) 
                longest = max(longest, 1 + lisUtil(a, j));
        }
        return longest;
    }
    
    // dp bottom-up
    // T(n): O(n^2)
    public int lisDp(int[] a) {
        int n = a.length;
        
        // dp[i] stores LIS in a[0...i-1]
        int[] dp = new int[n];
        
        dp[0] = 1;
        for (int i = 1; i < n; ++i) {
            dp[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                if (a[i] > a[j])
                    dp[i] = max(dp[i], 1 + dp[j]);
            }
        }
        
        // get max length starting with every index
        int longest = dp[0];
        for (int i = 1; i < n; ++i)
            longest = max(longest, dp[i]); 
        return longest;
    }
    
    // Optimization: find max of all within same loop
    public int lisDp2(int[] a) {
        int n = a.length;
        
        // dp[i] stores LIS in a[0...i-1]
        int[] dp = new int[n];
        
        dp[0] = 1;
        for (int i = 1; i < n; ++i) {
            dp[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                // if this number can be included
                if (a[i] > a[j])
                    dp[i] = max(dp[i], 1 + dp[j]);
                
                // without including current number
                dp[i] = max(dp[i], dp[j]);
            }
        }
        
        return dp[n-1];
    }
    
    /*
     * NOTE:
     * This problem has a O(nlgn) solution discussed at:
     * https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     */
    
    public static void main(String[] args) {
        int[] a = {10, 22, 9, 33, 21, 50, 41, 60, 10};
        LongestIncreasingSubsequence obj = new LongestIncreasingSubsequence();
        System.out.println(obj.lisDp(a)); // 5
    }
}
