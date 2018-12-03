package advance_ds.segment_tree;

/**
 * @author Narendra Jha
 *
 * Segment Tree - Range Minimum Query - Lazy Propagation
 */
public class SegmentTreeRMQLazy {
	
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
		
		// construct left and right subtrees and then update value for current node
		int mid = (low + high) / 2;
		createSegmentTreeUtil(segmentTree, input, low, mid, (2*pos + 1));
		createSegmentTreeUtil(segmentTree, input, mid+1, high, (2*pos + 2));
		segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]); 
	}
	
	/**
     * Updates segment tree for a given range by given delta - Lazy Propagation
     */
    public void updateSegmentTreeRangeLazy(int[] segmentTree, int[] lazy, int inputLen,
    		int startRange, int endRange, int delta) {
    	updateSegmentTreeRangeUtilLazy(segmentTree, lazy, startRange, endRange, delta, 
        		0, inputLen - 1, 0);
    }

	private void updateSegmentTreeRangeUtilLazy(int[] segmentTree, int[] lazy, 
			int startRange, int endRange, int delta, int low, int high, int pos) {
		
		if(low > high) {
            return;
        }
		
		// Make sure all propagation is done at pos. If not, update
        // tree at pos and mark its children for lazy propagation
		if (lazy[pos] != 0) {
			segmentTree[pos] += lazy[pos];
			
			// if non-leaf node, update left and right child
			if (low != high) {
				lazy[2*pos + 1] += lazy[pos];
				lazy[2*pos + 2] += lazy[pos];
			}
			lazy[pos] = 0;
		}
		
		// NOW CHECK FOR THREE OVERLAP CONDITIONS:
		
		// NO OVERLAP:
		// If range to be updated is outside the current node range
		if(startRange > high || endRange < low) {
            return;
        }

		// TOTAL OVERLAP:
		if (startRange <= low && endRange >= high) {
			segmentTree[pos] += delta;
				
			// if non-leaf node, update lazy tree
			if (low != high) {
				lazy[2*pos + 1] += delta;
				lazy[2*pos + 2] += delta;
			}
            return;
        }

		// PARTIAL OVERLAP:
        // otherwise keep going left and right to find nodes to be updated 
        // and then update current node as minimum of left and right children
        int mid = (low + high)/2;
        updateSegmentTreeRangeUtilLazy(segmentTree, lazy, startRange, endRange, delta, 
        		low, mid, (2*pos + 1));
        updateSegmentTreeRangeUtilLazy(segmentTree, lazy, startRange, endRange, delta, 
        		mid+1, high, (2*pos + 2));
        segmentTree[pos] = Math.min(segmentTree[2*pos + 1], segmentTree[2*pos + 2]);
	}
	
	/**
     * Queries given range for minimum value - Lazy Propagation
     */
    public int rangeMinimumQueryLazy(int[] segmentTree, int[] lazy, 
    		int from, int to, int inputSize){
        return rangeMinimumQueryUtilLazy(segmentTree, lazy, 0, inputSize-1, from, to, 0);
    }

	private int rangeMinimumQueryUtilLazy(int[] segmentTree, int[] lazy, 
			int low, int high, int from, int to, int pos) {
		
		if(low > high) {
            return Integer.MAX_VALUE;
        }
		
		// Make sure all propagation is done at pos. If not, update
        // tree at pos and mark its children for lazy propagation
		if (lazy[pos] != 0) {
			segmentTree[pos] += lazy[pos];
			
			// if non-leaf node, update left and right child
			if (low != high) {
				lazy[2*pos + 1] += lazy[pos];
				lazy[2*pos + 2] += lazy[pos];
			}
			lazy[pos] = 0;
		}
		
		// NOW CHECK FOR THREE OVERLAP CONDITIONS:
		
		// NO OVERLAP
		if (from > high || to < low) {
			return Integer.MAX_VALUE;
		}
				
		// TOTAL OVERLAP
		if (from <= low && to >= high) {
			return segmentTree[pos];
		}
		
		// PARTIAL OVERLAP
		int mid = (low + high) / 2;
		int left = rangeMinimumQueryUtilLazy(segmentTree, lazy, low, mid, 
				from, to, (2*pos + 1));
		int right = rangeMinimumQueryUtilLazy(segmentTree, lazy, mid+1, high, 
				from, to, (2*pos + 2));
		return Math.min(left, right);
	}
	
	private int getNextPowerOfTwo(int n) {
		int logPart = (int) Math.ceil(Math.log(n) / Math.log(2));
		return (int) Math.pow(2, logPart);
	}

	public static void main(String[] args) {
		SegmentTreeRMQLazy segTreeRMQ = new SegmentTreeRMQLazy();
		int[] input = {0, 3, 4, 2, 1, 6, -1}; 
		int[] segmentTree = segTreeRMQ.createSegmentTree(input);
		int[] lazy = new int[segmentTree.length];
		
		System.out.println(segTreeRMQ.rangeMinimumQueryLazy(segmentTree, lazy, 
				0, 3, input.length)); // 0
		System.out.println(segTreeRMQ.rangeMinimumQueryLazy(segmentTree, lazy, 
				1, 5, input.length)); // 1
		System.out.println(segTreeRMQ.rangeMinimumQueryLazy(segmentTree, lazy, 
				1, 6, input.length)); // -1
		
		segTreeRMQ.updateSegmentTreeRangeLazy(segmentTree, lazy, input.length, 
				3, 5, -2); // {0, 3, 4, 0, -1, 4, -1}
		System.out.println(segTreeRMQ.rangeMinimumQueryLazy(segmentTree, lazy, 
				1, 5, input.length)); // -1
		
		segTreeRMQ.updateSegmentTreeRangeLazy(segmentTree, lazy, input.length, 
				3, 3, -2); // {0, 3, 4, -2, -1, 4, -1}
		System.out.println(segTreeRMQ.rangeMinimumQueryLazy(segmentTree, lazy, 
				0, 3, input.length)); // -2
	}

}
