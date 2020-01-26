package basic.stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Stack with middle operations on it.
 */
public class StackWithMiddleOperation {

    // Template for node of doubly LinkedList
    class Node {
        
        private int data;
        private Node prev;
        private Node next;
        
        // Constructor
        public Node(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }
    
    private Node top, mid;
    private int count; // no. of nodes.
    
    public StackWithMiddleOperation() {
        this.top = null;
        this.mid = null;
    }
    
    // Pushes an element to the top of the Stack
    // by inserting a node at the front of the doubly linked list
    public void push(int val) {
        Node node = new Node(val);
        count++;
        
        if (top == null) {
            // when list is empty
            mid = node;
        }
        else {
            node.next = top;
            top.prev = node;
            
            if (count % 2 != 0) // update mid when count is odd
                mid = mid.prev;
        }
        top = node;
    }
    
    // Removes and returns the top of stack.
    public int pop() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty.");
        }
        
        int key = top.data;
        top = top.next;
        if (top != null) {
            top.prev = null;
        }
        count--;
        
        if (count % 2 == 0) // update mid when count is even
            mid = mid.next;
        
        return key;
    }
    
    // Returns the top of stack without removing it.
    public int peek() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty.");
        }
        
        return top.data;
    }
    
    // Returns the middle element of stack.
    public int getMiddle() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty.");
        }
        
        return mid.data;
    }
    
    // Deletes and returns the middle element of stack.
    public int deleteMiddle() {
        if (top == null) { // count == 0
            throw new IllegalStateException("Stack is empty.");
        }
        
        int key = mid.data;
        if (count == 1) {
            top = mid = null;
        }
        else if (count == 2) {
            mid = top;
            top.next = null;
        }
        else {            
            // update links
            mid.prev.next = mid.next;
            mid.next.prev = mid.prev;
            
            // update mid
            if (count % 2 == 0)
                mid = mid.prev;
            else
                mid = mid.next;
        }
        count--;
        return key;
    }
    
    public static void main(String[] args) {
        StackWithMiddleOperation s = new StackWithMiddleOperation();
        s.push(10);
        System.out.println(s.getMiddle()); // 10
        s.push(20);
        System.out.println(s.getMiddle()); // 10
        s.push(30);
        System.out.println(s.getMiddle()); // 20
        s.push(40);
        System.out.println(s.getMiddle()); // 20
        s.push(50);
        System.out.println(s.getMiddle()); // 30
        System.out.println(s.pop()); // 50
        System.out.println(s.getMiddle()); // 20
        System.out.println(s.deleteMiddle()); // 20
        System.out.println(s.getMiddle()); // 30
        System.out.println(s.deleteMiddle()); // 30
        System.out.println(s.getMiddle()); // 10
        System.out.println(s.deleteMiddle()); // 10
        System.out.println(s.getMiddle()); // 40
        System.out.println(s.deleteMiddle()); // 40
    }
    
}
