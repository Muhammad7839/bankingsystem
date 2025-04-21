package com.bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.bank.Classes.Mbank;
import com.bank.Controllers.LoginController;
import com.bank.Controllers.SplashController;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Mbank mbank;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("splashScreen.fxml"));
        Parent splashRoot = splashLoader.load();
        scene = new Scene(splashRoot, 640, 480);

        mbank = new Mbank();

        SplashController splashController = splashLoader.getController();
        splashController.initialize();

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, Mbank mbank) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);

        if ("login".equals(fxml)) {
            LoginController loginController = fxmlLoader.getController();
            loginController.setMbank(mbank);
        }
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);

        if ("login".equals(fxml)) {
            LoginController loginController = fxmlLoader.getController();
            loginController.setMbank(mbank);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}