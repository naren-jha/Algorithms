package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/count-ways-reach-nth-stair-using-step-1-2-3/
// https://www.geeksforgeeks.org/count-number-of-ways-to-cover-a-distance/

public class CoverDistance {

    class SimpleRecursiveSolution {
        // T(n): O(3^n), S(n): O(n)
        public int countWays(int n) {
            if (n == 0) return 1;
            if (n == 1) return 1;
            if (n == 2) return 2;
            
            return countWays(n-3) + countWays(n-2) + countWays(n-1);
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(n), S(n): O(n)
        public int countWays(int n) {
            if (n <= 1) return 1; // edge case
            
            int[] dp = new int[n+1];
            dp[0] = 1;
            dp[1] = 1;
            dp[2] = 2;
            
            for (int i = 3; i <= n; i++)
                dp[i] = dp[i-3] + dp[i-2] + dp[i-1];
            
            return dp[n];
        }
        
        // we can space optimize above solution to O(1) by using 3 variables
        // T(n): O(n), S(n): O(1)
        public int countWaysSO(int n) {
            if (n <= 1) return 1; // edge case
            
            int t0 = 1, t1 = 1, t2 = 2, t;
            for (int i = 3; i <= n; i++) {
                t = t0 + t1 + t2;
                t0 = t1; t1 = t2; t2 = t;
            }
            
            return t2;
        }
    }
    
    public static void main(String[] args) {
        int n = 4;
        CoverDistance obj = new CoverDistance();
        System.out.println(obj.new SimpleRecursiveSolution().countWays(n)); // 7
        System.out.println(obj.new DPSolution().countWays(n)); // 7
        System.out.println(obj.new DPSolution().countWaysSO(n)); // 7
    }
}
