package advance_ds.trie;

import java.util.ArrayList;
import java.util.List;

// https://www.geeksforgeeks.org/implement-a-phone-directory/

/*
 * 	Problem: Implement a Phone Directory
 * 
	Given a list of contacts which exist in a phone directory. The task is to implement 
	search query for the phone directory. The search query on a string ‘str’ displays 
	all the contacts with prefix as ‘str’. When a user searches for a contact from the 
	contact list then suggestions (Contacts with prefix as the string entered so for) are  
	shown after user enters each character.
	
	Example:
	Input : contacts [] = {“gforgeeks” , “geeksquiz” }
	        Query String = “gekk”
	
	Output : Suggestions based on "g" are 
	         geeksquiz
	         gforgeeks
	
	         Suggestions based on "ge" are 
	         geeksquiz
	
	         No Results Found for "gek" 
	
	         No Results Found for "gekk" 
	         
	 Solution:
	 Phone Directory can be efficiently implemented using Trie Data Structure. We insert 
	 all the contacts into Trie.

	 Generally search query on a Trie is to determine whether the string is present in 
	 the trie or not, but in this case we are supposed to find all the matching strings 
	 with given prefix. This is equivalent to doing a DFS traversal on a graph. From a 
	 Trie node, visit adjacent Trie nodes and do this recursively until there are no 
	 more adjacent Trie Nodes. This recursive function will take 3 arguments one as Trie 
	 Node which points to the current Trie Node being visited, second as List<String> 
	 resultList, to populate result strings, and third a String which stores the string 
	 found so far. Each Trie Node stores a boolean variable ‘isEndOfWord’ which is true 
	 if the node represents end of a contact(word).
 */
public class PhoneDirectory extends Trie {
	
	public List<String> searchContacts(String query) {
		List<String> resultList = new ArrayList<String>();
		TrieNode current = root; // starting with root
		String prefix = "";
		for (int index = 0; index < query.length(); index++) {
			char ch = query.charAt(index);
			prefix += ch;
			TrieNode node = current.children.get(ch);
			if (node == null) {
				// return empty list if no match is found
				return resultList;
			}
			current = node;
		}
		
		populateWordsIntoResultListUsingDFS(current, resultList, prefix);
		return resultList;
	}
	
	public void populateWordsIntoResultListUsingDFS(TrieNode root, List<String> resultList, 
													String prefix) {
		if (root.isEndOfWord)
			resultList.add(prefix);
		
		for (Character ch : root.children.keySet()) {
			populateWordsIntoResultListUsingDFS(root.children.get(ch), resultList, 
					prefix + Character.toString(ch));
		}
	}
	
	public void constructTrie(String[] input) {
		for (int i = 0; i < input.length; i++)
			insert(input[i]);
	}

	public static void main(String[] args) {
		PhoneDirectory trie = new PhoneDirectory();
		
        String[] contacts = {"gforgeeks", "geeksquiz"};
        trie.constructTrie(contacts);
        
        String query = "gekk";
        for (int i = 0; i < query.length(); i++) {
        	String currentQuery = query.substring(0, i+1);
        	System.out.println("Search result for input '" + currentQuery + "':");
        	for (String result : trie.searchContacts(currentQuery))
        		System.out.print(result + ", ");
        	System.out.println(); // new line after each result
        	
        	/*
        	 *  Search result for input 'g':
				geeksquiz, gforgeeks 
				Search result for input 'ge':
				geeksquiz, 
				Search result for input 'gek':
				
				Search result for input 'gekk':

        	 */
        }
	}
}
