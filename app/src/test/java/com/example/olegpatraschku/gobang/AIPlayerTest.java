package com.example.olegpatraschku.gobang;

/**
 * Created by Oleg Patraschku on 5/26/2016.
 */

public class AIPlayerTest {
/*
    Rules r = new GomokuRules();
    Board b = new Board(15);
    Player[] p = createPlayers();
    Game g = new Game(p, b);
    AIPlayer ai = (AIPlayer)p[0];

    public AIPlayerTest() {
        g.setRules(r);
        g.addObserver(ai);
    }

    public Player[] createPlayers() {
        Player[] players = new Player[] {
                new AIPlayer(Color.BLACK),
                new Player(Color.WHITE)
        };
        return players;
    }


    @Test
    public void SomeNeighbors() {
        g.equals(ai.getPotentialMoves().size(), 0);

        g.playerPutsFigure(0,0);
        assertEquals(ai.getPotentialMoves().size(), 8);
        g.playerPutsFigure(0,b.SIZE - 1);
        assertEquals(ai.getPotentialMoves().size(), 16);
        g.playerPutsFigure(b.SIZE - 1,0);
        assertEquals(ai.getPotentialMoves().size(), 24);
        g.playerPutsFigure(b.SIZE - 1,b.SIZE - 1);
        assertEquals(ai.getPotentialMoves().size(), 32);


        g.playerPutsFigure(7,7);
        assertEquals(ai.getPotentialMoves().size(), 56); //24 + 32 = 56

        g.playerPutsFigure(10,10);
        assertEquals(ai.getPotentialMoves().size(), 75); // 56 + 19 = 75
    }

    @Test
    public void AllNeighbors() {
        for (int i = 0; i < b.SIZE; ++i) {
            for (int j = 0; j < b.SIZE; j++) {
                g.playerPutsFigure(i, j);
            }
        }
        assertEquals(ai.getPotentialMoves().size(), 0);
    }
    */
}