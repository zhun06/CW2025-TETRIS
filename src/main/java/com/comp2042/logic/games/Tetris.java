package com.comp2042.logic.games;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.board.Board;
import com.comp2042.logic.board.SimpleBoard;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.DownData;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.moves.MoveEvent;
import com.comp2042.util.EventSource;

public class Tetris implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GameController viewGameController;

    public Tetris(GameController c) {
        viewGameController = c;
        board.createNewBrick();
        viewGameController.setEventListener(this);
        viewGameController.initGameView(board.getBoardMatrix(), board.getViewData());
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

            viewGameController.refreshGameBackground(board.getBoardMatrix());

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
        viewGameController.refreshGameBackground(board.getBoardMatrix());
    }
}