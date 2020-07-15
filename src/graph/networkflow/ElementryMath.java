package graph.networkflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graph.networkflow.NetworkFlowBase.FlowEdge;

/**
 * Problem: https://open.kattis.com/problems/elementarymath
 *
 * @author Narendra Jha, njha.sde@gmail.com
 *
 */
public class ElementryMath {
    
    public static class InputPair {
        int a, b;
        
        public InputPair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
    
    private InputPair[] pairs;
    
    // Maps to store answer -> indices (of answer nodes) 
    // and vice-versa in flow-network
    private Map<Integer, Integer> answerIndexMap = new HashMap<>();
    private Map<Integer, Integer> indexAnswerMap = new HashMap<>();
    
    private final int n; // # of input pair nodes
    private final int m; // # of answer nodes
    private final int N; // total # of nodes
    
    private final int s, t; // source and sink node indices
    
    public ElementryMath(InputPair[] pairs) {
        this.pairs = pairs;
        n = pairs.length;
        
        int i = 0;
        for (InputPair pair : pairs) {
            for (int answer : getAnswersForPair(pair)) {
                if (!answerIndexMap.containsKey(answer)) {
                    answerIndexMap.put(answer, n + i);
                    indexAnswerMap.put(n + i, answer);
                    i++;
                }
            }
        }
        m = answerIndexMap.size(); // unique number of answer nodes
        
        N = n + m + 2;
        s = N - 1;
        t = N - 2;
    }
    
    public void solve() {
        NetworkFlowBase ff = new FordFulkersonDfsAdjacencyList(N, s, t);
        
        // Hook up edges from source to input pair nodes
        for (int i = 0; i < n; ++i)
            ff.addEdge(s, i, 1);
        
        // Hook up edges from input pair nodes to answer nodes
        for (int i = 0; i < n; ++i)
            for (int answer : getAnswersForPair(pairs[i]))
                ff.addEdge(i, answerIndexMap.get(answer), 1);
        
        // Hook up edges from answer nodes to sink
        for (Integer index : answerIndexMap.values())
            ff.addEdge(index, t, 1);
        
        printResultEquations(ff.getGraph());
    }

    private void printResultEquations(List<List<FlowEdge>> graph) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < n; ++i) {
            InputPair pair = pairs[i];
            char opr = '.';
            int ans = 0;
            for (FlowEdge edge : graph.get(i)) {
                if (edge.flow == 1 && !edge.isBackEdge()) {
                    ans = indexAnswerMap.get(edge.to);
                    opr = getOperatorForAnswer(pair, ans);
                    break;
                }
            }
            
            if (opr == '.') {
                System.out.println("impossible");
                return;
            }
            result.add(String.format("%d %c %d = %d", pair.a, opr, pair.b, ans));
        }
        
        for (String equation : result)
            System.out.println(equation);
    }
    
    private int[] getAnswersForPair(InputPair pair) {
        return new int[] {pair.a + pair.b, pair.a - pair.b, pair.a * pair.b};
    }
    
    private char getOperatorForAnswer(InputPair pair, int ans) {
        if (pair.a + pair.b == ans) return '+';
        if (pair.a - pair.b == ans) return '-';
        if (pair.a * pair.b == ans) return '*';
        return '.';
    }
    
    public static void main(String[] args) {
        example1();
        //example2();
    }

    private static void example1() {
        InputPair[] pairs = {
                new InputPair(1, 5),
                new InputPair(3, 3),
                new InputPair(4, 5),
                new InputPair(-1, -6)
        };
        
        ElementryMath solver = new ElementryMath(pairs);
        solver.solve();
        /*
         Outputs:
         1 - 5 = -4
         3 + 3 = 6
         4 + 5 = 9
         -1 + -6 = -7
         */
    }
    
    private static void example2() {
        InputPair[] pairs = {
                new InputPair(4, -2),
                new InputPair(4, -2),
                new InputPair(4, -2),
                new InputPair(4, -2)
        };
        
        ElementryMath solver = new ElementryMath(pairs);
        solver.solve();
        /*
         Outputs:
         impossible
         */
    }

}
