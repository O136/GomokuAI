package com.example.olegpatraschku.gobang.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleg Patraschku on 6/18/2016.
 */
public class AIBrain {

    private HashMap<String, Integer> blackPattern = new HashMap<>();
    private HashMap<String, Integer> whitePattern = new HashMap<>();
    private HashSet<WeightedCell> potentialMoves = new HashSet<>();

    private ArrayList<WeightedCell> movesfromPatterns;

    private Pattern blackPossible;
    private Pattern whitePossible;
    private Matcher m;
    private Board board;

    private float black = 1f;
    private float white = 1f;

    private char own;

    public AIBrain(final Board board, final GameStrategy g) {
        this.board = board;
        this.blackPattern = g.getBlackPattern();
        this.whitePattern = g.getWhitePattern();
        this.blackPossible = g.getCheckForAvailableBlackPattern();
        this.whitePossible = g.getCheckForAvailableWhitePattern();

        movesfromPatterns = new ArrayList<>(board.SIZE * board.SIZE);

        if (g.getStrategyType() == StrategyType.ATTACK) this.black = 1.1f;
        else if (g.getStrategyType() == StrategyType.DEFENCE) this.white = 1.1f;
        if (own == Constants.WHITE_CHAR) {
            float tmp = this.black;
            this.black = this.white;
            this.white = tmp;
        }
    }

    public void setOwnership(final char own) {
        this.own = own;
    }

    /**
     * @return the random best move with the heaviest max weight
     */
    public WeightedCell getCurrentMove() {
        ArrayList<WeightedCell> bestMoves = new ArrayList<>(board.SIZE);
        float maxWeight = -1;

        for (WeightedCell w : movesfromPatterns)
            if (w.getWeight() > maxWeight)
                maxWeight = w.getWeight();

        for (WeightedCell w : movesfromPatterns)
            if (w.getWeight() == maxWeight)
                bestMoves.add(w);

        int index = ThreadLocalRandom.current().nextInt(0, bestMoves.size());
        bestMoves.get(index).setOwnership(own); // TODO should also weight be set to 0 ?
        bestMoves.get(index).setWeight(0);
        return bestMoves.get(index);
    }

    /**
     * grabs 2 layers of neighbor cells arround a certain coordinate
     *
     * @param row
     * @param col
     */
    public void updatePotentialMovesFromIndex(final int row, final int col) {
        final int lowRow = row - 2;
        final int upperRow = row + 2;
        final int leftCol = col - 2;
        final int rightCol = col + 2;

        for (int i = lowRow; i <= upperRow; ++i) {
            for (int j = leftCol; j <= rightCol; ++j) {
                if (i < 0 || j < 0 || i >= board.SIZE || j >= board.SIZE) continue;

                WeightedCell c = board.getCellAtIndex(i, j);
                if (c.getOwnership() == Constants.FREE_CELL)
                    potentialMoves.add(c);
            }
        }
        //needed because of the previous move of the player
        potentialMoves.remove(board.getCellAtIndex(row, col));
    }

    public void updateInfluenceMap() {
        movesfromPatterns.clear();
        String line;
        ArrayList<WeightedCell> cells;

        for (WeightedCell cell : potentialMoves) {
            // TODO what the fuck is this ? also cells stays marked with black char after call of this method
            if (cell.getWeight() != 0) cell.setWeight(GameStrategy.DEFAULT_WEIGHT);

            for (Direction d : Direction.values()) {
                cells = board.getCellsForDirection(cell.ROW, cell.COL, d);

                cell.setOwnership(Constants.WHITE_CHAR);
                line = board.makeStringFromCells(cells);

                for (Map.Entry<String, Integer> gP : whitePattern.entrySet()) { // filled up from GomokuPatterns
                    String key = gP.getKey();
                    int val = gP.getValue();
                    m = whitePossible.matcher(line); // check if it makes sense to put if it's squeezed in between opponent's figures

                    while (m.find()) {
                        if (m.group().length() > 4) { //m.end() - m.start() > 4 if length of matched string is at least 5
                            if (line.contains(key)) {
                                cell.setWeight(cell.getWeight() + val * white);
//                                cell.setOwnership(Constants.WHITE_CHAR);
                            }
                        }
                    }
                }


//                String line2 = getLineWithOwnershipForDirection(Constants.BLACK_CHAR, cell.ROW, cell.COL, d);

                cell.setOwnership(Constants.BLACK_CHAR);
                line = board.makeStringFromCells(cells);


                for (Map.Entry<String, Integer> gP : blackPattern.entrySet()) { // filled up from GomokuPatterns
                    String key = gP.getKey();
                    int val = gP.getValue();
                    m = blackPossible.matcher(line); // check if it makes sense to put if it's squeezed in between opponent's figures

                    while (m.find()) {
                        if (m.group().length() > 4) { //m.end() - m.start() > 4 if length of matched string is at least 5
                            if (line.contains(key)) {
                                cell.setWeight(cell.getWeight() + val * black);
//                                cell.setOwnership(Constants.BLACK_CHAR);
                            }
                        }
                    }
                }
            }
            cell.setOwnership(Constants.FREE_CELL);
            movesfromPatterns.add
                    (new WeightedCell(cell.ROW, cell.COL, cell.getWeight(), Constants.FREE_CELL));
            //cell.getOwnership()
        }
    }
}
