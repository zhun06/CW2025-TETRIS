package com.comp2042.logic.data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class ViewData {
    private final int[][] brickData;
    private final int color;
    private final int xPosition;
    private final int yPosition;
    private final int ghostY;

    public ViewData(int[][] brickData, int color, int xPosition, int yPosition, int ghostY) {
        this.brickData = brickData;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ghostY = ghostY;
    }

    public List<Point> getCoordinates() {
        return extract(brickData, xPosition, yPosition);
    }

    public List<Point> getGhostCoordinates() {
        return extract(brickData, xPosition, ghostY);
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

    public int getFillColor() { return color; }
}
