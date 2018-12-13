package advance_ds.trie;

/**
 * @author Narendra Jha
 * 
 * Trie Data Structure Implementation - using array instead of HashMap
 * 
 * This implementation assumes that input words contains only 
 * lower case English alphabet letters 'a' through 'z'
 */
public class Trie2 {
	
	// Alphabet size (# of symbols) 
	private final int ALPHABET_SIZE = 26;
	
	private class TrieNode {
		TrieNode[] children;
		boolean isEndOfWord;
		
		public TrieNode() {
			children = new TrieNode[ALPHABET_SIZE];
			isEndOfWord = false;
		}
		
		public boolean isEmpty() {
			for (TrieNode node : children) {
				if (node != null)
					return false;
			}
			return true;
		}
	}
	
	private final TrieNode root;
	
	public Trie2() {
		root = new TrieNode();
	}
	
	// Iterative implementation of insert into Trie
	public void insert(String word) {
		TrieNode current = root; // starting with root
		int pos;
		for (int index = 0; index < word.length(); index++) {
			pos = word.charAt(index) - 'a';
			if (current.children[pos] == null) {
				current.children[pos] = new TrieNode();
			}
			current = current.children[pos];	
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
		
		int pos = word.charAt(index) - 'a';
		if (current.children[pos] == null) {
			current.children[pos] = new TrieNode();
		}
		insertRecursive(current.children[pos], word, index + 1);
	}
	
	// Iterative implementation of search into Trie
	public boolean search(String word) {
		TrieNode current = root; // starting with root
		int pos;
		for (int index = 0; index < word.length(); index++) {
			pos = word.charAt(index) - 'a';
			if (current.children[pos] == null) {
				return false;
			}
			current = current.children[pos];
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
		
		int pos = word.charAt(index) - 'a';
		if (current.children[pos] == null) {
			return false;
		}
		return searchRecursive(current.children[pos], word, index + 1);
	}
	
	// Prefix based search - iterative implementation
	public boolean searchPrefix(String prefix) {
		TrieNode current = root; // starting with root
		int pos;
		for (int index = 0; index < prefix.length(); index++) {
			pos = prefix.charAt(index) - 'a';
			if (current.children[pos] == null) {
				return false;
			}
			current = current.children[pos];
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
				return current.isEmpty();
			}
			else {
				// word does not exist, so we can not delete it
				// so we will not remove mapped children
				return false;
			}
			
		}
		
		int pos = word.charAt(index) - 'a';
		if (current.children[pos] == null) {
			// word does not exist, so we can not delete it
			// so we will not remove mapped children
			return false;
		}
		boolean shouldDeleteChildren = delete(current.children[pos], word, index + 1);
		
		if (shouldDeleteChildren) {
			// if true is returned then delete mapped children from array
			current.children[pos] = null;
			
			// delete current node if doesn't have any other children
			return current.isEmpty();
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
		
		int pos = word.charAt(index) - 'a';
		if (current.children[pos] == null) {
			return false;
		}
		boolean shouldDeleteChildren = deletePrefix(current.children[pos], word, index + 1);
		
		if (shouldDeleteChildren) {
			current.children[pos] = null;
			return current.isEmpty();
		}
		
		return false;
	}

	public static void main(String[] args) {
		Trie2 trie = new Trie2();
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
