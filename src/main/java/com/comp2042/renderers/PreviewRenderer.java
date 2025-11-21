package com.comp2042.renderers;

import com.comp2042.colors.*;
import com.comp2042.controllers.GameController;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.managers.SceneManager;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PreviewRenderer {
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;
    private final List<GridPane> grids;
    private final List<Rectangle[][]> rectangles = new ArrayList<>();
    private ThemeColor themeColor;

    // Constructor
    public PreviewRenderer(GameController gameController) {
        grids = gameController.getPreviewGrids();
        this.initializePreview();
    }

    // On new game
    public void onNewGame() {this.initializeColor();}

    // Get color scheme
    private void initializeColor() {
        switch (SceneManager.getTheme()) {
            case CANDY -> themeColor = new CandyColor();
            case NATURE -> themeColor = new NatureColor();
            case NEON -> themeColor = new NeonColor();
        }
    }

    // Initialize preview
    private void initializePreview() {
        for (int i = 0; i < 3; i++) {
            Rectangle[][] grid = new Rectangle[6][6];
            rectangles.add(grid);

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    grid[row][col] = rect;
                    rect.setStrokeWidth(2);
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
                        rectangles.get(i)[j+1][k+1].setFill(themeColor.getBrickColor(value));
                        rectangles.get(i)[j+1][k+1].setStroke(themeColor.getBrickOutline(value));
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
                    rectangles.get(i)[j][k].setArcWidth(0);
                    rectangles.get(i)[j][k].setArcHeight(0);
                    rectangles.get(i)[j][k].setStroke(themeColor.getPreviewColor());
                    rectangles.get(i)[j][k].setFill(themeColor.getPreviewColor());
                }
            }
        }
    }


}
