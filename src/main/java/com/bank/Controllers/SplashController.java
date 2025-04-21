package com.bank.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.bank.App;

public class SplashController {

    @FXML
    private Label bankNameLabel;

    public void initialize() {
        bankNameLabel.setText("Evergreen Finanace");
        transitionToLoginScreen();
    }

    private void transitionToLoginScreen() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    App.setRoot("login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }
}
