package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/

public class PalindromePartitioning {

    // This problem is a variation of matrix chain multiplication problem
    
    // T(n): O(n^3), S(n): O(n^2)
    public int minPalindromePartition(String s) {
        int n = s.length();
        
        // c[i][j] = minimum number cuts needed for palindrome partitioning of s[i..j]
        // c[0][n-1] will hold the final result
        int[][] c = new int[n][n];
        
        // p[i][j] indicates whether s[i..j] is a palindrome or not
        boolean[][] p = new boolean[n][n];
        
        // initialize values for length 1
        for (int i = 0; i < n; i++) {
            c[i][i] = 0;
            p[i][i] = true;
        }
        
        // fill entries for lengths 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n-len; i++) {
                int j = i + len - 1;
                
                if (len == 2)
                    p[i][j] = (s.charAt(i) == s.charAt(j));
                else
                    p[i][j] = (s.charAt(i) == s.charAt(j)) && p[i+1][j-1];
                
                // if s[i..j] is palindrome, then no cut is required
                if (p[i][j]) {
                    c[i][j] = 0;
                }
                else {
                    // else, consider partitioning at every position from k = i to j-1
                    // and choose the minimum of all
                    c[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        c[i][j] = Math.min(c[i][j], c[i][k] + 1 + c[k+1][j]);
                    }
                }
            }
        }
        return c[0][n-1];
    }
    
    /*
     * Time complexity of above approach can be reduced down to O(n^2), if we find all 
     * palindrome substrings first and then we calculate minimum cut.
     * 
     */
    // T(n): O(n^2), S(n): O(n^2)
    public int minPalindromePartitionOptimized(String s) {
        int n = s.length();
        
        // c[i] = minimum number cuts needed for palindrome partitioning of s[0..i]
        // c[n-1] will hold the final result
        int[] c = new int[n];
        
        // p[i][j] indicates whether s[i..j] is a palindrome or not
        boolean[][] p = new boolean[n][n];
        
        // initialize values for length 1
        for (int i = 0; i < n; i++)
            p[i][i] = true;
        
        // fill entries for lengths 2 to n
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n-len; i++) {
                int j = i + len - 1;
                
                if (len == 2)
                    p[i][j] = (s.charAt(i) == s.charAt(j));
                else
                    p[i][j] = (s.charAt(i) == s.charAt(j)) && p[i+1][j-1];
            }
        }
        
        for (int i = 0; i <n; i++) {
            // if s[0..i] is palindrome, then no cut is required
            if (p[0][i]) {
                c[i] = 0;
            }
            else {
                // else, consider partitioning at every position from j = 0 to i-1
                // and choose the minimum of all
                c[i] = Integer.MAX_VALUE;
                for (int j = 0; j < i; j++) {
                    if (p[j+1][i] && (c[j] + 1) < c[i])
                        c[i] = c[j] + 1;
                }
            }
        }
        return c[n-1];
    }
    
    // We can optimize above solution even further by doing everything in one loop
    // T(n): O(n^2), S(n): O(n^2)
    public int minCut(String s){
        int n = s.length();
        // c[i] = minimum number cuts needed for palindrome partitioning of s[0..i]
        // c[n-1] will hold the final result
        int[] c = new int[n];
        
        // p[i][j] indicates whether s[i..j] is a palindrome or not
        boolean[][] p = new boolean[n][n];
        
        c[0] = 0;
        for (int i = 1; i < n; i++) {
            int min = i;
            for (int j = 0; j <= i; j++) {
                if (i <= j + 1)
                    p[i][j] = (s.charAt(i) == s.charAt(j));
                else
                    p[i][j] = (s.charAt(i) == s.charAt(j)) && p[i-1][j+1];
                
                if (p[i][j])
                    min = Math.min(min, j == 0 ? 0 : 1 + c[j-1]);
            }
            c[i] = min;
        }
        return c[n-1];
    }
    
    public static void main(String[] args) {
        String s = "ababbbabbababa";
        PalindromePartitioning obj = new PalindromePartitioning();
        
        System.out.println(obj.minPalindromePartition(s)); // 3
        System.out.println(obj.minPalindromePartitionOptimized(s)); // 3
        System.out.println(obj.minCut("abda")); // 3
    }
}
