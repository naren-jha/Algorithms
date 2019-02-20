package dynamicprogramming.basic;

public class Test {

	public static int f(int m, int n) {
		if (n == 0)
			return 1;
		if (n < 0)
			return 0;
		if (m > n)
			return 0;
		
		return f(m, n-m) + f(m+1, n);
	}
	
	public static void main(String[] args) {
		int m = 1;
		int n = 2;
		System.out.println(f(m, n));
	}
	
}
