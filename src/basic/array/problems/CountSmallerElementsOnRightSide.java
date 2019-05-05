package basic.array.problems;

// https://www.geeksforgeeks.org/count-smaller-elements-on-right-side/
// https://www.geeksforgeeks.org/count-smaller-elements-right-side-using-set-c-stl/
// https://leetcode.com/problems/count-of-smaller-numbers-after-self/
// https://www.careercup.com/question?id=12814662
// https://www.geeksforgeeks.org/counting-inversions/

public class CountSmallerElementsOnRightSide {

	/*
	 * Simplest solution is using two for loop, which takes O(n^2) time. We can solve  
	 * this problem in O(n*lgn) time using modified Self-Balancing BSTs, such as
	 * (AVL or Red-Black Tree)
	 * 
	 * Let us use AVL tree. We keep a 'size' variable in each node of the tree which 
	 * stores size of the subtree rooted with that node.
	 * 
	 * We traverse the array from right to left and insert all elements one by one in 
	 * the AVL tree. While inserting a new key in AVL tree, if key is greater than 
	 * root, then it is greater than all the nodes in left subtree of root as well. 
	 * So we take the size of left subtree + 1 (1 is for root itself) as the count of 
	 * all smaller elements for the key being inserted, and we keep adding the the 
	 * counts recursively.
	 * 
	 * It is important that we traverse the array from right to left.
	 */
	
	
	class AVLTree {

		private class Node {
			int key;
			Node left, right;
			int height;
			int size; // Modification for this problem
			
			public Node(int key) {
				this.key = key;
				left = right = null;
				height = 0;
				size = 1; // Modification for this problem
			}
		}
		
		private Node root;
		public AVLTree() {
			root = null;
		}
		
		// Get height of subtree rooted at given node
		private int height(Node node) {
			if (node == null)
				return -1; // empty tree height
			return node.height;
		}
		
		// Get size of subtree rooted at given node
		private int size(Node node) {
			if (node == null)
				return 0; // empty tree size
			return node.size;
		}
		
		// A utility function to left rotate a subtree rooted with root
		private Node leftRotate(Node root) {
			Node newRoot = root.right;
			root.right = root.right.left;
			newRoot.left = root;
			
			// update heights
			root.height = 1 + Math.max(height(root.left), height(root.right));
			newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
			
			// Modification for this problem: update size
			root.size = size(root.left) + size(root.right) + 1;
			newRoot.size = size(newRoot.left) + size(newRoot.right) + 1;
			
			return newRoot;
		}
		
		// A utility function to right rotate a subtree rooted with root
		private Node rightRotate(Node root) {
			Node newRoot = root.left;
			root.left = root.left.right;
			newRoot.right = root;
			
			// update heights
			root.height = 1 + Math.max(height(root.left), height(root.right));
			newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));
			
			// Modification for this problem: update size
			root.size = size(root.left) + size(root.right) + 1;
			newRoot.size = size(newRoot.left) + size(newRoot.right) + 1;
			
			return newRoot;
		}
		
		// Get balance for given node
		private int balance(Node node) {
			return height(node.left) - height(node.right);
		}
		
		// this method calls insert(root, key)
		public void insert(int key, int[] count, int i) {
			root = insert(root, key, count, i);
		}

		// Insert into AVL tree
		public Node insert(Node root, int key, int[] count, int i) {
			/* STEP 1:  PERFORM THE NORMAL BST INSERTION */
			if (root == null)
				return new Node(key);
			else if (key <= root.key)
				root.left = insert(root.left, key, count, i);
			else {
				root.right = insert(root.right, key, count, i);
				
				// Modification for this problem: 
				// keep adding the count of smaller elements
				count[i] += size(root.left) + 1;
			}
			
			// Modification for this problem: Update the size
			root.size = size(root.left) + size(root.right) + 1;
			
			/* STEP 2: UPDATE HEIGHT OF THIS ANCESTOR NODE */ 
			root.height = 1 + Math.max(height(root.left), height(root.right));
			
			/* STEP 3: GET THE BALANCE FACTOR OF THIS ANCESTOR NODE 
			 * (to check whether this node is unbalanced) */
			int balance = balance(root);
			
			if (balance > 1) {
				// this node is unbalanced, and its either (Left, Left) or (Left, Right) case
				
				if (balance((root.left)) >= 0) {
					// height(root.left.left) >= height(root.left.right)
					// its (Left, Left) case
					root = rightRotate(root);
				}
				else {
					// its (Left, Right) case
					root.left = leftRotate(root.left);
					root = rightRotate(root);
				}
			}
			else if (balance < -1) {
				// this node is unbalanced, and its either (Right, Right) or (Right, Left) case
				
				if (balance((root.right)) <= 0) {
					// height(root.right.right) >= height(root.right.left)
					// its (Right, Right) case
					root = leftRotate(root);
				}
				else {
					// its (Right, Left) case
					root.right = rightRotate(root.right);
					root = leftRotate(root);
				}
			}
			
			return root;
		}
	}
	
	public void countSmallerElements(int[] a) {
		int n = a.length;
		
		int[] smallerCount = new int[n];
		AVLTree tree = new AVLTree();
		
		for (int i = n-1; i >= 0; i--)
			tree.insert(a[i], smallerCount, i);
		
		System.out.println("Element\tCount");
		for (int i = 0; i < n; i++)
			System.out.println(a[i] + "\t" + smallerCount[i]);
	}
	
	/*
	 * We could use existing Self-balancing BST from Java library (i.e., TreeSet)
	 * to solve this problem. But it leads to a O(n^2) solution again.
	 * 
	 * What we have to do to solve this problem using TreeSet is:
	 * 1. Traverse the array from right to left, and for each element
	 * 2. Find headSet(a[i]) which returns set of elements smaller than a[i],
	 *    then size() of headSet(a[i]) will give us the count of smaller elements
	 *    to the right of a[i]
	 * 3. Add current element to TreeSet
	 * 
	 * This solutions takes O(n^2), because size() method called on headSet(a[i])
	 * takes O(n) in worst case.
	 */
	
	/*
	 * There is another way to solve this problem using modified MergeSort algorithm
	 * which also takes O(n*lgn). Using this approach, it is quite easier to solve this 
	 * problem, as we don't have to implement our own Self-balancing BST. We will 
	 * discuss modified MergeSort technique to solve this problem while solving 
	 * another similar problem called "Count Inversions".
	 * 
	 * A yet another approach is to solve it using Segment Tree or BIT, which also 
	 * takes O(n*lgn) time.
	 */
	
	public static void main(String[] args) {
		CountSmallerElementsOnRightSide o = new CountSmallerElementsOnRightSide();
		int[] a = {10, 6, 15, 20, 30, 5, 7};
		o.countSmallerElements(a);
		/*
		 * Element	Count
		 * 10		3
		 * 6		1
		 * 15		2
		 * 20		2
		 * 30		2
		 * 5		0
		 * 7		0
		 */
		
		a = new int[]{10, 6, 6, 6};
		o.countSmallerElements(a);
		/*
		 * Element	Count
		 * 10		3
		 * 6		0
		 * 6		0
		 * 6		0
		 */
	}
}
