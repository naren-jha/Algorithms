package basic.queue.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Stack using Single Queue – Method 4 -
 */
class Stack4 {
    private Queue<Integer> q;
    
    public Stack4() {
        q = new LinkedList<Integer>();
    }
    
    // Method to add a new item to the stack
    public void push(int item) {
        // get current size of queue
        int size = q.size();
        
        // enqueue current element
        q.add(item);
        
        // Dequeue all previous elements and 
        // put them after current element
        for (int i = 0; i < size; i++) {
            q.add(q.poll());
        }
    }
    
    // Method to remove and return the top of stack
    public int pop() {
        if (q.isEmpty())
            throw new IllegalStateException("Empty stack.");
        
        return q.poll();
    }
    
    // Method to return the top of stack without removing it.
    public int peek() {
        if (q.isEmpty())
            throw new IllegalStateException("Empty stack.");
        
        return q.peek();
    }
    
    // Method to check if stack is empty or not.
    public boolean isEmpty() {
        return q.isEmpty();
    }
    
}

public class StackUsingSingleQueue {

    public static void main(String[] args) {
        Stack4 s = new Stack4();
        s.push(1);
        s.push(2);
        s.push(3);
        
        /* Pop items */
        System.out.println(s.peek()); // 3
        System.out.println(s.pop()); // 3
        System.out.println(s.peek()); // 2
        System.out.println(s.pop()); // 2
        System.out.println(s.isEmpty()); // false
        System.out.println(s.peek()); // 1
        System.out.println(s.pop()); // 1
        System.out.println(s.isEmpty()); // true
    }
    
}
