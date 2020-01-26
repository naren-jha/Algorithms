package basic.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Calculate stock span
 */
public class StockSpan {
    
    public static int[] calculateSpan2(int[] price) {
        int n = price.length;
        
        // create an array to store span values
        int[] s = new int[n];
        
        // span value of first day is always 1
        s[0] = 1;
        
        // calculate span values of remaining days 
        // by linearly checking previous days
        for (int i = 0; i < n; i++) {
            s[i] = 1; // initialize span value
            
            // traverse left while the next element on left
            // is smaller than or equal to price[i]
            for (int j = i-1; j >= 0 && price[i] >= price[j]; j--)
                s[i]++;
        }
        return s; // return span array
    }
    
    public static int[] calculateSpan(int[] price) {
        // create a stack and push index of first element to it
        Stack<Integer> st = new Stack<Integer>();
        st.push(0);
        
        int n = price.length;
        // create an array to store span values
        int[] s = new int[n];
        
        // span value of first element is always 1
        s[0] = 1;
        
        // calculate span values for rest of the elements
        for (int i = 0; i < n; i++) {
            // pop elements from stack till stack is not empty 
            // and top of stack is smaller than or equal to price[i]
            while (!st.isEmpty() && price[st.peek()] <= price[i])
                st.pop();
            
            // if stack becomes empty, then price[i] is greater than 
            // all elements to the left of it (price[0], price[1],...
            // price[i-1]) else price[i] is greater than elements 
            // after top of stack
            s[i] = st.isEmpty() ? (i + 1) : (i - st.peek());
            
            // push this element to the stack
            st.push(i);
        }
        return s; // return span array
    }

    public static void main(String[] args) {
        int price[] = {10, 4, 5, 90, 120, 80};
         
        int[] s = calculateSpan(price); 
        System.out.println(Arrays.toString(s)); // [1, 1, 2, 4, 5, 1]
    }

}
