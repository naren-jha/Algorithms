package basic.stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * k Stacks in a single array in time and space efficient way
 */
public class KStacks {

    // Array of size n to store actual content to be stored in stacks
    private int[] arr;
    
    // Array of size k to store indexes of top elements of stacks
    private int[] top;
    
    // Array of size n to store next entry in all stacks and free list
    private int[] next;
    
    private int free; // To store beginning index of free list
    
    // Constructor
    public KStacks(int n, int k) {
        // Create array objects
        arr = new int[n];
        top = new int[k];
        next = new int[n];
        
        // Initialize all stacks as empty
        for (int i = 0; i < k; i++)
            top[i] = -1;
        
        // Initialize all spaces as free
        for (int i = 0; i < n-1; i++)
            next[i] = i + 1;
        next[n-1] = -1; // -1 is used to indicate end of free list
        
        // Initialize beginning of free list as 
        // first element of array, i.e., 0
        free = 0;
    }
    
    // To push an item in stack number 'sn' where 0 <= sn <= k-1
    public void push(int val, int sn) {
        if (isFull())
            throw new IllegalStateException("Stack overflow");
        
        int i = free; // Get index of first free slot
        
        // Update index of free slot to index of next slot in free list
        free = next[i];
        
        next[i] = top[sn]; // Store next of top
        top[sn] = i; // Update top for stack number 'sn'        
        
        arr[i] = val; // Put the item in array
    }
    
    // To pop an item from stack number 'sn' where 0 <= sn <= k-1
    public int pop(int sn) {
        if (isEmpty(sn))
            throw new IllegalStateException("Stack underflow");
        
        int i = top[sn]; // Get index of top item in stack number 'sn'
        top[sn] = next[i]; // Update top to next of previous top
        
        // Attach the previous top to the beginning of free list
        next[i] = free;
        
        // Make index being popped as the first free index
        free = i;
        
        // Return top of stack number 'sn'
        return arr[i];
    }
    
    // To check if stack number 'sn' is empty or not
    public boolean isEmpty(int sn) {
        return top[sn] == -1;
    }
    
    // A utility function to check if there is space available or not
    public boolean isFull() {
        /*when array is exhausted 'free' will be -1
        because that is what we set next[n-1] to*/
        return free == -1;
    }
    
    public static void main(String[] args) {
        // Let's create 3 stacks in an array of size 10
        KStacks ks = new KStacks(10, 3);
        ks.push(1, 0); // push 1 in stack1
        ks.push(10, 1); // push 10 in stack2
        ks.push(100, 2); // push 100 in stack3
        
        ks.push(2, 0); // push 2 in stack1
        ks.push(20, 1); // push 20 in stack2
        ks.push(200, 2); // push 200 in stack3
        
        ks.push(3, 0); // push 3 in stack1
        ks.push(30, 1); // push 30 in stack2
        ks.push(300, 2); // push 300 in stack3
        
        System.out.println(ks.pop(0)); // 3
        System.out.println(ks.pop(0)); // 2
        System.out.println(ks.pop(1)); // 30
        System.out.println(ks.pop(2)); // 300
        System.out.println(ks.pop(1)); // 20
        System.out.println(ks.pop(2)); // 200
    }
}
