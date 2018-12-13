package test;

import java.util.Scanner;

public class UberTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		int result = 0;
		if (input.length() <= 30) {
			result = 1;
		}
		else {
			int cNum = 0, maxPossibleCount = 24, currSpaceIndex = input.indexOf(" "), prevSpaceIndex = 0;
			int len = input.length();
			while (len > 0) {
				cNum++;
				if (cNum > 9)
					maxPossibleCount = 23;
				
				if (len - maxPossibleCount < 0)
					break;
				
				int prev = 0;
				while (currSpaceIndex - prevSpaceIndex < maxPossibleCount) {
					prev = currSpaceIndex;
					currSpaceIndex = input.indexOf(" ", currSpaceIndex+1);
				}
				len -= (prev-prevSpaceIndex);
				prevSpaceIndex = prev;
			}
			result = cNum;
		}
		System.out.println(result);
	}

}
