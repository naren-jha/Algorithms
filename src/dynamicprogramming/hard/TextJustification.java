package dynamicprogramming.hard;

/**
 * @author Narendra Jha
 *
 * https://www.geeksforgeeks.org/word-wrap-problem-dp-19/
 * https://leetcode.com/problems/text-justification/
 * https://www.youtube.com/watch?v=RORuwHiblPc
 * 
 * https://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-006-introduction-to-algorithms-fall-2011/lecture-videos/lecture-20-dynamic-programming-ii-text-justification-blackjack/
 * CLRS: Problems: 15-4 Printing neatly
 * 
 * Note:
 * DP Solution is not accepted by Leetcode, as it expects greedy solution, 
 * which is not always the optimal solution to this problem.
 */

public class TextJustification {

	/*
	 * The greedy solution is to place as many words as possible in the first line. Then 
	 * do the same thing for the second line and so on until all words are placed. This 
	 * solution gives optimal solution for many cases, but doesn’t give optimal solution 
	 * in all cases. Here is a Greedy solution:
	 * https://leetcode.com/problems/text-justification/discuss/24876/Simple-Java-Solution
	 */
	
	/* 
	 * Abstract Idea:
	 * The brute-force approach would be to try every partition of the input string, i.e.,
	 * to check each word, for whether it can appear at the beginning of a line or not.
	 * this results in an exponential time solution, as we have 'n' words, and there 
	 * can be a partition or no partition for each word, so that gives T(n) = O(2^n)
	 * 
	 * So what we really want to know here is that, for an optimal solution, where does 
	 * the second line begin. We know that first line begins at first word, but where 
	 * does the second line begin. And once we know where second line begin, we need to 
	 * know where the third line begins, and so on.
	 * 
	 * For second line, we will have to examine all words after first word. Once we know
	 * where second line begins (lets say second line begins at some ith word), then for
	 * third line, we will have to examine all words after ith word. so in general we will 
	 * have to examine (n-i) words to find out where the next line begins, where 'i' is the 
	 * word number at which immediate previous line begins.
	 */
	
	/*
	 * Algorithm:
	 * 
	 * Badness - We define badness as cube of extra spaces required to justify a line. For 
	 * example if extra spaces required to justify a line = 2, then it adds 2^3 = 8 to the
	 * overall badness score. We want an optimal solution which has minimum badness.
	 * 
	 * 1. Declare and generate a line cost array lc[][]. lc[i][j] stores the 'badness' cost 
	 *    when words[i..j] are kept in one line, where 'badness' cost is cube of number of  
	 *    extra spaces required to justify the line.
	 *    
	 *    To generate 'badness' cost array, first we store number of extra spaces required 
	 *    to justify a line. i.e., lc[i][j] = number of extra spaces required to fit 
	 *    words[i..j] in one line, then we take cube of values.
	 *    Negative values are replaced with infinity to indicate that words[i..j] cannot
	 *    fit in one line, and therefore should not be considered while calculating the 
	 *    overall optimal solution.
	 *    Value for last line are made zero to prevent the arrangement of the last line 
	 *    from influencing the overall optimal solution.
	 *    
	 * 2. To find overall optimal solution to the problem, what we want is to optimally 
	 * 	  arrange words[1..n] throughout multiple lines.
	 *    
	 *    Our subproblem here is to arrange words[1..j] where j = 1,2,3...n
	 *    
	 *    For an optimal arrangement of words[1..j], Suppose we know that the last line, 
	 *    which ends at word j, begins with word i . The preceding lines, therefore, 
	 *    contain words[1...(i-1)]. In fact, they must contain an optimal arrangement of
	 *    words[1...(i-1)]
	 *    
	 *    Let c[j] be the cost of an optimal arrangement of words[1..j]. If we know that
	 *    the last line contains words[i..j], then c[j] = c[i-1] + lc[i][j].
	 *    As a base case, when we're computing c[1], we need c[0]. If we set c[0] = 0, 
	 *    then c[1] = lc[1][1], which is what we want.
	 *    But since we don't know for which value of 'i' c[j] will be minimum, so we try 
	 *    all values of 'i' from 1 to 'j', and select the one for which c[j] is minimum.
	 *    We also store the selected value of 'i' in another array p[], so that we can 
	 *    reconstruct the optimal solution later.
	 */
	
	private int MAX = Integer.MAX_VALUE;
	
