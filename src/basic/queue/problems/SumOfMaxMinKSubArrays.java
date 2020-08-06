package basic.queue.problems;

import java.util.ArrayDeque;
import java.util.Deque;

public class SumOfMaxMinKSubArrays {

    // Returns sum of min and max element of all subarrays of size k
    public int sumOfMaxMinKSubArrays(int[] a, int k) {
        int n = a.length;
        int sum = 0;
        
        // The queue will store indexes of useful elements in every window
        // In deque 'maxDq' we maintain decreasing order of
        // values from front to rear
        // In deque 'minDq' we  maintain increasing order of
        // values from front to rear
        Deque<Integer> maxDq = new ArrayDeque<Integer>();
        Deque<Integer> minDq = new ArrayDeque<Integer>();
         
        // Process first window of size K
        int i;
        for (i = 0; i < k; ++i) {
            
            // Remove all previous greater elements that are useless.
            while (!maxDq.isEmpty() && a[i] >= a[maxDq.peekLast()])
                maxDq.removeLast();   // Remove from rear // same as pop()
            
            // Remove all previous smaller elements that are useless.
            while (!minDq.isEmpty() && a[i] <= a[minDq.peekLast()])
                minDq.removeLast();   // Remove from rear // same as pop()
             
            // Add current element at rear of both deque
            maxDq.addLast(i); // same as offer(i)
            minDq.addLast(i); // same as offer(i)
        }
         
        // Process rest of the Array elements
        for ( ; i < n; ++i) {
            
            // Elements at the front of the deques are the largest and smallest 
            // elements of previous window respectively
            sum += a[maxDq.peek()] + a[minDq.peek()];
             
            // Remove all elements which are out of this window
            while ((!maxDq.isEmpty()) && maxDq.peek() <= i-k)
                maxDq.removeFirst(); // same as poll()
            while ((!minDq.isEmpty()) && minDq.peek() <= i-k)
                minDq.removeFirst(); // same as poll()
             
            // Remove all previous greater elements that are useless.
            while ((!maxDq.isEmpty()) && a[i] >= a[maxDq.peekLast()])
                maxDq.removeLast(); // same as pop()
            
            // Remove all previous smaller elements that are useless.
            while ((!minDq.isEmpty()) && a[i] <= a[minDq.peekLast()])
                minDq.removeLast(); // same as pop()
             
            // Add current element at rear of both deque
            maxDq.addLast(i); // same as offer(i)
            minDq.addLast(i); // same as offer(i)
        }
     
        // Sum of minimum and maximum element of last window
        sum += a[maxDq.peek()] + a[minDq.peek()];
        
        return sum;
    }
    
    public static void main(String[] args) {
        int[] arr = {2, 5, -1, 7, -3, -1, -2} ;
        SumOfMaxMinKSubArrays obj = new SumOfMaxMinKSubArrays();
        System.out.println(obj.sumOfMaxMinKSubArrays(arr, 4)); // 18
    }
}
