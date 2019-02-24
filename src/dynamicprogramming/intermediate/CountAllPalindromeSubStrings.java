package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-palindrome-sub-strings-string/

public class CountAllPalindromeSubStrings {
	// Bottom-up tabulation
	// T(n): O(n^2), S(n): O(n^2)
	public int countPalindromeSubstrings(String s) {
		int n = s.length();
		char[] ch = s.toCharArray();
		
		// create a 2D array to store intermediate results
		// where p[i][j] indicates whether s[i, j] is a palindrome or not
		// p[i][j] = true, => substring s[i, j] is a palindrome
		// p[i][j] = false, => substring s[i, j] is not a palindrome
		boolean[][] p = new boolean[n][n];
		
		// All substrings of length 1 are palindrome
		for (int i = 0; i < n; i++)
			p[i][i] = true;
		
		// Check for substrings of length 2
		for (int i = 0; i < n-1; i++) {
			if (ch[i] == ch[i+1]) {
				p[i][i+1] = true;
			}
		}
		
		// Check for lengths greater than 2
		for (int len = 3; len <= n; len++) {
			for (int i = 0; i < n - (len-1); i++) {
				int j = i + (len-1);
				if (ch[i] == ch[j] && p[i+1][j-1])
					p[i][j] = true;
				else
					p[i][j] = false; // redundant in Java
			}
		}
		
		// count total number of true entries in p[][] array 
		// to get total number of palindrome substrings.
		int count = 0;
		for (int i = 0; i < n; i++) {
			// lower diagonal of the matrix is never used
			// and we are not counting palindromes of length 1
			// so start from j = i+1
			for (int j = i+1; j < n; j++)
				if (p[i][j])
					count++;
		}
		return count;
	}
	
	/*
	 * There is a O(n^2) time and O(n) space solution discussed at: 
	 * https://www.geeksforgeeks.org/count-palindrome-sub-strings-string-set-2/
	 */
	
	/*
	 * This problem also has a linear time solution using Manacher’s Algorithm
	 * https://www.geeksforgeeks.org/find-number-distinct-palindromic-sub-strings-given-string/
	 */
	
	public static void main(String[] args) {
		CountAllPalindromeSubStrings obj = new CountAllPalindromeSubStrings();
		System.out.println(obj.countPalindromeSubstrings("abbaeae")); // 4
	}
}
