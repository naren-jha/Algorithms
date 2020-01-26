package basic.stack;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Stack using priority queue
 */
class Stack2 {
    // template for stack element entry in priority queue
    private class Entry {
        int data;
        int priority;
        
        Entry(int data, int priority) {
            this.data = data;
            this.priority = priority;
        }
    }
    
    private PriorityQueue<Entry> pq = 
            new PriorityQueue<Entry>(new Comparator<Entry>() {
        @Override
        public int compare(Entry e1, Entry e2) {
            /*if (e2.priority > e1.priority)
                return 1;
            else if (e2.priority < e1.priority)
                return -1;
            else
                return 0;*/
            
            return e2.priority - e1.priority;
        }
    });
    
    private int count;
    
    // constructor
    public Stack2() {
        count = 0;
    }
    
    // adds an element to the top of Stack
    public void push(int value) {
        pq.add(new Entry(value, ++count));
    }
    
    // removes and returns top of stack
    public int pop() {
        if (pq.isEmpty())
            throw new IllegalStateException("Stack underflow.");
        
        count--;
        return pq.poll().data;
    }
    
    // returns top of stack without removing it
    public int peek() {
        if (pq.isEmpty())
            throw new IllegalStateException("Stack underflow.");
        
        return pq.peek().data;
    }
    
    // reports whether stack is empty or not
    public boolean isEmpty() {
        return pq.isEmpty();
    }
}

public class StackUsingPriorityQueue {

    public static void main(String[] args) {
        Stack2 s = new Stack2();
        s.push(10);
        s.push(20);
        s.push(30);
        
        System.out.println(s.pop()); // 30
        System.out.println(s.peek()); // 20
        System.out.println(s.pop()); // 20
        
        System.out.println(s.isEmpty()); // false
        s.push(50);
        s.push(60);
        
        System.out.println(s.peek()); // 60
        System.out.println(s.pop()); // 60
        System.out.println(s.pop()); // 50
        System.out.println(s.pop()); // 10
        System.out.println(s.isEmpty()); // true
    }
    
}
