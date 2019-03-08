package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-product-subarray-added-negative-product-case/
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
			 * max product ending at current position will be min product ending
			 * at previous position multiplied by current element,
			 * 
			 * and min product ending at current position will be max product
			 * ending at previous position multiplied by current element. */
			else if (a[i] < 0) {
				int tmp = max_ending_here;
				max_ending_here = min_ending_here * a[i];
				min_ending_here = tmp * a[i];
			}
			
			/* If current element is zero, maximum product cannot end at current 
			 * element. Update max_ending_here with 0, and min_ending_here with 1.
			 * 
			 * max_ending_here is updated to 0, to handle the case when all other 
			 * elements are negative, in that case overall max product should be 0. */
			else {
				max_ending_here = 0;
				min_ending_here = 1;
			}
			
			// Update max_so_far
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
		
		a = new int[]{6, -3, -10, 0, 2};
		System.out.println(maxProductSubarray(a)); // 180
		
		a = new int[]{-1, -3, -10, 0, 60};
		System.out.println(maxProductSubarray(a)); // 60
		
		a = new int[]{-2, -3, 0, -2, -40};
		System.out.println(maxProductSubarray(a)); // 80
		
		a = new int[]{0, -4, 0, -2};
		System.out.println(maxProductSubarray(a)); // 0
		
		a = new int[]{-4};
		System.out.println(maxProductSubarray(a)); // -4
		
		a = new int[]{-4, 5};
		System.out.println(maxProductSubarray(a)); // 5
	}
}
