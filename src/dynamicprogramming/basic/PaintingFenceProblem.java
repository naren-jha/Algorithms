package dynamicprogramming.basic;

/*
 * https://www.youtube.com/watch?v=deh7UpSRaEY
 * https://www.geeksforgeeks.org/painting-fence-algorithm/
 */

public class PaintingFenceProblem {
	
	public int countWays(int n, int k) {
		if (n <= 0)
			return 0;
		if (n == 1)
			return k;
		
		int same = k, diff = k * (k - 1);
		int prevDiff;
		for(int i = 3; i <= n; i++) {
			prevDiff = diff;
			diff = (same + diff) * (k - 1);
			same = prevDiff;
		}
		return same + diff;
	}
	
	// same approach as previous one, but loop
	// starts from 2 instead of 3
	public int countWays2(int n, int k) {
		if (n <= 0)
			return 0;
		
		int same = 0, diff = k;
		int prevDiff;
		for(int i = 2; i <= n; i++) {
			prevDiff = diff;
			diff = (same + diff) * (k - 1);
			same = prevDiff;
		}
		return same + diff;
	}
	
	public static void main(String[] args) {
		PaintingFenceProblem obj = new PaintingFenceProblem();
		int n = 3, k = 2;
		System.out.println(obj.countWays(n, k));
		System.out.println(obj.countWays2(n, k));
	}

}
