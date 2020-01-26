package dynamicprogramming.basic;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/maximum-length-subsequence-difference-adjacent-elements-either-0-1/
// https://www.geeksforgeeks.org/maximum-length-subsequence-difference-adjacent-elements-either-0-1-set-2/

public class LongestSubseqAdjacentDiff01 {

    /*
     * GeeksForGeeks has a O(n^2) dynamic programming solution 
     * based on the same idea which we used in Longest Increasing
     * Subsequence problem. This problem however can be solved in 
     * O(n) time using a Hashtable, see the solution below.
     */

    public int countSeq(int[] a) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int prev, next, same;
        int longestSubsequence = 0;
        for (int key : a) {
            prev = map.containsKey(key-1) ? map.get(key-1) : 0;
            next = map.containsKey(key+1) ? map.get(key+1) : 0;
            same = map.containsKey(key) ? map.get(key) : 0;
            int value = Math.max(prev, Math.max(next, same)) + 1;
            map.put(key, value);
            if (longestSubsequence < value)
                longestSubsequence = value;
        }
        return longestSubsequence;
    }
    
    public static void main(String[] args) {
        int[] a = {2, 5, 6, 3, 7, 6, 5, 8};
        LongestSubseqAdjacentDiff01 obj = new LongestSubseqAdjacentDiff01();
        System.out.println(obj.countSeq(a)); // 6
    }
    
}
