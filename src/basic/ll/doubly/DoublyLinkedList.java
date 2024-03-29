package basic.ll.doubly;

/**
 * Implementation of Doubly LinkedList data structure
 * 
 * @author Narendra Jha, njha.sde@gmail.com
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
        return size;
    }
    
    // This method mainly calls recursive size method
    public int sizeRec() {
        return size(head);
    }
    
    // Recursive method to get size
    public int size(Node n) {
        if (n == null) return 0;
        return size(n.next) + 1;
    }
    
    // Iterative method to get size
    public int sizeItr() {
        int size = 0;
        for (Node p = head; p != null; p = p.next)
            size++;
        
        return size;
    }
    
    // Checks if the linked list is empty or not
    public boolean isEmpty() {
        return head == null;
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
        size++;
    }
    
    // Appends given element to the end of the list
    public void add(int val) {
        Node node = new Node(val);
        if (head == null) {
            head = node;
        }
        else {
            Node p = head;
            while (p.next != null) p = p.next;
            p.next = node;
            node.prev= p;
        }
        size++;
    }
    
    // Inserts given element at the specified position in list
    public void add(int index, int val) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        
        // at the beginning of the list
        if (index == 0) {
            addFirst(val);
        }
        else {
            Node p = head;
            while (--index != 0) p = p.next;
            Node node = new Node(val);
            node.next = p.next;
            node.prev = p;
            if (p.next != null) p.next.prev = node;
            p.next = node;
            size++;
        }
        
    }
    
    // Removes element at the specified position in list.
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        
        if (index == 0) {
            head = head.next;
            head.prev = null;
        }
        else {
            Node p = head;
            while (index-- != 0) p = p.next;
            p.prev.next = p.next;
            if (p.next != null) p.next.prev = p.prev;
        }
        size--;
    }
    
    // If present, removes first occurrence of the given element from list
    public boolean remove(Object ele) {
        int val = (int) ele;
        Node pntr = head;
        while (pntr != null) {
            if (pntr.data == val) {
                if (pntr == head) {
                    // when first node is the target node
                    head = head.next;
                    if (head != null)
                        head.prev = null;
                }
                else {
                    pntr.prev.next = pntr.next;
                    if (pntr.next != null)
                        pntr.next.prev = pntr.prev;
                }
                
                size--;
                return true;
            }
            pntr = pntr.next;
        }
        return false;
    }
    
    // Returns elements at given 'index'
    public Integer get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        
        Node p = head;
        while(index-- != 0) p = p.next;
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
        System.out.println(l); // [20, 3, 4, 10, 3, 8]
        System.out.println(l.size()); // 6
        
        l.remove(1);l.remove(4);
        System.out.println(l); // [20, 4, 10, 3]
        System.out.println(l.get(1)); // 4
        
        Object e = 10;
        System.out.println(l.remove(e)); // true
        System.out.println(l); // [20, 4, 3]
        
        l = new LinkedList();
        l.add(4);
        e = 4;
        System.out.println(l.remove(e)); // true
        System.out.println(l); // []
    }

}