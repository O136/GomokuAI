package com.example.olegpatraschku.gobang;

import android.graphics.Color;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class GomokuGameTest {
    private GameStrategy strategy = new GomokuStrategy(StrategyType.ATTACK);
    private Rules rules = new GomokuRules();
    private Board board = new Board(Constants.BIG_BOARD);
    private Player human = new Player(Color.BLACK, Constants.BLACK_CHAR);
    private AIPlayer AI = new AIPlayer(Color.WHITE, Constants.WHITE_CHAR, new AIBrain(board, strategy));

    private Player p[] = new Player[]{ human, AI };

    private Game game = new Game(p, board, rules);

    public void setGameOrder(final int order) {
        while (true) if (game.updateCurrentMove() == order) break;
    }


    @Before
    public void before() throws Exception {
    }

    @Test
    public void testOrder() throws Exception {
        setGameOrder(0);
        assertEquals(human, game.getPlayerForCurrentMove());
        game.updateCurrentMove();
        assertEquals(AI, game.getPlayerForCurrentMove());
        game.updateCurrentMove();
        assertEquals(human, game.getPlayerForCurrentMove());
        game.updateCurrentMove();
        assertEquals(AI, game.getPlayerForCurrentMove());
        game.updateCurrentMove();

        for (int i = 0; i < Constants.BIG_BOARD * Constants.BIG_BOARD; ++i) {
            if (i % 2 == 1) assertEquals(AI, game.getPlayerForCurrentMove());
            else if (i % 2 == 0) assertEquals(human, game.getPlayerForCurrentMove());
            game.updateCurrentMove();
        }
    }

//    @Test
//    public void testAIMoves() throws Exception {
//        setGameOrder(0);
//        int i = 0;
//        int figurePutByHuman = 0;
//
//        while (figurePutByHuman < 5) {
//            if (game.playerPutsFigure(new WeightedCell(i, i, 0, game.getPlayerForCurrentMove().getOwnership()))) {
//                WeightedCell c = AI.putBrainToWork();
//                if (!game.playerPutsFigure(c))
//                ++figurePutByHuman;
//            }
//            ++i;
//        }
//        printBoard(board);
//    }

    @After
    public void after() throws Exception {
        p = null;
        assertNull(p);
    }

//    @Test
//    public void whenGameCreatedGameNotNull() {
//        Player[] p = returnCreatedArrayWith2Players();
//        Game g = new Game(p, new Board(10));
//        assertNotNull(g);
//    }
//
//
//    @Test
//    public void ifGetPlayerForCurrentMoveReturns2PlayersInCycle() {
//        Player[] p = returnCreatedArrayWith2Players();
//        Game g = new Game(p, new Board(10));
//        g.setRules(new GomokuRules());
//        assertEquals(p[0],g.getPlayerForCurrentMove());
//        g.playerPutsFigure(0,0);
//
//        assertEquals(p[1],g.getPlayerForCurrentMove());
//        g.playerPutsFigure(0,0);
//
//        assertNotEquals(p[0],g.getPlayerForCurrentMove());
//    }
//
//    @Test
//    public void ifFillBoardWithFiguresThenAddOneMoreIsFail() {
//        Player[] players = returnCreatedArrayWith2Players();
//
//        Rules r = new GomokuRules();
//        Board b = new Board(10);
//        Game g = new Game(players, b);
//        g.setRules(r);
//
//
//        for(int i = 0; i < 10; ++i){
//            for(int j = 0; j < 10; ++j) {
//                assertEquals(g.playerPutsFigure(i, j), true);
//            }
//        }
//
//        //testing if board is fully covered by figures
//        for(int i = 0; i < 10; ++i){
//            for(int j = 0; j < 10; ++j) {
//                assertEquals(g.playerPutsFigure(i, j), false);
//            }
//        }
//    }
//
//
//    @Test
//    public void TestRx() {
//
//    }
}