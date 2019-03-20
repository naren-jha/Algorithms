package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/non-crossing-lines-connect-points-circle/
// http://www.geometer.org/mathcircles/catalan.pdf

public class NonCrossingHandShakes {
	
	/*
	 * for n = 8 we have m = n/2 i.e 4 pairs.
	 * 
		       1
		   8       2
		 
		 7           3
		   
		   6       4
		       5
		       
	Cm = number of non-crossing hand shakes with m number of pairs.
	
	Now every time we draw a line between two pair of numbers, on each side 
	we have to leave even number of pairs (otherwise handshakes gonna cross)
	
	Considering 1 we can draw lines in following manner:
	1-2 this devides problem into 0 and 3 pairs.
	1-4 this devides problem into 1 and 2 pairs.
	1-6 into 2 and 1 pairs.
	1-8 into 3 and 0 pairs.
	
	So C4 = C0*C3 + C1*C2 + C2*C1 + C3*C0 = 1*5 + 1*2 + 2*1 + 5*1 = 14 
	(C3 and C2 can be precomputed in similar fashion)
	*/
	
	public int countHandShakes(int n) {
		if (n % 2 == 1)
			throw new IllegalStateException("n cannot be odd");
		
		return catalanNumber(n/2);
	}
	
	// T(n): O(n^2), S(n): O(n)
	public int catalanNumber(int n) {
		int[] dp = new int[n+1];
		dp[0] = dp[1] = 1;
		
		for (int j = 2; j <= n; j++)
			for (int i = 0; i < j; i++)
				dp[j] += dp[i] * dp[j-i-1];
		
		return dp[n];
	}
	
	public static void main(String[] args) {
		int n = 8;
		System.out.println(new NonCrossingHandShakes().countHandShakes(n)); // 14
	}
}