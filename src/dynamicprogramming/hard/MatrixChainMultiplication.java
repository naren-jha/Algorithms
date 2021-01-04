package dynamicprogramming.hard;

import static java.lang.Math.min;

/**
 * @author Narendra Jha
 *
 * CLRS 15.2
 * https://www.geeksforgeeks.org/matrix-chain-multiplication-dp-8/
 * https://www.geeksforgeeks.org/printing-brackets-matrix-chain-multiplication-problem/
 */
public class MatrixChainMultiplication {
    
    class SimpleRecursiveSolution {
        
        // T(n): Exponential
        public int matrixChainOrder(int[] p) {
            int n = p.length - 1; // Number of matrices being multiplied
            return matrixChainOrder(p, 1, n);
        }
        
        public int matrixChainOrder(int[] p, int i, int j) {
            // base case: one matrix (no multiplication needed)
            if (i == j) return 0;
            
            // to find the optimal split point for A[i...j], we'll have to try all the possible split points b/w i and j
            // k can have values ranging from k = i to k = (j-1)
            // k = i => A[i...j] is split as A[i] and A[i+1...j]
            // k = i+1 => A[i...j] is split as A[i...i+1] and A[i+2...j]
            // k = i+2 => A[i...j] is split as A[i...i+2] and A[i+3...j]
            // .
            // .
            // .
            // k = j-1 => A[i...j] is split as A[i...j-1] and A[j]
            // we cannot use k = j, as that implies no split, and represents the original problem A[i...j] that we're trying to solve
            int minCost = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                minCost = min(minCost, matrixChainOrder(p, i, k) + matrixChainOrder(p, k+1, j) + p[i-1]*p[k]*p[j]);
            }
            return minCost;
        }
    }
    
    class DPSolution {
        // In the recursive solution to this problem, we observe that 'i' starts from 1 and 'j' starts from 'n'
        // but 'i' does not always increase OR 'j' does not always decrease in the subsequent calls for subproblems
        // but length of the matrix chain always decreases in the subsequent calls for subproblems.
        // So in the bottom-up approach, we'll have to solve this by starting from smaller length and then moving 
        // towards larger length.
        
        // Therefore, we identify this new type of DP problem, where in the recurrence we have two varying indices (i and j)
        // and length of the subproblems keeps getting shorter and shorter with subsequent calls (But i or j does not necessarily 
        // increase or decrease in any particular order). In these type of problems
        // to construct DP table (bottom-up), we have to solve them by the length of the subproblems
        
        // Bottom-up tabulation
        // T(n): O(n^3), S(n): O(n^2)
        public int matrixChainOrder(int[] p) {
            int n = p.length - 1; // Number of matrices being multiplied
            
            // dp[i][j] stores minimum cost required to multiply matrix chain A[i..j]
            // therefore dp[1][n] will hold the final result: min cost to multiply A[1..n]
            int[][] dp = new int[n+1][n+1];
            
            // initialize cost for length 1
            for (int i = 1; i <= n; i++)
                dp[i][i] = 0;
            
            // fill entries for lengths 2 to n
            for (int len = 2; len <= n; len++) {
                for (int i = 1; i <= n - (len - 1); i++) {
                    int j = i + (len - 1);
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j]);
                    }
                }
            }
            
            return dp[1][n];
        }
        
        /*
         * We can extend above solution to print optimal matrix product parenthesization
         */
        public void printOptimalParenthesis(int[] p) {
            int n = p.length - 1; // Number of matrices being multiplied
            
            // dp[i][j] stores minimum cost required to multiply matrix chain A[i..j]
            // therefore dp[1][n] will hold the final result: min cost to multiply A[1..n]
            int[][] dp = new int[n+1][n+1];
            
            // split[i][j] stores matrix number where matrix product of A[i..j]
            // should be split to get optimal solution
            int[][] split = new int[n+1][n+1];
            
            // initialize cost for length 1
            for (int i = 1; i <= n; i++)
                dp[i][i] = 0;
            
            // fill entries for lengths 2 to n
            for (int len = 2; len <= n; len++) {
                for (int i = 1; i <= n - (len - 1); i++) {
                    int j = i + (len - 1);
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        int cost = dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j];
                        if (cost < dp[i][j]) {
                            dp[i][j] = cost;
                            split[i][j] = k;
                        }
                    }
                }
            }
            
            printParenthesis(split, 1, n);
        }

        private void printParenthesis(int[][] split, int i, int j) {
            // base case:
            if (i == j) {
                System.out.print("A"+i);
                return;
            }
            
            System.out.print("(");
            printParenthesis(split, i, split[i][j]);
            printParenthesis(split, split[i][j]+1, j);
            System.out.print(")");
        }
        
        
        /*
         * Below is an algorithm to actually multiply a given matrix chain in optimal way
         * CLRS: Exercise 15.2-2
         * 
         * BELOW CODE IS NOT TESTED
         */
        public int[][] MatrixChainMultiply(int[][][] A, int[][] split, int i, int j) {
            // base case:
            if (i == j) {
                return A[i];
            }
            
            int[][] X = MatrixChainMultiply(A, split, i, split[i][j]);
            int[][] Y = MatrixChainMultiply(A, split, split[i][j]+1, j);
            return MatrixMultiply(X, Y);
        }
        
        public int[][] MatrixMultiply(int[][] A, int[][] B) {
            int aRows = A.length, aCols = A[0].length;
            int bRows = B.length, bCols = B[0].length;
            
            if (aCols != bRows)
                throw new IllegalStateException("Matrices cannot be multiplied");
            
            int[][] res = new int[aRows][bCols];
            for (int i = 0; i < aRows; i++) {
                for (int j = 0; j < bCols; j++) {
                    res[i][j] = 0;
                    for (int k = 0; k < aCols; k++)
                        res[i][j] += A[i][k] * B[k][j];
                }
            }
            return res;
        }
    }
    
    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // matrices dimensions
        MatrixChainMultiplication o = new MatrixChainMultiplication();
        
        System.out.println(o.new SimpleRecursiveSolution().matrixChainOrder(p));// 15125
        System.out.println(o.new DPSolution().matrixChainOrder(p)); // 15125
        
        o.new DPSolution().printOptimalParenthesis(p); // ((A1(A2A3))((A4A5)A6))
    }

}
