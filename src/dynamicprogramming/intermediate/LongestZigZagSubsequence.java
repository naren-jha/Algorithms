package dynamicprogramming.intermediate;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/longest-zig-zag-subsequence/
// https://www.geeksforgeeks.org/longest-alternating-subsequence/
// https://leetcode.com/problems/wiggle-subsequence/

public class LongestZigZagSubsequence {

    // This problem is similar to LIS problem
    
    // Aug 2020
    public int lzzs(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        
        int[] up = new int[n]; // up[i] -> longest zigzag subsequence at index 'i' such that a[i] > a[i-1]
        int[] dn = new int[n]; // dn[i] -> longest zigzag subsequence at index 'i' such that a[i] < a[i-1]
        
        up[0] = dn[0] = 1;
        for (int i = 1; i < n; i++) {
            up[i] = dn[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                if (a[i] > a[j])
                    up[i] = max(up[i], dn[j] + 1);
                
                if (a[i] < a[j])
                    dn[i] = max(dn[i], up[j] + 1);
            }
        }
        
        int longest = 0;
        for (int i = 0; i < n; i++) {
            longest = max(longest, max(up[i], dn[i]));
        }
        
        return longest;
    }
    
    // O(n) time solution
    // 2,1,4,5,6,3,3,4,8,4
    // Since every next number will either increase (or decrease) or will remain same.
    // therefore for each 'i', length will either remain same or increase by 1.
    // therefore we don't need to solve for all 'j', we can solve this in linear time
    public int lzzsLT(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        
        int[] up = new int[n]; // up[i] -> longest zigzag subsequence at index 'i' such that a[i] > a[i-1]
        int[] dn = new int[n]; // dn[i] -> longest zigzag subsequence at index 'i' such that a[i] < a[i-1]
        
        up[0] = dn[0] = 1;
        for (int i = 1; i < n; i++) {
            if (a[i] > a[i-1]) {
                up[i] = dn[i-1] + 1;
                dn[i] = dn[i-1];
            }
            else if (a[i] < a[i-1]) {
                dn[i] = up[i-1] + 1;
                up[i] = up[i-1];
            }
            else {
                up[i] = up[i-1];
                dn[i] = dn[i-1];
            }
        }
        
        return max(up[n-1], dn[n-1]);
    }
    
    // In fact we don't need O(n) space to solve this. we can do this in O(1) space
    // T(n): O(n), S(n): O(1)
    public int lzzsLTCS(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        
        int up = 1, dn = 1;
        for (int i = 1; i < n; ++i) {
            if (a[i] > a[i-1]) up = dn + 1;
            else if (a[i] < a[i-1]) dn = up + 1;
        }
        
        return max(up, dn);
    }
    
    // Old solution
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
        System.out.println(zigZag.lzzsLT(a)); // 6
        System.out.println(zigZag.lzzsLTCS(a)); // 6
        
        a = new int[] {1,17,5,10,13,15,10,5,16,8};
        System.out.println(zigZag.length(a)); // 7
        System.out.println(zigZag.lzzs(a)); // 7
        System.out.println(zigZag.lzzsLT(a)); // 7
        System.out.println(zigZag.lzzsLTCS(a)); // 7
    }
}
