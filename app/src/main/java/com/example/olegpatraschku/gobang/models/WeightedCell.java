package com.example.olegpatraschku.gobang.models;

/**
 * Created by Oleg Patraschku on 6/1/2016.
 * If default parameters get complicated then use builder pattern
 */
public class WeightedCell {
    public final int ROW, COL;
    private float weight;
    private char ownership;

    public WeightedCell(final int row, final int col, final float weight, final char ownership) {
        ROW = row;
        COL = col;
        setWeight(weight);
        setOwnership(ownership);
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float w) {
        weight = w;
    }

    public char getOwnership() {
        return ownership;
    }

    public void setOwnership(char ownership) {
        this.ownership = ownership;
    }
}
