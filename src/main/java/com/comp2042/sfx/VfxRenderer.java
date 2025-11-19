package com.comp2042.sfx;

import com.comp2042.colors.CandyColor;
import com.comp2042.colors.NatureColor;
import com.comp2042.colors.NeonColor;
import com.comp2042.colors.ThemeColor;
import com.comp2042.logic.data.ClearRow;
import com.comp2042.logic.games.TetrisGame;
import com.comp2042.managers.SceneManager;
import com.comp2042.util.SfxEvent;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

public class VfxRenderer {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private ThemeColor themeColor;

    private final int ROWS = TetrisGame.ROWS;
    private final int COLS = TetrisGame.COLS;
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;

    private final double BOARD_WIDTH = COLS * BRICK_SIZE;
    private final double BOARD_HEIGHT = ROWS * BRICK_SIZE;

    public VfxRenderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.initialize();
    }

    private void initialize() {
        switch (SceneManager.getTheme()) {
            case NEON -> this.themeColor = new NeonColor();
            case NATURE ->  this.themeColor = new NatureColor();
            case CANDY ->  this.themeColor = new CandyColor();
        }
    }

    // Main entry
    public void render(SfxEvent event, ClearRow clearRow) {
        switch (event) {
            case GAME_START -> onStart();
            case LINE_CLEAR -> onLineClear(clearRow);
            case LEVEL_UP -> onLevelUp();
            case GAME_OVER -> onGameOver();
        }
    }

    // GAME START
    public void onStart() {
        DoubleProperty alpha = new SimpleDoubleProperty(0);

        alpha.addListener((obs, oldA, a) -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Multiply overlay alpha by animation alpha for smooth fade-in/out
            Color overlay = (Color) themeColor.getOverlayColor();
            gc.setFill(Color.color(
                    overlay.getRed(),
                    overlay.getGreen(),
                    overlay.getBlue(),
                    overlay.getOpacity() * a.doubleValue()
            ));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(alpha, 0)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(alpha, 1.0)), // fade in smoothly
                new KeyFrame(Duration.seconds(1.2), new KeyValue(alpha, 0))    // fade out
        );

        tl.setOnFinished(e -> gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()));
        tl.play();
    }


    // LINE CLEAR — Explosion + floating popup text
    public void onLineClear(ClearRow clearRow) {
        if (clearRow == null) return;
        List<Integer> rows = clearRow.getClearedRows();
        if (rows.isEmpty()) return;

        int removed = clearRow.getLinesRemoved();

        String text = switch (removed) {
            case 1 -> "SINGLE";
            case 2 -> "DOUBLE";
            case 3 -> "TRIPLE";
            case 4 -> "TETRIS!";
            default -> removed + " LINES";
        };

        // EXPLOSION
        DoubleProperty alpha = new SimpleDoubleProperty(1.0);
        DoubleProperty expand = new SimpleDoubleProperty(0);

        alpha.addListener((obs, oldA, a) -> drawLineClear(rows, a.doubleValue(), expand.get()));
        expand.addListener((obs, oldE, e) -> drawLineClear(rows, alpha.get(), e.doubleValue()));

        Timeline explosion = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(alpha, 1.0),
                        new KeyValue(expand, 0)
                ),
                new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(alpha, 0.0),
                        new KeyValue(expand, 25)
                )
        );

        // POPUP TEXT FLOATING UPWARD
        double startY = BOARD_HEIGHT / 2;
        double endY = startY - 50;

        DoubleProperty popupAlpha = new SimpleDoubleProperty(1.0);
        DoubleProperty popupY = new SimpleDoubleProperty(startY);

        popupAlpha.addListener((obs, oldA, a) -> drawPopup(text, a.doubleValue(), popupY.get()));
        popupY.addListener((obs, oldY, y) -> drawPopup(text, popupAlpha.get(), y.doubleValue()));

        Timeline popup = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(popupAlpha, 1.0),
                        new KeyValue(popupY, startY)
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(popupAlpha, 0.0),
                        new KeyValue(popupY, endY)
                )
        );

        popup.setOnFinished(e -> gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()));

        explosion.play();
        popup.play();
    }

    private void drawLineClear(List<Integer> rows, double alpha, double expand) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int row : rows) {
            double y = row * BRICK_SIZE;

            gc.setFill(themeColor.getClearRowColor());
            gc.fillRect(-expand, y - expand / 2,
                    canvas.getWidth() + expand * 2,
                    BRICK_SIZE + expand);
        }
    }

    private void drawPopup(String message, double alpha, double y) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(themeColor.getTextColor());
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));

        Text t = new Text(message);
        t.setFont(gc.getFont());
        double w = t.getLayoutBounds().getWidth();

        double x = (BOARD_WIDTH - w) / 2; // horizontal center
        gc.fillText(message, x, y);
    }


    // LEVEL UP — ring floating slightly upward
    public void onLevelUp() {
        DoubleProperty alpha = new SimpleDoubleProperty(1.0);
        DoubleProperty radius = new SimpleDoubleProperty(0);
        DoubleProperty centerY = new SimpleDoubleProperty(BOARD_HEIGHT / 2);

        alpha.addListener((obs, oldA, a) -> drawLevelUp(radius.get(), centerY.get(), a.doubleValue()));
        radius.addListener((obs, oldR, r) -> drawLevelUp(r.doubleValue(), centerY.get(), alpha.get()));
        centerY.addListener((obs, oldY, y) -> drawLevelUp(radius.get(), y.doubleValue(), alpha.get()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(alpha, 1.0),
                        new KeyValue(radius, 0),
                        new KeyValue(centerY, BOARD_HEIGHT / 2)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(alpha, 0.0),
                        new KeyValue(radius, BOARD_WIDTH * 0.8),
                        new KeyValue(centerY, BOARD_HEIGHT / 2 - 30) // float slightly up
                )
        );

        tl.setOnFinished(e -> gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight()));
        tl.play();
    }

    private void drawLevelUp(double radius, double centerY, double alpha) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(themeColor.getLevelUpColor());
        gc.setLineWidth(6);

        double cx = BOARD_WIDTH / 2;
        gc.strokeOval(cx - radius, centerY - radius, radius * 2, radius * 2);
    }

    // GAME OVER — Red scanlines
    public void onGameOver() {
        DoubleProperty alpha = new SimpleDoubleProperty(0);

        alpha.addListener((obs, oldA, a) -> drawScanlines(a.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(alpha, 0)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(alpha, 0.6)),
                new KeyFrame(Duration.seconds(1.0), new KeyValue(alpha, 0))
        );

        tl.setOnFinished(e -> gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight()));
        tl.play();
    }

    private void drawScanlines(double alpha) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.color(1, 0, 0, alpha));

        for (int y = 0; y < BOARD_HEIGHT; y += 8) {
            gc.fillRect(0, y, BOARD_WIDTH, 4);
        }
    }
}
