package com.comp2042.logic.games;

import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.Score;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.util.EventSource;

public class TetrisGame implements InputEventListener {
    public static int ROWS = 24;
    public static int COLS = 10;
    public static int BRICK_SIZE = 25;
    private final Board board = new SimpleBoard(ROWS, COLS);
    private boolean gameOver = false;


    @Override
    public void onDownEvent(MoveEvent event) {
        ClearRow clearRow = null;
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                gameOver = true;
            }
        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

    }

    @Override
    public void onLeftEvent(MoveEvent event) { board.moveBrickLeft(); }

    @Override
    public void onRightEvent(MoveEvent event) { board.moveBrickRight(); }

    @Override
    public void onRotateEvent(MoveEvent event) { board.rotateLeftBrick(); }

    @Override
    public void createNewGame() { board.newGame(); }

    // Getters
    public Board getBoard() {return board;}
    public ViewData getViewData() { return board.getViewData(); }
    public ClearRow getClearRow() { return board.clearRows(); }
    public Score getScore() { return board.getScore(); }
    public boolean isGameOver() { return gameOver; }

}