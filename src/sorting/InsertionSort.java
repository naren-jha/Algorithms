package sorting;

import java.util.Scanner;

public class InsertionSort {
	
	public static void insertionSort(int[] a) {
		long st = System.currentTimeMillis();
		for(int j=1; j<a.length; j++) {
			int key = a[j];
			int i=j-1;
			while(i>=0 && a[i]>key) { // a[i] > key -> stable sort, a[i] >= key -> unstable sort
				a[i+1] = a[i];
				i--;
			}
			a[i+1] = key;
		}
		System.out.println( "For input size " + a.length + " time taken by insertion sort is " + (System.currentTimeMillis()-st) + "ms" );
	}
	
	public static void main(String[] args) {
        System.out.println("Enter array elements separated by space:");
        Scanner in = new Scanner(System.in);
        String[] input = in.nextLine().split(" ");
        in.close();
        int[] elements = new int[input.length];
        for(int i=0; i<input.length; i++)
            elements[i] = Integer.parseInt(input[i]);
        
        insertionSort(elements);
        System.out.println("Sorted elements are:");
        for(int e : elements)
            System.out.print(e + " ");
    }

}
