package advance_ds.segment_tree;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Segment Tree - Range Sum Query
 */
public class RangeSumQuery {
    /**
     * Creates a new segment tree from given input array.
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
        
        // construct left and right subtrees and then set value for current node
        int mid = (low + high) / 2;
        createSegmentTreeUtil(segmentTree, input, low, mid, (2*pos + 1));
        createSegmentTreeUtil(segmentTree, input, mid+1, high, (2*pos + 2));
        segmentTree[pos] = segmentTree[2*pos + 1] + segmentTree[2*pos + 2]; 
    }
    
    /**
     * Updates segment tree for given index by given delta
     */
    public void updateSegmentTree(int input[], int segmentTree[], int index, int delta){
        input[index] += delta;
        updateSegmentTreeUtil(segmentTree, index, delta, 0, input.length - 1, 0);
    }

    private void updateSegmentTreeUtil(int[] segmentTree, int index, int delta, 
            int low, int high, int pos) {
        // if index to be updated is outside the current node range
        if(index < low || index > high){
            return;
        }
        
        // if low and high become equal, then index will also be equal to them
        // so we update value in segment tree at pos, and return as low == high
        // means its a leaf node
        if(low == high){
            segmentTree[pos] += delta;
            return;
        }
        
        // otherwise keep going left and right to find indexes to be updated 
        // and then update current node as sum of left and right children
        int mid = (low + high) / 2;
        updateSegmentTreeUtil(segmentTree, index, delta, low, mid, (2*pos + 1));
        updateSegmentTreeUtil(segmentTree, index, delta, mid + 1, high, (2*pos + 2));
        segmentTree[pos] = segmentTree[2*pos + 1] + segmentTree[2*pos + 2];
    }
    
    /**
     * Updates segment tree for a given range by given delta
     */
    public void updateSegmentTreeRange(int[] input, int[] segmentTree, 
            int startRange, int endRange, int delta) {
        for(int i = startRange; i <= endRange; i++) {
            input[i] += delta;
        }
        
        updateSegmentTreeRangeUtil(segmentTree, startRange, endRange, delta, 
                0, input.length - 1, 0);
    }

    private void updateSegmentTreeRangeUtil(int[] segmentTree, int startRange, int endRange, 
            int delta, int low, int high, int pos) {
        
        // if range to be updated is outside the current node range
        if(low > high || startRange > high || endRange < low ) {
            return;
        }

        // if leaf node
        if(low == high) {
            segmentTree[pos] += delta;
            return;
        }

        // otherwise keep going left and right to find nodes to be updated 
        // and then update current node as sum of left and right children
        int mid = (low + high)/2;
        updateSegmentTreeRangeUtil(segmentTree, startRange, endRange, delta, 
                low, mid, (2*pos + 1));
        updateSegmentTreeRangeUtil(segmentTree, startRange, endRange, delta, 
                mid+1, high, (2*pos + 2));
        segmentTree[pos] = segmentTree[2*pos + 1] + segmentTree[2*pos + 2];
    }
    
    /**
     * Calculates sum of numbers in given range.
     */
    public int rangeSumQuery(int[] segmentTree, int from, int to, int inputSize){
        return rangeSumQueryUtil(segmentTree, 0, inputSize-1, from, to, 0);
    }

    private int rangeSumQueryUtil(int[] segmentTree, int low, int high, 
            int from, int to, int pos) {
        // total overlap
        if (from <= low && to >= high) {
            return segmentTree[pos];
        }
        
        // no overlap
        if (from > high || to < low) {
            return 0;
        }
        
        // partial overlap
        int mid = (low + high) / 2;
        int left = rangeSumQueryUtil(segmentTree, low, mid, from, to, (2*pos + 1));
        int right = rangeSumQueryUtil(segmentTree, mid+1, high, from, to, (2*pos + 2));
        return left + right;
    }

    private int getNextPowerOfTwo(int n) {
        int logPart = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, logPart);
    }

    public static void main(String[] args) {
        RangeSumQuery rsq = new RangeSumQuery();
        int[] input = {0, 3, 4, 2, 1, 6, -1}; 
        int[] segmentTree = rsq.createSegmentTree(input);
        
        System.out.println(rsq.rangeSumQuery(segmentTree, 0, 3, input.length)); // 9
        System.out.println(rsq.rangeSumQuery(segmentTree, 1, 5, input.length)); // 16
        System.out.println(rsq.rangeSumQuery(segmentTree, 1, 6, input.length)); // 15
        
        rsq.updateSegmentTree(input, segmentTree, 3, 4); // {0, 3, 4, 6, 1, 6, -1}
        System.out.println(rsq.rangeSumQuery(segmentTree, 1, 3, input.length)); // 13
        
        rsq.updateSegmentTreeRange(input, segmentTree, 3, 5, -2); // {0, 3, 4, 4, -1, 4, -1}
        System.out.println(rsq.rangeSumQuery(segmentTree, 1, 5, input.length)); // 14
        System.out.println(rsq.rangeSumQuery(segmentTree, 0, 3, input.length)); //11
        
    }
}
