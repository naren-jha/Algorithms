package basic.stack.appl;

import java.util.Stack;

/**
 * @author Narendra Jha
 * 
 * sorting stack using recursion
 */
public class SortStackUsingRecursion extends Stack<Integer> {
     
	// recursive Method to insert an item x in sorted way
    public void sortedInsert(int x) {
 
    	// Base case: Either stack is empty or newly inserted
        // item is greater than top of stack (which means it is
    	// greater than all existing elements in stack)
        if (isEmpty() || x > peek()) {
            push(x);
            return;
        }
        
        // if top is greater, remove the top item and recur
        int a = pop();
        sortedInsert(x);
 
        // put back the top items removed earlier
        push(a);
    }
     
    // method to sort stack
    public void sortStack() {
        if (!isEmpty()) {
            // Hold all items in Function Call Stack until we
            // reach end of the stack
            int x = pop();
            sortStack();
            
            // Insert all the items held in Function Call Stack
            // in sorted order
            sortedInsert(x);
        }
    }
 
    // driver method
    public static void main(String[] args) {
    	SortStackUsingRecursion s = new SortStackUsingRecursion();
    	s.push(30);
        s.push(-5);
        s.push(18);
        s.push(14);
        s.push(-3);
      
        System.out.println("Before sorting: ");
        System.out.println(s); // [30, -5, 18, 14, -3]
        s.sortStack();
      
        System.out.println("After sorting:");
        System.out.println(s); // [-5, -3, 14, 18, 30]
    }
}
