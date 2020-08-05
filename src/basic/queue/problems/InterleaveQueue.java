package basic.queue.problems;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Interleave a given queue using stack
 */
public class InterleaveQueue {

    // Method to interleave a given queue using stack
    public void interleaveQueue(Queue<Integer> q) {
        // To check the even number of elements
        if (q.size() % 2 != 0)
            throw new IllegalStateException("Require even number of"
                    + " elements in queue");
        
        Stack<Integer> s = new Stack<Integer>();
        int halfSize = q.size() / 2;
        
        // Push first half elements of queue into the stack
        // q: 16 17 18 19 20, s: 15(T) 14 13 12 11
        for (int i = 0; i < halfSize; i++) {
            s.push(q.poll());
        }
        
        // Enqueue stack elements back to the queue
        // q: 16 17 18 19 20 15 14 13 12 11
        while (!s.isEmpty() ) {
            q.offer(s.pop());
        }
        
        // Dequeue the first half elements of the queue and enqueue them back
        // q: 15 14 13 12 11 16 17 18 19 20
        for (int i = 0; i < halfSize; i++) {
            q.offer(q.poll());
        }
        
        // Once again, push the first half elements of queue into the stack
        // q: 16 17 18 19 20, s: 11(T) 12 13 14 15
        for (int i = 0; i < halfSize; i++) {
            s.push(q.poll());
        }
        
        // Interleave the elements of queue and stack
        // q: 11 16 12 17 13 18 14 19 15 20
        while (!s.isEmpty() ) {
            q.offer(s.pop());
            q.offer(q.poll());
        }
    }
    
    public static void main(String[] args) {
        
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(11);q.add(12);q.add(13);q.add(14);q.add(15);
        q.add(16);q.add(17);q.add(18);q.add(19);q.add(20);
        System.out.println(q); // [11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
        
        new InterleaveQueue().interleaveQueue(q);
        System.out.println(q); // [11, 16, 12, 17, 13, 18, 14, 19, 15, 20]
        
    }

}
