package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-palindromic-subsequence-given-string/

public class CountAllPalindromicSubsequence {

    /*
     * If first and last characters are same, then we consider it as palindrome
     * subsequence and check for the rest of the subsequence (i+1, j), (i, j-1)
     * http://disq.us/p/1m6afhu
     * 
     * else, check for rest of the subsequence and  remove common palindromic 
     * subsequences as they are counted twice when we do 
     * countPS(i+1, j) + countPS(i,j-1)
     */
    
    /*
     * Explanation:
     * Case 1
        Suppose our string looks like this A..............A. 
        Lets say A at the front is at ith position and A at the end is at jth position
        
        Since str[i] == str[j]
        Let countPS(i+1, j-1) = x no of palindromic subsequences in string str between i+1 and j-1
        
        Now if we append and prepend A on both sides of each of these x palindromic subsequences 
        we get new x no of palindromic subsequences in range i to j which are different from 
        previous x no of subsequences between i+1 and j-1.
        
        Till now the number of subsequences we have between i and j = (x + x + 1)
        (1 because of subsequences AA)
        
        We now need to consider subsequences coming from
        
        countPS(i+1, j) (here x no of subsequence are repeatedly counted)
        countPS(i, j-1) (here x no of subsequence are repeatedly counted)
        
        Since x no of subsequence are repeatedly counted in both of these combinations, we'll have to subtract repetition
        So the total number of palindromic subsequence we get is -
        
        (countPS(i+1, j) - x) + (countPS(i, j-1) -x) + (x + x + 1)
        = countPS(i+1, j) + countPS(i, j-1) + 1
        
        So though we need to consider the contribution of countPS(i+1, j-1) or x but it gets cancelled. 
        Thats why the above equation is correct.
        
       Case 2
        Suppose our string looks like this A..............B. 
        Lets say A at the front is at ith position and B at the end is at jth position. A != B.
        
        Since str[i] != str[j]
        Let countPS(i+1, j-1) = x be the no of palindromic subsequences in string str between i+1 and j-1
        
        Now we don't get new x no of palindromic subsequences in range i to j by appending A on both sides 
        of previous x no of subsequences between i+1 and j-1 because A != B.
        
        So now x will be added only once and has to be subtracted twice.
        So we see it as a negative term in the statement.
        
        The total number of palindromic subsequence in this case we get is -
        
        (countPS(i+1, j) - x) + (countPS(i, j-1) -x) + x
        = countPS(i+1, j) + countPS(i, j-1) - x
        = countPS(i+1, j) + countPS(i, j-1) - countPS(i+1, j-1)
     */
    
    class SimpleRecursiveSolution {
        // T(n): Exp
        public int countPS(String s) {
            return countPS(s.toCharArray(), 0, s.length()-1);
        }
        
        private int countPS(char[] s, int from, int to) {
            if (from == to)
                return 1;
            if (from > to)
                return 0;
            
            if (s[from] == s[to])
                return countPS(s, from, to-1) + countPS(s, from+1, to) + 1;
            return countPS(s, from, to-1) + countPS(s, from+1, to) - countPS(s, from+1, to-1);
        }
    }
    
    class DPSolution {
        // Bottom-up tabulation
        // T(n): O(n^2), S(n): O(n^2)
        public int countPS(String s) {
            int n = s.length();
            char[] c = s.toCharArray();
            
            // create a 2D array to store intermediate results
            // where res[i][j] indicates no. of LPS for s[i, j]
            int[][] res = new int[n][n];
            
            for (int i = n-1; i >= 0; i--) {
                // starting from j = i will also work fine, as lower diagonal 
                // of the res[][] matrix is not utilized in the solution
                for (int j = 0; j < n; j++) {
                    if (i == j)
                        res[i][j] = 1;
                    else if (i > j)
                        res[i][j] = 0;
                    else if (c[i] == c[j])
                        res[i][j] = res[i][j-1] + res[i+1][j] + 1;
                    else
                        res[i][j] = res[i][j-1] + res[i+1][j] - res[i+1][j-1];
                }
            }
            return res[0][n-1];
        }
    }
    
    public static void main(String[] args) {
        CountAllPalindromicSubsequence o = new CountAllPalindromicSubsequence();
        String s = "abcb";
        System.out.println(o.new SimpleRecursiveSolution().countPS(s)); // 6
        System.out.println(o.new DPSolution().countPS(s)); // 6
    }
}
