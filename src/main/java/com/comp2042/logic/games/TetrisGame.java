package com.comp2042.logic.games;

import com.comp2042.logic.data.*;
import com.comp2042.logic.time.Stopwatch;
import com.comp2042.logic.time.Timer;
import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.managers.GameManager;
import com.comp2042.util.*;

import java.util.Queue;

public class TetrisGame implements InputEventListener {
    // Game mode
    private GameMode gameMode;
    // Grid & sizing
    public static int ROWS = 24;
    public static int COLS = 10;
    public static int BRICK_SIZE = 25;
    // Final
    private final Board board;
    private GameResult gameResult;
    // Data
    private ClearRow clearRow;
    private SfxData sfxData;
    private TimeData timeData;
    private int fallSpeed = 400;
    private boolean gameOver = false;

    // Constructor
    public TetrisGame() {
        board = new SimpleBoard(ROWS, COLS); // Persistent
    }

    // Initializing game
    @Override
    public void createNewGame() {
        initialize();
        gameOver = false;
        board.newGame();
        gameMode.onGameStart();
        sfxData.update(SfxEvent.GAME_START);
    }

    private void initialize() {
        setGameMode();
        Timer timer = new Timer();
        Stopwatch stopwatch = new Stopwatch();
        timeData = new TimeData(timer, stopwatch);
        sfxData = new SfxData();
        gameResult = new GameResult();
    }

    private void setGameMode() {
        GameChoice gameChoice = GameManager.getCurrentGameChoice();
        switch (gameChoice) {
            case ZEN -> this.gameMode = new ZenMode(this);
            case FORTY_LINES -> this.gameMode = new FortyMode(this);
            case BLITZ -> this.gameMode = new BlitzMode(this);
        }
    }

    // Moves
    @Override
    public void onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            onMerge();
        } else {
            if (event.getEventSource() == EventSource.USER) board.getScore().onDownEvent(); // User
            else gameMode.onTick(); // Thread
        }
    }

    @Override
    public void onHardDropEvent(MoveEvent event) {
        board.hardDropBrick();
        board.getScore().onHardDropEvent();
        onMerge();
        sfxData.update(SfxEvent.HARD_DROP);
    }

    private void onMerge () {
        board.mergeBrickToBackground();
        board.createNewBrick();

        clearRow = board.clearRow();
        if (clearRow.getLinesRemoved() > 0) { // onLineClear
            board.getScore().onLineClear(clearRow);
            gameMode.onLineClear();
            sfxData.update(SfxEvent.LINE_CLEAR);
        }

        if (board.isFull()) { // onBoardFull
            gameMode.onBoardFull();
            this.onGameOver();
        }
    }

    public void onGameOver() {
        gameResult.update(board.getScore(), timeData.getElapsedTime());
        sfxData.update(SfxEvent.GAME_OVER);
        gameOver = true;
    }

    public void onLevelUp() {sfxData.update(SfxEvent.LEVEL_UP);}

    @Override
    public void onLeftEvent(MoveEvent event) { board.moveBrickLeft(); }

    @Override
    public void onRightEvent(MoveEvent event) { board.moveBrickRight(); }

    @Override
    public void onRotateEvent(MoveEvent event) { board.rotateLeftBrick(); }

    // Setters
    public void setFallSpeed(int fallSpeed) {this.fallSpeed = fallSpeed;} // Delay tick
    public void setGameState(GameState gameState) {gameResult.setGameState(gameState);} // GAME_OVER / WIN / LOSE

    // Getters
    // Board & brick
    public Board getBoard() { return board; }
    public ViewData getViewData() { return board.getViewData(); }
    public Queue<Brick> getNextBricks() {return board.getNextBricks();}

    // Score and stats
    public Score getScore() { return board.getScore(); }
    public TimeData getTimeData() { return timeData; }
    public int getFallSpeed() { return fallSpeed; }
    public boolean isGameOver() { return gameOver; }
    public GameResult getGameResult() { return gameResult; }

    // Special effects
    public ClearRow getClearRow() {return clearRow;}
    public SfxData getSfxData() { return sfxData; }

    // Pause & Resume
    public void pause() {timeData.onPause();}
    public void resume() {timeData.onResume();}

}