package basic.stack.problems;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Stack with getMin() and getMax() in O(1) time and extra space
 */
public class StackWithMinMaxInO1TimeAndSpace {

    private int minEle, maxEle;
    private Stack<Integer> s;
    
    public StackWithMinMaxInO1TimeAndSpace() {
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
        else if (x > maxEle) {
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
        if (y < minEle) {
            int e = minEle; // current element is minEle
            minEle = 2*minEle - y; // update minimum
            return e;
        }
        else if (y > maxEle) {
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
        return (y < minEle) ? minEle : ((y > maxEle) ? maxEle : y);
    }
    
    public int getMin() {
        if (s.isEmpty())
            throw new IllegalStateException("Stack is empty.");
        
        return minEle;
    }
    
    public int getMax() {
        if (s.isEmpty())
            throw new IllegalStateException("Stack is empty.");
        
        return maxEle;
    }
    
    public static void main(String[] args) {
        StackWithMinMaxInO1TimeAndSpace s = new StackWithMinMaxInO1TimeAndSpace();
        s.push(3);
        s.push(5);
        System.out.println(s.getMin()); // 3
        System.out.println(s.getMax()); // 5
        s.push(2);
        s.push(1);
        s.push(10);
        System.out.println(s.getMin()); // 1
        System.out.println(s.getMax()); // 10
        
        System.out.println(s.pop()); // 10
        System.out.println(s.getMin()); // 1
        System.out.println(s.getMax()); // 5
        
        System.out.println(s.pop()); // 1
        System.out.println(s.peek()); // 2
        System.out.println(s.getMin()); // 2
        System.out.println(s.getMax()); // 5
    }
    
}
