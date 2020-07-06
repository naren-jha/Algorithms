package basic.unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Union Find (AKA Disjoint Set) data structure
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class UnionFindGeneric<T> {

    private List<Integer> parents;
    private int numOfComp;
    private List<Integer> compSize;
    
    private Map<T, Integer> objectMap;
    
    public UnionFindGeneric() {
        objectMap = new HashMap<>();
        parents = new ArrayList<>();
        compSize = new ArrayList<>();
    }
    
    public int find(T s) {
        if (!objectMap.containsKey(s)) {
            int val = parents.size();
            parents.add(val);
            objectMap.put(s, val);
            compSize.add(1);
        }
        int n = objectMap.get(s);
        
        int r = n;
        while (parents.get(r) != r) r = parents.get(r);
        
        // compress path to get amortized time complexity
        while (n != r) {
            int p = parents.get(n);
            parents.set(n, r);
            n = p;
        }
        
        return r;
    }
    
    // Unifies elements 'm' and 'n' into one group
    // Returns 'false' if they are already in same group, else returns 'true'
    public boolean union(T m, T n) {
        int rm = find(m); // root on 'm'
        int rn = find(n); // root on 'n'
        
        // if the elements are already in the same group
        if (rm == rn) return false;
        
        // merge smaller group into larger group
        if (compSize.get(rm) < compSize.get(rn)) {
            parents.set(rm, rn);
            compSize.set(rn, compSize.get(rn) + compSize.get(rm));
        }
        else {
            parents.set(rn, rm);
            compSize.set(rm, compSize.get(rm) + compSize.get(rn));
        }
        
        --numOfComp;
        return true;
    }
    
    // Returns 'true' if 'm' and 'n' are in the same group, else returns 'false'
    public boolean areConnected(T m, T n) {
        return find(m) == find(n);
    }
    
    // Returns size of component node 'n' belongs to
    public int getCompSize(T n) {
        return compSize.get(find(n));
    }

    // Returns number of elements in union find
    public int size() {
        return parents.size();
    }
    
    // Returns number of components in union find
    public int getNumOfComponents() {
        return numOfComp;
    }
    
    public static void main(String[] args) {
        UnionFindGeneric<String> uf = new UnionFindGeneric<>();
        
        uf.union("rate", "ratings");
        uf.union("approval", "popularity");
        uf.union("popularity", "acceptance");
        
        System.out.println(uf.areConnected("rate", "ratings")); // true
        System.out.println(uf.areConnected("approval", "popularity")); // true
        System.out.println(uf.areConnected("popularity", "acceptance")); // true
        System.out.println(uf.areConnected("approval", "acceptance")); // true
        System.out.println(uf.areConnected("ratings", "approval")); // false
    }
}
