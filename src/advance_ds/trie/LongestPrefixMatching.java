package advance_ds.trie;

import advance_ds.trie.Trie;
import advance_ds.trie.Trie.TrieNode;

// https://www.geeksforgeeks.org/longest-prefix-matching-a-trie-based-solution-in-java/

/*
 * 	Given a dictionary of words and an input string, find the longest prefix
 	of the string which is also a word in dictionary.
	Examples:
	
	Let the dictionary contains the following words:
	{are, area, base, cat, cater, children, basement}
	
	Below are some input/output examples:
	--------------------------------------
	Input String            Output
	--------------------------------------
	caterer                 cater
	basemexy                base
	child                   <Empty>
 */

/*
 * 	Solution:
	We build a Trie of all dictionary words. Once the Trie is built, traverse through it 
	using characters of input string. If prefix matches a dictionary word, store current
	length and look for a longer match. Finally, return the longest match.
	Following is Java implementation
 */

public class LongestPrefixMatching {

	private Trie trie = new Trie();
	
	public String solution(String input) {
		TrieNode current = trie.root;
		StringBuilder res = new StringBuilder();
		int lastMatch = 0;
		for (int index = 0; index < input.length(); index++) {
			char ch = input.charAt(index);
			TrieNode node = current.children.get(ch);
			if (node != null) {
				res.append(ch);
				current = node;
				if (current.isEndOfWord)
					lastMatch = index + 1;
			}
			else
				break;
		}
		return res.toString().substring(0, lastMatch);
	}
	
	public static void main(String[] args) {
		LongestPrefixMatching obj = new LongestPrefixMatching();
		obj.trie.insert("are"); 
		obj.trie.insert("area"); 
		obj.trie.insert("base"); 
		obj.trie.insert("cat"); 
		obj.trie.insert("cater"); 
		obj.trie.insert("basement"); 
		
		System.out.println("result = " + obj.solution("caterer")); // cater
		System.out.println("result = " + obj.solution("basement")); // basement
		System.out.println("result = " + obj.solution("are")); // are
		System.out.println("result = " + obj.solution("arex")); // are
		System.out.println("result = " + obj.solution("basemexz")); // base
		System.out.println("result = " + obj.solution("xyz")); // <EMPTY_STRING>
	}

}
