package sorting;

import java.util.Scanner;

public class HeapSort {
	private static int heapSize;

	public static void heapSort(int[] a) {
		buildMaxHeap(a);
		int temp = 0;
		for(int i = a.length - 1; i >= 1; i--) {
			temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			heapSize--;
			maxHeapify(a,0);
		}
	}
	
	public static void buildMaxHeap(int[] a) {
		heapSize = a.length;
		for(int i = a.length/2 - 1; i >= 0; i--)
			maxHeapify(a,i);
	}
	
	public static void maxHeapify(int[] a, int i) {
		int l = getLeftIndex(i);
		int r = getRightIndex(i);
		
		int largest = i;
		if(l < heapSize && a[l] > a[i])
			largest = l;
		if(r < heapSize && a[r] > a[largest])
			largest = r;
		
		int temp = 0;
		if(largest != i) {
			// exchange a[i] with a[largest]
			temp = a[i];
			a[i] = a[largest];
			a[largest] = temp;
			maxHeapify(a,largest);
		}
	}
	
	public static int getParentIndex(int i) {
		return i/2; // floorDiv
	}
	
	public static int getLeftIndex(int i) {
		return 2*i;
	}
	
	public static int getRightIndex(int i) {
		return (2*i + 1);
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
        heapSort(elements);
        System.out.println( "For input size " + elements.length + " time taken by heap sort is " + (System.currentTimeMillis()-st) + "ms" );
        
        System.out.println("Sorted elements are:");
        for(int e : elements)
            System.out.print(e + " ");
    }
}
