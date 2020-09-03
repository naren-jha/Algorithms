package dynamicprogramming.intermediate;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-zig-zag-subsequence/
// https://www.geeksforgeeks.org/longest-alternating-subsequence/
// https://leetcode.com/problems/wiggle-subsequence/

public class LongestZigZagSubsequence {

    // This problem is similar to LIS problem
    
    // Aug 2020
    public int lzzs(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        
        int[] dp1 = new int[n]; // dp1[i] -> longest zigzag subsequence at index 'i' such that a[i] > a[j] (previous element in subsequence)
        int[] dp2 = new int[n]; // dp1[i] -> longest zigzag subsequence at index 'i' such that a[i] < a[j]
        
        dp1[0] = dp2[0] = 1;
        for (int i = 1; i < n; i++) {
            dp1[i] = dp2[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                if (a[i] > a[j])
                    dp1[i] = max(dp1[i], dp2[j] + 1);
                
                if (a[i] < a[j])
                    dp2[i] = max(dp2[i], dp1[j] + 1);
            }
        }
        
        int len1 = Arrays.stream(dp1).max().getAsInt();
        int len2 = Arrays.stream(dp2).max().getAsInt();
        return max(len1, len2);
    }
    
    // Bottom-up tabulation
    // T(n): O(n^2), S(n): O(n)
    public int length(int[] a) {
        int n = a.length;
        
        /* Create an array to store intermediate results
         * z[i][0] = Length of the longest Zig-Zag subsequence ending at 
         * index i when last element is greater than its previous element 
         * 
         * z[i][1] = Length of the longest Zig-Zag subsequence ending at 
         * index i when last element is smaller than its previous element   
        */
        int[][] z = new int[n][2];
        
        z[0][0] = 1; z[0][1] = 1;
        int res = 1;
        for (int i = 1; i < n; i++) {
            z[i][0] = 1; z[i][1] = 1;
            for (int j = i-1; j >= 0; --j) {
                if (a[i] > a[j])
                    z[i][0] = max(z[i][0], z[j][1] + 1);
                
                if (a[i] < a[j])
                    z[i][1] = max(z[i][1], z[j][0] + 1);
            }
            
            if (res < max(z[i][0], z[i][1]))
                res = max(z[i][0], z[i][1]);
        }
        
        return res;
    }
    
    /*
     * The first recurrence relation is based on the fact that, If we are at 
     * position i and this element has to be bigger than its previous element 
     * then for this sequence (upto i) to be bigger we will try to choose an 
     * element j ( < i) such that A[j] < A[i] i.e. A[j] can become A[i]’s 
     * previous element and Z[j][1] + 1 is bigger than Z[i][0] then we will 
     * update Z[i][0].
     * 
     * Remember we have chosen Z[j][1] + 1 not Z[j][0] + 1 to satisfy alternate 
     * property because in Z[j][0] last element is bigger than its previous one 
     * and A[i] is greater than A[j] which will break the alternating property 
     * if we update. So above fact derives first recurrence relation, similar is
     * the reason for second recurrence relation as well.
     */
    
    public static void main(String[] args) {
        int[] a = {10, 22, 9, 33, 49, 50, 31, 60};
        LongestZigZagSubsequence zigZag = new LongestZigZagSubsequence();
        System.out.println(zigZag.length(a)); // 6
        System.out.println(zigZag.lzzs(a)); // 6
        
        a = new int[] {1,17,5,10,13,15,10,5,16,8};
        System.out.println(zigZag.length(a)); // 7
        System.out.println(zigZag.lzzs(a)); // 7
    }
}
