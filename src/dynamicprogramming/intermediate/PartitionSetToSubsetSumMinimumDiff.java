package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum/

public class PartitionSetToSubsetSumMinimumDiff {

    int totalSum = 0;
    int[][] dp;
    
    public int findMin(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++)
            totalSum += a[i];
        
        dp = new int[n+1][totalSum+1];
        for (int i = 0; i <= n; i++)
            Arrays.fill(dp[i], -1);
        
        return findMinUtil(a, n, 0);
    }

    private int findMinUtil(int[] a, int i, int subset1Sum) {
        // Base case: When all elements are processed
        if (i == 0) {
            int subset2Sum = totalSum - subset1Sum;
            return Math.abs(subset1Sum - subset2Sum);
        }
        
        if (dp[i][subset1Sum] != -1)
            return dp[i][subset1Sum];
        
        /* For every element in given array, we have two choices:
         * 1. We include it in subset1
         * 2. We do not include it in subset1 (that means it gets included in subset2)
         */
        return dp[i][subset1Sum] = Math.min(findMinUtil(a, i-1, subset1Sum + a[i-1]), 
                                            findMinUtil(a, i-1, subset1Sum));
    }
    
    public static void main(String[] args) {
        PartitionSetToSubsetSumMinimumDiff o = new PartitionSetToSubsetSumMinimumDiff();
        int[] a = {3, 1, 4, 2, 2, 1};
        System.out.println(o.findMin(a)); // 1
    }
}
