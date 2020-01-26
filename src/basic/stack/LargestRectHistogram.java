package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Program to calculate largest rectangle in a histogram
 */
public class LargestRectHistogram {
    
    public static int getLargestRectangleInHistoram(int[] hist) {
        int maxArea = 0, area;
        
        // create an empty stack to hold indexes of hist[] array
        // the bars stored in stack will always be in increasing 
        // order of their heights
        Stack<Integer> s = new Stack<Integer>();
        
        // loop through all bars of given histogram
        int i = 0;
        while (i < hist.length) {
            // if stack is empty or this bar is bigger than the bar
            // at top of stack, then push this bar index to stack
            if (s.isEmpty() || hist[i] >= hist[s.peek()]) {
                s.push(i);
                i++;
            }
            
            // else if stack is not empty and this bar is smaller than
            // the bar at top of stack, then calculate area of rectangle
            // with stack top as the smallest (or minimum height) bar.
            else {
                int top = s.pop();
                
                // if stack is empty, it means everything till i
                // has to be greater than or equal to hist[top]
                // so get area by hist[top] * i;
                if (s.isEmpty()) {
                    area = hist[top] * i;
                }
                
                // if stack is not empty then everything from (s.peek() + 1)
                // to i-1 has to be greater than or equal to hist[top]
                // so area = hist[top] * (i - s.peek() - 1)
                else {
                    area = hist[top] * (i - s.peek() - 1);
                }
                
                // update max area if required
                if (area > maxArea)
                    maxArea = area;
            }
        }
        
        // now pop remaining bars from stack and calculate
        // area with every popped bar as the smallest bar
        while (!s.isEmpty()) {
            int top = s.pop();
            
            // if stack is empty, it means everything till i
            // has to be greater than or equal to hist[top]
            // so get area by hist[top] * i;
            if (s.isEmpty()) {
                area = hist[top] * i;
            }
            
            // if stack is not empty then everything from (s.peek() + 1)
            // to i-1 has to be greater than or equal to hist[top]
            // so area = hist[top] * (i - s.peek() - 1)
            else {
                area = hist[top] * (i - s.peek() - 1);
            }
            
            // update max area if required
            if (area > maxArea)
                maxArea = area;
        }
        
        return maxArea;
    }
    
    // naive approach 
    public static int getLargestRectangleArea(int[] hist) {
        int maxArea = 0;
        for (int i = 0; i < hist.length; i++) {
            int minHeight = hist[i];
            for (int j = i; j < hist.length; j++) {
                minHeight = Math.min(minHeight, hist[j]);
                int area = minHeight * (j - i + 1);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int hist[] = { 2, 1, 5, 6, 2, 3 };
        System.out.println(getLargestRectangleArea(hist)); // 10
    }

}
