package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-possible-decodings-given-digit-sequence/

public class CountDecodingsDigitSequence {
	
	class SimpleRecursiveSolution {
		// T(n): Exp
		public int countDecodings(String s) {
			return countDecodings(s.toCharArray(), s.length());
		}
		
		private int countDecodings(char[] s, int n) {
			if (n == 0 || n == 1)
				return 1;
			
			int count = 0;
			if (s[n-1] != '0') // 1 to 9
				count = countDecodings(s, n-1);
			
			// 10 to 26
			// when last two digits form a number 
			// greater than equal to 10 or smaller than equal to 26
			if (s[n-2] == '1' || (s[n-2] == '2' && s[n-1] <= '6'))
				count += countDecodings(s, n-2);
			
			return count;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n), S(n): O(n)
		public int countDecodings(String s) {
			char[] sa = s.toCharArray();
			int n = s.length();
			
			int[] res = new int[n+1];
			Arrays.fill(res, 0); // redundant in Java
			
			res[0] = res[1] = 1;
			for (int i = 2; i <= n; i++) {
				if (sa[i-1] != '0')
					res[i] = res[i-1];
				if (sa[i-2] == '1' || (sa[i-2] == '2' && sa[i-1] <= '6'))
					res[i] += res[i-2];
			}
			return res[n];
		}
		
		// Bottom-up tabulation, Space optimized
		// T(n): O(n), S(n): O(1)
		public int countDecodingsSO(String s) {
			char[] sa = s.toCharArray();
			int n = s.length();
			
			int a = 1, b = 1, c = 0;
			for (int i = 2; i <= n; i++) {
				if (sa[i-1] != '0')
					c = b;
				if (sa[i-2] == '1' || (sa[i-2] == '2' && sa[i-1] <= '6'))
					c += a;
				a = b;
				b = c;
			}
			return b;
		}
	}
	
	public static void main(String[] args) {
		CountDecodingsDigitSequence o = new CountDecodingsDigitSequence();
		String s = "1234";
		System.out.println(o.new SimpleRecursiveSolution().countDecodings(s)); // 3
		System.out.println(o.new DPSolution().countDecodings(s)); // 3
		System.out.println(o.new DPSolution().countDecodingsSO(s)); // 3
	}
}
