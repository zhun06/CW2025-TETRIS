package com.comp2042.renderers;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.Score;
import com.comp2042.managers.GameManager;
import com.comp2042.util.GameChoice;
import javafx.scene.control.Label;

import java.time.Duration;


public class StatsRenderer {
    GameChoice gameChoice;
    private final Label levelLabel, timeLabel, rowsLabel, currentScoreLabel, highScoreLabel;

    public StatsRenderer(GameController gameController) {
        gameChoice= GameManager.getCurrentGameChoice();
        this.levelLabel = gameController.getGameLabels().get(0);
        this.timeLabel = gameController.getGameLabels().get(1);
        this.rowsLabel = gameController.getGameLabels().get(2);
        this.currentScoreLabel = gameController.getGameLabels().get(3);
        this.highScoreLabel = gameController.getGameLabels().get(4);
        this.initialize();
    }

    private void initialize() {
        switch (gameChoice) {
            case ZEN -> {
                showLabel(levelLabel);
                hideLabel(timeLabel);
                showLabel(rowsLabel);
                showLabel(currentScoreLabel);
                showLabel(highScoreLabel);
            }
            case FORTY_LINES, BLITZ ->  {
                hideLabel(levelLabel);
                showLabel(timeLabel);
                showLabel(rowsLabel);
                hideLabel(currentScoreLabel);
                hideLabel(highScoreLabel);
            }
        }
    }

    private void showLabel(Label label) {
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hideLabel(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    public void render(Score score, Duration remainingTime, Duration elapsedTime) {
        this.renderTime(remainingTime, elapsedTime);
        levelLabel.setText("Level: " + score.levelProperty().getValue());
        rowsLabel.setText("Rows cleared: " + score.rowsClearedProperty().getValue());
        currentScoreLabel.setText("Current score: " + score.scoreProperty().getValue());
        highScoreLabel.setText("High score: " + score.highScoreProperty().getValue());
    }

    public void renderTime(Duration remainingTime, Duration elapsedTime) {
        GameChoice choice= GameManager.getCurrentGameChoice();
        switch (choice) {
            case FORTY_LINES -> {
                timeLabel.setVisible(true);
                timeLabel.setManaged(true);
                timeLabel.setText(formatSeconds(elapsedTime));
            }
            case BLITZ -> {
                timeLabel.setVisible(true);
                timeLabel.setManaged(true);
                timeLabel.setText(formatSeconds(remainingTime));
            }
        }
    }

    private String formatSeconds(Duration d) {
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }

}
