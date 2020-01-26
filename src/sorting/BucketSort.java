package sorting;

import java.util.LinkedList;

/**
 * Implementation of Bucket Sort
 * 
 * @author Narendra Jha
 * 
 */
public class BucketSort {

    public static void bucketSort(double[] a) {
        int n = a.length;
        LinkedList<Double>[] b = new LinkedList[n];
        for (int i = 0; i < n; i++)
            b[i] = new LinkedList<Double>();
        for (int i = 0; i < n; i++)
            b[(int) Math.floor(n*a[i])].add(a[i]);
        
        System.out.print("Sorted elements are: ");
        for (int i = 0; i < n; i++){
            double[] tempArray = new double[b[i].size()];
            int j = 0;
            for(Double d : b[i]) {
                tempArray[j++] = d.doubleValue();
            }
            insertionSort(tempArray);
            for(double e : tempArray)
                System.out.print(e + " ");
        }
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
    
    public static void main(String[] args) {
        double[] elements = {0.8, 0.9, 0.2, 0.4, 0.11, 0.12, 0.25};
        bucketSort(elements);
        // Sorted elements are: 0.11 0.12 0.2 0.25 0.4 0.8 0.9 
    }
}
