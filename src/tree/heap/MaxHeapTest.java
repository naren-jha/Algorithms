package tree.heap;

import java.util.Arrays;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Max-Heap Data Structure Implementation 
 */

class MaxHeap {
    private int[] items; // array to store heap
    private int size; // heap size
    
    public MaxHeap() {
        this(10); // default size 10
    }
    
    public MaxHeap(int capacity) {
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
        while (parentIndex >= 0 && items[parentIndex] < items[index]) {
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
            int largerChildIndex = leftChildIndex;
            // if current element has right child
            // and right child is even larger than left child
            if (rightChildIndex < size && 
                    items[rightChildIndex] > items[leftChildIndex]) {
                largerChildIndex = rightChildIndex;
            }
            
            // if elements are in order, 'break' the loop
            // else swap elements, update indices, and continue to next level
            if (items[index] >= items[largerChildIndex]) {
                break;
            }
            swap(index, largerChildIndex);
            index = largerChildIndex;
            leftChildIndex = getLeftIndex(index);
            rightChildIndex = getRightIndex(index);
        }
    }
    // helper methods - end -
    
    // Returns maximum element (root) from max-heap without removing it.
    public int peek() {
        if (size == 0)
            throw new IllegalStateException("Empty heap.");
        return items[0]; // Return maximum, which is root of the heap
    }
    
    // Removes and returns maximum element (root) from max-heap.
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
    
    // Increases value of key at a given index
    public void increaseKey(int index, int newKey) {
        if (index > size - 1)
            throw new IllegalStateException("Index overflow.");
        if (newKey < items[index])
            throw new IllegalStateException("New key is smaller than current key");
        if (newKey == items[index])
            return;
        
        items[index] = newKey; // update key
        heapifyUp(index);
    }
    
    // Deletes key at a given index
    public void delete(int index) {
        if (index > size - 1)
            throw new IllegalStateException("Index overflow.");
        
        increaseKey(index, Integer.MAX_VALUE); // bring the key at root
        poll(); // and then delete
    }
    
}

public class MaxHeapTest {

    public static void main(String[] args) {
        MaxHeap h = new MaxHeap();
        h.add(3);
        h.add(2);
        h.add(15);
        h.add(5);
        h.add(4);
        h.delete(1);
        h.add(45);
        
        System.out.println(h.poll()); // 45
        System.out.println(h.peek()); // 15
        h.increaseKey(2, 25);
        System.out.println(h.peek()); // 25
    }
    
}
