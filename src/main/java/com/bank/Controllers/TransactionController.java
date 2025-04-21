package com.bank.Controllers;

import java.io.IOException;

import com.bank.Classes.Account;
import com.bank.Classes.Mbank;
import com.bank.Utilities.Utils;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TransactionController {

    @FXML
    private Label msgLabel;

    @FXML
    private TextField amountField;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button transferButton;

    private BooleanProperty isTransferButtonEnabled = new SimpleBooleanProperty(false);
    private BooleanProperty isAmountEmpty = new SimpleBooleanProperty(true);

    @FXML
    private TextField accountField;

    private Mbank mbank;

    private String TransactionType;

    public void setMbank(Mbank mbank, String TransactionType) {
        this.mbank = mbank;
        this.TransactionType = TransactionType;

        initialize();
    }

    @FXML
    private void initialize() {

        if (TransactionType == null)
            return;

        if (TransactionType == "Deposit") {
            depositButton.setDisable(true);
            depositButton.disableProperty().bind(isAmountEmpty);

            isAmountEmpty.bind(Bindings.createBooleanBinding(() -> amountField.getText().trim().isEmpty(),
                    amountField.textProperty()));

            depositButton.styleProperty().bind(
                    Bindings.when(isAmountEmpty)
                            .then("-fx-font-size: 14; -fx-background-color: lightgrey;")
                            .otherwise(
                                    "-fx-font-size: 14; -fx-background-color: #FF1493; -fx-font-weight: bold; -fx-text-fill: white;"));
        } else if (TransactionType == "Withdraw") {
            withdrawButton.setDisable(true);

            withdrawButton.disableProperty().bind(isAmountEmpty);

            isAmountEmpty.bind(Bindings.createBooleanBinding(() -> amountField.getText().trim().isEmpty(),
                    amountField.textProperty()));

            withdrawButton.styleProperty().bind(
                    Bindings.when(isAmountEmpty)
                            .then("-fx-font-size: 14; -fx-background-color: lightgrey;")
                            .otherwise(
                                    "-fx-font-size: 14; -fx-background-color: #FF1493; -fx-font-weight: bold; -fx-text-fill: white;"));
        } else {
            transferButton.disableProperty().bind(isTransferButtonEnabled);

            isTransferButtonEnabled.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    transferButton.setStyle("-fx-font-size: 14;-fx-background-color: lightgrey;");
                } else {
                    transferButton.setStyle(
                            "-fx-font-size: 14; -fx-background-color: #FF1493; -fx-font-weight: bold; -fx-text-fill: white;");
                }
            });

            isTransferButtonEnabled.bind(Bindings.isEmpty(amountField.textProperty())
                    .or(Bindings.isEmpty(accountField.textProperty())));
        }
    }

    @FXML
    private void depositButtonClicked() {

        Account account = mbank.getAccountOfCurrentCustomer();

        try {
            float amount;

            try {
                amount = Float.parseFloat(amountField.getText());
            } catch (Exception e) {
                throw new Exception("Invalid syntax");
            }

            if (amount <= 0)
                throw new Exception("Amount can not be zero or negative!");

            String depositStatus = mbank.deposit(account.getAccountNumber(), amount);

            if (depositStatus != "SUCCESS")
                throw new Exception(depositStatus);

            msgLabel.setText("Transaction successful! Amount deposited: $" + amount);
            msgLabel.setStyle("-fx-font-size: 14; -fx-text-fill: darkgreen;");

            transitionToOptionsScreen();

        } catch (Exception e) {
            msgLabel.setText(Utils.exceptionToString(e));
        }
    }

    @FXML
    private void withdrawButtonClicked() {
        Account currentAccount = mbank.getAccountOfCurrentCustomer();

        try {
            float amount;

            try {
                amount = Float.parseFloat(amountField.getText());
            } catch (Exception e) {
                throw new Exception("Invalid syntax");
            }
            if (amount <= 0)
                throw new Exception("Amount can not be zero or negative!");

            String withdrawStatus = mbank.withdraw(currentAccount.getAccountNumber(), amount);
            if (withdrawStatus != "SUCCESS")
                throw new Exception(withdrawStatus);

            msgLabel.setText("Transaction successful! Amount withdrew: $" + amount);
            msgLabel.setStyle("-fx-font-size: 14; -fx-text-fill: darkgreen;");
            transitionToOptionsScreen();
        } catch (Exception e) {
            msgLabel.setText(Utils.exceptionToString(e));
        }
    }

    private void transitionToOptionsScreen() {

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> loadOptionsScreen());
        delay.play();
    }

    @FXML
    private void transferButtonClicked() {
        Account currentAccount = mbank.getAccountOfCurrentCustomer();

        try {
            String accountString = accountField.getText();

            float amount;
            int accountNumber;

            try {
                accountNumber = Integer.parseInt(accountString);
                if (accountNumber < 0)
                    throw new Exception("Account number can not be a negative number");
            } catch (Exception e) {
                throw new Exception("Invalid syntax for account number!");
            }
            if (accountString.length() != 7)
                throw new Exception("Account must be 7-digit number");

            try {
                amount = Float.parseFloat(amountField.getText());
            } catch (Exception e) {
                throw new Exception("Invalid syntax for amount!");
            }
            if (amount <= 0)
                throw new Exception("Amount can not be zero or negative!");

            String transferStatus = mbank.transfer(currentAccount.getAccountNumber(), accountNumber, amount);
            if (transferStatus != "SUCCESS")
                throw new Exception(transferStatus);

            loadReceiptScreen();
        } catch (Exception e) {
            msgLabel.setText(Utils.exceptionToString(e));
        }
    }

    @FXML
    private void cancelButtonClicked() {
        loadOptionsScreen();
    }

    private void loadOptionsScreen() {
        try {
            FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("/com/bank/optionsScreen.fxml"));
            Parent Root = optionsLoader.load();

            OptionsController optionsController = optionsLoader.getController();
            optionsController.setMbank(mbank);

            Scene optionsScene = new Scene(Root, 640, 480);
            Stage stage = (Stage) msgLabel.getScene().getWindow();
            stage.setScene(optionsScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadReceiptScreen() {
        try {
            FXMLLoader receiptLoader = new FXMLLoader(getClass().getResource("/com/bank/receiptScreen.fxml"));
            Parent Root = receiptLoader.load();

            ReceiptController receiptController = receiptLoader.getController();
            receiptController.setMbank(mbank);

            Scene receiptScene = new Scene(Root, 640, 480);
            Stage stage = (Stage) msgLabel.getScene().getWindow();
            stage.setScene(receiptScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}