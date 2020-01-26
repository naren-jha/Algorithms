package advance_ds.trie;

import advance_ds.trie.Trie.TrieNode;

// https://www.geeksforgeeks.org/longest-common-prefix-using-trie/

/*
 *     Given a set of strings, find the longest common prefix.

    Input  : {“geeksforgeeks”, “geeks”, “geek”, “geezer”}
    Output : "gee"
    
    Input  : {"apple", "ape", "april"}
    Output : "ap"
    
    We can solve this problem in many ways, lets solve it here 
    using Trie date structure.
    
    Steps:
    1. Insert all the words one by one in the trie. 
       After inserting we perform a walk on the trie.
      
    2. In this walk, go deeper until we find a node having more than 1 children
       (branching occurs) or 0 children (one of the string gets exhausted).
       This is because the characters (nodes in trie) which are present in the 
       longest common prefix must be the single child of its parent, 
       i.e., there should not be a branching in any of these nodes.
        
        
    Time Complexity : Inserting all the words in the trie takes O(M*N) time and 
    performing a walk on the trie takes O(M) time, where
        N = Number of strings
        M = Length of the largest string string
 */

public class LongestCommonPrefix {
    
    private Trie trie = new Trie();
    
    public String solution(String[] input) {
        // first construct trie out of given strings
        constructTrie(input);
        
        // then find longest common prefix
        TrieNode current = trie.root;
        StringBuilder res = new StringBuilder();
        char key;
        while (current.children.size() == 1) {
            key = current.children.keySet().iterator().next();
            res.append(key);
            current = current.children.get(key);
        }
        return res.toString();
    }
    
    public void constructTrie(String[] input) {
        for (int i = 0; i < input.length; i++)
            trie.insert(input[i]);
    }

    public static void main(String[] args) {
        LongestCommonPrefix obj = new LongestCommonPrefix();
        String[] input = {"geeksforgeeks", "geeks", "geek", "geezer"}; 
        System.out.println(obj.solution(input)); // gee
        
        LongestCommonPrefix obj2 = new LongestCommonPrefix();
        String[] input2 = {"abcd", "efgh", "ijkl", "mnop"}; 
        System.out.println(obj2.solution(input2)); // <EMPTY_STRING>
        
        LongestCommonPrefix obj3 = new LongestCommonPrefix();
        String[] input3 = {"geeksforgeeks", "geeks", "geek", "abc"}; 
        System.out.println(obj3.solution(input3)); // <EMPTY_STRING>
    }

}
