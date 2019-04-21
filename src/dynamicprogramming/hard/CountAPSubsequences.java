package dynamicprogramming.hard;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-arithmetic-progression-subsequences-array/
// https://discuss.codechef.com/t/maxpr-editorial/5849

public class CountAPSubsequences {
	
	/*
	 * Algorithm:
	 * 1. Find min and max in array
	 * 
	 * 2. Minimum difference possible in AP will be (min - max)
	 *    and maximum difference possible in AP will be (max - min)
	 *    min_d = min - max
	 *    max_d = max - min
	 *    
	 * 3. Loop for all possible difference 'd' (from 'min_d' to 'max_d')
	 * 
	 * 4. For each value of 'd', we find all possible count of APs of size >= 2 
	 *    We apply similar kind of technique as in LIS problem to do that
	 *    
	 * 5. Keep adding all such AP counts into a variable. 
	 * 
	 * 6. In the end, add 'n' to count for all single element AP, and add 1 to 
	 *    count empty set.
	 *    
	 * Considering that outer most loop for 'd' goes through some constant number of times,
	 * this algorithm takes O(n^2) time.
	 */

	// T(n): O(n^2), S(n): O(n)
	public static int countAP_Slow(int[] a) {
		int n = a.length;
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		
		for (int i = 0; i < n; i++) {
			max = Math.max(max, a[i]);
			min = Math.min(min, a[i]);
		}
		
		int min_d = min - max;
		int max_d = max - min;
		
		int[] dp = new int[n];
		
		int count = 0;
		for (int d = min_d; d <= max_d; d++) {
			
			Arrays.fill(dp, 1); // reset all count to 1 everytime, to indicate
			// single element count for each element
			
			for (int i = 1; i < n; i++) {
				for (int j = i-1; j >= 0; j--) {
					if (a[j] + d == a[i])
						dp[i] += dp[j];
				}
				
				count += dp[i] - 1; // -1 to remove single element count
				// we will add single element count at once in the end
				// if we add single element count inside the loop, it will
				// get added several times
			}
		}
		count += (n + 1); // n for single element, and 1 for empty set
		
		return count;
	}
	
	/*
	 * We can optimize above solution to linear time. 
	 * What we can do is maintain a sum[] array, where sum[x] stores count of 
	 * all the APs with last element value as 'x'. i.e., it stores sum of all 
	 * the dp's where last element in AP was 'x'. Doing this we get rid of the 
	 * 'j' loop, which leads to O(n) time solution. Ofcourse we may do badly in 
	 * terms of space complexity because of this.
	 */
	
	// T(n): O(n), S(n): O(Max(n, max))
	public static int countAP_Fast(int[] a) {
		int n = a.length;
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		
		for (int i = 0; i < n; i++) {
			max = Math.max(max, a[i]);
			min = Math.min(min, a[i]);
		}
		
		int min_d = min - max;
		int max_d = max - min;
		
		int[] dp = new int[n];
		int[] sum = new int[max + 1];
		
		int count = 0;
		for (int d = min_d; d <= max_d; d++) {
			
			Arrays.fill(sum, 0); // reset all sum to 0
			// as we will be generating new sum values for every 'd'
			
			sum[a[0]] = 1;
			for (int i = 1; i < n; i++) {
				dp[i] = 1;
				
				if (a[i] - d >= 1 && a[i] - d <= max_d)
					dp[i] += sum[a[i] - d];
				
				count += dp[i] - 1; // -1 to remove single element count
				// we will add single element count at once in the end
				// if we add single element count inside the loop, it will
				// get added several times
				
				sum[a[i]] += dp[i];
			}
		}
		count += (n + 1); // n for single element, and 1 for empty set
		
		return count;
	}
	
	public static void main(String[] args) {
		int[] a = {1, 2, 3};
		System.out.println(countAP_Slow(a)); // 8
		System.out.println(countAP_Fast(a)); // 8
		
		a = new int[]{1, 2, 3, 4, 5};
		System.out.println(countAP_Slow(a)); // 23
		System.out.println(countAP_Fast(a)); // 23
		
		a = new int[]{10, 20, 30, 45};
		System.out.println(countAP_Slow(a)); // 12
		System.out.println(countAP_Fast(a)); // 12
	}
}
