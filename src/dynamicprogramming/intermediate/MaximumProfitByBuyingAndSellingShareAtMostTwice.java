package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/maximum-profit-by-buying-and-selling-a-share-at-most-twice/

public class MaximumProfitByBuyingAndSellingShareAtMostTwice {

	/*
	 * Idea is this:
	 * Max profit with at most two transactions =
       			MAX {
       					max profit with one transaction in subarray a[0..i] +
            		 	max profit with one transaction in aubarray a[i..n-1]
            		}
		Where i varies from 0 to n-1
		
		Note:
		Maximum possible profit in one transaction can be calculated using 
		O(n) algorithm (second approach) discussed in following post:
		https://www.geeksforgeeks.org/maximum-difference-between-two-elements/
	 */
	
	/*
	 * ALGORITHM:
	 * 1. Create a table profit[0..n-1] and initialize all values in it to 0
	 * 
	 * 2. Traverse a[] from right to left and update profit[i] such that profit[i] 
	 * 	  stores maximum profit achievable from one transaction in subarray a[i..n-1]
	 * 
	 * 3. Traverse a[] from left to right and update profit[i] such that profit[i] 
	 * 	  stores maximum profit achievable from two transactions: one in subarray 
	 * 	  a[0..i] and other in subarray a[i..n-1]
	 * 
	 * 4. profit[n-1] will contain the final result.
	 */
	
	// T(n): O(n), S(n): O(n)
	public static int maxProfit(int[] a) {
		int n = a.length;
		int[] profit = new int[n];
		
		profit[n-1] = 0;
		int max = a[n-1];
		for (int i = n-2; i >= 0; i--) {
			profit[i] = Math.max(profit[i+1], max - a[i]);
			max = Math.max(max, a[i]);
		}
		
		int min = a[0];
		for (int i = 1; i < n; i++) {
			profit[i] = Math.max(profit[i-1], profit[i] + (a[i] - min) );
			min = Math.min(min, a[i]);
		}
		
		return profit[n-1];
	}
	
	// SOME NOTES:
	
	/*
	 * In above solution, we see that in 2nd loop, we are finding maximum profit in two 
	 * transactions: "one in a[0..i] and other in a[i..n-1]".
	 * 
	 * Initially, it looks like, the solution should have been: "one transaction in 
	 * a[0..i] and other in a[i+1..n-1]", but that may not give correct result all the 
	 * time. Reason: we are looking for maximum achievable profit from AT MOST two 
	 * transactions. What it means is that number of transactions could be two or one, 
	 * what we are interested in is maximizing profit with AT MOST two transactions, so 
	 * profit may be maximum with just one transaction
	 * 
	 * When we do one transaction in a[0..i] and other in a[i..n-1], what it basically 
	 * means is that we are including the possibility of doing nothing on ith day, as 
	 * buy and sell on ith day can cancel out each-other, which includes the case of 
	 * having just one transaction.
	 * 
	 * GFG mentions that they are solving for "one transaction in a[0..i] and other
	 * in a[i+1..n-1]", which is incorrect, however their code implementation is 
	 * correct.
	 */
	
	/*
	 * In GFG solution, inside the two 'for' loops, they are updating 'min' and 'max' 
	 * before finding the profit for ith day. What that basically means is that, they  
	 * are including the possibility of buying and selling on the same day: which is a 
	 * redundant operation, as buying and selling on the same day is not going to give 
	 * us any profit, and therefore is not going to contribute to the maximum profit.
	 */
	
	public static void main(String[] args) {
		int[] price = {2, 30, 15, 10, 8, 25, 80};
		System.out.println(maxProfit(price)); // 100
	}
}
