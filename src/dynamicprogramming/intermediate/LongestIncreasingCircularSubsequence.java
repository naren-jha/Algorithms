package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/find-longest-increasing-subsequence-circular-manner/

public class LongestIncreasingCircularSubsequence {

    /* Algorithm:
     * 1. Append given array to itself (to create circular array)
     * 2. Find LIS for every window of size n
     * 3. Max of all LIS is LICS */
    
    // T(n): O(n^3), S(n): O(n)
    public int lics(int[] a) {
        int n = a.length;
        int[] circularA = new int[2*n];
        for (int i = 0; i < 2*n; i++)
            circularA[i] = a[i % n];
        
        int lics = 0;
        for (int i = 0; i < n; i++)
            lics = Math.max(lics, lis(circularA, i, i+n));
        return lics;
    }
    
    private int lis(int[] a, int start, int end) {
        int n = end-start;
        int[] res = new int[n];
        
        res[0] = 1; int lis = 1;
        for (int i = 1; i < n; i++) {
            res[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[start + j] < a[start + i]) {
                    if (res[i] < res[j] + 1) {
                        res[i] = res[j] + 1;
                    }
                }
            }
            
            if (res[i] > lis)
                lis = res[i];
        }
        
        // return maximum from res[] array
        return lis;
    }
    
    public static void main(String[] args) {
        LongestIncreasingCircularSubsequence o = new LongestIncreasingCircularSubsequence();
        int[] a = {1, 4, 6, 2, 3};
        System.out.println(o.lics(a)); // 4
    }
}
