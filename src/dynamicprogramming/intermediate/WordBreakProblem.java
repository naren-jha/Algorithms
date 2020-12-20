package dynamicprogramming.intermediate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://www.geeksforgeeks.org/word-break-problem-dp-32/
// https://leetcode.com/problems/word-break/

public class WordBreakProblem {
    
    class SimpleRecursiveSolution {
        // TC: O(2^(n-1))
        // We are trying to break the input string at every possible index. For input string of length 'n', 
        // there are n-1 such break points. At each break point the string is either broken or not broken,
        // and we are trying all possibilities.
        public boolean wordBreak(String s, List<String> wordDict) {
            return wordBreak(s, new HashSet<>(wordDict), 0);
        }
        
        private boolean wordBreak(String s, Set<String> dict, int si) {
            if (si == s.length()) return true; // base case: empty string
            
            for (int ei = si; ei < s.length(); ++ei) {
                if (dict.contains(s.substring(si, ei+1)) && wordBreak(s, dict, ei+1))
                    return true;
            }
            return false;
        }
    }
    
    class DPSolution {
        // bottom-up tabulation
        // TC: O(n^2), SC: O(n)
        public boolean wordBreak(String s, List<String> wordDict) {
            HashSet<String> dict = new HashSet<>(wordDict);
            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            dp[n] = true; // base case: empty string
            
            for (int si = n-1; si >= 0; si--) {
                for (int ei = si; ei < n; ++ei) {
                    if (dict.contains(s.substring(si, ei+1)) && dp[ei+1]) {
                        dp[si] = true;
                        break;
                    }
                }
            }
            
            return dp[0];
        }
    }
    
    public static void main(String[] args) {
        String[] words = {"mobile", "samsung", "sam", "sung", "man", "mango", "icecream", "and", 
                          "go", "i", "like", "ice", "cream", "cats", "dog", "sand", "fox", "cat"};
        List<String> wordDict = new ArrayList<>(Arrays.asList(words));
        WordBreakProblem solver = new WordBreakProblem();
        
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("ilikesamsung", wordDict)); // true
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("iiiiiiii", wordDict)); // true
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("", wordDict)); // true
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("ilikelikeimangoiii", wordDict)); // true
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("samsungandmango", wordDict)); // true
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("samsungandmangok", wordDict)); // false
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("catsandog", wordDict)); // false
        System.out.println(solver.new SimpleRecursiveSolution().wordBreak("catsanddog", wordDict)); // true
        
        System.out.println();
        System.out.println(solver.new DPSolution().wordBreak("ilikesamsung", wordDict)); // true
        System.out.println(solver.new DPSolution().wordBreak("iiiiiiii", wordDict)); // true
        System.out.println(solver.new DPSolution().wordBreak("", wordDict)); // true
        System.out.println(solver.new DPSolution().wordBreak("ilikelikeimangoiii", wordDict)); // true
        System.out.println(solver.new DPSolution().wordBreak("samsungandmango", wordDict)); // true
        System.out.println(solver.new DPSolution().wordBreak("samsungandmangok", wordDict)); // false
        System.out.println(solver.new DPSolution().wordBreak("catsandog", wordDict)); // false
        System.out.println(solver.new DPSolution().wordBreak("catsanddog", wordDict)); // true
    }
}
