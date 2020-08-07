package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Insertion Sort
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class InsertionSort {
    
    public static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i-1;
            while (j >= 0 && a[j] > key) { // a[i] > key -> stable sort, a[i] >= key -> unstable sort
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = key;
        }
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        insertionSort(array);
        System.out.println(Arrays.toString(array)); // [2, 3, 4, 6, 8, 10, 13]
        
        runTests();
    }
    
    private static Random RANDOM = new Random();

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
            int[] arrayCopy = array.clone();
    
            insertionSort(array);
            Arrays.sort(arrayCopy);
    
            if (!Arrays.equals(array, arrayCopy)) {
                System.err.println("ERROR");
                System.out.println("Actual: " + Arrays.toString(array));
                System.out.println("Expected: " + Arrays.toString(arrayCopy));
            }
        }
    }

    private static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

}
