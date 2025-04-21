package com.bank.Controllers;

import java.io.IOException;

import com.bank.Classes.Account;
import com.bank.Classes.Customer;
import com.bank.Classes.Mbank;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OptionsController {

    private Mbank mbank;

    @FXML
    private Label nameLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label accountTypeLabel;

    public void setMbank(Mbank mbank) {
        this.mbank = mbank;
        Account customerAccount = mbank.getAccountOfCurrentCustomer();
        Customer customer = mbank.getCurrentCustomer();
        nameLabel.setText("Welcome, " + customer.getCustomerName());
        balanceLabel.setText("$" + customerAccount.getBalance());
        accountNumberLabel.setText("Account number: " + customerAccount.getAccountNumber());
        int accountType = customerAccount.getAccountType();
        String accountTypeString;
        if (accountType == Account.CHECKING)
            accountTypeString = "Checking";
        else
            accountTypeString = "Savings";

        accountTypeLabel.setText("Account Type: " + accountTypeString);

    }

    @FXML
    private void depositButtonClicked() {
        try {
            FXMLLoader depositLoader = new FXMLLoader(getClass().getResource("/com/bank/depositScreen.fxml"));
            Parent optionsRoot = depositLoader.load();

            TransactionController transactionController = depositLoader.getController();
            transactionController.setMbank(mbank, "Deposit");

            Scene depositScene = new Scene(optionsRoot, 640, 480);
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(depositScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void withdrawButtonClicked() {
        try {
            FXMLLoader withdrawLoader = new FXMLLoader(getClass().getResource("/com/bank/withdrawScreen.fxml"));
            Parent optionsRoot = withdrawLoader.load();

            TransactionController transactionController = withdrawLoader.getController();
            transactionController.setMbank(mbank, "Withdraw");

            Scene withdrawScene = new Scene(optionsRoot, 640, 480);
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(withdrawScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void transferButtonClicked() {
        try {
            FXMLLoader transferLoader = new FXMLLoader(getClass().getResource("/com/bank/transferScreen.fxml"));
            Parent optionsRoot = transferLoader.load();

            TransactionController transactionController = transferLoader.getController();
            transactionController.setMbank(mbank, "Transfer");

            Scene transferScene = new Scene(optionsRoot, 640, 480);
            Stage stage = (Stage) nameLabel.getScene().getWindow();
            stage.setScene(transferScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void processLogoutButtonClicked() {
        mbank.logout();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/login.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        LoginController loginController = loader.getController();
        loginController.setMbank(mbank);

        Scene loginScene = new Scene(root, 640, 480);
        Stage currentStage = (Stage) nameLabel.getScene().getWindow();
        currentStage.setScene(loginScene);
    }

}
