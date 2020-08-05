package basic.stack.problems;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * reversing stack
 */
class Stack1 {
    
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
    
    private Node top; // top of stack
    
    public Stack1() {
        this.top = null;
    }
    
    // reverses stack using simple linked list reversal logic.
    public void reverse() {
        Node curr, prev, next;
        curr = top;
        prev = null;
        
        while (curr != null) {
            // 1. store next node address in a temporary variable 'next'
            // 2. set current node link to previous node
            // 3. update previous node and current node
            next = curr.next; // 1
            curr.next = prev; // 2
            prev = curr; // 3
            curr = next; // 3
        }
        // finally update top to reversed linked list's first node
        // which is original linked list's last node
        top = prev;
    }
    
    public void push(int val) {
        Node node = new Node(val);
        node.next = top;
        top = node;
    }
    
    public int pop() {
        if(top == null) {
            // when stack is empty
            throw new IllegalStateException("Stack is empty.");
        }
        Node targetNode = top;
        top = top.next;
        return targetNode.data;
    }
    
    public int peek() {
        if(top == null) {
            // when stack is empty
            throw new IllegalStateException("Stack is empty.");
        }
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

public class ReverseStack {

    public static void main(String[] args) {
        Stack1 s = new Stack1();
        s.push(1);s.push(2);s.push(3);s.push(4);
        System.out.println("Original stack: " + s); // [4, 3, 2, 1]
        s.reverse();
        System.out.println("Reversed stack: " + s); // [1, 2, 3, 4]
    }

}
