package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * Stack with getMax() in O(1) time and extra space
 */
public class StackWithMaxInO1TimeAndSpace {

	private int maxEle;
	private Stack<Integer> s;
	
	public StackWithMaxInO1TimeAndSpace() {
		s = new Stack<Integer>();
	}

	public void push(int x) {
		if (s.isEmpty()) {
			s.push(x);
			maxEle = x;
			return;
		}
		
		if (x > maxEle) {
			// new number is greater than current maximum
			s.push(2*x - maxEle);
			maxEle = x;
		}
		else {
			s.push(x);
		}
	}
	
	public int pop() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		int y = s.pop();
		if (y > maxEle) {
			int e = maxEle; // current element is maxEle
			maxEle = 2*maxEle - y; // update maximum
			return e;
		}
		else
			return y; // current element is top of stack
	}
	
	public int peek() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		int y = s.peek();
		return y > maxEle ? maxEle : y;
	}
	
	public int getMax() {
		if (s.isEmpty())
			throw new IllegalStateException("Stack is empty.");
		
		return maxEle;
	}
	
	public static void main(String[] args) {
		StackWithMaxInO1TimeAndSpace s = new StackWithMaxInO1TimeAndSpace();
		s.push(5);
        s.push(3);
        System.out.println(s.getMax()); // 5
        s.push(12);
        s.push(15);
        System.out.println(s.getMax()); // 15
        System.out.println(s.pop()); // 15
        System.out.println(s.getMax()); // 12
        System.out.println(s.pop()); // 12
        System.out.println(s.peek()); // 3
	}
	
}
