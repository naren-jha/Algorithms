package dynamicprogramming.basic;

// https://www.geeksforgeeks.org/find-maximum-possible-stolen-value-houses/
// https://www.geeksforgeeks.org/maximum-sum-such-that-no-two-elements-are-adjacent/

public class MaximumSumNoTwoAdjacent {

	/*
	 * 1. If last element is included, then max sum will be
	 * maxSum(a, n) = a[n-1] + maxSum(a, n-2)
	 * this is because, if last element is included then next
	 * adjacent element cannot be include.
	 * 
	 * 2. If last element is not included, then max sum will be
	 * maxSum(a, n) = maxSum(a, n-1)
	 * 
	 * Combining 1 and 2, we get recurrence equation
	 * maxSum(a, n) = max(a[n-1] + maxSum(a, n-2), maxSum(a, n-1))
	 */
	class SimpleRecursiveSolution {
		
		// T(n): O(2^n)
		public int maxSum(int[] a) {
			return maxSumUtil(a, a.length);
		}
		
		private int maxSumUtil(int[] a, int n) {
			if (n <= 0)
				return 0;
			
			return Math.max(a[n-1] + maxSumUtil(a, n-2), 
							maxSumUtil(a, n-1));
		}
	}
	
	class DPSolution {
		// bottom-up tabulation
		// T(n): O(n), S(n): O(n)
		public int maxSum(int[] a) {
			int n = a.length;
			if (n == 0)
				return 0;
			if (n == 1)
				return a[0];
			if (n == 2)
				return Math.max(a[0], a[1]);
			
			// create an array 'res', where res[i] represents
			// solution to problem of size i+1
			int[] res = new int[n];
			
			res[0] = a[0];
			res[1] = Math.max(a[0], a[1]);
			for (int i = 2; i < n; i++) {
				res[i] = Math.max(a[i] + res[i-2], res[i-1]);
			}
			
			return res[n-1];
		}
		
		/*
		 * In above solution, since we are always accessing
		 * only two immediate previous variables from array,
		 * so we can manage with just two separate variables
		 * instead of using whole array. see solution below.
		 */
		// T(n): O(n), S(n): O(1)
		public int maxSumSpaceOptimized(int[] a) {
			int n = a.length;
			if (n == 0)
				return 0;
			if (n == 1)
				return a[0];
			if (n == 2)
				return Math.max(a[0], a[1]);
			
			int i_2 = a[0];
			int i_1 = Math.max(a[0], a[1]);
			int maxSum = 0;
			for (int i = 2; i < n; i++) {
				maxSum = Math.max(a[i-1] + i_2, i_1);
				i_2 = i_1;
				i_1 = maxSum;
			}
			
			return maxSum;
		}
	}
	
	public static void main(String[] args) {
		int[] a = {5, 5, 10, 100, 10, 5};
		MaximumSumNoTwoAdjacent obj = new MaximumSumNoTwoAdjacent();
		System.out.println(obj.new SimpleRecursiveSolution().maxSum(a)); // 110
		System.out.println(obj.new DPSolution().maxSum(a)); // 110
		System.out.println(obj.new DPSolution().maxSumSpaceOptimized(a)); // 110
	}
}
