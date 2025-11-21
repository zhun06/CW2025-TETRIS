package com.comp2042.renderers;

import com.comp2042.colors.*;
import com.comp2042.controllers.GameController;
import com.comp2042.logic.board.Board;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.managers.SceneManager;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.awt.Point;

public class BoardRenderer {
    private final Board board;
    private final GridPane gameBoard;

    private final Rectangle[][] rectangle;

    private final int ROWS = TetrisGame.ROWS;
    private final int COLS = TetrisGame.COLS;
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;

    private ThemeColor themeColor; // Interface (Current theme)

    // Constructor
    public BoardRenderer(GameController gameController, Board board) {
        this.gameBoard = gameController.getGameBoard();
        this.board = board;
        this.rectangle = new Rectangle[ROWS][COLS];
        this.initializeBoard();
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

    // Initialize board
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle[i][j] = rect;
                rect.setStrokeWidth(1);
                rect.setStrokeType(StrokeType.INSIDE);
                gameBoard.add(rect, j, i);
            }
        }
    }

    // Render
    public void render(ViewData brick) {
        this.refreshBoard();
        this.renderBoard();
        this.renderGhost(brick);
        this.renderBrick(brick);
    }

    // Render existing bricks
    private void renderBoard() {
        for (int i = 4; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board.getBoardMatrix()[i][j] != 0) {
                    rectangle[i][j].setStrokeWidth(2);
                    rectangle[i][j].setArcWidth(9);
                    rectangle[i][j].setArcHeight(9);
                    rectangle[i][j].setFill(themeColor.getBrickColor(board.getBoardMatrix()[i][j]));
                    rectangle[i][j].setStroke(themeColor.getBrickOutline(board.getBoardMatrix()[i][j]));
                }
            }
        }
    }

    // Render current brick
    private void renderBrick(ViewData brick) {
        for (Point p : brick.getCoordinates()) {
            rectangle[p.y][p.x].setStrokeWidth(2);
            rectangle[p.y][p.x].setArcWidth(9);
            rectangle[p.y][p.x].setArcHeight(9);
            rectangle[p.y][p.x].setFill(themeColor.getBrickColor(brick.getFillColor()));
            rectangle[p.y][p.x].setStroke(themeColor.getBrickOutline(brick.getFillColor()));
        }
    }

    // Render ghost brick
    private void renderGhost(ViewData brick) {
        for (Point p : brick.getGhostCoordinates()) {
            rectangle[p.y][p.x].setStrokeWidth(2);
            rectangle[p.y][p.x].setArcWidth(9);
            rectangle[p.y][p.x].setArcHeight(9);
            rectangle[p.y][p.x].setStroke(themeColor.getBrickOutline(brick.getFillColor()));
            rectangle[p.y][p.x].setFill(themeColor.getGhostColor(brick.getFillColor()));
        }
    }


    // Refresh board
    private  void refreshBoard() {
        for (Rectangle[] rects : rectangle) {
            for (Rectangle rect : rects) {
                rect.setStrokeWidth(1);
                rect.setArcWidth(0);
                rect.setArcHeight(0);
                rect.setStroke(themeColor.getBoardColor());
                rect.setFill(themeColor.getBoardColor());
            }
        }
        for (int i = 4; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                rectangle[i][j].setStroke(themeColor.getGridColor());
            }
        }
    }

}