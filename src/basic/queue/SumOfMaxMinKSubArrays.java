package basic.queue;

import java.util.Deque;
import java.util.LinkedList;

public class SumOfMaxMinKSubArrays {

    // Returns sum of min and max element of all subarrays of size k
    public int sumOfMaxMinKSubArrays(int[] a, int k) {
        int n = a.length;
        int sum = 0;
        
        // The queue will store indexes of useful elements in every window
        // In deque 'g' we maintain decreasing order of
        // values from front to rear
        // In deque 's' we  maintain increasing order of
        // values from front to rear
        Deque<Integer> g = new LinkedList<Integer>();
        Deque<Integer> s = new LinkedList<Integer>();
         
        // Process first window of size K
        int i;
        for (i = 0; i < k; ++i) {
            
            // Remove all previous greater elements that are useless.
            while (!g.isEmpty() && a[i] >= a[g.peekLast()])
                g.removeLast();   // Remove from rear
            
            // Remove all previous smaller elements that are useless.
            while (!s.isEmpty() && a[i] <= a[s.peekLast()])
                s.removeLast();   // Remove from rear
             
            // Add current element at rear of both deque
            g.addLast(i);
            s.addLast(i);
        }
         
        // Process rest of the Array elements
        for ( ; i < n; ++i) {
            
            // Element at the front of the deque 'g' & 's'
            // is the largest and smallest element of
            // previous window respectively
            sum += a[g.peek()] + a[s.peek()];
             
            // Remove all elements which are out of this window
            while ((!g.isEmpty()) && g.peek() <= i-k)
                g.removeFirst();
            while ((!s.isEmpty()) && s.peek() <= i-k)
                s.removeFirst();
             
            // Remove all previous greater elements that are useless.
            while ((!g.isEmpty()) && a[i] >= a[g.peekLast()])
                g.removeLast();
            
            // Remove all previous smaller elements that are useless.
            while ((!s.isEmpty()) && a[i] <= a[s.peekLast()])
                s.removeLast();
             
            // Add current element at rear of both deque
            g.addLast(i);
            s.addLast(i);
        }
     
        // Sum of minimum and maximum element of last window
        sum += a[g.peek()] + a[s.peek()];
        
        return sum;
    }
    
    public static void main(String[] args) {
        int[] arr = {2, 5, -1, 7, -3, -1, -2} ;
        SumOfMaxMinKSubArrays obj = new SumOfMaxMinKSubArrays();
        System.out.println(obj.sumOfMaxMinKSubArrays(arr, 4)); // 18
    }
}
