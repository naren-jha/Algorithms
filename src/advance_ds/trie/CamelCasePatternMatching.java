package advance_ds.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://www.geeksforgeeks.org/print-words-matching-pattern-camelcase-notation-dictonary/

/*
 *  Problem: Print all words matching a pattern in CamelCase Notation Dictionary

    Given a dictionary of words where each word follows CamelCase notation, print 
    all words in the dictionary that match with a given pattern consisting of 
    uppercase characters only.
    
    CamelCase is the practice of writing compound words or phrases such that each 
    word or abbreviation begins with a capital letter. Common examples include: 
    “PowerPoint” and “WikiPedia”, “GeeksForGeeks”, “CodeBlocks”, etc.
    
    Examples:
    
    Input: 
    dict[] = ["Hi", "Hello", "HelloWorld",  "HiTech", "HiGeek", 
    "HiTechWorld", "HiTechCity", "HiTechLab"]
    
    For pattern "HT",
    Output: ["HiTech", "HiTechWorld", "HiTechCity", "HiTechLab"]
    
    For pattern "H",
    Output: ["Hi", "Hello", "HelloWorld",  "HiTech", "HiGeek", 
        "HiTechWorld", "HiTechCity", "HiTechLab"]
    
    For pattern "HTC",
    Output: ["HiTechCity"]
    
    
    Input: 
    dict[] = ["WelcomeGeek","WelcomeToGeeksForGeeks", "GeeksForGeeks"]
    
    For pattern "WTG",
    Output: ["WelcomeToGeeksForGeeks"]
    
    For pattern "GFG",
    Output: [GeeksForGeeks]
    
    For pattern "GG",
    Output: No match found
    
    Solution:
    The idea is to insert all dictionary keys into the Trie one by one. Here 
    key refers to only Uppercase characters in original word in CamelCase 
    notation. If we encounter the key for the first time, then after marking 
    last node's isEndOfWord to true, we insert the complete word for that key 
    into the ArrayList associated with the last node. If we encounter a key 
    that is already in the Trie, we update the ArrayList associated with the 
    last node with current word. After all dictionary words are processed, we 
    search for the pattern in the Trie and store all words that matches the 
    pattern into a result list.
*/

public class CamelCasePatternMatching {
    
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        List<String> words;
        
        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            isEndOfWord = false;
            words = new ArrayList<String>();
        }
    }
    
    TrieNode root;
    
    public CamelCasePatternMatching() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        
        TrieNode current = root; // starting with root
        
        for (int index = 0; index < word.length(); index++) {
            
            char ch = word.charAt(index);
            
            // consider only uppercase characters
            if (Character.isLowerCase(ch))
                continue;
            
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        
        current.isEndOfWord = true; // mark end of word
        current.words.add(word); // add word to list
    }
    
    public List<String> getAllMatchingWords(List<String> dict, String pattern) {
        // first construct trie from given dictionary
        for (int index = 0; index < dict.size(); index++)
            insert(dict.get(index));
        
        // then find matching words for given pattern
        return search(pattern);
    }

    private List<String> search(String pattern) {
        TrieNode current = root; // starting with root
        List<String> resultList = new ArrayList<String>();
        for (int index = 0; index < pattern.length(); index++) {
            char ch = pattern.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                // pattern not present
                // so return empty list as result
                return resultList;
            }
            current = node;
        }
        
        // pattern is present in trie
        // so populate all words matching this pattern into result list
        populateResult(current, resultList);
        return resultList;
    }

    private void populateResult(TrieNode root, List<String> resultList) {
        // if current node is end of word
        // all words stored in its list are match
        if (root.isEndOfWord) {
            for (String word : root.words)
                resultList.add(word);
        }
        
        // recurse for all children of current node
        for (TrieNode node : root.children.values())
            populateResult(node, resultList);
    }

    public static void main(String[] args) {
        CamelCasePatternMatching trie = new CamelCasePatternMatching();
        List<String> dict = Arrays.asList("Hi", "Hello", "HelloWorld", "HiTech", 
                                "HiGeek", "HiTechWorld", "HiTechCity", "HiTechLab");
        
        List<String> resultList = trie.getAllMatchingWords(dict, "HT");
        for (String word : resultList)
            System.out.print(word + " ");
        // HiTech HiTechCity HiTechWorld HiTechLab
        System.out.println(); // new line
        
        CamelCasePatternMatching trie2 = new CamelCasePatternMatching();
        resultList = trie2.getAllMatchingWords(dict, "HTC");
        for (String word : resultList)
            System.out.print(word + " ");
        // HiTechCity
    }
}
