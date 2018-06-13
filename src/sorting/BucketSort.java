package sorting;

import java.util.LinkedList;
import java.util.Scanner;

public class BucketSort {

	public static void main(String[] args) {
		System.out.println("Enter values in the interval [0,1) separated by space:");
		Scanner in = new Scanner(System.in);
		String[] input = in.nextLine().split(" ");
		in.close();
		double[] elements = new double[input.length];
		for(int i=0; i<input.length; i++)
			elements[i] = Double.parseDouble(input[i]);
		
		bucketSort(elements);		
	}

	public static void bucketSort(double[] a) {
		long st = System.currentTimeMillis();
		
		int n = a.length;
		LinkedList<Double>[] b = new LinkedList[n];
		for(int i=0; i<n; i++)
			b[i] = new LinkedList<Double>();
		for(int i=0; i<n; i++)
			b[(int) Math.floor(n*a[i])].add(new Double(a[i]));
		System.out.println("Sorted elements are:");
		for(int i=0; i<n; i++){
			double[] tempArray = new double[b[i].size()];
			int j = 0;
			for(Double d : b[i]) {
				tempArray[j++] = d.doubleValue();
			}
			insertionSort(tempArray);
			for(double e : tempArray)
				System.out.print(e + " ");
		}
		System.out.println( "\nFor input size " + a.length + " time taken by bucket sort is " + (System.currentTimeMillis()-st) + "ms" );
	}
	
	public static void insertionSort(double[] a) {
		for(int j=1; j<a.length; j++) {
			double key = a[j];
			int i=j-1;
			while(i>=0 && a[i]>key) {
				a[i+1] = a[i];
				i--;
			}
			a[i+1] = key;
		}
	}
}
