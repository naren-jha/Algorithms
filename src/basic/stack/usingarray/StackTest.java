package basic.stack.usingarray;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Stack using array
 */
class Stack {
    int[] elements;
    int top;
    
    public Stack() {
        this(16); // default size: 16
    }
    
    public Stack(int size) {
        elements = new int[size];
        top = -1; // empty stack
    }
    
    public void push(int val) {
        if(top == elements.length - 1) {
            // When stack is full, one way of handling it is to throw
            // error saying no more elements can be pushed into stack.
            // throw new IndexOutOfBoundsException("Can't push element on"
            //         + " stack: stack is full.");
            
            // A better approach is to do a dynamic array implementation
            // and increase stack size, whenever it overflows.
            extendStack();
        }
        elements[++top] = val;
    }

    public int pop() {
        if(top == -1) {
            // when stack is empty
            throw new IllegalStateException("Stack is empty.");
        }
        return elements[top--];
    }
    
    public int peek() {
        if(top == -1) {
            // when stack is empty
            throw new IllegalStateException("Stack is empty.");
        }
        return elements[top];
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    private void extendStack() {
        int[] newStack = new int[elements.length*2];
        for (int i = 0; i < elements.length; i++) {
            newStack[i] = elements[i];
        }
        elements = newStack;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for(int i = 0; i <= top; i++)
            result.append(elements[i]).append(", ");
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
