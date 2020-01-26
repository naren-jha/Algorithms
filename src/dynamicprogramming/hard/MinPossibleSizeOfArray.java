package dynamicprogramming.hard;

import java.util.Arrays;

// https://www.geeksforgeeks.org/find-minimum-possible-size-of-array-with-given-rules-for-removal/
// https://code.google.com/codejam/contest/4214486/dashboard#s=p2

public class MinPossibleSizeOfArray {
    
    static int[][] dp;
    
    public static int minSize(int[] a, int k) {
        int n = a.length;
        dp = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], -1);
        
        return minSizeUtil(a, 0, n-1, k);
    }
    
    private static int minSizeUtil(int[] a, int lo, int hi, int k) {
        if (hi-lo+1 < 3)
            return hi-lo+1;
        
        if (dp[lo][hi] != -1)
            return dp[lo][hi];
        
        // when first element is not removed
        int res = 1 + minSizeUtil(a, lo+1, hi, k);
        
        // when first element can be removed as part of a triplet
        for (int x = lo+1; x <= hi-1; x++) {
            for (int y = x+1; y <= hi; y++) {
                if (a[x] - a[lo] == k && a[y] - a[x] == k && minSizeUtil(a, lo+1, x-1, k) == 0
                            && minSizeUtil(a, x+1, y-1, k) == 0)
                    res = Math.min(res, minSizeUtil(a, y+1, hi, k));
            }
        }
        return dp[lo][hi] = res;
    }
    
    /*    _______________________________
     * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|
     * lo           x       y         hi
     */

    public static void main(String[] args) {
        int[] a = {2, 3, 4, 5, 6, 4};
        int k = 1;
        System.out.println(minSize(a, k)); // 0
    }
}
