package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Insertion Sort
 * 
 * @author Narendra Jha
 * 
 */
public class InsertionSort {
    
    public static void insertionSort(int[] a) {
        for (int j = 1; j < a.length; j++) {
            int key = a[j];
            int i = j-1;
            while (i >= 0 && a[i] > key) { // a[i] > key -> stable sort, a[i] >= key -> unstable sort
                a[i+1] = a[i];
                i--;
            }
            a[i+1] = key;
        }
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        insertionSort(array);
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
    
            insertionSort(array);
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
