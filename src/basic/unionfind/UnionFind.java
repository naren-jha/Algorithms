package basic.unionfind;

/**
 * Implementation of Union Find (AKA Disjoint Set) data structure
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class UnionFind {

    // array to store the parent of each node, p[i] points to parent of i
    // if p[i] = i then i is a root node
    private int[] p;
    
    // To track number of components in union find
    private int numOfComp;
    
    // To track size of each component in union find
    private int[] compSize;
    
    public UnionFind(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("size should be greater than 0");
        
        this.numOfComp = size;
        
        p = new int[size];
        compSize = new int[size];
        
        for (int i = 0; i < size; ++i) {
            p[i] = i; // each node is its own parent
            compSize[i] = 1; // every component size is initially 1
        }
    }
    
    // Finds which component node 'n' belongs to
    public int find(int n) {
        // find root of 'n'
        int r = n;
        while (p[r] != r) r = p[r];
        
        // compress path to get amortized time complexity
        while (n != r) {
            int parent = p[n];
            p[n] = r;
            n = parent;
        }
        
        return r;
    }
    
    // A recursive implementation of find() method
    public int findRec(int n) {
        if (p[n] == n) return n;
        return p[n] = findRec(p[n]);
    }
    
    // Unifies elements 'm' and 'n' into one group
    // Returns 'false' if they are already in same group, else returns 'true'
    public boolean union(int m, int n) {
        int rm = find(m); // root on 'm'
        int rn = find(n); // root on 'n'
        
        // if the elements are already in the same group
        if (rm == rn) return false;
        
        // merge smaller group into larger group
        if (compSize[rm] < compSize[rn]) {
            p[rm] = rn;
            compSize[rn] += compSize[rm];
        }
        else {
            p[rn] = rm;
            compSize[rm] += compSize[rn];
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
        return p.length;
    }
    
    // Returns number of components in union find
    public int getNumOfComponents() {
        return numOfComp;
    }
    
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(12);
        
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
