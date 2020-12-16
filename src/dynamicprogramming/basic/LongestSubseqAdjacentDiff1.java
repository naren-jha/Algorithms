package dynamicprogramming.basic;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/longest-subsequence-such-that-difference-between-adjacents-is-one/

public class LongestSubseqAdjacentDiff1 {
    
    /*
     * We can solve this problem in O(n^2) time and O(n) space using similar approach as LIS problem.
     * However this problem can be solved in O(n) time as well.
     * 
     * Idea:
     * For each a[i], we can append it to a subsequence which ends with either (a[i] + 1) or (a[i] - 1)
     * so we maintain a HashMap and store {a[i] -> (max length of subsequence ending with a[i])}
     * then for each a[i], we find length of (a[i] + 1) and (a[i] - 1) from the HashMap
     * and add 1 to whichever is bigger (to get the max length of subsequence ending with a[i]).
     */

    public int countSeq(int[] a) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // a[i] -> (max length of subsequence ending with a[i])
        
        int longest = 0;
        for (int key : a) {
            int l1 = map.getOrDefault(key-1, 0);
            int l2 = map.getOrDefault(key+1, 0);
            int l = max(l1, l2) + 1;
            map.put(key, l);
            
            longest = max(longest, l);
        }
        
        return longest;
    }
    
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 3, 2};
        LongestSubseqAdjacentDiff1 solver = new LongestSubseqAdjacentDiff1();
        System.out.println(solver.countSeq(a)); // 6
    }
}
