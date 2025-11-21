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

public class LeaderBoardSetter {
    private final TableView<ScoreRecord> leaderBoardTable;
    private final TableColumn<ScoreRecord, String> modeColumn, timeColumn, scoreColumn, rowsColumn;

    public LeaderBoardSetter(LeaderBoardController lbc) {
        leaderBoardTable = lbc.getLeaderBoardTable();
        modeColumn = lbc.getLeaderBoardColumns().get(0);
        timeColumn = lbc.getLeaderBoardColumns().get(1);
        scoreColumn = lbc.getLeaderBoardColumns().get(2);
        rowsColumn = lbc.getLeaderBoardColumns().get(3);

        setColumnFactories();
        centerColumn();
    }

    private void setColumnFactories() {
        // Map ScoreRecord properties to the columns
        modeColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getMode())));

        timeColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.ZEN) || mode.equals(GameChoice.BLITZ)) {
                return new ReadOnlyObjectWrapper<>("N/A");
            }

            Duration duration = cellData.getValue().getTime();
            if (duration == null) {
                return new ReadOnlyObjectWrapper<>("None");
            }

            String formattedTime = formatSeconds(duration);

            return new ReadOnlyObjectWrapper<>(formattedTime);
        });

        scoreColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.FORTY_LINES) || mode.equals(GameChoice.BLITZ) || mode.equals(GameChoice.HARDCORE) ) {
                return new ReadOnlyObjectWrapper<>("N/A");
            }

            return new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getScore()));
        });


        rowsColumn.setCellValueFactory(cellData -> {
            GameChoice mode = cellData.getValue().getMode();
            if (mode.equals(GameChoice.ZEN) || mode.equals(GameChoice.FORTY_LINES) || mode.equals(GameChoice.HARDCORE)){
                return new ReadOnlyObjectWrapper<>("N/A");
            }
            return new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getRows()));
        });

    }

    public void centerColumn() {
        modeColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setStyle("-fx-alignment: CENTER;");
        scoreColumn.setStyle("-fx-alignment: CENTER;");
        rowsColumn.setStyle("-fx-alignment: CENTER;");
    }

    public void update() {
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.load();

        ObservableList<ScoreRecord> items = leaderBoardTable.getItems();
        items.clear(); // Clear existing rows

        // Iterate through all modes and create a row (ScoreRecord) for each
        for (GameChoice mode : GameChoice.values()) {
            ScoreRecord scoreData = csvLoader.get(mode);

            ScoreRecord record = new ScoreRecord(
                    mode,
                    scoreData.getTime(),
                    scoreData.getScore(),
                    scoreData.getRows()
            );

            items.add(record); // Add new row to the TableView
        }
    }

    private String formatSeconds(Duration d) {
        double seconds = d.toNanos() / 1_000_000_000.0;
        return String.format("%.2f", seconds);
    }

}
