package com.comp2042.setters;

import com.comp2042.controllers.GameController;
import com.comp2042.logic.data.GameResult;
import com.comp2042.managers.GameManager;
import com.comp2042.util.GameChoice;
import com.comp2042.util.GameState;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.time.Duration;

/**Sets the result labels (win/loss/game over) for the current game.*/
public class ResultSetter {

    private GameChoice gameChoice;
    private GameState gameState;
    private final Label gameResult, timeResult, rowsResult, scoreResult, highScoreResult;

    /**
     * Constructor.
     * @param gameController the game controller containing the result labels
     */
    public ResultSetter(GameController gameController) {
        this.gameResult = gameController.getResultLabels().get(0);
        this.timeResult = gameController.getResultLabels().get(1);
        this.rowsResult = gameController.getResultLabels().get(2);
        this.scoreResult = gameController.getResultLabels().get(3);
        this.highScoreResult = gameController.getResultLabels().get(4);
    }

    /** Initializes label visibility depending on game mode. */
    private void initialize() {
        this.gameChoice = GameManager.getCurrentGameChoice();
        switch (gameChoice) {
            case ZEN -> {
                showLabel(gameResult); hideLabel(timeResult); hideLabel(rowsResult); showLabel(scoreResult); showLabel(highScoreResult);
            }
            case FORTY_LINES -> {
                showLabel(gameResult); hideLabel(rowsResult); hideLabel(scoreResult); showLabel(highScoreResult);
                if (!gameState.equals(GameState.LOSE)) showLabel(timeResult); else hideLabel(timeResult);
            }
            case BLITZ -> {
                showLabel(gameResult); hideLabel(timeResult); showLabel(rowsResult); hideLabel(scoreResult); showLabel(highScoreResult);
            }
            case HARDCORE -> {
                showLabel(gameResult); showLabel(timeResult); hideLabel(rowsResult); hideLabel(scoreResult); showLabel(highScoreResult);
            }
        }
    }

    /** Updates all result labels according to the game result. */
    public void update(GameResult gameResult) {
        this.gameState = gameResult.getGameState();
        this.initialize();

        setGameState();
        setTime(gameResult.endTimeProperty().get());
        setRows(gameResult.endRowsProperty().get());
        setScore(gameResult.endScoreProperty().get());
        setHighScore(gameResult);
    }

    private void setGameState() {
        switch (gameState) {
            case LOSE -> { gameResult.setText("You Lost"); gameResult.setTextFill(Color.RED); }
            case WIN -> { gameResult.setText("You Won"); gameResult.setTextFill(Color.GREEN); }
            case GAME_OVER -> { gameResult.setText("Game Over"); gameResult.setTextFill(Color.WHITE); }
        }
    }

    private void setTime(Duration endTime) {
        if (gameState == GameState.LOSE) return;
        switch (gameChoice) {
            case FORTY_LINES -> timeResult.setText("Time taken: " + formatSeconds(endTime));
            case HARDCORE -> timeResult.setText("Time survived: " + formatSeconds(endTime));
        }
    }

    private void setRows(int rowsCleared) { rowsResult.setText("Rows cleared: " + rowsCleared); }

    private void setScore(int score) { scoreResult.setText("End score: " + score); }

    private void setHighScore(GameResult gameResult) {
        switch (gameChoice) {
            case ZEN -> highScoreResult.setText("High Score: " + gameResult.highScoreProperty().getValue());
            case FORTY_LINES -> highScoreResult.setText("Best time: " + formatSeconds(gameResult.bestTimeProperty().get()));
            case BLITZ -> highScoreResult.setText("Most rows: " + gameResult.mostRowsProperty().getValue());
            case HARDCORE -> highScoreResult.setText("Longest time survived: " + formatSeconds(gameResult.bestTimeProperty().get()));
        }
    }

    private void showLabel(Label label) { label.setVisible(true); label.setManaged(true); }
    private void hideLabel(Label label) { label.setVisible(false); label.setManaged(false); }

    private String formatSeconds(Duration d) {
        if (d == null) return "none";
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }
}