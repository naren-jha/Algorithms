package basic.array.problems;

// https://www.geeksforgeeks.org/given-a-sorted-and-rotated-array-find-if-there-is-a-pair-with-a-given-sum/

public class CheckForAPairInSortedRotatedArray {

	// This problem is an extension of previous problem: CheckForAPairWithGivenSum
	
	// T(n): O(n), S(n): O(1)
	public static boolean isPair(int[] a, int sum) {
		int n = a.length;
		int i;
		for (i = 0; i < n-1; i++)
			if (a[i] > a[i+1])
				break;
		// i is now the index of pivot element
		
		int l = i+1; // index where sorted array begins
		int r = i; // index where sorted array ends
		
		while (l != r) {
			if (a[l] + a[r] == sum)
				return true;
			else if (a[l] + a[r] < sum)
				l = (l+1) % n;
			else
				r = (r-1+n) % n;
		}
		return false;
	}
	
	// We can use Hashing technique as well, just like we did in previous problem
	// but that will take O(n) space, therefore for sorted array above approach is 
	// better. The step to find the pivot element index can be optimized to O(Logn) 
	// using the Binary Search approach 
	
	// How to count all pairs having given sum
	// T(n): O(n), S(n): O(1)
	public static int countPair(int[] a, int sum) {
		int n = a.length;
		int i;
		for (i = 0; i < n-1; i++)
			if (a[i] > a[i+1])
				break;
		// i is now the index of pivot element
		
		int l = i+1; // index where sorted array begins
		int r = i; // index where sorted array ends
		int count = 0;
		while (l != r) {
			if (a[l] + a[r] == sum) {
				count++;
				
				// This condition is required to be checked, otherwise l and r 
	            // will cross each other and loop will never terminate
				if (l == (r-1+n) % n)
					break;
				
				l = (l+1) % n;
				r = (r-1+n) % n;
			}
			else if (a[l] + a[r] < sum)
				l = (l+1) % n;
			else
				r = (r-1+n) % n;
		}
		return count;
	}
	
	public static void main(String[] args) {
		int[] a = {11, 15, 6, 8, 9, 10};
		int sum = 16;
		System.out.println(isPair(a, sum)); // true
		
		sum = 22;
		System.out.println(isPair(a, sum)); // false
		
		sum = 21;
		System.out.println(countPair(a, sum)); // 2
	}
}
