package dynamicprogramming.basic;

import java.util.Arrays;

// CLRS 15.1
// https://www.geeksforgeeks.org/cutting-a-rod-dp-13/

public class RodCuttingProblem {
    
    class SimpleRecursiveSolution {
        
        // T(n): Exponential, as it considers all possible cuts
        // with 2^(n-1) total possible combinations
        public int cutRod(int[] price, int n) {
            if (n == 0)
                return 0;
            int maxRevenue = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++)
                maxRevenue = Math.max(maxRevenue, (price[i-1] + cutRod(price, n-i)) );
            return maxRevenue;
        }
        
        /* Actual recurrence equation for above solution should be :
         * maxRevenue = Max(maxRevenue, (cutRod(price, i) + cutRod(price, n-i)) )
         * 
         * However, We don't need to divide first piece further, because
         * combinations generated by dividing first piece further, are already considered
         * while dividing second piece in previous iterations.
         */        
    }
    
    class DPSolution {
        
        // top-to-bottom memoization
        // T(n): O(n^2), S(n): O(n)
        public int cutRodMemoized(int[] price, int n) {
            int[] res = new int[n+1];
            Arrays.fill(res, -1);
            
            return cutRodMemoizedUtil(price, n, res);
        }
        
        private int cutRodMemoizedUtil(int[] price, int n, int[] res) {
            if (res[n] != -1)
                return res[n];
            
            if (n == 0)
                return 0;
            
            int maxRevenue = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++)
                maxRevenue = Math.max(maxRevenue, 
                                     (price[i-1] + cutRodMemoizedUtil(price, n-i, res)) );
            res[n] = maxRevenue;
            return res[n];
        }
        
        // bottom-up tabulation
        // T(n): O(n^2), S(n): O(n)
        public int cutRodBottomUp(int[] price, int n) {
            int[] res = new int[n+1];
            res[0] = 0;
            
            for (int j = 1; j <= n; j++) {
                int maxRevenue = Integer.MIN_VALUE;
                for (int i = 1; i <= j; i++)
                    maxRevenue = Math.max(maxRevenue, (price[i-1] + res[j-i]) );
                res[j] = maxRevenue;
            }
            return res[n];
        }
        
        /*
         * Reconstructing a solution:
         * We can extend above solutions to print the sizes of cuts 
         * that led to optimal solution
         */
        private void printCutRodSolution(int[] price, int n) {
            int[] res = new int[n+1];
            int[] cuts = new int[n+1];
            res[0] = 0;
            
            for (int j = 1; j <= n; j++) {
                int maxRevenue = Integer.MIN_VALUE;
                for (int i = 1; i <= j; i++) {
                    if (maxRevenue < (price[i-1] + res[j-i])) {
                        maxRevenue = (price[i-1] + res[j-i]);
                        cuts[j] = i;
                    }
                }
                res[j] = maxRevenue;
            }
            
            // Print result
            while (n != 0) {
                System.out.print(cuts[n] + " ");
                n = n - cuts[n];
            }
        }
    }
    
    public static void main(String[] args) {
        int[] price = new int[] {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n = 7;
        
        RodCuttingProblem obj = new RodCuttingProblem();
        System.out.println(obj.new SimpleRecursiveSolution().cutRod(price, n)); // 18
        
        System.out.println(obj.new DPSolution().cutRodMemoized(price, n)); // 18
        System.out.println(obj.new DPSolution().cutRodBottomUp(price, n)); // 18
        
        obj.new DPSolution().printCutRodSolution(price, n); // 1 6 
    }
}