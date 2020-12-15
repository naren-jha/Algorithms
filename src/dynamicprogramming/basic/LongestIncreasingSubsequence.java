package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/

public class LongestIncreasingSubsequence {
    
    // Idea:
    // TC: O(n^n) SC: O(n)
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
    // TC: O(n^2) SC: O(n)
    public int lisMem(int[] a) {
        int n = a.length;
        // mem[i] stores LIS in a[0...i-1]
        int[] mem = new int[n];
        Arrays.fill(mem, -1);
        
        lisUtil(a, n-1, mem);
        
        // we'll have to get maximum starting from every index
        return Arrays.stream(mem).max().getAsInt();
    }
    
    private int lisUtil(int[] a, int i, int[] mem) {
        if (mem[i] != -1) return mem[i];
        
        if (i == 0) return mem[i] = 1;
        
        mem[i] = 1; // every single element is length 1 LIS to begin with
        for (int j = i-1; j >= 0; --j) {
            int lisFromJ = lisUtil(a, j, mem);
            if (a[i] > a[j]) 
                mem[i] = max(mem[i], 1 + lisFromJ);
        }
        return mem[i];
    }
    
    // dp bottom-up tabulation
    // TC: O(n^2) SC: O(n)
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
    // TC: O(n^2) SC: O(n)
    public int lisDp2(int[] a) {
        int n = a.length;
        
        // dp[i] stores LIS in a[0...i-1]
        int[] dp = new int[n];
        
        dp[0] = 1;
        int lis = dp[0];
        for (int i = 1; i < n; ++i) {
            dp[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                // if this number can be included
                if (a[i] > a[j])
                    dp[i] = max(dp[i], 1 + dp[j]);
            }
            
            lis = max(lis, dp[i]);
        }
        
        return lis;
    }
    
    /*
     * O(nlgn) time solution using binary search
     * https://stackoverflow.com/questions/2631726/how-to-determine-the-longest-increasing-subsequence-using-dynamic-programming
     * https://algorithmsandme.com/longest-increasing-subsequence-in-onlogn/
     * https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     */
    public int lenOfLIS(int[] nums) {
        int n = nums.length;
        int[] seq = new int[n];
        
        int len = 0;
        for (int num : nums) {
            int lo = 0, hi = len;
            while (lo != hi) {
                int mid = (lo + hi) / 2;
                if (num > seq[mid]) lo = mid + 1;
                else hi = mid;
            }
            
            seq[hi] = num;
            if (hi == len) len++;
        }
        return len;
    }
    
    // using Java library binary search
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] seq = new int[n];
        
        int len = 0;
        for (int num : nums) {
            int ip = Arrays.binarySearch(seq, 0, len, num);
            if (ip < 0) ip = -ip-1; // since binary search returns (-(insertion point) - 1) if the key is not present
            
            seq[ip] = num;
            if (ip == len) len++;
        }
        
        return len;
    }
    
    /*
     * When current num is bigger than the last entry in seq[] (i.e., seq[len-1]),
     * then after binary search, we will have ip = len, so we can directly insert current 
     * num at location len in seq[] array and then increase the len.
     * 
     * But when current num is smaller than the last entry in seq[], then we get ip = a value 
     * which is < len (0 <= ip <= len-1), so then instead of ignoring this smaller number, 
     * we replace existing number at that spot with current num.
     * The reason we replace an existing number with a smaller number is "to get as larger LIS as possible"
     * 
     * For example, for the input array [10, 2, 5, 7]
     * 
     * step 1:
     * num = 10
     * ip = 0
     * seq[0] = 10
     * len = 1
     * 
     * step 2:
     * num = 2
     * ip = 0
     * seq[0] = 2 (we replace 10 by 2)
     * len = 1
     * 
     * step 3:
     * num = 5
     * ip = 1
     * seq[1] = 5 
     * seq = [2, 5]
     * len = 2
     * 
     * Observe that if we did not replace 10 by 2 (at step 2), then at next step for number 5
     * we'll again get ip as 0, and we'll not be able to insert 5 at next location and thus we'll 
     * get len of LIS as still 1, which is incorrect as it should have been 2.
     */
    
    public static void main(String[] args) {
        int[] a = {10, 22, 9, 33, 21, 50, 41, 60, 10};
        LongestIncreasingSubsequence solver = new LongestIncreasingSubsequence();
        System.out.println(solver.lisMem(a)); // 5
        System.out.println(solver.lisDp(a)); // 5
        System.out.println(solver.lisDp2(a)); // 5
        System.out.println(solver.lengthOfLIS(a)); // 5
        System.out.println(solver.lenOfLIS(a)); // 5
    }
}
