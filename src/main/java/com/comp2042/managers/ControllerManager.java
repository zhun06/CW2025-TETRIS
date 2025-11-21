package com.comp2042.managers;

import com.comp2042.controllers.*;
import com.comp2042.util.GameChoice;

import javafx.scene.Parent;
import java.io.IOException;

// ALL scene switch goes through here
// Manage and store ALL Controller roots
// Each fxml file loaded exactly once
// If controller don't exist: create and store controller and it's root
// Else: reuse controller and it's root
public class ControllerManager {
    // Roots
    private static Parent homeRoot, themeRoot, gameRoot, leaderBoardRoot;
    // Game manager
    private static GameManager gameManager;
    // Controllers
    private static HomeController homeController;
    private static ThemeController themeController;
    private static GameController gameController;
    private static LeaderBoardController leaderBoardController;

    public static void callHomeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (homeRoot == null) setupHomeController(); // Setup
        SceneManager.setRoot(homeRoot); // Reuse root
        homeRoot.requestFocus(); // Focus
    }

    private static void setupHomeController() throws IOException {
        SceneManager.readFXML("home");
        homeController = SceneManager.getLoader().getController(); // Get loader -> get controller
        homeRoot = SceneManager.getRoot(); // Get root
        homeController.addKeyHandler(); // Add key handler
    }

    public static void callThemeController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (themeRoot == null) setupThemeController(); // Setup
        SceneManager.setRoot(themeRoot); // Reuse root
        themeRoot.requestFocus(); // Focus
    }

    private static void setupThemeController() throws IOException {
        SceneManager.readFXML("theme");
        themeRoot = SceneManager.getRoot(); // Get root
        themeController = SceneManager.getLoader().getController(); // Get loader -> get controller
        themeController.addKeyHandler(); // Add key handler
    }

    public static void callGameController(GameChoice gameChoice) throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (gameRoot == null) setupGameController(); // Setup
        SceneManager.setRoot(gameRoot); // Reuse root
        gameRoot.requestFocus(); // Focus
        gameManager.startGame(gameChoice); // Start Game
    }

    private static void setupGameController() throws IOException {
        SceneManager.readFXML("game");
        gameRoot = SceneManager.getRoot(); // Get root
        gameController = SceneManager.getLoader().getController(); // Get loader -> get controller
        gameController.addKeyHandler(); // Add key handler
        gameManager = GameManager.getInstance(gameController); // Create GameManager singleton instance
        gameController.setGameManager(gameManager); // Pass game manager
    }

    public static void callLeaderBoardController() throws IOException {
        SceneManager.initScene(); // Ensure scene exist
        if (leaderBoardRoot == null) setupLeaderBoardController(); // Setup
        leaderBoardController.refresh(); // Update leader board
        SceneManager.setRoot(leaderBoardRoot); // Reuse root
        leaderBoardRoot.requestFocus(); // Focus
    }

    private static void setupLeaderBoardController() throws IOException {
        SceneManager.readFXML("leaderBoard");
        leaderBoardRoot = SceneManager.getRoot(); // Get root
        leaderBoardController = SceneManager.getLoader().getController(); // Get loader -> get controller
        leaderBoardController.addKeyHandler(); // Add key handler
    }



}

