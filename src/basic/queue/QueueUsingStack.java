package basic.queue;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Queue using Stack
 */
class Queue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    
    public Queue() {
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();
    }
    
    // Method to add a new item to the queue
    public void enqueue(int item) {
        stack1.push(item);
    }
    
    // Method to remove and return oldest item from queue
    public int dequeue() {
        if (stack1.isEmpty() && stack2.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        
        return stack2.pop();
    }
    
    // Method to return oldest item from queue 
    // without removing it.
    public int front() {
        if (stack1.isEmpty() && stack2.isEmpty())
            throw new IllegalStateException("Empty queue");
        
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        
        return stack2.peek();
    }

    // Checks if queue is empty or not
    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
    
}

public class QueueUsingStack {

    public static void main(String[] args) {
        Queue q = new Queue();
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
