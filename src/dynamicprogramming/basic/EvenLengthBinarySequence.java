package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-even-length-binary-sequences-with-same-sum-of-first-and-second-half-bits/

public class EvenLengthBinarySequence {
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countSeq(int n) {
            return countSeqUtil(n, 0);
        }

        private int countSeqUtil(int n, int diff) {
            // Base cases:
            // we can't cover diff of more than n
            if (Math.abs(diff) > n) return 0;
            
            if (n == 0) {
                if (diff == 0) return 1;
                else return 0;
            }
            
            return 2*countSeqUtil(n-1, diff) // First and last bits are same
                    + countSeqUtil(n-1, diff+1) // First bit is 0 & last bit is 1
                    + countSeqUtil(n-1, diff-1); // First bit is 1 & last bit is 0
        }
    }
    
    class DPSolution {
        // top-down memoization
        // T(n): O(n^2), S(n): O(n^2)
        public int countSeq(int n) {
            // diff will range from -n to +n, so we need a
            // nX2n matrix, but we take (n+1)x(2n+1) matrix
            // to simplify indexes while using this matrix
            int[][] res = new int[n+1][2*n+1];
            
            // initialize entire matrix with -1
            for (int i = 0; i <= n; i++)
                Arrays.fill(res[i], -1);
            
            return countSeqUtil(n, 0, res);
        }
        
        private int countSeqUtil(int n, int diff, int[][] res) {
            // Base cases:
            // we can't cover diff of more than n
            if (Math.abs(diff) > n) return 0;
            
            if (n == 0) {
                if (diff == 0) return 1;
                else return 0;
            }
            
            if (res[n][n+diff] != -1)
                return res[n][n+diff];
            
            res[n][n+diff] = 2*countSeqUtil(n-1, diff, res) // First and last bits are same
                    + countSeqUtil(n-1, diff+1, res) // First bit is 0 & last bit is 1
                    + countSeqUtil(n-1, diff-1, res); // First bit is 1 & last bit is 0
            
            return res[n][n+diff];
        }
    }
    
    class LinearTimeSolutionWithoutDP {
        // T(n): O(n), S(n): O(1)
        public int countSeq(int n) {
            if (n == 0) // edge case
                return 0;
            
            int nCr = 1; // as nC0 = 1
            int res = 1;
            for (int r = 1; r <= n; r++) {
                // Compute nCr using nC(r-1) 
                // nCr/nC(r-1) = (n+1-r)/r
                nCr = nCr * (n+1-r)/r;
                res += nCr*nCr;
            }
            return res;
        }
    }
    
    public static void main(String[] args) {
        int n = 2;
        EvenLengthBinarySequence obj = new EvenLengthBinarySequence();
        System.out.println(obj.new SimpleRecursiveSolution().countSeq(n)); // 6
        System.out.println(obj.new DPSolution().countSeq(n)); // 6
        System.out.println(obj.new LinearTimeSolutionWithoutDP().countSeq(n)); // 6
    }

}
