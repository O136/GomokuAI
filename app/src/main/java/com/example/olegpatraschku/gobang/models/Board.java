package com.example.olegpatraschku.gobang.models;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Oleg Patraschku on 5/11/2016.
 */
public class Board implements Cloneable {
    public final int SIZE;
    private final WeightedCell[][] cells;
    private int freeCells;

    public Board(final int size) {
        SIZE = size;
        cells = new WeightedCell[SIZE][SIZE];
        freeCells = SIZE * SIZE;
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                cells[i][j] = new WeightedCell
                        (i, j, GameStrategy.DEFAULT_WEIGHT, Constants.FREE_CELL);
    }

    /**
     * Used when a new game must be started
     * TODO must also notify the view to remove the circles and if present, the lines
     */
    public void clearBoard() {
        freeCells = SIZE * SIZE;
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j) {
                cells[i][j].setWeight(GameStrategy.DEFAULT_WEIGHT);
                cells[i][j].setOwnership(Constants.FREE_CELL);
            }
    }

    public ArrayList<WeightedCell> getNeighborsOf(int n, int row, int col) {
        ArrayList<WeightedCell> neighbors = new ArrayList<>(24); // 2 layers around max 24 cells
        final int lowRow = row - n;
        final int upperRow = row + n;
        final int leftCol = col - n;
        final int rightCol = col + n;

        for (int i = lowRow; i <= upperRow; ++i) {
            for (int j = leftCol; j <= rightCol; ++j) {
                if (i < 0 || j < 0 || i >= SIZE || j >= SIZE) continue;

                if (cells[i][j].getOwnership() == Constants.FREE_CELL)
                    neighbors.add(cells[i][j]);
            }
        }
        //here there is no need, getNeighborsOf is called
        neighbors.remove(cells[row][col]);
        return neighbors;
    }

    @Override
    public Object clone() {
        Board b = new Board(SIZE);
        //TODO stupid double iteration could've been done with one call of constructor
        WeightedCell c;
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j) {
                c = b.getCellAtIndex(i, j);
                c.setWeight(cells[i][j].getWeight());
                c.setOwnership(cells[i][j].getOwnership());
            }

        return b;
    }

    public char getOwnershipAt(final int row, final int col) {
        return cells[row][col].getOwnership();
    }

    public WeightedCell getCellAtIndex(final int row, final int col) {
        return cells[row][col];
    }

    public int getFreeCells() {
        return freeCells;
    }

    public void setFreeCells(int freeCells) {
        this.freeCells = freeCells;
    }
    /**
     * Collects characters from 9 cells in a certain position
     *
     * @param row
     * @param col
     * @param d   is the direction of a line made out from 9 cells
     * @return the string which is made of 9 char from the 9 cells
     */
    public ArrayList<WeightedCell> getCellsForDirection(final int row, final int col, final Direction d) {
        //WeightedCell[] cells = new WeightedCell[9];
        ArrayList<WeightedCell> cells = new ArrayList<>();

        int rowDir;
        int colDir;
        int r;
        int c;

        switch (d) {
            default:
            case VERTICAL:
                rowDir = 1;
                colDir = 0;
                break;

            case HORIZONTAL:
                rowDir = 0;
                colDir = 1;
                break;

            case RIGHT_DIAGONAL:
                rowDir = 1;
                colDir = -1;
                break;

            case LEFT_DIAGONAL:
                rowDir = 1;
                colDir = 1;
                break;
        }


        for (int i = 4; i > 0; --i) {
            r = row - rowDir * i;
            c = col - colDir * i;
            if (r > -1 && r < SIZE && c > -1 && c < SIZE) //>=0
                cells.add(getCellAtIndex(r, c));
        }

        cells.add(getCellAtIndex(row, col));

        for (int i = 1; i < 5; ++i) {
            r = row + rowDir * i;
            c = col + colDir * i;
            if (r > -1 && r < SIZE && c > -1 && c < SIZE) //>=0
                cells.add(getCellAtIndex(r, c));
        }

        return cells;
    }

    public String makeStringFromCells(final ArrayList<WeightedCell> cells) {
        StringBuilder builder = new StringBuilder();
        for (WeightedCell cell : cells)
                builder.append(cell.getOwnership());
        return builder.toString();
    }
}
