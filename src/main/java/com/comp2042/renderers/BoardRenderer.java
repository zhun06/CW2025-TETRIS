package com.comp2042.renderers;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BoardRenderer {

    private final GridPane boardPanel;
    private final Rectangle[][] rectangle;
    private static final int BRICK_SIZE = 20;

    public BoardRenderer(GridPane boardPanel, int rows, int cols) {
        this.boardPanel = boardPanel;
        this.rectangle = new Rectangle[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rect.setFill(Color.TRANSPARENT);
                rect.setArcWidth(9);
                rect.setArcHeight(9);
                rectangle[i][j] = rect;
                boardPanel.add(rect, j, i);
            }
        }
    }

    private Paint getFillColor(int value) {
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

    public void updateBoard(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                rectangle[i - 2][j].setFill(getFillColor(board[i][j]));
            }
        }
    }

}


