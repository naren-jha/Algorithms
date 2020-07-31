package basic.ll.doubly;

/**
 * Implementation of CircularDoublyLinkedList data structure
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class CircularDoublyLinkedList {

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
    
    private Node head;
    
    public CircularDoublyLinkedList() {
        this.head = null;
    }
    
    // This method mainly calls recursive size method
    public int sizeRec() {
        if (head == null) return 0;
        return size(head);
    }
    
    // Recursive method to get size
    public int size(Node n) {
        if (n.next == head) return 1;
        return size(n.next) + 1;
    }
    
    // Iterative method to get size
    public int size() {
        // When list is empty
        if (head == null) return 0;
        
        int size = 1;
        for (Node p = head; p.next != head; p = p.next)
            size++;
        return size;
    }
    
    // Checks if the linked list is empty or not
    public boolean isEmpty() {
        return head == null;
    }
    
    // Utility method to add given element to an empty list
    private void addToEmpty(int val) {
        Node node = new Node(val);
        node.next = node.prev = node; // point to itself
        head = node; // initialize 'head' pointer
    }
    
    // Inserts given element at the beginning of the list
    // Time Complexity: O(1)
    public void addFirst(int val) {
        // When list is empty
        if (head == null) {
            addToEmpty(val);
            return;
        }
        
        Node last = head.prev;
        Node node = new Node(val);
        node.next = head;
        node.prev = last;
        last.next = head.prev = node;
        head = node;
    }
    
    // Appends given element at the end of the list
    // Time Complexity: O(1)
    public void addLast(int val) {
        // When list is empty
        if (head == null) {
            addToEmpty(val);
            return;
        }
        
        Node last = head.prev;
        Node node = new Node(val);
        node.next = head;
        node.prev = last;
        last.next = head.prev = node;
    }
    
    // Inserts given element after a given element in the list
    // Time Complexity: O(n)
    public void addAfter(int val, int item) {
        // When list is empty
        if (head == null)
            throw new IllegalStateException("Empty list.");
        
        /* Algorithm:
         * 1. Create a node, say T
         * 2. Search the node after which T need to be insert, say that node is P
         * 3. Make T.next = P.next, T.prev = P,
         * 4. P.next.prev = T and P.next = T
         * */
        Node node = null;
        Node pntr = head;
        do {
            if (pntr.data == item) {
                node = new Node(val);
                node.next = pntr.next;
                node.prev = pntr;
                pntr.next.prev = node;
                pntr.next = node;
                return;
            }
            pntr = pntr.next;
        } while (pntr != head);
        
        throw new IllegalStateException(item + " is not present in the list.");
    }
    
    // If present, removes first occurrence of the given element from list
    public boolean remove(int key) {
        // When list is empty
        if (head == null)
            return false;
        
        Node curr = head;
        do {
            if (curr.data == key) {
                // When target node is the only node in list
                if (head.next == head) {
                    head = null;
                }
                // When more than one node in list
                else {
                    curr.next.prev = curr.prev;
                    curr.prev.next = curr.next;
                    
                    if (curr == head)
                        head = curr.next;
                }
                return true;
            }
            
            curr = curr.next;
        } while (curr != head);
        
        return false;
    }
    
    // To print list using reference variable. Iterative approach
    @Override
    public String toString() {
        // When list is empty
        if (head == null)
            return "[]";
        
        StringBuilder result = new StringBuilder("[");
        Node pntr = head;
        do {
            result.append(pntr.data).append(", ");
            pntr = pntr.next;
        } while (pntr != head);
        result.delete(result.lastIndexOf(","), result.length());
        result.append("]");
        return result.toString();
    }
    
    // Main method to test the program
    public static void main(String[] args) {
        CircularDoublyLinkedList l = new CircularDoublyLinkedList();
        System.out.println(l.sizeRec()); // 0
        l.addLast(30);l.addFirst(20);l.addLast(40);l.addFirst(10);
        l.addAfter(35, 30);l.addAfter(45, 40);
        System.out.println(l); // [10, 20, 30, 35, 40, 45]
        System.out.println(l.size()); // 6
        System.out.println(l.sizeRec()); // 6
        l.remove(30);
        System.out.println(l); // [10, 20, 35, 40, 45]
    }

}
