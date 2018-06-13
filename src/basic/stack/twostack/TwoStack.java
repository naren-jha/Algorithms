package basic.stack.twostack;

/**
 * @author Narendra Jha
 * 
 * Two Stacks in a single array
 */
public class TwoStack {

	int[] arr;
	int top1, top2;
	int size;
	
	public TwoStack() {
		this(50); // default size: 50
	}
	
	public TwoStack(int n) {
		arr = new int[n];
		size = n;
		top1 = -1;
		top2 = size;
	}
	
	// Pushes a new element to stack1
	public void push1(int val) {
		if (top2 - top1 == 1) // when array is full
			throw new IllegalStateException("Stack overflow");
		
		arr[++top1] = val;
	}
	
	// Pushes a new element to stack2
	public void push2(int val) {
		if (top2 - top1 == 1) // when array is full
			throw new IllegalStateException("Stack overflow");
		
		arr[--top2] = val;
	}
	
	// Removes and returns top of stack1
	public int pop1() {
		if (top1 == -1) // when stack1 is empty
			throw new IllegalStateException("Stack underflow");
		
		return arr[top1--];
	}
	
	// Removes and returns top of stack2
	public int pop2() {
		if (top2 == size) // when stack2 is empty
			throw new IllegalStateException("Stack underflow");
		
		return arr[top2++];
	}
	
	// Checks if stack1 is empty or not
	public boolean isEmpty1() {
		return top1 == -1;
	}
	
	// Checks if stack2 is empty or not
	public boolean isEmpty2() {
		return top2 == size;
	}
	
	// Checks if array is full or not
	public boolean isFull() {
		return top2 - top1 == 1;
	}
	
	public static void main(String[] args) {
		TwoStack ts = new TwoStack(5);
        ts.push1(5);
        ts.push2(10);
        ts.push2(15);
        ts.push1(11);
        ts.push2(7);
        System.out.println(ts.pop1()); // 11
        ts.push2(40);
        System.out.println(ts.pop2()); // 40
	}

}
