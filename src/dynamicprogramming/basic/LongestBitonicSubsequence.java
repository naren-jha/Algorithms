package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/longest-bitonic-subsequence-dp-15/

public class LongestBitonicSubsequence {

	/*
	 * This problem is a variation of standard Longest 
	 * Increasing Subsequence (LIS) problem. Let the 
	 * input array be a[] of length n. We need to construct 
	 * two arrays lis[] and lds[] using Dynamic Programming 
	 * solution of LIS problem. lis[i] stores the length of 
	 * the Longest Increasing Subsequence ending with a[i]. 
	 * lds[i] stores the length of the Longest Decreasing 
	 * Subsequence beginning with a[i]. Finally, we need to 
	 * return the max value of lis[i] + lds[i] – 1 where 
	 * i varies from 0 to n-1.
	 */
	
	// T(n): O(n^2), S(n): O(n)
	public int lbs(int[] a) {
		int n = a.length;
		int[] lis = new int[n];
		int[] lds = new int[n];
		
		lis[0] = 1;
		for (int i = 1; i < n; i++) {
			lis[i] = 1;
			for (int j = 0; j < i; j++) {
				if (a[j] < a[i]) {
					if (lis[i] < lis[j] + 1) {
						lis[i] = lis[j] + 1;
					}
				}
			}
		}
		
		lds[n-1] = 1;
		for (int i = n-2; i >= 0; i--) {
			lds[i] = 1;
			for (int j = n-1; j > i; j--) {
				if (a[j] < a[i]) {
					if (lds[i] < lds[j] + 1) {
						lds[i] = lds[j] + 1;
					}
				}
			}
		}
		
		int lbs = lis[0] + lds[0] - 1;
		for (int i = 1; i < n; i++)
			if (lbs < lis[i] + lds[i] - 1)
				lbs = lis[i] + lds[i] - 1;
		return lbs;
	}
	
	public static void main(String[] args) {
		int[] a = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
		LongestBitonicSubsequence obj = new LongestBitonicSubsequence();
		System.out.println(obj.lbs(a)); // 7
	}
}
