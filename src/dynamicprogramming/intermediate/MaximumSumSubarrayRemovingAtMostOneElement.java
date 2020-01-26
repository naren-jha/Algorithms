package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-sum-subarray-removing-one-element/

public class MaximumSumSubarrayRemovingAtMostOneElement {

    /*
     * The idea is this:
     * If removal of one element condition was not applied, we could have solved this 
     * problem using Kadane’s algorithm but here one element can be removed, so we
     * have to handle that case.
     * 
     * The removal condition can be handled using two arrays (forward and backward arrays)
     * we create two arrays fw[] and bw[], where fw[i] stores maximum sum sub-array 
     * ending at a[i], and bw[i] stores maximum sum sub-array starting at a[i]. 
     * When both forward and backward arrays are populated, we use them for one element 
     * removal condition as follows:
     * For each index i, maximum sum sub-array after ignoring ith element will be 
     * fw[i-1] + bw[i+1]. So we loop for all possible values of i (i = 1 to n-2) 
     * and choose maximum among them.
     */
    public static int maxSum(int[] a) {
        int n = a.length;
        
        int currMax, maxSoFar;
        int[] fw = new int[n]; // stores maximum sum sub-array in forward direction
        int[] bw = new int[n]; // stores maximum sum sub-array in backward direction
        
        // populate results for maximum sum sub-array in forward direction
        currMax = maxSoFar = fw[0] = a[0];
        for (int i = 1; i < n; i++) {
            currMax = Math.max(a[i], currMax + a[i]);
            maxSoFar = Math.max(maxSoFar, currMax);
            fw[i] = currMax;
        }
        
        // populate results for maximum sum sub-array in backward direction
        currMax = maxSoFar = bw[n-1] = a[n-1];
        for (int i = n-1; i >= 0; i--) {
            currMax = Math.max(a[i], currMax + a[i]);
            maxSoFar = Math.max(maxSoFar, currMax);
            bw[i] = currMax;
        }
        
        /* Initialize result by maxSoFar, so that the case when no element is 
         * removed to get max sum sub-array is also handled. */
        int result = maxSoFar;
        
        // choosing maximum ignoring ith element
        for (int i = 1; i < n-1; i++) {
            result = Math.max(result, fw[i-1] + bw[i+1]);
        }
        return result;
    }
    
    public static void main(String[] args) {
        int[] a = {-2, -3, 4, -1, -2, 1, 5, -3};;
        System.out.println(maxSum(a)); // 9
    }
}
