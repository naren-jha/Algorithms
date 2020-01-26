package basic.stack;

import java.util.Stack;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 *
 * Program for Tower of Hanoi
 */
public class TowerOfHanoi {
    
    // recursive method to solve Tower of Hanoi puzzle
    public static void towerOfHanoi(int n, char fromRod, char toRod, char auxRod) {
        // base case
        if (n == 1) {
            System.out.println("Move disk 1 from rod " + fromRod + " to rod " + toRod);
            return;
        }
        towerOfHanoi(n-1, fromRod, auxRod, toRod);
        System.out.println("Move disk " + n + " from rod " + fromRod + " to rod " + toRod);
        towerOfHanoi(n-1, auxRod, toRod, fromRod);
    }
    
    // iterative method to solve Tower of Hanoi puzzle
    public static void towerOfHanoi(int n, Stack<Integer> src, Stack<Integer> aux,
                                            Stack<Integer> dest) {
        // calculate total number of moves required
        int moves = (int) (Math.pow(2, n) - 1);
        
        // take three poles to show movements
        char s = 'S', a = 'A', d = 'D';
        
        // if number of disks is even, then interchange
        // destination pole and auxiliary pole
        if (n % 2 == 0) {
            a = 'D';
            d = 'A';
        }
        
        // perform legal movements one by one
        for (int i = 1; i <= moves; i++) {
            if (i % 3 == 1)
                moveDisksBetweenTwoPoles(src, dest, s, d);
            else if (i % 3 == 2)
                moveDisksBetweenTwoPoles(src, aux, s, a);
            else if (i % 3 == 0)
                moveDisksBetweenTwoPoles(aux, dest, a, d);
        }
    }
    
    // method to perform legal movement between two poles
    public static void moveDisksBetweenTwoPoles(Stack<Integer> pole1, 
                                    Stack<Integer> pole2, char s, char d) {
        if (pole1.isEmpty() && pole2.isEmpty())
            throw new IllegalStateException("empty poles");
        
        if (pole1.isEmpty()) {
            int disk = pole2.pop();
            pole1.push(disk);
            moveDisk(disk, d, s);
        }
        else if (pole2.isEmpty()) {
            int disk = pole1.pop();
            pole2.push(disk);
            moveDisk(disk, s, d);
        }
        else if (pole1.peek() > pole2.peek()) {
            int disk = pole2.pop();
            pole1.push(disk);
            moveDisk(disk, d, s);
        }
        else if (pole2.peek() > pole1.peek()) {
            int disk = pole1.pop();
            pole2.push(disk);
            moveDisk(disk, s, d);
        }
    }
    
    // method to show the movement of disks
    public static void moveDisk(int disk, char fromPole, char toPole) {
        System.out.println("Move disk " + disk + " from pole " + 
                                    fromPole + " to pole " + toPole);
    }

    public static void main(String[] args) {
        int n = 3; // number of disks
        Stack<Integer> src = new Stack<Integer>();
        Stack<Integer> aux = new Stack<Integer>();
        Stack<Integer> dest = new Stack<Integer>();
        
        // put all disks in src pole
        for (int i = n; i >= 1; i--)
            src.push(i);
        
        towerOfHanoi(n, src, aux, dest);
        /*
         * Output:
         * Move disk 1 from pole S to pole A
         * Move disk 2 from pole S to pole D
         * Move disk 1 from pole A to pole D
         * Move disk 3 from pole S to pole A
         * Move disk 1 from pole D to pole S
         * Move disk 2 from pole D to pole A
         * Move disk 1 from pole S to pole A
         * Move disk 4 from pole S to pole D
         * Move disk 1 from pole A to pole D
         * Move disk 2 from pole A to pole S
         * Move disk 1 from pole D to pole S
         * Move disk 3 from pole A to pole D
         * Move disk 1 from pole S to pole A
         * Move disk 2 from pole S to pole D
         * Move disk 1 from pole A to pole D
         */
    }
    
    // public static void main(String[] args) {
    //     int n = 4; // number of disks
    //     towerOfHanoi(n, 'A', 'C', 'B');
        
        /* Output:
         * Move disk 1 from rod A to rod B
         * Move disk 2 from rod A to rod C
         * Move disk 1 from rod B to rod C
         * Move disk 3 from rod A to rod B
         * Move disk 1 from rod C to rod A
         * Move disk 2 from rod C to rod B
         * Move disk 1 from rod A to rod B
         * Move disk 4 from rod A to rod C
         * Move disk 1 from rod B to rod C
         * Move disk 2 from rod B to rod A
         * Move disk 1 from rod C to rod A
         * Move disk 3 from rod B to rod C
         * Move disk 1 from rod A to rod B
         * Move disk 2 from rod A to rod C
         * Move disk 1 from rod B to rod C
         */
    // }

}
