package test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UberTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] numbers = new int[n];
        for(int t = 0; t < n; t++) {
        	numbers[t] = sc.nextInt();
        }
        int k = sc.nextInt();
        
		Set<String> matches = new HashSet<String>();
		int oddCount = 0;
		for (int i = 0; i < numbers.length; i++) {
			int j = i;
			while (j < numbers.length) {
				if (numbers[j] % 2 == 1) {
					oddCount++;
				}
				if (oddCount > k) {
					break;
				}
				StringBuilder tmp = new StringBuilder("");
				for (int p = i; p <= j ; p++) {
					tmp.append(numbers[p]);
					tmp.append("-");
				}
				matches.add(tmp.toString());
				j++;
			}
			oddCount = 0;
		}
		System.out.println(matches.size());

	}

}
