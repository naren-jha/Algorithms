package dynamicprogramming.intermediate;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/count-distinct-subsequences/

public class DistinctSubsequences {

	public int count(String s) {
		char[] sa = s.toCharArray();
		int n = sa.length;
		
		Map<Character, Integer> repetitionMap = new HashMap<Character, Integer>();
		int[] dp = new int[n+1];
		dp[0] = 1; // empty string is one subsequence
		
		for (int i = 1; i <= n; i++) {
			dp[i] = 2*dp[i-1];
			if (repetitionMap.containsKey(sa[i-1]))
				dp[i] -= dp[repetitionMap.get(sa[i-1])];
			repetitionMap.put(sa[i-1], i-1);
		}
		return dp[n];
	}
	
	public static void main(String[] args) {
		DistinctSubsequences obj = new DistinctSubsequences();
		System.out.println(obj.count("gfg")); // 7
	}
}
