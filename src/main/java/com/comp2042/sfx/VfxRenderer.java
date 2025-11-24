package com.comp2042.sfx;

import com.comp2042.colors.*;
import com.comp2042.controllers.GameController;
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
import java.util.concurrent.ThreadLocalRandom;

public class VfxRenderer {
    private final Canvas popupCanvas;
    private final Canvas sweepCanvas;
    private final Canvas levelUpCanvas;
    private final Canvas gameOverCanvas;

    private final GraphicsContext popupGC;
    private final GraphicsContext sweepGC;
    private final GraphicsContext levelUpGC;
    private final GraphicsContext gameOverGC;

    private ThemeColor themeColor;

    private final int ROWS = TetrisGame.ROWS;
    private final int COLS = TetrisGame.COLS;
    private final int BRICK_SIZE = TetrisGame.BRICK_SIZE;

    private final double BOARD_WIDTH = COLS * BRICK_SIZE;
    private final double BOARD_HEIGHT = ROWS * BRICK_SIZE;

    public VfxRenderer(GameController gameController) {
        this.popupCanvas = gameController.getVfxCanvases().get(0);
        this.sweepCanvas = gameController.getVfxCanvases().get(1);
        this.levelUpCanvas = gameController.getVfxCanvases().get(2);
        this.gameOverCanvas = gameController.getVfxCanvases().get(3);

        this.popupGC = popupCanvas.getGraphicsContext2D();
        this.sweepGC = sweepCanvas.getGraphicsContext2D();
        this.levelUpGC = levelUpCanvas.getGraphicsContext2D();
        this.gameOverGC = gameOverCanvas.getGraphicsContext2D();
    }

    public void onNewGame() {
        switch (SceneManager.getTheme()) {
            case CANDY -> themeColor = new CandyColor();
            case MINION -> themeColor = new MinionColor();
            case NATURE -> themeColor = new NatureColor();
            case NEON -> themeColor = new NeonColor();
            case OCEAN -> themeColor = new OceanColor();
            case SUNSET -> themeColor = new SunsetColor();
        }
    }

    public void render(SfxEvent event, ClearRow clearRow) {
        switch (event) {
            case GAME_START -> onStart();
            case LINE_CLEAR -> onLineClear(clearRow);
            case LEVEL_UP -> onLevelUp();
            case GAME_OVER -> onGameOver();
        }
    }


