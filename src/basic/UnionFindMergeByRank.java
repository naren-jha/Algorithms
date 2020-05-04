package basic;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Union Find (AKA Disjoint Set) data structure
 * 
 * This implementation merges components by the rank of their root nodes,
 * which should help minimize the height of the tree, consequently improving 
 * performance. However, performance improvement due to rank based merging 
 * can be debated, as we can't use array as underlying data structure, 
 * and path compression is anyway going reduce the heights of 
 * all component trees to 1
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class UnionFindMergeByRank {

    private Map<Integer, Node> nodeMap = new HashMap<>();
    
    // To track number of components in union find
    private int numOfComp;
    
    // To track size of each component in union find
    private int[] compSize;
    
    class Node {
        int data;
        Node parent;
        int rank;
        
        Node(int data) {
            this.data = data;
            this.parent = this;
            this.rank = 0;
        }
    }
    
    public UnionFindMergeByRank(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("size should be greater than 0");
        
        this.numOfComp = size;
        compSize = new int[size];
        
        for (int i = 0; i < size; ++i) {
            nodeMap.put(i, new Node(i));
            compSize[i] = 1;
        }
    }
    
    public void addNewElement(int e) {
        if (nodeMap.containsKey(e)) throw new IllegalArgumentException("duplicate entry");
        nodeMap.put(e, new Node(e));
    }
    
    // Finds which component node 'n' belongs to
    public int find(int n) {
        if (!nodeMap.containsKey(n)) throw new IllegalArgumentException("invalid node");
        Node node = nodeMap.get(n);
        
        // find root of 'n'
        Node root = node;
        while (root.parent != root) root = root.parent;
        
        // compress path to get amortized time complexity
        while (node != root) {
            Node parent = node.parent;
            node.parent = root;
            node.rank = 0; // redundant
            node = parent;
        }
        // after path compression, update rank (height) of root node to 1
        if (n != root.data) root.rank = 1;
        
        return root.data;
    }
    
    // A recursive implementation of find() method
    public int findRec(int n) {
        if (!nodeMap.containsKey(n)) throw new IllegalArgumentException("invalid node");
        Node root = findRecUtil(nodeMap.get(n));
        
        // after path compression, update rank (height) of root node to 1
        if (n != root.data) root.rank = 1;
        
        return root.data;
    }
    
    // A utility method used in recursive implementation of find operation
    public Node findRecUtil(Node n) {
        if (n.parent == n) return n;
        return n.parent = findRecUtil(n.parent);
    }
    
    // Unifies elements 'm' and 'n' into one group
    // Returns 'false' if they are already in same group, else returns 'true'
    public boolean union(int m, int n) {
        int rm = find(m); // root on 'm'
        int rn = find(n); // root on 'n'
        
        // if the elements are already in the same group
        if (rm == rn) return false;
        
        Node rmNode = nodeMap.get(rm);
        Node rnNode = nodeMap.get(rn);
        
        // merge by rank
        if (rmNode.rank >= rnNode.rank) {
            rnNode.parent = rmNode;
            compSize[rm] += compSize[rn];
            
            if (rmNode.rank == rnNode.rank) rmNode.rank++;
        }
        else {
            rmNode.parent = rnNode;
            compSize[rn] += compSize[rm];
        }
        
        --numOfComp;
        return true;
    }
    
    // Returns 'true' if 'm' and 'n' are in the same group, else returns 'false'
    public boolean areConnected(int m, int n) {
        return find(m) == find(n);
    }
    
    // Returns size of component node 'n' belongs to
    public int getCompSize(int n) {
        return compSize[find(n)];
    }
    
    // Returns number of elements in union find
    public int size() {
        return nodeMap.size();
    }
    
    // Returns number of components in union find
    public int getNumOfComponents() {
        return numOfComp;
    }
    
    public static void main(String[] args) {
        UnionFindMergeByRank uf = new UnionFindMergeByRank(12);
        
        uf.union(4, 9);
        uf.union(0, 1);
        uf.union(5, 6);
        uf.union(5, 10);
        uf.union(4, 3);
        uf.union(3, 2);
        uf.union(7, 1);
        
        System.out.println(uf.find(7)); // 0
        System.out.println(uf.find(9)); // 4
        System.out.println(uf.find(10)); // 5
        
        System.out.println(uf.areConnected(3, 9)); // true
        System.out.println(uf.areConnected(1, 9)); // false
        
        System.out.println(uf.getCompSize(9)); // 4
        System.out.println(uf.getNumOfComponents()); // 5
        
        uf.union(4, 5);
        System.out.println(uf.find(10)); // 4
        System.out.println(uf.getCompSize(9)); // 7
        System.out.println(uf.getNumOfComponents()); // 4
    }
}
