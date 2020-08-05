package basic.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Queue using Stack
 */
class Queue {
    private Deque<Integer> s1;
    private Deque<Integer> s2;
    
    public Queue() {
        s1 = new ArrayDeque<Integer>();
        s2 = new ArrayDeque<Integer>();
    }
    
    // Method to add a new item to the queue
    public void offer(int item) {
        s1.push(item);
    }
    
    // Method to remove and return oldest item from queue
    public int poll() {
        if (s1.isEmpty() && s2.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        
        return s2.pop();
    }
    
    // Method to return oldest item from queue 
    // without removing it.
    public int peek() {
        if (s1.isEmpty() && s2.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        
        return s2.peek();
    }

    // Checks if queue is empty or not
    public boolean isEmpty() {
        return s1.isEmpty() && s2.isEmpty();
    }
    
}

public class QueueUsingStack {

    public static void main(String[] args) {
        Queue q = new Queue();
        q.offer(1);
        q.offer(2);
        q.offer(3);
         
        /* Dequeue items */
        System.out.println(q.peek()); // 1
        System.out.println(q.poll()); // 1
        System.out.println(q.peek()); // 2
        System.out.println(q.poll()); // 2
        System.out.println(q.isEmpty()); // false
        System.out.println(q.peek()); // 3
        System.out.println(q.poll()); // 3
        System.out.println(q.isEmpty()); // true
    }

}
