package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-sum-of-digits-in-numbers-from-1-to-n/

public class SumOfDigitsFrom1ToN {
	
	// T(n): O(d), S(n): O(d)
	public int digitSum(int n) {		
		int d = Integer.toString(n).length();
		// or using log: int d = (int) (Math.log10(n)) + 1;
		int[] dp = new int[d];
		dp[0] = 0;
		
		for (int i = 1; i < d; i++)
			dp[i] = (int) (dp[i-1]*10 + 45*Math.pow(10, i-1));
		return digitSumUtil(n, dp);
	}

	public int digitSumUtil(int n, int[] dp) {
		if (n < 10)
			return n*(n+1)/2;
		
		int d = Integer.toString(n).length();		
		int p = (int) Math.pow(10, d-1);
		int msd = n/p;
		int k = n % (p * msd);
		return msd*dp[d-1] + (msd*(msd-1)/2)*p + msd*(k + 1) + digitSumUtil(k, dp);
	}
	
	public static void main(String[] args) {
		SumOfDigitsFrom1ToN o = new SumOfDigitsFrom1ToN();
		System.out.println(o.digitSum(328)); // 3241
	}
}
