package com.comp2042.renderers;

import com.comp2042.logic.board.Board;
import com.comp2042.logic.data.ViewData;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.managers.GameManager;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.awt.*;
import java.util.Arrays;

public class Renderer {
    private final Board board;
    private final GridPane gamePanel;

    private final Rectangle[][] rectangle;

    private final int ROWS = TetrisGame.ROWS;
    private final int COLS = TetrisGame.COLS;
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;

    // Constructor
    public Renderer(Board board) {
        this.board = board;
        this.gamePanel = GameManager.getGamePanel();
        this.rectangle = new Rectangle[ROWS][COLS];
        this.initializeBoard();
    }

    // Initialize board
    private void initializeBoard() {
        gamePanel.setBackground(Background.fill(Color.BLACK));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Rectangle rect = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle[i][j] = rect;
                gamePanel.add(rect, j, i);
            }
        }
    }

    // Render
    public void render(ViewData brick) {
        this.refreshBoard();
        this.renderBoard();
        this.renderBrick(brick);
    }

    // Render existing bricks
    public void renderBoard() {
        for (int i = 4; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board.getBoardMatrix()[i][j] != 0) {
                    rectangle[i][j].setArcWidth(9);
                    rectangle[i][j].setArcHeight(9);
                    rectangle[i][j].setFill(setFillColor(board.getBoardMatrix()[i][j]));
                }
            }
        }
    }

    // Render current brick
    public void renderBrick(ViewData brick) {
        for (Point p : brick.getCoordinates()) {
            rectangle[p.y][p.x].setArcWidth(9);
            rectangle[p.y][p.x].setArcHeight(9);
            rectangle[p.y][p.x].setFill(setFillColor(brick.getFillColor()));
        }
    }

    // Refresh board
    private  void refreshBoard() {
        for (Rectangle[] rects : rectangle) {
            for (Rectangle rect : rects) {
                rect.setArcWidth(0);
                rect.setArcHeight(0);
                rect.setFill(Color.BLACK);
            }
        }
        for (int i = 4; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                rectangle[i][j].setStrokeType(StrokeType.INSIDE);
                rectangle[i][j].setStroke(Color.DARKBLUE);
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

    private void printBoard(int[][] board) {
        for (int i = 0; i < ROWS; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }
}


