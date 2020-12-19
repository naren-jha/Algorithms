package dynamicprogramming.intermediate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// https://www.geeksforgeeks.org/word-break-problem-dp-32/
// https://leetcode.com/problems/word-break/

public class WordBreakProblem {
    
    // Aug 2020
    public boolean wordBreakRec(String s, Set<String> dict) {
        return wordBreak(s, s.length(), dict, 0);
    }
    
    private boolean wordBreak(String s, int n, Set<String> dict, int start) {
        if (start == n) return true; // empty string
        for (int end = start; end < n; ++end) {
            if (dict.contains(s.substring(start, end+1)) && wordBreak(s, n, dict, end+1))
                return true;
        }
        return false;
    }
    
    public boolean wordBreak(String s, Set<String> dict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[n] = true; // empty string
        
        for (int start = n-1; start >= 0; start--) {
            for (int end = start; end < n; ++end) {
                if (dict.contains(s.substring(start, end+1)) && dp[end+1]) {
                    dp[start] = true;
                    break;
                }
            }
        }
        
        return dp[0];
    }
    
    // ==================================================================
    

    private String[] words = {"mobile", "samsung", "sam", "sung", 
                                "man", "mango", "icecream", "and", 
                                    "go", "i", "like", "ice", "cream"};
    private Set<String> dict = new HashSet<String>(Arrays.asList(words));
    
    class SimpleRecursiveSolution {
        /*
         * T(n): Exponential. O(2^(n-1)) to be precise.
         * We are inserting spaces in between string. There can be n-1 space
         * inserted for a string of length 'n'. Each space can be either 
         * present or not present, and we are considering all possibilities.
         */
        public boolean isWordBreak(String s) {
            int n = s.length();
            if (n == 0)
                return true;
            
            for (int i = 1; i <= n; i++)
                if (dict.contains(s.substring(0, i)) && isWordBreak(s.substring(i, n)))
                    return true;
            return false;
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n)
        public boolean isWordBreak(String s) {
            int n = s.length();
            if (n == 0)
                return true;
            
            // create a boolean array to store results. If res[i] is true, then it
            // means s[i, n] can be segmented into dictionary words. So the final 
            // result will be stored in res[0]
            boolean[] res = new boolean[n];
            
            // initializations
            Arrays.fill(res, false); // redundant in Java            
            res[n-1] = dict.contains(s.substring(n-1, n)); // is last character present
            
            for (int i = n-2; i >= 0; i--) {
                for (int j = i+1; j <= n; j++) {
                    // j == n avoids IndexOutOfBoundsException from res[j]
                    if (dict.contains(s.substring(i, j)) && (j == n || res[j])) {
                        res[i] = true;
                        break;
                    }
                }
            }
            return res[0];
        }
    }
    
    public static void main(String[] args) {
        WordBreakProblem obj = new WordBreakProblem();
        WordBreakProblem.SimpleRecursiveSolution srs = obj.new SimpleRecursiveSolution();
        System.out.println(srs.isWordBreak("ilikesamsung")); // true
        System.out.println(srs.isWordBreak("iiiiiiii")); // true
        System.out.println(srs.isWordBreak("")); // true
        System.out.println(srs.isWordBreak("ilikelikeimangoiii")); // true
        System.out.println(srs.isWordBreak("samsungandmango")); // true
        System.out.println(srs.isWordBreak("samsungandmangok")); // false
        
        System.out.println("------");
        WordBreakProblem.DPSolution dps = obj.new DPSolution();
        System.out.println(dps.isWordBreak("ilikesamsung")); // true
        System.out.println(dps.isWordBreak("iiiiiiii")); // true
        System.out.println(dps.isWordBreak("")); // true
        System.out.println(dps.isWordBreak("ilikelikeimangoiii")); // true
        System.out.println(dps.isWordBreak("samsungandmango")); // true
        System.out.println(dps.isWordBreak("samsungandmangok")); // false
        
        System.out.println("------");
        System.out.println(obj.wordBreakRec("ilikesamsung", obj.dict)); // true
        System.out.println(obj.wordBreakRec("iiiiiiii", obj.dict)); // true
        System.out.println(obj.wordBreakRec("", obj.dict)); // true
        System.out.println(obj.wordBreakRec("ilikelikeimangoiii", obj.dict)); // true
        System.out.println(obj.wordBreakRec("samsungandmango", obj.dict)); // true
        System.out.println(obj.wordBreakRec("samsungandmangok", obj.dict)); // false
        // "catsandog"
        String[] d = {"cats","dog","sand","and","cat"};
        System.out.println(obj.wordBreakRec("catsandog", new HashSet<>(Arrays.asList(d)))); // false
        
        System.out.println("------");
        System.out.println(obj.wordBreak("ilikesamsung", obj.dict)); // true
        System.out.println(obj.wordBreak("iiiiiiii", obj.dict)); // true
        System.out.println(obj.wordBreak("", obj.dict)); // true
        System.out.println(obj.wordBreak("ilikelikeimangoiii", obj.dict)); // true
        System.out.println(obj.wordBreak("samsungandmango", obj.dict)); // true
        System.out.println(obj.wordBreak("samsungandmangok", obj.dict)); // false
        // "catsandog"
        System.out.println(obj.wordBreak("catsandog", new HashSet<>(Arrays.asList(d)))); // false
    }
}
