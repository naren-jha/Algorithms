package advance_ds;

/**
 * @author Narendra Jha
 * Binary Indexed Tree or Fenwick Tree
 */
public class BinaryIndexedTree {
	
	// Starting from index+1 keep updating affected nodes of BIT
	// till next node is not outside the range of BIT
	public void update(int[] bit, int val, int index) {
		index = index + 1;
		while (index < bit.length) {
			bit[index] += val;
			index = getNext(index);
		}
	}
	
	// Starting from index+1 keep adding value till you reach node 0
	public int sum(int[] bit, int index) {
		index = index + 1;
		int sum = 0;
		while (index > 0) {
			sum += bit[index];
			index = getParent(index);
		}
		return sum;
	}
	
	// Creating BIT is like updating the tree for every element of input array
	public int[] createTree(int[] input) {
		int[] bit = new int[input.length + 1];
		for (int i = 0; i < input.length; i++) {
			update(bit, input[i], i);
		}
		return bit;
	}

	/**
     * To get parent
     * 1) Get 2's complement to get minus of index number
     * 2) AND it with original index number
     * 3) Subtract result from original index number
     */
	private int getParent(int index) {
		return index - (index & -index);
	}

	/**
     * To get next
     * 1) Get 2's complement to get minus of index number
     * 2) AND it with original index number
     * 3) Add result to original index number
     */
	private int getNext(int index) {
		return index + (index & -index);
	}



	public static void main(String[] args) {
		int[] input = {1, 2, 3, 4, 5, 6, 7};
		BinaryIndexedTree tree = new BinaryIndexedTree();
        int[] bit = tree.createTree(input);
        System.out.println(tree.sum(bit, 0)); // 1
        System.out.println(tree.sum(bit, 1)); // 3
        System.out.println(tree.sum(bit, 2)); // 6
        System.out.println(tree.sum(bit, 3)); // 10
        System.out.println(tree.sum(bit, 4)); // 15
        System.out.println(tree.sum(bit, 5)); // 21
        System.out.println(tree.sum(bit, 6)); // 28
        
        // Lets do an update
        // input[3] = 6 | change = (newValue - oldValue) = (6 - 4) = 2
        // which makes, input = {1, 2, 3, 6, 5, 6, 7} and
        // bit = {0, 1, 3, 3, 12, 5, 11, 7}
        tree.update(bit, 2, 3); 
        
        System.out.println(tree.sum(bit, 3)); // 12
        System.out.println(tree.sum(bit, 6)); // 30
	}

}
