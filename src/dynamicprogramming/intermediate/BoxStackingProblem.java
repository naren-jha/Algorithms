package dynamicprogramming.intermediate;

import java.util.Arrays;

// https://www.geeksforgeeks.org/box-stacking-problem-dp-22/

public class BoxStackingProblem {

    // This problem is a variation of LIS problem
    
    private class Box implements Comparable<Box> {
        int height, width, depth, area;
        
        Box(int height, int width, int depth) {
            this.height = height;
            this.width = width;
            this.depth = depth;
            area = width * depth;
        }
        
        // to sort in decreasing order
        @Override
        public int compareTo(Box box) {
            return box.area - this.area;
        }
    }
    
    public int maxStackHeight(Box[] b) {
        int n = b.length;
        // create an array to store three different rotations of the original boxes
        Box[] boxes = new Box[3*n];
        
        // generate all three rotations of original boxes
        for (int i = 0; i < n; i++) {
            boxes[3*i] = new Box(b[i].height, Math.max(b[i].width, b[i].depth), 
                                        Math.min(b[i].width, b[i].depth));
            boxes[3*i + 1] = new Box(b[i].width, Math.max(b[i].height, b[i].depth), 
                                        Math.min(b[i].height, b[i].depth));
            boxes[3*i + 2] = new Box(b[i].depth, Math.max(b[i].height, b[i].width), 
                                        Math.min(b[i].height, b[i].width));
        }
        Arrays.sort(boxes); // sort by area in decreasing order
        n *= 3;
        
        // create a result array to store intermediate results
        // where mhs[i] represents max height stack ending at boxes[i]
        int[] mhs = new int[n];
        
        // populate dp table
        mhs[0] = boxes[0].height;
        for (int i = 1; i < n; i++) {
            mhs[i] = boxes[i].height;
            for (int j = 0; j < i; j++) {
                if (boxes[j].width > boxes[i].width && boxes[j].depth > boxes[i].depth) {
                    if (mhs[i] < mhs[j] + boxes[i].height) {
                        mhs[i] = mhs[j] + boxes[i].height;
                    }
                }
            }
        }
        
        // get maximum from mhs[] array
        return Arrays.stream(mhs).max().getAsInt();
    }
    
    public static void main(String[] args) {
        BoxStackingProblem o = new BoxStackingProblem();
        Box[] b = new Box[4]; 
        b[0] = o.new Box(4, 6, 7); 
        b[1] = o.new Box(1, 2, 3); 
        b[2] = o.new Box(4, 5, 6); 
        b[3] = o.new Box(10, 12, 32);
        
        System.out.println(o.maxStackHeight(b));
    }
}
