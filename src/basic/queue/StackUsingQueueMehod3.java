package basic.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Narendra Jha
 *
 * Stack using Queue – Method 3 -
 */
class Stack3 {
	private Queue<Integer> q1, q2;
	
	public Stack3() {
		q1 = new LinkedList<Integer>();
		q2 = new LinkedList<Integer>();
	}
	
	// Method to add a new item to the stack
	public void push(int item) {
		if (q1.isEmpty()) {
			q1.add(item);
			while (!q2.isEmpty()) {
				q1.add(q2.poll());
			}
		}
		else { // if q2.isEmpty()
			q2.add(item);
			while (!q1.isEmpty()) {
				q2.add(q1.poll());
			}
		}
	}
	
	// Method to remove and return the top of stack
	public int pop() {
		if (q1.isEmpty() && q2.isEmpty())
			throw new IllegalStateException("Empty stack.");
		
		if (!q1.isEmpty())
			return q1.poll();
		else // if !q2.isEmpty()
			return q2.poll();
	}
	
	// Method to return the top of stack without removing it.
	public int peek() {
		if (q1.isEmpty() && q2.isEmpty())
			throw new IllegalStateException("Empty stack.");
		
		if (!q1.isEmpty())
			return q1.peek();
		else // if !q2.isEmpty()
			return q2.peek();
	}
	
	// Method to check if stack is empty or not.
	public boolean isEmpty() {
		return q1.isEmpty() && q2.isEmpty();
	}
	
}

public class StackUsingQueueMehod3 {
	
	public static void main(String[] args) {
		Stack3 s = new Stack3();
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
