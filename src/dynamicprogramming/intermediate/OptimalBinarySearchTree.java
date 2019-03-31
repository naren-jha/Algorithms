package dynamicprogramming.intermediate;

/**
 * @author Narendra Jha
 *
 * CLRS 15.5
 * https://www.geeksforgeeks.org/optimal-binary-search-tree-dp-24/
 */
public class OptimalBinarySearchTree {

	class SimpleRecursiveSolution {
		// T(n): Exponential
		public float optimalBst(float[] p, float[] q, int i, int j) {
			// base case
			if (j == i-1)
				return q[i-1];
			
			float sum = q[i-1];
			for (int k = i; k <= j; k++)
				sum += p[i] + q[i];
				
			float minCost = Float.MAX_VALUE;
			for (int r = i; r <= j; r++) {
				float cost = optimalBst(p, q, i, r-1) + optimalBst(p, q, r+1, j) + sum;
				if (cost < minCost)
					minCost = cost;
			}
			return minCost;
		}
	}
	
	class DPSolution {
		// Bottom-up tabulation
		// T(n): O(n^3), S(n): O(n^2)
		public float optimalBst(float[] p, float[] q, int n) {
			// e[i][j] stores expected cost of searching optimal binary search tree
			// containing keys k[i..j], e[1][n] will hold the final result
			float[][] e = new float[n+2][n+1];
			
			// to store sum of probabilities
			float[][] w = new float[n+2][n+1];
			
			for (int i = 1; i <= n+1; i++)
				e[i][i-1] = w[i][i-1] = q[i-1];
			
			for (int len = 1; len <= n; len++) {
				for (int i = 1; i <= n - len + 1; i++) {
					int j = i + len - 1;
					e[i][j] = Float.MAX_VALUE;
					w[i][j] = w[i][j-1] + p[j] + q[j];
					
					for (int r = i; r <= j; r++) {
						float cost = e[i][r-1] + e[r+1][j] + w[i][j];
						if (cost < e[i][j])
							e[i][j] = cost;
					}
				}
			}
			
			return e[1][n];
		}
		
		/*
		 * We can extend above solution to print Optimal Binary Search Tree
		 * CLRS: Exercise 15.5-1
		 */
		public void printOptimalBst(float[] p, float[] q, int n) {
			// e[i][j] stores expected cost of searching optimal binary search tree
			// containing keys k[i..j], e[1][n] will hold the final result
			float[][] e = new float[n+2][n+1];
			
			// to store sum of probabilities
			float[][] w = new float[n+2][n+1];
			
			// root[i][j] stores root key number for optimal binary search tree
			// containing keys k[i..j]
			int[][] root = new int[n+1][n+1];
			
			for (int i = 1; i <= n+1; i++)
				e[i][i-1] = w[i][i-1] = q[i-1];
			
			for (int len = 1; len <= n; len++) {
				for (int i = 1; i <= n - len + 1; i++) {
					int j = i + len - 1;
					e[i][j] = Float.MAX_VALUE;
					w[i][j] = w[i][j-1] + p[j] + q[j];
					
					for (int r = i; r <= j; r++) {
						float cost = e[i][r-1] + e[r+1][j] + w[i][j];
						if (cost < e[i][j]) {
							e[i][j] = cost;
							root[i][j] = r;
						}
					}
				}
			}
			
			printBst(root, 1, n, 0, null);
		}
		
		private void printBst(int[][] root, int i, int j, int parent, String child) {
			// Base case: when we reach a leaf node
			if (j == i-1) {
				System.out.println("d" + (i-1) + " is the " + child + " child of k" + parent);
				return;
			}
			
			int r = root[i][j];
			if (parent == 0)
				System.out.println("k" + r + " is the root");
			else
				System.out.println("k" + r + " is the " + child + " child of k" + parent);
			
			printBst(root, i, r-1, r, "left");
			printBst(root, r+1, j, r, "right");
		}
	}
	
	public static void main(String[] args) {
		float[] p = {0.00f, 0.15f, 0.10f, 0.05f, 0.10f, 0.20f};
		float[] q = {0.05f, 0.10f, 0.05f, 0.05f, 0.05f, 0.10f};
		int n = 5;
		OptimalBinarySearchTree o = new OptimalBinarySearchTree();
		
		System.out.println(o.new SimpleRecursiveSolution().optimalBst(p, q, 1, n)); // 2.75
		System.out.println(o.new DPSolution().optimalBst(p, q, n)); // 2.75
		
		System.out.println();
		o.new DPSolution().printOptimalBst(p, q, n);
		/*
		 * k2 is the root
		 * k1 is the left child of k2
		 * d0 is the left child of k1
		 * d1 is the right child of k1
		 * k5 is the right child of k2
		 * k4 is the left child of k5
		 * k3 is the left child of k4
		 * d2 is the left child of k3
		 * d3 is the right child of k3
		 * d4 is the right child of k4
		 * d5 is the right child of k5
		 */
	}
}
