package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/sequences-given-length-every-element-equal-twice-previous/

public class SequencesOfGivenLength {

    class SimpleRecursiveSolution {
        // T(m, n): Exp
        public int countSeq(int m, int n) {
            if (m < Math.pow(2, n-1))
                return 0;
            if (n == 1)
                return m;
            
            return countSeq(m/2, n-1) + countSeq(m-1, n);
        }
    }
    
    class DPSolution {
        // top-down memoization technique
        // T(m, n): O(mn), S(m, n): O(mn)
        public int countSeqMemoized(int m, int n) {
            int[][] res = new int[m+1][n+1];
            for (int i = 0; i <= m; i++)
                Arrays.fill(res[i], -1);
            return countSeqMemoizedUtil(m, n, res);
        }
        
        private int countSeqMemoizedUtil(int m, int n, int[][] res) {
            if (m < Math.pow(2, n-1))
                return 0;
            if (n == 1)
                return m;
            
            if (res[m][n] != -1)
                return res[m][n];
            
            res[m][n] = countSeqMemoizedUtil(m/2, n-1, res) + countSeqMemoizedUtil(m-1, n, res);
            return res[m][n];
        }
        
        // bottom-up tabulation technique
        // T(m, n): O(mn), S(m, n): O(mn)
        public int countSeq(int m, int n) {
            int[][] res = new int[m+1][n+1];
            
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (j == 0)
                        res[i][j] = 0;
                    else if (i < Math.pow(2, j-1))
                        res[i][j] = 0;
                    else if (j == 1)
                        res[i][j] = i;
                    else
                        res[i][j] = res[i/2][j-1] + res[i-1][j];
                }
            }
            
            return res[m][n];
        }
    }
    
    public static void main(String[] args) {
        SequencesOfGivenLength obj = new SequencesOfGivenLength();
        int m = 5, n = 2;
        System.out.println(obj.new SimpleRecursiveSolution().countSeq(m, n));
        
        System.out.println(obj.new DPSolution().countSeqMemoized(m, n));
        System.out.println(obj.new DPSolution().countSeq(m, n));
    }
}
