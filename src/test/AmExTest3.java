package test;

public class AmExTest3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 2;
		String S = "1A 2D 2G 1C";
		int totalFamilies = N*3;
        if (S.equals("")) {
        	System.out.println(totalFamilies);
            return;
        }
            
        String[] reservedSeats = S.split(" ");
		int[][] flags = new int[N][3];
		
		for (String seat : reservedSeats) {
			int rowNum = Integer.parseInt(seat.replaceAll("[^0-9]", ""));
			char label = seat.replaceAll("[^A-Z]", "").charAt(0);
			
			int j = -1;
			if (label == 'A' || label == 'B' || label == 'C') {
				j = 1;
			}
			if (label == 'H' || label == 'J' || label == 'K') {
				j = 3;
			}
			if (label == 'E' || label == 'F' || label == 'D' || label == 'G') {
				j = 2;
			}
			
			if (j != -1) {
				if (label == 'D' || label == 'G') {
					if (flags[rowNum-1][j-1] == -1) {
						flags[rowNum-1][j-1] = 1;
					}
					else {
						flags[rowNum-1][j-1] = -1;
					}
				}
				else {
					flags[rowNum-1][j-1] = 1;
				}
			}
		}
		
		for (int[] rows : flags) {
			for (int col : rows) {
				if (col == 1)
					totalFamilies--;
			}
		}
		
		System.out.println(totalFamilies);

	}

}
