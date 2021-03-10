package advance_ds;

/**
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 * Implementation of Binary Indexed Tree / Fenwick Tree data structure
 */

public class BinaryIndexedTree {
    
    private int[] bit;
    
    BinaryIndexedTree(int[] input) {
        bit = new int[input.length + 1];
        
        for (int i = 0; i < input.length; i++) {
            update(input[i], i);
        }
    }

    public void update(int val, int index) {
        for (int i = index+1; i < bit.length; i += (i & -i))
            bit[i] += val;
    }
    
    public int sum(int index) {
        int sum = 0;
        for (int i = index+1; i > 0; i -= (i & -i)) 
            sum += bit[i];
        return sum;
    }

    public static void main(String[] args) {
        int[] input = {1, 2, 3, 4, 5, 6, 7};
        BinaryIndexedTree tree = new BinaryIndexedTree(input);
        System.out.println(tree.sum(0)); // 1
        System.out.println(tree.sum(1)); // 3
        System.out.println(tree.sum(2)); // 6
        System.out.println(tree.sum(3)); // 10
        System.out.println(tree.sum(4)); // 15
        System.out.println(tree.sum(5)); // 21
        System.out.println(tree.sum(6)); // 28
        
        // input[3] = 6 | change = (newValue - oldValue) = (6 - 4) = 2
        // which makes, input = {1, 2, 3, 6, 5, 6, 7} and
        // bit = {0, 1, 3, 3, 12, 5, 11, 7}
        tree.update(2, 3); 
        
        System.out.println(tree.sum(3)); // 12
        System.out.println(tree.sum(6)); // 30
    }

}
