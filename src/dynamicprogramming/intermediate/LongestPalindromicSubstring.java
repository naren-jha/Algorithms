package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/longest-palindrome-substring-set-1/

public class LongestPalindromicSubstring {
    
    // If we apply brute-force approach to check. Then to check all the possible substring, it'll take O(n^2)
    // and then to check if each of those substring is a palindrome or not, it'll take another O(n). So total O(n^3).
    // But we are doing some repetitive work in this process. When we check for s[i to j], we are also checking for 
    // s[i+1 to j-1], and then later we solve for s[i+1 to j-1] again (subproblem for s[i+1 to j-1] itself
    // has many other overlapping subproblems). So we'll have to use DP to solve this efficiently.
    // We can solve the subproblems starting from smaller length and going towards higher length.
    
    // d[i][j] stores true, if s[i...j] is a palindrome, else it stores false.

    // bottom-up tabulation
    // T(n): O(n^2), S(n): O(n^2)
    public int longestPalindromeSubstring(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLen = 1, si = 0;
        
        // len 1 substrings
        for (int i = 0; i < n; ++i) dp[i][i] = true;
        
        // len 2 substrings
        for (int i = 0; i < n-1; ++i) {
            dp[i][i+1] = s.charAt(i) == s.charAt(i+1);
            if (dp[i][i+1]) {
                maxLen = 2;
                si = i;
            }
        }
        
        for (int len = 3; len <= n; ++len) {
            for (int i = 0; i < n - (len-1); i++){
                int j = i + (len - 1);
                dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i+1][j-1]);
                if (dp[i][j] && len > maxLen) {
                    maxLen = len;
                    si = i;
                }
            }
        }
        
        System.out.println("Longest Palindrome Substring is: " + s.substring(si, si + maxLen));
        return maxLen;
    }
    
    // We can also solve this problem in O(n^2) time and O(1) space. See solution below.
    // The idea is to choose a center for the palindrome and expand as long as possible.
    // Center of palindrome will be a character, if the length of palindrome is odd (example: abcba).
    // Center of palindrome will be two characters (or space between them), if the length of palindrome is even (example: abba).
    // so we try all possiblity for even and odd length palindromes
    public int longestPalindromeSubstr(String s) {
        int n = s.length();
        if (n == 0) return 0;
        
        int maxLen = 1, si = 0;
        for (int i = 0; i < n; ++i) {
            int len1 = expand(s, i, i); // odd length palindrome
            int len2 = expand(s, i, i+1); // even length palindrome
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                si = i - (len-1) / 2;
            }
        }
        
        
        System.out.println("Longest Palindrome Substring is: " + s.substring(si, si + maxLen));
        return maxLen;
    }
    
    int expand(String s, int i, int j) {
        int n = s.length();
        while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        
        // above while loop will fail either because index went out of bounds or characters didn't match
        // so we'll have to contract one position left and right to get valid length
        i++; j--;
        
        return j-i+1;
    }
    
    /*
     * This problem also has a linear time solution using Manacher’s Algorithm
     * https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
     */
    
    public static void main(String[] args) {
        LongestPalindromicSubstring obj = new LongestPalindromicSubstring();
        
        System.out.println(obj.longestPalindromeSubstring("aacabdkacaa")); // 3
        // Longest Palindrome Substring is: aca
        
        System.out.println(obj.longestPalindromeSubstr("aacabdkacaa")); // 3
        // Longest Palindrome Substring is: aca
    }
}
