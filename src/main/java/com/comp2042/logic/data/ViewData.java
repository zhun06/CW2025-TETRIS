package com.comp2042.logic.data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current brick's visual data used for rendering:
 * its shape, color, position, and ghost-position.
 */
public final class ViewData {

    private final int[][] brickShape;
    private final int color;
    private final int xPosition;
    private final int yPosition;
    private final int ghostY;

    /**
     * @param brickShape the shape matrix of the brick
     * @param color brick color
     * @param xPosition x location on the board
     * @param yPosition y location on the board
     * @param ghostY y position of the ghost piece
     */
    public ViewData(int[][] brickShape, int color, int xPosition, int yPosition, int ghostY) {
        this.brickShape = brickShape;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ghostY = ghostY;
    }

    /**
     * Computes all filled cell coordinates of the brick.
     * @return list of absolute positions occupied by the brick
     */
    public List<Point> getCoordinates() {
        return extract(brickShape, xPosition, yPosition);
    }

    /**
     * Computes all filled cell coordinates of the ghost brick.
     * @return ghost block coordinates
     */
    public List<Point> getGhostCoordinates() {
        return extract(brickShape, xPosition, ghostY);
    }

    private List<Point> extract(int[][] brick, int posX, int posY) {
        List<Point> coords = new ArrayList<>();
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[0].length; j++) {
                if (brick[i][j] != 0) {
                    coords.add(new Point(j + posX, i + posY));
                }
            }
        }
        return coords;
    }

    /** @return raw matrix of the brick */
    public int[][] getBrickShape() { return brickShape; }

    /** @return brick's x-position */
    public int getxPosition() { return xPosition; }

    /** @return brick's y-position */
    public int getyPosition() { return yPosition; }

    /** @return fill color of the brick */
    public int getFillColor() { return color; }
}
