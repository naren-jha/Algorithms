package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/minimum-number-of-squares-whose-sum-equals-to-given-number-n/

public class SquareSumToNumber {

    class SimpleRecursiveSolution {
        // T(n): Exp
        public int minSquares(int n) {
            // Base case:
            if (n <= 3)
                return n;
            
            int res = n;
            for (int x = 1; x <= Math.sqrt(n); x++)
                res = Math.min(res, 1 + minSquares(n - x*x));
            return res;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n*sqrt(n)), S(n): O(n)
        public int minSquares(int n) {
            int[] res = new int[n+1];
            res[0] = 0; res[1] = 1; res[2] = 2; res[3] = 3;
            
            for (int i = 4; i <= n; i++) {
                res[i] = i;
                for (int x = 1; x <= Math.sqrt(i); x++)
                    res[i] = Math.min(res[i], 1 + res[i - x*x]);
            }
            return res[n];
        }
    }
    
    public static void main(String[] args) {
        SquareSumToNumber o = new SquareSumToNumber();
        int n = 6;
        
        System.out.println(o.new SimpleRecursiveSolution().minSquares(n)); // 3
        System.out.println(o.new DPSolution().minSquares(n)); // 3
    }
}
