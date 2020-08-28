package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.geeksforgeeks.org/maximum-length-chain-of-pairs-dp-20/
// https://www.geeksforgeeks.org/print-maximum-length-chain-of-pairs/

public class MaxLengthChainPairs {

    class Pair {
        int a;
        int b;
        
        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }
    }
    
    /*
     * This problem is a variation of standard Longest 
     * Increasing Subsequence problem. Following is a 
     * simple two step process we are going to use to 
     * solve this problem.
     * 
     * 1) Sort given pairs in increasing order of first 
     * (or smaller) element.
     * 
     * 2) Now run a modified LIS process where we compare 
     * the second element of already finalized LIS with 
     * the first element of new LIS being constructed.
     */
    // T(n): O(n^2), S(n): O(n)
    public int getMaxChainLength(Pair[] pairs) {
        Arrays.sort(pairs, (p1, p2) -> p1.a - p2.a);
        
        int n = pairs.length;
        int[] mcl = new int[n];
        
        mcl[0] = 1;
        for (int i = 1; i < n; ++i) {
            mcl[i] = 1;
            for (int j = i-1; j >= 0; --j) {
                if (pairs[j].b < pairs[i].a)
                    mcl[i] = max(mcl[i], 1 + mcl[j]);
            }
        }
        
        // get maximum from mcl[] array
        return Arrays.stream(mcl).max().getAsInt();
    }
    
    /*
     * We can also print the longest chain using similar logic.
     * see solution below.
     */
    // T(n): O(n^2), S(n): O(n)
    public void maxChainLength(Pair[] pairs) {
        Arrays.sort(pairs, (p1, p2) -> p1.a - p2.a);
        
        int n = pairs.length;
        List<Pair>[] mclResLists = new List[n];
        for (int i = 0; i < n; i++)
            mclResLists[i] = new ArrayList<Pair>();
        
        mclResLists[0].add(pairs[0]);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[j].b < pairs[i].a) {
                    if (mclResLists[i].size() < mclResLists[j].size()) {
                        mclResLists[i] = new ArrayList<Pair>(mclResLists[j]);
                    }
                }
            }
            mclResLists[i].add(pairs[i]);
        }
        
        int maxLen = 0;
        for (List<Pair> resList : mclResLists)
            maxLen = Math.max(maxLen, resList.size());
        
        System.out.println("Max chain length is: " + maxLen);
        System.out.println("Max length chains are: ");
        for (List<Pair> resList : mclResLists)
            if (resList.size() == maxLen)
                System.out.println(resList);
    }
    
    public static void main(String[] args) {
        MaxLengthChainPairs o = new MaxLengthChainPairs();
        Pair[] pairs = {o.new Pair(50, 60), o.new Pair(15, 25),
                        o.new Pair(5, 24), o.new Pair (27, 40)};
        
        System.out.println(o.getMaxChainLength(pairs)); // 3
        
        o.maxChainLength(pairs);
        /*
         * Max chain length is: 3
         * Max chain are: 
         * [(5, 24), (27, 40), (50, 60)]
         */
    }
}
