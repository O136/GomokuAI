package com.example.olegpatraschku.gobang.models;

import java.util.Observable;

/**
 * Created by Oleg Patraschku on 5/11/2016.
 */
public class Game extends Observable {
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    private Player[] players;
    private Rules rules;
    private Board board;
    private int currentMove = 0;

    public Game(final Player[] p, final Board board, final Rules r) {
        this.board = board;
        this.players = p;
        this.rules = r;
    }

    public Player getPlayerForCurrentMove() {
        return players[currentMove];
    }

    public int updateCurrentMove() {
        currentMove = (++currentMove) % players.length;
        return currentMove;
    }

    /**
     * @param cell contains all the needed info to validate a move
     * @return True if it's possible to put a figure at row, col otherwise false
     */
    public boolean playerPutsFigure(final WeightedCell cell) {
        //TODO when the turn is for the AIPlayer then human shouldn't be able to interaction
        if (rules.validateMoveAt(board, cell)) {
            board.setFreeCells(board.getFreeCells() - 1);
            setChanged();
            notifyObservers(cell); //will draw lines first then update in case of exception
            return true;
        }
        return false;
    }
}
