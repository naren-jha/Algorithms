package test;

public class AmExTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String S = "55 53 72-654";
		S = S.replaceAll("[ -]", "");
		int rem = S.length() % 3;
		int p = S.length();
		
		if (rem == 1)
			p = S.length() - 4;
		if (rem == 2)
			p = S.length() - 2;
		
		StringBuilder res = new StringBuilder("");
		for (int i = 0; i < p; i = i + 3) {
			res.append(S.substring(i, i+3)).append("-");
		}
		
		if (rem == 0)
			res.deleteCharAt(res.length()-1);
		if (rem == 1)
			res.append(S.substring(p, p+2)).append("-").append(S.substring(p+2, p+4));
		if (rem == 2)
			res.append(S.substring(p, p+2));

		System.out.println(res.toString());
	}

}
