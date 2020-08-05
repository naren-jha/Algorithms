package basic.queue.usingarray;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Deque using circular array
 */
class Deque {
    private int[] a;
    private int front;
    private int rear;
    private int N; // size of array
    
    public Deque() {
        this(16);
    }
    
    public Deque(int arrSize) {
        front = rear = -1;
        N = arrSize;
        a = new int[N];
    }
    
    // Inserts an element at the front end of the deque
    public void insertFront(int key) {
        if (isFull())
            throw new IllegalStateException("Deque is full.");
        
        if (isEmpty())
            front = rear = 0;
        else
            front = (front - 1 + N) % N;
        a[front] = key;
    }
    
    // Inserts an element at the rear end of the deque
    public void insertRear(int key) {
        if (isFull())
            throw new IllegalStateException("Deque is full");
        
        if (isEmpty())
            front = rear = 0;
        else
            rear = (rear + 1) % N;
        a[rear] = key;
    }
    
    // Deletes and returns element from the front end of the deque
    public int deleteFront() {
        if (isEmpty())
            throw new IllegalStateException("Deque is empty");
        
        int key = a[front];
        if (front == rear)
            front = rear = -1;
        else
            front = (front + 1) % N;
        return key;
    }
    
    // Deletes and returns element from the rear end of the deque
    public int deleteRear() {
        if (isEmpty())
            throw new IllegalStateException("Deque is empty");
        
        int key = a[rear];
        if (front == rear)
            rear = front = -1;
        else
            rear = (rear - 1 + N) % N;
        return key;
    }
    
    // Returns element from the front end of the deque
    // without deleting it
    public int getFront() {
        if (isEmpty())
            throw new IllegalStateException("Deque is empty");
        
        return a[front];
    }
    
    // Returns element from the rear end of the deque
    // without deleting it
    public int getRear() {
        if (isEmpty())
            throw new IllegalStateException("Deque is empty");
        
        return a[rear];
    }
    
    // Checks if deque is empty or not
    public boolean isEmpty() {
        return front == -1 && rear == -1;
    }
    
    // Checks if deque is full or not
    public boolean isFull() {
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
        
        System.out.println(dq.deleteFront()); // 40
        System.out.println(dq.deleteFront()); // 10
        System.out.println(dq); // [20, 30]
        
        dq.insertRear(40);
        dq.insertRear(10);
        System.out.println(dq); // [20, 30, 40, 10]
        
        System.out.println(dq.deleteRear()); // 10
        System.out.println(dq.deleteRear()); // 40
        System.out.println(dq); // [20, 30]
    }

}
