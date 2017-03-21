package com.example.olegpatraschku.gobang.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleg Patraschku on 5/26/2016.
 */
public class GomokuStrategy implements GameStrategy {

    private final StrategyType strategy;
    private HashMap<String, Integer> blankPattern = new HashMap<>();
    private HashMap<String, Integer> blackPattern = new HashMap<>();
    private HashMap<String, Integer> whitePattern = new HashMap<>();
    private Pattern blackPossible; //checks if there makes sense to put there,
    private Pattern whitePossible; //e.g if we have only 3 spaces left then a move there is useless

    public GomokuStrategy(final StrategyType s) {
        strategy = s;

        blankPattern.put("__xx",   100);
        blankPattern.put("xx__",   100);
        blankPattern.put("_xx_",   200);
        blankPattern.put("_xx_x",  800);
        blankPattern.put("_x_xx",  800);
        blankPattern.put("xx_x_",  800);
        blankPattern.put("x_xx_",  800);
        blankPattern.put("_xxx",   1500);
        blankPattern.put("xxx_",   1500);
        blankPattern.put("_x_xxx", 2000);
        blankPattern.put("_xx_xx", 2000);
        blankPattern.put("_xxx_x", 2000);
        blankPattern.put("_xxx_",  3000);
        blankPattern.put("_xxxx",  4000);
        blankPattern.put("xxxx_",  4000);
        blankPattern.put("_xxxx_", 7000);
        blankPattern.put("xxxxx",  99999);


        for (Map.Entry<String, Integer> cursor : blankPattern.entrySet()) {
            String p = cursor.getKey();

            blackPattern.
                    put(p.replaceAll("x", String.valueOf(Constants.BLACK_CHAR)), cursor.getValue());
            whitePattern.
                    put(p.replaceAll("x", String.valueOf(Constants.WHITE_CHAR)), cursor.getValue());

            /**
             * these checks are needed to see if there are less than 5
             * figures in a certain line(vertical, diagonal ...)
             */

            blackPossible = Pattern.
                    compile("[_" + Constants.BLACK_CHAR + "]*" + Constants.BLACK_CHAR + "[_" + Constants.BLACK_CHAR + "]*");
            whitePossible = Pattern.
                    compile("[_" + Constants.WHITE_CHAR + "]*" + Constants.WHITE_CHAR + "[_" + Constants.WHITE_CHAR + "]*");

        }
    }

    /**
     * checks the empty cell for a certain possible score
     * @param owner tells us from which perspective to evaluate the cell at row, col
     * @param board where the cells lie
     * @param row
     * @param col
     * @return the score after patterns were applied
     */
    @Override
    public float evaluateAsOwnerForBoardAt(char owner, Board board, int row, int col) {
        String line;
        ArrayList<WeightedCell> cells;
        HashMap<String, Integer> patterns = owner == Constants.WHITE_CHAR ? whitePattern : blackPattern;
        Pattern possible = owner == Constants.WHITE_CHAR ? whitePossible : blackPossible;
        Matcher m;

        WeightedCell c = board.getCellAtIndex(row, col);
        final char origOwner = c.getOwnership();
        float newWeight = c.getWeight();

        c.setOwnership(owner);// so, we get the needed lines

        for (Direction direction : Direction.values()) {
            cells = board.getCellsForDirection(row, col, direction);
            line = board.makeStringFromCells(cells);

            for (Map.Entry<String, Integer> gP : patterns.entrySet()) {
                String key = gP.getKey();
                int val = gP.getValue();
                m = possible.matcher(line); // check if it makes sense to put if it's squeezed in between opponent's figures

                while (m.find()) {
                    if (m.group().length() > 4) { //m.end() - m.start() > 4 if length of matched string is at least 5
                        if (line.contains(key)) {
                            newWeight += val;
                        }
                    }
                }
            }
        }

        c.setOwnership(origOwner); // TODO test it ! should be Constants.FREE_CELL
        return newWeight;
    }

    @Override
    public HashMap<String, Integer> getBlackPattern() {
        return blackPattern;
    }

    @Override
    public HashMap<String, Integer> getWhitePattern() {
        return whitePattern;
    }

    @Override
    public Pattern getCheckForAvailableBlackPattern() {
        return blackPossible;
    }

    /**
     * @return the pattern which is needed to check whether 5 in line are possible
     */
    @Override
    public Pattern getCheckForAvailableWhitePattern() {
        return whitePossible;
    }

    @Override
    public StrategyType getStrategyType() {
        return strategy;
    }

}
