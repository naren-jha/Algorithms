package basic.stack.problems;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Program for merging overlapping intervals
 */

// template for interval. We can use a 2-D array as well (arr[][2]).
class Interval {
    int start, end;
    
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class MergeOverlapingIntervals {
    
    // merges overlapping intervals and prints results
    public static void mergeIntervals(Interval[] arr) {
        int n = arr.length;
        
        // test if the given set has at least one interval
        if (n == 0)
            return;
        
        // take an empty stack of intervals
        Stack<Interval> s = new Stack<Interval>();
        
        // sort given set of intervals by their start time
        Arrays.sort(arr, (a, b) -> a.start - b.start);
        
        // push the first interval to stack
        s.push(arr[0]);
        
        // loop from next interval onwards and merge if necessary
        for (int i = 1; i < n; i++) {
            Interval top = s.peek();
            
            // if current interval is not overlapping with stack top,
            // push it to the stack
            if (top.end < arr[i].start)
                s.push(arr[i]);
            
            // otherwise update the ending time of top if
            // ending of current interval is greater
            else if (top.end < arr[i].end) {
                top.end = arr[i].end;
                s.pop();
                s.push(top);
            }
        }
        
        // print result
        System.out.println("Merged intervals are: ");
        while (!s.isEmpty()) {
            Interval top = s.pop();
            System.out.print("[" + top.start + ", " + top.end + "] ");
        }
            
    }
    
    // merges overlapping intervals and prints results
    // does not use extra space as stack
    public static void mergeIntervalsWithoutStack(Interval[] arr) {
        int n = arr.length;
        
        // test if the given set has at least one interval
        if (n == 0)
            return;
        
        // sort intervals in descending order of their start time
        Arrays.sort(arr, (a, b) -> b.start - a.start);
        
        // Stores index of last element in output array (modified arr[])
        int index = 0;
        
        // traverse through input intervals
        for (int i = 0; i < n; i++) {
            
            // if this is not the first interval
            // and overlaps with the previous one
            if (i != 0 && arr[index-1].start <= arr[i].end) {
                do {
                    arr[index-1].start = arr[i].start;
                    arr[index-1].end = Math.max(arr[index-1].end, arr[i].end);
                    index--;
                } while (index != 0 && arr[index-1].start <= arr[i].end);
            }
            
            // else if this is the first interval, 
            // or it doesn't overlap with previous one.
            else {
                arr[index] = arr[i];
            }
            
            index++;
        }
        
        // Now arr[0..index-1] stores the merged Intervals
        System.out.println("Merged intervals are: ");
        for (int i = 0; i < index; i++) {
            System.out.print("[" + arr[i].start + ", " + arr[i].end + "] ");
        }
    }

    public static void main(String[] args) {
        Interval[] intervals = {
                new Interval(6, 8),
                new Interval(1, 9),
                new Interval(2, 4),
                new Interval(4, 7)
        };
        
        mergeIntervalsWithoutStack(intervals);
        /*
         * Output:
         * Merged intervals are: 
         * [1, 9] 
         */
    }

}
