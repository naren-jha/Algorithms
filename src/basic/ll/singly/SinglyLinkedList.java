package basic.ll.singly;

/**
 * Implementation of Singly LinkedList data structure
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
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
    
    // for detecting loop and fixing it
    private Node slowPointer, fastPointer;
    
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
        size = size(n.link, size);
        return size;
    }
    
    // Iterative method to get size
    public int sizeItr() {
        int size = 0;
        for (Node tmp = this.head; tmp != null; tmp = tmp.link)
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
        node.link = head;
        head = node;
    }
    
    // Appends given element to the end of the list
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
                pntr = pntr.link;
            }
            node.link = pntr.link;
            pntr.link = node;
        }
        size++;
    }
    
    // Removes element at the specified position in list.
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        if (index == 0) {
            head = head.link;
            // here abandoned object will be deleted from memory
            // by java garbage collector.
            // if we are to implement this DS in C/C++
            // we would have to explicitly deallocate memory
            // by using "free(obj) in C" or "delete obj in C++"
        }
        else {
            Node pntr = head;
            for (int i = 0; i < index-1; i++) {
                pntr = pntr.link;
            }
            Node targetNode = pntr.link;
            pntr.link = targetNode.link;
            // here targetNode object will be abandoned, and
            // therefore will be taken care by java garbage collector.
            // if we are to implement this DS in C/C++
            // we would have to explicitly deallocate memory
            // by using "free(targetNode) in C" 
            // or "delete targetNode in C++"
        }
        size--;
    }
    
    // If present, removes first occurrence of the given element from list
    public boolean remove(Object ele) {
        int val = (int) ele;
        Node pntr = head, prevNode = null;
        while (pntr != null) {
            if(pntr.data == val) {
                if(pntr == head) // when first node is the target node
                    head = pntr.link;
                else
                    prevNode.link = pntr.link;
                return true;
            }
            prevNode = pntr;
            pntr = pntr.link;
        }
        return false;
    }
    
    // Iterative method to reverse LinkedList.
    public void reverse() {
        Node curr, prev, next;
        curr = head;
        prev = null;
        
        while (curr != null) {
            // 1. store next node address in a temporary variable 'next'
            // 2. set current node link to previous node
            // 3. update previous node and current node
            next = curr.link; // 1
            curr.link = prev; // 2
            prev = curr; // 3
            curr = next; // 3
        }
        // finally update head to reversed linked list's first node
        // which is original linked list's last node
        head = prev;
    }
    
    /**
     * @param p is head node of linked list
     */
    // Recursive method to reverse LinkedList.
    /* In a practical scenario recursive approach is not recommended
     * as it involves plenty of method calls, which doesn't just takes up 
     * lot of memory space in stack, but also slows down system performance.
     * for every method call, some memory is allocated in stack for 
     * execution of that method call (to store its local variables and all)
     * and assigning dedicated memory for a method execution and clearing it 
     * when execution is over, is a time consuming process. and if number of
     * method calls reaches a million or so, system may go out of memory or
     * may just become very slow.
     * */
    public void reverse(Node p) {
        if (p.link == null) {
            // exit condition
            /* update head to reversed linked list's first node
             * which is original linked list's last node */
            head = p;
            return;
        }
        reverse(p.link);
        
        // link of current nodes's next node is set to current node
        // means the pointer is reversed
        Node q = p.link;
        q.link = p;
        
        // to set last node of reversed linked list to null
        p.link = null;
    }
    
    /**
     * @param p is head node of linked list
     */
    // Prints linked list using recursive approach
    public void print(Node p) {
        if (p == null) {
            // exit condition
            System.out.println(); // new line after printing all elements
            return;
        }
        System.out.print(p.data + " ");
        print(p.link);
    }
    
    /**
     * @param p is head node of linked list
     */
    // Prints reversed linked list using recursive approach
    public void printReverse(Node p) {
        if (p == null) return; // exit condition
        printReverse(p.link);
        System.out.print(p.data+ " ");
        
    }
    
    // Returns elements at given 'index'
    public Integer get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node p = head;
        while(index-- != 0)
            p = p.link;
        return p.data;
    }
    
    // Detects loop in linked list
    public boolean hasLoop() {
        slowPointer = fastPointer = head;
        while (fastPointer != null && fastPointer.link != null) {
            slowPointer = slowPointer.link;
            fastPointer = fastPointer.link.link;
            if (slowPointer == fastPointer) {
                System.out.println("Loop found");
                return true;
            }
        }
        System.out.println("No loop found.");
        return false;
    }
    
    // Fixes loop in linkedlist
    public void fixLoop() {
        // Finding start of the loop
        slowPointer = head;
        while(slowPointer != fastPointer) {
            slowPointer = slowPointer.link;
            fastPointer = fastPointer.link;
        }
        // Now both slowPointer and fastPointer points to start of the loop.
        System.out.println("Start of the loop is: "+ slowPointer + 
                " Which holds value: " + slowPointer.data);

        // Fixing the loop by breaking link
        while(fastPointer.link != slowPointer) {
            fastPointer = fastPointer.link;
        }
        fastPointer.link = null;
        System.out.println("Loop fixed.");
    }

    
    // Introduces loop of length 'l'.
    // this is method is created for testing purposes
    // so that we can introduce loop in a linked list and
    // then detect and fix the same.
    public void introduceLoop(int len) {
        if (head == null) {
            System.out.println("List is empty. Cannot introduce loop.");
            return;
        }
        
        // start with two pointers at head
        // then move one pointer 'len' nodes forward,
        // to get a gap of 'len' between the two pointers.
        // In between, if the moving pointer becomes null
        // then length passed was incorrect and less than 
        // (size of linkedlist + 1)
        Node p1 = head, p2 = head;
        int count = 0;
        while (count < len) {
            if(p2 == null) {
                System.out.println("Incorrect length. Cannot introduce loop.");
                return;
            }
            p2 = p2.link;
            count++;
        }
        
        // Now as both pointers are 'len' nodes apart,
        // move both the pointers together till 
        // leading pointer reaches to the last node
        // i.e., till it's next node is not null
        while (p2.link != null) {
            p1 = p1.link;
            p2 = p2.link;
        }
        p2.link = p1.link;
        // connecting last node to some node would count as length of 1
        // and therefore, loop length would become 'len+1'
        // that's why, we do p1.getlink(), to reduce length by 1. 
        // so that it compensates loop length to keep it 'len'
    }
    
    
    /* some other useful methods */
    // Removes all of the elements from this list.
    public void clear() {
        // just make head = null
        // and first node will be taken care by GC
        // as head will no more be pointing to it
        // and similarly second and subsequent nodes will be
        // deleted from memory as well by Java GC
        head = null;
    }

    // Returns true if this list contains the specified element.
    public boolean contains(int val) {
        Node pntr = head;
        while (pntr != null) {
            if(pntr.data == val)
                return true;
            pntr = pntr.link;
        }
        return false;
    }

    // Returns the index of the first occurrence of the
    // specified element in this list, 
    // or -1 if this list does not contain the element.
    public int indexOf(int val) {
        Node pntr = head;
        int index = 0;
        while (pntr != null) {
            if(pntr.data == val)
                return index;
            pntr = pntr.link;
            index++;
        }
        return -1;
    }

    // Returns the index of the last occurrence of the
    // specified element in this list, 
    // or -1 if this list does not contain the element.
    public int lastIndexOf(int val) {
        Node pntr = head;
        int index = 0, lastIndex = -1;;
        while (pntr != null) {
            if(pntr.data == val)
                lastIndex = index;
            pntr = pntr.link;
            index++;
        }
        return lastIndex;
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

public class SinglyLinkedList {

    public static void main(String[] args) {
        
        LinkedList l = new LinkedList();
        l.add(4);l.add(10);l.add(8);l.add(2,3);l.addFirst(20);
        //l.remove(1);l.remove(0);
        //l.reverse();
        //l.reverse(l.head);
        System.out.println(l); // [10, 4, 10, 3, 8]
        System.out.println(l.get(1)); // 10
        
        //l.clear();
        //System.out.println(l);
        //l.clear();
        
        //System.out.println(l.contains(8));
        //System.out.println(l.indexOf(10));
        //l.add(3);System.out.println(l.lastIndexOf(3));
        
        //Object e = 10;
        //System.out.println(l.remove(e));
        //System.out.println(l);
        
        //l.print(l.head);
        //l.printReverse(l.head);
        
        l.introduceLoop(2);
        if(l.hasLoop())
            l.fixLoop();
        l.hasLoop();
    }

}

