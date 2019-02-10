package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/

// Not sure if this problem should be tagged under dynamic programming.

public class LargestSumContiguousSubarray {

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
	}
	
	public static void main(String[] args) {
		int[] a = {-2, -3, 4, -1, -2, 1, 5, -3};
		System.out.println(maxSubArraySum(a)); // 7
		printMaxSumSubArray(a);
	}
}