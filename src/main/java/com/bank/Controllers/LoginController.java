package com.bank.Controllers;

import java.io.IOException;
import java.util.Scanner;

import com.bank.Classes.Mbank;
import com.bank.Utilities.Utils;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField customerIdField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    private int loginAttempts = 3;

    private Mbank mbank;

    private BooleanProperty isLoginButtonDisabled = new SimpleBooleanProperty(true);

    public void setMbank(Mbank mbank) {
        this.mbank = mbank;
    }

    @FXML
    private void initialize() {
        loginButton.disableProperty().bind(isLoginButtonDisabled);

        isLoginButtonDisabled.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loginButton.setStyle("-fx-font-size: 14;-fx-background-color: lightgrey;");
            } else {
                loginButton.setStyle("-fx-font-size: 14;-fx-background-color: #FF1493; -fx-font-weight: bold; -fx-text-fill: white;");
            }
        });

        isLoginButtonDisabled.bind(Bindings.isEmpty(customerIdField.textProperty())
                .or(Bindings.isEmpty(pinField.textProperty())));
    }

    @FXML
    private void loginButtonClicked() {

        int customerId, pin;
        try {
            customerId = Integer.parseInt(customerIdField.getText());
            pin = Integer.parseInt(pinField.getText());

        } catch (Exception e) {
            Platform.runLater(() -> messageLabel.setText(Utils.exceptionToString(e)));
            return;
        }

        if (login(customerId, pin)) {
            loginAttempts = 3;
            Platform.runLater(() -> messageLabel.setText("Login successful. Customer ID: " + customerId));
            mbank.setcurrentCustomerID(customerId);
            loadOptionsScreen();
        } else {
            Platform.runLater(() -> {
                messageLabel.setText("Invalid login. Please try again.");
                loginAttempts--;

                if (loginAttempts == 0) {
                    System.exit(0);
                }
            });
        }
    }

    private boolean login(int customerId, int pin) {
        return mbank.loginHelper(customerId, pin);
    }

    private void loadOptionsScreen() {
        try {
            FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("/com/bank/optionsScreen.fxml"));
            Parent optionsRoot = optionsLoader.load();

            OptionsController optionsController = optionsLoader.getController();
            optionsController.setMbank(mbank);

            Scene optionsScene = new Scene(optionsRoot, 640, 480);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(optionsScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loginConsole() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 3;

        while (attempts > 0) {
            int cNumber = scanner.nextInt();

            int cPin = scanner.nextInt();

            if (mbank.loginHelper(cNumber, cPin)) {
                break;
            } else {
                attempts--;

                if (attempts == 0) {
                    System.exit(0);
                }
            }

        }

        scanner.close();
    }

}
