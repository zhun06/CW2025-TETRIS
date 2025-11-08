package com.comp2042.renderers;

import com.comp2042.logic.data.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BrickRenderer {

    private final GridPane brickPanel;
    private final Rectangle[][] rectangles;
    private static final int BRICK_SIZE = 20;

    public BrickRenderer(GridPane brickPanel, int rows, int cols) {
        this.brickPanel = brickPanel;
        this.rectangles = new Rectangle[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setArcWidth(9);
                rect.setArcHeight(9);
                rectangles[i][j] = rect;
                brickPanel.add(rect, j, i);
            }
        }
    }

    private Paint getColor(int value) {
        return switch (value) {
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.TRANSPARENT;
        };
    }

    public void updateBrick(ViewData piece, GridPane gamePanel) {
        int[][] data = piece.getBrickData();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                rectangles[i][j].setFill(getColor(data[i][j]));
            }
        }

        double offsetX = gamePanel.getLayoutX() + piece.getxPosition() * (brickPanel.getVgap() + BRICK_SIZE);
        double offsetY = -42 + gamePanel.getLayoutY() + piece.getyPosition() * (brickPanel.getHgap() + BRICK_SIZE);

        brickPanel.setLayoutX(offsetX);
        brickPanel.setLayoutY(offsetY);
    }

}
