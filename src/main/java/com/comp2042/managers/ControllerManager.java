package com.comp2042.managers;

import com.comp2042.controllers.*;
import com.comp2042.util.GameChoice;

import javafx.scene.Parent;
import java.io.IOException;

/**
 * Manages all scene switches and controller instances.
 * Each FXML file is loaded exactly once. If a controller does not exist, it is created and stored along with its root.
 * Otherwise, the existing controller and root are reused.
 */
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

    /**
     * Switches to the HomeController scene.
     * @throws IOException if FXML cannot be loaded
     */
    public static void callHomeController() throws IOException {
        SceneManager.initScene();
        if (homeRoot == null) setupHomeController();
        SceneManager.setRoot(homeRoot);
        homeRoot.requestFocus();
    }

    /** Initializes the HomeController and its root. */
    private static void setupHomeController() throws IOException {
        SceneManager.readFXML("home");
        homeController = SceneManager.getLoader().getController();
        homeRoot = SceneManager.getRoot();
        homeController.addKeyHandler();
    }

    /**
     * Switches to the ThemeController scene.
     * @throws IOException if FXML cannot be loaded
     */
    public static void callThemeController() throws IOException {
        SceneManager.initScene();
        if (themeRoot == null) setupThemeController();
        SceneManager.setRoot(themeRoot);
        themeRoot.requestFocus();
    }

    /** Initializes the ThemeController and its root. */
    private static void setupThemeController() throws IOException {
        SceneManager.readFXML("theme");
        themeRoot = SceneManager.getRoot();
        themeController = SceneManager.getLoader().getController();
        themeController.addKeyHandler();
    }

    /**
     * Switches to the GameController scene and starts the specified game.
     * @param gameChoice the game mode to start
     * @throws IOException if FXML cannot be loaded
     */
    public static void callGameController(GameChoice gameChoice) throws IOException {
        SceneManager.initScene();
        if (gameRoot == null) setupGameController();
        SceneManager.setRoot(gameRoot);
        gameRoot.requestFocus();
        gameManager.startGame(gameChoice);
    }

    /** Initializes the GameController and its root. */
    private static void setupGameController() throws IOException {
        SceneManager.readFXML("game");
        gameRoot = SceneManager.getRoot();
        gameController = SceneManager.getLoader().getController();
        gameController.addKeyHandler();
        gameManager = GameManager.getInstance(gameController);
        gameController.setGameManager(gameManager);
    }

    /**
     * Switches to the LeaderBoardController scene and refreshes it.
     * @throws IOException if FXML cannot be loaded
     */
    public static void callLeaderBoardController() throws IOException {
        SceneManager.initScene();
        if (leaderBoardRoot == null) setupLeaderBoardController();
        leaderBoardController.refresh();
        SceneManager.setRoot(leaderBoardRoot);
        leaderBoardRoot.requestFocus();
    }

    /** Initializes the LeaderBoardController and its root. */
    private static void setupLeaderBoardController() throws IOException {
        SceneManager.readFXML("leaderBoard");
        leaderBoardRoot = SceneManager.getRoot();
        leaderBoardController = SceneManager.getLoader().getController();
        leaderBoardController.addKeyHandler();
    }
}