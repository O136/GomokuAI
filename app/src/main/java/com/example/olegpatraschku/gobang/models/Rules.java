package com.example.olegpatraschku.gobang.models;

/**
 * Created by Oleg Patraschku on 5/8/2016.
 */
public interface Rules {
    /**
     * checks if the player can put a figure at a certain position
     *
     * @param board here are all the positions saved and then the board is checked with the @cell to see whether it can be put there or not
     * @param cell  it's just the container with saved row and col where the player wants to put something
     * @return boolean if the player could/could not put at the desired position
     */
    boolean validateMoveAt(final Board board, final WeightedCell cell);

    void checkFinalMove(final Board board, final WeightedCell cell) throws GameOverException;

}
