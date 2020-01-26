package advance_ds.trie;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/find-all-shortest-unique-prefixes-to-represent-each-word-in-a-given-list/

/*
 *  Find shortest unique prefix for every word in a given list | Set 1 (Using Trie)
    Given an array of words, find all shortest unique prefixes to represent 
    each word in the given array. Assume that no word is prefix of another.
    
    Examples:
    
    Input: arr[] = {"zebra", "dog", "duck", "dove"}
    Output: dog, dov, du, z
    Explanation: dog => dog
                 dove = dov 
                 duck = du
                 z   => zebra 
    
    Input: arr[] =  {"geeksgeeks", "geeksquiz", "geeksforgeeks"};
    Output: geeksf, geeksg, geeksq}
 */

/*
 *     Solution:    
 *     A Simple Solution is to consider every prefix of every word (starting from the shortest to 
 *     largest), and if a prefix is not prefix of any other string, then print it.

    An Efficient Solution is to use Trie. The idea is to maintain a count in every node. 
    Below are steps:
    
    1) Construct a Trie of all words. Also maintain frequency of every node (Here frequency is
     number of times node is visited during insertion). Time complexity of this step is O(N) 
     where N is total number of characters in all words.
    
    2) Now, for every word, we find the character nearest to the root with frequency as 1. 
    The prefix of the word is path from root to this character. To do this, we can traverse 
    Trie starting from root. For every node being traversed, we check its frequency. If 
    frequency is one, we print all characters from root to this node and don’t traverse 
    down this node.
    Time complexity of this step also is O(N) where N is total number of characters in all words.
 */
public class ShortestUniquePrefix {
    
    // Trie DS - Start
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        int freq;
        
        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            isEndOfWord = false;
            freq = 1;
        }
    }
    
    TrieNode root;
    
    public ShortestUniquePrefix() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode current = root; // starting with root
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            else {
                node.freq++;
            }
            current = node;
        }
        current.isEndOfWord = true; // mark end of word
    }
    // Trie DS - End
    
    // returns shortest unique prefixes for all given words
    public String[] solution(String[] input) {
        // first construct trie out of given strings
        root.freq = 0; // not required
        for (int i = 0; i < input.length; i++) {
            insert(input[i]);
        }
        
        // then find shortest unique prefixes
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = "";
            TrieNode curr = root;
            for (int j = 0; j < input[i].length(); j++) {
                char ch = input[i].charAt(j);
                result[i] += ch;
                curr = curr.children.get(ch);
                if (curr.freq == 1)
                    break;
                
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ShortestUniquePrefix trie = new ShortestUniquePrefix();
        String[] input = {"zebra", "dog", "duck", "dove"};
        String[] result = trie.solution(input);
        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i] + " --> " + result[i]);
        }
        /*
        zebra-->z
        dog-->dog
        duck-->du
        dove-->dov
        */
    }

}
