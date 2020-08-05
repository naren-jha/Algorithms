package basic.queue.problems;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * k queues in a single array in 
 * time and space efficient way
 */
public class KQueues {

    // Array of size n to store actual content to be stored in queues
    private int[] arr;
    
    // Array of size k to store indexes of front elements of queues
    private int[] front;
    
    // Array of size k to store indexes of rear elements of queues
    private int[] rear;
    
    // Array of size n to store next entry in all queues and free list
    private int[] next;
    
    private int free; // To store beginning index of free list
    
    // Constructor
    public KQueues(int n, int k) {
        // Create array objects
        arr = new int[n];
        front = new int[k];
        rear = new int[k];
        next = new int[n];
        
        // Initialize all queues as empty
        for (int i = 0; i < k; i++)
            front[i] = -1;
        
        // Initialize all spaces as free
        for (int i = 0; i < n-1; i++)
            next[i] = i + 1;
        next[n-1] = -1; // -1 is used to indicate end of free list
        
        // Initialize beginning of free list as 
        // first element of array, i.e., 0
        free = 0;
    }
    
    // To enqueue an item in queue number 'qn' where 0 <= qn <= k-1
    public void enqueue(int val, int qn) {
        if (isFull())
            throw new IllegalStateException("Queue overflow");
        
        int i = free; // Get index of first free slot
        
        // Update index of free slot to index of next slot in free list
        free = next[i];
        
        // Set front for first item in queue number 'qn', else update next of rear
        if (isEmpty(qn))
            front[qn] = i;
        else
            next[rear[qn]] = i;
        
        next[i] = -1; // update end of queue
        
        rear[qn] = i; // update rear for queue number 'qn'
        
        arr[i] = val; // Put the item in array
    }
    
    // To dequeue an item from queue number 'qn' where 0 <= qn <= k-1
    public int dequeue(int qn) {
        if (isEmpty(qn))
            throw new IllegalStateException("Queue underflow");
        
        int i = front[qn]; // Get index of front item in queue number 'qn'
        front[qn] = next[i]; // Update top to next of previous top
        
        // Attach the previous front to the beginning of free list
        next[i] = free;
        
        // Make index being dequeued as the first free index
        free = i;
        
        // Return front of queue number 'qn'
        return arr[i];
    }
    
    // To check if queue number 'qn' is empty or not
    public boolean isEmpty(int qn) {
        return front[qn] == -1;
    }
    
    // A utility function to check if there is space available or not
    public boolean isFull() {
        /*when array is exhausted 'free' will be -1
        because that is what we set next[n-1] to*/
        return free == -1;
    }
    
    public static void main(String[] args) {
        // Let's create 3 queues in an array of size 10
        KQueues kq = new KQueues(10, 3);
        kq.enqueue(1, 0); // enqueue 1 in queue1
        kq.enqueue(10, 1); // enqueue 10 in queue2
        kq.enqueue(100, 2); // enqueue 100 in queue3
        
        kq.enqueue(2, 0); // enqueue 2 in queue1
        kq.enqueue(20, 1); // enqueue 20 in queue2
        kq.enqueue(200, 2); // enqueue 200 in queue3
        
        kq.enqueue(3, 0); // enqueue 3 in queue1
        kq.enqueue(30, 1); // enqueue 30 in queue2
        kq.enqueue(300, 2); // enqueue 300 in queue3
        
        System.out.println(kq.dequeue(0)); // 1
        System.out.println(kq.dequeue(0)); // 2
        System.out.println(kq.dequeue(1)); // 10
        System.out.println(kq.dequeue(2)); // 100
        System.out.println(kq.dequeue(1)); // 20
        System.out.println(kq.dequeue(2)); // 200
    }

}
