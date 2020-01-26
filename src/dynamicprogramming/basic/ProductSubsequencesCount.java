package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-subsequences-product-less-k/

public class ProductSubsequencesCount {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countProdSubSeq(int[] a, int k) {
            return countProdSubSeqUtil(a, a.length, k);
        }
        
        private int countProdSubSeqUtil(int[] a, int n, int k) {
            if (n == 0)
                return 0;
            if (a[n-1] <= k)
                return countProdSubSeqUtil(a, n-1, k/a[n-1]) + 1
                                + countProdSubSeqUtil(a, n-1, k);
            else 
                return countProdSubSeqUtil(a, n-1, k);
        }
    }
    
    class DPSolution {
        // Top-down memoization
        // T(n): O(ak), S(n): O(ak)
        public int countProdSubSeqMemoized(int[] a, int k) {
            int n = a.length;
            int[][] res = new int[n+1][k+1];
            for (int i = 0; i <= n; i++)
                Arrays.fill(res[i], -1);
            return countProdSubSeqUtil(a, n, k, res);
        }
        
        private int countProdSubSeqUtil(int[] a, int n, int k, int[][] res) {
            if (n == 0)
                return 0;
            
            if (res[n][k] != -1)
                return res[n][k];
            
            if (a[n-1] <= k)
                res[n][k] = countProdSubSeqUtil(a, n-1, k/a[n-1], res) + 1
                                + countProdSubSeqUtil(a, n-1, k, res);
            else 
                res[n][k] = countProdSubSeqUtil(a, n-1, k, res);
            
            
            return res[n][k];
        }
        
        // Bottom-up tabulation
        // T(n): O(ak), S(n): O(ak)
        public int countProdSubSeq(int[] a, int k) {
            int n = a.length;
            int[][] res = new int[n+1][k+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    if (i == 0 || j == 0)
                        res[i][j] = 0;
                    else if (a[i-1] <= j)
                        res[i][j] = res[i-1][j/a[i-1]] + 1 + res[i-1][j];
                    else
                        res[i][j] = res[i-1][j];
                }
            }
            
            return res[n][k];
        }
        
        // Bottom-up tabulation
        // T(n): O(ak), S(n): O(k)
        public int countProdSubSeqSpaceOptimized(int[] a, int k) {
            int n = a.length;
            int[][] res = new int[2][k+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= k; j++) {
                    if (i == 0 || j == 0)
                        res[i%2][j] = 0;
                    else if (a[i-1] <= j)
                        res[i%2][j] = res[(i-1)%2][j/a[i-1]] + 1 + res[(i-1)%2][j];
                    else
                        res[i%2][j] = res[(i-1)%2][j];
                }
            }
            
            return res[n%2][k];
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
