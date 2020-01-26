package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

public class LongestPalindromicSubstring {

    // Bottom-up tabulation
    // T(n): O(n^2), S(n): O(n^2)
    public int longestPalindromeSubstring(String s) {
        int n = s.length();
        char[] ch = s.toCharArray();
        
        // create a 2D array to store intermediate results
        // where p[i][j] indicates whether s[i, j] is a palindrome or not
        // p[i][j] = true, => substring s[i, j] is a palindrome
        // p[i][j] = false, => substring s[i, j] is not a palindrome
        boolean[][] p = new boolean[n][n];
        
        // All substrings of length 1 are palindrome
        int maxLength = 1;
        for (int i = 0; i < n; i++)
            p[i][i] = true;
        
        // Check for substrings of length 2
        int start = 0;
        for (int i = 0; i < n-1; i++) {
            if (ch[i] == ch[i+1]) {
                p[i][i+1] = true;
                maxLength = 2;
                start = i;
            }
        }
        
        // Check for lengths greater than 2
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - (len-1); i++) {
                int j = i + (len-1);
                if (ch[i] == ch[j] && p[i+1][j-1]) {
                    p[i][j] = true;
                    if (len > maxLength) {
                        maxLength = len;
                        start = i;
                    }
                }
                else
                    p[i][j] = false; // redundant in Java
            }
        }
        
        System.out.print("Longest Palindrome Substring is: ");
        System.out.println(s.substring(start, start+maxLength));
        return maxLength;
    }
    
    /*
     * There is a O(n^2) time and O(1) space solution discussed at: 
     * https://www.geeksforgeeks.org/longest-palindromic-substring-set-2/
     */
    
    /*
     * This problem also has a linear time solution using Manacher’s Algorithm
     * https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
     */
    
    public static void main(String[] args) {
        LongestPalindromicSubstring obj = new LongestPalindromicSubstring();
        System.out.println(obj.longestPalindromeSubstring("forgeeksskeegfor"));
        /*
         * Longest Palindrome Substring is: geeksskeeg
         * 10
         */
    }
}
