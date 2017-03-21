package com.example.olegpatraschku.gobang;

import android.graphics.Color;

import com.example.olegpatraschku.gobang.algorithms.AlfaBeta;
import com.example.olegpatraschku.gobang.algorithms.Node;
import com.example.olegpatraschku.gobang.models.AIBrain;
import com.example.olegpatraschku.gobang.models.AIPlayer;
import com.example.olegpatraschku.gobang.models.Board;
import com.example.olegpatraschku.gobang.models.Constants;
import com.example.olegpatraschku.gobang.models.Game;
import com.example.olegpatraschku.gobang.models.GameStrategy;
import com.example.olegpatraschku.gobang.models.GomokuRules;
import com.example.olegpatraschku.gobang.models.GomokuStrategy;
import com.example.olegpatraschku.gobang.models.Player;
import com.example.olegpatraschku.gobang.models.Rules;
import com.example.olegpatraschku.gobang.models.StrategyType;
import com.example.olegpatraschku.gobang.models.WeightedCell;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by oleg on 11.08.16.
 */
public class AlfaBetaTest {
    Board b;
    Game g;
    Rules r;
    GameStrategy s;
    Player[] players;

    @Before
    public void initializeGame() throws Exception {
        b = new Board(Constants.SMALL_BOARD);
        s = new GomokuStrategy(StrategyType.ATTACK);
        r = new GomokuRules();
        players = new Player[] {
                new Player(Color.BLACK, Constants.BLACK_CHAR),
                new AIPlayer(Color.WHITE, Constants.WHITE_CHAR, new AIBrain(b, s))
        };
        g = new Game(players, b, r);
    }

    @Test
    public void testNeighbours() throws Exception {
        assertEquals(g.getPlayerForCurrentMove(), players[0]);
        int middle = b.SIZE / 2;
        int oneLayer = 1;

        WeightedCell cell = g.getPlayerForCurrentMove().getMoveFrom(middle,middle);

        assertEquals(g.getPlayerForCurrentMove(), players[0]);
        g.playerPutsFigure(cell);
        assertEquals(8, b.getNeighborsOf(oneLayer, middle, middle).size());

        assertEquals(g.getPlayerForCurrentMove(), players[1]);
        g.playerPutsFigure(cell);
        assertEquals(g.getPlayerForCurrentMove(), players[1]);
    }

    @Test
    public void testNodeCreation() throws Exception {
        int oneLayer = 2;
        int middle = b.SIZE / 2; //it's just the last move of the player last player

        Node root = new Node(b, middle, middle);
        assertEquals(g.getPlayerForCurrentMove(), players[0]);

        ArrayList<WeightedCell> potentialMoves = b.getNeighborsOf(oneLayer, middle, middle);

        AlfaBeta.createNodesForRoot(players[0].getOwnership(), root, potentialMoves, 3);
        assertEquals(24, root.getChildren().size());
        assertEquals(g.getPlayerForCurrentMove(), players[0]);
        WeightedCell c = new AlfaBeta(s).run(root, g.getPlayerForCurrentMove().getOwnership(), 3);
    }

    @Test
    public void testReallySmallBoard() throws Exception {
        b = new Board(5);

        //first col
        b.getCellAtIndex(0, 0).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(1, 0).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(2, 0).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(3, 0).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(4, 0).setOwnership(Constants.BLACK_CHAR);

        //second col
        b.getCellAtIndex(0, 1).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(1, 1).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(2, 1).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(4, 1).setOwnership(Constants.WHITE_CHAR);

        //third col
        b.getCellAtIndex(0, 2).setOwnership(Constants.WHITE_CHAR);
        b.getCellAtIndex(1, 2).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(4, 2).setOwnership(Constants.WHITE_CHAR);

        //fourth call
        b.getCellAtIndex(0, 3).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(4, 3).setOwnership(Constants.WHITE_CHAR);

        //fifth col
        b.getCellAtIndex(0, 4).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(1, 4).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(2, 4).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(3, 4).setOwnership(Constants.BLACK_CHAR);
        b.getCellAtIndex(4, 4).setOwnership(Constants.WHITE_CHAR);

        int lastMoveRow = 2, lastMoveCol = 1;

        Node root = new Node(b, lastMoveRow, lastMoveCol);

        ArrayList<WeightedCell> potentialMoves = b.getNeighborsOf(2, lastMoveRow, lastMoveCol);

        AlfaBeta.createNodesForRoot(players[0].getOwnership(), root, potentialMoves, 1);

        assertEquals(g.getPlayerForCurrentMove(), players[0]);

        WeightedCell c = new AlfaBeta(s).run(root, players[0].getOwnership(), 1);
        System.out.println(c.ROW + "," + c.COL + "->" + c.getOwnership() + " w:" + c.getWeight());
    }

    public void printBoard(final Board b) {
        for (int i = 0; i < b.SIZE; ++i) {
            for (int j = 0; j < b.SIZE; j++) {
                System.out.print(b.getOwnershipAt(i, j));
            }
            System.out.println();
        }
    }
}
