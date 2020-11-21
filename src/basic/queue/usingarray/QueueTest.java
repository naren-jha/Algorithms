package basic.queue.usingarray;

/**
 * Implementation of Queue data structure using array
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
class Queue {
    private int[] a;
    private int front;
    private int rear;
    private int n; // current capacity of queue
    
    public Queue() {
        this(16);
    }
    
    public Queue(int n) {
        this.n = n;
        a = new int[n];
        front = rear = -1;
    }
    
    public boolean isEmpty() {
        return (front == -1 && rear == -1);
    }
    
    public void offer(int value) {
        if ((rear + 1) % n == front) {
            //throw new IllegalStateException("Queue is full");
            increaseCapacity();
        }
        
        if (isEmpty())
            front = rear = 0;
        else
            rear = (rear + 1) % n;
        a[rear] = value;
    }

    private void increaseCapacity() {
        int[] ta = a;
        a = new int[2*n];
        
        // copy 'n' elements from 'front' to 'rear'
        for (int i = 0, j = front; i < n; i++, j = (j+1)%n)
            a[i] = ta[j];
        
        front = 0; rear = n-1;
        n *= 2;
    }
    
    public int poll() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        
        int value = a[front];
        if (front == rear)
            front = rear = -1;
        else
            front = (front + 1) % n;
        return value;
    }
    
    public int peek() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        return a[front];
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = front; i != rear; i = (i + 1) % n)
            result.append(a[i]).append(", ");
        if (rear != -1)
            result.append(a[rear]);
        result.append("]");
        return result.toString();
    }
    
}

public class QueueTest {

    public static void main(String[] args) {
        Queue q = new Queue(4);
        q.offer(2);q.offer(4);q.offer(6);q.offer(8);
        System.out.println(q.poll()); // 2
        System.out.println(q.poll()); // 4
        
        System.out.println(q); // [6, 8]
        
        q.offer(10);q.offer(12);
        q.offer(14);
        System.out.println(q); // [6, 8, 10, 12, 14]
        
        System.out.println(q.poll()); // 6
        System.out.println(q); // [8, 10, 12, 14]
        System.out.println(q.peek()); // 8
        System.out.println(q); // [8, 10, 12, 14]
    }

}
