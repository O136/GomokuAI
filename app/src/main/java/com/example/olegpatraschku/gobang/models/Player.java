package com.example.olegpatraschku.gobang.models;

/**
 * Created by Oleg Patraschku on 5/7/2016.
 * The player with char 'BLACK_CHAR'(black figure) always
 * starts first
 */

public class Player {
    private int color;
    private char c;
    //TODO it can be possible that 2 players get same letter

    public Player(final int color, char c) {
        this.color = color;
        this.c = c;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public char getOwnership() {
        return c;
    }

    public void setOwnership(char c) {
        this.c = c;
    }

    public WeightedCell getMoveFrom(final int row, final int col) {
        return new WeightedCell(row, col, 0, getOwnership());
    }
}