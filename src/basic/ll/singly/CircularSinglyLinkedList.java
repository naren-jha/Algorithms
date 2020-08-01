package basic.ll.singly;

/**
 * Implementation of CircularSinglyLinkedList data structure
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class CircularSinglyLinkedList {
    
    // Template for node in LinkedList
    class Node {
        
        private int data;
        private Node next;
        
        // Constructor
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node last;
    
    public CircularSinglyLinkedList() {
        this.last = null;
    }
    
    // This method mainly calls recursive size method
    public int sizeRec() {
        if (last == null) return 0;
        return size(last);
    }
    
    // Recursive method to get size
    public int size(Node n) {
        if (n.next == last) return 1;
        return size(n.next) + 1;
    }
    
    // Iterative method to get size
    public int size() {
        // When list is empty
        if (last == null) return 0;
        
        int size = 1;
        for (Node p = last; p.next != last; p = p.next)
            size++;
        return size;
    }
    
    // Checks if the linked list is empty or not
    public boolean isEmpty() {
        return last == null;
    }
    
    // Utility method to add given element to an empty list
    private void addToEmpty(int val) {
        Node node = new Node(val);
        node.next = node; // point to itself
        last = node; // initialize 'last' pointer
    }
    
    // Inserts given element at the beginning of the list
    // Time Complexity: O(1)
    public void addFirst(int val) {
        // When list is empty
        if (last == null) {
            addToEmpty(val);
            return;
        }
        
        Node node = new Node(val);
        node.next = last.next;
        last.next = node;
    }
    
    // Appends given element at the end of the list
    // Time Complexity: O(1)
    public void addLast(int val) {
        // When list is empty
        if (last == null) {
            addToEmpty(val);
            return;
        }
        
        Node node = new Node(val);
        node.next = last.next;
        last.next = node;
        last = node;
    }
    
    // Inserts given element after a given element in the list
    // Time Complexity: O(n)
    public void addAfter(int val, int item) {
        // When list is empty
        if (last == null)
            throw new IllegalStateException("Empty list.");
        
        /* Algorithm:
         * 1. Create a node, say T
         * 2. Search the node after which T needs to be inserted,
         *    say that node is P
         * 3. Make T.next = P.next
         * 4. P.next = T
         * */
        Node node = null;
        Node p = last;
        do {
            if (p.data == item) {
                node = new Node(val);
                node.next = p.next;
                p.next = node;
                if (p == last) last = node;
                return;
            }
            p = p.next;
        } while (p != last);
        
        throw new IllegalArgumentException(item + " is not present in the list.");
    }
    
    // If present, removes first occurrence of the given element from list
    public boolean remove(int key) {
        // When list is empty
        if (last == null)
            return false;
        
        Node curr = last, prev = last;
        do {
            if (curr.data == key) {
                // When target node is the only node in list
                if (last.next == last) {
                    last = null;
                }
                // When more than one node in list
                else {
                    prev.next = curr.next;
                    if (curr == last) last = prev;
                }
                return true;
            }
            
            prev = curr;
            curr = curr.next;
        } while (curr != last);
        
        return false;
    }
    
    // To print list using reference variable. Iterative approach
    @Override
    public String toString() {
        // When list is empty
        if (last == null)
            return "[]";
        
        StringBuilder result = new StringBuilder("[");
        Node pntr = last.next;
        do {
            result.append(pntr.data).append(", ");
            pntr = pntr.next;
        } while (pntr != last.next);
        result.delete(result.lastIndexOf(","), result.length());
        result.append("]");
        return result.toString();
    }
    
    // Main method to test the program
    public static void main(String[] args) {
        CircularSinglyLinkedList l = new CircularSinglyLinkedList();
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