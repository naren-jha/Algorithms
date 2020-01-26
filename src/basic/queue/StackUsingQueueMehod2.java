package basic.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Stack using Queue – Method 2 -
 */
class Stack2 {
    private Queue<Integer> q1, q2;
    
    public Stack2() {
        q1 = new LinkedList<Integer>();
        q2 = new LinkedList<Integer>();
    }
    
    // Method to add a new item to the stack
    public void push(int item) {
        q1.add(item);
    }
    
    // Method to remove and return the top of stack
    public int pop() {
        if (q1.isEmpty())
            throw new IllegalStateException("Empty stack.");
        
        // Dequeue everything except the last element from q1 
        // and enqueue to q2, last item left in q1 will be result
        while (q1.size() != 1) {
            q2.add(q1.poll());
        }
        int res = q1.poll();
        
        // Swap the names of two queues
        Queue<Integer> q = q1;
        q1 = q2;
        q2 = q;
        
        return res;
    }
    
    // Method to return the top of stack without removing it.
    public int peek() {
        if (q1.isEmpty())
            throw new IllegalStateException("Empty stack.");
        
        // Dequeue everything except the last element from q1 
        // and enqueue to q2, last item left in q1 will be result
        while (q1.size() != 1) {
            q2.add(q1.poll());
        }
        int res = q1.poll();
        q2.add(res);
        
        // Swap the names of two queues
        Queue<Integer> q = q1;
        q1 = q2;
        q2 = q;
        
        return res;
    }
    
    // Method to check if stack is empty or not.
    public boolean isEmpty() {
        return q1.isEmpty();
    }
    
}

public class StackUsingQueueMehod2 {

    public static void main(String[] args) {
        Stack2 s = new Stack2();
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
