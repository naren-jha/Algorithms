package tree.heap;

import java.util.Arrays;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Min-Heap Data Structure Implementation 
 */

class MinHeap {
    private int[] items; // array to store heap
    private int size; // heap size
    
    public MinHeap() {
        this(10); // default size 10
    }
    
    public MinHeap(int capacity) {
        size = 0;
        items = new int[capacity];
    }
    
    // helper methods - start -
    private int getLeftIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }
    
    private int getRightIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }
    
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }
    
    private void swap(int indexOne, int indexTwo) {
        int temp = items[indexOne];
        items[indexOne] = items[indexTwo];
        items[indexTwo] = temp;
    }
    
    private void ensureExtraCapacity() {
        // if array is exhausted, double the size of array
        if (size == items.length)
            items = Arrays.copyOf(items, items.length * 2);
    }
    
    // fixes the heap in bottom-up manner
    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        
        // while current element has a parent and 
        // parent-child elements are out of order
        while (parentIndex >= 0 && items[parentIndex] > items[index]) {
            swap(parentIndex, index);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }
    
    // fixes the heap in top-down manner
    private void heapifyDown(int index) {
        int leftChildIndex = getLeftIndex(index),
            rightChildIndex = getRightIndex(index);
        
        // while current element has left child
        // i.e., while left child index falls in the range of heap size
        while (leftChildIndex < size) {
            int smallerChildIndex = leftChildIndex;
            // if current element has right child
            // and right child is even smaller than left child
            if (rightChildIndex < size && 
                    items[rightChildIndex] < items[leftChildIndex]) {
                smallerChildIndex = rightChildIndex;
            }
            
            // if elements are in order, 'break' the loop
            // else swap elements, update indices, and continue to next level
            if (items[index] <= items[smallerChildIndex]) {
                break;
            }
            swap(index, smallerChildIndex);
            index = smallerChildIndex;
            leftChildIndex = getLeftIndex(index);
            rightChildIndex = getRightIndex(index);
        }
    }
    // helper methods - end -
    
    // Returns minimum element (root) from min-heap without removing it.
    public int peek() {
        if (size == 0)
            throw new IllegalStateException("Empty heap.");
        return items[0]; // Return minimum, which is root of the heap
    }
    
    // Removes and returns minimum element (root) from min-heap.
    public int poll() {
        if (size == 0)
            throw new IllegalStateException("Empty heap.");
        
        int item = items[0];
        items[0] = items[size - 1]; // copy last element to root
        size--; // shrink the size of the heap
        heapifyDown(0); // fix the heap
        return item;
    }
    
    // Inserts a new key
    public void add(int key) {
        ensureExtraCapacity(); // ensure space in array
        items[size] = key; // add key
        size++; // increase heap size
        heapifyUp(size - 1);
    }
    
    // Decreases value of key at a given index
    public void decreaseKey(int index, int newKey) {
        if (index > size - 1)
            throw new IllegalStateException("Index overflow.");
        if (newKey > items[index])
            throw new IllegalStateException("New key is bigger than current key");
        if (newKey == items[index])
            return;
        
        items[index] = newKey; // update key
        heapifyUp(index);
    }
    
    // Deletes key at a given index
    public void delete(int index) {
        if (index > size - 1)
            throw new IllegalStateException("Index overflow.");
        
        decreaseKey(index, Integer.MIN_VALUE); // bring the key at root
        poll(); // and then delete
    }
    
}

public class MinHeapTest {

    public static void main(String[] args) {
        MinHeap h = new MinHeap();
        h.add(3);
        h.add(2);
        h.add(15);
        h.add(5);
        h.add(4);
        h.delete(1);
        h.add(45);
        
        System.out.println(h.poll()); // 2
        System.out.println(h.peek()); // 4
        h.decreaseKey(2, 1);
        System.out.println(h.peek()); // 1
    }
    
}
