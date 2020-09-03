package dynamicprogramming.basic;

import static java.lang.Math.max;

// https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
// Kadane's Algorithm

public class MaximumSumSubarray {

    // returns largest sum of a continuous subarray
    public static int maxSubArraySum(int[] a) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;
        
        for (int i = 0; i < a.length; i++) {
            maxEndingHere += a[i];
            if (maxSoFar < maxEndingHere)
                maxSoFar = maxEndingHere;
            
            if (maxEndingHere < 0)
                maxEndingHere = 0;
        }
        
        return maxSoFar;
    }
    
    // we can also print the subarray with max sum. see solution below.
    public static void printMaxSumSubArray(int[] a) {
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
        
        System.out.println("largest sum is: " + maxSoFar);
        System.out.println("largest sum continuous subarray is:");
        for (int i = start; i <= end; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }
    
    // first approach can be simplified as below 
    // handles all negative number case as well
    public static int maxSubArraySum_m2(int[] a) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;
        
        for (int i = 0; i < a.length; i++) {
            maxEndingHere = max(a[i], maxEndingHere + a[i]);
            maxSoFar = max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    // Another modified approach which handles all negative number case as well
    private static int kadane(int[] a) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;
        boolean isPositive = false;
        
        for (int i = 0; i < a.length; i++) {
            maxEndingHere += a[i];
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
            else if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
                isPositive = true;
            }            
        }
        
        // System.out.println(isPositive);
        // there is at least one positive number
        if (isPositive)
            return maxSoFar;
        
        // Special case: when all numbers in array a[] are negative
        maxSoFar = a[0];
        for (int i = 0; i < a.length; i++) {
            if (maxSoFar < a[i])
                maxSoFar = a[i];
        }
        
        return maxSoFar;
    }
    
    public static void main(String[] args) {
        int[] a = {-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.println(maxSubArraySum(a)); // 7
        
        printMaxSumSubArray(a);
        /*
         * largest sum is: 7
         * largest sum continuous subarray is:
         * 4 -1 -2 1 5 
         */
        
        System.out.println(maxSubArraySum_m2(a)); // 7
        
        System.out.println(kadane(a)); // 7
    }
}