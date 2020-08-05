package basic.stack.problems;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Find celebrity in a party
 */
public class Party {
    
    /* matrix below is used to simulate 
     * hypothetical function haveAcquaintance [knows()]
     */
    
    /* we are taking a party of 4 person. In this party, 
     * person with id 2 is the celebrity
     */
    private static int[][] matrix = { {0, 0, 1, 0},
                                          {0, 0, 1, 0},
                                          {0, 0, 0, 0},
                                          {0, 0, 1, 0} };
    
    
    // returns true if a knows b, otherwise returns false
    public static boolean knows(int a, int b) {
        return matrix[a][b] == 1;
    }
    
    /**
     * @param n is the number of person in the party
     * 
     * finds and returns celebrity id (celebrity id will range 
     * from 0 to n-1). returns -1 if celebrity is not found.
     */
    public static int findCelebrity(int n) {
        Stack<Integer> s = new Stack<Integer>();
        
        // Step 1: Push everyone into a stack
        for (int i = 0; i < n; i++)
            s.push(i);
        
        while (s.size() > 1) {
            // Step 2: Pop off top two persons from the 
            // stack, discard one person based on return
            // status of knows(A, B).
            int a = s.pop();
            int b = s.pop();
            
            // Step 3: Push the possible celebrity back to stack
            if (knows(a, b))
                s.push(b);
            else
                s.push(a);
        }
        int c = s.pop();
        
        // Step 5: Check if the last person in the stack 
        // is actually a celebrity or not.
        for (int i = 0; i < n; i++) {
            // if c knows any person other than himself,
            // or, if any other person in the party doesn't know c
            // then there is no celebrity in the party, so return -1
            if (i != c && (knows(c, i) || !knows(i, c)))
                return -1;
        }
        return c;
    }
    
    // an improved version - with less number of push and pops
    public static int findCelebrity2(int n) {
        Stack<Integer> s = new Stack<Integer>();
        
        for (int i = 0; i < n; i++)
            s.push(i);
        
        int c = s.pop();
        while (!s.isEmpty()) {
            int b = s.pop();
            if (knows(c, b))
                c = b;
        }
        
        for (int i = 0; i < n; i++) {
            if (i != c && (knows(c, i) || !knows(i, c)))
                return -1;
        }
        return c;
    }
    
    // solution using two pointers
    public static int findCelebrity3(int n) {
        // Initialize two pointers to the 
        // beginning and end of the loop
        int a = 0;
        int b = n - 1;
        
        // keep moving till both pointers 
        // don't become same
        while (a < b) {
            if (knows(a, b))
                a++;
            else
                b--;
        }
        
        // check if 'a' is actually a celebrity or not
        for (int i = 0; i < n; i++) {
            // if 'a' knows any person other than himself,
            // or, if any other person in the party doesn't know 'a'
            // then there is no celebrity in the party, so return -1
            if (i != a && (knows(a, i) || !knows(i, a)))
                return -1;
        }
        return a;
    }
    
    // without using two pointers
    public static int findCelebrity4(int n) {
        int c = 0;
        for (int i = 1; i < n; i++) {
            if (knows(c, i))
                c = i;
        }
        
        for (int i = 0; i < n; i++) {
            if (i != c && (knows(c, i) || !knows(i, c)))
                return -1;
        }
        return c;
    }
    
    // solution using recursion
    public static int celebrity(int n) {
        // base case: when there is just one person
        // then that person is the celebrity
        if (n == 1)
            return 0;
        
        int c = celebrity(n-1);
        
        // if we have a celebrity from previous step of recursion
        if (c != -1) {
            // check if celebrity from previous step is still the 
            // celebrity including this step of recursion
            if (!knows(c, n-1) && knows(n-1, c))
                return c;
        }
        
        // when we don't have a celebrity from previous step,
        // or when we have a celebrity from previous step, but that 
        // person is no more the celebrity including this step,
        // then check if person added at this step of recursion 
        // is the celebrity or not
        for (int i = 0; i < n-1; i++) {
            if (knows(n-1, i) || !knows(i, n-1))
                return -1;
        }
        return n-1;
    }

    public static void main(String[] args) {
        int n = 4;
        int result = celebrity(n);
        if (result == -1)
            System.out.println("No Celebrity");
        else
            System.out.println("Celebrity ID " + result);
    }

}
