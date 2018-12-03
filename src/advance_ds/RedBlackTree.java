package advance_ds;

import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
	
	private enum Color {
		BLACK,
		RED
	}
	
	private class Node {
		int key;
		Color color;
		Node left;
		Node right;
		Node parent;
		
		public Node(int key) {
			this.key = key;
			this.color = Color.RED;
			this.left = this.right = this.parent = null;
		}
	}
	
	private Node root;
	
	public RedBlackTree() {
		root = null;
	}
	
	// Standard BST insert
	private Node BstInsert(int key) {
		Node node = new Node(key); // create a Red Node
		Node parent = null, current = root;
		while (current != null) {
			parent = current;
			if (key < current.key)
				current = current.left;
			else
				current = current.right;
		}
		
		node.parent = parent;
		if (parent == null)
			root = node;
		else if (key < parent.key)
			parent.left = node;
		else
			parent.right = node;
		
		return node;
	}
	
	private void leftRotate(Node root) {
		Node newRoot = root.right;
		root.right = newRoot.left;
		if (newRoot.left != null)
			newRoot.left.parent = root;
		
		newRoot.parent = root.parent;
		if (root.parent == null)
			this.root = newRoot;
		else if (root.parent.left == root)
			root.parent.left = newRoot;
		else
			root.parent.right = newRoot;
		
		newRoot.left = root;
		root.parent = newRoot;
	}
	
	private void rightRotate(Node root) {
		Node newRoot = root.left;
		root.left = newRoot.right;
		if (newRoot.right != null)
			newRoot.right.parent = root;
		
		newRoot.parent = root.parent;
		if (root.parent == null)
			this.root = newRoot;
		else if (root.parent.right == root)
			root.parent.right = newRoot;
		else
			root.parent.left = newRoot;
		
		newRoot.right = root;
		root.parent = newRoot;
	}
	
	private void fixViolations(Node root) {
		while (root != null && root.color == Color.RED 
				&& root.parent != null && root.parent.color == Color.RED) {
			
			// Case A: When Parent of root is left child of Grand-parent of root
			if (root.parent == root.parent.parent.left) {
				// it is either (left, left) or (left, right) case
				
				Node uncle = root.parent.parent.right;
				
				/* Case 1: Uncle is red: Only Recoloring is required */
				if (uncle!= null && uncle.color == Color.RED) {
					root.parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					root.parent.parent.color = Color.RED;
					root = root.parent.parent;
				}
				else {
					// Uncle is black: Its either case 2 or case 3
					
					/* Case 2: Root is right child of its parent: Left rotation required */
					if (root.parent.right == root) {
						leftRotate(root.parent);
						root = root.left;
						
					}
					
					// case 3: (left, left) orientation: One rotation fixes the tree
					rightRotate(root.parent.parent);
					root.parent.color = Color.BLACK;
					root.parent.right.color = Color.RED;
				}
			}
			// Case B: When Parent of root is right child of Grand-parent of root
			else {
				// it is either (right, right) or (right, left) case
				
				Node uncle = root.parent.parent.left;
				
				/* Case 1: Uncle is red: Only Recoloring is required */
				if (uncle!= null && uncle.color == Color.RED) {
					root.parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					root.parent.parent.color = Color.RED;
					root = root.parent.parent;
				}
				else {
					// Uncle is black: Its either case 2 or case 3
					
					/* Case 2: Root is left child of its parent: Right rotation required */
					if (root.parent.left == root) {
						rightRotate(root.parent);
						root = root.right;
						
					}
					
					// case 3: (right, right) orientation: One rotation fixes the tree
					rightRotate(root.parent.parent);
					root.parent.color = Color.BLACK;
					root.parent.left.color = Color.RED;
				}
			}
		}
	}
	
	public void insert(int key) {
		Node newNode = BstInsert(key);
		fixViolations(newNode);
		this.root.color = Color.BLACK;
	}
	
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		tree.insert(7);
	    tree.insert(6);
	    tree.insert(5);
	    tree.insert(4);
	    tree.insert(3);
	    tree.insert(2);
	    tree.insert(1);
	    
	    tree.inorder(); // 1 2 3 4 5 6 7 
	    tree.levelOrder(); // 6 4 7 2 5 1 3 

	}
	
	// A utility method to do level order traversal of Binary Tree
	public void levelOrder() {
		// If the tree is empty
		if (root == null)
			return;
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		while (!q.isEmpty()) {
			Node current = q.poll();
			System.out.print(current.key + " ");
			if (current.left != null)
				q.add(current.left);
			if (current.right != null)
				q.add(current.right);
		}
		System.out.println(); // go to next line after result
	}
	
	// This method mainly calls recursive inorder method
	public void inorder() {
		inorderRec(root);
		System.out.println(); // new line after result
	}
	
	// A utility method to do inorder traversal of Binary Tree
	public void inorderRec(Node root) {
		// Base case
		if (root == null)
			return;
		
		inorderRec(root.left);
		System.out.print(root.key + " ");
		inorderRec(root.right);
	}

}
