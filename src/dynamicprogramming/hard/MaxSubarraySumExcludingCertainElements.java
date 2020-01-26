package dynamicprogramming.hard;

import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/maximum-subarray-sum-excluding-certain-elements/

public class MaxSubarraySumExcludingCertainElements {

    // this problem is basically an extension of kadane's algorithm
    
    // TC: O(min(n,m)), SC: O(m)
    public static int maxSubArraySum(int[] a, int[] b) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < b.length; i++)
            set.add(b[i]);
            
        int maxSoFar = Integer.MIN_VALUE;
        int maxEndingHere = 0;
        
        for (int i = 0; i < a.length; i++) {
            // If an element is present in 'b', then don't consider that element and
            // reset current sum to start a new subarray from next element onwards
            if (set.contains(a[i])) {
                maxEndingHere = 0;
                continue;
            }
            
            maxEndingHere = Math.max(a[i], maxEndingHere + a[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    public static void main(String[] args) {
        int[] a = {3, 4, 5, -4, 6}; 
        int[] b = {1, 8, 5};
        System.out.println(maxSubArraySum(a, b)); // 7
    }
}
