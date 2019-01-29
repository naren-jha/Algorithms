package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/cutting-a-rod-dp-13/

public class RodCuttingProblem {
	
	class SimpleRecursiveSolution {
		
		/*
		 * PSEUDO:
		 * 
		 * CUT-ROD(p, n)
		 * 		if n==0
		 * 			return 0
		 * 
		 * 		maxRevenue = -infinity
		 * 		for (i = 1; i <= n; i++)
		 * 			maxRevenue = max(maxRevenue, (p[i] + CUT-ROD(p, n-i)));
		 * 
		 * 		return maxRevenue;
		 */
		
		// T(n): Exponential, as it considers all possible cuts
		// with 2^(n-1) total possible combinations
		public int cutRod(int[] price, int n) {
			if (n == 0)
				return 0;
			int maxRevenue = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++)
				maxRevenue = Math.max(maxRevenue, (price[i] + cutRod(price, n-i-1)) );
			return maxRevenue;
		}
		
	}
	
	class DPSolution {
		
		// top-to-bottom memoization
		// T(n): O(n^2)
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
			for (int i = 0; i < n; i++)
				maxRevenue = Math.max(maxRevenue, (price[i] + cutRodMemoizedUtil(price, n-i-1, res)) );
			res[n] = maxRevenue;
			return res[n];
		}
		
		// bottom-up tabulation
		// T(n): O(n^2)
		public int cutRodBottomUp(int[] price, int n) {
			int[] res = new int[n+1];
			res[0] = 0;
			
			for (int j = 1; j <= n; j++) {
				int maxRevenue = Integer.MIN_VALUE;
				for (int i = 0; i < j; i++)
					maxRevenue = Math.max(maxRevenue, (price[i] + res[j-i-1]) );
				res[j] = maxRevenue;
			}
			return res[n];
		}
		
		/*
		 * Reconstructing a solution:
		 * We can extend above solutions to print the sizes of cuts 
		 * that led to optimal solution
		 */
		private void cutRodBottomUpExtended(int[] price, int n, int[] res, int[] cuts) {
			res[0] = 0;
			for (int j = 1; j <= n; j++) {
				int maxRevenue = Integer.MIN_VALUE;
				for (int i = 0; i < j; i++) {
					if (maxRevenue < (price[i] + res[j-i-1])) {
						maxRevenue = (price[i] + res[j-i-1]);
						cuts[j] = i+1;
					}
				}
				res[j] = maxRevenue;
			}
		}

		public void printCutRodSolution(int[] price, int n) {
			int[] res = new int[n+1];
			int[] cuts = new int[n+1];
			cutRodBottomUpExtended(price, n, res, cuts);
			
			while (n > 0) {
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
