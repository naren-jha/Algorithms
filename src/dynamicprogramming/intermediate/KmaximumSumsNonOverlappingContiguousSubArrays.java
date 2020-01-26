package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/k-maximum-sums-non-overlapping-contiguous-sub-arrays/

public class KmaximumSumsNonOverlappingContiguousSubArrays {

    public static void kMax(int[] a, int k) {
        
        for (int x = 0; x < k; x++) { // Apply Kadane’s Algorithm K times
            
            int maxSoFar = Integer.MIN_VALUE;
            int maxEndingHere = 0;
            int start = 0, end = 0, sp = 0;
            
            for (int i = 0; i < a.length; i++) {
                maxEndingHere += a[i];
                if (maxSoFar < maxEndingHere) {
                    maxSoFar = maxEndingHere;
                    start = sp;
                    end = i;
                }
                
                if (maxEndingHere < 0) {
                    maxEndingHere = 0;
                    sp = i+1;
                }
            }
            
            System.out.println("Maximum non-overlapping sub-array sum" + (x + 1)
                                 + " = " +  maxSoFar +  ", starting index = " + start
                                 + ", ending index = " + end);
            
            // Replace all elements of the maximum subarray by -infinity. So that 
            // these places cannot form maximum sum subarray again
            for (int i = start; i <= end; i++) {
                a[i] = Integer.MIN_VALUE;
            }
            
        }
    }
    
    public static void main(String[] args) {
        // Test case 1 
        int[] a1 = {4, 1, 1, -1, -3, -5, 6, 2, -6, -2}; 
        int k1 = 3; 
        kMax(a1, k1);
        /* OUTPUT:
         * Maximum non-overlapping sub-array sum1 = 8, starting index = 6, ending index = 7
         * Maximum non-overlapping sub-array sum2 = 6, starting index = 0, ending index = 2
         * Maximum non-overlapping sub-array sum3 = -1, starting index = 3, ending index = 3
         */
        
        System.out.println();
        // Test case 2 
        int[] a2 = {5, 1, 2, -6, 2, -1, 3, 1}; 
        int k2 = 2; 
        kMax(a2, k2);
        /* OUTPUT:
         * Maximum non-overlapping sub-array sum1 = 8, starting index = 0, ending index = 2
         * Maximum non-overlapping sub-array sum2 = 5, starting index = 4, ending index = 7
         */
    }
}
