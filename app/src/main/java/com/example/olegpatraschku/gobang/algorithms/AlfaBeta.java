package com.example.olegpatraschku.gobang.algorithms;

import com.example.olegpatraschku.gobang.models.Board;
import com.example.olegpatraschku.gobang.models.Constants;
import com.example.olegpatraschku.gobang.models.GameStrategy;
import com.example.olegpatraschku.gobang.models.WeightedCell;

import java.util.ArrayList;

/**
 *
 */
public class AlfaBeta {
    private final GameStrategy strategy;

    public AlfaBeta(GameStrategy strategy) {
        this.strategy = strategy;
    }

    public static void createNodesForRoot(char player, Node root, ArrayList<WeightedCell> freeMoves, int depth) {
        if(depth == 0) return;

        for(WeightedCell move : freeMoves) {
            Board b = (Board)root.getBoard().clone();
            b.getCellAtIndex(move.ROW, move.COL).setOwnership(player);

            Node newChild = new Node(b, move.ROW, move.COL);
            root.addChild(newChild);
            createNodesForRoot(player, newChild, b.getNeighborsOf(1, move.ROW, move.COL), depth - 1);
        }
    }

    public WeightedCell run(Node node, char player, int depth) {
        if(depth == 0 || node.getChildren() == null) {
            int row = node.getRow(), col = node.getCol();
            float weight = strategy.evaluateAsOwnerForBoardAt(player, node.getBoard(), row, col);
            weight = player == Constants.WHITE_CHAR ? weight : -weight;

            return new WeightedCell(row, col, weight, player);
        }

        if(player == Constants.BLACK_CHAR) {
            Node n = node.getChildren().get(0); // used as default if all weights are same
            WeightedCell v = new WeightedCell(n.getRow(), n.getCol(), Float.MIN_VALUE, Constants.BLACK_CHAR);
            for(Node child: node.getChildren()) {
                WeightedCell ret = run(child, Constants.WHITE_CHAR, depth - 1);
                v = ret.getWeight() >= v.getWeight() ? ret : v;
            }
            return v;
        }
        else {
            Node n = node.getChildren().get(0); // used as default if all weights are same
            WeightedCell v = new WeightedCell(n.getRow(), n.getCol(), Float.MAX_VALUE, Constants.WHITE_CHAR);

            for(Node child: node.getChildren()) { // child is the board
                WeightedCell ret = run(child, Constants.BLACK_CHAR, depth - 1);
                v = ret.getWeight() <= v.getWeight() ? ret : v;
            }

            return v;
        }
    }
}
