package advance_ds.trie;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/word-formation-using-concatenation-of-two-dictionary-words/

/*
 *     Problem: Word formation using concatenation of two dictionary words
 * 
    Given a dictionary find out if a given word can be made by combining two words 
    in the dictionary.
    
    Examples:
    1.
    Input : dictionary[] = {"news", "abcd", "tree", 
                                  "geeks", "paper"}   
            word = "newspaper"
    Output : Yes
    We can form "newspaper" using "news" and "paper"
    
    2.
    Input : dictionary[] = {"geeks", "code", "xyz", 
                               "forgeeks", "paper"}   
            word = "geeksforgeeks"
    Output : Yes
    
    3.
    Input : dictionary[] = {"geek", "code", "xyz", 
                               "forgeeks", "paper"}   
            word = "geeksforgeeks"
    Output : No
    
    Solution:
    1. Construct a Trie out of given dictionary words.
    2. Get indexes of all prefix matches for the given word
    3. Match rest of the word for each indexes matched in previous step
       if any two combination is a match, return true, else false
*/

public class WordFormationUsingConcatenation extends Trie {
    
    public boolean isPossible(String word) {
        // Search for the word in the trie and get its prefix positions
        // upto which there is matched 
        List<Integer> prefixPositions = findPrefixIndices(word); 
       
        // Word formation is not possible if the word did not have 
        // at least one prefix match
        if (prefixPositions.isEmpty()) 
            return false; 
       
        // Search for rest of substring for each prefix match
        for (Integer i : prefixPositions) {
            //String restOfSubstring = word.substring(i, word.length()); 
            if (searchRecursive(root, word, i)) return true;
        }
        
        return false;
    }
    
    public List<Integer> findPrefixIndices(String word) {
        List<Integer> prefixPositions = new ArrayList<Integer>();
        TrieNode current = root; // starting with root
        int index;
        for (index = 0; index < word.length(); index++) {
            // store valid prefix positions
            if (current.isEndOfWord)
                prefixPositions.add(index);
            
            char ch = word.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null)
                return prefixPositions;
            
            current = node;
        }
        if (current.isEndOfWord)
            prefixPositions.add(index);
        return prefixPositions;
    }

    public void constructTrie(String[] input) {
        for (int i = 0; i < input.length; i++)
            insert(input[i]);
    }

    public static void main(String[] args) {
        WordFormationUsingConcatenation trie = new WordFormationUsingConcatenation();
        
        String[] dict = {"news", "newspa", "paper", "geek"};
        trie.constructTrie(dict);
        
        String word = "newspaper";
        if(trie.isPossible(word)) 
            System.out.println( "Yes"); 
        else 
            System.out.println("No");
        // Output: Yes
    }
    
}