	// T(n): O(n^2), S(n): O(n^2)
	public void justifyText(String[] words, int maxWidth) {
		// 1.
		int n = words.length;
		int[][] lc = new int[n+1][n+1];
		
		for (int i = 1; i <= n; i++) {
			lc[i][i] = maxWidth - words[i-1].length();
			for (int j = i+1; j <= n; j++) {
				lc[i][j] = lc[i][j-1] - (1 + words[j-1].length()); // one for space
			}
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (lc[i][j] < 0)
					lc[i][j] = MAX;
				else if (j == n)
					lc[i][j] = 0;
				else
					lc[i][j] = lc[i][j] * lc[i][j] * lc[i][j];
			}
		}
		
		// 2.
		int[] c = new int[n+1];
		int[] p = new int[n+1];
		
		c[0] = 0;
		for (int j = 1; j <= n; j++) {
			c[j] = MAX;
			for (int i = 1; i <= j; i++) {
				if (lc[i][j] != MAX && (c[i-1] + lc[i][j] < c[j])) {
					c[j] = c[i-1] + lc[i][j];
					p[j] = i;
				}
			}
		}
		
		System.out.println("Minimum badness cost is: " + c[n] + "\n");
		printLines(words, p, maxWidth, n);
	}
	
	private void printLines(String[] words, int[] p, int maxWidth, int j) {
		// Base case:
		if (j == 0)
			return;
		
		int i = p[j];
		printLines(words, p, maxWidth, i-1);
		
		String line = constructLine(words, maxWidth, i, j);
		System.out.println(line);
	}

	private String constructLine(String[] words, int maxWidth, int i, int j) {
		StringBuilder builder = new StringBuilder();
		
		// if it is last line or number of words in the line is 1,
		// then left-justified, else center-justified
		if (j == words.length || i-1 == j-1) {
			for (int k = i-1; k < j; k++)
				builder.append(words[k]).append(" ");
			builder.deleteCharAt(builder.length() - 1); // remove last space
			
			for (int k = builder.length(); k < maxWidth; k++)
	            builder.append(" ");
		}
		else {
			int charCount = -1;
			for (int k = i-1; k < j; k++)
				charCount += words[k].length() + 1;
			
			int gapCount = j-i;
			
			// calculate fixed number of spaces required for each word
			int spaces = (maxWidth - charCount) / gapCount;
			
			// calculate additional spaces required to be distributed 
			// to first few words
	        int r = (maxWidth - charCount) % gapCount;
	        
	        for (int k = i-1; k < j; k++) {
	            builder.append(words[k]);
	            if (k < j - 1) {
	                for (int s = 0; s <= spaces; s++)
	                    builder.append(" ");
	                if (r != 0) {
	                	builder.append(" ");
	                	r--;
	                }
	            }
	        }
		}
		return builder.toString();
	}
	
	/*
	 * Improvements:
	 * 1.
	 * Time and space complexities of above approach is O(n^2). We can do a bit better 
	 * than O(n^2). We can get both the time and space down to O(n*maxWidth).
	 * The key observation is that at most ceil(maxWidth/2) words can fit on a line. 
	 * (Each word is at least one character long, and there's a space between words.)
	 * 
	 * Since a line with words[i..j] contains j-i+1 words, if j-i+1 > ceil(maxWidth/2)
	 * then we know that lc[i][j] = infinity. We need only compute and store lc[i][j]
	 * for j-i+1 <= ceil(maxWidth/2). And the inner for loop in the computation of
	 * c[j] and p[j] can run from max(1, j - ceil(maxWidth/2) + 1) to j
	 * 
	 * 2.
	 * We can reduce the space even further to O(n). We do so by not storing the lc
	 * table, and instead computing the value of lc[i][j] as needed in the last loop.
	 * The idea is that we could compute lc[i][j] in O(1) time if we 
	 * knew the value of spaces required to justify. And if we scan for the minimum 
	 * value in descending order of i, we can compute that as 
	 * lc[i][j] = lc[i+1][j] - (1 + words[j-1].length())
	 * (Initially, lc[j][j] = maxWidth - words[j-1]) This improvement reduces the space
	 * to O(n), since now the only tables we store are c and p.
	 */

	public static void main(String[] args) {
		String[] words = {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
		int maxWidth = 20;
		
		new TextJustification().justifyText(words, maxWidth);
		// Minimum badness cost is: 149
		/*
		 * Science    is   what
		 * we  understand  well
		 * enough to explain to
		 * a  computer.  Art is
		 * everything  else  we
		 * do 
		 */
	}
}
