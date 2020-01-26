package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Bubble Sort
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */
public class BubbleSort {

    public static void bubbleSort(int[] a) {        
        int temp = 0, n = a.length;
        boolean sw = true; // swap
        for (int i = 0; i < n-1 && sw; ++i) {
            sw = false;
            for (int j = 0; j < n-i-1; ++j) {
                if (a[j] > a[j+1]) {
                    temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    sw = true;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int[] array = {10, 4, 6, 8, 13, 2, 3};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
        
        // TODO: move to javatests/...
        runTests();
    }
    
    private static Random RANDOM = new Random();

    public static void runTests() {
        final int NUM_TESTS = 1000;
        for (int i = 1; i <= NUM_TESTS; i++) {

            int[] array = new int[i];
            for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
            int[] arrayCopy = array.clone();
    
            bubbleSort(array);
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
