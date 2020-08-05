package basic.queue.using_linkedlist;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Circular Queue Using Circular Linked List
 */
public class CircularQueueUsingCircularLinkedList {
    
    // Template for node in LinkedList
    class Node {
        
        private int data;
        private Node next;
        
        // Constructor
        public Node(int data) {
            this.data = data;
        }
    }
    
    // we don't need a separate pointer to 'front' 
    // as 'rear.next' will give us 'front'
    private Node rear;
    
    public CircularQueueUsingCircularLinkedList() {
        rear = null;
    }
    
    public boolean isEmpty() {
        return rear == null;
    }
    
    public void offer(int value) {
        Node node = new Node(value);
        if (isEmpty()) {
            rear = node;
            rear.next = rear;
            return;
        }
        
        Node front = rear.next;
        rear.next = node;
        rear = node;
        rear.next = front;
    }
    
    public int poll() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        
        int value = rear.next.data;
        if (rear.next == rear)
            rear = null;
        else
            rear.next = rear.next.next;
        
        return value;
    }
    
    public int peek() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        
        return rear.next.data;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node pntr = rear.next;
        while (pntr != rear) {
            result.append(pntr.data).append(", ");
            pntr = pntr.next;
        }
        if (rear != null)
            result.append(pntr.data).append(", ");
        if (result.indexOf(",") != -1)
            result.delete(result.lastIndexOf(","), result.length());
        result.append("]");
        return result.toString();
    }
    
    public static void main(String[] args) {
        CircularQueueUsingCircularLinkedList q = new CircularQueueUsingCircularLinkedList();
        q.offer(2);q.offer(4);q.offer(6);q.offer(8);
        System.out.println(q); // [2, 4, 6, 8]
        System.out.println(q.poll()); // 2
        System.out.println(q); // [4, 6, 8]
        System.out.println(q.peek()); // 4
        System.out.println(q); // [4, 6, 8]
    }
}
