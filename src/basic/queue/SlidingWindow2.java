package basic.queue;

import java.util.Deque;
import java.util.LinkedList;

public class SlidingWindow2 {
    
    // A Dequeue based method to print maximum element of
    // all subarrays of size k
    public void printMax(int[] a, int k) {
        int n = a.length;
        
        // Create a Double Ended Queue, 'dq' that will store indexes 
        // of array elements. The queue will store indexes of useful 
        // elements in every window and it will maintain indices in  
        // decreasing order of values from front to rear in 'dq', i.e.,
        // a[dq.front()] to a[dq.rear()] are sorted in decreasing order
        Deque<Integer> dq = new LinkedList<Integer>();
         
        /* Process first k (or first window) elements of array */
        int i;
        for (i = 0; i < k; ++i) {
            // For every element, the previous smaller elements are useless so
            // remove them from dq
            while (!dq.isEmpty() && a[i] >= a[dq.peekLast()])
                dq.removeLast();   // Remove from rear
             
            // Add new element at rear of queue
            dq.addLast(i);
        }
         
        // Process rest of the elements, i.e., from a[k] to a[n-1]
        for ( ; i < n; ++i) {
            // The element at the front of the queue is the largest element of
            // previous window, so print it
            System.out.print(a[dq.peek()] + " ");
             
            // Remove the elements which are out of this window
            while ((!dq.isEmpty()) && dq.peek() <= i-k)
                dq.removeFirst();
             
            // Remove all elements smaller than the current
            // element (remove useless elements)
            while ((!dq.isEmpty()) && a[i] >= a[dq.peekLast()])
                dq.removeLast();
             
 
            // Add current element at the rear of dq
            dq.addLast(i);
        }
     
        // Print the maximum element of last window
        System.out.println(a[dq.peek()]);
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
