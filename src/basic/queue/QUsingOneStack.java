package basic.queue;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Queue using one explicit Stack
 */
class Q {
    private Stack<Integer> stack1;
    
    public Q() {
        stack1 = new Stack<Integer>();
    }
    
    // Method to add a new item to the queue
    public void enqueue(int item) {
        stack1.push(item);
    }
    
    // Method to remove and return oldest item from queue
    public int dequeue() {
        if (stack1.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        // Base case
        if (stack1.size() == 1) {
            return stack1.pop();
        }
        
        int tmp = stack1.pop();
        int res = dequeue();
        stack1.push(tmp);
        return res;
    }
    
    // Method to return oldest item from queue 
    // without removing it.
    public int front() {
        if (stack1.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        // Base case
        if (stack1.size() == 1) {
            return stack1.peek();
        }
        
        int tmp = stack1.pop();
        int res = front();
        stack1.push(tmp);
        return res;
    }
    
    // Checks if queue is empty or not
    public boolean isEmpty() {
        return stack1.isEmpty();
    }

}

public class QUsingOneStack {

    public static void main(String[] args) {
        Q q = new Q();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
         
        /* Dequeue items */
        System.out.println(q.front()); // 1
        System.out.println(q.dequeue()); // 1
        System.out.println(q.front()); // 2
        System.out.println(q.dequeue()); // 2
        System.out.println(q.isEmpty()); // false
        System.out.println(q.front()); // 3
        System.out.println(q.dequeue()); // 3
        System.out.println(q.isEmpty()); // true
    }

}
