package sorting;

import java.util.Scanner;

public class BubbleSort {

	private static void bubbleSort(int[] a) {
		long st = System.currentTimeMillis();
		
		int temp = 0, n = a.length;
		boolean sw = true; // switch
		for(int i=0; i<n-1 && sw; i++) {
			sw = false;
			for(int j=0; j<n-i-1; j++) {
				if(a[j] > a[j+1]) {
					temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
					sw = true;
				}
			}
		}
		
		System.out.println( "For input size " + a.length + " time taken by buuble sort is " + (System.currentTimeMillis()-st) + "ms" );
	}
	
	public static void main(String[] args) {
        System.out.println("Enter array elements separated by space:");
        Scanner in = new Scanner(System.in);
        String[] input = in.nextLine().split(" ");
        in.close();
        int[] elements = new int[input.length];
        for(int i=0; i<input.length; i++)
            elements[i] = Integer.parseInt(input[i]);
        
        bubbleSort(elements);
        System.out.println("Sorted elements are:");
        for(int e : elements)
            System.out.print(e + " ");
    }
}
