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

public class SimpleBoard implements Board {
    // Board
    private final int width ;
    private final int height;
    private int[][] currentGameMatrix;
    // Helpers
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    // Bricks
    private Brick currentBrick;
    private Queue<Brick> nextBricks;
    // Util
    private Point currentOffset;
    private final Score score;
    private boolean isFull;

    // Constructor
    public SimpleBoard(int rows, int cols) {
        this.width = cols;
        this.height = rows;
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

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


    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        NextShapeInfo nextShape = brickRotator.getNextShape();
        // Try rotation with kicks
        boolean success = tryApplyRotation(nextShape.getShape());
        if (success) {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
        return false;
    }

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


    @Override
    public boolean hardDropBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        while (!MatrixOperations.intersect(
                currentMatrix,
                brickRotator.getCurrentShape(),
                currentOffset.x,
                currentOffset.y + 1
        )) {
            currentOffset.y++;
        }
        return true;
    }

    @Override
    public void createNewBrick() {
        if (nextBricks.isEmpty()) {
            nextBricks.add(brickGenerator.getBrick());
            nextBricks.add(brickGenerator.getBrick());
            nextBricks.add(brickGenerator.getBrick());
        }
        currentBrick = nextBricks.poll();
        nextBricks.add(brickGenerator.getBrick());
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(3, 1);
        isFull =  MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), currentBrick.getColor(), (int) currentOffset.getX(), (int) currentOffset.getY(), getGhostY());
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

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

    @Override
    public ClearRow clearRow() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        score.addScore(clearRow.getScoreBonus());
        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public boolean isFull() {return isFull;}

    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        nextBricks = new ArrayDeque<>();
        score.reset();
        isFull = false;
        createNewBrick();
    }

    @Override
    public Queue<Brick> getNextBricks() {return nextBricks;}

}
