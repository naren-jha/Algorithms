package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/
	
// F0 = 0, F1 = 1, F2 = 1, F3 = 2, F4 = 3, F5 = 5 . . . .

/*
fib(n) = n, if n <= 1
fib(n) = fib(n-1) + fib(n-2), otherwise
*/

public class FibonacciNumbers {
	
	private class SimpleButInefficientSolution {
		public int fib(int n) {
			if (n <= 1)
				return n;
			return fib(n-1) + fib(n-2);
		}
	}
	
	private class DPSolution {
		// top-to-bottom memoized solution
		public int fibMemoized(int n, int[] fib) {
			if (n <= 1)
				return n;
			
			if (fib[n] != -1)
				return fib[n];
			
			fib[n] = fibMemoized(n-1, fib) + fibMemoized(n-2, fib);
			return fib[n];
		}
		
		// bottom-up tabulation method
		public int fibBottomUp(int n) {
			int[] f = new int[n+1]; // 1 extra to handle n = 0 case
			f[0] = 0; f[1] = 1;
			
			for (int i = 2; i <= n; i++) {
				f[i] = f[i-1] + f[i-2];
			}
			return f[n];
		}
		
		// bottom-up tabulation (space optimized) method
		public int fibBottomUpSpaceOptimized(int n) {
			int a = 0, b = 1, c;
			if (n == 0)
				return a;
			for (int i = 2; i <= n; i++) {
				c = a + b;
				a = b;
				b = c;
			}
			return b;
		}
	}
	
	private class ConstantTimeAndSpaceSolution {
		public int fib(int n) {
			return (int) (Math.pow((1 + Math.sqrt(5))/2, n) / Math.sqrt(5));
		}
	}

	public static void main(String[] args) {
		System.out.println(new FibonacciNumbers().new SimpleButInefficientSolution().fib(5));
		
		int[] fib = new int[46]; // 1 extra to handle n = 0 case
		Arrays.fill(fib, -1);
		System.out.println(new FibonacciNumbers().new DPSolution().fibMemoized(45, fib));
		
		System.out.println(new FibonacciNumbers().new DPSolution().fibBottomUp(45));
		System.out.println(new FibonacciNumbers().new DPSolution().fibBottomUpSpaceOptimized(45));
		
		System.out.println(new FibonacciNumbers().new ConstantTimeAndSpaceSolution().fib(45));
	}

}
