package com.comp2042.setters;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.GameResult;
import com.comp2042.managers.GameManager;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.time.Duration;

public class ResultSetter {
    private final GameChoice gameChoice;
    private GameState gameState; // GAME_OVER, WIN, LOSE
    private final Label gameResult, levelResult, timeResult, rowsResult, scoreResult, highScoreResult;

    public ResultSetter(GameController gameController) {
        this.gameChoice = GameManager.getCurrentGameChoice();

        this.gameResult = gameController.getResultLabels().get(0);
        this.levelResult = gameController.getResultLabels().get(1);
        this.timeResult = gameController.getResultLabels().get(2);
        this.rowsResult = gameController.getResultLabels().get(3);
        this.scoreResult = gameController.getResultLabels().get(4);
        this.highScoreResult = gameController.getResultLabels().get(5);
        this.initialize();
    }

    private void initialize() {
        switch (gameChoice) {
            case ZEN -> {
                showLabel(gameResult);
                showLabel(levelResult);
                hideLabel(timeResult);
                hideLabel(rowsResult);
                showLabel(scoreResult);
                showLabel(highScoreResult);
            }
            case FORTY_LINES ->  {
                showLabel(gameResult);
                hideLabel(levelResult);
                showLabel(timeResult);
                hideLabel(rowsResult);
                hideLabel(scoreResult);
                showLabel(highScoreResult);
            }
            case BLITZ -> {
                showLabel(gameResult);
                hideLabel(levelResult);
                hideLabel(timeResult);
                showLabel(rowsResult);
                hideLabel(scoreResult);
                showLabel(highScoreResult);
            }
        }
    }

    public void update(GameResult gameResult) {
        this.gameState = gameResult.getGameState();

        this.setGameState();
        this.setLevel(gameResult.endLevelProperty().get());
        this.setTime(gameResult.endTimeProperty().get());
        this.setRows(gameResult.endRowsProperty().get());
        this.setScore(gameResult.endScoreProperty().get());
        this.setHighScore(gameResult);
    }

    private void setGameState() {
        switch (gameState) {
            case LOSE ->  {
                this.gameResult.setText("You Lost");
                this.gameResult.setTextFill(Color.RED);
            }
            case WIN ->  {
                this.gameResult.setText("You Won");
                this.gameResult.setTextFill(Color.GREEN);
            }
            case GAME_OVER -> {
                this.gameResult.setText("Game Over");
                this.gameResult.setTextFill(Color.DARKGRAY);
            }
        }
    }

    private void setLevel(int level) {levelResult.setText("Level reached: " + level);}

    private void setTime(Duration endTime) {
        if (gameState == GameState.LOSE) return;
        timeResult.setText("Time taken: " + formatSeconds(endTime));
    }

    private void setRows(int rowsCleared) {rowsResult.setText("Rows cleared: " + rowsCleared);}

    private void setScore(int score) {scoreResult.setText("End score: " + score);}

    private void setHighScore(GameResult gameResult) {
        switch (gameChoice) {
            case ZEN -> highScoreResult.setText("High Score: " + gameResult.highScoreProperty().getValue());
            case FORTY_LINES ->  highScoreResult.setText("Best time: " + formatSeconds(gameResult.bestTimeProperty().get()));
            case BLITZ ->   highScoreResult.setText("Most rows: " + gameResult.mostRowsProperty().getValue());
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

    private String formatSeconds(Duration d) {
        if (d == null) return "none";
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }

}
