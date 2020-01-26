package basic.array.problems;

// https://www.geeksforgeeks.org/maximum-product-subarray/

public class MaximumProductSubarray {

    public static int maxProductSubarray(int[] a) {
        
        // max product ending at current position
        int max_ending_here = 1;
        
        // min product ending at current position
        int min_ending_here = 1;
        
        // Initialize overall max product
        int max_so_far = Integer.MIN_VALUE;
        
        for (int i = 0; i < a.length; i++) {
            
            /* If current element is positive, then 
             * 
             * max product ending at current position will be max product ending
             * at previous position multiplied by current element,
             * 
             * and min product ending at current position will be min product
             * ending at previous position multiplied by current element, 
             * but only if it is not equal to 1, if previous min product is equal 
             * to 1, then it remains 1. */
            if (a[i] > 0) {
                max_ending_here = max_ending_here * a[i];
                if (min_ending_here != 1)
                    min_ending_here = min_ending_here * a[i];
            }
            
            /* If current element is negative, then 
             * 
             * min product ending at current position will be max product ending
             * at previous position multiplied by current element,
             * 
             * and max product ending at current position will be min product
             * ending at previous position multiplied by current element, 
             * but only if min product ending at previous position is not equal to 1,
             * if previous min product is equal to 1, then  max product ending at 
             * current position becomes 1. */
            else if (a[i] < 0) {
                int tmp = max_ending_here;
                if (min_ending_here != 1)
                    max_ending_here = min_ending_here * a[i];
                else
                    max_ending_here = 1;
                
                min_ending_here = tmp * a[i];
            }
            
            /* If current element is zero, maximum product cannot end at current 
             * element. Update max_ending_here and min_ending_here with 1.
             * to mark the end of current subarray at previous element, and 
             * beginning of new subarray from next element onwards*/
            else {
                max_ending_here = 1;
                min_ending_here = 1;
            }
            
            // Update max_so_far
            max_so_far = Math.max(max_so_far, max_ending_here);
        }
        
        return max_so_far;
    }
    
    public static void main(String[] args) {
        int[] a = {1, -2, -3, 0, 7, -8, -2};
        System.out.println(maxProductSubarray(a)); // 112
        
        a = new int[]{6, -3, -10, 0, 2};
        System.out.println(maxProductSubarray(a)); // 180
        
        a = new int[]{-1, -3, -10, 0, 60};
        System.out.println(maxProductSubarray(a)); // 60
        
        a = new int[]{-2, -3, 0, -2, -40};
        System.out.println(maxProductSubarray(a)); // 80
        
        // This algorithm will not work with following test cases:
        // expected output for following two test cases are 0 and -4
        // but what you get is 1 and 1. We have a corrected solution
        // in dynamic programming intermediate, which handles these
        // corner cases as well.
        a = new int[]{0, -4, 0, -2};
        System.out.println(maxProductSubarray(a)); // 1
        
        a = new int[]{-4};
        System.out.println(maxProductSubarray(a)); // 1
    }
}
