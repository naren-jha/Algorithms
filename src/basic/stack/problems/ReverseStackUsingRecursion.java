package basic.stack.problems;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * reversing stack using recursion
 */
public class ReverseStackUsingRecursion extends Stack<Integer> {
     
    // a recursive method to insert an element at the bottom of a stack.
    public void insertAtBottom(int x) {
        // Base case
        if (isEmpty()) {
            push(x);
            return;
        }
        
        // All items are held in Function Call Stack until we
        // reach end of the stack. When stack becomes empty, 
        // if part is executed and the item is inserted
        // at the bottom
        int a = pop();
        insertAtBottom(x);
 
        // push all the items held in Function Call Stack
        // once 'x' is inserted at the bottom
        push(a);
    }
     
    // method to reverse stack using recursion
    // this method uses insertAtBottom() method
    public void reverse() {
        if (!isEmpty()) {
            // Hold all items in Function Call Stack until we
            // reach end of the stack
            int x = pop();
            reverse();
            
            // Insert all the items held in Function Call Stack
            // one by one from the bottom to top. Every item is
            // inserted at the bottom
            insertAtBottom(x);
        }
    }
 
    // driver method
    public static void main(String[] args) {
        ReverseStackUsingRecursion st = new ReverseStackUsingRecursion();
        
        // push elements into the stack
        st.push(1);
        st.push(2);
        st.push(3);
        st.push(4);
         
        System.out.println("Original Stack");
        System.out.println(st); // [1, 2, 3, 4]
        st.reverse();
        System.out.println("Reversed Stack");
        System.out.println(st); // [4, 3, 2, 1]
    }
}
