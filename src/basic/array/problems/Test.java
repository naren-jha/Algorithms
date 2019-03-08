package basic.array.problems;

import java.util.*;

public class Test {
	
	/*Problem: Ritik loves to jump here and there.So his friend Sugam gave him an array of N Integers in which he can choose any pair of Integers Ai and Aj if and only if i+K=j. Now he can add 1 to one of those elements and subtract one from the other i.e move 1 from one element to the other.He can perform this operation any number of times by jumping here and there in the array.
			Now Ritik wants to know if he can make all the numbers Prime by jumping and performing 0 or more number of given operations on the array.
			Note : 0 and 1 are not considered as prime numbers.

			Input Format
			The first line of input contains an Integer K.
			The Second line of input contains an Integer N denoting the size of Array. 
			Next line contains elements of the array A separated by whitespaces.

			Constraints
			1 <= K <= N
			1 <= N <= 10000
			1 <= i <= N 
			1 <= Ai <= 100


			Output Format
			You must print 1 as answer if it possible to make all numbers as prime and -1 if not.

			Sample TestCase 1:
			Input:
			2
			5
			2 8 4 5 6
			
			Output:
			1
			
			Explanation:
			Move 1 from A3 to A5. Move 1 from A4 to A2. Move 1 from A4 to A2. Move 1 from A4 to A2.
			Final array {2,11,3,2,7}.Hence 1 since all are primes.*/

	public static void main(String args[] ) throws Exception {

	    Scanner in = new Scanner(System.in);
	    int k = in.nextInt();
	    int n = in.nextInt();
	    int[] arr = new int[n];
	
	    for (int i = 0; i < n; i++) {
	    	arr[i] = in.nextInt();
	    }
	
	    for (int i = 0; i < k; i++) {
	    	// make a list of numbers to which jumps can be made
	    	List<Integer> list = new ArrayList<Integer>();
	        for (int j = i; j < n; j = j + k) {
	        	list.add(arr[j]);
	        }
	        
	        // check if all numbers in the list can be made prime or not
	        if (!canBeMadePrime(list)) {
	        	System.out.println(-1);
	        	return;
	        }
	    }
	    System.out.println(1);

   }
   
   // returns true if all numbers in the given list can be made prime
   public static boolean canBeMadePrime(List<Integer> list) {
	   
	   // sum the list of numbers
       int sum = list.stream().mapToInt(v -> v).sum();
       
       // get all prime numbers less than 'sum'
       // Set<Integer> primes = getPrimes(sum);
       
       // check if 'sum' can be expressed as sum of 'list.size()' prime numbers
       return isSumOfKprimes(sum, list.size());
       
   }
   
   /*// returns set of prime numbers less than 'n'
   public static Set<Integer> getPrimes(int n) {
	   
	   Set<Integer> primes = new HashSet<Integer>();
	   
	   boolean[] flags = new boolean[n + 1];
	   flags[0] = flags[1] = false;
	   
	   int i, j;
	   for (i = 2; i <= n; i++) {
		   flags[i] = true;
	   }
	   
	   for (i = 2; i <= Math.sqrt(n); i++) {
		   // if flags[i] is not changed, then it is a prime
           if (flags[i]) {
        	   // mark all multiples of 'i' as notPrime
               for (j = 2*i; j <= n; j += i) {
            	   flags[j] = false;
               }
           }
       }
	   
	   for (i = 2; i <= n; i++) {
		   // if flags[i] is true, then 'i' is prime, else not
		   if (flags[i]) {
			   primes.add(i);
		   }
	   }
	   return primes;
	   
   }*/
   
   // returns true if 'n' can be expressed as sum of 'k' prime numbers
   public static boolean isSumOfKprimes(int n, int k) {
	   
	   // if n < 2k, simply return false
       if (n < 2*k)
           return false;
        
       // for k = 1, return value depends on whether 'n' is prime or not
       if (k == 1)
           return isPrime(n);
            
       if (k == 2) {
           // if k = 2 and n is even, then by Goldbach’s conjecture
    	   // n can be expressed as sum of two prime numbers, so return true
           if (n % 2 == 0) {
               return true;
           }
                
           // if k = 2 and n is odd, then out of two prime numbers, one must be number 2
           // as all other primes are odd. so return value depends on 
           // whether 'n-2' is prime or not
           return isPrime(n - 2);
       }
        
       // for k >= 3, 'n' can always be expressed as sum of 'k' prime numbers
       // Case 1: when n is even, n - 2*(k-2) is also even (as even - even = even) 
       // so by Goldbach’s conjecture, n - 2*(k-2) can be written as sum of two prime numbers (call it p and q)
       // and therefore n can be written as 2,2,2....(k-2) times, plus p and q. 
       
       // Case 2: when n is odd, (n-3) - 2*(k-3) is even,
       // so again by Goldbach’s conjecture, (n-3) - 2*(k-3) can be written as sum of two prime numbers (call it p and q)
       // and therefore n can be witten as 2,2,2....(k-3) times, plus 3, p and q
       
       // so for k >= 3, simply return true
       return true;
       
   }

   // checks whether an integer is prime or not
   public static boolean isPrime(int n) {
       for (int i = 2; i*i <= n; i++) {
           if (n % i == 0) {
               return false;
           }
       }
       return true;
   }

}
