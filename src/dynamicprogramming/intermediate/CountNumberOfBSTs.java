package dynamicprogramming.intermediate;

// http://www.geeksforgeeks.org/program-nth-catalan-number/
// https://youtu.be/YDf982Lb84o

public class CountNumberOfBSTs {

	// The problem is equivalent to finding nth catalan number
	
	// T(n): O(n^2), S(n): O(n)
	public static int numberOfBSTs(int n) {
		int[] dp = new int[n+1];
		dp[0] = dp[1] = 1;
		
		for (int j = 2; j <= n; j++)
			for (int i = 0; i < j; i++)
				dp[j] += dp[i] * dp[j-i-1];
		
		return dp[n];
	}
	
	/* NOTE:
	 * There are other variations of this problem which has exactly same solution:
	 * Like find the total number of BSTs possible given preorder traversal of BST
	 * https://youtu.be/RUB5ZPfKcnY
	 * Similary for inorder, and postorder as well.
	 * 
	 * The trick here is that no matter what traversal of BST you are given, the problem
	 * has a generic solution for 'n' number of given keys, to count total number of BSTs
	 */
	
	public static void main(String[] args) {
		System.out.println(numberOfBSTs(4)); // 14
	}
}
