package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/maximum-weight-transformation-of-a-given-string/

public class MaximumWeightTransformationOfString {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int maxWeight(String s, int n) {
            if (n <= 1)
                return n;
            
            int pairWeight = (s.charAt(n-1) != s.charAt(n-2)) ? 4 : 3;
            return Math.max(1 + maxWeight(s, n-1), pairWeight + maxWeight(s, n-2));
        }
    }
    
    class DPSolution {
        // T(n): O(n^2), S(n): O(n)
        public int maxWeight(String s) {
            int n = s.length();
            int[] dp = new int[n+1];
            
            dp[0] = 0; dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                int pairWeight = (s.charAt(i-1) != s.charAt(i-2)) ? 4 : 3;
                dp[i] = Math.max(1 +dp[i-1], pairWeight + dp[i-2]);
            }
            return dp[n];
        }
    }
    
    public static void main(String[] args) {
        MaximumWeightTransformationOfString o = new MaximumWeightTransformationOfString();
        String s = "AAAAABB";
        System.out.println(o.new SimpleRecursiveSolution().maxWeight(s, s.length())); // 11
        System.out.println(o.new DPSolution().maxWeight(s)); // 11
    }
}
