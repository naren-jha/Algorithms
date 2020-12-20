package dynamicprogramming.intermediate;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;

// https://www.geeksforgeeks.org/box-stacking-problem-dp-22/
// https://youtu.be/kLucR6-Q0GA
// https://youtu.be/9mod_xRB-O0

public class BoxStackingProblem {

    // This problem is a variation of LIS problem
    
    /*
     * Width and depth forms the base area of a box.
     * 
     * But since we can use multiple instances of each box, we can rotate boxes to get different base areas.
     * For each box, there are only 3 different unique base areas possible by rotation. They are as below:
     * 
     * (w, d) : use width and depth to form base area
     * (w, h) : use width and height to form base area
     * (d, h) : use depth and height to form base area
     * 
     * The last (remaining) dimension will become height of the box. So exact configurations will be
     * (w, d), h
     * (w, h), d
     * (d, h), w
     * 
     * The other combinations possible by rotations will basically repeat the same base area, 
     * which are of no use for us.
     * 
     * Then since we want to find the largest possible stack height, we would want to keep boxes with larger 
     * area to the bottom of stack and boxes with smaller area to the top. 
     * And to do that, we'll sort the boxes in decreasing order of their areas.
     * 
     * But comparison based on just area is not sufficient to tell that dimensions of a box is 
     * strictly less than the other.
     * 
     * For example
     * Box1: w = 100, d = 2
     * Box2: w = 10, d = 5
     * Area of box1 is bigger than box2, but dimensions are not strictly lesser (5 > 2), 
     * so that means we cannot place box2 on top of box1
     * 
     * To check the dimensions correctly, we'll use the concept of LIS (or LDS)
     * 
     * One other thing is, while creating boxes with different orientations, we'll use larger of the
     * 2 base dimensions as width and smaller as depth. This is to make sure that 
     * when 2 boxes are compared (during LIS), both has larger dimension as their width and smaller
     * dimension as their depth, so that larger value is compared against larger value and smaller
     * value is compared against smaller value.
     * 
     * For example:
     * Box1: w = 15, d = 10, area = 150
     * Box2: w = 8, d = 12, area = 96
     * When we try to place box2 on top of box1, we won't be able to do so (as 12 > 10).
     * 
     * But if we take larger value as width and smaller value as depth
     * Box1: w = 15, d = 10, area = 150
     * Box2: w = 12, d = 8, area = 96
     * Now we'll be able to place box2 on top of box1
     * 
     * So if we didn't take larger value as width and smaller value as depth, 
     * then we'll get incorrect result in this case.
     * 
     * We don't necessarily have to take larger of the two as width and smaller of the two as depth
     * we can take it other way around as well, i.e., smaller of the two as width and larger of the
     * two as depth. It just has to be consistent for all the boxes.
     */
    
    private class Box {
        int w, d, h; // width, height, and depth
        int area;
        
        Box(int w, int d, int h) {
            this.w = w;
            this.d = d;
            this.h = h;
            area = w * d;
        }
    }
    
    public int maxStackHeight(Box[] b) {
        int n = b.length;
        Box[] boxes = new Box[3*n]; // stores three different orientations of the original boxes
        
        // generate different orientations of the original boxes
        for (int i = 0; i < n; i++) {
            boxes[3*i] = new Box(max(b[i].w, b[i].d), min(b[i].w, b[i].d), b[i].h);
            boxes[3*i + 1] = new Box(max(b[i].w, b[i].h), min(b[i].w, b[i].h), b[i].d);
            boxes[3*i + 2] = new Box(max(b[i].d, b[i].h), min(b[i].d, b[i].h), b[i].w);
        }
        
        Arrays.sort(boxes, (b1, b2) -> b2.area - b1.area); // sort in decreasing order of area
        
        n *= 3;
        int[] lis = new int[n]; // lis[i] stores max height stack ending at boxes[i]
        lis[0] = boxes[0].h;
        
        int maxHeight = 0;
        for (int i = 1; i < n; ++i) {
            lis[i] = boxes[i].h;
            for (int j = i-1; j >= 0; --j) {
                if (boxes[i].w < boxes[j].w && boxes[i].d < boxes[j].d)
                    lis[i] = max(lis[i], lis[j] + boxes[i].h);
            }
            
            maxHeight = max(maxHeight, lis[i]);
        }
        
        return maxHeight;
    }
    
    public static void main(String[] args) {
        BoxStackingProblem solver = new BoxStackingProblem();
        Box[] b = new Box[4]; 
        b[0] = solver.new Box(4, 6, 7); 
        b[1] = solver.new Box(1, 2, 3); 
        b[2] = solver.new Box(4, 5, 6); 
        b[3] = solver.new Box(10, 12, 32);
        
        System.out.println(solver.maxStackHeight(b)); // 60
    }
}
