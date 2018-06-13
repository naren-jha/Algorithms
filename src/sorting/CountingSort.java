package sorting;

import java.util.Scanner;

public class CountingSort {

	public static void main(String[] args) {
		System.out.println("Enter array elements separated by space:");
		Scanner in = new Scanner(System.in);
		String[] input = in.nextLine().split(" ");
		System.out.println("Enter max value(k):");
		int maxValue = in.nextInt();
		in.close();
		
		int[] elements = new int[input.length];
		int[] outputArray = new int[input.length];
		for(int i=0; i<input.length; i++)
			elements[i] = Integer.parseInt(input[i]);
		
		long st = System.currentTimeMillis();
		countingSort(elements,outputArray,maxValue);
		System.out.println( "For input size " + elements.length + " time taken by counting sort is " + (System.currentTimeMillis()-st) + "ms" );
		
		System.out.println("Sorted elements are:");
		for(int e : outputArray)
			System.out.print(e + " ");
	}

	public static void countingSort(int[] a, int[] b, int k) {
		int[] c = new int[k+1];
		for(int i=0; i<k+1; i++)
			c[i] = 0;
		for(int j=0; j<a.length; j++)
			c[a[j]]++;
		// c[i] now contains the number of elements equal to i.
		for(int i=1; i<k+1; i++)
			c[i] = c[i] + c[i-1];
		// c[i] now contains the number of elements less than or equal to i.
		for(int j=a.length-1; j>=0; j--){
			b[c[a[j]]-1] = a[j];
			c[a[j]]--;
		}
	}
}
