package basic.queue.problems;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Queue using one explicit Stack
 */
class Q {
    private Deque<Integer> s1;
    
    public Q() {
        s1 = new ArrayDeque<Integer>();
    }
    
    // Method to add a new item to the queue
    public void offer(int item) {
        s1.push(item);
    }
    
    // Method to remove and return oldest item from queue
    public int poll() {
        if (s1.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        // Base case
        if (s1.size() == 1) {
            return s1.pop();
        }
        
        int tmp = s1.pop();
        int res = poll();
        s1.push(tmp);
        return res;
    }
    
    // Method to return oldest item from queue 
    // without removing it.
    public int peek() {
        if (s1.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        // Base case
        if (s1.size() == 1) {
            return s1.peek();
        }
        
        int tmp = s1.pop();
        int res = peek();
        s1.push(tmp);
        return res;
    }
    
    // Checks if queue is empty or not
    public boolean isEmpty() {
        return s1.isEmpty();
    }

}

public class QUsingOneStack {

    public static void main(String[] args) {
        Q q = new Q();
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
