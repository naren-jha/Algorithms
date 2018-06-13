package basic.stack.appl.reverse.ll;

import java.util.Stack;

public class ReverseLinkedListUsingStack {

	public static void main(String[] args) {
		
		LinkedList l = new LinkedList();
		l.add(2);l.add(5);l.add(3);l.add(8);
		l.reverse();
		System.out.println(l);

	}
	
}

class LinkedList {
	
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
	
	private int size;
	private Node head;
	
	public LinkedList() {
		this.size = 0;
		this.head = null;
	}

	// Returns the number of elements in this list
	public int size() {
		return this.size;
	}
	
	// Checks if the linked list is empty or not
	public boolean isEmpty() {
		return this.head == null;
	}
	
	// Appends the specified element to the end of this list
	public void add(int val) {
		
		Node node = new Node(val);
		// In C we have to use malloc function
		// to implement above line as below
		// struct Node* node = (Node*)malloc(sizeof(struct node));
		
		if (head == null) {
			head = node;
		}
		else {
			Node pntr = head;
			while (pntr.link != null) {
				pntr = pntr.link;
			}
			pntr.link = node;
		}
		size++;
	}
	
	// Inserts the specified element at the specified position in this list
	public void add(int index, int val) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		Node node = new Node(val);
		// at the beginning of the list
		if (index == 0) {
			node.link = head;
			head = node;
		}
		// at the end of the list
		else if (index == size) {
			add(val);
		}
		// at a position other than beginning or end
		else {
			Node pntr = head;
			for (int i = 0; i < index-1; i++) {
				pntr = pntr.link;
			}
			node.link = pntr.link;
			pntr.link = node;
		}
		size++;
	}
	
	// Returns elements at position 'index'
	public Integer get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		Node p = head;
		while(index-- != 0)
			p = p.link;
		return p.data;
	}
	
	// Reverses LinkedList using explicit stack.
	public void reverse() {
		
		if(head == null) return;
		Stack<Node> s = new Stack<Node>();
		
		// pushing into stack
		Node temp = head;
		while(temp != null) {
			s.push(temp);
			temp = temp.link;
		}
		
		// popping from stack
		temp = s.pop();
		head = temp;
		while(!s.isEmpty()) {
			temp.link = s.pop();
			temp = temp.link;
		}
		temp.link = null;
		
	}
	
	// To print linked list using reference variable. Iterative approach
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Node pntr = head;
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
