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
    
    // =======================================================================
    
    // dp top-down memoization
    public int lisMem(int[] a) {
        int n = a.length;
        // dp[i] stores LIS in a[0...i-1]
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        
        lisUtil(a, n-1, dp);
        
        // we'll have to get maximum starting from every index
        return Arrays.stream(dp).max().getAsInt();
    }
    
    private int lisUtil(int[] a, int i, int[] dp) {
        if (i == 0) 
            return dp[i] = 1;
        
        if (dp[i] != -1) return dp[i];
        
        dp[i] = 1; // every single element is length 1 LIS to begin with
        for (int j = i-1; j >= 0; --j) {
            int lisFromJ = lisUtil(a, j, dp);
            if (a[i] > a[j]) 
                dp[i] = max(dp[i], 1 + lisFromJ);
        }
        return dp[i];
    }
    
    // dp bottom-up tabulation
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
        int longest = 1;
        for (int i = 1; i < n; ++i) {
            dp[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                // if this number can be included
                if (a[i] > a[j])
                    dp[i] = max(dp[i], 1 + dp[j]);
            }
            
            longest = max(longest, dp[i]);
        }
        
        return longest;
    }
    
    /*
     * NOTE:
     * This problem has a O(nlgn) solution discussed at:
     * https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     */
    
    // An O(nlgn) time solution
    public int lengthOfLIS(int[] nums) { // O(nlogn)
        int n = nums.length;
        int[] seq = new int[n];
        
        int len = 0;
        for (int num : nums){
            int i = Arrays.binarySearch(seq, 0, len, num);
            if (i < 0) i = -i-1;
            
            seq[i] = num;
            if (i == len) len++;
        }
        
        return len;
    }
    //https://algorithmsandme.com/longest-increasing-subsequence-in-onlogn/
    /*
     * When we are trying to insert a bigger number than the last inserted number in seq[] (i.e., seq[len-1]),
     * then after binary search (and after manipulating index i), we get i = len, so we can directly insert new 
     * number at location len in seq[] array and increase the len and move to next number
     * 
     * But when the number we are trying to insert in seq[] is smaller than seq[len-1] then we get i = a value 
     * which is < len (0 <= i <= len-1), so then instead of ignoring this smaller number, we replace existing number
     * at that spot with this number.
     * The reason why we replace an existing number with a smaller number is "to get as larger LIS as possible"
     * 
     * For example, for the input array [10, 2, 5, 7]
     * 
     * step 1:
     * i = 0
     * seq[0] = 10
     * len = 1
     * 
     * step 2:
     * i = 0
     * seq[0] = 2 (we replace 10 by 2)
     * len = 1
     * 
     * step 3:
     * i = 1
     * seq[1] = 5 
     * seq = [2, 5]
     * len = 2
     * 
     * so if we observe here, at step 2, if we did not replace 10 by 2, then at next step for number 5
     * we would again get i as 0, and we would not be able to insert 5 at next location and thus we'll 
     * get len of LIS as still 1 which should have been 2.
     */
    
    public static void main(String[] args) {
        int[] a = {10, 22, 9, 33, 21, 50, 41, 60, 10};
        LongestIncreasingSubsequence obj = new LongestIncreasingSubsequence();
        System.out.println(obj.lisMem(a)); // 5
        System.out.println(obj.lisDp(a)); // 5
        System.out.println(obj.lisDp2(a)); // 5
        System.out.println(obj.lengthOfLIS(a)); // 5
    }
}
