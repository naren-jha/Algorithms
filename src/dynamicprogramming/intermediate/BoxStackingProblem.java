package dynamicprogramming.intermediate;

import static java.lang.Math.max;

import java.util.Arrays;

// https://www.geeksforgeeks.org/box-stacking-problem-dp-22/
// https://www.youtube.com/watch?v=9mod_xRB-O0

public class BoxStackingProblem {

    // This problem is a variation of LIS problem
    
    /*
     * Since each box is available in unlimited quantity, so same box can be rotated and 
     * used again in different orientation. So that means every box can be used in 3 different 
     * orientations. So we consider every orientations of each box and create a new array.
     * Then we sort the boxes[] array (with all orientations) by their base area in decreasing order
     * so that when we place boxes on top of one-another (while finding maximum height stack)
     * smaller area box always comes on top of a larger area box
     */
    
    private class Box {
        int height, width, depth, area;
        
        Box(int height, int width, int depth) {
            this.height = height;
            this.width = width;
            this.depth = depth;
            area = width * depth;
        }
    }
    
    public int maxStackHeight(Box[] b) {
        int n = b.length;
        // create an array to store three different rotations of the original boxes
        Box[] boxes = new Box[3*n];
        
        // generate all three rotations of original boxes
        for (int i = 0; i < n; i++) {
            boxes[3*i] = new Box(b[i].height, Math.max(b[i].width, b[i].depth), Math.min(b[i].width, b[i].depth));
            boxes[3*i + 1] = new Box(b[i].width, Math.max(b[i].height, b[i].depth), Math.min(b[i].height, b[i].depth));
            boxes[3*i + 2] = new Box(b[i].depth, Math.max(b[i].height, b[i].width), Math.min(b[i].height, b[i].width));
        }
        Arrays.sort(boxes, (b1, b2) -> b2.area - b1.area); // sort by area in decreasing order
        n *= 3;
        
        // create a result array to store intermediate results
        // where mhs[i] represents max height stack ending at boxes[i]
        int[] mhs = new int[n];
        
        // populate dp table
        mhs[0] = boxes[0].height;
        for (int i = 1; i < n; i++) {
            mhs[i] = boxes[i].height;
            for (int j = i-1; j >= 0; --j) {
                if (boxes[i].width < boxes[j].width && boxes[i].depth < boxes[j].depth)
                    mhs[i] = max(mhs[i], mhs[j] + boxes[i].height);
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
        
        System.out.println(o.maxStackHeight(b)); // 60
    }
}
