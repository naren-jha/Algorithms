package advance_ds.segment_tree;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Problem Statement: Given 'n' number of coins, you have to perform two operation efficiently:
 * 1. Flip coins in a given range of index
 * 2. Find out number of head or tail in a given range of index
 */
public class CountToggleInARange {
    
    /**
     * Creates a new segment tree
     */
    public int[] createSegmentTree(int[] input) {
        int n = input.length;
        int segTreeSize = 2 * getNextPowerOfTwo(n) - 1;
        int[] segmentTree = new int[segTreeSize];
        
        createSegmentTreeUtil(segmentTree, input, 0, n-1, 0);
        return segmentTree;
    }

    private void createSegmentTreeUtil(int[] segmentTree, int[] input, 
            int low, int high, int pos) {
        if (low == high) {
            // its a leaf node
            segmentTree[pos] = input[low];
            return;
        }
        
        // construct left and right subtrees and then update value for current node
        int mid = (low + high) / 2;
        createSegmentTreeUtil(segmentTree, input, low, mid, (2*pos + 1));
        createSegmentTreeUtil(segmentTree, input, mid+1, high, (2*pos + 2));
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]); 
    }
    
    
    
    private int getNextPowerOfTwo(int n) {
        int logPart = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, logPart);
    }

    public static void main(String[] args) {
        CountToggleInARange countToggle = new CountToggleInARange();
        
        // coins array
        int[] input = {0, 1, 1, 0, 1, 0, 0, 1}; // 1 - HEAD, 0 - TAIL
        int[] segmentTree = countToggle.createSegmentTree(input);
        int[] lazy = new int[segmentTree.length];
    }

}
