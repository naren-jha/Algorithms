package basic.array.problems;

//https://www.geeksforgeeks.org/counting-inversions/
//https://www.geeksforgeeks.org/count-inversions-in-an-array-set-2-using-self-balancing-bst/
//https://www.geeksforgeeks.org/count-inversions-array-set-3-using-bit/

public class CountInversions_Approach2 {

	// This approach is similar to solution of CountSmallerElementsOnRightSide problem
	
	private int inversionCount = 0;
	
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
		public void insert(int key, int i) {
			root = insert(root, key, i);
		}

		// Insert into AVL tree
		public Node insert(Node root, int key, int i) {
			/* STEP 1:  PERFORM THE NORMAL BST INSERTION */
			if (root == null)
				return new Node(key);
			else if (key <= root.key) {
				root.left = insert(root.left, key, i);
				
				// Modification for this problem: 
				// keep adding the count of bigger elements to the left of array
				inversionCount += size(root.right) + 1;
			}
			else
				root.right = insert(root.right, key, i);
			
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
	
	public int countInversions(int[] a) {
		int n = a.length;
		
		AVLTree tree = new AVLTree();
		
		for (int i = 0; i < n; i++)
			tree.insert(a[i], i);
		
		return inversionCount;
	}
	
	public static void main(String[] args) {
		int[] a = {10, 6, 15, 20, 30, 5, 7};
		System.out.println(new CountInversions_Approach2().countInversions(a)); // 10
	}
}
