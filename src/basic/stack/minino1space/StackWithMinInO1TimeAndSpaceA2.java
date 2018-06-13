package basic.stack.minino1space;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * Stack with getMin() in O(1) time and extra space
 */
public class StackWithMinInO1TimeAndSpaceA2 {

	private int minEle;
	private Stack<Integer> s;
	
	public StackWithMinInO1TimeAndSpaceA2() {
		s = new Stack<Integer>();
	}
	
	public void push(int x) {
	    if (s.isEmpty()) {
	        minEle = x;
	        s.push(x);
	    } else if (x > minEle) {
	        s.push(minEle - x);
	    } else {
	        s.push(minEle);
	        minEle = x;
	    }
    }
	
	public int pop() {
        if (s.isEmpty())
        	throw new IllegalStateException("Stack is empty.");
        
	    int y = s.pop();
	    if (y >= minEle) {
	        int m = minEle;
	        minEle = y;
	        return m;
	    }
	    return minEle - y;
    }
	
	public int peek() {
        if (s.isEmpty())
        	throw new IllegalStateException("Stack is empty.");
        
	    int y = s.peek();
	    return y >= minEle ? minEle : minEle - y;
    }
	
	public int getMin() {
        if (s.isEmpty())
        	throw new IllegalStateException("Stack is empty.");
        
	    return minEle;
    }
	
	public static void main(String[] args) {
		StackWithMinInO1TimeAndSpaceA2 s = new StackWithMinInO1TimeAndSpaceA2();
		s.push(3);
        s.push(5);
        System.out.println(s.getMin()); // 3
        s.push(2);
        s.push(1);
        s.push(-1);
        System.out.println(s.getMin()); // -1
        System.out.println(s.pop()); // -1
        System.out.println(s.getMin()); // 1
        System.out.println(s.pop()); // 1
        System.out.println(s.peek()); // 2
	}
}
