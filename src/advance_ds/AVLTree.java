package advance_ds;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Implementation of AVL Tree data structure
 */
public class AVLTree {

    /* Template for Node in BST*/
    private class Node {
        int key;
        Node left, right;
        int height;
        
        public Node(int key) {
            this.key = key;
            left = right = null;
            height = 0;
        }
    }
    
    private Node root;
    
    // Constructor
    public AVLTree() {
        root = null;
    }
    
    // Get height of subtree rooted at given node
    private int height(Node node) {
        if (node == null)
            return -1; // empty tree height
        return node.height;
    }
    
    private void updateHeight(Node root) {
    	root.height = 1 + Math.max(height(root.left), height(root.right));
    }
    
    // A utility function to left rotate a subtree rooted with root
    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        root.right = root.right.left;
        newRoot.left = root;
        
        // update heights
        updateHeight(root);
        updateHeight(newRoot);
        
        return newRoot;
    }
    
    // A utility function to right rotate a subtree rooted with root
    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        root.left = root.left.right;
        newRoot.right = root;
        
        // update heights
        updateHeight(root);
        updateHeight(newRoot);
        
        return newRoot;
    }
    
    // Get balance for given node
    private int balance(Node node) {
        return height(node.left) - height(node.right);
    }
    
    // this method calls insert(root, key)
    public void insert(int key) {
        root = insert(root, key);
    }

    // Insert into AVL tree
    public Node insert(Node root, int key) {
        if (root == null)
            return new Node(key);
        
        if (key < root.key)
            root.left = insert(root.left, key);
        else if (key > root.key)
            root.right = insert(root.right, key);
        else // duplicate keys are not inserted
            return root;
        
        updateHeight(root);
        
        root = fixTree(root);
        
        return root;
    }

	private Node fixTree(Node root) {
        int balance = balance(root);
        
        if (balance > 1) {
            // this node is unbalanced, and its either (Left, Left) or (Left, Right) case
            
            if (balance((root.left)) >= 0) {
                // height(root.left.left) >= height(root.left.right)
                // its (Left, Left) case
                root = rotateRight(root);
            }
            else {
                // its (Left, Right) case
                root.left = rotateLeft(root.left);
                root = rotateRight(root);
            }
        }
        else if (balance < -1) {
            // this node is unbalanced, and its either (Right, Right) or (Right, Left) case
            
            if (balance((root.right)) <= 0) {
                // height(root.right.right) >= height(root.right.left)
                // its (Right, Right) case
                root = rotateLeft(root);
            }
            else {
                // its (Right, Left) case
                root.right = rotateRight(root.right);
                root = rotateLeft(root);
            }
        }
		return root;
	}
    
    // This method mainly calls recursive delete method
    public void delete(int key) {
        root = delete(root, key);
    }
    
    // A utility method to delete a node in BST
    public Node delete(Node root, int key) {
        if (root == null)
            return root;
        
        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);
        else { // Found key to be deleted
            // Case 1: No child
            if (root.left == null && root.right == null) {
                root = null;
            }
            // Case 2: One child
            else if (root.left == null) {
                root = root.right;
            }
            else if (root.right == null) {
                root = root.left;
            }
            // Case 3: Two child
            else {
                // Find inorder successor: node with smallest value in right subtree
                Node successor = root.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                
                root.key = successor.key;
                root.right = delete(root.right, successor.key);
            }
        }
        
        // If the tree had only one node then return  
        if (root == null)  
            return root;
        
        updateHeight(root);
        
        root = fixTree(root);
        
        return root;
    }
    
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.insert(25);
        avlTree.preorder(); // 30 20 10 25 40 50
        
        AVLTree avlTree2 = new AVLTree();
        avlTree2.insert(1);
        avlTree2.insert(2);
        avlTree2.insert(3);
        avlTree2.insert(6);
        avlTree2.insert(5);
        avlTree2.insert(-2);
        avlTree2.insert(-5);
        avlTree2.insert(-8);
        avlTree2.preorder(); // 2 -2 -5 -8 1 5 3 6 
        
        
        AVLTree tree = new AVLTree();  
        /* Constructing tree */
        tree.insert(9);
        tree.insert(5);
        tree.insert(10);
        tree.insert(0);
        tree.insert(6);
        tree.insert(11);
        tree.insert(-1);
        tree.insert(1);
        tree.insert(2);

        /* The constructed AVL Tree would be  
                 9  
                / \  
               1   10  
              / \   \  
             0   5   11  
            /   / \  
          -1   2   6  
        */
        tree.preorder(); // 9 1 0 -1 5 2 6 10 11

        tree.delete(10);

        /* The AVL Tree after deletion of 10  
                 1  
                / \  
               0   9  
              /   / \  
            -1   5   11  
                / \  
               2   6  
        */
        tree.preorder(); // 1 0 -1 9 5 2 6 11
    }
    
    // This method mainly calls recursive preorder method
    public void preorder() {
        preorderRec(root);
        System.out.println(); // new line after result
    }
    
    // A utility method to do preorder traversal of Binary Tree
    public void preorderRec(Node root) {
        // Base case
        if (root == null)
            return;
        
        System.out.print(root.key + " ");
        preorderRec(root.left);
        preorderRec(root.right);
    }
}
