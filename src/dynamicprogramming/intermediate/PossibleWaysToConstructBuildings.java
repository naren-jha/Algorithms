package dynamicprogramming.intermediate;

// https://www.geeksforgeeks.org/count-possible-ways-to-construct-buildings/

public class PossibleWaysToConstructBuildings {
	
	public static int countWays(int n) {
		int f = f(n);
		return f*f;
	}
	
	public static int f(int n) {
		if (n == 0)
			return 1;
		if (n == 1)
			return 2;
		
		return f(n-2) + f(n-1);
	}
	// Above solution can be easily memoized/tabulated
	
	public static void main(String[] args) {
		System.out.println(countWays(3)); // 25
	}
}
