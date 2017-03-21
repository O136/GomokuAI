package com.example.olegpatraschku.gobang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.olegpatraschku.gobang.models.AIBrain;
import com.example.olegpatraschku.gobang.models.AIPlayer;
import com.example.olegpatraschku.gobang.models.Board;
import com.example.olegpatraschku.gobang.models.Constants;
import com.example.olegpatraschku.gobang.models.Direction;
import com.example.olegpatraschku.gobang.models.Game;
import com.example.olegpatraschku.gobang.models.GameOverException;
import com.example.olegpatraschku.gobang.models.GameStrategy;
import com.example.olegpatraschku.gobang.models.GomokuRules;
import com.example.olegpatraschku.gobang.models.GomokuStrategy;
import com.example.olegpatraschku.gobang.models.Player;
import com.example.olegpatraschku.gobang.models.Rules;
import com.example.olegpatraschku.gobang.models.StrategyType;
import com.example.olegpatraschku.gobang.models.WeightedCell;
import com.example.olegpatraschku.gobang.views.BoardView;
import com.example.olegpatraschku.gobang.views.FigureView;
import com.example.olegpatraschku.gobang.views.TableLayoutViewListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class acts as the GameController
 */

public class GameController extends AppCompatActivity implements Observer, TableLayoutViewListener {

    private BoardView boardView;
    private Player[] players;
    private Game game;
    private Board board;
    private Rules rules;
    private GameStrategy strategy;
    private Player human;
    private AIPlayer AI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (BoardView) findViewById(R.id.board_view);

        strategy = new GomokuStrategy(StrategyType.ATTACK);
        rules = new GomokuRules();
        board = new Board(Constants.SMALL_BOARD);

        /**
         * first player always starts with BLACK_CHAR character no matter what
         * BLACK_CHAR represents the black figures
         */

        human = new Player(Color.BLACK, Constants.BLACK_CHAR);
        AI = new AIPlayer(Color.WHITE, Constants.WHITE_CHAR, new AIBrain(board, strategy));

        players = new Player[2];

        boolean blackFirst =
                getIntent().getBooleanExtra(getString(R.string.black_color_option), false);
        boolean onlyOnePlayer =
                getIntent().getBooleanExtra(getString(R.string.one_player_option), false);

        game = new Game(players, board, rules);
        game.addObserver(this);
        boardView.setTableLayoutViewListener(this);

        if(onlyOnePlayer) {
            players[0] = human;
            players[1] = AI;
            if(!blackFirst) {
                human.setColor(Color.WHITE);
                human.setOwnership(Constants.WHITE_CHAR);
                AI.setColor(Color.BLACK);
                AI.setOwnership(Constants.BLACK_CHAR);
                game.updateCurrentMove();
                WeightedCell weightedCell = new WeightedCell(board.SIZE / 2, board.SIZE / 2,
                        0, game.getPlayerForCurrentMove().getOwnership());
                game.playerPutsFigure(weightedCell);
                game.updateCurrentMove();
            }
        }
        else {
            players[0] = human;
            players[1] = new Player(Color.WHITE, Constants.WHITE_CHAR);
        }
    }

    public void printBoard(Board b) {
        for(int i=0; i < board.SIZE; ++i) {
            System.out.println();
            for (int j = 0; j < board.SIZE; ++j)
                System.out.print("  " + b.getCellAtIndex(i, j).getOwnership());
        }
        System.out.println();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof Game) {
            WeightedCell cell = (WeightedCell) data;
            final int color = game.getPlayerForCurrentMove().getColor();
            getFigureViewAt(cell).setCircleColor(color);

            try {
                rules.checkFinalMove(board, cell);
            } catch (GameOverException g) {
                if (g.getCells() == null)
                    Toast.makeText(this, "no more space", Toast.LENGTH_SHORT).show();
                else {
                    //draw the sweet lines
                    Toast.makeText(this, "GAME OVER!", Toast.LENGTH_SHORT).show();
                    drawLinesForDirectionWithColor(g.getCells(), Color.YELLOW, g.getDirection());
                }
                return;
            }
            AI.increaseSightFrom(cell.ROW, cell.COL);
        }
    }

    private void drawLinesForDirectionWithColor
            (ArrayList<WeightedCell> cells, int color, Direction direction) {
        for (WeightedCell c : cells)
            if (c.getOwnership() == game.getPlayerForCurrentMove().getOwnership())
                getFigureViewAt(c).setLineDirectionWithColor(direction, color);
    }

    private FigureView getFigureViewAt(WeightedCell cell) {
        TableRow row = (TableRow) boardView.tableLayout.getChildAt(cell.ROW);
        return (FigureView) row.getChildAt(cell.COL);
    }

    /**
     * this method will be only triggered by a human player not AI
     * @param f figure which was selected
     */
    @Override
    public void FigureViewSelected(FigureView f) {
        int row = f.getRow();
        int col = f.getCol();

        WeightedCell cell = game.getPlayerForCurrentMove().getMoveFrom(row, col);

        System.out.println(game.getPlayerForCurrentMove().getClass().toString());

        if (!game.playerPutsFigure(cell)) {
            Toast.makeText(this, "Can't do that", Toast.LENGTH_SHORT).show();
            return;
        }
        game.updateCurrentMove();
        WeightedCell aiCell = game.getPlayerForCurrentMove().getMoveFrom(row, col);
        game.playerPutsFigure(aiCell);
        game.updateCurrentMove();
    }
}