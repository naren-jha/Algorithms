package basic.queue.usingarray;

import java.util.Arrays;

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
    
    public Queue() {
        this(16);
    }
    
    public Queue(int n) {
        front = rear = -1;
        a = new int[n];
    }
    
    public boolean isEmpty() {
        return (front == -1 && rear == -1);
    }
    
    public void offer(int value) {
        int n = a.length;
        if ((rear + 1) % n == front) {
            //throw new IllegalStateException("Queue is full");
            n *= 2;
            a = Arrays.copyOf(a, n);
        }
        
        if (isEmpty())
            front = rear = 0;
        else
            rear = (rear + 1) % n;
        a[rear] = value;
    }
    
    public int poll() {
        if (isEmpty())
            throw new IllegalStateException("empty queue");
        
        int n = a.length;
        int value= a[front];
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
        int n = a.length;
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
        Queue q = new Queue(3);
        q.offer(2);q.offer(4);q.offer(6);q.offer(8);
        System.out.println(q); // [2, 4, 6, 8]
        System.out.println(q.poll()); // 2
        System.out.println(q); // [4, 6, 8]
        System.out.println(q.peek()); // 4
        System.out.println(q); // [4, 6, 8]
    }

}
