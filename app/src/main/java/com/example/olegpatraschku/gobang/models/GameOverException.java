package com.example.olegpatraschku.gobang.models;

import java.util.ArrayList;

/**
 * Created by Oleg Patraschku on 6/22/2016.
 */
public class GameOverException extends Exception {
    private final ArrayList<WeightedCell> cells;
    private final Direction direction;

    public GameOverException(final ArrayList<WeightedCell> cells,
                             final Direction direction) {
        this.cells = cells;
        this.direction = direction;
    }

    /**
     * @return null if there were no cell that formed the winning pattern
     * causing the end of the game, otherwise get the array of the cells
     * which form the end of game pattern
     */
    public ArrayList<WeightedCell> getCells() {
        return cells;
    }

    public Direction getDirection() {
        return direction;
    }
}
