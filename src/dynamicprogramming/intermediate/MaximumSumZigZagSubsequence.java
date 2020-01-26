package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-sum-alternating-subsequence-sum/

public class MaximumSumZigZagSubsequence {

    // This problem is an extension of MaximumSumIncreasingSubsequence problem
    // and is similar to LongestZigZagSubsequence problem
    
    // Bottom-up tabulation
    // T(n): O(n^2), S(n): O(n)
    public int maxSum(int[] a) {
        int n = a.length;
        
        /* Create an array to store intermediate results
         * z[i][0] = Maximum sum of the Zig-Zag subsequence ending at 
         * index i when last element is greater than its previous element 
         * 
         * z[i][1] = Maximum sum of the Zig-Zag subsequence ending at 
         * index i when last element is smaller than its previous element   
        */
        int[][] z = new int[n][2];
        
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            z[i][0] = a[i]; z[i][1] = a[i];
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i])
                    if (z[i][0] < z[j][1] + a[i])
                        z[i][0] = z[j][1] + a[i];
                
                if (a[j] > a[i])
                    if (z[i][1] < z[j][0] + a[i])
                        z[i][1] = z[j][0] + a[i];
            }
            if (res < Math.max(z[i][0], z[i][1]))
                res = Math.max(z[i][0], z[i][1]);
        }
        return res;
    }
    
    public static void main(String[] args) {
        MaximumSumZigZagSubsequence zigZag = new MaximumSumZigZagSubsequence();
        int[] a = {8, 2, 3, 5, 7, 9, 10};
        System.out.println(zigZag.maxSum(a)); // 25
    }
}
