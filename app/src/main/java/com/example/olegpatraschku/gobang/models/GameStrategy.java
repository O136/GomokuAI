package com.example.olegpatraschku.gobang.models;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Oleg Patraschku on 6/18/2016.
 */
public interface GameStrategy {
    int DEFAULT_WEIGHT = 20;

    HashMap<String, Integer> getBlackPattern();

    HashMap<String, Integer> getWhitePattern();

    Pattern getCheckForAvailableBlackPattern();

    Pattern getCheckForAvailableWhitePattern();

    float evaluateAsOwnerForBoardAt(char owner, Board board, final int row, final int col);

    StrategyType getStrategyType();
}
