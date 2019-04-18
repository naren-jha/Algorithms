package dynamicprogramming.hard;

import java.util.Stack;

// https://www.geeksforgeeks.org/maximum-profit-by-buying-and-selling-a-share-at-most-k-times/

public class MaximumProfitByBuyingAndSellingShareAtMostKtimes {
	
	// Explanation : https://youtu.be/oDhu5uGq_ic

	/*
	 * Slower solution - easier to understand
	 * profit[i][j] = max{
	 * 						profit[i][j-1], 
	 * 						max(price[j] - price[m] + profit[i-1][m]), where m = 0 to j-1
	 * 					 }
	 */	
	// T(n): O(n^2 * k), S(n): O(n*k)
	public static int maxProfitSlow(int[] price, int k) {
		int n = price.length;
		
		// profit[i][j] stores maximum possible profit by making at most 'i' transactions 
		// till 'jth' day
		int[][] profit = new int[k+1][n];
		
		// initialize first row and first column values to 0
		
		// if k == 0: profit will be zero,
		// as we can't make any profit without any transaction
		for (int j = 0; j < n; j++)
			profit[0][j] = 0;
		
		// if n = 0 (day 0): profit will be zero, as no matter how many transactions you  
		// have, if you have only one day price, you cannot make any profit
		for (int i = 0; i <= k; i++)
			profit[i][0] = 0;
		
		// fill rest of the table in bottom-up fashion
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j < n; j++) {
				int maxVal = 0;
				for (int m = 0; m < j; m++) {
					maxVal = Math.max(maxVal, price[j] - price[m] + profit[i-1][m]); 
				}
				profit[i][j] = Math.max(profit[i][j-1], maxVal);
			}
		}
		return profit[k][n-1];
	}
	
	/*
	 * Faster solution - optimization in previous solution
	 * Optimization: We don't have to look for best day to buy, by running third loop
	 * for (int m = 0; m < j; m++), we can decide that in constant time.
	 * 
	 * profit[i][j] = max(profit[i][j-1], price[j] + maxDiff)
     * 						where maxDiff = max(maxDiff, profit[i-1][j] - price[j])
	 */
	// T(n): O(n*k), S(n): O(n*k)
	public static int maxProfit(int[] price, int k) {
		int n = price.length;
		
		int[][] profit = new int[k+1][n];
		
		for (int j = 0; j < n; j++)
			profit[0][j] = 0;
		
		for (int i = 0; i <= k; i++)
			profit[i][0] = 0;
		
		for (int i = 1; i <= k; i++) {
			int maxDiff = -price[0];
			for (int j = 1; j < n; j++) {
				profit[i][j] = Math.max(profit[i][j-1], price[j] + maxDiff);
				maxDiff = Math.max(maxDiff, profit[i-1][j] - price[j]);
			}
		}
		
		return profit[k][n-1];
	}

	// Space complexity of above solution can further be optimized to O(n)
	
	// We can also print the solution after profit[][] array is populated
	public static void printOptimalSolution(int[] price, int k) {
		// First we populate the profit array as before
		int n = price.length;
		int[][] profit = new int[k+1][n];
		
		for (int j = 0; j < n; j++)
			profit[0][j] = 0;
		for (int i = 0; i <= k; i++)
			profit[i][0] = 0;
		
		for (int i = 1; i <= k; i++) {
			int maxDiff = -price[0];
			for (int j = 1; j < n; j++) {
				profit[i][j] = Math.max(profit[i][j-1], price[j] + maxDiff);
				maxDiff = Math.max(maxDiff, profit[i-1][j] - price[j]);
			}
		}
		
		// And then print the solution
		Stack<Integer> stack = new Stack<Integer>();
		int i = k, j = n-1;
		while (true) {
			if (i == 0 || j == 0)
				break;
			
			if (profit[i][j] == profit[i][j-1]) {
				j--;
			}
			else {
				stack.push(j);
				int diff = profit[i][j] - price[j];
				for (int p = j-1; p >= 0; p--) {
					if (profit[i-1][p] - price[p] == diff) {
						i--;
						j = p;
						stack.push(j);
						break;
					}
				}
			}
		}
		
		int buyAt, sellAt;
		while (!stack.isEmpty()) {
			buyAt = stack.pop();
			sellAt = stack.pop();
			System.out.println("Buy at day " + buyAt + ", with price = " + price[buyAt]);
			System.out.println("Sell at day " + sellAt + ", with price = " + price[sellAt]);
		}
	}
	
	public static void main(String[] args) {
		int[] price = {10, 22, 5, 75, 65, 80};
		int k = 2;
		
		System.out.println(maxProfitSlow(price, k)); // 87
		System.out.println(maxProfit(price, k)); // 87
		
		printOptimalSolution(price, k);
		/*
		 * Buy at day 0, with price = 10
		 * Sell at day 1, with price = 22
		 * Buy at day 2, with price = 5
		 * Sell at day 5, with price = 80
		 */
	}
}
