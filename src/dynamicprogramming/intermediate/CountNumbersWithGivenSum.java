package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-of-n-digit-numbers-whose-sum-of-digits-equals-to-given-sum/

public class CountNumbersWithGivenSum {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countNumbers(int n, int sum) {
            // Base cases:
            if (n == 0)
                return sum == 0 ? 1 : 0;
            /*if (sum == 0)
                return 0;*/
            
            int count = 0;
            int x = 0, end = sum-1;
            if (n == 1) { // excluding leading 0
                x = 1;
                end = sum;
            }
            for (; x <= end && x <= 9; x++) {
                count += countNumbers(n-1, sum - x);
            }
            return count;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*k), S(n): O(n*k)
        public int countNumbers(int n, int sum) {
            int[][] res = new int[n+1][sum+1];
            
            Arrays.fill(res, 0); // redundant in Java
            res[0][0] = 1; // when n == 0 && sum == 0
            
            /*
            for (int j = 1; j <= sum; j++) // n == 0 && sum != 0
                res[0][j] = 0; // redundant in Java
            for (int i = 1; i <= n; i++) // n != 0 && sum == 0
                res[i][0] = 0; // redundant in Java
            */
            
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= sum; j++) {
                    int x = 0, end = j-1;
                    if (i == 1) { // excluding leading 0
                        x = 1;
                        end = j;
                    }
                    for (; x <= end && x <= 9; x++) {
                        res[i][j] += res[i-1][j-x];
                    }
                }
            }
            
            return res[n][sum];
        }
        
        // We can space optimize above solution to O(sum), as at any time,
        // we are using only two rows, so we can manage with res[2][sum+1]
    }
    
    public static void main(String[] args) {
        int n = 3, sum = 6;
        CountNumbersWithGivenSum o = new CountNumbersWithGivenSum();
        System.out.println(o.new SimpleRecursiveSolution().countNumbers(n, sum)); // 21
        System.out.println(o.new DPSolution().countNumbers(n, sum)); // 21
    }
}
