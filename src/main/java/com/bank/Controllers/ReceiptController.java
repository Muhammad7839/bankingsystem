package com.bank.Controllers;

import java.io.IOException;

import com.bank.Classes.Customer;
import com.bank.Classes.Mbank;
import com.bank.Classes.Transaction;
import com.bank.Classes.TransferRecord;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReceiptController {

    @FXML
    private Label dateTimeLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label recipientNameLabel;

    @FXML
    private Label recipientAccountLabel;

    @FXML
    private Label recipientAddressLabel;

    @FXML
    private Label senderNameLabel;

    @FXML
    private Label senderAccountLabel;

    @FXML
    private Label senderAddressLabel;

    private Mbank mbank;

    public void setMbank(Mbank mbank) {
        this.mbank = mbank;
        displayReciept();
    }

    public void displayReciept() {
        TransferRecord T = mbank.getLastTransfer();
        Transaction depositTransaction = T.getDepositTransaction();
        Transaction withdrawTransaction = T.getWithdrawTransaction();
        int dCustomer = depositTransaction.getCustomerNumber();
        int wCustomer = withdrawTransaction.getCustomerNumber();

        dateTimeLabel.setText(depositTransaction.getTransactionDate());
        amountLabel.setText("Amount: $" + depositTransaction.getTransactionAmount());

        Customer customer = mbank.getCustomer(wCustomer);
        senderNameLabel.setText(customer.getCustomerName());
        senderAccountLabel.setText("Account Number: " + withdrawTransaction.getAccountNumber());
        senderAddressLabel.setText("Address: " + customer.getCustomerAddress());

        customer = mbank.getCustomer(dCustomer);
        recipientNameLabel.setText(customer.getCustomerName());
        recipientAccountLabel.setText("Account Number: " + depositTransaction.getAccountNumber());
        recipientAddressLabel.setText("Address: " + customer.getCustomerAddress());

    }

    private void loadOptionsScreen() {
        try {
            FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("/com/bank/optionsScreen.fxml"));
            Parent Root = optionsLoader.load();

            OptionsController optionsController = optionsLoader.getController();
            optionsController.setMbank(mbank);

            Scene optionsScene = new Scene(Root, 640, 480);
            Stage stage = (Stage) amountLabel.getScene().getWindow();
            stage.setScene(optionsScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeButtonClicked() {
        loadOptionsScreen();
    }
}