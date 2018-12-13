package test;

import java.util.Scanner;

public class ThoughtWorksTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int r = sc.nextInt();
		int[] n = new int[r];
		for (int i = 0; i < r; i++)
			n[i] = sc.nextInt();
		
		for (int round = 0; round < r; round++) {
			char[] binary = Integer.toBinaryString(n[round]).toCharArray();
			char player = ' ';
			for (int move = 0; move < binary.length; move++) {
				if (player == ' ') {
					// first move by Xavier
					player = 'X';
				}
				else {
					// switch player from second move onwards
					player = player == 'X' ? 'Y' : 'X';
				}
				
				if (move > 0 && binary[move-1] == binary[move])
					binary[move-1] = binary[move-1] == '1' ? '0' : '1';
				
				if (move < binary.length - 1 && binary[move+1] == binary[move])
					binary[move+1] = binary[move+1] == '1' ? '0' : '1';
				
				binary[move] = binary[move] == '1' ? '0' : '1';
			}
			
			int modifiedNumber = Integer.parseInt(new String(binary), 2);
			if (modifiedNumber == n[round] || modifiedNumber + 1 == n[round] || modifiedNumber -1 == n[round])
				System.out.println(player);
			else
				System.out.println(player == 'X' ? 'Y' : 'X');
		}
        
	}

}
