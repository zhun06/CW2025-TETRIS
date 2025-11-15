package com.comp2042.logic.games;

import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.Score;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.util.EventSource;

import java.util.Queue;

public class TetrisGame implements InputEventListener {
    // Grid & sizing
    public static int ROWS = 24;
    public static int COLS = 10;
    public static int BRICK_SIZE = 25;
    private final Board board = new SimpleBoard(ROWS, COLS);
    // Game over
    private boolean gameOver = false;

    @Override
    public void onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            onMerge();
        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().addScore(1);
            }
        }
    }

    @Override
    public void onHardDropEvent(MoveEvent event) {
        board.getScore().addScore(10);
        board.hardDropBrick();
        onMerge();
    }

    @Override
    public void onLeftEvent(MoveEvent event) { board.moveBrickLeft(); }

    @Override
    public void onRightEvent(MoveEvent event) { board.moveBrickRight(); }

    @Override
    public void onRotateEvent(MoveEvent event) { board.rotateLeftBrick(); }

    @Override
    public void createNewGame() {
        gameOver = false;
        board.newGame();
    }

    private void onMerge () {
        board.mergeBrickToBackground();
        board.createNewBrick();
        ClearRow clearRow = board.clearRow();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().addRowsCleared(clearRow.getLinesRemoved());
            board.getScore().addScore(clearRow.getScoreBonus());
        }
        if (board.isFull()) {
            board.getScore().setHighScore();
            gameOver = true;
        }
    }

    // Getters
    public Board getBoard() { return board; }
    public ViewData getViewData() { return board.getViewData(); }
    public Score getScore() { return board.getScore(); }
    public Queue<Brick> getNextBricks() {return board.getNextBricks();}
    public boolean isGameOver() { return gameOver; }

}