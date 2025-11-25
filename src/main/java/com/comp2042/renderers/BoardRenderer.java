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

/**
 * Renders the main Tetris game board with current bricks, ghost bricks, and static blocks.
 */
public class BoardRenderer {

    private final Board board;
    private final GridPane gameBoard;
    private final Rectangle[][] rectangle;
    private final int ROWS = TetrisGame.ROWS;
    private final int COLS = TetrisGame.COLS;
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;
    private ThemeColor themeColor;

    /**
     * Constructor.
     * @param gameController the game controller containing the game board
     * @param board the Tetris board model
     */
    public BoardRenderer(GameController gameController, Board board) {
        this.gameBoard = gameController.getGameBoard();
        this.board = board;
        this.rectangle = new Rectangle[ROWS][COLS];
        this.initializeBoard();
    }

    /** Initializes the board colors when starting a new game. */
    public void onNewGame() { this.initializeColor(); }

    /** Sets the theme color scheme based on current scene theme. */
    private void initializeColor() {
        switch (SceneManager.getTheme()) {
            case CANDY -> themeColor = new CandyColor();
            case MINION -> themeColor = new MinionColor();
            case NATURE -> themeColor = new NatureColor();
            case NEON -> themeColor = new NeonColor();
            case OCEAN -> themeColor = new OceanColor();
            case SUNSET -> themeColor = new SunsetColor();
        }
    }

    /** Initializes the board grid with empty rectangles. */
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

    /**
     * Renders the board including current bricks and ghost bricks.
     * @param brick the current moving brick data
     */
    public void render(ViewData brick) {
        this.refreshBoard();
        this.renderBoard();
        this.renderGhost(brick);
        this.renderBrick(brick);
    }

    /** Renders all static bricks on the board. */
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

    /** Renders the current moving brick. */
    private void renderBrick(ViewData brick) {
        for (Point p : brick.getCoordinates()) {
            rectangle[p.y][p.x].setStrokeWidth(2);
            rectangle[p.y][p.x].setArcWidth(9);
            rectangle[p.y][p.x].setArcHeight(9);
            rectangle[p.y][p.x].setFill(themeColor.getBrickColor(brick.getFillColor()));
            rectangle[p.y][p.x].setStroke(themeColor.getBrickOutline(brick.getFillColor()));
        }
    }

    /** Renders the ghost brick shadow at the predicted landing position. */
    private void renderGhost(ViewData brick) {
        for (Point p : brick.getGhostCoordinates()) {
            rectangle[p.y][p.x].setStrokeWidth(2);
            rectangle[p.y][p.x].setArcWidth(9);
            rectangle[p.y][p.x].setArcHeight(9);
            rectangle[p.y][p.x].setStroke(themeColor.getBrickOutline(brick.getFillColor()));
            rectangle[p.y][p.x].setFill(themeColor.getGhostColor(brick.getFillColor()));
        }
    }

    /** Refreshes the board to clear previous brick and ghost visuals. */
    private void refreshBoard() {
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