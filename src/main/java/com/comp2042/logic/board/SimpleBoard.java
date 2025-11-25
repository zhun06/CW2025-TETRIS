package com.comp2042.logic.board;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.moves.BrickRotator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.data.NextShapeInfo;
import com.comp2042.logic.data.Score;
import com.comp2042.logic.data.ViewData;

import java.awt.Point;
import java.util.*;

/**
 * A simple implementation of the {@link Board} interface.
 * Manages the current brick, upcoming bricks, board matrix, and {@link Score}.
 */
public class SimpleBoard implements Board {
    /** Board dimensions. */
    private final int width ;
    private final int height;
    /** Current game matrix (board state). */
    private int[][] currentGameMatrix;
    /** Brick generator and rotator helpers. */
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    /** Current active brick and upcoming bricks queue. */
    private Brick currentBrick;
    private Queue<Brick> nextBricks;
    /** Current brick offset and score. */
    private Point currentOffset;
    private Score score;
    /** Flag indicating if the board is full. */
    private boolean isFull;

    /**
     * Constructs a new SimpleBoard.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public SimpleBoard(int rows, int cols) {
        this.width = cols;
        this.height = rows;
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
    }

    /**{@inheritDoc} */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**{@inheritDoc} */
    @Override
    public void moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (!conflict) currentOffset = p;
    }

    /**{@inheritDoc} */
    @Override
    public void moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (!conflict) currentOffset = p;
    }

    /**{@inheritDoc} */
    @Override
    public void rotateLeftBrick() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        // Try rotation with kicks
        boolean success = tryApplyRotation(nextShape.getShape());
        if (success) brickRotator.setCurrentShape(nextShape.getPosition());
    }

    /**{@inheritDoc} */
    private boolean tryApplyRotation(int[][] nextShapeMatrix) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        for (int[] kick : brickRotator.KICK_OFFSETS) {
            int dx = kick[0];
            int dy = kick[1];

            boolean conflict = MatrixOperations.intersect(
                    currentMatrix,
                    nextShapeMatrix,
                    (int)currentOffset.getX() + dx,
                    (int)currentOffset.getY() + dy
            );

            if (!conflict) {
                currentOffset = new Point(
                        (int)currentOffset.getX() + dx,
                        (int)currentOffset.getY() + dy
                );
                return true;
            }
        }
        return false;
    }

    /**{@inheritDoc} */
    @Override
    public void hardDropBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        while (!MatrixOperations.intersect(
                currentMatrix,
                brickRotator.getCurrentShape(),
                currentOffset.x,
                currentOffset.y + 1
        )) {
            currentOffset.y++;
        }
    }

    /**{@inheritDoc} */
    @Override
    public void createNewBrick() {
        currentBrick = brickGenerator.getBrick();
        nextBricks = brickGenerator.getNextBricks();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(3, 1);
        isFull =  MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**{@inheritDoc} */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**{@inheritDoc} */
    @Override
    public ClearRow clearRow() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /**{@inheritDoc} */
    @Override
    public void setBoardMatrix(int[][] newBoardMatrix) {currentGameMatrix = newBoardMatrix;}

    /**{@inheritDoc} */
    @Override
    public void setBrickOffset(int offsetX, int offsetY) {this.currentOffset = new Point(offsetX, offsetY);}

    /**{@inheritDoc} */
    @Override
    public int[][] getBoardMatrix() {return currentGameMatrix;}

    @Override
    public Queue<Brick> getNextBricks() {return nextBricks;}

    /**{@inheritDoc} */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), currentBrick.getColor(), (int) currentOffset.getX(), (int) currentOffset.getY(), getGhostY());
    }

    /**{@inheritDoc} */
    @Override
    public int getGhostY() {
        int ghostY = currentOffset.y;
        while (!MatrixOperations.intersect(
                currentGameMatrix,
                brickRotator.getCurrentShape(),
                currentOffset.x,
                ghostY + 1
        )) {
            ghostY++;
        }
        return ghostY;
    }

    /**{@inheritDoc} */
    @Override
    public Score getScore() {return score;}

    /**{@inheritDoc} */
    @Override
    public boolean isFull() {return isFull;}

    /**{@inheritDoc} */
    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        nextBricks = new ArrayDeque<>();
        score = new Score();
        isFull = false;
        createNewBrick();
    }
}
