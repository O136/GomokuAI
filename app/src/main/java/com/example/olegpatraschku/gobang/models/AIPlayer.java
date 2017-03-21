package com.example.olegpatraschku.gobang.models;

/**
 * Created by Oleg Patraschku on 5/7/2016.
 */

public class AIPlayer extends Player {
    final AIBrain brain;

    @Override
    public void setOwnership(char c) {
        super.setOwnership(c);
        brain.setOwnership(c);
    }

    public AIPlayer(final int color, final char own, final AIBrain brain) {
        super(color, own);
        this.brain = brain;
        setOwnership(own);
    }

    /**
     * Brain sees which figure was put recently
     * @param row
     * @param col
     */
    public void increaseSightFrom(final int row, final int col) {
        brain.updatePotentialMovesFromIndex(row, col);
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    @Override
    public WeightedCell getMoveFrom(final int row, final int col) {
        brain.updateInfluenceMap();
        return brain.getCurrentMove();
    }
}
