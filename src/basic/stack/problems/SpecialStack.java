package basic.stack.problems;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * A class that supports all the stack operations and one additional 
 * operation getMin() that returns the minimum element from stack at 
 * any time.  This class inherits from the Stack class and uses an 
 * auxiliary stack that holds minimum elements
 */
public class SpecialStack extends Stack2 {

    private Stack2 min; // auxiliary stack
    
    public SpecialStack() {
        min = new Stack2();
    }
    
    /* SpecialStack's member method to insert an element to it. 
     * This method makes sure that the min stack is also updated 
     * with appropriate minimum values */
    @Override
    public void push(int val) {
        if (isEmpty()) {
            super.push(val);
            min.push(val);
        }
        else {
            super.push(val);
            int currMin = min.peek();
            if (val <= currMin)
                min.push(val); // 'val' is new minimum
        }
    }

    /* SpecialStack's member method to remove an element from it. 
     * This method removes top element from min stack as well. */
    @Override
    public int pop() {
        int key = super.pop();
        int currMin = min.peek();
        
        if (key == currMin)
            min.pop();
        return key;
    }

    /* SpecialStack's member method to get minimum element from it. */
    public int getMin() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty.");
        
        return min.peek();
    }
    
    public static void main(String[] args) {
        SpecialStack s = new SpecialStack();
        s.push(10);
        s.push(20);
        s.push(30);
        System.out.println(s.getMin()); // 10
        s.pop();
        System.out.println(s.getMin()); // 10
        s.push(5);
        System.out.println(s.getMin()); // 5
    }
    
}

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
            throw new IllegalStateException("Can't pop element from"
                    + " stack: stack is empty.");
        }
        return elements[top--];
    }
    
    public int peek() {
        if(top == -1) {
            // when stack is empty
            throw new IllegalStateException("Can't pop element from"
                    + " stack: stack is empty.");
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
