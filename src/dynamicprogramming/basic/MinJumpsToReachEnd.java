package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
// https://www.geeksforgeeks.org/minimum-number-jumps-reach-endset-2on-solution/

public class MinJumpsToReachEnd {

	class SimpleRecursiveSolution {
		// T(n): Exp
		public int minJumps(int[] a) {
			int n = a.length;
			return minJumps(a, 0, n-1);
		}
		
		private int minJumps(int[] a, int src, int dest) {
			// Base condition
			if (src == dest)
				return 0;
			
			// If first element is zero, end cannot be reached
			if (a[src] == 0)
				return Integer.MAX_VALUE;
			
			// If we can directly reach to the end point from src
			if (src + a[src] >= dest)
				return 1;
			
			int minJumps = Integer.MAX_VALUE;
			for (int i = src+1; i <= src + a[src] && i <= dest; i++) {
				int minJumpFrom_i = minJumps(a, i, dest);
				if (minJumpFrom_i != Integer.MAX_VALUE && minJumpFrom_i + 1 < minJumps)
					minJumps = minJumpFrom_i + 1;
			}
			return minJumps;
		}
	}
	
	class DPSolution {
		// top-down memoization
		// T(n): O(n^2), S(n): O(n)
		public int minJumpsMem(int[] a) {
			int n = a.length;
			
			// create an array to store results, such that res[i] indicates
			// the minimum number of jumps needed to reach a[n-1] from a[i]
			int[] res = new int[n];
			Arrays.fill(res, -1);
			
			return minJumpsMem(a, 0, n-1, res);
		}
		
		private int minJumpsMem(int[] a, int src, int dest, int[] res) {
			// Base condition
			if (src == dest)
				return 0;
			
			// If first element is zero, end cannot be reached
			if (a[src] == 0) 
				return Integer.MAX_VALUE;
			
			// If we can directly reach to the end point from src
			if (src + a[src] >= dest)
				return 1;
			
			if (res[src] != -1)
				return res[src];
			
			int minJumps = Integer.MAX_VALUE;
			for (int i = src+1; i <= src + a[src] && i <= dest; i++) {
				int minJumpFrom_i = minJumpsMem(a, i, dest, res);
				if (minJumpFrom_i != Integer.MAX_VALUE && minJumpFrom_i + 1 < minJumps)
					minJumps = minJumpFrom_i + 1;
			}
			res[src] = minJumps;
			return res[src];
		}
		
		// bottom-up tabulation : approach 1
		// T(n): O(n^2), S(n): O(n)
		public int minJumps(int[] a) {
			// If first element is zero, end cannot be reached
			if (a[0] == 0)
				return Integer.MAX_VALUE;
			
			int n = a.length;
			
			// create an array to store results, such that res[i] indicates
			// the minimum number of jumps needed to reach a[n-1] from a[i]
			int[] res = new int[n]; // res[0] will hold the final result
			
			res[n-1] = 0;
			for (int i = n-2; i >= 0; i--) {
				// If a[i] is 0 then a[n-1] can't be reached from here
				if (a[i] == 0)
					res[i] = Integer.MAX_VALUE;
				
				// If we can directly reach to the end point from i
				else if (i + a[i] >= n-1)
					res[i] = 1;
				
				else {
					int min = Integer.MAX_VALUE;
					for (int j = i+1; j <= i + a[i] && j < n-1; j++) {
						if (res[j] != Integer.MAX_VALUE && res[j] + 1 < min)
							min = res[j] + 1;
					}
					res[i] = min;
				}
			}
			
			return res[0];
		}
		
		// bottom-up tabulation : approach 2 (can be ignored)
		// T(n): O(n^2), S(n): O(n)
		public int minJumps2(int[] a) {
			// If first element is zero, end cannot be reached
			if (a[0] == 0)
				return Integer.MAX_VALUE;
			
			int n = a.length;
			
			// create an array to store results, such that res[i] indicates
			// the minimum number of jumps needed to reach a[i] from a[0]
			int[] res = new int[n]; // res[n-1] will hold the final result
			
			res[0] = 0;
			for (int i = 1; i < n; i++ ) {
				int min = Integer.MAX_VALUE;
				for (int j = 0; j < i; j++) {
					if (j + a[j] >= i && res[j] != Integer.MAX_VALUE) {
						min = Math.min(min, res[j]+1);
						
						break; // As soon as any index becomes reachable from 
						// any previous index it is given the value 1 + moves 
						// till the previous index without bothering for the 
						// next indexes which may only give higher value to it
					}
				}
				res[i] = min;
			}
			
			return res[n-1];
		}
	}
	
	class LinearTimeSolution {
		public int minJumps(int[] a) {
			int n = a.length;
			if (n <= 1)
				return 0;
			if (a[0] == 0)
				return -1;
			
			int maxReach = a[0]; // Maximal reachable index in the array so far
			int step = a[0]; // Number of steps we can still take 
			int jump = 1; // minimum jumps necessary to reach end
			
			for (int i = 1; i < n; i++) {
				if (i == n-1)
					return jump;
				
				maxReach = Math.max(maxReach, a[i]+i);
				step--;
				if (step == 0) {
					jump++; 
					step = maxReach - i;
					if (step <= 0) // if we cannot go any further
						return -1;
					
					// Optimization: if we can reach end directly from this point
					if (maxReach >= n-1)
						return jump;
				}
			}
			return jump;
		}
	}
	
	public static void main(String[] args) {
		int[] a = {1, 3, 6, 3, 2, 3, 6, 8, 9, 5};
		MinJumpsToReachEnd o = new MinJumpsToReachEnd();
		System.out.println(o.new SimpleRecursiveSolution().minJumps(a)); // 4
		System.out.println(o.new DPSolution().minJumpsMem(a)); // 4
		System.out.println(o.new DPSolution().minJumps(a)); // 4
		System.out.println(o.new DPSolution().minJumps2(a)); // 4
		System.out.println(o.new LinearTimeSolution().minJumps(a)); // 4
	}
}
