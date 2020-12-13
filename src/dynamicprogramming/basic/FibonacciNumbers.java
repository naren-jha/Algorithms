package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
    
// F0 = 0, F1 = 1, F2 = 1, F3 = 2, F4 = 3, F5 = 5 . . . .

/*
fib(n) = n, if n <= 1
fib(n) = fib(n-1) + fib(n-2), otherwise
*/

public class FibonacciNumbers {
    
    // TC: O(2^n), SC: O(n) 
    // max height (or max number of levels) of recursion tree is n and 
    // for each node, we are making 2 recursive calls
    private class SimpleButInefficientSolution {
        public int fib(int n) {
            if (n <= 1) return n;
            return fib(n-1) + fib(n-2);
        }
    }
    
    private class DPSolution {
        // top-to-bottom memoized solution
        // TC: O(n), SC: O(n)
        public int fibMemoized(int n, int[] fib) {
            if (n <= 1) return n;
            
            if (fib[n] != -1) return fib[n];
            
            fib[n] = fibMemoized(n-1, fib) + fibMemoized(n-2, fib);
            return fib[n];
        }
        
        // bottom-up tabulation method
        // TC: O(n), SC: O(n)
        public int fibBottomUp(int n) {
            int[] f = new int[n+1]; // 1 extra to handle n = 0 case
            f[0] = 0; f[1] = 1;
            
            for (int i = 2; i <= n; i++)
                f[i] = f[i-1] + f[i-2];
            
            return f[n];
        }
        
        // bottom-up tabulation (space optimized) method
        // TC: O(n), SC: O(1)
        public int fibBottomUpSpaceOptimized(int n) {
            if (n <= 1) return n; // edge case
            
            int a = 0, b = 1, c = 0;
            for (int i = 2; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return c;
        }
        
        // bottom-up tabulation (space optimized) method. Uses only two variables.
        // TC: O(n), SC: O(1)
        public int fibBottomUpSpaceOptimized2Variables(int n) {
            if (n <= 1) return n; // edge case
            
            int a = 0, b = 1;
            for (int i = 2; i <= n; i++) {
                b = a + b;
                a = b - a;
            }
            return b;
        }
    }
    
    // http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibFormula.html
    // TC: O(1), SC: O(1)
    private class ConstantTimeAndSpaceSolution {
        public int fib(int n) {
            double phi = (1 + Math.sqrt(5)) / 2;
            return (int) Math.round(Math.pow(phi, n) / Math.sqrt(5));
        }
    }

    public static void main(String[] args) {
        int n = 25;
        System.out.println(new FibonacciNumbers().new SimpleButInefficientSolution().fib(n));
        
        int[] fib = new int[n+1]; // 1 extra to handle n = 0 case
        Arrays.fill(fib, -1);
        System.out.println(new FibonacciNumbers().new DPSolution().fibMemoized(n, fib));
        
        System.out.println(new FibonacciNumbers().new DPSolution().fibBottomUp(n));
        System.out.println(new FibonacciNumbers().new DPSolution().fibBottomUpSpaceOptimized(n));
        System.out.println(new FibonacciNumbers().new DPSolution().fibBottomUpSpaceOptimized2Variables(n));
        
        System.out.println(new FibonacciNumbers().new ConstantTimeAndSpaceSolution().fib(n));
    }

}
