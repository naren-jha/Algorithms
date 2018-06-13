package basic.queue.array;

/**
 * @author Narendra Jha
 *
 * Queue using array
 */
public class QueueTest {

	public static void main(String[] args) {
		Queue q = new Queue();
		q.enqueue(2);q.enqueue(4);q.enqueue(6);q.enqueue(8);
		System.out.println(q); // [2, 4, 6, 8]
		System.out.println(q.dequeue()); // 2
		System.out.println(q); // [4, 6, 8]
		System.out.println(q.front()); // 4
		System.out.println(q); // [4, 6, 8]
	}

}

class Queue {
	private int[] a;
	private int front;
	private int rear;
	private int N = 100;
	
	public Queue() {
		front = rear = -1;
		a = new int[N];
	}
	
	public boolean isEmpty() {
		return (front == -1 && rear == -1);
	}
	
	public void enqueue(int value) {
		if ((rear + 1) % N == front) {
			// when queue is full
			throw new IllegalStateException("Queue is full");
		}
		else if (isEmpty()) {
			// when queue is empty
			front = rear = 0;
		}
		else {
			rear = (rear + 1) % N;
		}
		a[rear] = value;
	}
	
	public int dequeue() {
		int value;
		if (isEmpty()) {
			// when queue is empty
			throw new IllegalStateException("Queue is empty");
		}
		else if (front == rear) {
	        // when queue has only one element
	        // in that case, dequeue operation
	        // should make queue empty
	        value = a[front];
	        front = rear = -1;
	    }
		else {
			value = a[front];
			front = (front + 1) % N;
		}
		return value;
	}
	
	public int front() {
	    if (isEmpty()) {
	        // when queue is empty
	    	throw new IllegalStateException("Queue is empty");
	    }
	    return a[front];
	}
	
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
