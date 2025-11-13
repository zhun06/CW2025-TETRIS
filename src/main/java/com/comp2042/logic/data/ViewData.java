package com.comp2042.logic.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class ViewData {

    private final int[][] brickData;
    private final int color;
    private final int xPosition;
    private final int yPosition;

    public ViewData(int[][] brickData, int color, int xPosition, int yPosition) {
        this.brickData = brickData;
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public List<Point> getCoordinates() {
        List<Point> coordinates = new ArrayList<>();
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[0].length; j++) {
                if (brickData[i][j] != 0) {
                    coordinates.add(new Point(j + xPosition, i + yPosition));
                }
            }
        }
        return coordinates;
    }

    public int getFillColor() { return color; }
}
