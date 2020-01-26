package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/number-subsequences-string-divisible-n/

public class NumberOfSubsequencesDivisibleByN {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int count(String s, int N) {
            return count(s, N, 0, 0) - 1; // -1 to exclude empty string
        }
        
        private int count(String s, int N, int i, int rem) {
            // Base case
            if (i == s.length())
                return (rem == 0) ? 1 : 0;
            
            // sum of counts by excluding and including current character
            return count(s, N, i+1, rem) + count(s, N, i+1, (rem*10 + s.charAt(i)) % N);
        }
    }
    
    class DPSolution {
        // T(n): O(len * N), S(n): O(len * N)
        public int count(String s, int N) {
            int len = s.length();
            
            // dp[i][j] stores number of subsequences in s[i...(len-1)] which leaves 
            // remainder j after division by N
            int[][] dp = new int[len+1][N]; // maximum remainder possible is N-1
            
            // initialize for i == len
            dp[len][0] = 1;
            for (int j = 1; j < N; j++)
                dp[len][j] = 0; // redundant in Java
            
            // fill rest of the table
            for (int i = len-1; i >= 0; i--) {
                for (int j = 0; j < N; j++) {
                    dp[i][j] = dp[i+1][j] + dp[i+1][(j*10 + s.charAt(i)) % N];
                }
            }
            
            return dp[0][0] - 1; // -1 to exclude empty string
        }
    }
    
    public static void main(String[] args) {
        NumberOfSubsequencesDivisibleByN o = new NumberOfSubsequencesDivisibleByN();
        String s = "330";
        int N = 6;
        System.out.println(o.new SimpleRecursiveSolution().count(s, N)); // 4
        System.out.println(o.new DPSolution().count(s, N)); // 4
    }
}
