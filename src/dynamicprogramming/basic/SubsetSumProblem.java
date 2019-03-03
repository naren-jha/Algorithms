package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/subset-sum-problem-dp-25/
// https://www.geeksforgeeks.org/subset-sum-problem-osum-space/

public class SubsetSumProblem {
	
	class SimpleRecursiveSolution {
		public boolean isSubsetSum(int[] a, int n, int sum) {
			// Base conditions
			if (sum == 0)
				return true;
			if (sum != 0 && n == 0)
				return false;
			
			// when last number cannot be included
			if (a[n-1] > sum)
				return isSubsetSum(a, n-1, sum);
			
			return isSubsetSum(a, n-1, sum) || isSubsetSum(a, n-1, sum-a[n-1]);
		}
	}
	
	class DPSolution {
		// bottom-up tabulation
		// T(n) = O(n*sum)
		// S(n) = O(n*sum)
		public boolean isSubsetSum(int[] a, int n, int sum) {
			boolean[][] res = new boolean[n+1][sum+1];
			
			// when sum == 0
			for (int i = 0; i <= n; i++)
				res[i][0] = true;
			
			// when (sum != 0 && n == 0)
			for (int j = 1; j <= sum; j++)
				res[0][j] = false; // redundant in Java
			
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= sum; j++) {
					if (a[i-1] > j) // when last number cannot be included
						res[i][j] = res[i-1][j];
					else
						res[i][j] = res[i-1][j] || res[i-1][j-a[i-1]];
				}
			}
			
			// print complete result table
			/*for (int i = 0; i <= n; i++) {
				for (int j = 0; j <= sum; j++) {
					System.out.print(res[i][j] + " ");
				}
				System.out.println();
			}*/
			
			return res[n][sum];
		}
		
		/*
		 * We can solve this problem in O(sum) space. To fill each row in above solution,
		 * we use data only from its immediate previous row, so alternate rows can be 
		 * used to fill data. Means, at any time we need to maintain only 2 rows, 
		 * irrespective of the value of 'n'. See the solution below.
		 */
		
		// bottom-up tabulation, space optimized
		// T(n) = O(n*sum)
		// S(n) = O(sum)
		public boolean isSubsetSumSpaceOptimized(int[] a, int n, int sum) {
			boolean[][] res = new boolean[2][sum+1];
			
			// when sum == 0
			res[0][0] = true;
			res[1][0] = true;
			
			// when (sum != 0 && n == 0)
			for (int j = 1; j <= sum; j++)
				res[0][j] = false; // redundant in Java
			
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= sum; j++) {
					if (a[i-1] > j) // when last number cannot be included
						res[i % 2][j] = res[(i-1) % 2][j];
					else
						res[i % 2][j] = res[(i-1) % 2][j] 
													|| res[(i-1) % 2][j-a[i-1]];
				}
			}
			
			return res[n % 2][sum];
		}
	}

	public static void main(String[] args) {
		int[] a = {3, 34, 4, 12, 5, 2};
		int n = a.length;
		int sum = 9;
		
		SubsetSumProblem obj = new SubsetSumProblem();
		System.out.println(obj.new SimpleRecursiveSolution().isSubsetSum(a, n, sum)); // true
		
		System.out.println(obj.new DPSolution().isSubsetSum(a, n, sum)); // true
		System.out.println(obj.new DPSolution().isSubsetSumSpaceOptimized(a, n, sum)); // true
	}

}
