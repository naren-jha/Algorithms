package basic.stack.usingarray;

import java.util.Arrays;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Implements Stack data structure using array
 */
class Stack {
    int[] e; // elements of stack
    int top;
    
    public Stack() {
        this(16); // default size: 16
    }
    
    public Stack(int size) {
        e = new int[size];
        top = -1; // empty stack
    }
    
    public void push(int val) {
        if (top == e.length - 1) e = Arrays.copyOf(e, 2*e.length);
        e[++top] = val;
    }

    public int pop() {
        if (isEmpty()) throw new IllegalStateException("empty stack");
        return e[top--];
    }
    
    public int peek() {
        if (isEmpty()) throw new IllegalStateException("empty stack");
        return e[top];
    }
    
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i <= top; i++)
            result.append(e[i]).append(", ");
        if (result.indexOf(",") != -1)
            result.delete(result.lastIndexOf(","), result.length());
        result.append("]");
        return result.toString();
    }
    
}

public class StackTest {

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(5);
        s.push(8);
        s.push(9);
        System.out.println(s); // [5, 8, 9]
        System.out.println(s.pop()); // 9
        System.out.println(s); // [5, 8]
        System.out.println(s.peek()); // 8
        System.out.println(s); // [5, 8]
    }
}
