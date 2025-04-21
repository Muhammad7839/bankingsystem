package com.bank.Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private static int transactionCounter = 1000;
    private int transactionNumber;
    private int customerNumber;
    private int accountNumber;
    private char transactionType;
    private Date transactionDate;
    private float transactionAmount;

    public Transaction(int customerNumber, int accountNumber, char transactionType, String transactionDate,
            float transactionAmount) {
        this.transactionNumber = generateTransactionNumber();
        setCustomerNumber(customerNumber);
        setAccountNumber(accountNumber);
        setTransactionType(transactionType);
        setTransactionDate(transactionDate);
        setTransactionAmount(transactionAmount);
    }

    public void setTransactionCounter(int start) {
        transactionCounter = start;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        if (isValidCustomerId(customerNumber)) {
            this.customerNumber = customerNumber;
        } else {
            throw new IllegalArgumentException("Customer ID must be a 5-digit integer.");
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        if (isValidAccountNumber(accountNumber)) {
            this.accountNumber = accountNumber;
        } else {
            throw new IllegalArgumentException(
                    "Account number must be a 7-digit number with the last 2 digits matching the account type.");
        }
    }

    public char getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(char transactionType) {
        if (isValidTransactionType(transactionType)) {
            this.transactionType = transactionType;
        } else {
            throw new IllegalArgumentException("Invalid transaction type. Use 'W' for Withdraw or 'D' for Deposit.");
        }
    }

    public String getTransactionDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(transactionDate);
    }

    public void setTransactionDate(String transactionDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.transactionDate = dateFormat.parse(transactionDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use System Date/Timestamp format.");
        }
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        if (transactionAmount >= 0) {
            this.transactionAmount = transactionAmount;
        } else {
            throw new IllegalArgumentException("Transaction amount must be non-negative.");
        }
    }

    private int generateTransactionNumber() {
        return ++transactionCounter;
    }

    private boolean isValidTransactionType(char transactionType) {
        return transactionType == 'W' || transactionType == 'D';
    }

    private boolean isValidCustomerId(int customerNumber) {
        return String.valueOf(customerNumber).length() == 5;
    }

    private boolean isValidAccountNumber(int accountNumber) {
        String accountNumberString = String.valueOf(accountNumber);

        if (accountNumberString.length() != 7) {
            return false;
        }

        return true;
    }
}
