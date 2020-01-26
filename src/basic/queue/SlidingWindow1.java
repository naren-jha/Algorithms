package basic.queue;

public class SlidingWindow1 {

    public void printMax(int[] a, int k) {
        int n = a.length;
        for (int i = 0; i < n - k + 1; i++) {
            int max = a[i];
            for (int j = 1; j < k; j++) {
                if (a[i + j] > max)
                    max = a[i + j];
            }
            System.out.print(max + " ");
        }
        System.out.println(); // new line
    }
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SlidingWindow1 sw = new SlidingWindow1();
        sw.printMax(arr, 3); // 3 4 5 6 7 8 9 10 
        
        arr = new int[] {1, 2, 3, 1, 4, 5, 2, 3, 6};
        sw.printMax(arr, 3); // 3 3 4 5 5 5 6
        
        arr = new int[] {8, 5, 10, 7, 9, 4, 15, 12, 90, 13};
        sw.printMax(arr, 4); // 10 10 10 15 15 90 90
    }

}
