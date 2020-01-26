package tree.bst;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Binary Search Tree (BST) data structure and most common algorithms on it
 */
class BinarySearchTree {
    
    /* Template for Node in BST*/
    class Node {
        int key;
        Node left, right;
        
        public Node(int key) {
            this.key = key;
            left = right = null;
        }
    }
    
    /* Template used to find largest BST in a binary tree */
    class Info {
        boolean isBST;
        int size;
        int min;
        int max;
        
        Info() {
            isBST = true;
            size = 0;
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
        }
    }
    
    /* Template used in optimized diameter method */
    class NoOfNodes {
        int n;
    }
    
    // Root of BST
    Node root;
    
    // Constructor
    public BinarySearchTree() {
        root = null;
    }
    
    // Iterative method to insert a new key in BST
    public void insert(int key) {
        
        Node node = new Node(key);
        // When tree is empty
        if (root == null) {
            root = node;
            return;
        }
        
        Node parent = null, current = root;
        while (current != null) {
            parent = current;
            if (key < current.key)
                current = current.left;
            else if (key > current.key)
                current = current.right;
        }
        
        if (key < parent.key)
            parent.left = node;
        else if (key > parent.key)
            parent.right = node;
        
    }
    
    // Iterative method to insert a new key in BST
    // without using two pointers
    public void insert2(int key) {
        
        Node node = new Node(key);
        // When tree is empty
        if (root == null) {
            root = node;
            return;
        }
        
        Node current = root;
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    current.left = node;
                    break;
                }
                current = current.left;
            }
            else if (key > current.key) {
                if (current.right == null) {
                    current.right = node;
                    break;
                }
                current = current.right;
            }
        }
    }
    
    // This method mainly calls recursive insert method
    public void insert3(int key) {
        root = insertRec(root, key);
    }
    
    // Recursive method to insert a new key in BST
    private Node insertRec(Node root, int key) {
        
        // Base Case: Tree is empty or we reached a leaf node.
        // return a new node
        if (root == null) {
            root = new Node(key);
            return root;
        }
        
        // Otherwise, recur down the tree
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        }
        else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }
        // NOTE: duplicate values will not be inserted
        
        return root;
    }
    
    // Iterative method to search a given key in BST
    public Node search(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key)
                return current;
            else if (key < current.key)
                current = current.left;
            else if (key > current.key)
                current = current.right;
        }
        
        // when tree is empty, or when key is not found
        return null;
    }
    
    // This method mainly calls recursive search method
    public Node search2(int key) {
        return searchRec(root, key);
    }
    
    // A utility method to search a given key in BST
    private Node searchRec(Node root, int key) {
        // Base Case: root is null or key is present at root
        if (root == null || key == root.key)
            return root;
        
        if (key < root.key)
            return searchRec(root.left, key);
        else
            return searchRec(root.right, key);
    }
    
    // This method mainly calls recursive delete method
    public void delete(int key) {
        deleteRec(root, key);
    }
    
    // A utility method to delete a node in BST
    private Node deleteRec(Node root, int key) {
        if (root == null)
            return root;
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else { // Found key to be deleted
            // Case 1: No child
            if (root.left == null && root.right == null) {
                // delete root; // if C++
                root = null;
            }
            // Case 2: One child
            else if (root.left == null) {
                // Node temp = root; // if C++
                root = root.right;
                // delete temp; // if C++
            }
            else if (root.right == null) {
                // Node temp = root; // if C++
                root = root.left;
                // delete temp; // if C++
            }
            // Case 3: Two child
            else {
                // Find inorder successor: node with smallest value in right subtree
                Node successor = root.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                
                root.key = successor.key;
                root.right = deleteRec(root.right, successor.key);
            }
        }
        return root;
    }
    
    
    /* Tree Traversals - start - */
    
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
    
    // Iterative approach to do inorder traversal of Binary Tree
    void inorderItr() {
        // If the tree is empty
        if (root == null) 
            return;
        
        Stack<Node> s = new Stack<Node>();
        Node curr = root;
        while (!s.isEmpty() || curr != null) {
            if (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            else {
                curr = s.pop();
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
        }
        System.out.println(); // new line after result
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
    
    // Iterative approach to do preorder traversal of Binary Tree
    void preorderItr() {
        // If the tree is empty
        if (root == null) 
            return;
        
        Stack<Node> s = new Stack<Node>();
        s.push(root);
        Node curr;
        while (!s.isEmpty()) {
            curr = s.pop();
            System.out.print(curr.key + " ");
            if (curr.right != null)
                s.push(curr.right);
            if (curr.left != null)
                s.push(curr.left);
        }
        System.out.println(); // new line after result
    }
    
    // This method mainly calls recursive postorder method
    public void postorder() {
        postorderRec(root);
        System.out.println(); // new line after result
    }
    
    // A utility method to do postorder traversal of Binary Tree
    public void postorderRec(Node root) {
        // Base case
        if (root == null)
            return;
        
        postorderRec(root.left);
        postorderRec(root.right);
        System.out.print(root.key + " ");
    }
    
    // Iterative approach to do postorder traversal of Binary Tree
    void postorderItr() {
        // If the tree is empty
        if (root == null) 
            return;
        
        Stack<Node> s1 = new Stack<Node>();
        Stack<Node> s2 = new Stack<Node>();
        s1.push(root);
        Node curr;
        while (!s1.isEmpty()) {
            curr = s1.pop();
            s2.push(curr);
            if (curr.left != null)
                s1.push(curr.left);
            if (curr.right != null)
                s1.push(curr.right);
        }
        
        while (!s2.isEmpty()) {
            System.out.print(s2.pop().key + " ");
        }
        System.out.println(); // new line after result
    }
    
    // Iterative approach to do postorder traversal of Binary Tree
    // Using one stack
    void postorderItrOneStack() {
        Stack<Node> s = new Stack<Node>();
        Node curr = root;
        while (!s.isEmpty() || curr != null) {
            if (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            else {
                // no left child
                Node temp = s.peek().right;
                if (temp == null) {
                    // no left child, no right child
                    
                    // both left and right child of node at top of stack
                    // is null, means we can print this node and...
                    temp = s.pop();
                    System.out.print(temp.key + " ");
                    
                    // ...go in upward direction and keep printing nodes
                    // till current node is right child of its parent
                    while (!s.isEmpty() && temp == s.peek().right) {
                        temp = s.pop();
                        System.out.print(temp.key + " ");
                    }
                    // we can use a do..while loop for code inside this 
                    // IF block
                }
                else {
                    // no left child, but there is right child
                    curr = temp;
                }
            }
        }
        System.out.println(); // new line after result
    }
    
    /* Morris traversal algorithms - start - */
    
    // Morris inorder traversal
    public void morrisInorder() {
        Node curr = root;
        while (curr != null) {
            // left is null then print the node and go to right
            if (curr.left == null) {
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
            else {
                // if left is not null, we need to make our ways to 
                // come back to current node before going to visit left subtree
                
                // find the predecessor - start -
                // go one left
                Node predecessor = curr.left;
                // and then keep going right till either 
                // right node becomes null or it points back to current node.
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                // find the predecessor - end -
                
                // if predecessor's right is null then go left after establishing 
                // link from predecessor to current.
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    // link established. now go to visit left subtree
                    curr = curr.left;
                }
                else {
                    // left is already visited. Go right after visiting current.
                    predecessor.right = null;
                    System.out.print(curr.key + " ");
                    curr = curr.right;
                }
            }
        }
        System.out.println(); // new line after result
    }
    
    // Morris inorder traversal - A more simpler approach -
    public void morrisInorder2() {
        Node curr = root;
        while (curr != null) {
            // left is null then print the node and go to right
            if (curr.left == null) {
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
            else {
                // if left is not null, we need to make our ways to 
                // come back to current node before going to visit left subtree
                
                // find the predecessor - start -
                // go one left
                Node predecessor = curr.left;
                // and then keep going right till either 
                // right node becomes null or it points back to current node.
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                // find the predecessor - end -
                
                predecessor.right = curr;
                Node temp = curr.left;
                curr.left = null;
                curr = temp;
            }
        }
        System.out.println(); // new line after result
    }
    
    // Morris preorder traversal
    public void morrisPreorder() {
        Node curr = root;
        while (curr != null) {
            // left is null then print the node and go to right
            if (curr.left == null) {
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
            else {
                // if left is not null, we need to make our ways to 
                // come back to current node before going to visit left subtree
                
                // find the predecessor - start -
                // go one left
                Node predecessor = curr.left;
                // and then keep going right till either 
                // right node becomes null or it points back to current node.
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                // find the predecessor - end -
                
                // if predecessor's right is null then go left after establishing 
                // link from predecessor to current.
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    // link established. now visit current node and 
                    // then go visiting left subtree
                    System.out.print(curr.key + " ");
                    curr = curr.left;
                }
                else {
                    // left is already visited. Remove link and go right.
                    predecessor.right = null;
                    curr = curr.right;
                }
            }
        }
        System.out.println(); // new line after result
    }
    
    // Morris postorder traversal
    public void morrisPostorder() {
        
        // create a dummy node and set its left child to root of the tree
        Node dummy = new Node(0);
        dummy.left = root;
        
        // start from dummy node instead of root node.
        Node curr = dummy;
        while (curr != null) {
            // left is null then go to right
            if (curr.left == null) {
                curr = curr.right;
            }
            else {
                // if left is not null, we need to make our ways to 
                // come back to current node before going to visit left subtree
                
                // find the predecessor - start -
                // go one left
                Node predecessor = curr.left;
                // and then keep going right till either 
                // right node becomes null or it points back to current node.
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                // find the predecessor - end -
                
                // if predecessor's right is null then go left after establishing 
                // link from predecessor to current.
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    // link established. go left.
                    curr = curr.left;
                }
                else {
                    // output all the nodes from the left child of the current node to 
                    // the predecessor node in reverse order.
                    printReverse(curr.left, predecessor);
                    predecessor.right = null;
                    curr = curr.right;
                }
            }
        }
        System.out.println(); // new line after result
    }
    
    // Method used in Morris postorder traversal
    /* prints all nodes from 'from' to 'to' in reverse order */
    public void printReverse(Node from, Node to) {
        reverse(from, to); // establish link in reverse order
        
        // print nodes in reverse order
        Node p = to;
        while (true) {
            System.out.print(p.key + " ");
            if (p == from)
                break;
            p = p.right;
        }
        
        reverse(to, from); // undo links back to original order
    }
    
    // Method used in Morris postorder traversal
    /* reverses links from 'from' to 'to' */
    public void reverse(Node from, Node to) {
        if (from == to)
            return;
        
        Node x = from, y = from.right, z = null;
        while (x != to) {
            z = y.right;
            y.right = x;
            x = y; y = z;
        }
    }
    
    /* Morris traversal algorithms - end - */
    /* Tree Traversals - end - */
    
    // A utility method to do reverse level order traversal for a binary tree.
    public void levelOrderInReverse() {
        if (root == null)
            return;
        
        Queue<Node> q = new LinkedList<Node>();
        Stack<Node> s = new Stack<Node>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node tmp = q.poll();
            if (tmp.right != null)
                q.offer(tmp.right);
            if (tmp.left != null)
                q.offer(tmp.left);
            s.push(tmp);
        }
        while (!s.isEmpty()) {
            System.out.print(s.pop().key + " ");
        }
        System.out.println(); // new line after result
    }
    
    // A utility method to do spiral level order traversal for a binary tree
    // Using two Stack
    public void spiralUsingTwoStack() {
        if (root == null)
            return;
        
        Stack<Node> s1 = new Stack<Node>();
        Stack<Node> s2 = new Stack<Node>();
        s1.push(root);
        while (!s1.isEmpty() || !s2.isEmpty()) {
            while (!s1.isEmpty()) {
                Node tmp = s1.pop();
                System.out.print(tmp.key + " ");
                if (tmp.left != null)
                    s2.push(tmp.left);
                if (tmp.right != null)
                    s2.push(tmp.right);
            }
            while (!s2.isEmpty()) {
                Node tmp = s2.pop();
                System.out.print(tmp.key + " ");
                if (tmp.right != null)
                    s1.push(tmp.right);
                if (tmp.left != null)
                    s1.push(tmp.left);
            }
        }
        System.out.println(); // new line after result
    }
    
    // A utility method to do spiral level order traversal for a binary tree
    // Using one Deque and delimiter
    public void spiralUsingOneDequeAndDelimiter() {
        if (root == null)
            return;
        
        Deque<Node> dq = new LinkedList<Node>();
        dq.offer(null); // add delimiter null in middle
        dq.offerFirst(root); // add from top
        while (dq.size() > 1) { // when only delimiter is left
            while (dq.peekFirst() != null) {
                Node tmp = dq.pollFirst(); // remove from top
                System.out.print(tmp.key + " ");
                if (tmp.left != null)
                    dq.offerLast(tmp.left); // add from bottom
                if (tmp.right != null)
                    dq.offerLast(tmp.right); // add from bottom
            }
            while (dq.peekLast() != null) {
                Node tmp = dq.pollLast();  // remove from bottom
                System.out.print(tmp.key + " ");
                if (tmp.right != null)
                    dq.offerFirst(tmp.right); // add from top
                if (tmp.left != null)
                    dq.offerFirst(tmp.left); // add from top
            }
        }
        System.out.println(); // new line after result
    }
    
    // A utility method to do spiral level order traversal for a binary tree
    // Using one Deque and counter
    public void spiralUsingOneDequeAndCounter() {
        if (root == null)
            return;
        
        Deque<Node> dq = new LinkedList<Node>();
        dq.offerFirst(root); // add from top
        int levelCount = 1;
        boolean flip = true;
        while (!dq.isEmpty()) {
            int currentCount = 0;
            while (levelCount != 0) {
                if (flip) {
                    Node tmp = dq.pollFirst(); // remove from top
                    System.out.print(tmp.key + " ");
                    if (tmp.left != null) {
                        dq.offerLast(tmp.left); // add from bottom
                        currentCount++;
                    }
                    if (tmp.right != null) {
                        dq.offerLast(tmp.right); // add from bottom
                        currentCount++;
                    }
                }
                else {
                    Node tmp = dq.pollLast();  // remove from bottom
                    System.out.print(tmp.key + " ");
                    if (tmp.right != null) {
                        dq.offerFirst(tmp.right); // add from top
                        currentCount++;
                    }
                    if (tmp.left != null) {
                        dq.offerFirst(tmp.left); // add from top
                        currentCount++;
                    }
                }
                levelCount--;
            }
            flip = !flip;
            levelCount = currentCount;
        }
        System.out.println(); // new line after result
    }
    
    // A utility method to do level by level printing of Binary Tree
    // Using two Queues
    public void levelByLevelTwoQueue() {
        // If the tree is empty
        if (root == null)
            return;
        
        Queue<Node> q1 = new LinkedList<Node>();
        Queue<Node> q2 = new LinkedList<Node>();
        q1.add(root);
        while (!q1.isEmpty() || !q2.isEmpty()) {
            while (!q1.isEmpty()) {
                Node tmp = q1.poll();
                System.out.print(tmp.key + " ");
                if (tmp.left != null)
                    q2.add(tmp.left);
                if (tmp.right != null)
                    q2.add(tmp.right);
            }
            System.out.println();
            while (!q2.isEmpty()) {
                Node tmp = q2.poll();
                System.out.print(tmp.key + " ");
                if (tmp.left != null)
                    q1.add(tmp.left);
                if (tmp.right != null)
                    q1.add(tmp.right);
            }
            System.out.println();
        }
    }
    
    // A utility method to do level by level printing of Binary Tree
    // Using one Queue and delimiter
    public void levelByLevelOneQueueAndDelimiter() {
        // If the tree is empty
        if (root == null)
            return;
        
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        q.add(null);
        while (!q.isEmpty()) {
            Node tmp = q.poll();
            if (tmp != null) {
                System.out.print(tmp.key + " ");
                if (tmp.left != null)
                    q.add(tmp.left);
                if (tmp.right != null)
                    q.add(tmp.right);
            }
            else {
                if (!q.isEmpty()) {
                    System.out.println();
                    q.add(null);
                }
            }
        }
        System.out.println(); // new line after result
    }
    
    // A utility method to do level by level printing of Binary Tree
    // Using one Queue and counter
    public void levelByLevelOneQueueAndCounter() {
        // If the tree is empty
        if (root == null)
            return;
        
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        int levelCount = 1, currentCount = 0;
        while (!q.isEmpty()) {
            while (levelCount != 0) {
                Node tmp = q.poll();
                System.out.print(tmp.key + " ");
                if (tmp.left != null) {
                    q.add(tmp.left);
                    currentCount++;
                }
                if (tmp.right != null) {
                    q.add(tmp.right);
                    currentCount++;
                }
                levelCount--;
            }
            System.out.println();
            levelCount = currentCount;
            currentCount = 0;
        }
    }
    
    // Iterative method to find height of Binary Tree
    // Using level by level printing technique
    public int heightItr() {
        // If the tree is empty
        if (root == null)
            return -1;
        
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        int levelCount = 1, currentCount = 0, height = -1;
        while (!q.isEmpty()) {
            while (levelCount != 0) {
                Node tmp = q.poll();
                if (tmp.left != null) {
                    q.add(tmp.left);
                    currentCount++;
                }
                if (tmp.right != null) {
                    q.add(tmp.right);
                    currentCount++;
                }
                levelCount--;
            }
            levelCount = currentCount;
            currentCount = 0;
            height++;
        }
        
        return height;
    }
    
    // A utility method to find inorder successor for a given node in BST
    public Node inorderSuccessor(int  key) {
         
        // First find the node
        Node curr = search(key);
        
        // when tree is empty, or when key is not found
        if (curr == null) return null;

        // Case 1: When node has right subtree
        if (curr.right != null) {
            // Minimum in right subtree will be inorder successor
            curr = curr.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
 
        // Case 2: No right subtree
        // Nearest ancestor for which the given node is in its 
        // left subtree will be inorder successor
        Node ancestor = root;
        Node successor = null;
        while (ancestor != curr) {
            if (key < ancestor.key) {
                successor = ancestor; 
                ancestor = ancestor.left;
            }
            else {
                ancestor = ancestor.right;
            }
        }
        return successor;

    }
    
    // A utility method to find inorder successor for a given node in BST
    public Node inorderSuccessor2(int key) {
        Node succ = null;
        Node curr = root;
        /* 
         * Find the key node and store the last bigger node 
         * than key node in a temporary variable.
         */
        while (curr != null && key != curr.key) {
            if (key < curr.key) {
                succ = curr;
                curr = curr.left;
            }
            else if (key > curr.key) {
                curr = curr.right;
            }
        }
        
        // when tree is empty, or when key is not found
        if (curr == null) return null;
        
        // Case 1: When node has right subtree
        if (curr.right != null) {
            // Minimum in right subtree will be inorder successor
            curr = curr.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
        
        // Case 2: No right subtree
        // Nearest ancestor for which the given node is in its 
        // left subtree will be inorder successor, and we have 
        // already found that node when searching for key node 
        // in the tree.
        return succ;
    }
    
    // A utility method to find inorder predecessor for a given node in BST
    public Node inorderPredecessor(int  key) {
         
        // First find the node
        Node curr = search(key);
        
        // when tree is empty, or when key is not found
        if (curr == null) return null;

        // Case 1: When node has left subtree
        if (curr.left != null) {
            // Maximum in left subtree will be inorder predecessor
            curr = curr.left;
            while (curr.right != null) {
                curr = curr.right;
            }
            return curr;
        }
 
        // Case 2: No left subtree
        // Nearest ancestor for which the given node is in its 
        // right subtree will be inorder predecessor
        Node ancestor = root;
        Node predecessor = null;
        while (ancestor != curr) {
            if (key < ancestor.key) {
                ancestor = ancestor.left;
            }
            else {
                predecessor = ancestor;
                ancestor = ancestor.right;
            }
        }
        return predecessor;

    }
    
    // A utility method to find inorder predecessor for a given node in BST
    public Node inorderPredecessor2(int key) {
        Node pred = null;
        Node curr = root;
        /* 
         * Find the key node and store the last smaller node 
         * than key node in a temporary variable.
         */
        while (curr != null && key != curr.key) {
            if (key < curr.key) {
                curr = curr.left;
            }
            else if (key > curr.key) {
                pred = curr;
                curr = curr.right;
            }
        }
        
        // when tree is empty, or when key is not found
        if (curr == null) return null;
        
        // Case 1: When node has left subtree
        if (curr.left != null) {
            // Maximum in left subtree will be inorder predecessor
            curr = curr.left;
            while (curr.right != null) {
                curr = curr.right;
            }
            return curr;
        }
        
        // Case 2: No left subtree
        // Nearest ancestor for which the given node is in its 
        // right subtree will be inorder predecessor, and we have 
        // already found that node when searching for key node 
        // in the tree.
        return pred;
    }
    
    // A utility method to check if a given binary tree is a binary search tree or not
    // This method simply calls recursive utility method
    public boolean isBST() {
        return isBstUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    // Recursive method to find if a binary tree is binary search tree
    public boolean isBstUtil(Node root, int lowerBound, int upperBound) {
        // Base Case
        if (root == null) return true;
        
        if (root.key < lowerBound || root.key > upperBound)
            return false;        
        return isBstUtil(root.left, lowerBound, root.key)
                && isBstUtil(root.right, root.key, upperBound);
    }
    
    // A utility method to check if a given binary tree is a binary search tree or not
    // using inorder traversal technique
    public boolean isBST2() {
        // If the tree is empty
        if (root == null) 
            return true;
        
        Stack<Node> s = new Stack<Node>();
        Node curr = root, prev = null;
        while (!s.isEmpty() || curr != null) {
            if (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            else {
                curr = s.pop();
                if (prev != null && curr.key < prev.key)
                    return false;
                prev = curr;
                curr = curr.right;
            }
        }
        return true;
    }
    
    // Iterative method to find min in BST
    public int min() {
        // If the tree is empty
        if (root == null) {
            System.out.println("Tree is empty");
            return -1;
        }
        
        // Otherwise
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        
        return current.key;
    }
    
    // This method mainly calls recursive min method
    public int min2() {
        return minRec(root);
    }
    
    // Recursive method to find min in BST
    private int minRec(Node root) {
        // If the tree is empty
        if (root == null) {
            System.out.println("Tree is empty");
            return -1; // or return Integer.MIN_VALUE
        }
        
        // Base case: when we have reached the left most node
        if (root.left == null)
            return root.key;
        
        return minRec(root.left);
    }
    
    // Iterative method to find max in BST
    public int max() {
        // If the tree is empty
        if (root == null) {
            System.out.println("Tree is empty");
            return -1; // or return Integer.MAX_VALUE
        }
        
        // Otherwise
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        
        return current.key;
    }
    
    // This method mainly calls recursive max method
    public int max2() {
        return maxRec(root);
    }
    
    // Recursive method to find max in BST
    private int maxRec(Node root) {
        // If the tree is empty
        if (root == null) {
            System.out.println("Tree is empty");
            return -1; // or return Integer.MAX_VALUE
        }
        
        // Base case: when we have reached the right most node
        if (root.right == null)
            return root.key;
        
        return maxRec(root.right);
    }
    
    // This method mainly calls recursive height method
    public int height() {
        return heightRec(root);
    }
    
    // Recursive method to find height of Binary Tree
    private int heightRec(Node root) {
        // Base case
        if (root == null)
            return -1;
        
        int lHeight = heightRec(root.left);
        int rHeight = heightRec(root.right);
        return 1 + (lHeight > rHeight ? lHeight : rHeight);
    }
    
    // This method mainly calls recursive isSameBinaryTree method
    public boolean isSameBinaryTree(BinarySearchTree bst) {
        return isSameBinaryTreeRec(this.root, bst.root);
    }
    
    // Recursive method to check if two binary trees are same or not
    private boolean isSameBinaryTreeRec(Node n1, Node n2) {
        if (n1 == null && n2 == null)
            return true;
        if (n1 == null || n2 == null)
            return false;
        
        return n1.key == n2.key
                && isSameBinaryTreeRec(n1.left, n2.left)
                && isSameBinaryTreeRec(n1.right, n2.right);
    }
    
    // Iterative method to check if two binary trees are same or not
    public boolean isSameBinaryTreeItr(BinarySearchTree bst) {
        Node n1 = this.root;
        Node n2 = bst.root;
        
        if (n1 == null && n2 == null)
            return true;
        if (n1 == null || n2 == null)
            return false;
        
        Stack<Node> stackN1 = new Stack<>();
        Stack<Node> stackN2 = new Stack<>();
        stackN1.push(n1);
        stackN2.push(n2);
        while (!stackN1.empty() && !stackN2.empty()) {
            Node tmpN1 = stackN1.pop();
            Node tmpN2 = stackN2.pop();
            if (tmpN1.key != tmpN2.key)
                return false;
            if (tmpN1.left != null && tmpN2.left != null) {
                stackN1.push(tmpN1.left);
                stackN2.push(tmpN2.left);
            } else if (tmpN1.left != null || tmpN2.left != null) {
                return false;
            }
            if (tmpN1.right != null && tmpN2.right != null) {
                stackN1.push(tmpN1.right);
                stackN2.push(tmpN2.right);
            } else if (tmpN1.right != null || tmpN2.right != null) {
                return false;
            }
        }
        return true;
    }
    
    // This method mainly calls recursive size method
    public int size() {
        return sizeRec(root);
    }
    
    // Recursive method to find size of a binary tree
    private int sizeRec(Node root) {
        // Base case
        if (root == null)
            return 0;
        
        int lSize = sizeRec(root.left);
        int rSize = sizeRec(root.right);
        return lSize + rSize + 1;
    }
    
    // Iterative method to find size of a binary tree
    public int sizeItr() {
        // If the tree is empty
        if (root == null)
            return 0;
        
        int size = 0;
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while (!q.isEmpty()) {
            Node current = q.poll();
            size++;
            if (current.left != null)
                q.add(current.left);
            if (current.right != null)
                q.add(current.right);
        }
        return size;
    }
    
    // This method mainly calls recursive diameter method
    public int diameter() {
        return diameterRec(root);
    }
    
    // A utility method to find diameter of a binary tree 
    private int diameterRec(Node root) {
        // Base case: when tree is empty
        if (root == null)
            return 0;
        
        // get no. of Nodes 
        // through longest path of left and right subtrees.
        // No of nodes will be one more than height,
        // as we have calculated height as no of edges
        // through longest path.
        int lNode = heightRec(root.left) + 1;
        int rNode = heightRec(root.right) + 1;
        
        // get the diameter of left and right subtrees
        int lDiameter = diameterRec(root.left);
        int rDiameter = diameterRec(root.right);
        
        /* Return max of following three
         * 1. Diameter of left subtree
         * 2. Diameter of right subtree
         * 3. No. of Nodes through height of left subtree + 
         *    No. of Nodes through height of right subtree + 1 
         */
        return Math.max(lNode + rNode + 1,
                Math.max(lDiameter, rDiameter));
    }
    
    // This method mainly calls recursive diameterOpt method
    public int diameterOpt() {
        return diameterOpt(root, new NoOfNodes());
    }
    
    // Optimized diameter method. 
    private int diameterOpt(Node root, NoOfNodes nodes) {
        if (root == null) {
            nodes.n = 0;
            return 0;
        }

        NoOfNodes lNode = new NoOfNodes();
        NoOfNodes rNode = new NoOfNodes();

        int lDiameter = diameterOpt(root.left, lNode);
        int rDiameter = diameterOpt(root.right, rNode);

        nodes.n = Math.max(lNode.n, rNode.n) + 1;
        return Math.max(Math.max(lDiameter, rDiameter), lNode.n + rNode.n + 1);
    }
    
    // This method mainly calls recursive rootToLeafSum method
    public boolean rootToLeafSum(int sum) {
        return rootToLeafSum(root, sum);
    }
    
    // Utility method to print root-to-leaf path for given sum in a binary tree
    private boolean rootToLeafSum(Node root, int sum) {
        // Base case
        if (root == null)
            return false;
        
        // Base case
        if (root.left == null && root.right == null) {
            if (root.key == sum) {
                System.out.print(root.key + "<-");
                return true;
            }
            else {
                return false;
            }
        }
        
        // recurse left and right subtrees with reduced sum
        if (rootToLeafSum(root.left, sum - root.key)) {
            System.out.print(root.key + "<-");
            return true;
        }
        if (rootToLeafSum(root.right, sum - root.key)) {
            System.out.print(root.key + "<-");
            return true;
        }
        return false;
    }
    
    // This method mainly calls recursive rootToLeafMaxSum method
    public int rootToLeafMaxSum() {
        return rootToLeafMaxSum(root);
    }
    
    // Utility method to calculate maximum root-to-leaf sum in a binary tree
    private int rootToLeafMaxSum(Node root) {
        // Base case
        if (root == null)
            return 0;
        
        // calculate maximum node-to-leaf sum for left child
        int leftSum = rootToLeafMaxSum(root.left);
        
        // calculate maximum node-to-leaf sum for right child
        int rightSum = rootToLeafMaxSum(root.right);
        
        return (leftSum > rightSum ? leftSum : rightSum) + root.key;
    }
    
    // This method mainly calls recursive rootToLeafMinSum method
    public int rootToLeafMinSum() {
        return rootToLeafMinSum(root);
    }
    
    // Utility method to calculate minimum root-to-leaf sum in a binary tree
    private int rootToLeafMinSum(Node root) {
        // Base case
        if (root == null)
            return 0;
        
        // calculate minimum node-to-leaf sum for left child
        int leftSum = rootToLeafMinSum(root.left);
        
        // calculate minimum node-to-leaf sum for right child
        int rightSum = rootToLeafMinSum(root.right);
        
        return (leftSum < rightSum ? leftSum : rightSum) + root.key;
    }
    
    // This method mainly calls recursive lca method
    public Node lca(int n1, int n2) {
        return lca(root, n1, n2);
    }
    
    // Method to find lowest common ancestor of nodes n1 and n2 in a
    // binary search tree. This method assumes that both n1 and n2 are 
    // present in the BST
    private Node lca(Node root, int n1, int n2) {
        if (root == null)
            return null;
  
        // If both n1 and n2 are smaller than root, then LCA lies in left
        if (root.key > n1 && root.key > n2)
            return lca(root.left, n1, n2);
  
        // If both n1 and n2 are bigger than root, then LCA lies in right
        if (root.key < n1 && root.key < n2) 
            return lca(root.right, n1, n2);
  
        return root;
    }
    
    public Node lcaItr(int n1, int n2) {
        Node tmp = root;
        while (tmp != null) {
            // If both n1 and n2 are smaller than root, then LCA lies in left
            if (tmp.key > n1 && tmp.key > n2)
                tmp = tmp.left;
            
            // If both n1 and n2 are bigger than root, then LCA lies in right
            if (tmp.key < n1 && tmp.key < n2)
                tmp = tmp.right;
            
            else
                break;
        }
        return tmp;
    }
    
    // This method mainly calls recursive lcaBinaryTree method
    public Node lcaBinaryTree(int n1, int n2) {
        return lcaBinaryTree(root, n1, n2);
    }
    
    // Method to find lowest common ancestor of nodes n1 and n2 in a
    // binary tree. This method assumes that both n1 and n2 are 
    // present in the binary tree
    private Node lcaBinaryTree(Node root, int n1, int n2) {
        if (root == null)
            return null;
  
        if (root.key == n1 || root.key == n2)
            return root;
        
        Node left = lcaBinaryTree(root.left, n1, n2);
        Node right = lcaBinaryTree(root.right, n1, n2);
        if (left == null && right == null)
            return null;
        else if (left != null && right != null)
            return root;
        else
            return left != null ? left : right;
    }
    
    // This method mainly calls largestBSTUtil method
    public int largestBST() {
        Info i = largestBSTUtil(root);
        return i.size;
    }
    
    // Method to find largest BST in a binary tree
    private Info largestBSTUtil(Node root) {
        // If root is null return min as Integer.MAX and max as Integer.MIN
        if (root == null)
            return new Info();
        
        // Postorder traversal of tree. First visit left and right, then
        // use information from left and right subtrees to calculate largest BST.
        Info left = largestBSTUtil(root.left);
        Info right = largestBSTUtil(root.right);
        
        Info i = new Info();
        
        // If either of left or right subtree is not BST or the data
        // of this node is not greater than max of left or less than min of right
        // then subtree with this node as root will not be BST. 
        // Return false and max size of left and right subtree to parent
        if (left.isBST == false || right.isBST == false || 
                left.max > root.key || right.min < root.key) {
            i.isBST = false;
            i.size = Math.max(left.size, right.size);
            return i;
        }
        
        // If we reach this point means subtree with this node as root is BST.
        // Set isBST as true. Then set size as size of left + size of right + 1.
        // Set min and max to be returned to parent.
        i.isBST = true;
        i.size = left.size + right.size + 1;
        
        // If root.left is null then set root.key as min else
        // take min of left side as min
        i.min = root.left != null ? left.min : root.key;
        
        // If root.right is null then set root.key as max else
        // take max of right side as max
        i.max = root.right != null ? right.max : root.key;
        
        return i;
        
    }
}

public class BstTest {

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert3(15);tree.insert3(10);tree.insert3(20);
        tree.insert3(8);tree.insert3(12);tree.insert3(17);
        tree.insert3(25);
        tree.inorder(); // 8 10 12 15 17 20 25 
        
        System.out.println(tree.search2(10) != null); // true
        System.out.println(tree.search2(14) != null); // false
        
        BinarySearchTree tree2 = new BinarySearchTree();
        tree2.insert2(15);tree2.insert2(10);tree2.insert2(20);
        tree2.insert2(8);tree2.insert2(12);tree2.insert2(17);
        tree2.insert2(25);
        tree2.inorder(); // 8 10 12 15 17 20 25
        
        System.out.println(tree2.search(10) != null); // true
        System.out.println(tree2.search(14) != null); // false
        
        tree.insert(23);tree.insert(27);
        System.out.println("Before delete:");
        tree.levelOrder(); // 15 10 20 8 12 17 25 23 27
        tree.delete(20);
        System.out.println("After delete:");
        tree.levelOrder(); // 15 10 23 8 12 17 25 27
        
        System.out.println("Minimum in tree is " + tree2.min()); // 8
        System.out.println("Minimum in tree is " + tree2.min2()); // 8
        System.out.println("Maximum in tree is " + tree2.max()); // 25
        System.out.println("Maximum in tree is " + tree2.max2()); // 25
        
        tree2.insert2(19);
        System.out.println("Height: " + tree2.height()); // 3
        System.out.println("Height Iterative: " + tree2.heightItr()); // 3
        
        System.out.print("Level order: ");
        tree2.levelOrder(); // 15 10 20 8 12 17 25 19
        System.out.print("Preorder: ");
        tree2.preorder(); // 15 10 8 12 20 17 19 25
        System.out.print("Postorder: ");
        tree2.postorder(); // 8 12 10 19 17 25 20 15
        
        System.out.print("Preorder Iterative: ");
        tree2.preorderItr(); // 15 10 8 12 20 17 19 25
        System.out.print("Inorder Iterative: ");
        tree2.inorderItr(); // 8 10 12 15 17 19 20 25
        System.out.print("Postorder Iterative: ");
        tree2.postorderItr(); // 8 12 10 19 17 25 20 15
        System.out.print("Postorder Iterative Using One Stack: ");
        tree2.postorderItrOneStack(); // 8 12 10 19 17 25 20 15
        
        System.out.print("Morris Inorder: ");
        tree2.morrisInorder(); // 8 10 12 15 17 19 20 25
        //System.out.print("Morris Inorder - approach2: ");
        //tree2.morrisInorder2(); // 8 10 12 15 17 19 20 25
        System.out.print("Morris Preorder: ");
        tree2.morrisPreorder(); // 15 10 8 12 20 17 19 25
        System.out.print("Morris Postorder: ");
        tree2.morrisPostorder(); // 8 12 10 19 17 25 20 15
        
        System.out.print("Reverse level order: ");
        tree2.levelOrderInReverse(); // 19 8 12 17 25 10 20 15
        
        System.out.println("Spiral level order: ");
        tree2.spiralUsingTwoStack(); // 15 20 10 8 12 17 25 19
        tree2.spiralUsingOneDequeAndDelimiter(); // 15 20 10 8 12 17 25 19
        tree2.spiralUsingOneDequeAndDelimiter(); // 15 20 10 8 12 17 25 19
        
        System.out.println("Level by level printing: ");
        /* 15
         * 10 20
         * 8 12 17 25
         * 19
         */
        tree2.levelByLevelTwoQueue();
        System.out.println("-----------------");
        tree2.levelByLevelOneQueueAndDelimiter();
        System.out.println("-----------------");
        tree2.levelByLevelOneQueueAndCounter();
        
        System.out.print("Inorder Successor - approach2: ");
        System.out.println(tree2.inorderSuccessor(19).key); // 20
        System.out.print("Inorder Successor - approach3: ");
        System.out.println(tree2.inorderSuccessor2(19).key); // 20
        
        System.out.print("Inorder Predecessor - approach2: ");
        System.out.println(tree2.inorderPredecessor(17).key); // 15
        System.out.print("Inorder Predecessor - approach3: ");
        System.out.println(tree2.inorderPredecessor2(17).key); // 15
        
        // Test if a binary tree is binary search tree or not
        BinarySearchTree tree3 = new BinarySearchTree();
        tree3.root = tree3.new Node(12); // create root node
        tree3.root.left = tree3.new Node(16); // left is bigger
        tree3.root.right = tree3.new Node(20);
        System.out.println(tree3.isBST()); // false
        System.out.println(tree3.isBST2()); // false
        tree3.root.left = tree3.new Node(8); // left is smaller
        System.out.println(tree3.isBST()); // true
        System.out.println(tree3.isBST2()); // true
        tree3.root.left.left = tree3.new Node(10); // left is bigger
        System.out.println(tree3.isBST()); // false
        System.out.println(tree3.isBST2()); // false
        
        // Same binary tree test
        BinarySearchTree tree4 = new BinarySearchTree();
        tree4.insert3(15);tree4.insert3(10);tree4.insert3(20);
        tree4.insert3(8);tree4.insert3(12);tree4.insert3(17);
        tree4.insert3(25);tree4.insert3(19);
        System.out.println(tree4.isSameBinaryTree(tree2)); // true
        System.out.println(tree4.isSameBinaryTree(tree)); // false
        System.out.println(tree4.isSameBinaryTreeItr(tree2)); // true
        System.out.println(tree4.isSameBinaryTreeItr(tree)); // false
        
        // Size test
        System.out.println(tree4.size()); // 8
        System.out.println(tree4.sizeItr()); // 8
        
        // rootToLeafSum test
        System.out.println(tree4.rootToLeafSum(71)); // 19<-17<-20<-15<-true
        System.out.println(tree4.rootToLeafSum(57)); // false
        
        // rootToLeafMaxSum test
        System.out.println(tree4.rootToLeafMaxSum()); // 71
        System.out.println(tree4.rootToLeafMinSum()); // 33
        
        // lowest common ancestor test
        System.out.println(tree4.lca(19, 25).key); // 20
        System.out.println(tree4.lcaItr(19, 25).key); // 20
        System.out.println(tree4.lcaBinaryTree(19, 25).key); // 20
        
        // largest BST test
        // When complete tree is BST
        System.out.println("Size of largest BST is " + tree4.largestBST()); // 8
        // When complete tree is not BST
        /* construct following Binary Tree
                  50
                /    \
              10      60
             /  \    /  \
            5   20  55   70
                   /    /  \
                  45   65   80
        */
        BinarySearchTree tree5 = new BinarySearchTree();
        tree5.root = tree5.new Node(50);
        tree5.root.left = tree5.new Node(10);
        tree5.root.right = tree5.new Node(60);
        tree5.root.left.left = tree5.new Node(5);
        tree5.root.left.right = tree5.new Node(20);
        tree5.root.right.left = tree5.new Node(55);
        tree5.root.right.left.left = tree5.new Node(45);
        tree5.root.right.right = tree5.new Node(70);
        tree5.root.right.right.left = tree5.new Node(65);
        tree5.root.right.right.right = tree5.new Node(80);
        
        /* The complete tree is not BST as 45 is in right subtree of 50.
        The following subtree is the largest BST
                60
               /  \
             55    70
             /     /  \
           45     65   80
       */
        System.out.println("Size of largest BST is " + tree5.largestBST()); // 6
        
        // Diameter test
        System.out.println("Diameter of tree is " + tree5.diameter()); // 6
        System.out.println("Diameter of tree is " + tree5.diameterOpt()); // 6
    }
}