package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/find-if-a-string-is-interleaved-of-two-other-strings-dp-33/

public class StringInterleavedOfTwoOtherString {

	class SimpleRecursiveSolution {
		
		public boolean isInterleaved(String A, String B, String C) {
			// If sum of lengths of A and B is not equal to length of C
			// then C cannot be an interleaving of A and B
			if (A.length() + B.length() != C.length())
				return false;
			
			return isInterleaved(A, B, C, 0, 0);
		}
		
		// T(n): Exp
		private boolean isInterleaved(String A, String B, String C, int i, int j) {
			// current index of C
			int k = i + j;
			
			// base case
			// When A and B are both empty, return true if C is empty, else return false
			if (i == A.length() && j == B.length())
				return k == C.length();
			// When atleast one of A or B is non-empty, but C is empty, return false
			if (k == C.length())
				return false;
			
			boolean res = false;
			if (i < A.length())
				res = (A.charAt(i) == C.charAt(k) && isInterleaved(A, B, C, i+1, j));
			if (j < B.length())
				res = (res || (B.charAt(j) == C.charAt(k) && isInterleaved(A, B, C, i, j+1)));
			return res;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(m*n), S(n): O(m*n)
		public boolean isInterleaved(String A, String B, String C) {
			int m = A.length(), n = B.length();
			
			// If sum of lengths of A and B is not equal to length of C
			if (m + n != C.length())
				return false;
			
			boolean[][] dp = new boolean[m+1][n+1];
			
			int k;
			for (int i = m; i >= 0; i--) {
				for (int j = n; j >= 0; j--) {
					k = i + j; 
					if (i == m && j == n)
						dp[i][j] = (k == C.length());
					else if (k == C.length())
						dp[i][j] = false;
					else {
						if (i < A.length())
							dp[i][j] = (A.charAt(i) == C.charAt(k) && dp[i+1][j]);
						if (j < B.length())
							dp[i][j] = dp[i][j] || (B.charAt(j) == C.charAt(k) && dp[i][j+1]);
					}
				}
			}
			return dp[0][0];
		}
	}
	
	public static void main(String[] args) {
		StringInterleavedOfTwoOtherString o = new StringInterleavedOfTwoOtherString();
		
		String A = "XXY", B = "XXZ", C = "XXZXXXY";
		System.out.println(o.new SimpleRecursiveSolution().isInterleaved(A, B, C)); // false
		System.out.println(o.new DPSolution().isInterleaved(A, B, C)); // false
		
		A = "XXY"; B = "XXZ"; C = "XXZXXY";
		System.out.println(o.new SimpleRecursiveSolution().isInterleaved(A, B, C)); // true
		System.out.println(o.new DPSolution().isInterleaved(A, B, C)); // true
		
		A = "ABCD"; B = "BCDX"; C = "ABCDXBCD";
		System.out.println(o.new SimpleRecursiveSolution().isInterleaved(A, B, C)); // true
		System.out.println(o.new DPSolution().isInterleaved(A, B, C)); // true
		
		A = "XXY"; B = "XXZ"; C = "XXXXZY";
		System.out.println(o.new SimpleRecursiveSolution().isInterleaved(A, B, C)); // true
		System.out.println(o.new DPSolution().isInterleaved(A, B, C)); // true
	}
}
