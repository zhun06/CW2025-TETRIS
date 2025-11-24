package com.comp2042.logic.board;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.Score;
import com.comp2042.logic.data.ViewData;

import java.util.Queue;

public interface Board {

    boolean moveBrickDown();

    void moveBrickLeft();

    void moveBrickRight();

    void rotateLeftBrick();

    void hardDropBrick();

    void createNewBrick();

    void setBoardMatrix(int[][] newBoardMatrix);

    void setBrickOffset(int offsetX, int offsetY);

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    int getGhostY();

    ClearRow clearRow();

    Queue<Brick> getNextBricks();

    Score getScore();

    boolean isFull();

    void newGame();

}
