package com.comp2042.logic.games;

import com.comp2042.util.GameState;

import java.time.Duration;

/**
 * Blitz mode: a 60-second challenge where the player clears as many rows as possible.
 * Timer expiration ends the game regardless of board state.
 */
public class BlitzMode implements GameMode {

    private final TetrisGame game;
    private final int fallSpeed = 150;

    /**
     * Creates a BlitzMode instance associated with a Tetris game.
     * @param game the TetrisGame instance
     */
    public BlitzMode(TetrisGame game) {
        this.game = game;
    }

    /**Starts the countdown timer and initializes the fixed fall speed.*/
    @Override
    public void onGameStart() {
        game.setFallSpeed(fallSpeed);
        game.getTimeData().startTimer(Duration.ofSeconds(60));
    }

    /**Ends the game when the timer expires.*/
    @Override
    public void onTick() {
        if (game.getTimeData().isTimeUp()) {
            game.setGameState(GameState.GAME_OVER);
            game.onGameOver();
        }
    }

    /**Ends the game if the board becomes full before time runs out.*/
    @Override
    public void onBoardFull() {game.setGameState(GameState.GAME_OVER);}
}
