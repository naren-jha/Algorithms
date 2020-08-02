package basic.stack.using_linkedlist;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Stack using LinkedList
 */
class Stack {
    
    // Template for node in LinkedList
    class Node {
        private int data;
        private Node next;
        
        // Constructor
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node top; // head is top of stack
    
    public Stack() {
        top = null;
    }
    
    public void push(int val) {
        Node node = new Node(val);
        node.next = top;
        top = node;
    }
    
    public int pop() {
        if(isEmpty()) throw new IllegalStateException("empty stack");
        int val = top.data;
        top = top.next;
        return val;
    }
    
    public int peek() {
        if(isEmpty()) throw new IllegalStateException("empty stack");
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node pntr = top;
        while (pntr != null) {
            result.append(pntr.data).append(", ");
            pntr = pntr.next;
        }
        if (result.indexOf(",") != -1)
            result.delete(result.lastIndexOf(","), result.length());
        result.append("]");
        return result.toString();
    }
}

public class StackTest {

    public static void main(String[] args) {
        Stack s = new Stack();
        s.push(2);s.push(5);s.push(3);s.push(7);
        System.out.println(s); // [7, 3, 5, 2]
        System.out.println(s.pop()); // 7
        System.out.println(s); // [3, 5, 2]
        System.out.println(s.peek()); // 3
        System.out.println(s); // [3, 5, 2]
    }

}