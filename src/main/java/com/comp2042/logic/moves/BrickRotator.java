package com.comp2042.logic.moves;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.data.NextShapeInfo;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    public int[][] KICK_OFFSETS = {
            {0, 0},    // try original
            {1, 0},    // right
            {-1, 0},   // left
            {0, -1},   // up
            {2, 0},    // far right
            {-2, 0}    // far left
    };

    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    public int[][] getCurrentShape() { return brick.getShapeMatrix().get(currentShape); }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
}
