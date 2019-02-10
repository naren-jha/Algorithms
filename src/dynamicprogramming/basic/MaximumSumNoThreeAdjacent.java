package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/maximum-subsequence-sum-such-that-no-three-are-consecutive/

public class MaximumSumNoThreeAdjacent {
	public int maxSum(int[] a) {
		int n = a.length;
		if (n == 0)
			return 0;
		
		int[] res = new int[n];
		if (n >= 1)
			res[0] = a[0];
		if (n >= 2)
			res[1] = a[0] + a[1];
		if (n >= 3)
			res[2] = Math.max(res[1], Math.max(res[0]+a[2], a[1]+a[2]));
		
		for (int i = 3; i < n; i++)
			res[i] = Math.max(res[i-1], Math.max(res[i-2]+a[i], res[i-3]+a[i-1]+a[i]));
			
		return res[n-1];
	}
	
	public int maxSumSpaceOptimized(int[] a) {
		int n = a.length;
		if (n == 0)
			return 0;
		
		if (n == 1)
			return a[0];
		if (n == 2)
			return a[0] + a[1];
		if (n == 3)
			return Math.max(a[0]+a[1], Math.max(a[0]+a[2], a[1]+a[2]));
		
		int i_3 = a[0];
		int i_2 = a[0] + a[1];
		int i_1 = Math.max(i_2, Math.max(i_3+a[2], a[1]+a[2]));
		int maxSum = 0;
		for (int i = 3; i < n; i++) {
			maxSum = Math.max(i_1, Math.max(i_2+a[i], i_3+a[i-1]+a[i]));
			i_3 = i_2;
			i_2 = i_1;
			i_1 = maxSum;
		}
			
		return maxSum;
	}
	
	public static void main(String[] args) {
		int[] a = { 100, 1000, 100, 1000, 1 };
		MaximumSumNoThreeAdjacent obj = new MaximumSumNoThreeAdjacent();
		System.out.println(obj.maxSum(a)); // 2101
		System.out.println(obj.maxSumSpaceOptimized(a)); // 2101
	}
}