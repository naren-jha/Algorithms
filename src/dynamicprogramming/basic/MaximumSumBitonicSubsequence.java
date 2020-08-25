package dynamicprogramming.basic;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/maximum-sum-bi-tonic-sub-sequence/

public class MaximumSumBitonicSubsequence {

    /*
     * This problem is a variation of standard Longest Increasing Subsequence (LIS) problem  
     * and Longest Bitonic Subsequence problem.
     * 
     * We construct two arrays MSIS[] (Maximum Sum Increasing Subsequence) and MSDS[] 
     * (Maximum Sum Decreasing Subsequence) MSIS[i] stores maximum sum of Increasing subsequence 
     * ending with a[i] and MSDS[i] stores maximum sum of Decreasing subsequence beginning with a[i].  
     * Finally, we need to return the max of MSIS[i] + MSDS[i] – a[i]
     */
    
    // T(n): O(n^2), S(n): O(n)
    public int maxSumBS(int[] a) {
        int n = a.length;
        int[] msis = new int[n];
        int[] msds = new int[n];
        
        msis[0] = a[0];
        for (int i = 1; i < n; ++i) {
            msis[i] = a[i];
            for (int j = i-1; j >= 0; --j) {
                if (a[i] > a[j])
                    msis[i] = max(msis[i], msis[j] + a[i]);
            }
        }
        
        msds[n-1] = a[n-1];
        for (int i = n-2; i >= 0; --i) {
            msds[i] = a[i];
            for (int j = i+1; j < n; ++j) {
                if (a[i] > a[j]) {
                    msds[i] = max(msds[i], msds[j] + a[i]);
                }
            }
        }
        
        int msbs = msis[0] + msds[0] - a[0];
        for (int i = 1; i < n; i++)
            msbs = max(msbs, msis[i] + msds[i] - a[i]);
        
        return msbs;
    }
    
    public static void main(String[] args) {
        int[] a = { 1, 15, 51, 45, 33, 100, 12, 18, 9 };
        MaximumSumBitonicSubsequence obj = new MaximumSumBitonicSubsequence();
        System.out.println(obj.maxSumBS(a)); // 194
        
    }
}
