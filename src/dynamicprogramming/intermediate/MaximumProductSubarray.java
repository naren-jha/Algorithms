package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-product-subarray-added-negative-product-case/
// https://www.geeksforgeeks.org/maximum-product-subarray/

public class MaximumProductSubarray {
    
    static int maxProdSA(int[] a) {
        int pp = 1, np = 1; // positive and negative product
        int maxProduct = Integer.MIN_VALUE;
        
        for (int val : a) {
            if (val > 0) {
                pp *= val;
                np *= val;
            }
            else if (val < 0) {
                np *= val;
                pp = np;
            }
            else {
                pp = 0;
                np = 1;
            }
            
            maxProduct = Math.max(maxProduct, pp);
            
            if (pp <= 0) pp = 1;
        }
        
        return maxProduct;
    }

    public static int maxProductSubarray(int[] a) {
        
        // max product ending at current position
        int max_ending_here = 1;
        
        // min product ending at current position
        int min_ending_here = 1;
        
        // Initialize overall max product
        int max_so_far = Integer.MIN_VALUE;
        
        for (int i = 0; i < a.length; i++) {
            
            if (a[i] > 0) {
                max_ending_here = max_ending_here * a[i];
                if (min_ending_here != 1)
                    min_ending_here = min_ending_here * a[i];
            }
            
            else if (a[i] < 0) {
                int tmp = max_ending_here;
                max_ending_here = min_ending_here * a[i];
                min_ending_here = tmp * a[i];
            }
            
            /* max_ending_here is updated to 0, to handle the case when all other 
             * elements are negative, in that case overall max product should be 0. */
            else {
                max_ending_here = 0;
                min_ending_here = 1;
            }
            
            max_so_far = Math.max(max_so_far, max_ending_here);
            
            /* If max_ending_here is <= zero, then to calculate product for next
             * iteration, it should be set to 1. */
            if (max_ending_here <= 0) {
                max_ending_here = 1;
            }
        }
        
        return max_so_far;
    }
    
    public static void main(String[] args) {
        int[] a = {1, -2, -3, 0, 7, -8, -2};
        System.out.println(maxProductSubarray(a)); // 112
        System.out.println(maxProdSA(a)); // 112
        
        a = new int[]{6, -3, -10, 0, 2};
        System.out.println(maxProductSubarray(a)); // 180
        System.out.println(maxProdSA(a)); // 180
        
        a = new int[]{-1, -3, -10, 0, 60};
        System.out.println(maxProductSubarray(a)); // 60
        System.out.println(maxProdSA(a)); // 60
        
        a = new int[]{-2, -3, 0, -2, -40};
        System.out.println(maxProductSubarray(a)); // 80
        System.out.println(maxProdSA(a)); // 80
        
        a = new int[]{0, -4, 0, -2};
        System.out.println(maxProductSubarray(a)); // 0
        System.out.println(maxProdSA(a)); // 0
        
        a = new int[]{-4};
        System.out.println(maxProductSubarray(a)); // -4
        System.out.println(maxProdSA(a)); // -4
        
        a = new int[]{-4, 5};
        System.out.println(maxProductSubarray(a)); // 5
        System.out.println(maxProdSA(a)); // 5
    }
}
