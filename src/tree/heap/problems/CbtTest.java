package tree.heap.problems;
import java.util.LinkedList;
import java.util.Queue;
 
class BinaryTree {
	/* Template for Node in BT*/
	class Node {
		int key;
		Node left, right;
		
		public Node(int key) {
			this.key = key;
			left = right = null;
		}
	}
	
	// Root of BT
	Node root;
	
	// Constructor
	public BinaryTree() {
		root = null;
	}
     
    // Iterative method to check if a binary tree is a
	// complete binary tree or not
    public boolean isCompleteBT(Node root) {
        // Base Case: An empty tree is complete Binary Tree
        if (root == null)
            return true;
         
        // Create an empty queue
        Queue<Node> queue =new LinkedList<Node>();
         
        // Create a flag variable which will be set true
        // when a non-full node is seen
        boolean flag = false;
         
        // Do level order traversal using queue.
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
             
            /* Check if left child is present*/
            if (node.left != null) {
                // If we have seen a non-full node, and we see a node
                // with non-empty left child, then the given tree is not
                // a complete Binary Tree
                if (flag == true)
                    return false;
                 
                // Enqueue Left Child
                queue.add(node.left);
            }
            // If this is a non-full node, set the flag as true
            else
                flag = true;
             
            /* Check if right child is present*/
            if (node.right != null) {
                // If we have seen a non full-node, and we see a node
                // with non-empty right child, then the given tree is not
                // a complete Binary Tree
                if (flag == true)
                    return false;
                 
                // Enqueue Right Child
                queue.add(node.right);
                 
            }
            // If this is a non-full node, set the flag as true
            else
                flag = true;
        }
        // If we reach here, then the tree is complete Bianry Tree
        return true;
    }
    
    // Method to count number of nodes in a binary tree
    public int countNodes(Node root) {
        if (root == null)
            return 0;
        return (1 + countNodes(root.left) + countNodes(root.right));
    }
  
    // Recursive method to check if a binary tree is a
 	// complete binary tree or not
    public boolean isCompleteBT(Node root, int index, int numberOfNodes) {
        // An empty tree is complete
        if (root == null)        
           return true;
  
        // If index assigned to current node is more than
        // number of nodes in tree, then tree is not complete
        if (index >= numberOfNodes)
           return false;
  
        // Recur for left and right subtrees
        return (isCompleteBT(root.left, 2 * index + 1, numberOfNodes)
            && isCompleteBT(root.right, 2 * index + 2, numberOfNodes));
    }
    
    // Method to check heap property in the tree
    public boolean isMaxHeapUtil(Node root) {
        //  Base case : single node satisfies property 
    	if (root.left == null && root.right == null)
    		return true;
    	
    	// There can be one such node in second last level
    	if (root.right == null) {
    	    //  check heap property at Node
            //  No recursive call , because no need to check last level
    		return root.key >= root.left.key;
    	}
    	else {
    	    //  Check heap property at Node and recursively
            //  check heap property for left and right subtrees
    		if (root.key >= root.left.key && root.key >= root.right.key)
    			return isMaxHeapUtil(root.left) && isMaxHeapUtil(root.right);
    		else
    			return false;
    	}
    }
    
    // Method to check if a binary tree is a max-heap or not
    public boolean isMaxHeap(Node root) {
    	if (root == null)
    		return true;
    	int numberOfNodes = countNodes(root);
    	return isCompleteBT(root, 0, numberOfNodes)
    			&& isMaxHeapUtil(root);
    }
     
}

public class CbtTest {
    public static void main(String[] args) {
        /* Let us construct the following Binary Tree which
          is not a complete Binary Tree
                1
              /   \
             2     3
            / \     \
           4   5     6
        */
      
    	BinaryTree bt = new BinaryTree();
        bt.root = bt.new Node(1);
        bt.root.left = bt.new Node(2);
        bt.root.right = bt.new Node(3);
        bt.root.left.left = bt.new Node(4);
        bt.root.left.right = bt.new Node(5);
        bt.root.right.right = bt.new Node(6);
         
        if (bt.isCompleteBT(bt.root))
            System.out.println("Complete Binary Tree");
        else
            System.out.println("Not A Complete Binary Tree");
        
        if (bt.isCompleteBT(bt.root, 0, bt.countNodes(bt.root)))
            System.out.println("Complete Binary Tree");
        else
            System.out.println("Not A Complete Binary Tree");
    }
}

class MaxHeapTest {
	public static void main(String[] args) {
		BinaryTree bt = new BinaryTree();
        
        bt.root = bt.new Node(10);
        bt.root.left = bt.new Node(9);
        bt.root.right = bt.new Node(8);
        bt.root.left.left = bt.new Node(7);
        bt.root.left.right = bt.new Node(6);
        bt.root.right.left = bt.new Node(5);
        bt.root.right.right = bt.new Node(4);
        bt.root.left.left.left = bt.new Node(3);
        bt.root.left.left.right = bt.new Node(2);
        bt.root.left.right.left = bt.new Node(1);
        
        if (bt.isMaxHeap(bt.root))
            System.out.println("Given binary tree is a Max-Heap");
        else
            System.out.println("Given binary tree is not a Max-Heap");
	}
}