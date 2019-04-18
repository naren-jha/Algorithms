package basic.queue.using_linkedlist;

/**
 * @author Narendra Jha
 *
 * Deque using doubly linked list
 */
class Deque {
	
	// Template for node in LinkedList
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
	
	private Node front;
	private Node rear;
	
	public Deque() {
		front = rear = null;
	}
	
	// Inserts an element at the front end of the deque
	public void insertFront(int key) {
		Node node = new Node(key);
		if (front == null && rear == null) {
			// when deque is empty
			front = rear = node;
			return;
		}
		node.next = front;
		front.prev = node;
		front = node;
	}
	
	// Inserts an element at the rear end of the deque
	public void insertRear(int key) {
		Node node = new Node(key);
		if (front == null && rear == null) {
			// when deque is empty
			front = rear = node;
			return;
		}
		rear.next = node;
		node.prev = rear;
		rear = node;
	}
	
    // Deletes and returns element from the front end of the deque
	public int deleteFront() {
		if (front == null && rear == null) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		int key = front.data;
		if (front == rear) {
	        // when deque has only one element, in that case, 
	        // delete operation should make deque empty
			front = rear = null;
	    }
		else {
			// when deque has more than one element
			front = front.next;
			front.prev = null;
		}
		
		return key;
	}
	
	// Deletes and returns element from the rear end of the deque
    public int deleteRear() {
    	if (front == null && rear == null) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		int key = rear.data;
		if (front == rear) {
	        // when deque has only one element, in that case, 
	        // delete operation should make deque empty
			rear = front = null;
	    }
		else {
			// when deque has more than one element
			rear = rear.prev;
			rear.next = null;
		}
		
		return key;
	}
    
    // Returns element from the front end of the deque
    // without deleting it
    public int getFront() {
    	if (front == null && rear == null) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		return front.data;
	}
	
    // Returns element from the rear end of the deque
    // without deleting it
    public int getRear() {
    	if (front == null && rear == null) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		return rear.data;
	}
	
	// Checks if deque is empty or not
	public boolean isEmpty() {
		return front == null && rear == null;
	}
	
	// To print Deque using reference variable
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		for (Node pntr = front; pntr != null; pntr = pntr.next) {
			result.append(pntr.data).append(", ");
		}
		if (result.indexOf(",") != -1)
			result.delete(result.lastIndexOf(","), result.length());
		result.append("]");
		return result.toString();
	}
}

public class DequeTest {

	public static void main(String[] args) {
		Deque dq = new Deque();
		System.out.println(dq.isEmpty()); // true
		
		dq.insertFront(10);
		dq.insertFront(40);
		dq.insertRear(20);
		dq.insertRear(30);
		
		System.out.println(dq); // [40, 10, 20, 30]
		System.out.println(dq.isEmpty()); // false
		
		dq.deleteFront();
		dq.deleteFront();
		System.out.println(dq); // [20, 30]
		
		dq.insertRear(40);
		dq.insertRear(10);
		System.out.println(dq); // [20, 30, 40, 10]
		
		dq.deleteRear();
		dq.deleteRear();
		System.out.println(dq); // [20, 30]
	}

}
