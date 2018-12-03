package sorting;

import java.util.Scanner;

public class RadixSort {

	// Method to sort given input array using radix sort algorithm
	public static void radixSort(int[] a) {
		// Find the maximum number to know number of digits
		int max = max(a);
		
		// Do counting sort for every digit. Note that instead 
        // of passing digit number, pos is passed. pos is 10^i 
        // where i is current digit number
		for (int pos = 1; max/pos > 0; pos *= 10) {
			countingSort(a, pos);
		}
	}
	
	// A utility method to get maximum value in a given array
	public static int max(int[] a) {
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max)
				max = a[i];
		}
		return max;
	}
	
	// Method to do digit sort as per the digit represented by 'pos'
	public static void countingSort(int[] a, int pos) {
		int n = a.length;
		int[] b = new int[n];
		
		// k for radix sort will be 9, as 
		// values in each digit sort ranges from 0 to 9
		int[] c = new int[10];
		for (int i = 0; i < 10; i++)
			c[i] = 0;
		
		// store count of each element
		for (int j = 0; j < n; j++)
			c[(a[j]/pos) % 10]++;
		
		// c[i] now contains the number of elements equal to i
		
		for (int i = 1; i < 10; i++)
			c[i] = c[i] + c[i-1];
		
		// c[i] now contains the number of elements less than or equal to i.
		
		// build the output array
		for (int j = n - 1; j >= 0; j--){
			b[c[(a[j]/pos) % 10] - 1] = a[j];
			c[(a[j]/pos) % 10]--;
		}
		
		// copy sorted output to original array so that 
		// it contains sorted numbers according to current digit
		for (int j = 0; j < n; j++)
			a[j] = b[j];
	}
	
	public static void main(String[] args) {
		System.out.println("Enter array elements separated by space:");
		Scanner in = new Scanner(System.in);
		String[] input = in.nextLine().split(" ");
		in.close();
		int[] elements = new int[input.length];
		for(int i=0; i<input.length; i++)
			elements[i] = Integer.parseInt(input[i]);
		
		long st = System.currentTimeMillis();
		radixSort(elements);
		System.out.println( "For input size " + elements.length + " time taken by radix sort is " + (System.currentTimeMillis()-st) + "ms" );
		
		System.out.println("Sorted elements are:");
		for(int e : elements)
			System.out.print(e + " ");
	}

}
