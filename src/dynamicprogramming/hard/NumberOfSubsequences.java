package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/number-subsequences-form-ai-bj-ck/

public class NumberOfSubsequences {

	// My explanation: http://disq.us/p/214lsck
	
	// T(n): O(n), S(n): O(1)
	public static int countSubsequences(String s) {
		int aCount = 0, bCount = 0, cCount = 0;
		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'a')
				aCount = 1 + 2*aCount;
			else if (s.charAt(i) == 'b')
				bCount = aCount + 2*bCount;
			else if (s.charAt(i) == 'c')
				cCount = bCount + 2*cCount;
		}
		return cCount;
	}
	
	public static void main(String[] args) {
		String s = "abbc";
		System.out.println(countSubsequences(s)); // 3
		s = "aaabbcc";
		System.out.println(countSubsequences(s)); // 63
	}
}
