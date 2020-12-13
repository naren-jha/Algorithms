package advance_ds.segment_tree;

import static java.lang.Math.min;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Implementation of Segment Tree - Range Minimum Query
 */
public class RangeMinimumQuery {
    
    private int[] st; // array to store segment tree
    private int n; // length of input array
    
    public RangeMinimumQuery(int[] input) {
        n = input.length;
        int stSize = 2 * nextPowerOfTwo(n) - 1;
        
        st = new int[stSize];
        buildTree(input, 0, n-1, 0);
    }
    
    /**
     * Creates a new segment tree from given input array.
     */
    private void buildTree(int[] input, int low, int high, int pos) {
        if (low == high) {
            // its a leaf node
            st[pos] = input[low];
            return;
        }
        
        // construct left and right subtrees and then update value for current node
        int mid = (low + high) / 2;
        buildTree(input, low, mid, (2*pos + 1));
        buildTree(input, mid+1, high, (2*pos + 2));
        st[pos] = min(st[2*pos + 1], st[2*pos + 2]);
    }
    
    /**
     * Updates segment tree for given index by given delta
     */
    public void update(int index, int delta){
        updateUtil(index, delta, 0, n-1, 0);
    }

    private void updateUtil(int index, int delta, int low, int high, int pos) {
        // if index to be updated is outside the current node range
        if (index < low || index > high){
            return;
        }
        
        // if low and high become equal, then index will also be equal to them
        // so we update value in segment tree at pos, and return as low == high
        // means its a leaf node
        if (low == high){
            st[pos] += delta;
            return;
        }
        
        // otherwise keep going left and right to find indexes to be updated 
        // and then update current node minimum as min of left and right children
        int mid = (low + high) / 2;
        updateUtil(index, delta, low, mid, (2*pos + 1));
        updateUtil(index, delta, mid + 1, high, (2*pos + 2));
        st[pos] = min(st[2*pos + 1], st[2*pos + 2]);
    }
    
    /**
     * Updates segment tree for a given range by given delta
     */
    public void updateRange(int from, int to, int delta) {
        if (from > to) throw new IllegalArgumentException("invalid range");
        updateRangeUtil(from, to, delta, 0, n-1, 0);
    }

    private void updateRangeUtil(int from, int to, int delta, int low, int high, int pos) {
        // if range to be updated is outside the current node range
        if (to < low || from > high) {
            return;
        }

        // if leaf node
        if (low == high) {
            st[pos] += delta;
            return;
        }

        // otherwise keep going left and right to find nodes to be updated 
        // and then update current node as minimum of left and right children
        int mid = (low + high) / 2;
        updateRangeUtil(from, to, delta, low, mid, (2*pos + 1));
        updateRangeUtil(from, to, delta, mid+1, high, (2*pos + 2));
        st[pos] = min(st[2*pos + 1], st[2*pos + 2]);
    }
    
    /**
     * Queries given range for minimum value.
     */
    public int minInRange(int from, int to) {
        if (from > to) throw new IllegalArgumentException("invalid range");
        return minInRangeUtil(0, n-1, from, to, 0);
    }

    private int minInRangeUtil(int low, int high, int from, int to, int pos) {
        // total overlap
        if (from <= low && to >= high) {
            return st[pos];
        }
        
        // no overlap
        if (to < low || from > high) {
            return Integer.MAX_VALUE;
        }
        
        // partial overlap
        int mid = (low + high) / 2;
        int left = minInRangeUtil(low, mid, from, to, (2*pos + 1));
        int right = minInRangeUtil(mid+1, high, from, to, (2*pos + 2));
        return min(left, right);
    }
    
    // Calculates next power of two for given n, is = 2^ceil(lgn))
    // Equivalent to: int x = (int) Math.pow(2, (int) Math.ceil(Math.log(n) / Math.log(2)));
    public static int nextPowerOfTwo(int n) {
        n--; // to handle the case when n is a perfect square
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n |= n >> 32;
        n++;
        return n;
    }

    public static void main(String[] args) {
        int[] input = {0, 3, 4, 2, 1, 6, -1}; 
        RangeMinimumQuery rmq = new RangeMinimumQuery(input);
        
        System.out.println(rmq.minInRange(0, 3)); // 0
        System.out.println(rmq.minInRange(1, 5)); // 1
        System.out.println(rmq.minInRange(1, 6)); // -1
        
        rmq.update(3, 4); // {0, 3, 4, 6, 1, 6, -1}
        System.out.println(rmq.minInRange(1, 3)); // 3
        
        rmq.updateRange(3, 5, -2); // {0, 3, 4, 4, -1, 4, -1}
        System.out.println(rmq.minInRange(1, 5)); // -1
        System.out.println(rmq.minInRange(0, 3)); // 0
    }

}
