package com.comp2042.logic.games;

import com.comp2042.logic.Time.Stopwatch;
import com.comp2042.logic.Time.Stopwatch;
import com.comp2042.logic.Time.Timer;
import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.managers.GameManager;
import com.comp2042.logic.data.*;
import com.comp2042.util.*;


import java.time.Duration;
import java.util.Queue;

public class TetrisGame implements InputEventListener {
    // Game mode
    private GameMode gameMode;
    // Grid & sizing
    public static int ROWS = 24;
    public static int COLS = 10;
    public static int BRICK_SIZE = 25;
    private final Board board = new SimpleBoard(ROWS, COLS);
    // Util
    private Timer timer;
    private Stopwatch stopWatch;
    private int fallSpeed = 400; // milliseconds per update
    private boolean gameOver = false;
    private GameState result;

    @Override
    public void onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            onMerge();
        } else {
            if (event.getEventSource() == EventSource.USER) board.getScore().addScore(1);
            else gameMode.onTick();
        }
    }

    @Override
    public void onHardDropEvent(MoveEvent event) {
        board.getScore().addScore(10);
        board.hardDropBrick();
        onMerge();
    }

    private void onMerge () {
        board.mergeBrickToBackground();
        board.createNewBrick();

        ClearRow clearRow = board.clearRow();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().addRowsCleared(clearRow.getLinesRemoved());
            board.getScore().addScore(clearRow.getScoreBonus());
            gameMode.onLineClear();
        }
        if (board.isFull() || gameOver) {
            board.getScore().setHighScore();
            result = GameState.GAME_OVER;
            gameOver = true;
            gameMode.onGameOver();
        }
    }

    @Override
    public void onLeftEvent(MoveEvent event) { board.moveBrickLeft(); }

    @Override
    public void onRightEvent(MoveEvent event) { board.moveBrickRight(); }

    @Override
    public void onRotateEvent(MoveEvent event) { board.rotateLeftBrick(); }

    @Override
    public void createNewGame() {
        initialize();
        gameOver = false;
        board.newGame();
        gameMode.onGameStart();
    }

    private void initialize() {
        setGameMode();
        timer = new Timer();
        stopWatch = new Stopwatch();
    }

    // Setters
    private void setGameMode() {
        GameChoice gameChoice = GameManager.getCurrentGameChoice();
        switch (gameChoice) {
            case ZEN -> this.gameMode = new ZenMode(this);
            case FORTY_LINES -> this.gameMode = new FortyMode(this);
            case BLITZ -> this.gameMode = new BlitzMode(this);
        }
    }
    public void setFallSpeed(int fallSpeed) {this.fallSpeed = fallSpeed;}
    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
    public void setResult(GameState result) {this.result = result;}


    // Getters
    public Board getBoard() { return board; }
    public ViewData getViewData() { return board.getViewData(); }
    public Score getScore() { return board.getScore(); }
    public Queue<Brick> getNextBricks() {return board.getNextBricks();}
    public int getFallSpeed() { return fallSpeed; }
    public boolean isGameOver() { return gameOver; }
    public GameState getResult() { return result; }

    // Pause & Resume
    public void pause() {
        timer.pause();
        stopWatch.pause();
    }
    public void resume() {
        timer.resume();
        stopWatch.resume();
    }

    // Timer (count down)
    public void startTimer(Duration duration) {timer.start(duration);}
    public Duration getRemainingTime() {return timer.getRemaining();}
    public boolean isTimeUp() {return timer.isTimeUp();}

    // Stop watch (count up)
    public void startStopwatch() {stopWatch.start();}
    public Duration getElapsedTime() {return stopWatch.getElapsed();}
}