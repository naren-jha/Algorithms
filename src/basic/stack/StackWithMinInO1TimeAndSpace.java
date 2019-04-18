package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * Stack with getMin() in O(1) time and extra space
 */
public class StackWithMinInO1TimeAndSpace {
	
	private int minEle;
	private Stack<Integer> s;
	
	public StackWithMinInO1TimeAndSpace() {
		s = new Stack<Integer>();
	}

	public void push(int x) {
		if (s.isEmpty()) {
			s.push(x);
			minEle = x;
			return;
		}
		
		if (x < minEle) {
			// new number is lesser than current minimum
			s.push(2*x - minEle);
			minEle = x;
		}
		else {
			s.push(x);
		}
	}
	
	public int pop() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		int y = s.pop();
		if (y < minEle) {
			int e = minEle; // current element is minEle
			minEle = 2*minEle - y; // update minimum
			return e;
		}
		else
			return y; // current element is top of stack
	}
	
	public int peek() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		int y = s.peek();
		return y < minEle ? minEle : y;
	}
	
	public int getMin() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		return minEle;
	}
	
	public static void main(String[] args) {
		StackWithMinInO1TimeAndSpace s = new StackWithMinInO1TimeAndSpace();
		s.push(3);
        s.push(5);
        System.out.println(s.getMin()); // 3
        s.push(2);
        s.push(1);
        System.out.println(s.getMin()); // 1
        System.out.println(s.pop()); // 1
        System.out.println(s.getMin()); // 2
        System.out.println(s.pop()); // 2
        System.out.println(s.peek()); // 5
	}
}
