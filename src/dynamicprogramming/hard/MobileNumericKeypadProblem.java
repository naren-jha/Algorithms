package dynamicprogramming.hard;

// https://www.geeksforgeeks.org/mobile-numeric-keypad-problem/

public class MobileNumericKeypadProblem {

    // create a direct address table with 
    // (current cell) -> (next valid cells) mapping
    private int[][] toPath = {
            {0, 8}, // from 0, we can go to 0 or 8
            {1, 2, 4}, // from 1, we can go to 1, 2, or 4
            {1, 2, 3, 5}, // etc
            {2, 3, 6},
            {1, 4, 5, 7},
            {2, 4, 5, 6, 8},
            {3, 5, 6, 9},
            {4, 7, 8},
            {0, 5, 7, 8, 9},
            {6, 8, 9}
    };
    
    class SimpleRecursiveSolution {
        // T(n): Exponential, due to overlapping subproblems
        public int countNumbers(int n) {
            int count = 0;
            for (int j = 0; j <= 9; j++)
                count += countUtil(n, j);
            return count;
        }
        
        private int countUtil(int n, int j) {
            if (n == 1)
                return 1;
            
            int count = 0;
            for (int k = 0; k < toPath[j].length; k++) {
                count += countUtil(n-1, toPath[j][k]);
            }
            return count;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n), S(n): O(n)
        public int countNumbers(int n) {
            // dp[i][j] stores number of words of length 'i' starting with 'j'
            int[][] dp = new int[n+1][10];
            
            // initialize base case for n == 1
            for (int j = 0; j < 10; j++)
                dp[1][j] = 1;
            
            // fill rest of the entries for length 2 to n
            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < 10; j++) {
                    dp[i][j] = 0;
                    for (int k = 0; k < toPath[j].length; k++) {
                        dp[i][j] += dp[i-1][toPath[j][k]];
                    }
                }
            }
            
            // sum count of all words of length 'n' starting with 0, 1, 2, ... 9
            int count = 0;
            for (int j = 0; j < 10; j++)
                count += dp[n][j];
            
            return count;
        }
    }
    
    public static void main(String[] args) {
        int n = 5;
        MobileNumericKeypadProblem obj = new MobileNumericKeypadProblem();
        
        System.out.println(obj.new SimpleRecursiveSolution().countNumbers(n)); // 2062
        System.out.println(obj.new DPSolution().countNumbers(n)); // 2062
    }
}
