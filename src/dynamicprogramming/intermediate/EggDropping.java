package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/egg-dropping-puzzle-dp-11/
// https://www.youtube.com/watch?v=iOaRjDT0vjc

public class EggDropping {

	/*
	 * The idea is that we consider each floor as the first drop 
	 * and solve problem for it, and then take minimum of solutions
	 * for all the floor, to get the minimum attempt needed.
	 * 
	 * When we drop an egg from a floor x, there can be two possible cases:
	 * 1. The egg breaks 
	 * 2. The egg doesn’t break
	 * 
	 * 1: If the egg breaks after dropping from xth floor, then we only 
	 * need to check for floors lower than x with remaining eggs; 
	 * so the problem reduces to x-1 floors and n-1 eggs
	 * 
	 * 2: If the egg doesn’t break after dropping from the xth floor, 
	 * then we only need to check for floors higher than x; so the 
	 * problem reduces to k-x floors and n eggs.
	 * 
	 * And since we need to minimize the number of attempts in worst case, 
	 * we take the maximum of two cases. We consider the max of above two 
	 * cases for every floor and choose the floor which yields minimum 
	 * number of attempts.
	 * 
	 * Below is recurrence:
	 * 
	 * k: Number of floors
	 * n: Number of Eggs
	 * 
	 * eggDrop(n, k) = 1 + min{max(eggDrop(n - 1, x - 1), eggDrop(n, k - x)): 
                 			x = {1, 2, ..., k}}
                 			
	 * We take max of two possibilities in above recurrence as 
	 * we need to consider worst case solution for each subproblem
                 			
	 */
	class SimpleRecursiveSolution {
		// T(n): Exp
		public int eggDrop(int n, int k) {
			// If there are no floors, then no trial is needed. OR if there is 
		    // one floor, one trial is needed. 
			if (k == 0 || k == 1)
				return k;
			
			// We need k trials for one egg and k floors
			if (n == 1)
				return k;
			
			// Consider all droppings from 1st floor to kth floor and 
		    // return the minimum of these values plus 1
			int min = Integer.MAX_VALUE;
			int res;
			for (int x = 1; x <= k; x++) {
				res = 1 + Math.max(eggDrop(n-1, x-1),  eggDrop(n, k-x));
				min = Math.min(min, res);
			}
			return min;
		}
	}
	
	class DPSolution {
		// T(n): O(n*k^2), S(n): O(nk)
		public int eggDrop(int n, int k) {
			// create a 2-d array res, where res[i][j] will represent  
		    // minimum number of trials needed for i eggs and j floors
			int[][] res = new int[n+1][k+1];
			
			// We need 1 trial for 1 floor and 0 trial for 0 floor
			for (int i = 1; i <= n; i++) {
				res[i][0] = 0;
				res[i][1] = 1;
			}
			
			// We always need j trials for 1 egg and j floors
			for (int j = 1; j <= k; j++) {
				res[1][j] = j;
			}
			
			// fill rest of the table
			int tmp;
			for (int i = 2; i <= n; i++) {
				for (int j = 2; j <= k; j++) {
					res[i][j] = Integer.MAX_VALUE;
					for (int x = 1; x <= j; x++) {
						tmp = 1 + Math.max(res[i-1][x-1], res[i][j-x]);
						res[i][j] = Math.min(res[i][j], tmp);
					}
				}
			}
			
			return res[n][k];
		}
		
		// We should be able to space optimize above solution as at any
		// iteration, we need access to only two rows: current row, and
		// immediate previous row
	}
	
	// There is a O(nlgk) solution of this problem using binomial coefficients
	// https://www.geeksforgeeks.org/eggs-dropping-puzzle-binomial-coefficient-and-binary-search-solution/
	
	public static void main(String[] args) {
		int n = 2;
		int k = 10;
		
		EggDropping e = new EggDropping();
		System.out.println(e.new SimpleRecursiveSolution().eggDrop(n, k)); // 4
		System.out.println(e.new DPSolution().eggDrop(n, k)); // 4
	}
}
