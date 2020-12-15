package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/sequences-given-length-every-element-equal-twice-previous/

public class SequencesOfGivenLength {

    class SimpleRecursiveSolution {
        // T(m, n): Exp
        public int countSeq(int m, int n) {
            if (n == 0)
                return 1;
            if (m < (1 << n-1)) // m < Math.pow(2, n-1)
                return 0;
            
            return countSeq(m/2, n-1) + countSeq(m-1, n);
        }
    }
    
    class DPSolution {
        // top-down memoization technique
        // T(m, n): O(mn), S(m, n): O(mn)
        public int countSeqMemoized(int m, int n) {
            int[][] mem = new int[m+1][n+1];
            for (int i = 0; i <= m; i++)
                Arrays.fill(mem[i], -1);
            return countSeqMemoizedUtil(m, n, mem);
        }
        
        private int countSeqMemoizedUtil(int m, int n, int[][] mem) {
            if (n == 0)
                return 1;
            if (m < (1 << n-1))
                return 0;
            
            if (mem[m][n] != -1)
                return mem[m][n];
            
            mem[m][n] = countSeqMemoizedUtil(m/2, n-1, mem) + countSeqMemoizedUtil(m-1, n, mem);
            return mem[m][n];
        }
        
        // bottom-up tabulation technique
        // T(m, n): O(mn), S(m, n): O(mn)
        public int countSeq(int m, int n) {
            int[][] dp = new int[m+1][n+1];
            for (int i = 0; i <= m; ++i)
                dp[i][0] = 1;
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (i < (1 << j-1))
                        dp[i][j] = 0;
                    else
                        dp[i][j] = dp[i/2][j-1] + dp[i-1][j];
                }
            }
            
            return dp[m][n];
        }
    }
    
    public static void main(String[] args) {
        SequencesOfGivenLength obj = new SequencesOfGivenLength();
        int m = 5, n = 2;
        System.out.println(obj.new SimpleRecursiveSolution().countSeq(m, n)); // 6
        
        System.out.println(obj.new DPSolution().countSeqMemoized(m, n)); // 6
        System.out.println(obj.new DPSolution().countSeq(m, n)); // 6
    }
}
