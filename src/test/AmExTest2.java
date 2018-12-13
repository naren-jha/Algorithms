package test;

import java.util.HashMap;
import java.util.Map;

public class AmExTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 1004;
		String s = Integer.toString(N);
		char[] chars = s.toCharArray();
		Map<Character, Integer> countMap = getCountMap(chars);
		
		int res = 0;
		if (!s.contains("0")) {
			int denom = getDenominator(countMap);
			res = factorial(s.length()) / denom;
		}
		else {
			// zero placement first
			int zeroCount = countMap.get('0');
			int validSlots = s.length() - 1;
			int zeroPlacementRes = factorial(validSlots) / (factorial(zeroCount) * factorial(validSlots - zeroCount));
			
			// other digit placement
			countMap.remove('0');
			int denom = getDenominator(countMap);
			res = factorial(s.length() - zeroCount) / denom;
			
			// effective result with zero placement
			res *= zeroPlacementRes;
		}
		System.out.println(res);

	}

	private static Map<Character, Integer> getCountMap(char[] chars) {
		Map<Character, Integer> countMap = new HashMap<Character, Integer>();
		for (char c : chars) {
		    if (!countMap.containsKey(c))
		    	countMap.put(c, 1);
		    else
		    	countMap.put(c, countMap.get(c)+1);
		}
		return countMap;
	}

	private static int getDenominator(Map<Character, Integer> map) {
		int denom = 1;
		for (Integer i : map.values()) {
			if (i > 1)
				denom *= factorial(i);
		}
		return denom;
	}
	
	private static int factorial(int n) {
		int res = 1;
        for (int i = 2; i <= n; i++)
            res *= i;
        return res;
	}

}
