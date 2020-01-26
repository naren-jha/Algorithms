package basic.array.problems;

import java.util.Arrays;

// https://www.geeksforgeeks.org/count-occurences-of-anagrams/

public class CountOccurencesOfAnagrams {

    public static int countAnagram(String text, String word) {
        int n = text.length(); // text length
        int k = word.length(); // word length
        
        int count = 0;
        for (int i = 0; i <= n - k; i++) {
            String s = text.substring(i, i + k);
            if (areAnagram(word, s))
                count++;
        }
        return count;    
    }
    
    private static boolean areAnagram(String s1, String s2) {
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        
        Arrays.sort(ch1);
        Arrays.sort(ch2);
        
        return Arrays.equals(ch1, ch2);
    }
    
    public static void main(String[] args) {
        String text = "forxxorfxdofr";
        String word = "for";
        System.out.println(countAnagram(text, word)); // 3
    }
}
