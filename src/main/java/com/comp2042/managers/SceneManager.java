package com.comp2042.managers;

import com.comp2042.util.Theme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**Manages the JavaFX scene, stage, root nodes, and theme.*/
public class SceneManager {

    private static Parent root;
    private static FXMLLoader loader;
    private static Stage stage;
    private static Scene scene;
    private static Theme currentTheme = Theme.CANDY;
    private static String fileName;

    private SceneManager() {}

    /** Initializes the scene if it has not been created. */
    public static void initScene() {
        if (scene == null) {
            root = new Pane();
            scene = new Scene(root);
            stage.setScene(scene);
            setSceneStyle();
            stage.setMinWidth(1000);
            stage.setMinHeight(700);
            stage.show();
        }
    }

    /**
     * Loads FXML file and sets root and loader.
     * @param fxmlName the name of the FXML file
     * @throws IOException if FXML cannot be loaded
     */
    public static void readFXML(String fxmlName) throws IOException {
        loader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxmlName + ".fxml"));
        root = loader.load();
    }

    /**
     * Sets the current root of the scene.
     * @param root the new root
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
        if (!stage.isFullScreen()) {
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    /** Applies base and theme CSS to the scene. */
    private static void setSceneStyle() {
        scene.getStylesheets().add(SceneManager.class.getResource("/css/base.css").toExternalForm());
        SceneManager.setTheme(currentTheme);
    }

    /**
     * Sets the current theme for the scene.
     * @param theme the selected theme
     */
    public static void setTheme(Theme theme) {
        scene.getStylesheets().removeIf(s -> s.contains("/css/themes/"));
        currentTheme = theme;
        switch(currentTheme) {
            case CANDY -> fileName = "candy";
            case MINION -> fileName = "minion";
            case NATURE -> fileName = "nature";
            case NEON -> fileName = "neon";
            case OCEAN -> fileName = "ocean";
            case SUNSET -> fileName = "sunset";
        }
        scene.getStylesheets().add(SceneManager.class.getResource("/css/themes/" + fileName + ".css").toExternalForm());
    }

    // Setters and getters
    public static void setStage(Stage s) { stage = s; }
    public static Parent getRoot() { return root; }
    public static FXMLLoader getLoader() { return loader; }
    public static Scene getScene() { return scene; }
    public static Stage getStage() { return stage; }
    public static Theme getTheme() { return currentTheme; }
}