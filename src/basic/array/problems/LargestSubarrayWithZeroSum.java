package basic.array.problems;

import java.util.HashMap;
import java.util.Map;

// https://www.geeksforgeeks.org/find-the-largest-subarray-with-0-sum/ 

public class LargestSubarrayWithZeroSum {

	public static int maxLen(int[] a) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		int sum = 0, maxLen = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0 && maxLen == 0)
				maxLen = 1;
			
			sum += a[i];
			if (sum == 0)
				maxLen = i+1;
			
			Integer prev_i = map.get(sum);
			if (prev_i != null)
				maxLen = Math.max(maxLen, i - prev_i);
			else
				map.put(sum, i);
		}
		return maxLen;
	}
	
	public static void main(String[] args) {
		int[] a = {15, -2, 2, -8, 1, 7, 10, 23};;
		System.out.println(maxLen(a)); // 5
	}
}
