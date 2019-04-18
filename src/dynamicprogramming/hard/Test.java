package dynamicprogramming.hard;

public class Test {
	
	private static int kadane(int[] a) {
		int maxSoFar = Integer.MIN_VALUE;
		int maxEndingHere = 0;
		boolean isPositive = false;
		
		for (int i = 0; i < a.length; i++) {
			maxEndingHere += a[i];
			if (maxEndingHere < 0) {
				maxEndingHere = 0;
			}
			else if (maxSoFar < maxEndingHere) {
				maxSoFar = maxEndingHere;
				isPositive = true;
			}			
		}
		System.out.println(isPositive);
		// there is at least one positive number
		if (isPositive)
			return maxSoFar;
		
		// Special case: when all numbers in array a[] are negative
		maxSoFar = a[0];
		for (int i = 0; i < a.length; i++) {
			if (maxSoFar < a[i])
				maxSoFar = a[i];
		}
		
		return maxSoFar;
	}
	
	public static void main(String[] args) {
		int[] a = {-2, -6, -2, -1, -3};
		System.out.println(kadane(a));
	}
	

}
