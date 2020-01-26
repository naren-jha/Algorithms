package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/maximum-sum-increasing-subsequence-dp-14/

public class MaximumSumIncreasingSubsequence {

    /*
     * This problem is a variation of standard Longest 
     * Increasing Subsequence (LIS) problem. We need a 
     * slight change in the Dynamic Programming solution 
     * of LIS problem. All we need to change is to use sum 
     * as a criteria instead of length of increasing subsequence.
     */
    
    // T(n): O(n^2), S(n): O(n)
    public int maxSumIS(int[] a) {
        int n = a.length;
        int[] msis = new int[n];
        
        msis[0] = a[0];
        for (int i = 1; i < n; i++) {
            msis[i] = a[i];
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) {
                    if (msis[i] < msis[j] + a[i]) {
                        msis[i] = msis[j] + a[i];
                    }
                }
            }
        }
        
        // get maximum from msis[] array
        return Arrays.stream(msis).max().getAsInt();
    }
    
    public static void main(String[] args) {
        int[] a = {1, 101, 2, 3, 100, 4, 5};
        MaximumSumIncreasingSubsequence obj = new MaximumSumIncreasingSubsequence();
        System.out.println(obj.maxSumIS(a)); // 106
    }
}
