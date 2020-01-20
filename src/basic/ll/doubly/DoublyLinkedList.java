package basic.ll.doubly;

/**
 * @author Narendra Jha
 *
 */
class LinkedList {
	
	// Template for node in LinkedList
	class Node {
		
		private int data;
		private Node prev;
		private Node next;
		
		// Constructor
		public Node(int data) {
			this.data = data;
			this.prev = null;
			this.next = null;
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
	
	// This method mainly calls recursive size method
	public int sizeRec() {
		return size(this.head, 0);
	}
	
	// Recursive method to get size
	public int size(Node n, int size) {
		if (n == null)
			return size;
		
		size++;
		size = size(n.next, size);
		return size;
	}
	
	// Iterative method to get size
	public int sizeItr() {
		int size = 0;
		for (Node tmp = this.head; tmp != null; tmp = tmp.next)
			size++;
		
		return size;
	}
	
	// Checks if the linked list is empty or not
	public boolean isEmpty() {
		return this.head == null;
	}
	
	// Inserts given element at the beginning of the list
	public void addFirst(int val) {
		Node node = new Node(val);
		if(head == null) {
			// when list is empty
			head = node;
		}
		else {
			node.next = head;
			head.prev = node;
			head = node;
		}
	}
	
	// Appends given element to the end of the list
	public void add(int val) {
		Node node = new Node(val);
		if (head == null) {
			head = node;
		}
		else {
			Node pntr = head;
			while (pntr.next != null) {
				pntr = pntr.next;
			}
			pntr.next = node;
			node.prev= pntr;
		}
		size++;
	}
	
	// Inserts given element at the specified position in list
	public void add(int index, int val) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		// at the beginning of the list
		if (index == 0) {
			addFirst(val);
		}
		// at the end of the list
		else if (index == size) {
			add(val);
		}
		// at a position other than beginning or end
		else {
			Node node = new Node(val);
			Node pntr = head;
			for (int i = 0; i < index-1; i++) {
				pntr = pntr.next;
			}
			node.next = pntr.next;
			pntr.next.prev = node;
			pntr.next = node;
			node.prev = pntr;
		}
		size++;
	}
	
	// Removes element at the specified position in list.
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			head = head.next;
			head.prev = null;
			// here abandoned object will be deleted from memory
			// by java garbage collector.
			// if we are to implement this DS in C/C++
			// we would have to explicitly deallocate memory
			// by using "free(obj) in C" or "delete obj in C++"
		}
		else {
			Node pntr = head;
			for (int i = 0; i < index; i++) {
				pntr = pntr.next;
			}
			pntr.prev.next = pntr.next;
			if(pntr.next != null)
				pntr.next.prev = pntr.prev;
			// here abandoned object will be deleted from memory
			// by java garbage collector.
			// if we are to implement this DS in C/C++
			// we would have to explicitly deallocate memory
			// by using "free(obj) in C" or "delete obj in C++"
		}
		size--;
	}
	
	// If present, removes first occurrence of the given element from list
	public boolean remove(Object ele) {
		int val = (int) ele;
		Node pntr = head;
		while (pntr != null) {
			if(pntr.data == val) {
				if(pntr == head) {
					// when first node is the target node
					head = pntr.next;
					head.prev = null;
				}
				else {
					pntr.prev.next = pntr.next;
					if(pntr.next != null)
						pntr.next.prev = pntr.prev;
				}
				return true;
			}
			pntr = pntr.next;
		}
		return false;
	}
	
	// Returns elements at given 'index'
	public Integer get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		Node p = head;
		while(index-- != 0)
			p = p.next;
		return p.data;
	}
	
	// To print linked list using reference variable. Iterative approach
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Node pntr = head;
		while (pntr != null) {
			result.append(pntr.data).append(", ");
			pntr = pntr.next;
		}
		if (result.indexOf(",") != -1)
			result.delete(result.lastIndexOf(","), result.length());
		result.append("]");
		return result.toString();
	}
	
}

public class DoublyLinkedList {

    public static void main(String[] args) {
        LinkedList l = new LinkedList();
        l.add(4);l.add(10);l.add(8);l.add(2,3);l.add(0,3);l.addFirst(20);
        //l.remove(1);l.remove(0);
        System.out.println(l);
        System.out.println(l.get(1));
        
        Object e = 10;
        System.out.println(l.remove(e));
        System.out.println(l);
    }

}