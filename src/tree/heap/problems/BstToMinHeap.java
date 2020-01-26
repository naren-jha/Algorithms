package tree.heap.problems;

import java.util.ArrayList;

public class BstToMinHeap {

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
    public BstToMinHeap() {
        root = null;
    }
    
    // Method to do inorder traversal of the tree and 
    // to store the node values in an array in sorted order
    public void inorderArray(Node root, ArrayList<Integer> arr) {
        // Base case
        if (root == null)
            return;
        
        inorderArray(root.left, arr);
        arr.add(root.key);
        inorderArray(root.right, arr);
    }
    
    // Method to do preorder traversal of the tree
    // and copy data from inorder array to nodes
    public int preorderCopyData(Node root, ArrayList<Integer> arr, int i) {
        // Base case
        if (root == null)
            return i;
        
        root.key = arr.get(i++);
        i = preorderCopyData(root.left, arr, i);
        i = preorderCopyData(root.right, arr, i);
        return i;
    }
    
    // Method to convert BST to min-heap
    public void convertBstToMinHeap(Node root) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        inorderArray(root, arr);
        preorderCopyData(root, arr, 0);
    }
    
    // Print preorder traversal of tree
    // this is to print result after conversion 
    public void printPreorder(Node root) {
        // Base case
        if (root == null)
            return;
        
        System.out.print(root.key + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }
    
    public static void main(String[] args) {
        BstToMinHeap bt = new BstToMinHeap();
        bt.root = bt.new Node(4);
        bt.root.left = bt.new Node(2);
        bt.root.right = bt.new Node(6);
        bt.root.left.left = bt.new Node(1);
        bt.root.left.right = bt.new Node(3);
        bt.root.right.left = bt.new Node(5);
        bt.root.right.right = bt.new Node(7);
        
        bt.convertBstToMinHeap(bt.root);
        bt.printPreorder(bt.root);
    }

}
