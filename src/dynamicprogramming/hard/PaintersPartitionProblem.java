package dynamicprogramming.hard;

/**
 * @author Narendra Jha
 * 
 * https://www.geeksforgeeks.org/painters-partition-problem/
 */
public class PaintersPartitionProblem {

    class SimpleRecursiveSolution {
        /*
         * Lets say we had to solve this problem for k = 2
         * How would we approach it? We can solve it something as below

        int f(int[] a, int i) {
            int sum = 0;
            int max = 0, min = Integer.MAX_VALUE;
            for (int x = i; i < a.length - 1; i++) {
                sum += a[x];
                max = Math.max(sum, sum(i+1));
                min = Math.min(min, max);
            }
            return min;
        }
        
        initial call is made with i = 0
        */
        
        /*
         * Now how do we extend above solution for any value of k.
         * We can solve it recursively, as below
         */
        
        // T(n): Exp
        public int minTime(int[] a, int i, int k) {
            if (k == 1)
                return sum(a, i);
            
            int sum = 0;
            int max = 0, min = Integer.MAX_VALUE;
            for (int x = i; x < a.length - (k-1); x++) {
                sum += a[x];
                max = Math.max(sum, minTime(a, x+1, k-1));
                min = Math.min(min, max);
                
                // optimization
                if (min == sum)
                    break;
            }
            return min;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2 * k), S(n): O(n*k)
        public int minTime(int[] a, int k) {
            int n = a.length;
            
            // dp[i][j] stores optimal solution for a[i..n-1] and k=j
            // therefore dp[0][k] will hold final result
            int[][] dp = new int[n][k+1];
            
            // when k == 1
            for (int i = 0; i < n; i++)
                dp[i][1] = sum(a, i);
            
            for (int i = n-1; i >= 0; i--) {
                for (int j = 2; j <= k; j++) {
                    int sum = 0;
                    int max = 0, min = Integer.MAX_VALUE;
                    for (int x = i; x < a.length - (j-1); x++) {
                        sum += a[x];
                        max = Math.max(sum, dp[x+1][j-1]);
                        min = Math.min(min, max);
                        
                        // optimization
                        if (min == sum)
                            break;
                    }
                    dp[i][j] = min;
                }
            }
            
            return dp[0][k];
        }
    }
    
    // utility method to get sum of array from 'i' to last element
    private int sum(int[] a, int i) {
        int sum = 0;
        for (int x = i; x < a.length; x++)
            sum += a[x];
        return sum;
    }
    
    public static void main(String[] args) {
        PaintersPartitionProblem o = new PaintersPartitionProblem();
        int[] a = {10, 20, 60, 50, 30, 40};
        int k = 3;
        System.out.println(o.new SimpleRecursiveSolution().minTime(a, 0, k));
        System.out.println(o.new DPSolution().minTime(a, k));
    }
}
