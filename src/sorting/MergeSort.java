package sorting;

import java.util.Scanner;

public class MergeSort {

	public static void main(String[] args) {
		System.out.println("Enter array elements separated by space:");
		Scanner in = new Scanner(System.in);
		String[] input = in.nextLine().split(" ");
		in.close();
		int[] elements = new int[input.length];
		for(int i=0; i<input.length; i++)
			elements[i] = Integer.parseInt(input[i]);
		
		long st = System.currentTimeMillis();
		mergeSort(elements);
		System.out.println( "For input size " + elements.length + " time taken by merge sort is " + (System.currentTimeMillis()-st) + "ms" );
		
		System.out.println("Sorted elements are:");
		for(int e : elements)
			System.out.print(e + " ");
	}
	
	// to simplify mergeSort method call
	public static void mergeSort(int[] a) {
		mergeSort(a,0,a.length-1);
	}
	
	public static void mergeSort(int[] a,int p,int r) {
		if(p < r) {
			int q = Math.floorDiv((p+r), 2); // int q = (p+r)/2 would also do just fine.
			mergeSort(a,p,q);
			mergeSort(a,q+1,r);
			merge(a,p,q,r);
		}
	}
	
	public static void merge(int[] a,int p,int q,int r) {
		int n1 = q-p+1, n2 = r-q;
		int[] la = new int[n1+1];
		int[] ra = new int[n2+1];
		for(int i = 0; i < n1; i++)
			la[i] = a[p+i];
		for(int j = 0; j < n2; j++)
			ra[j] = a[q+j+1];
		
		la[n1] = Integer.MAX_VALUE;
		ra[n2] = Integer.MAX_VALUE;
		
		int i = 0,j = 0;
		for(int k = p; k <= r; k++) {
			if(la[i] <= ra[j]) {
				a[k] = la[i];
				i++;
			} else {
				a[k] = ra[j];
				j++;
			}
		}
	}

}
