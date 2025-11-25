package com.comp2042.setters;

import com.comp2042.controllers.LeaderBoardController;
import com.comp2042.highScore.CsvLoader;
import com.comp2042.highScore.ScoreRecord;
import com.comp2042.util.GameChoice;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Duration;

/**
 * Populates and manages the LeaderBoard TableView.
 * Maps ScoreRecord properties to the appropriate columns and formats them depending on the game mode.
 */
public class LeaderBoardSetter {

    private final TableView<ScoreRecord> leaderBoardTable;
    private final TableColumn<ScoreRecord, String> modeColumn, timeColumn, scoreColumn, rowsColumn;

    /**
     * Constructor.
     * @param lbc the LeaderBoardController providing table and columns
     */
    public LeaderBoardSetter(LeaderBoardController lbc) {
        leaderBoardTable = lbc.getLeaderBoardTable();
        modeColumn = lbc.getLeaderBoardColumns().get(0);
        timeColumn = lbc.getLeaderBoardColumns().get(1);
        scoreColumn = lbc.getLeaderBoardColumns().get(2);
        rowsColumn = lbc.getLeaderBoardColumns().get(3);

        setColumnFactories();
        centerColumn();
    }

    /** Maps ScoreRecord properties to TableView columns with custom formatting. */
    private void setColumnFactories() {
        modeColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getMode())));

        timeColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.ZEN) || mode.equals(GameChoice.BLITZ)) return new ReadOnlyObjectWrapper<>("N/A");

            Duration duration = cellData.getValue().getTime();
            return new ReadOnlyObjectWrapper<>(duration == null ? "None" : formatSeconds(duration));
        });

        scoreColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.FORTY_LINES) || mode.equals(GameChoice.BLITZ) || mode.equals(GameChoice.HARDCORE))
                return new ReadOnlyObjectWrapper<>("N/A");
            return new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getScore()));
        });

        rowsColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.ZEN) || mode.equals(GameChoice.FORTY_LINES) || mode.equals(GameChoice.HARDCORE))
                return new ReadOnlyObjectWrapper<>("N/A");
            return new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getRows()));
        });
    }

    /** Centers the text in all TableView columns. */
    public void centerColumn() {
        modeColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setStyle("-fx-alignment: CENTER;");
        scoreColumn.setStyle("-fx-alignment: CENTER;");
        rowsColumn.setStyle("-fx-alignment: CENTER;");
    }

    /** Updates the TableView with the latest scores from CSV. */
    public void update() {
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.load();

        ObservableList<ScoreRecord> items = leaderBoardTable.getItems();
        items.clear();

        for (GameChoice mode : GameChoice.values()) {
            ScoreRecord scoreData = csvLoader.get(mode);
            items.add(new ScoreRecord(mode, scoreData.getTime(), scoreData.getScore(), scoreData.getRows()));
        }
    }

    /** Formats Duration to seconds with 2 decimal places. */
    private String formatSeconds(Duration d) {
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }
}