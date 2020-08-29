package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/different-ways-sum-n-using-numbers-greater-equal-m/

public class DiffWaysToSum {

    // This problem is a variation of coin change problem
    
    /*
     * The idea is that, since the sum has to be formed using numbers
     * >= m, the numbers that can be used to make sum are [m, m+1, m+2 ... n]
     * This is because if m is > n then a sum cannot be made
     */
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        /*public int countWays(int n, int m) {
            if (n == 0)
                return 1;
            if (n < 0)
                return 0;
            if (m > n)
                return 0;
            
            return countWays(n-m, m) + countWays(n, m+1);
        }*/
        
        public int countWays(int n, int m) {
            if (n == 0)
                return 1;
            
            int count = 0;
            if (n >= m)
                count += countWays(n-m, m);
            if (m < n)
                count += countWays(n, m+1);
            return count;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*(n-m)), S(n): O(mn)
        public int countWays(int n, int m) {
            int[][] res = new int[n+1][n+1];
            
            for (int i = 0; i <= n; i++) {
                for (int j = n; j >= m; j--) {
                    if (i == 0) { // for n = 0 case
                        res[i][j] = 1;
                        continue;
                    }
                    
                    if (i >= j)
                        res[i][j] += res[i-j][j];
                    if (j < n)
                        res[i][j] += res[i][j+1];
                }
            }
            return res[n][m];
        }
        
        // We should be able to space optimize above solution
    }
    
    public static void main(String[] args) {
        int m = 1;
        int n = 3;
        DiffWaysToSum o = new DiffWaysToSum();
        System.out.println(o.new SimpleRecursiveSolution().countWays(n, m));
        System.out.println(o.new DPSolution().countWays(n, m));
    }
}
