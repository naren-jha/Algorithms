package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Shell Sort
 * 
 * @author Narendra Jha
 * 
 */
public class Shell {
    
    public static void sort(int[] a) {
        int n = a.length; 
          
        // Start with a big gap, then reduce the gap 
        for (int gap = n/2; gap > 0; gap /= 2) {
            
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
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        sort(array);
        System.out.println(Arrays.toString(array));
        
        // TODO: move to javatests/...
        runTests();
    }
    
    static Random RANDOM = new Random();

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
            int[] arrayCopy = array.clone();
    
            sort(array);
            Arrays.sort(arrayCopy);
    
            if (!Arrays.equals(array, arrayCopy)) {
                System.err.println("ERROR");
                System.out.println("Actual: " + Arrays.toString(array));
                System.out.println("Expected: " + Arrays.toString(arrayCopy));
            }
        }
    }

    static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
    
}
