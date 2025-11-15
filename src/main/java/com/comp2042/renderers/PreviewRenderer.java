package com.comp2042.renderers;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.games.TetrisGame;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PreviewRenderer {
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;
    private final List<GridPane> grids;
    private final List<Rectangle[][]> rectangles = new ArrayList<>(3);

    // Constructor
    public PreviewRenderer(GameController gameController) {
        grids = gameController.getPreviewPanels();
        this.initialize();
    }

    // Initialize preview
    private void initialize() {
        for (int i = 0; i < 3; i++) {
            Rectangle[][] grid = new Rectangle[6][6];
            rectangles.add(grid);

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    grid[row][col] = rect;
                    rect.setStrokeWidth(1);
                    rect.setStrokeType(StrokeType.INSIDE);
                    grids.get(i).add(rect, col, row);
                }
            }
        }
    }

    public void render(Queue<Brick> brickPreview) {
        refreshPreview();
        Brick[] bricks = brickPreview.toArray(new Brick[0]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    int value = bricks[i].getShapeMatrix().getFirst()[j][k];
                    if (value != 0 ){
                        rectangles.get(i)[j+1][k+1].setArcWidth(9);
                        rectangles.get(i)[j+1][k+1].setArcHeight(9);
                        rectangles.get(i)[j+1][k+1].setFill(setFillColor(value));
                        rectangles.get(i)[j+1][k+1].setStroke(Color.DARKBLUE);
                    }
                }
            }
        }
    }

//    Refresh board
    private  void refreshPreview() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    rectangles.get(i)[j][k].setStroke(Color.BLACK);
                    rectangles.get(i)[j][k].setFill(Color.BLACK);
                }
            }
        }
    }

    private Paint setFillColor(int value) {
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
}
