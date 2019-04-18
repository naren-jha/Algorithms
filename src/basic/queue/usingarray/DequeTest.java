package basic.queue.usingarray;

/**
 * @author Narendra Jha
 *
 * Deque using circular array
 */
class Deque {
	private int[] a;
	private int front;
	private int rear;
	private int N; // size of array
	
	public Deque() {
		this(100); // default size: 100
	}
	
	public Deque(int arrSize) {
		front = rear = -1;
		N = arrSize;
		a = new int[N];
	}
	
	// Inserts an element at the front end of the deque
	public void insertFront(int key) {
		if ((rear + 1) % N == front) {
			// when deque is full
			throw new IllegalStateException("Deque is full.");
		}
		
		if (front == -1 && rear == -1) {
			// when deque is empty
			front = rear = 0;
		}
		else {
			front = (front - 1 + N) % N;
		}
		a[front] = key;
	}
	
	// Inserts an element at the rear end of the deque
	public void insertRear(int key) {
		if ((rear + 1) % N == front) {
			// when deque is full
			throw new IllegalStateException("Deque is full");
		}
		
		if (front == -1 && rear == -1) {
			// when deque is empty
			front = rear = 0;
		}
		else {
			rear = (rear + 1) % N;
		}
		a[rear] = key;
	}
	
    // Deletes and returns element from the front end of the deque
	public int deleteFront() {
		if (front == -1 && rear == -1) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		int key = a[front];
		if (front == rear) {
	        // when deque has only one element, in that case, 
	        // delete operation should make deque empty
	        front = rear = -1;
	    }
		else {
			front = (front + 1) % N;
		}
		return key;
	}
	
	// Deletes and returns element from the rear end of the deque
    public int deleteRear() {
    	if (front == -1 && rear == -1) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		int key = a[rear];
		if (front == rear) {
	        // when deque has only one element, in that case, 
	        // delete operation should make deque empty
	        rear = front = -1;
	    }
		else {
			rear = (rear - 1 + N) % N;
		}
		return key;
	}
    
    // Returns element from the front end of the deque
    // without deleting it
    public int getFront() {
    	if (front == -1 && rear == -1) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		return a[front];
	}
	
    // Returns element from the rear end of the deque
    // without deleting it
    public int getRear() {
    	if (front == -1 && rear == -1) {
			// when deque is empty
			throw new IllegalStateException("Deque is empty");
		}
		
		return a[rear];
	}
	
	// Checks if deque is empty or not
	public boolean isEmpty() {
		return front == -1 && rear == -1;
	}
	
	// Checks if deque is full or not
	public boolean isFull() {
		/* return ((front == 0 && rear == size-1) ||
	                front == rear+1) */
		return (rear + 1) % N == front;
	}
	
	// To print Deque using reference variable
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		for (int i = front; i != rear; i = (i + 1) % N)
			result.append(a[i]).append(", ");
		if (rear != -1)
			result.append(a[rear]);
		result.append("]");
		return result.toString();
	}
	
}

public class DequeTest {

	public static void main(String[] args) {
		Deque dq = new Deque(4);
		System.out.println(dq.isEmpty()); // true
		System.out.println(dq.isFull()); // false
		
		dq.insertFront(10);
		dq.insertFront(40);
		dq.insertRear(20);
		dq.insertRear(30);
		
		System.out.println(dq); // [40, 10, 20, 30]
		System.out.println(dq.isEmpty()); // false
		System.out.println(dq.isFull()); // true
		
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
