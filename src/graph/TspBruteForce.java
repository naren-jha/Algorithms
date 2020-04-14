package graph;

import java.util.Arrays;

/**
 * Solution of Traveling Salesman Problem using Brute-Force approach
 * 
 * Time Complexity: O(n!) [not suitable for n > 12]
 * 
 * https://youtu.be/09_LlHjoEiY?t=12011
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class TspBruteForce {
    
    public int[] tsp(double[][] m) {
        int n = m.length;
        int[] permutation = new int[n];
        
        // start with permutation sorted in ascending order
        // for example {0, 1, 2, 3} for n = 4
        for (int i = 0; i < n; ++i)
            permutation[i] = i;
        
        int[] bestTour = permutation.clone();
        double bestTourCost = Double.POSITIVE_INFINITY;

        do {
            double tourCost = computeTourCost(permutation, m, n);
            if (tourCost < bestTourCost) {
                bestTourCost = tourCost;
                bestTour = permutation.clone();
            }
        } while (nextPermutation(permutation));
        
        return bestTour;
    }
    
    public double computeTourCost(int[] permutation, double[][] m, int n) {
        double cost = 0;
        for (int i = 0; i < n-1; ++i) {
            int from = permutation[i];
            int to = permutation[i+1];
            cost += m[from][to];
        }
        
        // Compute the cost to return to the starting city
        int from = permutation[n-1]; // last node
        int to = permutation[0]; // first node
        cost += m[from][to];
        return cost;
    }
    
    // Generates next ordered permutation for the given current sequence 
    // Generation of next sequence is in-place, i.e., it repopulates 'permutation' array
    // Returns 'true' if next permutation is available, else returns 'false'
    private boolean nextPermutation(int[] currSeq) {
        // find valid 'from' index
        int from = getFrom(currSeq);
        
        // if currSeq is sorted in descending order 
        // then there is no more permutation available
        if (from == -1) return false;
        
        // find valid 'to' index and then swap
        int to = currSeq.length - 1;
        while (currSeq[from] >= currSeq[to]) 
            --to;
        swap(currSeq, from, to);
        
        // arrange rest of the number after index 'from' in ascending order
        // to get next sequence in order
        from++;
        to = currSeq.length - 1;
        while (from < to) {
            swap(currSeq, from, to);
            from++;
            to--;
        }
        
        return true;
      }

      private int getFrom(int[] sequence) {
        for (int i = sequence.length - 2; i >= 0; --i) 
            if (sequence[i] < sequence[i + 1]) 
                return i;
        return -1;
      }

      private void swap(int[] sequence, int i, int j) {
        int tmp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = tmp;
      }

    public static void main(String[] args) {
        TspBruteForce obj = new TspBruteForce();
        
        int n = 10;
        double[][] matrix = new double[n][n];
        for (double[] row : matrix) Arrays.fill(row, 100);

        // Construct an optimal tour
        int dist = 5;
        int[] optimalTour = {2, 7, 6, 1, 9, 8, 5, 3, 4, 0, 2};
        for (int i = 0; i < optimalTour.length - 1; i++)
          matrix[optimalTour[i]][optimalTour[i + 1]] = dist;

        int[] bestTour = obj.tsp(matrix);
        System.out.println(Arrays.toString(bestTour)); 
        // [0, 2, 7, 6, 1, 9, 8, 5, 3, 4]

        double tourCost = obj.computeTourCost(bestTour, matrix, matrix.length);
        System.out.println("Tour cost: " + tourCost); // Tour cost: 50.0
    }
}
