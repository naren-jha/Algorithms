package basic.array.problems;

// https://practice.geeksforgeeks.org/problems/frequency-of-array-elements/0#ExpectOP
// https://www.geeksforgeeks.org/count-frequencies-elements-array-o1-extra-space-time/

public class CountFrequenciesOfAllElements {

	public static void countFreq(int[] a) {
		int i = 0, n = a.length;
		while (i < n) {
			if (a[i] <= 0) {
				i++;
				continue;
			}
			
			int ei = a[i] - 1; // element index
			if (a[ei] <= 0) {
				a[ei]--;
				a[i] = 0;
				i++;
			}
			else {
				a[i] = a[ei];
				a[ei] = -1;
			}
		}
		
		System.out.println("Element\tCount");
		for (i = 0; i < n; i++)
			System.out.println((i+1) + "\t" + Math.abs(a[i]));
	}
	
	// Approach 2
	public static void countFreq_A2(int[] a) {
		int n = a.length;
		
		// Subtract 1 from every element so that the elements become in range from 0 to n-1 
		for (int i = 0; i < n; i++)
			a[i]--;
		
		// Use every element a[i] as index and add 'n' to element present 
		// at a[i]%n to keep track of count of occurrences of a[i] 
		for (int i = 0; i < n; i++)
			a[a[i] % n] = a[a[i] % n] + n;
		
		// To print counts, simply print the number of times n 
        // was added at index corresponding to every element 
		System.out.println("Element\tCount");
		for (int i = 0; i < n; i++)
			System.out.println((i+1) + "\t" + a[i]/n);
	}
	
	// In above approach, infact we can merge the first two for loop into one
	public static void countFreq_A3(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			a[i]--;
			a[a[i] % n] += n;
		}
		
		System.out.println("Element\tCount");
		for (int i = 0; i < n; i++)
			System.out.println((i+1) + "\t" + a[i]/n);
	}
	
	public static void main(String[] args) {
		int[] a = {7, 5, 7, 3, 3, 1, 2};
		//countFreq(a);
		//countFreq_A2(a);
		countFreq_A3(a);
	}
}
