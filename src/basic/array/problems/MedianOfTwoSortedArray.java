package basic.array.problems;

// https://leetcode.com/problems/median-of-two-sorted-arrays/
// https://youtu.be/LPFhl65R7ww

// similar
// https://www.geeksforgeeks.org/median-of-two-sorted-arrays/

public class MedianOfTwoSortedArray {
    
    // USE SECOND APPROACH

    // O(n) solution
    // if (m+n) is odd, then median is [(m+n)/2]th element in merged array
    // if (m+n) is even, then median is (a[(m+n)/2 - 1] + a[(m+n)/2]) / 2 in merged array
    public static double median_A1(int[] a1, int[] a2) {
        int n = a1.length;
        int m = a2.length;
        
        int i = 0, j = 0, count = -1, mid1 = 0, mid2 = 0;
        while (count != (m+n)/2) {
            int[] a; int index;
            
            // if first array is exhausted
            if (i == n) {
                a = a2; index = j;
                j++;
            }
            
            // if second array is exhausted
            else if (j == m) {
                a = a1; index = i;
                i++;
            }
            
            // no array is exhausted
            else {
                if (a1[i] < a2[j]) {
                    a = a1; index = i;
                    i++;
                }
                else {
                    a = a2; index = j;
                    j++;
                }
            }
            
            count++;
            if (count == ((m+n)/2 - 1) )
                mid1 = a[index];
            if (count == (m+n)/2)
                mid2 = a[index];
        }
        return (n + m) % 2 == 1 ? mid2 : (mid1 + mid2) / 2.0;
    }
    
    // O(lg min(n,m)) solution, Approach: Binary Search
    // explanation: https://youtu.be/LPFhl65R7ww
    /*
     * For an input array of size 'n' we can partition the array at (n+1) places
     *          _ _ _ _ _
     *         |_|_|_|_|_|
     *        0 1 2 3 4 5
     *       low         high
     * 
     * lets say we partition 'a1' at 'pa1', means there are pa1 elements to 
     * the left of a1 and (n - pa1) elements to the right of it after partition.
     * 
     * similarly, lets say we partition 'a2' at 'pa2', means there are pa2 
     * elements to the left of a2 and (m - pa2) elements to the right of it after 
     * partition.
     * 
     * For median, number of elements on either side of middle element(s) should be 
     * equal.
     * i.e., (pa1 + pa2) = (n - pa1) + (m - pa2)
     * However, when total number of elements are odd, then one side will have one 
     * element more than the other side. lets say we keep that one extra element in 
     * left side.
     * => (pa1 + pa2) = (n - pa1) + (m - pa2) + 1
     * => 2*pa2 = (n + m + 1) - 2*pa1
     * => pa2 = [(n + m + 1) / 2] - pa1
     * 
     * so if we partition array 'a1' at index 'pa1', then we can find the partition 
     * index for array 'a2' using above formula.
     * 
     * Note1: adding one in above formula does not affect the result when (n + m) is
     * even, it only helps us get one element extra in left side, when (n + m) is odd.
     * 
     * Note2: if length of 'a1' is bigger than 'a2', then calculating 'pa2' might 
     * result in negative index. so we have to make sure that 'a1' is smaller than
     * 'a2'
     * 
     * After partitioning, we have to check if max in left side (combining both arrays)
     * is less than min in right side. If so, we have found the correct partitioning, 
     * else we need to continue with binary search until we find correct partitioning.
     */
    public static double median_A2(int[] a1, int[] a2) {
        int n = a1.length;
        int m = a2.length;
        
        // make sure that first array is smaller
        if (n > m) {
            // swap
            int[] tmp = a1;
            a1 = a2;
            a2 = tmp;
            
            n = a1.length;
            m = a2.length;
        }
        
        int low = 0, high = n, halfLen = ((n + m + 1) / 2);
        int pa1, pa2;
        int maxLeftA1 = 0, minRightA1 = 0, maxLeftA2 = 0, minRightA2 = 0;
        while (low <= high) {
            pa1 = (low + high) / 2;
            pa2 = halfLen - pa1;
            
            maxLeftA1 = (pa1 == 0) ? Integer.MIN_VALUE : a1[pa1-1];
            minRightA1 = (pa1 == n) ? Integer.MAX_VALUE : a1[pa1];
            
            maxLeftA2 = (pa2 == 0) ? Integer.MIN_VALUE : a2[pa2-1];
            minRightA2 = (pa2 == m) ? Integer.MAX_VALUE : a2[pa2];
            
            if (maxLeftA1 <= minRightA2 && maxLeftA2 <= minRightA1) {
                // we have found the correct partitioning
                break;
            }
            else if (maxLeftA1 > minRightA2) {
                high = pa1 - 1;
            }
            else {
                low = pa1 + 1;
            }
        }
        
        // return the result after we break out of while loop
        if ((n + m) % 2 == 1)
            return Math.max(maxLeftA1, maxLeftA2);
        else
            return (Math.max(maxLeftA1, maxLeftA2) + Math.min(minRightA1, minRightA2)) / 2.0;
    }
    
    public static void main(String[] args) {
        int[] a1 = {1, 12, 15, 26, 38}; 
        int[] a2 = {2, 13, 17, 30, 45};
        System.out.println(median_A1(a1, a2)); // 16.0
        System.out.println(median_A2(a1, a2)); // 16.0
        
        a1 = new int[]{1, 3}; 
        a2 = new int[]{2};
        System.out.println(median_A1(a1, a2)); // 2.0
        System.out.println(median_A2(a1, a2)); // 2.0
        
        a1 = new int[]{1, 2}; 
        a2 = new int[]{3, 4};
        System.out.println(median_A1(a1, a2)); // 2.5
        System.out.println(median_A2(a1, a2)); // 2.5
    }
}
