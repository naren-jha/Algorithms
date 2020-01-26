package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Print next greater element 
 * for each element of a given array
 */
public class NextGreaterElement {
    
    public static void printNGE2(int[] a) {
        int n = a.length;
        int nge;
        for (int i = 0; i < n; i++) {
            nge = -1;
            for (int j = i+1; j < n; j++) {
                if (a[j] > a[i]) {
                    nge = a[j];
                    break;
                }
            }
            System.out.println(a[i] + " ---> " + nge);
        }
    }
    
    public static void printNGE(int[] a) {
        int n = a.length;
        Stack<Integer> s = new Stack<Integer>();
        // push first element to stack
        s.push(a[0]);
        
        // iterate through rest of the elements
        for (int i = 1; i < n; i++) {
            // while stack is not empty and 
            // current element is bigger than stack top,
            // keep printing results
            while (!s.isEmpty() && a[i] > s.peek())
                System.out.println(s.pop() + " ---> " + a[i]);
            
            // push current element to stack
            s.push(a[i]);
        }
        
        // print NGE as -1 for elements having no NGE
        while (!s.isEmpty())
            System.out.println(s.pop() + " ---> " + -1);
    }

    public static void main(String[] args) {
        int arr[]= {3, 7, 1, 5, 13, 6, 4, 12};
        printNGE(arr);
        /* Output: 
         * 3 ---> 7
         * 1 ---> 5
         * 5 ---> 13
         * 7 ---> 13
         * 4 ---> 12
         * 6 ---> 12
         * 12 ---> -1
         * 13 ---> -1
         * */
    }

}
