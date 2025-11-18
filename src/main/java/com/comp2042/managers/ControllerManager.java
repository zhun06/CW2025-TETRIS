package com.comp2042.managers;

import com.comp2042.controllers.*;
import com.comp2042.util.GameChoice;

import javafx.scene.Parent;
import java.io.IOException;

// ALL scene switch goes through here
// Manage and store ALL Controllers
// Each fxml file loaded exactly once
// If controller don't exist: create and store controller and it's root
// Else: reuse controller and it's root

public class ControllerManager {
    // Controllers
    private static HomeController homeController;
    private static ThemeController themeController;
    private static GameController gameController;
    private static LeaderBoardController leaderBoardController;
    // Roots
    private static Parent homeRoot, themeRoot, gameRoot, leaderBoardRoot;

    public static void callHomeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (homeRoot == null) {
            SceneManager.readFXML("home");
            homeController = SceneManager.getLoader().getController(); // Get loader -> get controller
            homeRoot = SceneManager.getRoot(); // Get root
            homeController.addKeyHandler(); // Add key handler
        }
        SceneManager.setRoot(homeRoot); // Reuse root
        homeRoot.requestFocus();
    }

    public static void callThemeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (themeRoot == null) {
            SceneManager.readFXML("theme");
            themeController = SceneManager.getLoader().getController(); // Get loader -> get controller
            themeRoot = SceneManager.getRoot(); // Get root
            themeController.addKeyHandler(); // Add key handler
        }
        SceneManager.setRoot(themeRoot); // Reuse root
        themeRoot.requestFocus();
    }

    public static void callGameController(GameChoice choice) throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (gameController == null) {
            SceneManager.readFXML("game");
            gameController = SceneManager.getLoader().getController(); // Get loader -> get controller
            gameRoot = SceneManager.getRoot(); // Get root
            gameController.addKeyHandler(); // Add key handler
        }
        SceneManager.setRoot(gameRoot); // Reuse root
        gameRoot.requestFocus();

        // Start Game
        GameManager.initialize(gameController);
        switch (choice) {
            case ZEN -> GameManager.startZen();
            case FORTY_LINES -> GameManager.startForty();
            case BLITZ ->  GameManager.startBlitz();
        }
    }

    public static void callLeaderBoardController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (leaderBoardController == null) {
            SceneManager.readFXML("leaderBoard");
            leaderBoardController = SceneManager.getLoader().getController(); // Get loader -> get controller
            leaderBoardRoot = SceneManager.getRoot(); // Get root
            leaderBoardController.addKeyHandler(); // Add key handler
        }
        leaderBoardController.refresh(); // Update leader board
        SceneManager.setRoot(leaderBoardRoot); // Reuse root
        leaderBoardRoot.requestFocus();
    }

    // Getters
    public static Parent getHomeRoot() {return homeRoot;}
    public static Parent getThemeRoot() {return themeRoot;}
    public static Parent getGameRoot() {return gameRoot;}
    public static Parent getLeaderBoardRoot() {return leaderBoardRoot;}

}

