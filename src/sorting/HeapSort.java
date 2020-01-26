package sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of Heap Sort
 * 
 * @author Narendra Jha
 * 
 */
public class HeapSort {
	private static int heapSize;

	public static void heapSort(int[] a) {
		buildMaxHeap(a);
		int temp = 0;
		for (int i = a.length - 1; i >= 1; i--) {
			temp = a[i];
			a[i] = a[0];
			a[0] = temp;
			heapSize--;
			maxHeapify(a, 0);
		}
	}
	
	public static void buildMaxHeap(int[] a) {
		heapSize = a.length;
		for (int i = a.length/2 - 1; i >= 0; i--)
			maxHeapify(a, i);
	}
	
	public static void maxHeapify(int[] a, int i) {
		int l = getLeftIndex(i);
		int r = getRightIndex(i);
		
		int largest = i;
		if (l < heapSize && a[l] > a[i])
			largest = l;
		if (r < heapSize && a[r] > a[largest])
			largest = r;
		
		int temp = 0;
		if (largest != i) {
			// exchange a[i] with a[largest]
			temp = a[i];
			a[i] = a[largest];
			a[largest] = temp;
			maxHeapify(a, largest);
		}
	}
	
	public static int getParentIndex(int i) {
		return i / 2; // floorDiv
	}
	
	public static int getLeftIndex(int i) {
		return 2*i + 1;
	}
	
	public static int getRightIndex(int i) {
		return 2*i + 2;
	}
	
	public static void main(String[] args) {
        int[] array = {3, 15, 24, 51, 77, 52, 92};
        heapSort(array);
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
    
            heapSort(array);
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
