package basic.stack.appl;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * sorting stack using another stack
 */
public class SortStackUsingAnotherStack extends Stack<Integer> {
       
    // method to sort stack
    public Stack<Integer> sortStack() {
        Stack<Integer> tmpStack = new Stack<Integer>();
        while (!isEmpty()) {
        	int x = pop();
        	while (!tmpStack.isEmpty() && tmpStack.peek() > x)
        		push(tmpStack.pop());
        	tmpStack.push(x);
        }
        
        return tmpStack;
    }
 
    // driver method
    public static void main(String[] args) {
    	SortStackUsingAnotherStack s = new SortStackUsingAnotherStack();
        s.add(34);
        s.add(3);
        s.add(31);
        s.add(98);
        s.add(92);
        s.add(23);
     
        System.out.println(s.sortStack()); // [3, 23, 31, 34, 92, 98]
    }
}
