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
                hideLabel(rowsLabel);
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

    public void render(Score score, Duration remainingTime, Duration elapsedTime) {
        this.renderTime(remainingTime, elapsedTime);
        this.renderLevel(score);
        this.renderRows(score);
        this.renderScore(score);
        this.renderHighScore(score);
    }

    private void renderTime(Duration remainingTime, Duration elapsedTime) {
        switch (gameChoice) {
            case FORTY_LINES -> {timeLabel.setText("Time: " + formatSeconds(elapsedTime));}
            case BLITZ -> {timeLabel.setText("Time left: " + formatSeconds(remainingTime));}
        }
    }

    private void renderLevel(Score score) {levelLabel.setText("Level: " + score.levelProperty().getValue());}

    private void renderRows(Score score) {
        switch (gameChoice) {
            case FORTY_LINES -> {rowsLabel.setText("Rows remaining: " + score.rowsRemainingProperty().getValue());}
            case BLITZ -> {rowsLabel.setText("Rows cleared: " + score.rowsClearedProperty().getValue());}
        }
    }

    private void renderScore(Score score) {currentScoreLabel.setText("Current score: " + score.scoreProperty().getValue());}

    private void renderHighScore(Score score) {highScoreLabel.setText("High score: " + score.highScoreProperty().getValue());}

    private void showLabel(Label label) {
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hideLabel(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    private String formatSeconds(Duration d) {
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }

}
