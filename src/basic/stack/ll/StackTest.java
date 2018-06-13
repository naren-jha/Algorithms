package basic.stack.ll;

/**
 * @author Narendra Jha
 *
 * Stack using LinkedList
 */
public class StackTest {

	public static void main(String[] args) {
		Stack s = new Stack();
		s.push(2);s.push(5);s.push(3);s.push(7);
		System.out.println(s); // [7, 3, 5, 2]
		System.out.println(s.pop()); // 7
		System.out.println(s); // [3, 5, 2]
		System.out.println(s.peek()); // 3
		System.out.println(s); // [3, 5, 2]
	}

}

class Stack {
	
	// Template for node in LinkedList
	class Node {
		private int data;
		private Node link;
		
		// Constructor
		public Node(int data) {
			this.data = data;
			this.link = null;
		}
	}
	
	private Node top; // head is top of stack
	
	public Stack() {
		this.top = null;
	}
	
	public void push(int val) {
		Node node = new Node(val);
		node.link = top;
		top = node;
	}
	
	public int pop() {
		if(top == null) {
			// when stack is empty
			throw new IllegalStateException("Stack is empty.");
		}
		Node targetNode = top;
		top = top.link;
		return targetNode.data;
	}
	
	public int peek() {
		if(top == null) {
			// when stack is empty
			throw new IllegalStateException("Stack is empty.");
		}
		return top.data;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Node pntr = top;
		while (pntr != null) {
			result.append(pntr.data).append(", ");
			pntr = pntr.link;
		}
		if (result.indexOf(",") != -1)
			result.delete(result.lastIndexOf(","), result.length());
		result.append("]");
		return result.toString();
	}
}
