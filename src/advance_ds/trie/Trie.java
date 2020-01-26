package advance_ds.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Trie Data Structure Implementation
 */
public class Trie {
    
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        
        public TrieNode() {
            children = new HashMap<Character, TrieNode>();
            isEndOfWord = false;
        }
    }
    
    TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Iterative implementation of insert into Trie
    public void insert(String word) {
        TrieNode current = root; // starting with root
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.isEndOfWord = true; // mark end of word
    }
    
    // Recursive implementation of insert into Trie
    public void insertRecursive(String word) {
        insertRecursive(root, word, 0);
    }

    private void insertRecursive(TrieNode current, String word, int index) {
        // exit condition
        if (index == word.length()) {
            current.isEndOfWord = true;
            return;
        }
        
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            node = new TrieNode();
            current.children.put(ch, node);
        }
        insertRecursive(node, word, index + 1);
    }
    
    // Iterative implementation of search into Trie
    public boolean search(String word) {
        TrieNode current = root; // starting with root
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfWord;
    }
    
    // Recursive implementation of search into Trie
    public boolean searchRecursive(String word) {
        return searchRecursive(root, word, 0);
    }

    private boolean searchRecursive(TrieNode current, String word, int index) {
        // exit condition
        if (index == word.length()) {
            return current.isEndOfWord;
        }
        
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        return searchRecursive(node, word, index + 1);
    }
    
    // Prefix based search - iterative implementation
    public boolean searchPrefix(String prefix) {
        TrieNode current = root; // starting with root
        for (int index = 0; index < prefix.length(); index++) {
            char ch = prefix.charAt(index);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return true;
    }
    
    // Deletes whole word from Trie
    public void delete(String word) {
        delete(root, word, 0);
    }

    // Returns true if parent should remove mapped children
    private boolean delete(TrieNode current, String word, int index) {
        // exit condition
        if (index == word.length()) {
            if (current.isEndOfWord) {
                // word exists, so we have to delete it
                
                current.isEndOfWord = false;
                // delete current node if it doesn't have any other children
                return current.children.size() == 0;
            }
            else {
                // word does not exist, so we can not delete it
                // so we will not remove children mapped
                return false;
            }
            
        }
        
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            // word does not exist, so we can not delete it
            // so we will not remove children mapped
            return false;
        }
        boolean shouldDeleteChildren = delete(node, word, index + 1);
        
        if (shouldDeleteChildren) {
            // if true is returned then delete the mapping of 
            // character and TrieNode reference from map
            current.children.remove(ch);
            
            // delete current node if doesn't have any other children
            return current.children.size() == 0;
        }
        
        return false;
    }
    
    // Deletes all words starting with given prefix
    public void deletePrefix(String word) {
        deletePrefix(root, word, 0);
    }

    // Returns true if parent should remove mapped children
    // The only difference from whole word delete is that when
    // exit condition is met, it simply returns true
    private boolean deletePrefix(TrieNode current, String word, int index) {
        // exit condition
        if (index == word.length()) {
            return true;
        }
        
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteChildren = deletePrefix(node, word, index + 1);
        
        if (shouldDeleteChildren) {
            current.children.remove(ch);
            return current.children.size() == 0;
        }
        
        return false;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("abgl");
        trie.insert("cdf");
        trie.insert("abcd");
        trie.insert("lmn");
        
        System.out.println(trie.search("ab")); // false
        System.out.println(trie.search("cdf")); // true
        System.out.println(trie.search("abc")); // true
        System.out.println(trie.search("abcd")); // true
        System.out.println(trie.search("lmn")); // true
        System.out.println(trie.search("abg")); // false
        System.out.println(trie.search("ghi")); // false
        
        System.out.println(trie.searchPrefix("ab")); // true
        System.out.println(trie.searchPrefix("abg")); // true
        System.out.println(trie.searchPrefix("lmk")); // false
        
        trie.delete("abc");
        trie.delete("lmn");
        
        System.out.println(trie.search("abc")); // false
        System.out.println(trie.search("abcd")); // true
        System.out.println(trie.search("lmn")); // false
        
        trie.deletePrefix("ab");
        System.out.println(trie.search("abcd")); // false
        System.out.println(trie.search("abgl")); // false
        System.out.println(trie.search("cdf")); // true
    }

}
