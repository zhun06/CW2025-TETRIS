package com.comp2042;

import com.comp2042.managers.ControllerManager;
import com.comp2042.managers.SceneManager;
import com.comp2042.sfx.SoundLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SoundLoader.loadAll();
        SceneManager.setStage(primaryStage);
        ControllerManager.callHomeController();
    }

    public static void main(String[] args) {launch(args);}
}
