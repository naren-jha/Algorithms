package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-subsequences-product-less-k/

public class ProductSubsequencesCount {

    class SimpleRecursiveSolution {
        // T(n): O(2^n), S(n): O(n) [as max height of recursion tree is n]
        public int countProdSubSeq(int[] a, int k) {
            return countProdSubSeqUtil(a, a.length, k);
        }
        
        private int countProdSubSeqUtil(int[] a, int n, int k) {
            if (n == 0)
                return 0;
            
            if (a[n-1] <= k)
                return 1 + countProdSubSeqUtil(a, n-1, k/a[n-1])
                                + countProdSubSeqUtil(a, n-1, k);
            else 
                return countProdSubSeqUtil(a, n-1, k);
        }
    }
    
    class DPSolution {
        // Top-down memoization
        // T(n): O(nk), S(n): O(nk)
        public int countProdSubSeqMemoized(int[] a, int k) {
            int n = a.length;
            int[][] mem = new int[n+1][k+1];
            for (int i = 0; i <= n; i++)
                Arrays.fill(mem[i], -1);
            return countProdSubSeqUtil(a, n, k, mem);
        }
        
        private int countProdSubSeqUtil(int[] a, int n, int k, int[][] mem) {
            if (n == 0)
                return 0;
            
            if (mem[n][k] != -1)
                return mem[n][k];
            
            if (a[n-1] <= k)
                mem[n][k] = 1 + countProdSubSeqUtil(a, n-1, k/a[n-1], mem)
                                + countProdSubSeqUtil(a, n-1, k, mem);
            else 
                mem[n][k] = countProdSubSeqUtil(a, n-1, k, mem);
            
            
            return mem[n][k];
        }
        
        // Bottom-up tabulation
        // T(n): O(nk), S(n): O(nk)
        public int countProdSubSeq(int[] a, int k) {
            int n = a.length;
            int[][] dp = new int[n+1][k+1];
            Arrays.fill(dp[0], 0);
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= k; j++) {
                    if (a[i-1] <= j)
                        dp[i][j] = 1 + dp[i-1][j/a[i-1]] + dp[i-1][j];
                    else
                        dp[i][j] = dp[i-1][j];
                }
            }
            
            return dp[n][k];
        }
        
        // Bottom-up tabulation
        // T(n): O(nk), S(n): O(k)
        public int countProdSubSeqSpaceOptimized(int[] a, int k) {
            int n = a.length;
            int[][] dp = new int[2][k+1];
            Arrays.fill(dp[0], 0);
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= k; j++) {
                    if (a[i-1] <= j)
                        dp[i%2][j] = 1 + dp[(i-1)%2][j/a[i-1]] + dp[(i-1)%2][j];
                    else
                        dp[i%2][j] = dp[(i-1)%2][j];
                }
            }
            
            return dp[n%2][k];
        }
    }
    
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        int k = 10;
        ProductSubsequencesCount obj = new ProductSubsequencesCount();
        System.out.println(obj.new SimpleRecursiveSolution().countProdSubSeq(a, k)); // 11
        System.out.println(obj.new DPSolution().countProdSubSeqMemoized(a, k)); // 11
        System.out.println(obj.new DPSolution().countProdSubSeq(a, k)); // 11
        System.out.println(obj.new DPSolution().countProdSubSeqSpaceOptimized(a, k)); // 11
    }
}
