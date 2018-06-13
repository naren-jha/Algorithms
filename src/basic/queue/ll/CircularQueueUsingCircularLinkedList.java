package basic.queue.ll;

/**
 * @author Narendra Jha
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
			this.next = null;
		}
	}
	
	private Node front, rear;
	
	public CircularQueueUsingCircularLinkedList() {
		front = rear = null;
	}
	
	public boolean isEmpty() {
		return (front == null && rear == null);
	}
	
	public void enqueue(int value) {
		Node node = new Node(value);
		if (isEmpty()) {
			// when queue is empty
			front = rear = node;
			front.next = front;
			return;
		}
		rear.next = node;
		rear = node;
		rear.next = front;
	}
	
	public int dequeue() {
		int value;
		if (isEmpty()) {
			// when queue is empty
			throw new IllegalStateException("Queue is empty");
		}
		
		if (front == rear) {
	        // when queue has only one element
	        // in that case, dequeue operation
	        // should make queue empty
	        value = front.data;
	        front = rear = null;
	    }
		else {
			value = front.data;
			front = front.next;
			rear.next = front;
		}
		
		return value;
	}
	
	public int front() {
	    if (isEmpty()) {
	        // when queue is empty
	    	throw new IllegalStateException("Queue is empty");
	    }
	    return front.data;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Node pntr = front;
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
		q.enqueue(2);q.enqueue(4);q.enqueue(6);q.enqueue(8);
		System.out.println(q); // [2, 4, 6, 8]
		System.out.println(q.dequeue()); // 2
		System.out.println(q); // [4, 6, 8]
		System.out.println(q.front()); // 4
		System.out.println(q); // [4, 6, 8]
	}
}
