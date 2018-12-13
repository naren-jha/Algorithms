package advance_ds.trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://www.geeksforgeeks.org/print-valid-words-possible-using-characters-array/

/*
 * 	Problem: Print all valid words that are possible using Characters of Array
 * 
	Given a dictionary and a character array, print all valid words that are 
	possible using characters from the array.
	
	Examples:
	
	Input : Dict - {"go","bat","me","eat","goal", "boy", "run"} 
	        arr[] = {'e','o','b', 'a','m','g', 'l'} 
	Output : go, me, goal.
	
	
	Solution:
	The idea is to use Trie data structure to store dictionary, then search words 
	in Trie using characters of given array.

	- Construct a Trie out of given dictionary words.
	- After that, we pick only those characters in ‘arr[]’ which are a child of the root of 
	  Trie.
	- For each character present in root's children, we populate result of valid words
	  using depth first search technique.
	- To quickly find whether a character is present in array or not, we create a hash of 
	  character arrays.
 */
public class PrintAllValidWords extends Trie {
	
	public List<String> getAllValidWords(char[] arr) {
		Set<Character> hash = new HashSet<Character>();
		for (char ch : arr)
			hash.add(ch);
		
		List<String> resultList = new ArrayList<String>();
		for (char ch : arr) {
			if (root.children.containsKey(ch)) {
				// populate result using depth first search
				populateWordsIntoResultListUsingDFS(root.children.get(ch), resultList, 
						Character.toString(ch), arr, hash);
			}
		}
		return resultList;
	}
	
	public void populateWordsIntoResultListUsingDFS(TrieNode root, List<String> resultList, 
			String currString, char[] arr, Set<Character> hash) {
		if (root.isEndOfWord)
			resultList.add(currString);
		
		for (Character ch : root.children.keySet()) {
			if (hash.contains(ch)) {
				populateWordsIntoResultListUsingDFS(root.children.get(ch), resultList, 
						currString + Character.toString(ch), arr, hash);
			}
		}
	}
	
	public void constructTrie(String[] input) {
		for (int i = 0; i < input.length; i++)
			insert(input[i]);
	}

	public static void main(String[] args) {
		PrintAllValidWords trie = new PrintAllValidWords();
		
        String[] dict = {"go", "bat", "me", "eat", "goal", "boy", "run"};
        trie.constructTrie(dict);
        
        char[] arr = {'e', 'o', 'b', 'a', 'm', 'g', 'l'};
        List<String> resultList = trie.getAllValidWords(arr);
        for (String word : resultList)
        	System.out.print(word + ", "); // me, go, goal, 
	}

}
