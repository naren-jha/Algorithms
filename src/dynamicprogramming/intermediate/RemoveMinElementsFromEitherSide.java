package dynamicprogramming.intermediate;

import java.util.PriorityQueue;

// https://www.geeksforgeeks.org/remove-minimum-elements-either-side-2min-max/
// https://www.geeksforgeeks.org/remove-minimum-elements-from-either-side-such-that-2min-becomes-more-than-max-set-2/

// https://www.geeksforgeeks.org/remove-minimum-elements-from-the-array-such-that-2min-becomes-more-than-max

public class RemoveMinElementsFromEitherSide {

     // T(n): O(n^2)
    public int removeMinElements(int[] a) {
        int n = a.length;
        int maxValidLen = 0;
        
        for (int start = 0; start < n; start++) {
            int min = Integer.MAX_VALUE; // min in current window
            int max = Integer.MIN_VALUE; // max in current window
            
            for (int end = start; end < n; end++) {
                min = Math.min(min, a[end]);
                max = Math.max(max, a[end]);
                if (2*min <= max)
                    break;
                
                int len = end-start+1;
                if (maxValidLen < len)
                    maxValidLen = len;
            }
        }
        return n - maxValidLen;
    }
    
    // T(n): O(nlgn) + O(n) = O(nlgn)
    class SolutionUsingRMQ {
        /*
         * Algorithm:
         * 1. Construct Segment Tree for RangeMinimumQuery and RangeMaximumQuery
         *    for given input array
         * 2. Take two pointers 'start' and 'end', and initialize both to 0
         * 3. While 'end' is less than length of input array, do following
         *    3.1 find min and max in current window using Segment Trees 
         *        constructed in step 1
         *    3.2 check if 2*min <= max, if so then increment 'start' pointer
         *        else update max valid length so far, if required 
         *    3.3 increment 'end'
         * 4. (inputArrayLength - maxValidLen) is your answer
         */
        public int removeMinElements(int[] a) {
            int n = a.length;
            
            RangeMinimumQuery rMinQ = new RangeMinimumQuery();
            int[] minTree = rMinQ.createSegmentTree(a);
            
            RangeMaximumQuery rMaxQ = new RangeMaximumQuery();
            int[] maxTree = rMaxQ.createSegmentTree(a);
            
            int start = 0, end = 0;
            int min, max; // min, max in current window
            int maxValidLen = 0;
            
            while (end < n) {
                min = rMinQ.rangeMinimumQuery(minTree, start, end, n);
                max = rMaxQ.rangeMaximumQuery(maxTree, start, end, n);
                if (2*min <= max)
                    start++;
                else
                    maxValidLen = Math.max(maxValidLen, end-start+1);
                end++;
            }
            return n - maxValidLen;
        }
    }
    
    public static void main(String[] args) {
        int[] a = {4, 5, 100, 9, 10, 11, 12, 15, 200};
        RemoveMinElementsFromEitherSide o = new RemoveMinElementsFromEitherSide();
        
        System.out.println(o.removeMinElements(a)); // 4
        
        System.out.println(o.new SolutionUsingRMQ().removeMinElements(a)); // 4
    }
    
    
    
    /**
     * @author Narendra Jha
     *
     * Segment Tree - Range Minimum Query
     */
    class RangeMinimumQuery {
        
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
         * Queries given range for minimum value.
         */
        public int rangeMinimumQuery(int[] segmentTree, int from, int to, int inputSize){
            return rangeMinimumQueryUtil(segmentTree, 0, inputSize-1, from, to, 0);
        }

        private int rangeMinimumQueryUtil(int[] segmentTree, int low, int high, 
                int from, int to, int pos) {
            // total overlap
            if (from <= low && to >= high) {
                return segmentTree[pos];
            }
            
            // no overlap
            if (from > high || to < low) {
                return Integer.MAX_VALUE;
            }
            
            // partial overlap
            int mid = (low + high) / 2;
            int left = rangeMinimumQueryUtil(segmentTree, low, mid, from, to, (2*pos + 1));
            int right = rangeMinimumQueryUtil(segmentTree, mid+1, high, from, to, (2*pos + 2));
            return Math.min(left, right);
        }
    }

    /**
     * @author Narendra Jha
     *
     * Segment Tree - Range Maximum Query
     */
    class RangeMaximumQuery {
        
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
            segmentTree[pos] = Math.max(segmentTree[2*pos + 1], segmentTree[2*pos + 2]); 
        }
        
        /**
         * Queries given range for maximum value.
         */
        public int rangeMaximumQuery(int[] segmentTree, int from, int to, int inputSize){
            return rangeMaximumQueryUtil(segmentTree, 0, inputSize-1, from, to, 0);
        }

        private int rangeMaximumQueryUtil(int[] segmentTree, int low, int high, 
                int from, int to, int pos) {
            // total overlap
            if (from <= low && to >= high) {
                return segmentTree[pos];
            }
            
            // no overlap
            if (from > high || to < low) {
                return Integer.MIN_VALUE;
            }
            
            // partial overlap
            int mid = (low + high) / 2;
            int left = rangeMaximumQueryUtil(segmentTree, low, mid, from, to, (2*pos + 1));
            int right = rangeMaximumQueryUtil(segmentTree, mid+1, high, from, to, (2*pos + 2));
            return Math.max(left, right);
        }
    }
    
    
    private int getNextPowerOfTwo(int n) {
        int logPart = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, logPart);
    }
}
