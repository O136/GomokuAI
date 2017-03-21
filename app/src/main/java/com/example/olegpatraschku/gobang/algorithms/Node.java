package com.example.olegpatraschku.gobang.algorithms;

import com.example.olegpatraschku.gobang.models.Board;

import java.util.ArrayList;

/**
 * Created by Oleg Patraschku on 11.08.16.
 */
public class Node {
    private Board board; //TODO instead of heavy class char[][] would also be good
    private int row, col; //the move which is taken care of in the board
    private ArrayList<Node> children;

    public Node(Board board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
        children = new ArrayList<>(9);
    }

    public Board getBoard() {
        return board;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }
}
