package com.example.olegpatraschku.gobang.models;

import java.util.ArrayList;

/**
 * Created by Oleg Patraschku on 5/8/2016.
 */
public class GomokuRules implements Rules {
    public static final String WINNING_BLACK;
    public static final String WINNING_WHITE;

    static {
        StringBuilder b = new StringBuilder();
        StringBuilder w = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            b.append(Constants.BLACK_CHAR);
            w.append(Constants.WHITE_CHAR);
        }
        WINNING_WHITE = w.toString();
        WINNING_BLACK = b.toString();
    }

    @Override
    public boolean validateMoveAt(Board board, WeightedCell cell) {
        WeightedCell c = board.getCellAtIndex(cell.ROW, cell.COL);

        if (c.getOwnership() == Constants.FREE_CELL) {
            c.setOwnership(cell.getOwnership());
            c.setWeight(cell.getWeight());
            return true;
        }

        return false;
    }

    @Override
    public void checkFinalMove(Board board, WeightedCell cell) throws GameOverException {
        if (board.getFreeCells() == 0) throw new GameOverException(null, null);
        ArrayList<WeightedCell> cells;
        String s;

        for (Direction direction : Direction.values()) {
            cells = board.getCellsForDirection(cell.ROW, cell.COL, direction);
            s = board.makeStringFromCells(cells);
            if (s.contains(WINNING_BLACK) || s.contains(WINNING_WHITE))
                throw new GameOverException(cells, direction);
        }
    }
}
