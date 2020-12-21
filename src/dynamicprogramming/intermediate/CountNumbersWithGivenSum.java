package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-of-n-digit-numbers-whose-sum-of-digits-equals-to-given-sum/

public class CountNumbersWithGivenSum {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countNumbers(int n, int sum) {
            // Base cases:
            if (n == 0 && sum == 0) return 1;
            if (n == 0 || sum == 0) return 0;
            
            int sd = 0; // start digit
            if (n == 1) sd = 1;
            
            int count = 0;
            for (int d = sd; d <= 9 && d <= sum; ++d)
                count += countNumbers(n-1, sum - d);
            
            return count;
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // T(n): O(n*k), S(n): O(n*k)
        public int countNumbers(int n, int sum) {
            int[][] dp = new int[n+1][sum+1];
            
            dp[0][0] = 1;
            for (int j = 1; j <= sum; j++) dp[0][j] = 0;
            for (int i = 1; i <= n; i++) dp[i][0] = 0;
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= sum; j++) {
                    int sd = 0;
                    if (i == 1) sd = 1;
                    
                    for (int d = sd; d <= 9 && d <= j; ++d)
                        dp[i][j] += dp[i-1][j-d];
                }
            }
            
            return dp[n][sum];
        }
        
        // We can space optimize above solution to O(sum), as at any time,
        // we are using only two rows, so we can manage with dp[2][sum+1]
    }
    
    public static void main(String[] args) {
        int n = 3, sum = 6;
        CountNumbersWithGivenSum o = new CountNumbersWithGivenSum();
        System.out.println(o.new SimpleRecursiveSolution().countNumbers(n, sum)); // 21
        System.out.println(o.new DPSolution().countNumbers(n, sum)); // 21
    }
}
