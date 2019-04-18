package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/maximize-arrj-arri-arrl-arrk-such-that-i-j-k-l/

// Maximize arr[j] – arr[i] + arr[l] – arr[k], such that i < j < k < l

public class MaximizeExpression {
	
	// This problem is same as MaximumProfitByBuyingAndSellingShareAtMostTwice
	// And therefore same approach can also be used to solve this
	
	// Here we are going to solve it using a different approach
	// T(n): O(n), S(n): O(n)
	public static int maxValue(int[] arr) {
		int n = arr.length;
		
		if (n < 4)
			throw new IllegalStateException("Array should have minimum 4 elements.");
		
		int[] dp1 = new int[n+1];
		int[] dp2 = new int[n];
		int[] dp3 = new int[n-1];
		int[] dp4 = new int[n-2];
		
		// initialize last values of dp arrays to MIN_VALUE
		dp1[n] = dp2[n-1] = dp3[n-2] = dp4[n-3] = Integer.MIN_VALUE;
		
		// dp1[] stores maximum value of arr[l]
		for (int i = n-1; i >= 0; i--)
			dp1[i] = Math.max(dp1[i+1], arr[i]);
		
		// dp2[] stores the maximum value of arr[l] - arr[k]
		for (int i = n-2; i >= 0; i--)
			dp2[i] = Math.max(dp2[i+1], dp1[i+1] - arr[i]);
		
		// dp3[] stores the maximum value of arr[l] - arr[k] + arr[j]
		for (int i = n-3; i >= 0; i--)
			dp3[i] = Math.max(dp3[i+1], dp2[i+1] + arr[i]);
		
		// dp4[] stores the maximum value of arr[l] - arr[k] + arr[j] – arr[i]
		for (int i = n-4; i >= 0; i--)
			dp4[i] = Math.max(dp4[i+1], dp3[i+1] - arr[i]);
		
		return dp4[0];
	}
	
	public static void main(String[] args) {
		int[] arr = {4, 8, 9, 2, 20};
		System.out.println(maxValue(arr)); // 23
	}
}
