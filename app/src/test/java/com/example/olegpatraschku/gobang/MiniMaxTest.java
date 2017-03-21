package com.example.olegpatraschku.gobang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oleg Patraschku on 8/9/2016.
 */

/**
 01 function alphabeta(node, depth, α, β, maximizingPlayer)
 02      if depth = 0 or node is a terminal node
 03          return the heuristic value of node
 04      if maximizingPlayer
 05          v := -∞
 06          for each child of node
 07              v := max(v, alphabeta(child, depth - 1, α, β, FALSE))
 08              α := max(α, v)
 09              if β ≤ α
 10                  break (* β cut-off *)
 11          return v
 12      else
 13          v := ∞
 14          for each child of node
 15              v := min(v, alphabeta(child, depth - 1, α, β, TRUE))
 16              β := min(β, v)
 17              if β ≤ α
 18                  break (* α cut-off *)
 19          return v
 */

public class MiniMaxTest {
    static class Algorithm {
        public static Integer MinMax(Node node, int depth, char own) {
            if(depth == 0 || node.children == null) return node.getValue();

            if(own == 'x') { //maximizing player
                int bestVal = Integer.MIN_VALUE;
                for(Node n: node.children) {
                    int r = Algorithm.MinMax(n, depth - 1, 'o');
                    bestVal = r > bestVal ? r : bestVal;
                }
                return bestVal;
            }
            else {
                int bestVal = Integer.MAX_VALUE;
                for(Node n: node.children) {
                    int r = Algorithm.MinMax(n, depth - 1, 'x');
                    bestVal = r < bestVal ? r : bestVal;
                }
                return bestVal;
            }
        }
    }

    @Test
    public void testMinMax() {
        Node start = new Node(0);
        //lvl n(1) children
        ArrayList<Node> n1_3 = new ArrayList<>(3);
        n1_3.add(new Node(-10));
        n1_3.add(new Node(2));
        n1_3.add(new Node(1));

        //lvl n(2) children
        ArrayList<Node> n2_3 = new ArrayList<>(3);
        n2_3.add(new Node(-2));
        n2_3.add(new Node(4));
        n2_3.add(new Node(1));

        ArrayList<Node> n2_2 = new ArrayList<>(2);
        n2_2.add(new Node(3));
        n2_2.add(new Node(-1));

        ArrayList<Node> n2_1 = new ArrayList<>(1);
        n2_1.add(new Node(-2));

        //wiring nodes
        start.children = n1_3;
        start.children.get(0).children = n2_3;
        start.children.get(1).children = n2_2;
        start.children.get(2).children = n2_1;

        assertEquals(-1, Algorithm.MinMax(start, 2, 'x').intValue());
        assertEquals(-1, Algorithm.MinMax(start, 2, 'x').intValue());

        assertEquals(-10, Algorithm.MinMax(start, 1, 'o').intValue());
        assertEquals(-10, Algorithm.MinMax(start, 1, 'o').intValue());
    }

    /**
     * Created by Oleg Patraschku on 8/9/2016.
     */
    public static class Node {

        private ArrayList<Integer> values;
        private Integer value;
        public  ArrayList<Node> children;

        public Node(Integer value) {
            setValue(value);
        }
        public Node(ArrayList<Integer> arr) {
            setValues(arr);
        }

        public Integer getMax() {
            return Collections.max(values);
        }

        public Integer getMin() {
            return Collections.min(values);
        }

        public void setValues(ArrayList<Integer> values) {
            this.values = values;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
