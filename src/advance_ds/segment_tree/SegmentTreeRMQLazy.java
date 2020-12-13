package advance_ds.segment_tree;

import static java.lang.Math.min;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Segment Tree - Range Minimum Query - Lazy Propagation
 */
public class SegmentTreeRMQLazy {
    
    private int[] st; // array to store segment tree
    private int[] lt; // array to store lazy tree
    private int n; // length of input array
    
    public SegmentTreeRMQLazy(int[] input) {
        n = input.length;
        int stSize = 2 * nextPowerOfTwo(n) - 1;
        st = new int[stSize];
        lt = new int[stSize];
        constructTree(input, 0, n-1, 0);
    }
    
    /**
     * Creates a new segment tree from given input array.
     */
    private void constructTree(int[] input, int low, int high, int pos) {
        if (low == high) {
            // its a leaf node
            st[pos] = input[low];
            return;
        }
        
        // construct left and right subtrees and then update value for current node
        int mid = (low + high) / 2;
        constructTree(input, low, mid, (2*pos + 1));
        constructTree(input, mid+1, high, (2*pos + 2));
        st[pos] = min(st[2*pos + 1], st[2*pos + 2]); 
    }
    
    /**
     * Updates segment tree for a given range by given delta - Lazy Propagation
     */
    public void updateSegmentTreeRangeLazy(int from, int to, int delta) {
        updateSegmentTreeRangeUtilLazy(from, to, delta, 0, n - 1, 0);
    }

    private void updateSegmentTreeRangeUtilLazy(int from, int to, int delta, 
                                                    int low, int high, int pos) {
        if (low > high) {
            return;
        }
        
        // Make sure all propagation is done at pos. If not, update
        // tree at pos and mark its children for lazy propagation
        if (lt[pos] != 0) {
            st[pos] += lt[pos];
            
            // if non-leaf node, update left and right child
            if (low != high) {
                lt[2*pos + 1] += lt[pos];
                lt[2*pos + 2] += lt[pos];
            }
            lt[pos] = 0;
        }
        
        // NOW CHECK FOR THREE OVERLAP CONDITIONS:
        
        // NO OVERLAP:
        // If range to be updated is outside the current node range
        if(from > high || to < low) {
            return;
        }

        // TOTAL OVERLAP:
        if (from <= low && to >= high) {
            st[pos] += delta;
                
            // if non-leaf node, update lazy tree
            if (low != high) {
                lt[2*pos + 1] += delta;
                lt[2*pos + 2] += delta;
            }
            return;
        }

        // PARTIAL OVERLAP:
        // otherwise keep going left and right to find nodes to be updated 
        // and then update current node as minimum of left and right children
        int mid = (low + high)/2;
        updateSegmentTreeRangeUtilLazy(from, to, delta, low, mid, (2*pos + 1));
        updateSegmentTreeRangeUtilLazy(from, to, delta, mid+1, high, (2*pos + 2));
        st[pos] = min(st[2*pos + 1], st[2*pos + 2]);
    }
    
    /**
     * Queries given range for minimum value - Lazy Propagation
     */
    public int rangeMinimumQueryLazy(int from, int to){
        return rangeMinimumQueryUtilLazy(from, to, 0, n-1, 0);
    }

    private int rangeMinimumQueryUtilLazy(int from, int to, int low, int high, int pos) {
        
        if(low > high) {
            return Integer.MAX_VALUE;
        }
        
        // Make sure all propagation is done at pos. If not, update
        // tree at pos and mark its children for lazy propagation
        if (lt[pos] != 0) {
            st[pos] += lt[pos];
            
            // if non-leaf node, update left and right child
            if (low != high) {
                lt[2*pos + 1] += lt[pos];
                lt[2*pos + 2] += lt[pos];
            }
            lt[pos] = 0;
        }
        
        // NOW CHECK FOR THREE OVERLAP CONDITIONS:
        
        // NO OVERLAP
        if (from > high || to < low) {
            return Integer.MAX_VALUE;
        }
                
        // TOTAL OVERLAP
        if (from <= low && to >= high) {
            return st[pos];
        }
        
        // PARTIAL OVERLAP
        int mid = (low + high) / 2;
        int left = rangeMinimumQueryUtilLazy(from, to, low, mid, (2*pos + 1));
        int right = rangeMinimumQueryUtilLazy(from, to, mid+1, high, (2*pos + 2));
        return Math.min(left, right);
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
        SegmentTreeRMQLazy segTreeRMQ = new SegmentTreeRMQLazy(input);
        
        System.out.println(segTreeRMQ.rangeMinimumQueryLazy(0, 3)); // 0
        System.out.println(segTreeRMQ.rangeMinimumQueryLazy(1, 5)); // 1
        System.out.println(segTreeRMQ.rangeMinimumQueryLazy(1, 6)); // -1
        
        segTreeRMQ.updateSegmentTreeRangeLazy(3, 5, -2); // {0, 3, 4, 0, -1, 4, -1}
        System.out.println(segTreeRMQ.rangeMinimumQueryLazy(1, 5)); // -1
        
        segTreeRMQ.updateSegmentTreeRangeLazy(3, 3, -2); // {0, 3, 4, -2, -1, 4, -1}
        System.out.println(segTreeRMQ.rangeMinimumQueryLazy(0, 3)); // -2
    }
}
