package com.comp2042.logic.data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class ViewData {
    private final int[][] brickShape;
    private final int color;
    private final int xPosition;
    private final int yPosition;
    private final int ghostY;

    public ViewData(int[][] brickShape, int color, int xPosition, int yPosition, int ghostY) {
        this.brickShape = brickShape;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ghostY = ghostY;
    }

    public List<Point> getCoordinates() {
        return extract(brickShape, xPosition, yPosition);
    }

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
    public int[][] getBrickShape() {return brickShape;}

    public int getxPosition() {return xPosition;}

    public int getyPosition() {return yPosition;}

    public int getFillColor() { return color; }
}
