package basic.queue.using_linkedlist;

/**
 * Implementation of Queue data structure using Linked List
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
class Queue {
    
    // Template for node in LinkedList
    class Node {
        int data;
        Node next;
        
        // Constructor
        public Node(int data) {
            this.data = data;
        }
    }
    
    private Node front;
    private Node rear;
    
    public Queue() {
        front = rear = null;
    }
    
    public boolean isEmpty() {
        return (front == null && rear == null);
    }
    
    public void offer(int value) {
        Node node = new Node(value);
        if (isEmpty()) {
            front = rear = node;
            return;
        }
        
        rear.next = node;
        rear = node;
    }
    
    public int poll() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        
        int value = front.data;
        if (front == rear)
            front = rear = null;
        else
            front = front.next;
        return value;
    }
    
    public int peek() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        return front.data;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node pntr = front;
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

public class QueueTest {

    public static void main(String[] args) {
        Queue q = new Queue();
        q.offer(2);q.offer(4);q.offer(6);q.offer(8);
        System.out.println(q); // [2, 4, 6, 8]
        System.out.println(q.poll()); // 2
        System.out.println(q); // [4, 6, 8]
        System.out.println(q.peek()); // 4
        System.out.println(q); // [4, 6, 8]
    }

}
