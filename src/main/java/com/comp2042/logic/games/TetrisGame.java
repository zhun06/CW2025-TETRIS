package com.comp2042.logic.games;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.DownData;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.util.EventSource;

public class TetrisGame implements InputEventListener {
    public static int ROWS = 24;
    public static int COLS = 10;
    public static int BRICK_SIZE = 25;

    private final Board board = new SimpleBoard(ROWS, COLS);

    private final GameController viewGameController;

    public TetrisGame(GameController c) {
        viewGameController = c;
        board.createNewBrick();
        viewGameController.setEventListener(this);
        viewGameController.initGameView(board);
        viewGameController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGameController.gameOver();
            }

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
    }
}