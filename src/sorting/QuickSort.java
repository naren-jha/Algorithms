package sorting;

import java.util.Random;
import java.util.Scanner;

public class QuickSort {

	public static void randomizedQuickSort(int[] a) {
		randomizedQuickSort(a,0,a.length-1);
	}

	public static void randomizedQuickSort(int[] a, int p, int r) {
		if(p < r) {
			int q = randomizedPartition(a,p,r);
			randomizedQuickSort(a,p,q-1);
			randomizedQuickSort(a,q+1,r);
		}
	}

	public static int randomizedPartition(int[] a, int p, int r) {
		int i = random(p,r);
		// exchange a[r] and a[i] to randomize the selection of pivot element
		int temp = a[r];
		a[r] = a[i];
		a[i] = temp;
		return partition(a,p,r);
	}

	public static int partition(int[] a, int p, int r) {
		int x = a[r];
		int i = p-1;
		for(int j=p; j<r; j++) {
			if(a[j] <= x) {
				i++;
				// exchange a[i] and a[j]
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}
		// exchange a[i+1] and a[r]
		int temp = a[i+1];
		a[i+1] = a[r];
		a[r] = temp;
		return i+1;
	}

	public static int random(int lowerBound, int upperBound) {
		// return a random number b/w lower and upper bound.
		Random rand = new Random();
		return lowerBound + rand.nextInt((upperBound - lowerBound) + 1);
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
        randomizedQuickSort(elements);
        System.out.println( "For input size " + elements.length + " time taken by quick sort is " + (System.currentTimeMillis()-st) + "ms" );
        
        System.out.println("Sorted elements are:");
        for(int e : elements)
            System.out.print(e + " ");
    }

}