    /* ===========================================================
       GAME START — soft overlay fade
       =========================================================== */
    public void onStart() {
        DoubleProperty alpha = new SimpleDoubleProperty(0);

        alpha.addListener((o, oldVal, a) -> {
            sweepGC.clearRect(0, 0, sweepCanvas.getWidth(), sweepCanvas.getHeight());
            levelUpGC.clearRect(0, 0, levelUpCanvas.getWidth(), levelUpCanvas.getHeight());
            gameOverGC.clearRect(0, 0, gameOverCanvas.getWidth(), gameOverCanvas.getHeight());

            Color overlay = themeColor.getOverlayColor();
            sweepGC.setFill(Color.color(overlay.getRed(), overlay.getGreen(), overlay.getBlue(), overlay.getOpacity() * a.doubleValue()));
            sweepGC.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        });

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(alpha, 0)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(alpha, 1)),
                new KeyFrame(Duration.seconds(1.2), new KeyValue(alpha, 0))
        );
        tl.play();
    }

    /* ===========================================================
       POPUP TEXT — for line clear and level up
       =========================================================== */
    public void playPopup(String message, double centerYOffset) {
        double startY = BOARD_HEIGHT / 2 + centerYOffset;
        double endY = startY - 45;

        DoubleProperty alpha = new SimpleDoubleProperty(1.0);
        DoubleProperty posY = new SimpleDoubleProperty(startY);

        alpha.addListener((o, oldA, a) -> drawPopup(message, a.doubleValue(), posY.get()));
        posY.addListener((o, oldY, y) -> drawPopup(message, alpha.get(), y.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(alpha, 1), new KeyValue(posY, startY)),
                new KeyFrame(Duration.seconds(1), new KeyValue(alpha, 0), new KeyValue(posY, endY))
        );

        tl.setOnFinished(e -> popupGC.clearRect(0, 0, popupCanvas.getWidth(), popupCanvas.getHeight()));
        tl.play();
    }

    private void drawPopup(String msg, double alpha, double y) {
        popupGC.clearRect(0, 0, popupCanvas.getWidth(), popupCanvas.getHeight());

        popupGC.setFill(Color.color(themeColor.getTextColor().getRed(),
                themeColor.getTextColor().getGreen(),
                themeColor.getTextColor().getBlue(),
                alpha));
        popupGC.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        Text t = new Text(msg);
        t.setFont(popupGC.getFont());

        double x = (BOARD_WIDTH - t.getLayoutBounds().getWidth()) / 2;
        popupGC.fillText(msg, x, y);
    }

    /* ===========================================================
       LINE CLEAR — sweep + particles
       =========================================================== */
    public void onLineClear(ClearRow clearRow) {
        if (clearRow == null || clearRow.getClearedRows().isEmpty()) return;

        List<Integer> rows = clearRow.getClearedRows();
        int removed = clearRow.getLinesRemoved();

        String text = switch (removed) {
            case 1 -> "SINGLE";
            case 2 -> "DOUBLE";
            case 3 -> "TRIPLE";
            case 4 -> "TETRIS!";
            default -> removed + " LINES";
        };

        playPopup(text, 0);

        DoubleProperty sweep = new SimpleDoubleProperty(0);
        DoubleProperty alpha = new SimpleDoubleProperty(1);

        sweep.addListener((o, oldV, v) -> drawLineClear(rows, v.doubleValue(), alpha.get()));
        alpha.addListener((o, oldA, a) -> drawLineClear(rows, sweep.get(), a.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sweep, 0), new KeyValue(alpha, 1)),
                new KeyFrame(Duration.seconds(0.45), new KeyValue(sweep, BOARD_WIDTH), new KeyValue(alpha, 0.8))
        );

        tl.setOnFinished(e -> sweepGC.clearRect(0, 0, sweepCanvas.getWidth(), sweepCanvas.getHeight()));
        tl.play();
    }

    private void drawLineClear(List<Integer> rows, double sweep, double alpha) {
        sweepGC.clearRect(0, 0, sweepCanvas.getWidth(), sweepCanvas.getHeight());

        Color c = themeColor.getClearRowColor();

        for (int row : rows) {
            double y = row * BRICK_SIZE;

            sweepGC.setFill(Color.color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
            sweepGC.fillRect(0, y, sweep, BRICK_SIZE);

            drawParticles(y, alpha);
        }
    }

    private void drawParticles(double rowY, double alpha) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 6; i++) {
            double x = r.nextDouble(0, BOARD_WIDTH);
            double y = rowY + r.nextDouble(0, BRICK_SIZE);
            sweepGC.setFill(Color.color(1, 1, 1, alpha * 0.7));
            sweepGC.fillOval(x, y - 4, 3, 3);
        }
    }

    /* ===========================================================
       LEVEL UP — expanding ring
       =========================================================== */
    public void onLevelUp() {
        playPopup("LEVEL UP!", -40);

        DoubleProperty radius = new SimpleDoubleProperty(0);
        DoubleProperty alpha = new SimpleDoubleProperty(1);

        radius.addListener((o, oldR, r) -> drawLevelUp(r.doubleValue(), alpha.get()));
        alpha.addListener((o, oldA, a) -> drawLevelUp(radius.get(), a.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(radius, 0), new KeyValue(alpha, 1)),
                new KeyFrame(Duration.seconds(0.55), new KeyValue(radius, BOARD_WIDTH * 0.8), new KeyValue(alpha, 0))
        );

        tl.setOnFinished(e -> levelUpGC.clearRect(0, 0, levelUpCanvas.getWidth(), levelUpCanvas.getHeight()));
        tl.play();
    }

    private void drawLevelUp(double radius, double alpha) {
        levelUpGC.clearRect(0, 0, levelUpCanvas.getWidth(), levelUpCanvas.getHeight());

        Color c = themeColor.getLevelUpColor();
        levelUpGC.setStroke(Color.color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
        levelUpGC.setLineWidth(6);

        double cx = BOARD_WIDTH / 2;
        double cy = BOARD_HEIGHT / 2;

        levelUpGC.strokeOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }

    /* ===========================================================
       GAME OVER — scanlines
       =========================================================== */
    public void onGameOver() {
        DoubleProperty alpha = new SimpleDoubleProperty(0);

        alpha.addListener((o, old, a) -> drawScanlines(a.doubleValue()));

        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(alpha, 0)),
                new KeyFrame(Duration.seconds(0.55), new KeyValue(alpha, 0.6)),
                new KeyFrame(Duration.seconds(0.95), new KeyValue(alpha, 0))
        );

        tl.setOnFinished(e -> gameOverGC.clearRect(0, 0, gameOverCanvas.getWidth(), gameOverCanvas.getHeight()));
        tl.play();
    }

    private void drawScanlines(double alpha) {
        gameOverGC.clearRect(0, 0, gameOverCanvas.getWidth(), gameOverCanvas.getHeight());

        gameOverGC.setFill(Color.color(1, 0, 0, alpha));
        for (int y = 0; y < BOARD_HEIGHT; y += 8) {
            gameOverGC.fillRect(0, y, BOARD_WIDTH, 4);
        }
    }
}