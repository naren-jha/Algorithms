package basic.array.problems;

import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeSet;

// https://www.geeksforgeeks.org/sliding-window-maximum-maximum-of-all-subarrays-of-size-k/

public class MaxOfAllSubarray {
  
  // Approach 1: using two loop
  // TC: O(nk), SC: O(1)
  public static void printMax_A1(int[] a, int k) {
    int max = Integer.MIN_VALUE, n = a.length;
    
    for (int i = 0; i <= n-k; i++) {
      for (int j = 0; j < k; j++) {
        max = Math.max(max, a[i+j]);
      }
      System.out.print(max + " ");
      max = Integer.MIN_VALUE;
    }
    
  }
  
  // Approach 2: using balanced BST
  // TC: O(nlgk), SC: O(k)
  public static void printMax_A2(int[] a, int k) {
    int n = a.length;
    
    TreeSet<Integer> redBlackTree = new TreeSet<Integer>();
    for (int i = 0; i < k; i++)
      redBlackTree.add(a[i]);
    
    int max = redBlackTree.last();
    System.out.print(max + " ");
    
    for (int i = k; i < n; i++) {
      redBlackTree.remove(a[i-k]); // O(lgk)
      redBlackTree.add(a[i]); // O(lgk)
      max = redBlackTree.last(); // O(lgk)
      System.out.print(max + " ");
    }
  }
  
  // Approach 3: dynamic programming
  // https://stackoverflow.com/questions/8031939/finding-maximum-for-every-window-of-size-k-in-an-array/17249084#17249084
  // TC: O(n), SC: O(n)
  public static void printMax_A3(int[] a, int k) {
    int n = a.length;
    
    int[] LR = new int[n];
    int[] RL = new int[n];
    
    // fill maximum from left to right
    for (int i = 0; i < n; i++) {
    	if (i % k == 0) // start of the block
    		LR[i] = a[i];
    	else
    		LR[i] = Math.max(LR[i-1], a[i]);
    }
    
    // fill maximum from right to left
    for (int i = n-1; i >= 0; i--) {
    	if (i == n-1)
    		RL[i] = a[i];
    	else if (i % k == (k-1))
    		RL[i] = a[i];
    	else
    		RL[i] = Math.max(RL[i+1], a[i]);
    }
    
    // get maximum for every window
    for (int i = 0; i <= n-k; i++)
    	System.out.print(Math.max(LR[i+k-1], RL[i]) + " ");
  }
  
  // Approach 4: using queue: most widely used approach for this problem
  // TC: O(n), SC: O(n)
  public static void printMax_A4(int[] a, int k) {
	int n = a.length;
    Deque<Integer> dq = new LinkedList<Integer>();
    
    for (int i = 0; i < k; i++) {
    	// remove smaller elements (than current element) from back of queue, 
    	// to keep the queue sorted, and make sure that maximum in each window 
    	// is at the front of the queue
    	while (!dq.isEmpty() && a[dq.peekLast()] <= a[i])
    		dq.removeLast();
    	dq.addLast(i);
    }
    
    System.out.print(a[dq.peekFirst()] + " ");
    
    for (int i = k; i < n; i++) {
    	// remove front of the queue, if it is out of current window
    	// (in each iteration only one element can go out of window)
    	if (dq.peekFirst() == i-k)
    		dq.removeFirst();
    	
    	// remove smaller elements (than current element) from back of queue, 
    	// to keep the queue sorted, and make sure that maximum in each window 
    	// is at the front of the queue
    	while (!dq.isEmpty() && a[dq.peekLast()] <= a[i])
    		dq.removeLast();
    	dq.addLast(i);
    	
    	System.out.print(a[dq.peekFirst()] + " ");
    }
  }
  
  /*
   * We can also solve this problem using a special kind of Queue, where 
   * along with queue() and dequeue() operations, max() can also be performed
   * in O(1) time. We can design this special queue using 2 queues, just like
   * we design special stack using 2 stacks (second stack/queue always
   * maintains the min/max value so far).
   */

  public static void main(String[] args) {
    int[] a = {3, 2, 1, 1, 4, 5, 2, 3, 6};
    int k = 3;
    printMax_A1(a, k); // 3 2 4 5 5 5 6
    System.out.println();
    
    printMax_A2(a, k); // 3 2 4 5 5 5 6
    System.out.println();
    
    printMax_A3(a, k); // 3 2 4 5 5 5 6
    System.out.println();
    
    printMax_A4(a, k); // 3 2 4 5 5 5 6
    System.out.println();
  }
}
