package sorting;

import java.util.Scanner;

public class Shell {
    
    public static void sort(int[] a) {
        int n = a.length; 
          
        // Start with a big gap, then reduce the gap 
        for (int gap = n/3; gap > 0; gap /= 3) {
        	
            // Do a gapped insertion sort for this gap size. 
            // The first gap elements a[0..gap-1] are already 
            // in gapped order keep adding one more element 
            // until the entire array is gap sorted 
            for (int i = gap; i < n; i += 1) {
                // save a[i] in temp and make a hole at position i 
                int temp = a[i]; 
  
                // shift earlier gap-sorted elements up until 
                // the correct location for a[i] is found 
                int j; 
                for (j = i; j >= gap && a[j - gap] > temp; j -= gap) 
                    a[j] = a[j - gap]; 
  
                // put temp (the original a[i]) in its correct location 
                a[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter array elements separated by space:");
        Scanner in = new Scanner(System.in);
        String[] input = in.nextLine().split(" ");
        in.close();
        int[] elements = new int[input.length];
        for(int i = 0; i < input.length; i++)
            elements[i] = Integer.parseInt(input[i]);
        
        long st = System.currentTimeMillis();
        sort(elements);
        System.out.println("For input size " + elements.length + " time taken by shell sort is " + (System.currentTimeMillis() - st) + "ms" );
        
        System.out.println("Sorted elements are:");
        for (int e : elements)
            System.out.print(e + " ");
    }
    
}
